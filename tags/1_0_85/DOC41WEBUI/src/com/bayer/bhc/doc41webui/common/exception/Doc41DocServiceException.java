package com.bayer.bhc.doc41webui.common.exception;

import com.bayer.bhc.doc41webui.web.Doc41ControllerAdvice;

/**
 * will not be caught by {@link Doc41ControllerAdvice}
 * @author EVAYD
 *
 */
public class Doc41DocServiceException extends Doc41ExceptionBase {

    public Doc41DocServiceException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public Doc41DocServiceException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

}
