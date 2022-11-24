/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.exception;


/**
 * DOC41 Service exception.
 * This exception offers the possibility to add a list of messages.
 * These messages will not been translated but displayed as a list while presenting 
 * at the client
 * 
 * @author ezzqc
 */
public class Doc41ServiceException extends Doc41ExceptionBase {
    
    private static final long serialVersionUID = -1706363511406072822L;

    /**
     * Constructor with message.
     * @param message exception message.
     */
    public Doc41ServiceException(final String message) {
        super(message);
    }
    
    /**
     * Constructor with message.
     * @param cause Throwable.
     * @param message exception message.
     */
    public Doc41ServiceException(final String message, final Throwable cause) {
        super(message,cause);
    }
    
 
}
