package com.bayer.bhc.doc41webui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.bayer.bhc.doc41webui.common.exception.Doc41AccessDeniedException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.exception.Doc41OptimisticLockingException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.DateRenderer;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;


/**
 * Annotated members of this class are used for all Controllers.
 * @author evayd
 *
 */
@ControllerAdvice
public class Doc41ControllerAdvice {
	
	public static final String DATE_PATTERN = "datePattern";
	public static final String DATE_MASK_PATTERN = "dateMaskPattern";
	public final static String DOC41_EXCEPTION = "doc41exception";
	
	@ModelAttribute(DATE_PATTERN)
	public String addDatePattern(){
		return DateRenderer.getDatePattern(LocaleInSession.get());
	}
	
	@ModelAttribute(DATE_MASK_PATTERN)
	public String addDateMaskPattern(){
		return generateMaskPattern(DateRenderer.getDatePattern(LocaleInSession.get()));
	}
	
	private String generateMaskPattern(String datePattern) {
		if(datePattern==null){
			return null;
		}
		return datePattern.replace('y', '9').replace('M', '9').replace('d', '9');
	}
	
	@ExceptionHandler(Exception.class)
	public String processHandlerException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
		if (ex instanceof MaxUploadSizeExceededException){
			ex = new Doc41ExceptionBase("File is to big for upload!",ex);
		}

		if (!(ex instanceof Doc41ExceptionBase)) {
			ex.printStackTrace();
			ex = new Doc41TechnicalException(this.getClass(), "fatal error: "+ex.getStackTrace()[0], ex);
		}

		Exception relevantException = ex;
		
		// search for Doc41OptimisticLockingException or Doc41AccessDeniedException:
		while (ex.getCause() instanceof Exception) {
			ex = (Exception)ex.getCause();

			if (ex instanceof Doc41OptimisticLockingException) {
				relevantException = ex;
			}
			if (ex instanceof Doc41AccessDeniedException) {
				relevantException = ex;
			}
		}
		request.setAttribute(DOC41_EXCEPTION, relevantException);
		Doc41Log.get().error(getClass(), UserInSession.getCwid(), relevantException);
		return "exception";
	}
	
}
