/**
 * 
 */
package com.bayer.bhc.doc41webui.web.monitoring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.MonitoringUC;
import com.bayer.bhc.doc41webui.web.Doc41Controller;

/**
 * @author MBGHY
 *
 */
public class ContactPersonAddController extends Doc41Controller {

	 /**
     * INTERFACE_NAME The <code>String</code> constant variable.
     */
    private static final String INTERFACE_NAME = "serviceName";

	private static final String CONTACT_TYPE = "contactType";
	
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
	
	/* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.web.Doc41Controller#hasRolePermission(com.bayer.bhc.doc41webui.domain.User)
	 */
	@Override
	protected boolean hasRolePermission(User usr) {
		return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		User user=new User();
		user.setCompany(getParameterValueByName(request,INTERFACE_NAME));
    	user.setType(getParameterValueByName(request,CONTACT_TYPE));
		return user;
	}
	
	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "surname.required","surname is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "firstname.required","firstname is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cwid", "cwid.required","cwid is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required","email is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "phone.required","phone is required");
		return super.processFormSubmission(request, response, command, errors);
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
       
		getMonitoringUC().addContactPerson((User)command);
     
         //remove conact type from session after adding.
        request.getSession().setAttribute(CONTACT_TYPE,null);
		return super.redirectOnSuccess(request, response, command, errors);
	}
	
	private String getParameterValueByName(HttpServletRequest request,String name) {
		String value = request.getParameter(name);
		if(value != null) {
			request.getSession().setAttribute(name,value);
		}else{
			value = (String) request.getSession().getAttribute(name);
		}
		return value;
	}
}
