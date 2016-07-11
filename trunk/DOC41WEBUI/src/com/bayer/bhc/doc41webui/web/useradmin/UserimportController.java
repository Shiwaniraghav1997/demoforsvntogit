/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.useradmin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.exception.Doc41InvalidPasswordException;
import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.bhc.doc41webui.container.UserEditForm;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class UserimportController extends AbstractDoc41Controller {
	
	@ModelAttribute("languageCountryList")
	public List<SelectionItem> addLanguageCountryList(){
		return getDisplaytextUC().getLanguageCountryCodes();
	}
	
	@ModelAttribute("timeZoneList")
	public List<SelectionItem> addTimezones(){
		return getDisplaytextUC().getTimezones();
	}
	
    /**
     * Get a reqired permission to perform a certain operation, can be overwritten to enforce specific permission
     * @param usr
     * @param request 
     * @return null, if no specific permission required.
     * @throws Doc41BusinessException 
     */
    @Override
    protected String[] getReqPermission(User usr, HttpServletRequest request) throws Doc41BusinessException {
        return new String[] {Doc41Constants.PERMISSION_USER_IMPORT};
//		return usr.hasPermission(Doc41Constants.PERMISSION_USER_IMPORT);
    }
	
	@RequestMapping(value="/useradmin/userimport",method = RequestMethod.GET)
    public UserEditForm get(@RequestParam(value="importcwid") String cwid) throws Doc41ExceptionBase {
		UserEditForm userEditForm;
		User importUser = getUserManagementUC().lookupUser(cwid);
		userEditForm = new UserEditForm(importUser);
        userEditForm.setExistingRoles(getUserManagementUC().getAllProfiles());
        userEditForm.setExistingRoleNames(getUserManagementUC().getAllProfileNamesList());
		return userEditForm;
    }

	
    @RequestMapping(value="/useradmin/importuser",method = RequestMethod.POST)
    public String save(HttpServletRequest request,@ModelAttribute UserEditForm userEditForm, BindingResult result) throws Doc41ExceptionBase{
    	userEditForm.validate(request, result, getUserManagementUC());
    	if (result.hasErrors()) {
    		return "/useradmin/userimport";
        }
    	try {
        	User user = userEditForm.copyToDomainUser();
        	user.setType(User.TYPE_INTERNAL);
        	
        	if (StringUtils.isNotBlank(user.getCwid()) && StringUtils.isNotBlank(user.getSurname())) {
    			try {
                  getUserManagementUC().createUser(user);
                  return "redirect:/useradmin/userlist";
                  
    			} catch (Doc41InvalidPasswordException e) {
    				result.rejectValue("password", "error.password.invalid", "Please enter a valid password.");
    				return "/useradmin/userimport";
    			}
    		} else {
    			if (StringUtils.isBlank(user.getCwid())) {
    				result.rejectValue("cwid", "isRequired", "Please enter a cwid.");
    				return "/useradmin/userimport";
              	} else {
              		result.rejectValue("cwid", "lookupRequired", "Please perform lookup first.");
              		return "/useradmin/userimport";
              	}
    		}
        } catch (Doc41InvalidPasswordException e) {
            result.rejectValue("password", "error.password.invalid", "Please enter a valid password.");
            return "/useradmin/userimport";
        }
    }
}
