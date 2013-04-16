/**
 * 
 */
package com.bayer.bhc.doc41webui.web.monitoring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.Doc41ErrorMessageKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.Monitor;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.MonitoringUC;
import com.bayer.bhc.doc41webui.web.Doc41Controller;
import com.bayer.ecim.foundation.basic.StringTool;

/**
 * @author MBGHY
 * 
 */
public class ContactPersonViewController extends Doc41Controller {

	/**
     * INTERFACE_DETAILS The <code>String</code> constant variable.
     */
    private static final String INTERFACE_DETAILS = "service";
	
    /**
     * EBC_USER The <code>String</code> constant variable.
     */
    private static final String EBC_USER = "ebcUser";
   
    /**
     * BACKEND_USER The <code>String</code> constant variable.
     */
    private static final String BACKEND_USER = "backendUser";
    
    /**
     * INTERFACE_NAME The <code>String</code> constant variable.
     */
    private static final String INTERFACE_NAME = "serviceName";

	
	private MonitoringUC monitoringUC;

	/**
	 * @return the monitoringUC
	 */
	public MonitoringUC getMonitoringUC() {
		return monitoringUC;
	}

	/**
	 * @param monitoringUC
	 *            the monitoringUC to set
	 */
	public void setMonitoringUC(MonitoringUC monitoringUC) {
		this.monitoringUC = monitoringUC;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bayer.bhc.doc41webui.web.Doc41Controller#hasRolePermission(com.bayer.bhc
	 * .doc41webui.domain.User)
	 */
	@Override
	protected boolean hasRolePermission(User usr) {
		return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.portlet.mvc.AbstractFormController#formBackingObject
	 * (javax.portlet.PortletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		if (request.getSession().getAttribute(INTERFACE_NAME) == null) {
			if (!StringTool.isTrimmedEmptyOrNull(request.getParameter(INTERFACE_NAME))) {
				request.getSession().setAttribute(INTERFACE_NAME,request.getParameter(INTERFACE_NAME));
			} else {
				throw new Doc41BusinessException(Doc41ErrorMessageKeys.SERVICE_NOT_FOUND);
			}
		}
		return new Monitor();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pInterfaceName = getInterfaceName(request);
		return super.handleRequestInternal(request, response)
						.addObject(INTERFACE_DETAILS,getMonitoringUC().findInterfaceDetailsByName(pInterfaceName))
							.addObject(EBC_USER,getMonitoringUC().findEBCContactPersonByInterface(pInterfaceName))
								.addObject(BACKEND_USER,getMonitoringUC().findBackendContactPersonByInterface(pInterfaceName));
	}

	/**
	 * Extracts The InterfaceName from request if it is null, from session.
	 * 
	 * @param request
	 *            The <code>PortletRequest</code>
	 * @return Returns The <code>String</code> pInterfaceName
	 */
	private String getInterfaceName(HttpServletRequest request) {
		String pInterfaceName = request.getParameter(INTERFACE_NAME);
		if(pInterfaceName!=null){
			request.getSession().setAttribute(INTERFACE_NAME,pInterfaceName);
		}else{
			pInterfaceName = (String) request.getSession().getAttribute(INTERFACE_NAME);
		}
		return pInterfaceName;
	}

}
