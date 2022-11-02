/**
 * 
 */
package com.bayer.bhc.doc41webui.common.exception;

import com.bayer.ecim.foundation.basic.NestingExceptionsParameter;

/**
 * @author imwif
 *
 */
public class Doc41RepositoryVendorNotFoundException
        extends Doc41RepositoryException {

    /**
     * @param pTitle
     * @param pInternalException
     * @param pMsgParameters
     */
    public Doc41RepositoryVendorNotFoundException(String pVendorNumber) {
        super("vendor "+pVendorNumber+" not found", null, new NestingExceptionsParameter[] {new NestingExceptionsParameter(0, pVendorNumber)}, false, true);
    }

}
