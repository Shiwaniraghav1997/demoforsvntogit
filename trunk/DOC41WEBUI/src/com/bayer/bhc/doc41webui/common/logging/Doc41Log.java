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

    /** the WARNING channel */
    private static final int WARNING = Dbg.WARNING;

    /** the logging channel */
    private static final int LOGGING = Dbg.LOGGING;

    /** a flag indicating whether INFO logging is active */
    private static boolean INFO_ACTIVE = Dbg.get().isLogicalChannelActive(INFO);

    /** a flag indicating whether WARNING logging is active */
    private static final boolean WARNING_ACTIVE = Dbg.get().isLogicalChannelActive(WARNING);

    /** a flag indicating whether ERROR logging is active */
    private static final boolean ERROR_ACTIVE = Dbg.get().isLogicalChannelActive(ERROR);

    /** a flag indicating whether Webmetrix logging is active */
    private static final boolean LOGGING_ACTIVE = Dbg.get().isLogicalChannelActive(LOGGING);
   
    /**
     * Logs debug message.
     * @param clazz   The Log Class
     * @param user    The User
     * @param msg     The Debug message
     */
    public void debug(Class<?> clazz, String user, Object msg) {
    	if (!INFO_ACTIVE) {
    		INFO_ACTIVE = Dbg.get().isLogicalChannelActive(INFO);
    	}
    	if (INFO_ACTIVE) {
            Dbg.get().println(INFO, clazz, user, msg);
        }
    }

    /**
     * Warning messages.
     * @param clazz   The Log Class
     * @param user    The User
     * @param msg     The error message
     */
    public void warning(Class<?> clazz, String user, Object msg) {
        if (WARNING_ACTIVE) {
            Dbg.get().println(INFO, clazz, user, msg);
        }
    }

    /**
     * Logs Error Message.
     * @param clazz   The Log Class
     * @param user    The User
     * @param msg     The error message
     */
    public void error(Class<?> clazz, String user, Object msg) {
    	//to log stacktrace
    	if(msg instanceof Throwable && !(msg instanceof NestingException)){
    		Throwable ex = (Throwable) msg;
    		new NestingException(ex.getMessage(), ex);
    	}
        if (ERROR_ACTIVE) {
            Dbg.get().println(ERROR, clazz, user, msg);
        }
    }

    /**
     * Controllers specific method to log webmetrix data in Webmetrix table.
     * @param pRequest          The PortletletRequest
     * @param handler         	The PortletHandlerObject
     * @param modelAndView      The ModelAndView
     */
	public void logWebMetrix(HttpServletRequest pRequest, Object handler, ModelAndView modelAndView) {
		if (LOGGING_ACTIVE) {
			String usr = UserInSession.getCwid();
			String jspName =modelAndView.getViewName();
			String logClass = handler.getClass().getName();
			String action = (String)pRequest.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_CTRL);
					
			Doc41LogEntry pObj = new Doc41LogEntry(usr, usr, null, action, jspName, null, null, null, null, null, null, null, null);
			Dbg.get().println(LOGGING, logClass, pRequest.getRemoteUser(),pObj);
		}
	}


    /**
     * Controllers specific method to log webmetrix data in Webmetrix table.
     * @param pRequest          The PortletletRequest
     * @param handler         	The PortletHandlerObject
     * @param URI      The requested URI
     */
	public void logWebMetrix(HttpServletRequest pRequest, Object handler, String anURI) {
		if (LOGGING_ACTIVE) {
			String usr = UserInSession.getCwid();
			String logClass = handler.getClass().getName();
			String action = (String)pRequest.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_CTRL);
					
			Doc41LogEntry pObj = new Doc41LogEntry(usr, usr, null, action, anURI, null, null, null, null, null, null, null, null);
			Dbg.get().println(LOGGING, logClass, pRequest.getRemoteUser(),pObj);
		}
	}

	/**
     * Common method to log webmetrix data
     * @param pRequest          The PortletletRequest
     * @param handler         	The PortletHandlerObject
     */
	public void logWebMetrix(HttpServletRequest pRequest, Object handler) {
		if (LOGGING_ACTIVE) {
			String jspName = null;
			String usr = UserInSession.getCwid();
			String logClass = handler.getClass().getName();
			String action = (String)pRequest.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_CTRL);
			jspName = (String)pRequest.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_VIEW);
			Doc41LogEntry pObj = new Doc41LogEntry(usr, usr, null, action, jspName, null, null, null, null, null, null, null, null);
			Dbg.get().println(LOGGING, logClass, pRequest.getRemoteUser(),pObj);
		}
	}
	
	
	/**
	 * Common method to log webmetrix data
	 * @param handler
	 * @param logEntry
	 * @param usr
	 */
	public void logWebMetrix(Class<?> clazz, Doc41LogEntry logEntry, String usr) {
		if (LOGGING_ACTIVE) {				
			Dbg.get().println(LOGGING, clazz, usr, logEntry);
		}
	}
}
