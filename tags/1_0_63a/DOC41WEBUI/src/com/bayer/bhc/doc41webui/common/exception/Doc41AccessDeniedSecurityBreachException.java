/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.exception;

import com.bayer.ecim.foundation.basic.NestingExceptionsSecurityBreachMarker;

/**
 * Access denied basic technical exception.
 * 
 * @author ezzqc
 */
public class Doc41AccessDeniedSecurityBreachException extends Doc41AccessDeniedException implements NestingExceptionsSecurityBreachMarker {

	private static final long serialVersionUID = 2767779773830032875L;

	/**
     * Default constructor.
     * @param involvedClass
     * @param message
     */
    public Doc41AccessDeniedSecurityBreachException(final Class<?> involvedClass) {
        super(involvedClass);
    }

}
