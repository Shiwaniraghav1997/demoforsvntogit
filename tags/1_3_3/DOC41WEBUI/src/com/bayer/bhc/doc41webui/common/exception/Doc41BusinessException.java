/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.exception;


/**
 * DOC41 Business exception.
 * 
 * @author ezzqc
 */
public class Doc41BusinessException extends Doc41ExceptionBase {

    private static final long serialVersionUID = -685616229857693364L;

    /**
     * Construtor with message.
     * @param message exception message.
     */
    public Doc41BusinessException(final String message) {
        super(message);
    }

    public Doc41BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
}
