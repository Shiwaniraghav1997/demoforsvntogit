package com.bayer.bhc.doc41webui.web.useradmin;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.exception.Doc41InvalidPasswordException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.bhc.doc41webui.container.UserEditForm;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class UsereditController extends AbstractDoc41Controller {
	
	protected boolean hasPermission(User usr) {
		return usr.hasPermission(Doc41Constants.PERMISSION_BUSINESS_ADMIN, Doc41Constants.PERMISSION_TECHNICAL_ADMIN);
    }
	
	@ModelAttribute("languageCountryList")
	public List<SelectionItem> addLanguageCountryList(){
		return getDisplaytextUC().getLanguageCountryCodes();
	}
	
	@ModelAttribute("timeZoneList")
	public List<SelectionItem> addTimezones(){
		return getDisplaytextUC().getTimezones();
	}
	
	@RequestMapping(value="/useradmin/useredit",method = RequestMethod.GET)
    public UserEditForm get(@RequestParam(value="editcwid") String cwid) throws Doc41ExceptionBase {
		if(cwid==null){
			throw new Doc41TechnicalException(null,"cwid is null");
		}
		UserEditForm userEditForm;
		User updateUser = getUserManagementUC().findUser(cwid);
		userEditForm = new UserEditForm(updateUser);
		return userEditForm;
    }
	
    @RequestMapping(value="/useradmin/updateuser",method = RequestMethod.POST)
    public String save(HttpServletRequest request,@ModelAttribute UserEditForm userEditForm, BindingResult result) throws Doc41ExceptionBase{
    	userEditForm.validate(request, result);
    	if (result.hasErrors()) {
    		return "/useradmin/updateuser";
        }
    	try {
        	User user = userEditForm.copyToDomainUser();
        	getUserManagementUC().editUser(user, true,true);
        } catch (Doc41InvalidPasswordException e) {
            result.rejectValue("password", "error.password.invalid", "Please enter a valid password.");
            return "/useradmin/updateuser";
        }
    	
        return "redirect:/useradmin/userlist";
    }
}
