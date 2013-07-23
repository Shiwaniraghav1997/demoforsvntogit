/**
 * 
 */
package com.bayer.bhc.doc41webui.web.monitoring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.domain.Monitor;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.MonitoringUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class InterfaceAddController extends AbstractDoc41Controller {

	@Autowired
    private MonitoringUC monitoringUC;

	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) {
		return usr.hasPermission(Doc41Constants.PERMISSION_BUSINESS_ADMIN, Doc41Constants.PERMISSION_TECHNICAL_ADMIN);
	}
	
	@RequestMapping(value="monitoring/addInterface",method = RequestMethod.GET)
	public Monitor get(){
		return new Monitor();
	}
	
	@RequestMapping(value="/monitoring/addInterfacePost",method = RequestMethod.POST)
    public String save(@ModelAttribute Monitor monitor, BindingResult result) throws Doc41ExceptionBase{
		ValidationUtils.rejectIfEmptyOrWhitespace(result, "name", "name.required","Name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(result, "desc", "desc.required","Description is required");
    	if (result.hasErrors()) {
    		return "/monitoring/addInterface";
        }
		
    	monitoringUC.addInterface(monitor);
        return "redirect:/monitoring/monitoringOverview";
    }
}
