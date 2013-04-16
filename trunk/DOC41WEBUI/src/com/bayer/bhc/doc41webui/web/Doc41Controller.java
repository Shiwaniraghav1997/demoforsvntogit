package com.bayer.bhc.doc41webui.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bayer.bhc.doc41webui.common.Doc41SessionKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.Convert;
import com.bayer.bhc.doc41webui.common.util.DateRenderer;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.AbortableCommandForm;
import com.bayer.bhc.doc41webui.container.AbstractCommandForm;
import com.bayer.bhc.doc41webui.container.Command;
import com.bayer.bhc.doc41webui.container.ResetableForm;
import com.bayer.bhc.doc41webui.container.ValidableForm;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.domain.VersionizedDomainObject;
import com.bayer.bhc.doc41webui.integration.db.SessionDataDAO;
import com.bayer.bhc.doc41webui.usecase.DisplaytextUC;
import com.bayer.bhc.doc41webui.usecase.SystemParameterUC;
import com.bayer.bhc.doc41webui.usecase.UserManagementUC;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.NestingException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.business.sbcommon.SessionDataDC;

/**
 * 
 * @author ezzqc,imrol
 */
@SuppressWarnings("deprecation")
public abstract class Doc41Controller extends SimpleFormController implements Doc41SessionKeys {

	public static final String TAB_ID_KEY = "tabId";
	// compare *.jsp
	protected static final String DATE_PATTERN = "datePattern";
	protected static final String DATE_MASK_PATTERN = "dateMaskPattern";

	private String abortAction;

	private String successAction;

	@Autowired
	private SessionDataDAO sessionDataDAO;

	private DisplaytextUC displaytextUC;

	private UserManagementUC userManagementUC;

	private SystemParameterUC systemParameterUC;

	public String getAbortAction() {
		return abortAction;
	}

	public void setAbortAction(String abortAction) {
		this.abortAction = abortAction;
	}

	public String getSuccessAction() {
		return successAction;
	}

	public void setSuccessAction(String successAction) {
		this.successAction = successAction;
	}

	public DisplaytextUC getDisplaytextUC() {
		return displaytextUC;
	}

	public void setDisplaytextUC(DisplaytextUC displaytextUC) {
		this.displaytextUC = displaytextUC;
	}

	public UserManagementUC getUserManagementUC() {
		return userManagementUC;
	}

	public void setUserManagementUC(UserManagementUC userManagementUC) {
		this.userManagementUC = userManagementUC;
	}

	public SystemParameterUC getSystemParameterUC() {
		return systemParameterUC;
	}

	public void setSystemParameterUC(SystemParameterUC systemParameterUC) {
		this.systemParameterUC = systemParameterUC;
	}

	protected abstract boolean hasRolePermission(User usr);

