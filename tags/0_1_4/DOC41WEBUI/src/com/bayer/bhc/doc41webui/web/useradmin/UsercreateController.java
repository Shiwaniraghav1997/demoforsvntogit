/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.useradmin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.exception.Doc41InvalidPasswordException;
import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.bhc.doc41webui.container.UserEditForm;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class UsercreateController extends AbstractDoc41Controller {
	
	@ModelAttribute("languageCountryList")
	public List<SelectionItem> addLanguageCountryList(){
		return getDisplaytextUC().getLanguageCountryCodes();
	}
	
	@ModelAttribute("timeZoneList")
	public List<SelectionItem> addTimezones(){
		return getDisplaytextUC().getTimezones();
	}
	
	@ModelAttribute("allCountriesList")
	public List<SelectionItem> addAllCountriesList(){
		return getDisplaytextUC().getCountryCodes();
	}
	
	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) {
		return usr.hasPermission(Doc41Constants.PERMISSION_USER_CREATE);
    }
	
	@RequestMapping(value="/useradmin/usercreate",method = RequestMethod.GET)
    public UserEditForm get() throws Doc41ExceptionBase {
		UserEditForm userEditForm = new UserEditForm(new User());
		userEditForm.setActive(Boolean.FALSE);
		userEditForm.setType(User.TYPE_EXTERNAL);
		return userEditForm;
    }
	
	@RequestMapping(value="/useradmin/createuser",method = RequestMethod.POST)
    public String save(HttpServletRequest request,@ModelAttribute UserEditForm userEditForm, BindingResult result) throws Doc41ExceptionBase{
    	userEditForm.validate(request, result);
    	if (result.hasErrors()) {
    		return "/useradmin/usercreate";
        }
    	try {
        	User user = userEditForm.copyToDomainUser();
//        	user.setType(User.TYPE_EXTERNAL);
        	
    			try {
                  getUserManagementUC().createUser(user);
                  return "redirect:/useradmin/userlist";
                  
    			} catch (Doc41InvalidPasswordException e) {
    				result.rejectValue("password", "error.password.invalid", "Please enter a valid password.");
    				return "/useradmin/usercreate";
    			}

        } catch (Doc41InvalidPasswordException e) {
            result.rejectValue("password", "error.password.invalid", "Please enter a valid password.");
            return "/useradmin/usercreate";
        }
    }
}
