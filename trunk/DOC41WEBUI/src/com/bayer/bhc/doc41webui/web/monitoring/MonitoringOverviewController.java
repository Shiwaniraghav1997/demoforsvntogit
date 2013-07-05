/**
 * File:MonitoringOverviewController.java
 *(C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.monitoring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.Monitor;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.MonitoringUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class MonitoringOverviewController extends AbstractDoc41Controller {

    /**
     * INTERFACE The <code>String</code> constant variable.
     */
    private static final String MONITORING_ENTRIES = "monitoringEntries";

    @Autowired
    private MonitoringUC monitoringUC;

 
    @RequestMapping(value="/monitoring/monitoringOverview",method = RequestMethod.GET)
    public @ModelAttribute(MONITORING_ENTRIES) List<Monitor> get() throws Doc41BusinessException{
    	return monitoringUC.getLatestMonitoringEntries();
    }



	@Override
	protected boolean hasPermission(User usr) {
		return usr.hasPermission(Doc41Constants.PERMISSION_BUSINESS_ADMIN, Doc41Constants.PERMISSION_TECHNICAL_ADMIN);
	}

}
