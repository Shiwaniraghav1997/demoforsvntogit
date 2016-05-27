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
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.Doc41SessionKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.Convert;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.integration.db.SessionDataDAO;
import com.bayer.bhc.doc41webui.usecase.SystemParameterUC;
import com.bayer.bhc.doc41webui.usecase.UserManagementUC;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.NestingException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.business.sbcommon.SessionDataDC;

/**
 * preHandle runs before each Controller, postHandle afterwards
 * @author evayd
 *
 */
public class Doc41HandlerInterceptor extends HandlerInterceptorAdapter implements Doc41SessionKeys {
	
	private static final String URI_FORBIDDEN = "/login/forbidden";

	private static final String URI_LOGIN = "/login/login";

	private static final String DB_SESSION_DC_REQ_ATTR ="DB_SESSION_DC_REQ_ATTR";
	
	private static final String CWID_PREFIX_DOC_SERVICE = "DS_";
    private static final String CWID_DOC_SERVICE_CARR = "DS_CARR";
	
	@Autowired
	protected UserManagementUC userManagementUC;
	@Autowired
	protected SystemParameterUC systemParameterUC;
	@Autowired
	protected SessionDataDAO sessionDataDAO;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		User usr = determineUser(request);
		HttpSession session= request.getSession();
		try{
			Doc41Log.get().debug(this.getClass(), "current URI: ", request.getRequestURI());
			
			// load persisted session data into HTTPSession:
			SessionDataDC dbSessionDC = retrieveSessionData(usr, session);
			request.setAttribute(DB_SESSION_DC_REQ_ATTR, dbSessionDC);
			
			if (usr == null) {
				response.sendRedirect(request.getContextPath() +URI_LOGIN);
				return false;
	
			} else {
				if(!usr.getActive() || !hasRolePermission(usr,handler,request)){
					response.sendRedirect(request.getContextPath() +URI_FORBIDDEN);
					return false;
				}
	
				Locale tmp = usr.getLocale(); //.getLanguage();
				if (tmp == null) {
					tmp = request.getLocale(); //.getLanguage();
				}
				LocaleInSession.put(tmp); // danger, determnUser does also, but without fallback!!!
				/* Add user to request */
				request.setAttribute(USER, usr);
			}
			return true;
		} catch (NestingException e1) {
		    //no logging for NestingException to prevent duplicate logs as the NestingException constructor already logs
            throw e1;
		} catch (Exception e1) {
            Doc41Log.get().error(
		            this.getClass(),
		            (usr!=null)?usr.getCwid():"",e1);
			throw e1;
		}
	}
	
	
	private boolean hasRolePermission(User usr, Object handler, HttpServletRequest request) throws Doc41BusinessException{
		Object realHandler = getRealHandler(handler);
		if(realHandler instanceof AbstractDoc41Controller){
			AbstractDoc41Controller d41Controller = (AbstractDoc41Controller) realHandler;
			return d41Controller.hasPermission(usr,request);
		}
		return true;
	}


	private Object getRealHandler(Object handler) {
		Object realHandler = handler;
		if(handler instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod) handler;
			realHandler = hm.getBean();
		}
		return realHandler;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
		try{
			// do the postRender intercepter job:
			String ctrl = getRealHandler(handler).getClass().getName();
			StringTokenizer st = new StringTokenizer(ctrl, ".");
			while (st.hasMoreTokens()){
			    ctrl = st.nextToken();
			}

			request.getSession().setAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_CTRL, ctrl.toLowerCase());
			if(modelAndView!=null){
				String viewName = modelAndView.getViewName();
				if(viewName!=null){
					request.getSession().setAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_VIEW, viewName);
				}
			}
		} catch (Exception e1) {
			User usr = determineUser(request);
			Doc41Log.get().error(
					this.getClass(),
					usr.getCwid(),e1);
			throw e1;
		}

	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// persist HTTPSession into session data in DB:
		User usr = UserInSession.get();
		if(usr!=null){
			HttpSession session= request.getSession();
			SessionDataDC dbSessionDC = (SessionDataDC) request.getAttribute(DB_SESSION_DC_REQ_ATTR);
			persistSessionData(usr,session,dbSessionDC);
			Doc41Log.get().logWebMetrix(request, getRealHandler(handler), request.getRequestURI());
		}
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
		User user = (User) request.getSession().getAttribute(DOC41_USER);
		String webSealCwid = getWebSealUser(request);
		User docServiceUser = getDocServiceUser(request);
		
		//kill session if webseal user differs from session user
		if(webSealCwid!=null && user !=null && user.getCwid()!=null
				&& !webSealCwid.equalsIgnoreCase(user.getCwid())){
			user = null;
			request.getSession().invalidate();
			request.getSession(true);
		}
		
		//kill session if docservice user in session but not in request
		if(isDocServiceUser(user) && (docServiceUser == null || !StringTool.equals(user.getCwid(), docServiceUser.getCwid()))){
		    user = null;
            request.getSession().invalidate();
            request.getSession(true);
		}

		if (user != null && (webSealCwid==null || webSealCwid.equalsIgnoreCase(user.getCwid()) )) {
			cwid = user.getCwid();
		} else if(docServiceUser!=null){
		    if(user==null || !StringTool.equals(user.getCwid(), docServiceUser.getCwid())){
		        user=docServiceUser;
		    }
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
					Doc41Log.get().error(this.getClass(), requestCwid, e);
				}
			}
		}

		// get the user info for given cwid from the DB:
		if (cwid != null && user == null) {
			try {
				LocaleInSession.put(Locale.US);
				user = userManagementUC.findUser(cwid.toUpperCase());
				
				if (null != user && null != user.getCwid()
				        && !user.hasPermission(Doc41Constants.PERMISSION_READ_ONLY)) {
				    user.setReadOnly(Boolean.FALSE);
				}
			} catch (Doc41BusinessException e) {
				Doc41Log.get().error(this.getClass(), cwid, "no user found");
				Doc41Log.get().error(this.getClass(), cwid, e);
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
	
	private User getDocServiceUser(HttpServletRequest request) {
	    String tmpCwid=request.getHeader("docservice-user");
        String tmpRole=request.getHeader("docservice-role");
        String tmpPwd=request.getHeader("docservice-password");
        String tmpHost=request.getRemoteHost();
        String tmpAddr=request.getRemoteAddr();
        try {
            // check for positive list (IPs of servers), list maintained in DB, properties, etc.
            // if not host in list return null
            
            if (!StringTool.isTrimmedEmptyOrNull(tmpCwid)){
                Doc41Log.get().debug(this.getClass(), tmpCwid, "DocService user request from: ["+tmpHost+"] "+tmpAddr+" for uri: "+request.getRequestURI()+" / url: "+request.getRequestURL());
                Properties subConfig = ConfigMap.get().getSubCfg("doc41controller", "docservicecheck");
                String allowedIPs = StringTool.emptyToNull(subConfig.getProperty("allowedIPs"));
                if((allowedIPs == null) || allowedIPs.contains("," + tmpAddr + ",")) {
                    String password = StringTool.decodePassword(subConfig.getProperty("pwd_"+tmpRole));
                    if(!StringTool.isTrimmedEmptyOrNull(tmpPwd) && !StringTool.isTrimmedEmptyOrNull(password)
                            && StringTool.equals(tmpPwd, password)){
                        // TODO: if introduce further remote roles, introduce a external service function TO service user MAPPING. currently only SD_CARR allowed
                        if(LocaleInSession.get()==null){
                            LocaleInSession.put(Locale.US);
                        }
                        User tmpUser = userManagementUC.findUser(CWID_DOC_SERVICE_CARR);
                        tmpUser.setSkipCustomerCheck(true);
                        tmpUser.setSkipVendorCheck(true);
                        tmpUser.setSkipCountryCheck(true);
                        return tmpUser;
                    }
                }
            }
        } catch (Doc41BusinessException e) {
            Doc41Log.get().error(this.getClass(), tmpCwid, e);
        }
        return null;
    }
	
	private boolean isDocServiceUser(User user) {
        return (user !=null && user.getCwid()!=null && user.getCwid().startsWith(CWID_PREFIX_DOC_SERVICE));
    }


    protected String getWebSealUser(HttpServletRequest request) {
		String tmpCwid=request.getHeader("iv-user");
		String tmpHost=request.getRemoteHost();
		String tmpAddr=request.getRemoteAddr();
		// check for positive list (IPs ofWEBSEAL servers), list maintained in DB, properties, etc.
		// if not host in list return null
		
		if (tmpCwid !=null){
			Doc41Log.get().debug(this.getClass(), tmpCwid, "WEBSEAL user request from: ["+tmpHost+"] "+tmpAddr+" for uri: "+request.getRequestURI()+" / url: "+request.getRequestURL());
			Properties subConfig = ConfigMap.get().getSubCfg("doc41controller", "websealcheck");
			String allowedIPs = subConfig.getProperty("allowedIPs");
			if(allowedIPs!=null && allowedIPs.contains(tmpAddr)){
				return tmpCwid;
			}
		}
		return null;
	}
	
	
	private final SessionDataDC retrieveSessionData(User pUser,HttpSession pSession) throws IOException,ClassNotFoundException{
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
			Doc41Log.get().error(this.getClass(), pUser.getCwid(), e1);
		}
		if (dbSessionDC == null) {
			// session not in DB
			dbSessionDC = new SessionDataDC();
			dbSessionDC.setId(pUser.getCwid());
		} else {
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
							Doc41Log.get().error(this.getClass(), pUser.getCwid(), e);
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
				} catch (Exception e){
					Doc41Log.get().error(
							this.getClass(),
							pUser.getCwid(),
							"Session object could not be completely restored: "+e.getMessage());	
					Doc41Log.get().error(this.getClass(), pUser.getCwid(), e);
				} finally {
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
			outStream = new ObjectOutputStream(new BufferedOutputStream(zoutStream));
			checkSessionAndStream(pSession, outStream);
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
				Doc41Log.get().error(this.getClass(), pUser.getCwid(), e1);
			}
		} catch (Exception e){
			Doc41Log.get().error(
					this.getClass(),
					pUser.getCwid(),
					"Session object could not be persisted: "+e.getMessage());		
			Doc41Log.get().error(this.getClass(), pUser.getCwid(), e);
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
}
