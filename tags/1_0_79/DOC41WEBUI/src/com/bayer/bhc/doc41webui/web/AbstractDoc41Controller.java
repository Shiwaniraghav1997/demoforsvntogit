package com.bayer.bhc.doc41webui.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bayer.bhc.doc41webui.common.Doc41SessionKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.BdsServiceError;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DisplaytextUC;
import com.bayer.bhc.doc41webui.usecase.UserManagementUC;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

/**
 * common implementation methods for all controllers.
 * @author evayd
 *
 */
public abstract class AbstractDoc41Controller implements Doc41SessionKeys {
	public static final String OBJECTID="objectID";
	
	@Autowired
	protected DisplaytextUC displaytextUC;
	@Autowired
	protected UserManagementUC userManagementUC;
	

    /**
     * Get a reqired permission to perform a certain operation, can be overwritten to enforce specific permission
     * @param usr
     * @param request 
     * @return null, if no specific permission required - or a list of permissions of which one is required
     * @throws Doc41BusinessException 
     */
    protected String[] getReqPermission(User usr, HttpServletRequest request) throws Doc41BusinessException {
        return null; // no specific
    }

    /**
	 * can be overwritten to enforce specific permission
	 * @param usr
	 * @param request 
	 * @return
	 * @throws Doc41BusinessException 
	 */
	protected boolean hasPermission(User usr, HttpServletRequest request) throws Doc41BusinessException {
	    String type = request.getParameter("type");
	    String[] mPerm = getReqPermission(usr, request);
	    boolean mRes = (mPerm == null) || usr.hasPermission(mPerm);
	    String pClass = getClass().getSimpleName();
	    if (mPerm != null) {
	        if (mRes) {
	            Doc41Log.get().debug(this, usr.getCwid(), pClass + " - CheckPerm " + type + " " + StringTool.list(mPerm, ",", false) + ": " + (mRes ? "ok" : "FAIL"));
	        } else {
                Doc41Log.get().warning(this, usr.getCwid(), pClass + " - CheckPerm " + type + " " + StringTool.list(mPerm, ",", false) + ": " + (mRes ? "ok" : "FAIL"));
	        }
	    } else {
            Doc41Log.get().debug(this, usr.getCwid(), pClass + " - CheckPerm " + StringTool.nvl(StringTool.emptyToNull(StringTool.embed(type, "(on Group) ", " ")),"in general") + " ok");
	    }
    	return mRes;
    }


	/* Getter */
	public DisplaytextUC getDisplaytextUC() {
		return displaytextUC;
	}


	public UserManagementUC getUserManagementUC() {
		return userManagementUC;
	}


	protected String displayStatus(boolean status, Tags tags) {
		String altText;
		String iconName;
		if(status){
			altText = tags.getTag("Active");
			iconName = "ball_green.gif";
		} else {
			altText = tags.getTag("Inactive");
			iconName = "ball_red.gif";
		}
		return "<img src='../resources/img/common/"+iconName+"' alt='"+altText+"' style='border: 0px;'>";
	}
	
	protected String getLastCustomerNumberFromSession(){
		return (String) session().getAttribute(LAST_CUSTOMER_NUMBER);
	}
	
	protected void setLastCustomerNumberFromSession(String customerNumber){
		if(!StringTool.isTrimmedEmptyOrNull(customerNumber)){
			session().setAttribute(LAST_CUSTOMER_NUMBER,customerNumber);
		}
	}
	
	protected String getLastVendorNumberFromSession(){
		return (String) session().getAttribute(LAST_VENDOR_NUMBER);
	}
	
	protected void setLastVendorNumberFromSession(String vendorNumber){
		if(!StringTool.isTrimmedEmptyOrNull(vendorNumber)){
			session().setAttribute(LAST_VENDOR_NUMBER,vendorNumber);
		}
	}
	
	protected String getAllErrorsAsString(BindingResult result) {
        StringBuilder sb = new StringBuilder();
        List<ObjectError> allErrors = result.getAllErrors();
        for (ObjectError objectError : allErrors) {
            sb.append(objectError.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
	
	protected BdsServiceError[] getAllErrorsAsServiceErrors(
            BindingResult result) {
	    List<ObjectError> allErrors = result.getAllErrors();
	    BdsServiceError[] errors = new BdsServiceError[allErrors.size()];
	    int i=0;
        for (ObjectError objectError : allErrors) {
            errors[i] = new BdsServiceError();
            errors[i].setErrorCode(objectError.getCode());
            if(objectError instanceof FieldError){
                FieldError fieldError = (FieldError) objectError;
                errors[i].setField(fieldError.getField());
                errors[i].setRejectedValue(""+fieldError.getRejectedValue());
            }
            i++;
        }
        return errors;
    }


    public static HttpSession session() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession(true); // true == allow create
	}
}
