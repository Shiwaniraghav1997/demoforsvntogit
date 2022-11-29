/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.exception;


/**
 * Thrown if trying to modify a non-actual domain object.
 * 
 * @author ezzqc
 */
public class Doc41OptimisticLockingException extends Doc41RepositoryException {
	private static final long serialVersionUID = -1163667205542509766L;

	public Doc41OptimisticLockingException() {
        super("Doc41OptimisticLockingException", null);
    }
    
    public Doc41OptimisticLockingException(final String message) {
        super(message, null);
    }
    
    public Doc41OptimisticLockingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
