/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.Doc41SessionKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41AccessDeniedException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.exception.Doc41OptimisticLockingException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;


/**
 * General handling with BOE exceptions.
 * 
 * @author ezzqc
 */
public class Doc41ExceptionResolver extends DispatcherServlet implements HandlerExceptionResolver {		
	private static final long serialVersionUID = 1L;
	// compare header.jspf !
	public final static String DOC41_EXCEPTION = "doc41exception";
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.DispatcherServlet#processHandlerException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	protected ModelAndView processHandlerException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		ModelAndView exMv = resolveException(request, response, handler, ex);
		if (exMv != null) {
			return exMv;
		} else {
			throw ex;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		
        if (ex instanceof MaxUploadSizeExceededException){
            ex = new Doc41ExceptionBase("File is to big for upload!",ex);
        }

	        if (!(ex instanceof Doc41ExceptionBase)) {
	        	ex.printStackTrace();
	        	ex = new Doc41TechnicalException(this.getClass(), "fatal error: "+ex.getStackTrace()[0], ex);
	        }
	        
	        if (ex instanceof Doc41ExceptionBase) {
	            SessionModelAndView smav = (SessionModelAndView)request.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_MAV);
	            String viewName = smav.getViewName();
	            ModelMap model = smav.getModel();
	            String action = (String)request.getSession().getAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_CTRL);
	            if (model != null) {
	
	            	model.addAttribute(DOC41_EXCEPTION, ex);
	            	model.addAttribute(Doc41SessionKeys.DOC41_LAST_RENDERED_ACTION, action.substring(0, action.length()-10)+".htm");
	                
	                // search for Doc41OptimisticLockingException or Doc41AccessDeniedException:
	                while (ex.getCause() instanceof Exception) {
	                    ex = (Exception)ex.getCause();
	                    
	                    if (ex instanceof Doc41OptimisticLockingException) {
	                    	model.addAttribute(DOC41_EXCEPTION, ex);
	                    }
	                    if (ex instanceof Doc41AccessDeniedException) {
	                    	model.addAttribute(DOC41_EXCEPTION, ex);
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
