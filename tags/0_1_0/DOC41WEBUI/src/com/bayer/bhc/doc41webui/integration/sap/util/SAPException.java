/**
 * 
 * $Id: SAPException.java,v 1.3 2009/09/07 18:21:38 imwif Exp $
 * 
 */

package com.bayer.bhc.doc41webui.integration.sap.util;

import com.bayer.ecim.foundation.basic.NestingException;
import com.bayer.ecim.foundation.basic.NestingExceptionsParameter;

/**
 * @author IMKLJ
 *
 */
public class SAPException extends NestingException {

	private static final long serialVersionUID = -3500519154782753621L;

	/**
	 * Constructor for SAPException.
	 * @param arg0
	 * @param arg1
	 */
	public SAPException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor for SAPException.
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 */
	public SAPException(
		String arg0,
		Throwable arg1,
		String arg2,
		String arg3,
		NestingExceptionsParameter[] arg4) {
		super(arg0, arg1, arg2, arg3, arg4);
	}

	/**
	 * Constructor for SAPException.
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 * @param arg5
	 */
	public SAPException(
		String arg0,
		Throwable arg1,
		String arg2,
		String arg3,
		NestingExceptionsParameter[] arg4,
		boolean arg5) {
		super(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	/**
	 * Constructor for SAPException.
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 * @param arg5
	 */
	public SAPException(String pTitle, Throwable pException, String pMessageKey, String pUserId, 
						NestingExceptionsParameter[] pMsgParameters, boolean pAutoTrace, boolean pNoStacktrace) {
		super(pTitle, pException, pMessageKey, pUserId, pMsgParameters, pAutoTrace, pNoStacktrace);
	}
}

