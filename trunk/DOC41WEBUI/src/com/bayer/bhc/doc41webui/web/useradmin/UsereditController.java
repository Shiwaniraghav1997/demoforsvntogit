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
 * The controller class for editing a user.
 * 
 */
public class UsereditController extends Doc41Controller {
	
	protected boolean hasRolePermission(User usr) {
		return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
    }
	
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
        Doc41Log.get().debug(this.getClass(), "System", "Entering UsereditController.formBackingObject() " );
        // Removing UserId from session.
        request.getSession().setAttribute("userId",null);
        
        UserEditForm userEditForm;
        User updateUser;
        if (isFormSubmission(request)) {
        	userEditForm = (UserEditForm) request.getSession().getAttribute(USER_EDIT_FORM);
        	updateUser = null;
        } else {        
			String cwid = request.getParameter("editcwid");
			if (cwid == null || (cwid.trim().length() == 0)) {
				cwid = (String)request.getSession().getAttribute("createdId");
			}
			updateUser = getUserManagementUC().findUser(cwid);
			
			userEditForm = new UserEditForm(updateUser);
			
			
			request.getSession().setAttribute(USER_EDIT_FORM,userEditForm);
        }
		Doc41Log.get().debug(this.getClass(), "System", "Exiting UsereditController.formBackingObject() " + updateUser);
		return userEditForm;		
	}
	
	@SuppressWarnings("deprecation")
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = super.handleRequestInternal(request, response);
		
//		UserEditForm userEditForm = (UserEditForm) request.getSession().getAttribute(USER_EDIT_FORM);
		
		return mav
                .addObject("languageCountryList", getDisplaytextUC().getLanguageCountryCodes())
                        .addObject("timeZoneList", getDisplaytextUC().getTimezones());
    }
	 
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		UserEditForm form =(UserEditForm)command;
        //user.setLocale(request.getLocale());
        try {
        	User user = form.copyToDomainUser();
        	getUserManagementUC().editUser(user, true,true);
        } catch (Doc41InvalidPasswordException e) {
            errors.rejectValue("password", "error.password.invalid", "Please enter a valid password.");
        }
		
        return super.redirectOnSuccess(request, response, command, errors);
	}
}
