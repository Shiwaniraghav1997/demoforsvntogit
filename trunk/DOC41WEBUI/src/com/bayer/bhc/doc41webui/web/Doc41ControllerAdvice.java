package com.bayer.bhc.doc41webui.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.Doc41SessionKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41AccessDeniedException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.exception.Doc41OptimisticLockingException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.util.DateRenderer;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;


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
	public ModelAndView processHandlerException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		ModelAndView exMv = resolveException(request, response, handler, ex);
		if (exMv != null) {
			return exMv;
		} else {
			throw ex;
		}
	}
	
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

		if (ex instanceof MaxUploadSizeExceededException){
			ex = new Doc41ExceptionBase("File is to big for upload!",ex);
		}

		if (!(ex instanceof Doc41ExceptionBase)) {
			ex.printStackTrace();
			ex = new Doc41TechnicalException(this.getClass(), "fatal error: "+ex.getStackTrace()[0], ex);
		}

		if (ex instanceof Doc41ExceptionBase) {
			String viewName = (String) request.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_VIEW);
			String action = (String)request.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_CTRL);
			@SuppressWarnings("unchecked")
			Map<String, Object> model =(Map<String, Object>)request.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_MODEL);
			if (model != null) {

				model.put(DOC41_EXCEPTION, ex);
				model.put(Doc41SessionKeys.DOC41_LAST_RENDERED_ACTION, action.substring(0, action.length()-10)+".htm");

				// search for Doc41OptimisticLockingException or Doc41AccessDeniedException:
				while (ex.getCause() instanceof Exception) {
					ex = (Exception)ex.getCause();

					if (ex instanceof Doc41OptimisticLockingException) {
						model.put(DOC41_EXCEPTION, ex);
					}
					if (ex instanceof Doc41AccessDeniedException) {
						model.put(DOC41_EXCEPTION, ex);
					}
				}
			}
			//fallback to userprofile to avoid bugging the same failing controller again for this request..
			String formerRedirect = (String)request.getAttribute("failedView");
			if(formerRedirect == null){
				request.setAttribute("failedView",viewName);
			}
			else{
				viewName="userprofile/myprofile";
			}
			return new ModelAndView(viewName,model);
		}
		return null;
	}

}
