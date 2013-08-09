package com.bayer.bhc.doc41webui.web.useradmin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.domain.UserPartner;
import com.bayer.bhc.doc41webui.usecase.UserManagementUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class UserCheckPartnerController extends AbstractDoc41Controller {
	@Autowired
	private UserManagementUC userManagementUC;

	@RequestMapping(value="/useradmin/checkpartner", method=RequestMethod.GET,produces="application/json") 
    @ResponseBody
    public Map<String, Object> checkPartner(@RequestParam String partnerNumber) throws Doc41ExceptionBase{
		UserPartner partner = userManagementUC.checkPartner(partnerNumber);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(partner==null){
			map.put("successful", false);
		} else {
			map.put("successful", true);
			map.put("partnerNumber", partner.getPartnerNumber());
			map.put("partnerName1", partner.getPartnerName1());
			map.put("partnerName2", partner.getPartnerName2());
		}
		return map ;
	}
}
