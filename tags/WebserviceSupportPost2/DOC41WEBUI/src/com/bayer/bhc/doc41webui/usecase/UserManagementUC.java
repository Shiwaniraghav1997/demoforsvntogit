/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.usecase;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.Doc41ErrorMessageKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.exception.Doc41RepositoryException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.logging.Doc41LogEntry;
import com.bayer.bhc.doc41webui.common.paging.PagingData;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.UserListFilter;
import com.bayer.bhc.doc41webui.container.UserPagingRequest;
import com.bayer.bhc.doc41webui.domain.PermissionProfiles;
import com.bayer.bhc.doc41webui.domain.Profile;
import com.bayer.bhc.doc41webui.domain.SapCustomer;
import com.bayer.bhc.doc41webui.domain.SapVendor;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.service.repository.UserManagementRepository;
import com.bayer.ecim.foundation.basic.StringTool;

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
        try {
            getUserManagementRepository().createUser(pUser);
            // logging
            Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(), "createUser() - user cwid '"+pUser.getCwid()+"'.");
            // audit
            logWebMetrix(pUser, "USER_CREATE");
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
    public void editUser(User pUser, boolean updateRoles,boolean updateLdap,boolean updatePartners,boolean updateCountries,boolean updatePlants) throws Doc41BusinessException {
        try {
            getUserManagementRepository().updateUser(pUser, updateRoles,updateLdap,updatePartners,updateCountries,updatePlants);
            // logging
            Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(), "editUser() - user cwid '"+pUser.getCwid()+"'.");
            // audit
            logWebMetrix(pUser, "USER_EDIT");
        } catch (Doc41ExceptionBase e) {
            throw new Doc41BusinessException(Doc41ErrorMessageKeys.USR_EDIT_FAILED, e);
        }
    }

    /**
	 * find user by cwid in Usermanagement DB, finds also deactivated or deleted users
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
     * Get All Profile(Beans).
     * @return
     * @throws Doc41TechnicalException
     */
    public List<Profile> getAllProfiles() throws Doc41TechnicalException {
        return getUserManagementRepository().getAllProfiles();
    }
    
    /**
     * Get the ordered List of all ProfileNames of currently existing profiles (not deleted)
     * @return
     * @throws Doc41TechnicalException
     */
    public List<String>getAllProfileNamesList() throws Doc41TechnicalException {
        return getUserManagementRepository().getAllProfileNamesList();
    }

    /**
     * Get the list of all ProfileNames of currently existing profiles (not deleted)
     * @return
     * @throws Doc41TechnicalException 
     */
    public HashMap<String,Profile> getAllProfilesMap() throws Doc41TechnicalException {
        return getUserManagementRepository().getAllProfilesMap();
    }
    
    /**
     * Get a list of Permissions with ProfileMap of assigned Profiles, only assigned Profiles included, value is Long(1).
     * @return
     * @throws Doc41TechnicalException
     */
    public List<PermissionProfiles> getPermissionProfiles() throws Doc41TechnicalException {
        return getUserManagementRepository().getPermissionProfiles();
    }

    /**
     * Get a list of all Permissions currently available.
     * @return
     * @throws Doc41TechnicalException
     */
    public List<PermissionProfiles> getAllPermissions() throws Doc41TechnicalException {
        return getUserManagementRepository().getAllPermissions();
    }

    /**
     * Check if user has a permissions requiring at least one Customer assigned.
     * @param pCwid
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsCustomers(String pCwid) throws Doc41TechnicalException {
        return getUserManagementRepository().userNeedsCustomers(pCwid);
    }

    /**
     * Check if user has a permissions requiring at least one Country assigned.
     * @param pCwid
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsCountries(String pCwid) throws Doc41TechnicalException {
        return getUserManagementRepository().userNeedsCountries(pCwid);
    }

    /**
     * Check if user has a permissions requiring at least one Vendor assigned.
     * @param pCwid
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsVendors(String pCwid) throws Doc41TechnicalException {
        return getUserManagementRepository().userNeedsVendors(pCwid);
    }

    /**
     * Check if user has a permissions requiring at least one Plant assigned.
     * @param pCwid
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsPlants(String pCwid) throws Doc41TechnicalException {
        return getUserManagementRepository().userNeedsPlants(pCwid);
    }
    
    
    /**
     * Check if user has a permissions requiring at least one Customer assigned.
     * @param pCwid
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsCustomers(String[] pProfiles) throws Doc41TechnicalException {
        return getUserManagementRepository().userNeedsCustomers(pProfiles);
    }

    /**
     * Check if user has a permissions requiring at least one Country assigned.
     * @param pProfiles
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsCountries(String[] pProfiles) throws Doc41TechnicalException {
        return getUserManagementRepository().userNeedsCountries(pProfiles);
    }

    /**
     * Check if user has a permissions requiring at least one Vendor assigned.
     * @param pProfiles
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsVendors(String[] pProfiles) throws Doc41TechnicalException {
        return getUserManagementRepository().userNeedsVendors(pProfiles);
    }

    /**
     * Check if user has a permissions requiring at least one Plant assigned.
     * @param pProfiles
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsPlants(String[] pProfiles) throws Doc41TechnicalException {
        return getUserManagementRepository().userNeedsPlants(pProfiles);
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
                	String wmAction;
					if (user.getActive() == null || user.getActive().booleanValue()) {
	                    user.setActive(Boolean.FALSE);
	                    wmAction="USER_DEACTIVATE";
	                } else {
	                    user.setActive(Boolean.TRUE);
	                    wmAction="USER_ACTIVATE";
	                }
	                getUserManagementRepository().updateUser(user, false,true,false,false,false);
	                // logging
	                Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(), "toggleUserActivation() - user cwid '"+pCwid+"'.");
	                // audit
	                logWebMetrix(user, wmAction);
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
    
    public SapCustomer checkCustomer(String customerNumber) throws Doc41BusinessException{
    	try {
    		SapCustomer customer = getUserManagementRepository().loadSAPCustomer(customerNumber);

			return customer;
		} catch (Doc41RepositoryException e) {
			throw new Doc41BusinessException("checkDeliveryForCustomer",e);
		}
    }
    
    public SapVendor checkVendor(String vendorNumber) throws Doc41BusinessException{
    	try {
    		SapVendor vendor = getUserManagementRepository().loadSAPVendor(vendorNumber);

			return vendor;
		} catch (Doc41RepositoryException e) {
			throw new Doc41BusinessException("checkDeliveryForVendor",e);
		}
    }
	
	private void logWebMetrix(User changedUser, String action){
		String loggedInUser = UserInSession.getCwid();
		String info = changedUser.getWebMetrixOutput();
		Doc41Log.get().logWebMetrix(this.getClass(),new Doc41LogEntry(loggedInUser, loggedInUser, "USER_MANAGEMENT", action, 
				StringTool.maxRString(info, 750, "..."), changedUser.getCwid(), null, null, null, null, null, null, null),loggedInUser);
	}

    public void addPermissionsToUser(User user)throws Doc41BusinessException{
        try {
            getUserManagementRepository().addPermissionsToUser(user);
        } catch (Doc41RepositoryException e) {
            throw new Doc41BusinessException("addPermissionsToUser",e);
        }
    }

}
