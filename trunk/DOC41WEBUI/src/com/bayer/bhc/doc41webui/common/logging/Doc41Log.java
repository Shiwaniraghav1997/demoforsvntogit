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
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.NestingException;
import com.bayer.ecim.foundation.basic.Singleton;

/**
 * Foundation specific logger class for CargoCockpit.
 * 
 * @author ezzqb
 * @id $Id: Doc41LoggerImpl.java,v 1.2 2012/02/22 14:16:09 ezzqc Exp $
 */
public class Doc41Log extends Singleton {


    public static final String ID = "Doc41Log";

    
    /**
     * Initializes the singleton
     * @throws InitException
     */
    public Doc41Log( String pID ) throws InitException {
        super(pID, true); // register and lock for init by this thread (with 30 sec safty lock timeout to protect from deadlock)
        try {
            /** nothing to init... */
            initSucceeded( Doc41Log.class ); // success, release lock
        } catch (Exception mEx) {
            initFailed( new InitException( "Failed to initialize " + pID + "!", mEx) ); // fail, trace & release lock for retry
        } catch (Error mErr) {
            initFailed( mErr ); // ERROR, release lock for retry
        }
    }
    
    /**
     * Gets a singletion instance of the Doc41Log.
     * @return Doc41Log
     * @throws InitException
     */
    public static Doc41Log get() throws InitException {
        Doc41Log mSing = (Doc41Log) getSingleton(ID);
        return (mSing == null) ? new Doc41Log(ID) : mSing;
    }
    

    /** the error channel */
    private static final int ERROR = Dbg.ERROR;

    /** the info channel */
    private static final int INFO = Dbg.INFO;

    /** the WARNING channel */
    private static final int WARNING = Dbg.WARNING;

    /** the logging channel */
    private static final int LOGGING = Dbg.LOGGING;
    
    /**
     * Looks, if debug channel ist active - this is NOT static/final!!!
     */
    public boolean isDebugActive() {
        return Dbg.get().isLogicalChannelActive(Dbg.INFO);
    }
    
    /**
     * Logs debug message.
     * @param clazz   The Log Class
     * @param user    The User
     * @param msg     The Debug message
     */
    public void debug(Object clazz, String user, Object msg) {
        Dbg.get().println(INFO, clazz, user, msg);
    }

    /**
     * Warning messages.
     * @param clazz   The Log Class
     * @param user    The User
     * @param msg     The error message
     */
    public void warning(Object clazz, String user, Object msg) {
        Dbg.get().println(WARNING, clazz, user, msg);
    }

    /**
     * Logs Error Message.
     * @param clazz   The Log Class
     * @param user    The User
     * @param msg     The error message
     */
    public void error(Object clazz, String user, Object msg) {
    	//to log stacktrace
    	if(msg instanceof Throwable && !(msg instanceof NestingException)){
    		Throwable ex = (Throwable) msg;
    		new NestingException(ex.getMessage(), ex);
    	}
        Dbg.get().println(ERROR, clazz, user, msg);
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
	public void logWebMetrix(Object clazz, Doc41LogEntry logEntry, String usr) {
		Dbg.get().println(LOGGING, clazz, usr, logEntry);
	}
}
