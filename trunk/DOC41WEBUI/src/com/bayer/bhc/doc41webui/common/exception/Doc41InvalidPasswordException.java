/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.exception;


/**
 * BoeaInvalidPasswordException.
 * 
 * @author ezzqc
 */
public class Doc41InvalidPasswordException extends Doc41BusinessException {
	private static final long serialVersionUID = -1330331576038033403L;

	/**
     * Constructor with message and cause.
     * @param message
     * @param cause
     */
    public Doc41InvalidPasswordException(final String message, final Throwable cause) {
        super(message, cause);
    }    
}
