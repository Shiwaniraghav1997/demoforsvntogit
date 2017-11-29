package com.bayer.bhc.doc41webui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.bayer.bhc.doc41webui.common.exception.Doc41AccessDeniedException;
import com.bayer.bhc.doc41webui.common.exception.Doc41DocServiceException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.exception.Doc41OptimisticLockingException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.DateRenderer;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.TimeRenderer;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.NestingException;
import com.bayer.ecim.foundation.basic.NestingExceptions;


/**
 * Annotated members of this class are used for all Controllers.
 * @author evayd
 *
 */
@ControllerAdvice
public class Doc41ControllerAdvice {
	
	public static final String DATE_PATTERN = "datePattern";
	public static final String DATE_MASK_PATTERN = "dateMaskPattern";
	public static final String DATE_MOMENT_PATTERN = "dateMomentPattern";
	
	public static final String DATE_TIME_PATTERN = "dateTimePattern";
	public static final String DATE_TIME_MOMENT_PATTERN = "dateTimeMomentPattern";
	
	public static final String STAGE = "stage";
	
	public final static String DOC41_EXCEPTION = "doc41exception";
	
	@ModelAttribute
	public void addDateTimePatterns(Model model){
		String datePattern = DateRenderer.getDatePattern(LocaleInSession.get());
		model.addAttribute(DATE_PATTERN,datePattern);
		
		String dateMaskPattern = generateMaskPattern(datePattern);
		model.addAttribute(DATE_MASK_PATTERN,dateMaskPattern);
		
		String dateMomentPattern = generateMomentPattern(datePattern);
		model.addAttribute(DATE_MOMENT_PATTERN,dateMomentPattern);
		
		String timePattern = TimeRenderer.GERMAN_TIME_PATTERN;
		String dateTimePattern = datePattern +" "+ timePattern;
		model.addAttribute(DATE_TIME_PATTERN,dateTimePattern);
		
		String dateTimeMomentPattern = generateMomentPattern(dateTimePattern);
		model.addAttribute(DATE_TIME_MOMENT_PATTERN,dateTimeMomentPattern);
	}
	
	@ModelAttribute(STAGE)
	public String addStage(){
		return (String) ConfigMap.get().getSubConfig("common.all").get("stage");
	}
	
	private String generateMaskPattern(String datePattern) {
		if(datePattern==null){
			return null;
		}
		return datePattern.replace('y', '9').replace('M', '9').replace('d', '9');
	}
	
	private String generateMomentPattern(String datePattern) {
		if(datePattern==null){
			return null;
		}
		return datePattern.replace('d', 'D').replace('y', 'Y').replace("'T'", "T");
	}
	
	@ExceptionHandler(Exception.class)
	public String processHandlerException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
	    if(ex instanceof Doc41DocServiceException){
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getLocalizedMessage());
            Doc41Log.get().debug(ex, null, ex.getMessage());
	        return "exception";
	    }
		if (ex instanceof MaxUploadSizeExceededException){
//            Doc41Log.get().debug(ex, null, ex.getMessage());
			ex = new Doc41ExceptionBase("File is to big for upload!",ex, true, true); // create no trace, just report like warning...
		}

		if (ex instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
            ex = new Doc41TechnicalException(this.getClass(), "HttpRequestMethodNotSupportedException, GET not supported, possibly illegal bookmark usage...", ex, true, true);
		}
		if (!(ex instanceof Doc41ExceptionBase)) {
		    Throwable[] internalsTh = NestingException.getEncapsulatedThrowableList(ex);
		    if ((internalsTh.length > 0) && (internalsTh[internalsTh.length-1] instanceof org.apache.catalina.connector.ClientAbortException)) {
                ex = new Doc41TechnicalException(this.getClass(), "client/download abort", ex, true, true);
		    } else {
		        ex = new Doc41TechnicalException(this.getClass(), "fatal error", ex);
		    }
		}

		Throwable relevantException = (ex instanceof NestingExceptions) ? ((NestingExceptions)ex).getBasicException() : ex;

		/** not expect to work, because the mentioned exceptions are autotracing... Better make by default not tracing Stacktrace...
		Exception relevantException = ex;
		
		// search for Doc41OptimisticLockingException or Doc41AccessDeniedException:
		while (ex.getCause() instanceof Exception) {
			ex = (Exception)ex.getCause();

			if (ex instanceof Doc41OptimisticLockingException) {
				relevantException = ex;
				Doc41Log.get().debug(relevantException, null, relevantException.getMessage());
			}
			if (ex instanceof Doc41AccessDeniedException) {
				relevantException = ex;
                Doc41Log.get().debug(relevantException, null, relevantException.getMessage());
			}
		}
		*/
		request.setAttribute(DOC41_EXCEPTION, relevantException);
		return "exception";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
		binder.registerCustomEditor(String.class, stringtrimmer);
	}
	
	
}
