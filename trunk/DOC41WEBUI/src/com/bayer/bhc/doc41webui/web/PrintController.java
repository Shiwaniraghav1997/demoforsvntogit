/**
 *(C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web;

import com.bayer.bhc.doc41webui.domain.User;


/**
 * Print controller.
 * 
 * @author ezzqc
 */
public class PrintController extends Doc41Controller {	
	
	protected boolean hasRolePermission(User usr) {
    	return true;
    }
}
