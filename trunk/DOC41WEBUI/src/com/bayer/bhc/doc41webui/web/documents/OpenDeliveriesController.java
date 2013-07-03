/**
 * File:OpenDeliveriesController.Java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.documents;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.Delivery;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

/**
 * Controller to manage Translations view related requests using Translations Usecase.
 * @author ezzqc,evayd
 * 
 */
@Controller
public class OpenDeliveriesController extends AbstractDoc41Controller {
	@Autowired
	private DocumentUC documentUC;
	
	protected boolean hasRolePermission(User usr) {
    	return usr.isCarrier();
    }
	
	@RequestMapping(value="/documents/opendeliveries",method = RequestMethod.GET)
	public List<Delivery> get(@RequestParam String type)  {
		String carrier = UserInSession.get().getCompany();//TODO use real carrier field
		List<Delivery> list = documentUC.getOpenDeliveries(type, carrier);
		return list;
    }
	
}
