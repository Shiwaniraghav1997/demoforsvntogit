/**
 * File:MonitoringOverviewController.java
 *(C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.monitoring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.MonitoringUC;
import com.bayer.bhc.doc41webui.web.Doc41Controller;

/**
 * All Monitoring Interfaces Latest requests processing Overview controller .
 * @author mbghy
 */
public class MonitoringOverviewController extends Doc41Controller {

    /**
     * INTERFACE The <code>String</code> constant variable.
     */
    private static final String MONITORING_ENTRIES = "monitoringEntries";

    /**
     * monitoringUC The spring managed bean<code>MonitoringUC</code>.
     */
    private MonitoringUC monitoringUC;

    /**
     * @return the monitoringUC
     */
    public MonitoringUC getMonitoringUC() {
        return monitoringUC;
    }

    /**
     * @param monitoringUC the monitoringUC to set
     */
    public void setMonitoringUC(final MonitoringUC monitoringUC) {
        this.monitoringUC = monitoringUC;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractFormController#handleRenderRequestInternal(javax.portlet.RenderRequest,
     *      javax.portlet.RenderResponse)
     */
    @SuppressWarnings("deprecation")
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1)
            throws Exception {
    	Doc41Log.get().debug(this.getClass(), arg0.getRemoteUser(), "handleRenderRequestInternal:ENTRY");
    	return super.handleRequestInternal(arg0, arg1)
                      .addObject(MONITORING_ENTRIES,getMonitoringUC().getLatestMonitoringEntries());
    }


	@Override
	protected boolean hasRolePermission(User usr) {
		return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
	}

}
