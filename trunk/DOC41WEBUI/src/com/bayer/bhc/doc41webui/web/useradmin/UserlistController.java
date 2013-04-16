/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.useradmin;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.container.PagingForm;
import com.bayer.bhc.doc41webui.container.UserListFilter;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.web.PagingListController;

/**
 * User list controller. Used for paging and filtering.
 * 
 * @author ezzqc
 */
public class UserlistController extends PagingListController {

	protected boolean hasRolePermission(User usr) {
		return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
    }
	
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        // one filter per session
        UserListFilter command = 
            (UserListFilter)request.getSession().getAttribute(USER_FILTER);
        
        if (command == null) {
            command = new UserListFilter();
            request.getSession().setAttribute(USER_FILTER, command);
        }
        return command;
    }
    
    protected ModelAndView getListModel(PagingForm pform, HttpServletRequest request, Object command) throws Exception {
        Doc41Log.get().debug(this.getClass(), "System", "Entering UserlistController.handleRenderRequestInternal()");         
        
        UserListFilter userListFilter = (UserListFilter)command;
        
        if (isResetRequest(userListFilter)) {
            userListFilter.reset();
        }
        PagingResult<User> pagingResult = getUserManagementUC().findUsers(userListFilter, pform);
        pform.setTotalSize(pagingResult.getTotalSize());
        return new ModelAndView().addObject("userList", pagingResult.getResult());
    }
}