	// no form needed.
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		return new AbortableCommandForm();
	}

	protected boolean isFormSubmission(HttpServletRequest request) {
		// send via POST and does not come via the sendGet() method inside - compare prolog.jspf:
		return super.isFormSubmission(request) && !"true".equals(request.getParameter("render"));
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		User usr = determineUser(request);
		try {
			HttpSession session= request.getSession();
			// load persisted session data into HTTPSession:
			SessionDataDC dbSessionDC = retrieveSessionData(usr, session);
	
			try {
				mav = handleWithPermissionCheck(request, response);
	
				// do the postRender intercepter job:
				String ctrl = this.getClass().getName();
				StringTokenizer st = new StringTokenizer(ctrl, ".");
				while (st.hasMoreTokens())  ctrl = st.nextToken();
		
				session.setAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_CTRL, ctrl.toLowerCase());
				SessionModelAndView smav = new SessionModelAndView(mav.getViewName(), mav.getModelMap());
				session.setAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_MAV, smav);
				
			} finally {
				// persist HTTPSession into session data in DB:
				persistSessionData(usr,session,dbSessionDC);
				Doc41Log.get().logWebMetrix(request, this, request.getRequestURI());
			}
			
	
			// it's important to keep the given TAB-ID:
			if (mav != null && mav.getViewName() != null && mav.getViewName().startsWith("redirect:/")) {
				mav.getModel().clear();
				String tabId = getBrowserTabId(request);
				// don't append if invalid, e.g. '0':
				if (tabId.length() > 3) mav.addObject(TAB_ID_KEY, getBrowserTabId(request));
			}
	
			return mav;
		} catch (Exception e1) {
			if(!(e1 instanceof NestingException)){ //to prevent duplicate logs as the NestingException constructor already logs
				Doc41Log.get().error(
						this.getClass(),
						usr.getCwid(),e1);
			}
			throw e1;
		}
	}
	
	protected final SessionDataDC retrieveSessionData(User pUser,HttpSession pSession) throws IOException,ClassNotFoundException{
		if (pUser == null || pSession == null) {
			return null;
		}
		if (!systemParameterUC.isDBSessionPersistence()) {
			return null;
		}
		long startTime = System.currentTimeMillis();
		SessionDataDC dbSessionDC = null;
		try {
			dbSessionDC = sessionDataDAO.getSessionData(pUser.getCwid());
		} catch (Doc41TechnicalException e1) {
			Doc41Log.get().error(
					this.getClass(),
					pUser.getCwid(),"Problem reading SessionData:"+e1.getMessage());
		}
		if (dbSessionDC == null) {
			// session not in DB
			dbSessionDC = new SessionDataDC();
			dbSessionDC.setId(pUser.getCwid());
		}
		else {
			long readTime = System.currentTimeMillis();
			if (dbSessionDC.getEndoflife().before(new Date())){
				dbSessionDC.setData(null);
				Doc41Log.get().debug(
						this.getClass(),
						pUser.getCwid(),
						"emptied old sessionData.");					
			}
			//stuff DB session props into HttpSession
			String sessionData=dbSessionDC.getData();
			if (sessionData != null){
				ObjectInputStream oIn =null;
				try {
					ByteArrayInputStream bIn = new ByteArrayInputStream(Convert.base64Decode(sessionData));
					GZIPInputStream zIn =new GZIPInputStream(bIn);
//	                ZipInputStream zIn = new ZipInputStream(bIn);
//	                zIn.getNextEntry();
					oIn = new ObjectInputStream(new BufferedInputStream(zIn));
					Doc41Log.get().debug(
							this.getClass(),
							pUser.getCwid(),
							"Reading session objects from DB:"
									+ bIn.available()+" took "+(readTime-startTime)+"ms.");
					while (true){
						String attribName = null;
						try {
							attribName= (String) oIn.readObject();
						} catch (EOFException e) {
							Doc41Log.get().debug(
									this.getClass(),
									pUser.getCwid(),
									"Reached end of session data.");
						}
						if (attribName == null){
							break;
						}
						Object attrib= oIn.readObject();
//						Doc41Log.get().debug(
//								this.getClass(),
//								pUser.getCwid(),
//								"Read object from DB:"
//										+ attribName
//										+ " object:"
//										+ attrib);
						pSession.setAttribute(attribName, attrib);
					}
				} catch (Throwable e){
					Doc41Log.get().error(
							this.getClass(),
							pUser.getCwid(),
							"Session object could not be completely restored: "+e.getMessage());					
				}
				finally {
					if (oIn !=null){
						oIn.close();						
					}
				}
			}
		}
		Doc41Log.get().debug(
				this.getClass(),
				pUser.getCwid(),
				"Read SessionData OID:"
						+ dbSessionDC.getObjectID()
						+ " changed:"
						+ dbSessionDC.getChanged()
						+ " size:"
						+ StringTool.nvl(dbSessionDC.getData(), "")
								.length()+" total processing time: "+(System.currentTimeMillis()-startTime)+"ms.");
		return dbSessionDC;
	}
	
	protected final void persistSessionData(User pUser,HttpSession pSession,SessionDataDC pSessionDataDC) throws IOException{
		if (pUser == null) {
			return;
		}
		if (!systemParameterUC.isDBSessionPersistence()){
			return;
		}
		ObjectOutputStream outStream = null;
		try {
			long startTime = System.currentTimeMillis();
			ByteArrayOutputStream boutStream = new ByteArrayOutputStream();
			GZIPOutputStream zoutStream = new GZIPOutputStream(boutStream);
//            ZipOutputStream zoutStream = new ZipOutputStream(boutStream);
//            zoutStream.putNextEntry(new ZipEntry("session"));
			outStream = new ObjectOutputStream(new BufferedOutputStream(zoutStream));
			checkSessionAndStream(pSession, outStream);
//            zoutStream.closeEntry();
            outStream.close();
			if (pSessionDataDC == null) {
				return;
			}
			try {
				pSessionDataDC.setData(Convert.base64Encode(boutStream.toByteArray()));
				sessionDataDAO.store(pSessionDataDC);
				
				Doc41Log.get().debug(
						this.getClass(),
						pUser.getCwid(),
						"Written SessionData OID:"
								+ pSessionDataDC.getObjectID() + " changed:"
								+ pSessionDataDC.getChanged() + " size:"
								+ pSessionDataDC.getData().length()+" took: "+(System.currentTimeMillis()-startTime)+"ms.");
			} catch (Doc41TechnicalException e1) {
				Doc41Log.get().error(this.getClass(), pUser.getCwid(),
						"Problem persisting SessionData:" + e1.getMessage());
			}
		} catch (Throwable e){
			Doc41Log.get().error(
					this.getClass(),
					pUser.getCwid(),
					"Session object could not be persisted: "+e.getMessage());								
		} finally {
			if (outStream != null) {
				outStream.close();
			}
		}
		
	}
	
	private void checkSessionAndStream(HttpSession session, ObjectOutputStream pOutStream) throws IOException {
		String attributName = null;
		
		try {
			@SuppressWarnings("unchecked")
			Enumeration<String> attributeNames = session.getAttributeNames();
			while (attributeNames.hasMoreElements()) {
				attributName = attributeNames.nextElement();
				Object attribute = session.getAttribute(attributName);
				checkAndStream(attributName, pOutStream);
				checkAndStream(attribute, pOutStream);
//				Doc41Log.get().debug(
//						this.getClass(),
//						"System",
//						"Wrote object from DB:"
//								+ attributName
//								+ " object:"
//								+ attribute);
			}
		} catch (NotSerializableException e) {
			Doc41Log.get().error(
					getClass(),
					null,
					"not serializable object in session attribute :"
							+ attributName);
			throw e;
		}

	}

	private void checkAndStream(Object testee, ObjectOutputStream pOutStream) throws IOException {
		pOutStream.writeObject(testee);
		pOutStream.flush();
	}

	private ModelAndView handleWithPermissionCheck(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Doc41Log.get().debug(this.getClass(), "current URI: ", request.getRequestURI());
		User usr = determineUser(request);

		if (usr == null) {
			// rescue where the original request wanted to go...
			// due to Spring MVC, one cannot directly send redirect on the HTTP
			// response :-( so redirecting is quite a bad hack
			if (request.getSession().getAttribute(DOC41_ORIGIN_URI) == null
					&& !request.getRequestURI().contains("userprofile")) {
				
				request.getSession().setAttribute(DOC41_ORIGIN_URI,request.getRequestURI()+"?"+request.getQueryString());
				Doc41Log.get().debug(this.getClass(),
						"No User, saved URI for redirect: ",
						request.getRequestURI());
			}
			return new ModelAndView("login/login");

		} else {
			if(!usr.getActive()){
				return new ModelAndView("login/forbidden");
			}
			
			String originURI = null;
			try {
				originURI = (String) request.getSession().getAttribute(
						DOC41_ORIGIN_URI);
			} catch (Exception e) {
				// ignore any eventual cast problems to continue processing
			}
			request.getSession().removeAttribute(DOC41_ORIGIN_URI);
			if (originURI != null) {
				String prefix = getServletContext().getContextPath() + "/";
				// Doc41Log.get().debug(this.getClass(),"APP Prefix: ",prefix);
				originURI = originURI.replace(prefix, "redirect:/");
				Doc41Log.get().debug(this.getClass(),
						"redirect to saved URI: ", originURI);
				ModelAndView mav = new ModelAndView(originURI);
				return mav;
			}
			if (hasRolePermission(usr)) {
				String tmpLanguage = usr.getLocale().getLanguage();
				if (tmpLanguage == null) {
					tmpLanguage = request.getLocale().getLanguage();
				}
				LocaleInSession.put(new Locale(tmpLanguage));

				String datePattern = DateRenderer.getDatePattern(LocaleInSession.get());
				String dateMaskPattern = generateMaskPattern(datePattern);
				return super
						.handleRequest(request, response)
						.addObject("user", usr)
						.addObject(TAB_ID_KEY, getBrowserTabId(request))
						.addObject(DATE_PATTERN,datePattern)
						.addObject(DATE_MASK_PATTERN,dateMaskPattern);

			}
		}
		return new ModelAndView("login/forbidden");
	}

	private String generateMaskPattern(String datePattern) {
		if(datePattern==null){
			return null;
		}
		return datePattern.replace('y', '9').replace('M', '9').replace('d', '9');
	}

	/**
	 * The user according to the cwid given by request will be inspected. The
	 * role is checked whether it is a readonly role. This information will be
	 * deposited in the session and in the thread as well. For each thread first
	 * the session will be inspected.
	 * 
	 * @param request
	 */
	protected final User determineUser(HttpServletRequest request) {

		String cwid = null;
		// Principal requestPrinciple = determineUserPrincipal(request);
		User user = (User) request.getSession().getAttribute(DOC41_USER);
		String webSealCwid = getWebSealUser(request);
		
		//kill session if webseal user differs from session user
		if(webSealCwid!=null && user !=null && user.getCwid()!=null
				&& !webSealCwid.equalsIgnoreCase(user.getCwid())){
			user = null;
			request.getSession().invalidate();
			request.getSession(true);
		}

		if (user != null && (webSealCwid==null || webSealCwid.equalsIgnoreCase(user.getCwid()) )) {
			cwid = user.getCwid();
		} else {
			cwid = webSealCwid;

			if (cwid == null) {
				cwid = System.getProperty("defaultUser");
			}

			if (cwid == null) {
				String requestCwid = request.getParameter("cwid");
				String password = request.getParameter("password");

				/* execute the authentication */
				try {
					if (requestCwid != null && password != null) {
						if (userManagementUC.isAuthenticated(requestCwid.trim(), password.trim())) {
							cwid = requestCwid;
						} else {
							Doc41Log.get().error(this.getClass(), requestCwid, "cwid or password wrong");
						}
					}
				} catch (Doc41BusinessException e) {
					Doc41Log.get().error(this.getClass(), requestCwid, "cwid couldn't be authenticated");
				}
			}
		}

		// get the user info for given cwid from the DB:
		if (cwid != null && user == null) {
			try {
				LocaleInSession.put(Locale.US);
				user = userManagementUC.findUser(cwid.toUpperCase());
				
				if (null != user && null != user.getCwid()) {
					if (!user.getRoles().contains(User.ROLE_OBSERVER)) {
						user.setReadOnly(Boolean.FALSE);
					}
				}
			} catch (Doc41BusinessException e) {
				Doc41Log.get().error(this.getClass(), cwid, "no user found");
				user = null;
			}
		}

		request.getSession().setAttribute(DOC41_USER, user);

		if (user != null) {
			Doc41Log.get().debug(this.getClass(), "USER:", user.getCwid());
			LocaleInSession.put(user.getLocale());
		} else {
			Doc41Log.get().debug(this.getClass(), "USER:", "user is null");
			LocaleInSession.put(null);
		}
		UserInSession.put(user);
		return user;
	}

	// private User createTestUser(String cwid) {
	// User user = new User();
	// user.setCwid(cwid);
	// user.setReadOnly(Boolean.FALSE);
	// user.getRoles().add(User.ROLE_TECH_ADMIN);
	// user.getRoles().add(User.ROLE_INSIDE_SALES);
	// user.getRoles().add(User.ROLE_BUSINESS_ADMIN);
	// return user;
	// }
	//

	protected String getWebSealUser(HttpServletRequest request) {
		String tmpCwid=request.getHeader("iv-user");
		String tmpHost=request.getRemoteHost();
		String tmpAddr=request.getRemoteAddr();
		// check for positive list (IPs ofWEBSEAL servers), list maintained in DB, properties, etc.
		// if not host in list return null
		
		if (tmpCwid !=null){
			Doc41Log.get().debug(this.getClass(), tmpCwid, "WEBSEAL user request from: ["+tmpHost+"] "+tmpAddr+" for uri: "+request.getRequestURI()+" / url: "+request.getRequestURL());
			@SuppressWarnings("unchecked")
			Map<String,String> subConfig = ConfigMap.get().getSubConfig("doc41controller", "websealcheck");
			String allowedIPs = subConfig.get("allowedIPs");
			if(allowedIPs!=null && allowedIPs.contains(tmpAddr)){
				return tmpCwid;
			}
		}
		return null;
	}


	

	public boolean isResetRequest(AbstractCommandForm cmd) {
		if (cmd == null) {
			return false;
		}
		return Command.RESET.getAction().equals(cmd.getCommand());
	}

	public boolean isAbortRequest(AbstractCommandForm cmd) {
		if (cmd == null) {
			return false;
		}
		return Command.ABORT.getAction().equals(cmd.getCommand());
	}

	protected Long getDcId(String id) {
		return VersionizedDomainObject.getDcId(id);
	}

	protected final Long getGivenDcId(HttpServletRequest request, String idName) {
		return getDcId(getGivenId(request, idName));
	}

	protected final String getGivenId(HttpServletRequest request, String idName) {
		// overwrite if given via action:
		String id = request.getParameter(idName);
		if (id != null)
			request.getSession().setAttribute(idName, id);

		// System.out.println(idName + " -> " +
		// request.getSession().getAttribute(idName));
		return (String) request.getSession().getAttribute(idName);
	}

	/**
	 * 
	 * Set the target for redirect back. In order to use it, configure the
	 * corresponding abortAction in the .xml appropriatly, e.g.:
	 * 
	 * <property name="abortAction" value="BACK"/>
	 * 
	 * @param pRequest
	 * @param target
	 */
	protected void setBackAction(HttpServletRequest request, String target) {
		request.getSession().setAttribute(BACK, target);
	}

	/*
	 * Use back action, if set. Else use abort action. Clear back action if
	 * used.
	 */
	protected ModelAndView redirectOnAbort(HttpServletRequest request, HttpServletResponse response) {
		// System.out.println("redirectOnAbort: "+getAbortAction());
		if (getAbortAction() == null) {
			return new ModelAndView(getSuccessView());
		}

		String backDirection = (String) request.getSession().getAttribute(getAbortAction());
		// System.out.println("backDirection: "+backDirection);
		if (backDirection != null) {
			setBackAction(request, null);
			return new ModelAndView(backDirection); // e.g.
													// "redirect:/operatingoverview.htm");
		} else {
			return new ModelAndView(getAbortAction());
		}
	}

	protected ModelAndView redirectOnSuccess(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {
		if (errors.hasErrors()) {
			return super.onSubmit(request, response, command, errors);
		}

		if (getSuccessAction() == null) {
			return super.onSubmit(request, response, command, errors);
		}
		if (getSuccessAction().trim().length() == 0) {
			return super.onSubmit(request, response, command, errors);
		}
		if (getSuccessAction().trim().equals(RENDER)) {
			return super.onSubmit(request, response, command, errors);
		}

		return new ModelAndView(getSuccessAction());
	}

	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {
		// System.out.println("processFormSubmission");
		// System.out.println(command.getClass());

		if (command instanceof AbstractCommandForm) {
			// System.out.println(((AbstractCommandForm)command).getCommand());

			if (isAbortRequest((AbstractCommandForm) command)) {
				((AbstractCommandForm) command).setCommand("");
				return redirectOnAbort(request, response);

			} else if (isResetRequest((AbstractCommandForm) command)) {
				if (command instanceof ResetableForm) {
					((ResetableForm) command).reset();
				}
			}

			// no abort or reset - do a further validation via the form:
			((ValidableForm) command).validate(request, errors);
		}
		try {
			return super.processFormSubmission(request, response, command,
					errors);
			// even in case of errors we want to get to the onSubmit() of the
			// controllers,
			// as we fetch some of the data for the view in subsequent .load
			// (Ajax) call:
			// // return onSubmit(request, response, command, errors);

		} catch (Exception e) {
			// compare CargoCockpitExceptionResolver:
			SessionModelAndView smav = (SessionModelAndView) request
						.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_MAV);
			ModelMap map = smav.getModel();
			if (map != null) {

				// take the submitted form:
				map.put(getCommandName(), command);

				for (String k : map.keySet()) {
					// take the corresponding errors of the submitted form:
					if (map.get(k) instanceof BindingResult) {
						map.put(k, errors);
					}
				}
			}
			throw e;
		}

		// if (!errors.hasErrors()) {
		// redirectOnSuccess(request, response, errors);
		// }
	}

	/*
	 * return 0 if new tab, i.e. unknown tab
	 */
	protected String getBrowserTabId(HttpServletRequest request) {
		String tabId = ServletRequestUtils.getStringParameter(request, TAB_ID_KEY, null);
		if (tabId == null) {
			tabId = (String) request.getAttribute(TAB_ID_KEY);
		}
		if (tabId != null) {
			return tabId;
		}
		// System.out.println("tab: "+request.getParameter("tabId"));

		return "0"; // request.hashCode()+"";
	}

	/**
	 * Implement this method to create a BrowserTabAttributes object specific
	 * for the Controller, if necessary
	 * 
	 * @return BrowserTabAttributes specific for the Controller
	 */
	protected BrowserTabAttributes createBrowserTabAttributes(String aVersionId) {
		return new BrowserTabAttributes();
	}

	protected BrowserTabAttributes getBrowserTabAttributes(HttpServletRequest request, String aVersionId) {
		// one order model (with filter) per tab of the session
		// using the Form(Controller) specific naming prefix for storing the
		// attributes should avoid conflicts
		String requestedTabId = getBrowserTabId(request);

		return getBrowserTabAttributes(request, aVersionId, requestedTabId);
	}

	protected BrowserTabAttributes getBrowserTabAttributes(HttpServletRequest request, String aVersionId, String requestedTabId) {
		@SuppressWarnings("unchecked")
		Map<String, BrowserTabAttributes> tabMap = 
		(Map<String, BrowserTabAttributes>) request.getSession().getAttribute(TAB_MAP);


		if (tabMap == null) {
			tabMap = new Hashtable<String, BrowserTabAttributes>();
			request.getSession().setAttribute(TAB_MAP, tabMap);
		}

		// there might be a tabId stored in the browser - if so, but no
		// attributes can be retrieved from the map, reuse this id!
		// (Otherwise there may exist a web page with a lot of invalid links
		// (old tabId)
		synchronized(tabMap){
			BrowserTabAttributes attribs = tabMap.get(requestedTabId + aVersionId);
			if (attribs == null) {
				attribs = createBrowserTabAttributes(aVersionId);
				if ("0".equals(requestedTabId)) {
					attribs.setTabId("" + System.currentTimeMillis());
				} else {
					attribs.setTabId(requestedTabId);
				}
				// further lookups on this request should deliver this newly created
				// id
				request.setAttribute(TAB_ID_KEY, attribs.getTabId());
	
				// using the Form(Controller) specific naming prefix for storing the
				// attributes should avoid conflicts
				// take the newly generated id as postfix
				tabMap.put(attribs.getTabId() + aVersionId, attribs);
				Doc41Log.get().debug(
						this.getClass(),
						"System",
						"getBrowserTabAttributes(" + aVersionId
								+ ")-created new  instance: " + attribs);
			}
			//refresh the timestamp
			attribs.setTimestamp(System.currentTimeMillis());
			// cleanup old data
			Iterator<String> browserTabIterator = tabMap.keySet().iterator();
			//halve session life time
			long tooOldTime=System.currentTimeMillis()-SessionDataDAO.getSessionLifeTime()*500L;
			while (browserTabIterator.hasNext()) {
				BrowserTabAttributes tmpAttribs=tabMap.get(browserTabIterator.next());
				if (tmpAttribs.getTimestamp()<tooOldTime) {
					browserTabIterator.remove();
					Doc41Log.get().debug(
							this.getClass(),
							"System",
							"removed old BrowserTabAttributes "+tmpAttribs);
				}
				else {
//					Doc41Log.get().debug(
//						this.getClass(),
//						"System",
//						"active BrowserTabAttributes "+tmpAttribs);
				}
			}
			return attribs;
		}
	}

}
