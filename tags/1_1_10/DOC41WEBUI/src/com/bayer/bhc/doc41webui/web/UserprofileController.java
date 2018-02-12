/**
 *(C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bayer.bhc.doc41webui.common.exception.Doc41AccessDeniedSecurityBreachException;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.ecim.foundation.basic.StringTool;


/**
 * User profile controller.
 * 
 * @author ezzqc
 */
@Controller
public class UserprofileController extends AbstractDoc41Controller {	
	
	@ModelAttribute("languageCountryList")
	public List<SelectionItem> addCountryCodes(){
		return displaytextUC.getLanguageCountryCodes();
	}
	
	@ModelAttribute("timeZoneList")
	public List<SelectionItem> addTimezones(){
		return displaytextUC.getTimezones();
	}
	

	@RequestMapping(value="/userprofile/myprofile",method = RequestMethod.GET)
    public User get() {
		return UserInSession.get();
    }
    
    @RequestMapping(value="/userprofile/saveprofile*",method = RequestMethod.POST)
    public String save(@ModelAttribute User user, BindingResult result) throws Doc41BusinessException, Doc41AccessDeniedSecurityBreachException{
    	if (result.hasErrors()) {
            return "/userprofile/myprofile";
        }
		if (!UserInSession.isReadOnly()) {
		    User mLoggedInUser = UserInSession.get();
			// only copy resulting Language/Country combination...
			if (!mLoggedInUser.getCwid().equals(user.getCwid())) {
			    throw new Doc41AccessDeniedSecurityBreachException(getClass());
			}
            if(!StringTool.equals(user.getPassword(), user.getPasswordRepeated())){
                //TODO put in validation
                result.rejectValue("passwordRepeated", "pwDifferent", "password and passwordRepeated do not match.");
                return "/userprofile/myprofile";
            } else {
                mLoggedInUser.setLocale(user.getLocale());
                mLoggedInUser.setPassword(user.getPassword());
                mLoggedInUser.setPasswordRepeated(user.getPasswordRepeated());
			    /* never directly persist user loaded 100% from form - security hole, do this way, copy to internal object and store internal object, USER may change anything, become internal, change cwid
			    if (BooleanTool.nvl(mLoggedInUser.isExternalUser(), false) {
			        mLoggedInUser.setFirstname(user.getFirstname());
			        mLoggedInUser.setSurname(user.getSurname());
			        mLoggedInUser.setEmail(user.getEmail());
			    }
			    */
                userManagementUC.editUser(mLoggedInUser, false,false,false,false,false);
				//userManagementUC.editUser(user, false,false,false,false,false);
			}
	    }

		UserInSession.get();
        LocaleInSession.put(user.getLocale());
        
        return "redirect:/userprofile/myprofile";
    }
}
