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

import com.bayer.bhc.doc41webui.container.UserEditForm;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.web.Doc41Controller;

/**
 * User import controller. Imports internal user from LDAP.
 * 
 * @author ezzqc
 */
public class UserlookupController extends Doc41Controller {
	
	protected boolean hasRolePermission(User usr) {
    	return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
    }
	
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		request.getSession().setAttribute("userId",null);
		return new User();
	}
	
	@SuppressWarnings("deprecation")
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return super.handleRequestInternal(request, response)
                .addObject("languageCountryList", getDisplaytextUC().getLanguageCountryCodes())
                        .addObject("timeZoneList", getDisplaytextUC().getTimezones());
    }

	
	@SuppressWarnings("deprecation")
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		
		// perform ldap search:
        String cwid = request.getParameter("lookupcwid");
        if (StringUtils.isNotBlank(cwid)) {
        	User importUser = getUserManagementUC().lookupUser(cwid);
        	
            //return new ModelAndView(getSuccessView()).addObject("edituser", user);
//        	Agent agentLoginUser = getUserManagementUC().getAgentForUser(UserInSession.get(), false,true);
        	UserEditForm form = new UserEditForm(importUser);
            request.getSession().setAttribute("importuser", form);
            return redirectOnSuccess(request, response, command, errors);
            
        } else {
            errors.rejectValue("cwid", "isRequired", "Please enter a cwid.");
        }
        
		return super.onSubmit(request, response, command, errors);
	}
		
}
