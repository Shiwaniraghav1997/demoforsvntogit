/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.exception;


/**
 * Access denied basic technical exception.
 * 
 * @author ezzqc
 */
public class Doc41AccessDeniedException extends Doc41TechnicalException {

	private static final long serialVersionUID = 2767779773830032874L;

	/**
     * Default constructor.
     * @param involvedClass
     * @param message
     */
    public Doc41AccessDeniedException(final Class<?> involvedClass) {
        super(involvedClass, "Doc41AccessDeniedException");
    }

}
