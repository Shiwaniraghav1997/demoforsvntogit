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
import java.util.Properties;
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
import com.bayer.ecim.foundation.basic.BooleanTool;
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
    public static final String CWID_DOC_SERVICE_CARR = "DS_CARR";
	
	@Autowired
	protected UserManagementUC userManagementUC;
	@Autowired
	protected SystemParameterUC systemParameterUC;
	@Autowired
	protected SessionDataDAO sessionDataDAO;

    /**
     * Create a toString() dump of a HTTPServletRequest.
     * @param pRequest
     * @return
     */
    public String toString(HttpServletRequest pRequest) {
        return toString(pRequest, -1);
    }

    /**
     * Create a toString() dump of a HTTPServletRequest.
     * @param pRequest
     * @param pDetailLevel level of details:
     *  0 only dump general informations
     *  1 add headers
     *  2 add parameters
     *  3 add attributes
     *  4 dump also included session
     *  5.. reserved (future extensions adding more informations, e.g. cookies, user principals, request dispatcher and servlet context.
     *  -1 dump full, all (now) available information
     * @return
     */
	public String toString(HttpServletRequest pRequest, int pDetailLevel) {
        StringBuffer sb = new StringBuffer(20000);

        if (pRequest == null) {
            return "\nREQUEST: n/a\n\n";
        }
        
        sb.append("\n" +
                "REQUEST:\n" +
                "========\n\n" +
                " getAuthType...................: " + pRequest.getAuthType()                         + "\n" +
                " getCharacterEncoding..........: " + pRequest.getCharacterEncoding()                + "\n" +
                " getContentLength..............: " + pRequest.getContentLength()                    + "\n" +
                " getContentType................: " + pRequest.getContentType()                      + "\n" +
                " getContextPath................: " + pRequest.getContextPath()                      + "\n" +
                " getLocalAddr..................: " + pRequest.getLocalAddr()                        + "\n" +
                " getLocalName..................: " + pRequest.getLocalName()                        + "\n" +
                " getLocalPort..................: " + pRequest.getLocalPort()                        + "\n" +
                " getLocale.....................: " + pRequest.getLocale()                           + "\n" +
                " getLocales....................: " + StringTool.list((Enumeration<?>)pRequest.getLocales(), ",", false) + "\n" +
                " getMethod.....................: " + pRequest.getMethod()                           + "\n" +
                " getPathInfo...................: " + pRequest.getPathInfo()                         + "\n" +
                " getPathTranslated.............: " + pRequest.getPathTranslated()                   + "\n" +
                " getProtocol...................: " + pRequest.getProtocol()                         + "\n" +
                " getQueryString................: " + pRequest.getQueryString()                      + "\n" +
                " getRemoteAddr.................: " + pRequest.getRemoteAddr()                       + "\n" +
                " getRemoteHost.................: " + pRequest.getRemoteHost()                       + "\n" +
                " getRemotePort.................: " + pRequest.getRemotePort()                       + "\n" +
                " getRemoteUser.................: " + pRequest.getRemoteUser()                       + "\n" +
                " getRequestURI.................: " + pRequest.getRequestURI()                       + "\n" +
                " getRequestURL.................: " + pRequest.getRequestURL()                       + "\n" +
                " getRequestedSessionId.........: " + pRequest.getRequestedSessionId()               + "\n" +
                " getScheme.....................: " + pRequest.getScheme()                           + "\n" +
                " getServerName.................: " + pRequest.getServerName()                       + "\n" +
                " getServerPort.................: " + pRequest.getServerPort()                       + "\n" +
                " getServletPath................: " + pRequest.getServletPath()                      + "\n" +
                " isRequestedSessionIdFromCookie: " + pRequest.isRequestedSessionIdFromCookie()      + "\n" +
                " isRequestedSessionIdFromURL...: " + pRequest.isRequestedSessionIdFromURL()         + "\n" +
//              " isRequestedSessionIdFromUrl...: " + pRequest.isRequestedSessionIdFromUrl()         + "\n" +
                " isRequestedSessionIdValid.....: " + pRequest.isRequestedSessionIdValid()           + "\n" +
                " isSecure......................: " + pRequest.isSecure()                            + "\n" +
                " class.........................: " + pRequest.getClass().getName()                  + "\n"
        );
        
        if ((pDetailLevel >= 1) || (pDetailLevel == -1)) {
            sb.append("\n\nHeaders:\n-----------\n\n");
            @SuppressWarnings("unchecked")
            Enumeration<String> mHeadList = pRequest.getHeaderNames();
            while (mHeadList.hasMoreElements()) {
                String mHead = mHeadList.nextElement();
                sb.append(" " + StringTool.minRString(mHead, 30, '.') + ": " + /*pRequest.getHeader(mHead) + " / " +*/ StringTool.list((Enumeration<?>)pRequest.getHeaders(mHead), ",", false) + "\n");
            }
        }
        
        if ((pDetailLevel >= 2) || (pDetailLevel == -1)) {
            sb.append("\n\nParameters:\n-----------\n\n");
            @SuppressWarnings("unchecked")
            Enumeration<String> mParmList = pRequest.getParameterNames();
            while (mParmList.hasMoreElements()) {
                String mParm = mParmList.nextElement();
                sb.append(" " + StringTool.minRString(mParm, 30, '.') + ": " + /*pRequest.getParameter(mParm) + " / " +*/ StringTool.list(pRequest.getParameterValues(mParm), ",", false) + "\n");
            }
        }
        
        if ((pDetailLevel >= 3) || (pDetailLevel == -1)) {
            sb.append("\n\nAttributes:\n-----------\n\n");
            @SuppressWarnings("unchecked")
            Enumeration<String> mAttrList = pRequest.getAttributeNames();
            while (mAttrList.hasMoreElements()) {
                String mAttr = mAttrList.nextElement();
                sb.append(" " + StringTool.minRString(mAttr, 30, '.') + ": " + pRequest.getAttribute(mAttr) + "\n");
            }
        }
        
        /*
         *  currently not traced - but may be interesting:
         * 
         *   javax.servlet.http.Cookie[] getCookies();
         *   java.security.Principal getUserPrincipal();
         *   javax.servlet.RequestDispatcher getRequestDispatcher(java.lang.String arg0);
         *   java.lang.String getRealPath(java.lang.String arg0);
         *   javax.servlet.http.HttpSession getSession(boolean arg0);
         *   boolean isUserInRole(java.lang.String arg0);
         *   
         */
        
        if ((pDetailLevel >= 4) || (pDetailLevel == -1)) {
            HttpSession mSess = pRequest.getSession();
            sb.append("\n\nSession:" + mSess.getId() + "\n\nSession Attributes:\n-------------------\n\n");
            @SuppressWarnings("unchecked")
            Enumeration<String> mSAttrList = mSess.getAttributeNames();
            while (mSAttrList.hasMoreElements()) {
                String mSAttr = mSAttrList.nextElement();
                sb.append(" " + StringTool.minRString(mSAttr, 30, '.') + ": " + mSess.getAttribute(mSAttr) + "\n");
            }
        }
        
        /* deprecated, same like Attributes
        sb.append("\n\nSession Values:\n---------------\n\n");
        @SuppressWarnings("unchecked")
        String[] mSValList = mSess.getValueNames();
        for (int i = 0; i < mSValList.length; i++) {
            String mSVal = mSValList[i];
            sb.append(" " + StringTool.minRString(mSVal, 30, '.') + ": " + mSess.getValue(mSVal) + "\n");
        }
         */
        
        /*
         * not yet traced:
         *   mSess.getServletContext();
         *
         */
        
        return sb.toString();
	}
	
	/**
	 * Dump a Request.
	 * @param pRequest
	 */
	public void dumpRequest(HttpServletRequest pRequest) {
	    if (Doc41Log.get().isDumpActive()) { 
            Doc41Log.get().dump(this, null, toString(pRequest));
	    }
	}

	/**
	 * Create a toString() dump of a HTTPResponse.
	 * @param pResponse
	 */
	public String toString(HttpServletResponse pResponse) {
	    if (pResponse == null) {
	        return "\nRESPONSE: n/a\n\n";
	    }

	    return "\n" +
	            "RESPONSE:\n" +
                "=========\n\n" +
                " getCharacterEncoding..: " + pResponse.getCharacterEncoding()        + "\n" +
                " getgetContentType.....: " + pResponse.getContentType()              + "\n" +
                " getLocale.............: " + pResponse.getLocale()                   + "\n" +
                " class.................: " + pResponse.getClass().getName()          + "\n";
	}

	
	/**
     * Dump a Response.
     * @param pResponse
     */
    public void dumpResponse(HttpServletResponse pResponse) {
        Doc41Log.get().dump(this, null, toString(pResponse));
    }
    
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	    User usr = determineUser(request);
		HttpSession session= request.getSession();
