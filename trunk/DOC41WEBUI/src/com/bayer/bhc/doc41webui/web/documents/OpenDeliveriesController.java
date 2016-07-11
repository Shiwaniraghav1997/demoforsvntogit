/**
 * File:OpenDeliveriesController.Java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.documents;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.DeliveryOrShippingUnit;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.StringTool;

/**
 * @author ezzqc,evayd
 * 
 */
@Controller
public class OpenDeliveriesController extends AbstractDoc41Controller {
	@Autowired
	private DocumentUC documentUC;
	
    /**
     * Get a reqired permission to perform a certain operation, can be overwritten to enforce specific permission
     * @param usr
     * @param request 
     * @return null, if no specific permission required.
     * @throws Doc41BusinessException 
     */
    @Override
    protected String[] getReqPermission(User usr, HttpServletRequest request) throws Doc41BusinessException {
		String type = request.getParameter("type");
		if(StringTool.isTrimmedEmptyOrNull(type)){
			throw new IllegalArgumentException("type is missing");
		}
		return new String[] {documentUC.getUploadPermission(type)};
		//return usr.hasPermission(permission);
    }
	
	@RequestMapping(value="/documents/opendeliveries",method = RequestMethod.GET)
	public List<DeliveryOrShippingUnit> get(@RequestParam String type,@RequestParam String vendorNumber) throws Doc41BusinessException  {
		return documentUC.getOpenDeliveries(type, vendorNumber);
    }
	
}
