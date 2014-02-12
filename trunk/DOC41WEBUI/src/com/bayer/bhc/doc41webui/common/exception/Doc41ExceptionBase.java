/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.exception;

import com.bayer.ecim.foundation.basic.NestingException;

/**
 * Doc41ExceptionBase. Basic Doc41 exception.
 * 
 * @author ezzqc
 */
public class Doc41ExceptionBase extends NestingException {	
	private static final long serialVersionUID = 1015878206364831295L;

	/**
     * Constructor with message and cause.
     */
    public Doc41ExceptionBase(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with message.
     */
    public Doc41ExceptionBase(final String message) {
        super(message,null);
    }

}
