/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.useradmin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.container.UserLookupForm;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class UserlookupController extends AbstractDoc41Controller {
	
	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) {
		return usr.hasPermission(Doc41Constants.PERMISSION_USER_LOOKUP);
    }
	
//	@ModelAttribute("languageCountryList")
//	public List<SelectionItem> addLanguageCountryList(){
//		return getDisplaytextUC().getLanguageCountryCodes();
//	}
//	
//	@ModelAttribute("timeZoneList")
//	public List<SelectionItem> addTimezones(){
//		return getDisplaytextUC().getTimezones();
//	}
	
	@RequestMapping(value="/useradmin/userlookup",method = RequestMethod.GET)
    public UserLookupForm get() {
		return new UserLookupForm();
    }
	
	@RequestMapping(value="/useradmin/lookuppost",method = RequestMethod.POST)
    public String save(HttpServletRequest request,@ModelAttribute UserLookupForm userLookupForm, BindingResult result) throws Doc41ExceptionBase{
    	if (result.hasErrors()) {
    		return "/useradmin/lookuppost";
        }

    	// perform ldap search:
        String cwid = userLookupForm.getCwid();
        if (StringUtils.isNotBlank(cwid)) {
        	User importUser = getUserManagementUC().lookupUser(cwid);
        	if(importUser==null){
        		result.rejectValue("cwid", "noFound", "Please enter a cwid.");
                return "/useradmin/lookuppost";
        	}
        	return "redirect:/useradmin/userimport?importcwid="+cwid;
        } else {
            result.rejectValue("cwid", "isRequired", "Please enter a cwid.");
            return "/useradmin/lookuppost";
        }
    }
	
}
