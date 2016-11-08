/**
 * 
 */
package com.bayer.bhc.doc41webui.common.exception;

import java.util.HashSet;

import com.bayer.ecim.foundation.basic.ArrayTool;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.StringTool;

/**
 * @author imwif
 *
 */
public class Doc41ClientAbortException extends Doc41ExceptionBase {

    /**
     * Inspects an exception for a portal specific, configurable list ofexceptions known to be ClientAbortExceptions.
     * @param cause
     * @param message to be used, if detecting a ClientAbortException.
     * @param doAutoLog if true, auto log to log (without trace). if false, do not log (logging done later for more informations about context).
     * @throws Doc41ClientAbortException (independent of portal technology), if the cause is detected to be one of the configured known ClientAbortExceptions.
     */
    public static void inspectForClientAbortException(Throwable cause, String message, boolean doAutoLog) throws Doc41ClientAbortException {
        String[] mExceptions = StringTool.splitString(ConfigMap.get().getSubCfg("Exceptions").getProperty("ClientAbortExceptions",""), ',');
        HashSet<String> mExMap = new HashSet<String>(ArrayTool.toList(mExceptions));
        if ( mExMap.contains((cause == null) ? " " : cause.getClass().getName()) || mExMap.contains((cause == null) ? " " : cause.getClass().getSimpleName()) ) {
            throw new Doc41ClientAbortException(message, cause, doAutoLog, true);
        }
    }
    
    
    /**
     * @param pTitle
     * @param pInternalException
     * @param pAutoTrace
     * @param pNoStacktrace
     */
    private Doc41ClientAbortException(String pTitle,
            Throwable pInternalException, boolean pAutoTrace,
            boolean pNoStacktrace) {
        super(pTitle, pInternalException, pAutoTrace, pNoStacktrace);
        // TODO Auto-generated constructor stub
    }

}
