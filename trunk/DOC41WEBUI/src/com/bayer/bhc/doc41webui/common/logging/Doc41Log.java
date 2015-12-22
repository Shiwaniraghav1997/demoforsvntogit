/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.logging;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.Doc41SessionKeys;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.ecim.foundation.basic.Dbg;
import com.bayer.ecim.foundation.basic.NestingException;
import com.bayer.ecim.foundation.basic.NestingExceptions;

/**
 * Foundation specific logger class for CargoCockpit.
 * 
 * @author ezzqb
 * @id $Id: Doc41LoggerImpl.java,v 1.2 2012/02/22 14:16:09 ezzqc Exp $
 */
public class Doc41Log {


    private static Doc41Log instance;
        
    public static synchronized Doc41Log get() {
        if (instance == null) {
            instance = new Doc41Log();
        }
        return instance;
    }
    

    /** the error channel */
    private static final int ERROR = Dbg.ERROR;

    /** the info channel */
    private static final int INFO = Dbg.INFO;

    /** the dump channel */
    private static final int BIGINFO = Dbg.BIGINFO;

    /** the WARNING channel */
    private static final int WARNING = Dbg.WARNING;

    /** the logging channel */
    private static final int LOGGING = Dbg.LOGGING;
    
    
    /**
     * Looks, if debug channel is active - this is NOT static/final!!!
     */
    public boolean isDebugActive() {
        return Dbg.get().isLogicalChannelActive(INFO);
    }
    
    /**
     * Logs debug message.
     * @param clazz   The Log Class
     * @param user    The User
     * @param msg     The Debug message
     */
    public void debug(Object clazz, String user, Object msg) {
        Dbg.get().println(INFO, clazz, (user == null) ? UserInSession.getCwid() : user, msg);
    }

    /**
     * Looks, if debug channel is active - this is NOT static/final!!!
     */
    public boolean isDumpActive() {
        return Dbg.get().isLogicalChannelActive(BIGINFO);
    }
    
    /**
     * Logs debug(dump) message (extra channel for heavy debug, dumps).
     * @param clazz   The Log Class
     * @param user    The User
     * @param msg     The Debug message
     */
    public void dump(Object clazz, String user, Object msg) {
        Dbg.get().println(BIGINFO, clazz, (user == null) ? UserInSession.getCwid() : user, msg);
    }

    /**
     * Warning messages.
     * @param clazz   The Log Class
     * @param user    The User
     * @param msg     The error message
     */
    public void warning(Object clazz, String user, Object msg) {
        Dbg.get().println(WARNING, clazz, (user == null) ? UserInSession.getCwid() : user, msg);
    }

    /**
     * Logs Error Message.
     * @param clazz   The Log Class
     * @param user    The User
     * @param msg     The error message
     */
    public void error(Object clazz, String user, Object msg) {
    	//to log stacktrace
    	if(msg instanceof Throwable && !(msg instanceof NestingExceptions)){
    		Throwable ex = (Throwable) msg;
    		new NestingException(ex.getMessage(), ex);
    	}
        Dbg.get().println(ERROR, clazz, (user == null) ? UserInSession.getCwid() : user, msg);
    }

    /**
     * Controllers specific method to log webmetrix data in Webmetrix table.
     * @param pRequest          The PortletletRequest
     * @param handler         	The PortletHandlerObject
     * @param modelAndView      The ModelAndView
     */
	public void logWebMetrix(HttpServletRequest pRequest, Object handler, ModelAndView modelAndView) {
	    String usr = UserInSession.getCwid();
		String jspName =modelAndView.getViewName();
		String action = (String)pRequest.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_CTRL);
					
		Doc41LogEntry pObj = new Doc41LogEntry(usr, usr, null, action, jspName, null, null, null, null, null, null, null, null);
		Dbg.get().println(LOGGING, handler, pRequest.getRemoteUser(),pObj);
	}

	/**
	 * Print (debug) a message one time, but then no more (until next logfile change - prevent spamming)
	 * @param clazz
	 * @param user
	 * @param mMessage
	 */
	public void debugMessageOnce(Object clazz, String user, String mMessage ) {
	    user = (user == null) ? UserInSession.getCwid() : user;
	    Dbg.get().dbgLogMessageOnce(INFO, 0, clazz, user, user, "", mMessage);
	}
	
    /**
     * Print (warn) a message one time, and on further occurence only to dump channel(until next logfile change - prevent spamming)
     * @param clazz
     * @param user
     * @param mMessage
     */
    public void warnMessageOnce(Object clazz, String user, String mMessage ) {
        user = (user == null) ? UserInSession.getCwid() : user;
        Dbg.get().dbgLogMessageOnce(WARNING, BIGINFO, clazz, user, user, "", mMessage);
    }
    

    /**
     * Controllers specific method to log webmetrix data in Webmetrix table.
     * @param pRequest          The PortletletRequest
     * @param handler         	The PortletHandlerObject
     * @param URI      The requested URI
     */
	public void logWebMetrix(HttpServletRequest pRequest, Object handler, String anURI) {
		String usr = UserInSession.getCwid();
		String action = (String)pRequest.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_CTRL);
					
		Doc41LogEntry pObj = new Doc41LogEntry(usr, usr, null, action, anURI, null, null, null, null, null, null, null, null);
		Dbg.get().println(LOGGING, handler, pRequest.getRemoteUser(),pObj);
	}

	/**
     * Common method to log webmetrix data
     * @param pRequest          The PortletletRequest
     * @param handler         	The PortletHandlerObject
     */
	public void logWebMetrix(HttpServletRequest pRequest, Object handler) {
		String jspName = null;
		String usr = UserInSession.getCwid();
		String action = (String)pRequest.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_CTRL);
		jspName = (String)pRequest.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_VIEW);
		Doc41LogEntry pObj = new Doc41LogEntry(usr, usr, null, action, jspName, null, null, null, null, null, null, null, null);
		Dbg.get().println(LOGGING, handler, pRequest.getRemoteUser(),pObj);
	}
	
	
	/**
	 * Common method to log webmetrix data
	 * @param handler
	 * @param logEntry
	 * @param usr
	 */
	public void logWebMetrix(Object clazz, Doc41LogEntry logEntry, String user) {
		Dbg.get().println(LOGGING, clazz, (user == null) ? UserInSession.getCwid() : user, logEntry);
	}
}
