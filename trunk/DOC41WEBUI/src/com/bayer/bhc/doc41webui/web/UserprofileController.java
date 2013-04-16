/**
 *(C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.ecim.foundation.basic.StringTool;


/**
 * User profile controller.
 * 
 * @author ezzqc
 */
public class UserprofileController extends Doc41Controller {	
	
	protected boolean hasRolePermission(User usr) {
    	return true;
    }
	
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		@SuppressWarnings("deprecation")
		ModelAndView mav = super.handleRequestInternal(request, response);
		return mav.addObject("languageCountryList", getDisplaytextUC().getLanguageCountryCodes())
                .addObject("timeZoneList", getDisplaytextUC().getTimezones());
    }
    
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
		return UserInSession.get();
	}
    
    @SuppressWarnings("deprecation")
	@Override
    protected void initBinder(HttpServletRequest request,
    		ServletRequestDataBinder binder) throws Exception {
    	super.initBinder(request, binder);
//    	List<Group> groupList =getUserManagementUC().findGroupsForUser(UserInSession.get());
//    	binder.registerCustomEditor(Group.class, new GroupEditor(groupList));
    }
	
    @SuppressWarnings("deprecation")
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    	
		User user =(User)command;
//		request.getSession().setAttribute(Doc41SessionKeys.DOC41_SESSION_LANGUAGE, user.getLanguage());
        
		if (!UserInSession.isReadOnly()) {
			if(!StringTool.equals(user.getPassword(), user.getPasswordRepeated())){
				errors.rejectValue("passwordRepeated", "pwDifferent", "password and passwordRepeated do not match.");
			} else {
				getUserManagementUC().editUser(user, false,false);
			}
	    }

		UserInSession.get().setLocale(user.getLocale());
        LocaleInSession.put(user.getLocale());
        
        return super.onSubmit(request, response, command, errors);
    }
}
