/**
 * 
 */
package com.bayer.bhc.doc41webui.web.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.domain.VersionizedDomainObject;
import com.bayer.bhc.doc41webui.usecase.MonitoringUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class ContactPersonEditController extends AbstractDoc41Controller {

    @Autowired
    private MonitoringUC monitoringUC;

    @Override
	protected boolean hasPermission(User usr) {
		return usr.hasPermission(Doc41Constants.PERMISSION_BUSINESS_ADMIN, Doc41Constants.PERMISSION_TECHNICAL_ADMIN);
	}
    
	@RequestMapping(value="monitoring/editContactPerson",method = RequestMethod.GET)
	public @ModelAttribute("contactPerson") User get(@RequestParam String serviceName,@RequestParam String objectId) throws Doc41BusinessException{
		User user = monitoringUC.findUserById(VersionizedDomainObject.getDcId(objectId));
		user.setCompany(serviceName);
		return user;
	}
	
	@RequestMapping(value="/monitoring/editContactPersonPost",method = RequestMethod.POST)
    public String save(@ModelAttribute("contactPerson") User user, BindingResult result) throws Doc41ExceptionBase{
		ValidationUtils.rejectIfEmptyOrWhitespace(result, "surname", "surname.required","surname is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(result, "firstname", "firstname.required","firstname is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(result, "cwid", "cwid.required","cwid is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(result, "email", "email.required","email is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(result, "phone", "phone.required","phone is required");
    	if (result.hasErrors()) {
    		return "/monitoring/addContactPerson";
        }
		
    	monitoringUC.editContactPerson(user);
        return "redirect:/monitoring/viewContactPerson?serviceName="+user.getCompany();
    }

}
