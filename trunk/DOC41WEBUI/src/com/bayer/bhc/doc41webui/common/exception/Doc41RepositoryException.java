/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.exception;

import com.bayer.ecim.foundation.basic.NestingExceptionsParameter;

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
    
    /**
     * Constructor with message, cause, exception parameters and trace control.
     * @param pTitle
     * @param pInternalException
     * @param pMsgParameters
     * @param pAutoTrace
     * @param pNoStacktrace
     */
    public Doc41RepositoryException(String pTitle, Throwable pInternalException, NestingExceptionsParameter[] pMsgParameters, boolean pAutoTrace, boolean pNoStacktrace) {
        super(pTitle, pInternalException, pMsgParameters, pAutoTrace, pNoStacktrace);
    }
    
}
