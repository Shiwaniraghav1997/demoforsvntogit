package com.bayer.bhc.doc41webui.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.bayer.bhc.doc41webui.common.Doc41SessionKeys;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DisplaytextUC;
import com.bayer.bhc.doc41webui.usecase.UserManagementUC;
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
	 * can be overwritten to enforce specific permission
	 * @param usr
	 * @param request 
	 * @return
	 */
	protected boolean hasPermission(User usr, HttpServletRequest request) {
    	return true;
    }


	/* Getter */
	public DisplaytextUC getDisplaytextUC() {
		return displaytextUC;
	}


	public UserManagementUC getUserManagementUC() {
		return userManagementUC;
	}


	protected String displayStatus(HttpServletRequest request, boolean status, Tags tags) {
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
}
