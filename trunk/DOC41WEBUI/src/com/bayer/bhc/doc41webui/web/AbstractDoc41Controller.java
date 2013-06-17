package com.bayer.bhc.doc41webui.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DisplaytextUC;
import com.bayer.bhc.doc41webui.usecase.UserManagementUC;

/**
 * common implementation methods for all controllers.
 * @author evayd
 *
 */
public abstract class AbstractDoc41Controller{
	
	@Autowired
	protected DisplaytextUC displaytextUC;
	@Autowired
	protected UserManagementUC userManagementUC;
	

	/**
	 * can be overwritten to enforce specific roles
	 * @param usr
	 * @return
	 */
	protected boolean hasRolePermission(User usr) {
    	return true;
    }
	
	

}
