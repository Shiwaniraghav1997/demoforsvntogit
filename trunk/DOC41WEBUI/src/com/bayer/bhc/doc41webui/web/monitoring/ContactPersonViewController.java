/**
 * 
 */
package com.bayer.bhc.doc41webui.web.monitoring;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.Monitor;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.MonitoringUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class ContactPersonViewController extends AbstractDoc41Controller {

    private static final String INTERFACE_DETAILS = "service";
    private static final String EBC_USER = "ebcUser";
    private static final String BACKEND_USER = "backendUser";

	@Autowired
	private MonitoringUC monitoringUC;


    /**
     * Get a reqired permission to perform a certain operation, can be overwritten to enforce specific permission
     * @param usr
     * @param request 
     * @return null, if no specific permission required.
     * @throws Doc41BusinessException 
     */
    @Override
    protected String[] getReqPermission(User usr, HttpServletRequest request) throws Doc41BusinessException {
        return new String[] {Doc41Constants.PERMISSION_MONITORING};
//		return usr.hasPermission(Doc41Constants.PERMISSION_MONITORING);
	}
	
	@RequestMapping(value="monitoring/viewContactPerson",method = RequestMethod.GET)
	public @ModelAttribute(INTERFACE_DETAILS) Monitor get(@RequestParam String serviceName,Map<String, Object> model) throws Doc41BusinessException{
		model.put(EBC_USER, monitoringUC.findEBCContactPersonByInterface(serviceName));
		model.put(BACKEND_USER,monitoringUC.findBackendContactPersonByInterface(serviceName));
		return monitoringUC.findInterfaceDetailsByName(serviceName);
	}
}
