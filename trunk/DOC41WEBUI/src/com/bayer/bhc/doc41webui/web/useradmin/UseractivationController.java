/**
 *(C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.useradmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

/**
 * User activation controller.
 * 
 */
public class UseractivationController extends UserlistController {
	
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		
        getUserManagementUC().toggleUserActivation(request.getParameter("togglecwid"));
		
        return redirectOnSuccess(request, response, command, errors);
	}
}
