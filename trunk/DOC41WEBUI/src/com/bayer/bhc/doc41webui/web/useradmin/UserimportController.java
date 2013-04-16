/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.useradmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.exception.Doc41InvalidPasswordException;
import com.bayer.bhc.doc41webui.container.UserEditForm;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.web.Doc41Controller;

/**
 * User import controller. Imports internal user from LDAP.
 * 
 * @author ezzqc
 */
public class UserimportController extends Doc41Controller {
	
	protected boolean hasRolePermission(User usr) {
		return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
    }
	
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		UserEditForm form = (UserEditForm) request.getSession().getAttribute("importuser");
		return form;
	}
	
	@SuppressWarnings("deprecation")
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return super.handleRequestInternal(request, response)
                .addObject("languageCountryList", getDisplaytextUC().getLanguageCountryCodes())
                        .addObject("timeZoneList", getDisplaytextUC().getTimezones());
    }

	
	@SuppressWarnings("deprecation")
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		UserEditForm form =(UserEditForm)command;
		User user = form.copyToDomainUser();
		
//		user.setActive(Boolean.FALSE);
		user.setType(User.TYPE_INTERNAL);
//		user.setLocale(request.getLocale());
		
		if (StringUtils.isNotBlank(user.getCwid()) && StringUtils.isNotBlank(user.getSurname())) {
			try {
              getUserManagementUC().createUser(user);
              // switch to edit controller:
              return new ModelAndView("redirect:/useredit.htm?editcwid="+user.getCwid().toUpperCase());
              
			} catch (Doc41InvalidPasswordException e) {
              errors.rejectValue("password", "error.password.invalid", "Please enter a valid password.");
			}
		} else {
			if (StringUtils.isBlank(user.getCwid())) {
				errors.rejectValue("cwid", "isRequired", "Please enter a cwid.");
          	} else {
          		errors.rejectValue("cwid", "lookupRequired", "Please perform lookup first.");
          	}
		}
		return super.onSubmit(request, response, command, errors);
	}
}
