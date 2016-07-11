package com.bayer.bhc.doc41webui.web.maintenance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.domain.PermissionProfiles;
import com.bayer.bhc.doc41webui.domain.Profile;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class ProfilePermissionsController extends AbstractDoc41Controller {
	
    /**
     * Get a reqired permission to perform a certain operation, can be overwritten to enforce specific permission
     * @param usr
     * @param request 
     * @return null, if no specific permission required - or a list of permissions of which one is required
     * @throws Doc41BusinessException 
     */
    @Override
    protected String[] getReqPermission(User usr, HttpServletRequest request) throws Doc41BusinessException {
        return new String[] {Doc41Constants.PERMISSION_PROFILEPERMISSIONS};
//		return usr.hasPermission(Doc41Constants.PERMISSION_PROFILEPERMISSIONS);
	}
	
	@RequestMapping(value="/maintenance/profilePermissions", method=RequestMethod.GET)
    public void get(ModelMap model) throws Doc41TechnicalException {
		List<PermissionProfiles> pps = getUserManagementUC().getPermissionProfiles();
		List<Profile> pro = getUserManagementUC().getAllProfiles();
        model.addAttribute("pps", pps);
        model.addAttribute("pro", pro);
    }

}