// error sim 1
//if (session != null) throw new Exception("TEST 1");
// WS-> Expected BEGIN_OBJECT but was STRING at line 1 column 1 
		try{
			Doc41Log.get().debug(this.getClass(), "current URI: ", request.getRequestURI());
            boolean mServletIsWebService = request.getServletPath().startsWith("/docservice/");
            boolean mTypeIsJson = request.getServletPath().endsWith(".json");
            Doc41Log.get().debug(this.getClass(), "current Servl: ", request.getServletPath() + (mServletIsWebService ? "(isWebService) " : "") + (mTypeIsJson ? "(isJson)" : "") );
            // FIXME: change kind of rejection depending on servlet:webservice and type:json
            dumpRequest(request);
            dumpResponse(response);
			
			// load persisted session data into HTTPSession:
			SessionDataDC dbSessionDC = retrieveSessionData(usr, session);
			request.setAttribute(DB_SESSION_DC_REQ_ATTR, dbSessionDC);

// error sim 2
// response.sendRedirect(request.getContextPath() +URI_LOGIN);
// WS -> Expected BEGIN_OBJECT but was STRING at line 1 column 1

// error sim 3
// response.sendRedirect(request.getContextPath() +URI_FORBIDDEN);
// WS -> Expected BEGIN_OBJECT but was STRING at line 1 column 1

			if (usr == null) {
                Doc41Log.get().debug(this, "<NULL>", "User empty/unknown, current URI: " + request.getRequestURI() + ", redirecting to: " + request.getContextPath() +URI_LOGIN);
                Doc41Log.get().debug(this, null, toString(request));
                Doc41Log.get().debug(this, null, toString(response));
			    response.sendRedirect(request.getContextPath() +URI_LOGIN);
				return false;
	
			} else {
				if(!usr.getActive() || !hasRolePermission(usr,handler,request)){
				    Doc41Log.get().warning(this, usr.getCwid(), "User inactive or has no permission, current URI: " + request.getRequestURI() + ", redirecting to: " + request.getContextPath() +URI_FORBIDDEN);
	                Doc41Log.get().debug(this, null, toString(request));
	                Doc41Log.get().debug(this, null, toString(response));
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
            Doc41Log.get().debug(this, null, toString(request));
            Doc41Log.get().debug(this, null, toString(response));
		    //no logging for NestingException to prevent duplicate logs as the NestingException constructor already logs
            throw e1;
		} catch (Exception e1) {
            Doc41Log.get().error(
		            this.getClass(),
		            (usr!=null)?usr.getCwid():"",e1);
            Doc41Log.get().debug(this, null, toString(request));
            Doc41Log.get().debug(this, null, toString(response));
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
	    String ctrl = null;
        String viewName = null;
	    try{
			// do the postRender intercepter job:
	        /*
			ctrl = getRealHandler(handler).getClass().getName();
			StringTokenizer st = new StringTokenizer(ctrl, ".");
			while (st.hasMoreTokens()){
			    ctrl = st.nextToken();
			}
			*/
            ctrl = getRealHandler(handler).getClass().getSimpleName();

            if(modelAndView!=null){
                viewName = modelAndView.getViewName();
            }
			request.getSession().setAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_CTRL, ctrl.toLowerCase());
			if(viewName!=null){
				request.getSession().setAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_VIEW, viewName);
			}
		} catch (java.lang.IllegalStateException ei) { // how to prevent autotrace by spring?
            Exception ein = new Doc41TechnicalException(this, "Doc41HandlerInterceptor.postHandle failed, IllegalState! viewname: " + viewName + ", ctrl: " + ctrl, ei, true, true);
            /*
            try {
                User usr = determineUser(request);
                Doc41Log.get().error(this, usr.getCwid(), ei.getClass().getSimpleName() + ": " + ei.getMessage());
            } catch (Exception e2) {
                new Doc41TechnicalException(this, "exception handling: lookUp User failed!", e2, true, true);
            }
            */
            throw ein;
		} catch (Exception e1) { // this should autotrace by spring
		    Exception e1n = new Doc41TechnicalException(this, "Doc41HandlerInterceptor.postHandle failed, other Ex! viewname: " + viewName + ", ctrl: " + ctrl, e1);
		    /*
		    try {
		        User usr = determineUser(request);
                Doc41Log.get().error(this, usr.getCwid(), e1.getClass().getSimpleName() + ": " + e1.getMessage());
		    } catch (Exception e2) {
	            new Doc41TechnicalException(this, "exception handling: lookUp User failed!", e2);
		    }
		    */
		    throw e1n;
		}

// error sim 5
// throw new Exception("TEST 5");
// WS -> success?!

	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// persist HTTPSession into session data in DB:
		User usr = UserInSession.get();
		if(usr!=null){
            Doc41Log.get().logWebMetrix(request, getRealHandler(handler), request.getRequestURI());
            try {
                HttpSession session= request.getSession();
                SessionDataDC dbSessionDC = (SessionDataDC) request.getAttribute(DB_SESSION_DC_REQ_ATTR);
                persistSessionData(usr,session,dbSessionDC);
            } catch (java.lang.IllegalStateException ei) { // how to prevent autotrace by spring?
                NestingException.handlingCompleted("Doc41HandlerInterceptor.afterCompletion failed, IllegalStateException... " +getRealHandler(handler).getClass().getSimpleName() + ", " + request.getRequestURI(), ei, null, true, true);
/* use springs autotracing (can not disable, otherwise would trace twice)
            } catch (Exception e1) { // this should autotrace by spring
                throw new Doc41TechnicalException(this, "Doc41HandlerInterceptor.postHandle failed, other Ex! viewname: " + viewName + ", ctrl: " + ctrl, e1);
*/
            }

		}

// error sim 4
// throw new Exception("TEST 4");
// WS -> BDSObj, aber NPE (container null: errors) -> succ?!
	
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
		Doc41Log.get().debug(this, null, "ReqSessionUser=" + ((user== null) ? "null" : user.getCwid())+"/websealUser="+webSealCwid+"/docServiceUser="+ ((docServiceUser== null) ? "null" : docServiceUser.getCwid()));
		
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
		    Doc41Log.get().debug(this, null, "SELECT ReqSessionUser=" + ((user== null) ? "null" : user.getCwid()));
		    cwid = user.getCwid();
		} else if(docServiceUser!=null){
		    if(user==null || !StringTool.equals(user.getCwid(), docServiceUser.getCwid())){
	            Doc41Log.get().debug(this, null, "SELECT docServiceUser=" + ((docServiceUser== null) ? "null" : docServiceUser.getCwid()));
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
                Doc41Log.get().debug(this, tmpCwid, "DocService user request from: ["+tmpHost+"] "+tmpAddr+" for uri: "+request.getRequestURI()+" / url: "+request.getRequestURL());
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
                        boolean mActivateRealCwids = BooleanTool.getBoolean(subConfig.getProperty("ActivateRealCwids"), false);
                        User tmpUser = mActivateRealCwids ? userManagementUC.findUser(tmpCwid) : null;
                        Doc41Log.get().debug(this, null, "DocService for cwid: " + tmpCwid + ", realCwids " + (mActivateRealCwids ? "enabled" : "disabled") + ", located: " + ((tmpUser == null) ? "n/a" : tmpUser.getCwid()) );
                        if (tmpUser == null) {
                            tmpUser = userManagementUC.findUser(CWID_DOC_SERVICE_CARR);
                            if (tmpUser != null) { // prepared to be able to deactivate user without causing NPE
                                tmpUser.setSkipCustomerCheck(true);
                                tmpUser.setSkipVendorCheck(true);
                                tmpUser.setSkipCountryCheck(true);
                            }
                        }
                        return tmpUser;
                    } else {
                        Doc41Log.get().warning(this, tmpCwid, "Bad password: " + tmpPwd );
                    }
                } else {
                    Doc41Log.get().warning(this, tmpCwid, "IP not allowed: " + tmpAddr );
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
			dbSessionDC = SessionDataDC.newInstanceOfSessionDataDC();
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
