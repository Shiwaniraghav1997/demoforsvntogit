package com.bayer.bhc.doc41webui.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.bhc.doc41webui.domain.User;

@Controller
@RequestMapping(value="/profiletest")
public class MyEditUserController extends AbstractDoc41Controller {

	@ModelAttribute("languageCountryList")
	public List<SelectionItem> addCountryCodes(){
		return displaytextUC.getLanguageCountryCodes();
	}
	
	@ModelAttribute("timeZoneList")
	public List<SelectionItem> addTimezones(){
		return displaytextUC.getTimezones();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	protected User handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return (User) request.getAttribute(USER);
    }

	@RequestMapping(method=RequestMethod.POST)
	public String save(@ModelAttribute User user, BindingResult result, Model model) throws Doc41BusinessException {
		System.out.println("HURZU");
		try {
        	getUserManagementUC().editUser(user, true,true);
        } catch (/*Doc41InvalidPassword*/Exception e) {
            result.rejectValue("password", "error.password.invalid", "Please enter a valid password.");
        }
		return "redirect:profileedittest";
	}
}
