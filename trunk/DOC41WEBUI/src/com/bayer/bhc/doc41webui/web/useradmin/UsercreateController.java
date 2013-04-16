/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.useradmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.exception.Doc41InvalidPasswordException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.container.UserEditForm;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.web.Doc41Controller;

/**
 * User create Controller. Create a new user.
 * 
 */
public class UsercreateController extends Doc41Controller {
		
	protected boolean hasRolePermission(User usr) {
		return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
    }
	
	@SuppressWarnings("deprecation")
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return super.handleRequestInternal(request, response)
                .addObject("languageCountryList", getDisplaytextUC().getLanguageCountryCodes())
                        .addObject("timeZoneList", getDisplaytextUC().getTimezones());
    }
	
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
        //Removing UserId from session.
        request.getSession().setAttribute("userId",null);
        request.getSession().setAttribute("createdId",null);
        
        
		UserEditForm userEditForm = new UserEditForm(new User());
		userEditForm.setActive(Boolean.FALSE);
		userEditForm.setType(User.TYPE_EXTERNAL);
		
		Doc41Log.get().debug(this.getClass(), "System", "Exiting UsercreateController.formBackingObject() " + userEditForm);
		return userEditForm ;
	}
	
	@SuppressWarnings("deprecation")
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		UserEditForm form =(UserEditForm)command;
        //user.setLocale(request.getLocale());
		try {
			User user = form.copyToDomainUser();
//			user.setType(User.TYPE_EXTERNAL);
//            user.setLocale(request.getLocale());
            getUserManagementUC().createUser(user);
            
            //request.setParameter("cwid", user.getCwid());
            request.getSession().setAttribute("createdId", user.getCwid());
            return redirectOnSuccess(request, response, command, errors);

		} catch (Doc41InvalidPasswordException e) {
            errors.rejectValue("password", "error.password.invalid", "Please enter a valid password.");
        }
		
        return super.onSubmit(request, response, command, errors);
	}
}
