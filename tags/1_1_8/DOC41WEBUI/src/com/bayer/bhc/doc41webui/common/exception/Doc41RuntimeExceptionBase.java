/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.exception;

import com.bayer.ecim.foundation.basic.NestingRuntimeException;

/**
 * Doc41ExceptionBase. Basic Doc41 exception.
 * 
 * @author ezzqc
 */
public class Doc41RuntimeExceptionBase extends NestingRuntimeException {	
	private static final long serialVersionUID = 1015878206364831295L;

	/**
     * Constructor with message and cause.
     */
    public Doc41RuntimeExceptionBase(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with message.
     */
    public Doc41RuntimeExceptionBase(final String message) {
        super(message,null);
    }

}
