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
import com.bayer.bhc.doc41webui.domain.SapVendor;
import com.bayer.bhc.doc41webui.usecase.UserManagementUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.StringTool;

@Controller
public class UserCheckVendorController extends AbstractDoc41Controller {
	@Autowired
	private UserManagementUC userManagementUC;

	@RequestMapping(value="/useradmin/checkvendor", method=RequestMethod.GET,produces="application/json") 
    @ResponseBody
    public Map<String, Object> checkVendor(@RequestParam String vendorNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			SapVendor vendor = userManagementUC.checkVendor(vendorNumber);
			
			if(vendor==null){
				map.put("successful", false);
			} else {
				map.put("successful", true);
				map.put("number", vendor.getNumber());
				map.put("name1", StringTool.nullToEmpty(vendor.getName1()));
				map.put("name2", StringTool.nullToEmpty(vendor.getName2()));
			}
		} catch (Doc41BusinessException e) {
			Doc41Log.get().error(getClass(), UserInSession.getCwid(), e);
			map.put("successful", false);
		}
		
		return map ;
	}
}
