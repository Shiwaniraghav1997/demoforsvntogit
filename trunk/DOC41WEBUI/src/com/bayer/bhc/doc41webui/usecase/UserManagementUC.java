/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.Doc41ErrorMessageKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.exception.Doc41RepositoryException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.paging.PagingData;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.PagingForm;
import com.bayer.bhc.doc41webui.container.UserListFilter;
import com.bayer.bhc.doc41webui.container.UserPagingRequest;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.service.repository.UserManagementRepository;

/**
 * User management use case implementation. Finds, edits, creates user. Also used for to active or deactive user accounts. 
 * 
 * @author ezlwb, ezzqc
 */
@Component
public class UserManagementUC {
	@Autowired
    private UserManagementRepository userMgmtRepo;
    
	/**
	 * creates a new user in the usermanagement DB
	 * an external user will first be created in LDAP
	 * 
	 * is used for create and import
	 * 
	 * @param user to be created
	 * @throws Doc41BusinessException 
	 */
    public void createUser(User pUser) throws Doc41BusinessException {
//        pUser.setActive(Boolean.FALSE);
        try {
            getUserManagementRepository().createUser(pUser);
            // logging
            Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(), "createUser() - user cwid '"+pUser.getCwid()+"'.");
            // audit
            // getDoc41AuditService().doAudit(this.getClass(), Doc41AuditEntry.ACTION_CREATE_USER, "user cwid '"+pUser.getCwid()+"'.", null, null, null, null, null, null, null, null, null);
        } catch (Doc41ExceptionBase e) {
            throw new Doc41BusinessException(Doc41ErrorMessageKeys.USR_CREATE_FAILED, e);
        }
    }

    /**
	 * update an existing user
	 * an external will also be updated in LDAP
	 * 
	 * user.type must be set!
	 * 
	 * @param user
	 * @throws RuntimeException if no type was given
	 */
    public void editUser(User pUser, boolean updateRoles,boolean updateLdap) throws Doc41BusinessException {
        try {
            getUserManagementRepository().updateUser(pUser, updateRoles,updateLdap);
            // logging
            Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(), "editUser() - user cwid '"+pUser.getCwid()+"'.");
            // audit
            // getDoc41AuditService().doAudit(this.getClass(), Doc41AuditEntry.ACTION_EDIT_USER, "user cwid '"+pUser.getCwid()+"'.", null, null, null, null, null, null, null, null, null);            
        } catch (Doc41ExceptionBase e) {
            throw new Doc41BusinessException(Doc41ErrorMessageKeys.USR_EDIT_FAILED, e);
        }
    }

    /**
	 * find user by cwid in Usermanagement DB
	 * 
	 * @param cwid
	 * @return User found
	 * @throws Doc41BusinessException 
	 */
    public User findUser(String pCwid) throws Doc41BusinessException {
        try {
            // logging
        	Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(), "findUser() - user cwid '"+pCwid+"'.");
            User userByCwid = getUserManagementRepository().getUserByCwid(pCwid);

            return userByCwid;
        } catch (Doc41RepositoryException e) {
            throw new Doc41BusinessException(Doc41ErrorMessageKeys.USR_FIND_FAILED, e);
        }
    }

    /**
	 * find users matching the given filtercriterias 
	 * in Usermanagement DB 
	 * 
	 * @param filter selected by user
	 * @return List of users
	 */
    public PagingResult<User> findUsers(UserListFilter pFilter, PagingData pdata) throws Doc41BusinessException {
        PagingResult<User> userList;
        
        try {
            // logging
        	Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(), "findUsers()");
            UserPagingRequest userRequest = new UserPagingRequest(pFilter, pdata);
            userList = getUserManagementRepository().getUsers(userRequest);
        } catch (Doc41RepositoryException e) {
            throw new Doc41BusinessException(Doc41ErrorMessageKeys.USR_FIND_ALL_FAILED, e);
        }

        return userList;
    }
    
    /**
	 * find user by cwid in Bayer LDAP
	 * used for internal user import
	 * 
	 * @param cwid
	 * @return User found
	 */
    public User lookupUser(String pCwid) throws Doc41BusinessException {
        try {
            return getUserManagementRepository().lookupUser(pCwid);
        } catch (Doc41RepositoryException e) {
            throw new Doc41BusinessException(Doc41ErrorMessageKeys.USR_MGT_LDAP_LOOKUPUSER_FAILED, e);
        }
    }
    
    /**
     * authenticate internal user against Bayer ldap.
     * 
     * @param cwid
     * @param password
     * @return boolean
     * @throws Doc41BusinessException 
     */
    public boolean isAuthenticated(String cwid, String password) throws Doc41BusinessException {
        try {
            return getUserManagementRepository().isAuthenticated(cwid, password);
        } catch (Doc41RepositoryException e) {
            throw new Doc41BusinessException(Doc41ErrorMessageKeys.USR_MGT_LDAP_AUTHINTUSER_FAILED, e);
        }
    }

    /**
	 * toggle active flag of a user and store it in usermanagement db
	 * @param user
	 * @return modified user
	 * @throws Doc41BusinessException 
	 */
    public User toggleUserActivation(String pCwid) throws Doc41BusinessException {
        User user = null;
        if (pCwid != null) {
            try {
                user = getUserManagementRepository().getUserByCwid(pCwid);
            
                if (user != null) {
	                if (user.getActive() == null || user.getActive().booleanValue()) {
	                    user.setActive(Boolean.FALSE);
	                } else {
	                    user.setActive(Boolean.TRUE);
	                }
	                getUserManagementRepository().updateUser(user, false,true);
	                // logging
	                Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(), "toggleUserActivation() - user cwid '"+pCwid+"'.");
	                // audit
	                // getDoc41AuditService().doAudit(this.getClass(), Doc41AuditEntry.ACTION_EDIT_USER, "user cwid '"+pCwid+"' is active?: '"+user.getActive()+"'", null, null, null, null, null, null, null, null, null);            
                }
            } catch (Doc41ExceptionBase e) {
                throw new Doc41BusinessException(Doc41ErrorMessageKeys.USR_TOGGLE_ACTIVATION_FAILED, e);
            }
        }
        return user;
    }
    
	
    
    // GETTER
    public UserManagementRepository getUserManagementRepository() {
        return userMgmtRepo;
    }

}
