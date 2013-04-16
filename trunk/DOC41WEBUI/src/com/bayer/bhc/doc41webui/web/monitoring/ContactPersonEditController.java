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
public class ContactPersonEditController extends Doc41Controller {

	private static final String OBJECT_ID = "objectId";

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
		return getMonitoringUC().findUserById(getGivenDcId(request, OBJECT_ID));
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
		getMonitoringUC().editContactPerson((User)command);
		return super.redirectOnSuccess(request, response, command, errors);
	}
}
