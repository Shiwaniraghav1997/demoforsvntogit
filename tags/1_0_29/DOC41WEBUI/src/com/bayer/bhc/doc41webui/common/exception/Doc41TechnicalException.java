/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.exception;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;


/**
 * DOC41 basic technical exception.
 * 
 * @author ezzqc
 */
public class Doc41TechnicalException extends Doc41ExceptionBase {

    private static final long serialVersionUID = -7060440654291109821L;

    public Doc41TechnicalException(final Class<?> involvedClass, final String message) {
        super(message);
        if(involvedClass != null){
            if (UserInSession.get() == null) {
            	Doc41Log.get().error(involvedClass, null, message);
            } else {
            	Doc41Log.get().error(involvedClass, UserInSession.getCwid(), message);
            }
        }
    }
    
    /**
     * Enforce an error log when throwing a new Doc41TechnicalException.
     * @param involvedClass
     * @param message
     * @param cause
     * 
     */
    public Doc41TechnicalException(final Class<?> involvedClass, final String message, final Throwable cause) {
        super(message, cause);
        if(involvedClass != null){
            if (UserInSession.get() == null) {
            	Doc41Log.get().error(involvedClass, null, cause);
            } else {
            	Doc41Log.get().error(involvedClass, UserInSession.getCwid(), cause);
            }
        }
    }
    
}
