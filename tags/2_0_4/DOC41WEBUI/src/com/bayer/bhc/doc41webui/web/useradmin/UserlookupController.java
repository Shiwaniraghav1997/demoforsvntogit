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
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.container.UserLookupForm;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class UserlookupController extends AbstractDoc41Controller {
	
    /**
     * Get a reqired permission to perform a certain operation, can be overwritten to enforce specific permission
     * @param usr
     * @param request 
     * @return null, if no specific permission required.
     * @throws Doc41BusinessException 
     */
    @Override
    protected String[] getReqPermission(User usr, HttpServletRequest request) throws Doc41BusinessException {
        return new String[] {Doc41Constants.PERMISSION_USER_LOOKUP};
//		return usr.hasPermission(Doc41Constants.PERMISSION_USER_LOOKUP);
    }
	
	@RequestMapping(value="/useradmin/userlookup",method = RequestMethod.GET)
    public UserLookupForm get() {
		return new UserLookupForm();
    }
	
	/**
	 * 
	 * @param request
	 * @param userLookupForm
	 * @param result
	 * @return
	 * @throws Doc41ExceptionBase
	 */
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
            return "/useradmin/userlookup";
        }
    }
	
}
