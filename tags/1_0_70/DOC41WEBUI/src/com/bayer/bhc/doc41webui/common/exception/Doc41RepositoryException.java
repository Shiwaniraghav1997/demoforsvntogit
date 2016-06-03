/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.exception;

/**
 * DOC41 Repository exception. Should re-throw exception from layer below.
 * 
 * @author ezzqc
 */
public class Doc41RepositoryException extends Doc41ExceptionBase {

    private static final long serialVersionUID = -9017947246674434308L;

    public Doc41RepositoryException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public Doc41RepositoryException(final Throwable cause) {
        super(cause.getMessage(), cause);
    }

}
