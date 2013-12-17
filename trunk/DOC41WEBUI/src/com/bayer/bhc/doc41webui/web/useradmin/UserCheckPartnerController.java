package com.bayer.bhc.doc41webui.web.useradmin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.SapPartner;
import com.bayer.bhc.doc41webui.usecase.UserManagementUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.StringTool;

@Controller
public class UserCheckPartnerController extends AbstractDoc41Controller {
	@Autowired
	private UserManagementUC userManagementUC;

	@RequestMapping(value="/useradmin/checkpartner", method=RequestMethod.GET,produces="application/json") 
    @ResponseBody
    public Map<String, Object> checkPartner(@RequestParam String partnerNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			SapPartner partner = userManagementUC.checkPartner(partnerNumber);
			
			if(partner==null){
				map.put("successful", false);
			} else {
				map.put("successful", true);
				map.put("partnerNumber", partner.getPartnerNumber());
				map.put("partnerName1", StringTool.nullToEmpty(partner.getPartnerName1()));
				map.put("partnerName2", StringTool.nullToEmpty(partner.getPartnerName2()));
				map.put("partnerType", partner.getPartnerType());
			}
		} catch (Doc41BusinessException e) {
			Doc41Log.get().error(getClass(), UserInSession.getCwid(), e);
			map.put("successful", false);
		}
		
		return map ;
	}
}
