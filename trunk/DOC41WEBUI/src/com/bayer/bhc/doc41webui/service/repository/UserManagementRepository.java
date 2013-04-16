package com.bayer.bhc.doc41webui.service.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bbs.aila.model.AILAPerson;
import com.bayer.bhc.doc41webui.common.Doc41ErrorMessageKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41AccessDeniedException;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41RepositoryException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.UserPagingRequest;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.integration.db.LdapDAO;
import com.bayer.bhc.doc41webui.integration.db.UserManagementDAO;
import com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN.Doc41ProfileNDC;
import com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN.Doc41UserNDC;
import com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN.Doc41UserProfileNDC;
import com.bayer.bhc.doc41webui.service.mapping.UserMapper;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.SendMail;

/**
 * @author ezzqc
 * @author evfpu
 *
 */
@Component
public class UserManagementRepository extends AbstractRepository {
	@Autowired
	private UserManagementDAO userManagementDAO;
	@Autowired
	private LdapDAO ldapDAO;
	@Autowired
	private UserMapper userMapper;
	
	public LdapDAO getLdapDAO() {
		return ldapDAO;
	}
	public void setLdapDAO(LdapDAO ldapDAO) {
		this.ldapDAO = ldapDAO;
	}
	public UserManagementDAO getUserManagementDAO() {
		return userManagementDAO;
	}
	public void setUserManagementDAO(UserManagementDAO userManagementDAO) {
		this.userManagementDAO = userManagementDAO;
	}
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
    /**
     * Observers do not have permissions to store and delete functions.
     * 
     * @throws Doc41AccessDeniedException
     */
    private void checkUser(String errorMsg) throws Doc41RepositoryException {
        if (UserInSession.isReadOnly()) {
            throw new Doc41RepositoryException(errorMsg, new Doc41AccessDeniedException(this.getClass()));
        }
    }
    
    public User getUserByCwid(final String pCwid) throws Doc41RepositoryException {
    	try {
	        Doc41Log.get().debug(this.getClass(), "System", "Entering UserManagementRepositoryImpl.getUserByCwid(): " + pCwid);
	        Doc41UserNDC userDC = userManagementDAO.getUserByCWID(pCwid);
	        if (userDC == null ) return null;
	        User domainUser = null;
	        domainUser = copyDcToDomainUser(userDC);
			if (domainUser.getLocale() == null) {
//				if ("de".equals(pLoc.getLanguage())) {
//					domainUser.setLanguage(Locale.GERMAN.getLanguage());
//				} else {
					domainUser.setLocale(Locale.US);
//				}
			}
	        Doc41Log.get().debug(this.getClass(), "System", "Exiting UserManagementRepositoryImpl.getUserByCwid(): " + domainUser);
			return domainUser;
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during getUserByCwid.", e);
		}
	}

	
	public PagingResult<User> getUsers(final UserPagingRequest pUserPagingRequest) throws Doc41RepositoryException {
		try{
			Doc41Log.get().debug(this.getClass(), "System", "Entering UserManagementRepositoryImpl.getUsers()");        

			PagingResult<Doc41UserNDC> result = userManagementDAO.getDoc41UserNDCs(pUserPagingRequest);
			List<Doc41UserNDC> userDCList = result.getResult();
			List<User> domainUserList = new ArrayList<User>();
			for (Doc41UserNDC userDC : userDCList) {
				User user = copyDcToDomainUser(userDC);
				domainUserList.add(user);
			}        
			PagingResult<User> pagingResult = new PagingResult<User>();
			pagingResult.setResult(domainUserList);
			pagingResult.setTotalSize(result.getTotalSize());
			Doc41Log.get().debug(this.getClass(), "System", "Exiting UserManagementRepositoryImpl.getUsers(): ");
			return pagingResult;
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("UserManagementRepository.getUsers", e);
		}
	}
	
	public User lookupUser(final String pCwid) throws Doc41RepositoryException {
		AILAPerson person = null;
        try {
            person = getLdapDAO().lookupUser(pCwid);
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_LDAP_LOOKUPUSER_FAILED, e);
        }
		User domainUser = userMapper.mapToDomainObject(person, new User());
		domainUser.setType(User.TYPE_INTERNAL);
		
		return domainUser;
	}
	
    public boolean isAuthenticated(String cwid, String password) throws Doc41RepositoryException {
        boolean isAuth = false;
        try {
            isAuth = getLdapDAO().isInternalUserAuthenticated(cwid, password);
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_LDAP_AUTHINTUSER_FAILED, e);
        }
        return isAuth;
    }	
    
	public void createUser(User pUser) throws Doc41RepositoryException, Doc41BusinessException {
        checkUser(Doc41ErrorMessageKeys.USR_MGT_INSERT_USER_FAILED);
        
        //if (pUser.isExternalUser()) return;
        		
        Doc41Log.get().debug(this.getClass(), "System", "Entering UserManagementRepositoryImpl.createUser(): " + pUser);
        // LDAP part
        
        if (pUser.isExternalUser()) {
            try {
            	AILAPerson person = userMapper.mapToLdapDataCarrier(pUser, new AILAPerson());
                person = getLdapDAO().createUser(person);
                pUser.setCwid(person.getCwid());
                Doc41Log.get().debug(this.getClass(), "System", "Person created: " + person);
            } catch (Doc41TechnicalException e) {
                throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_LDAP_CREATEUSER_FAILED, e);
            }
            
            if (StringUtils.isNotBlank(pUser.getPassword())) {
                try {
                    getLdapDAO().setPassword(pUser.getCwid(), pUser.getPassword());
                } catch (Doc41TechnicalException e) {
                	Doc41Log.get().error(this.getClass(), "System", e);
                    throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_LDAP_SETPASSWORD_FAILED, e);
                }
            }
        }
        
        updateLdapGroup(pUser);
        
        // UserManagement DB part
        try {
            Doc41UserNDC userDC = userManagementDAO.getUserByCWID(pUser.getCwid());
            
            
            if (userDC == null) {
                userDC = copyDomainToDCUser(pUser, true);
    		
                userDC = userManagementDAO.insertUser(userDC);
                if(userDC!=null){
                	pUser.setDcId(userDC.getObjectID());
                    List<String> roles = pUser.getRoles();
                	if (roles != null) {
                		for (String roleName : roles) {                			
                			addUserToProfileInDB(userDC, roleName, pUser.getLocale());
                		}
                	}
                }
            } else {
                // if user already exists, update the activity status:
            	userManagementDAO.updateUser(copyDomainToDCUser(pUser, false));
            }
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_INSERT_USER_FAILED, e);
        }

        // Password notification part for external user - inform user:
        if (pUser.isExternalUser()) {
            if (StringUtils.isNotBlank(pUser.getPassword())) {
                String body = "The new Password for "+pUser.getCwid()+" is '"+pUser.getPassword()+"'";
                try {
                    SendMail.get().send("BOE@bayer.com", pUser.getEmail(), "New Password", body);
                    
                } catch (InitException e) {
                    Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), "no mail send: "+body);
                } catch (MessagingException e) {
                    Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), "no mail send: "+body);
                }
            }
        }
	}
	private void updateLdapGroup(User pUser) throws Doc41RepositoryException {
		try {
        	if(pUser.getActive()!=null){
	        	if (pUser.getType().equals(User.TYPE_EXTERNAL)) {
	        		if(pUser.getActive()){
	        			getLdapDAO().addExternalUserToGroup(pUser.getCwid());
	        		} else {
	        			getLdapDAO().removeExternalUserFromGroup(pUser.getCwid());
	        		}
	        	} else {
	        		if(pUser.getActive()){
	        			getLdapDAO().addInternalUserToGroup(pUser.getCwid());
	        			if(pUser.isTechnicalAdmin()){
	        				getLdapDAO().addInternalUserToLogGroup(pUser.getCwid());
	        			} else {
	        				getLdapDAO().removeInternalUserFromLogGroup(pUser.getCwid());
	        			}
	        		} else{
	        			getLdapDAO().removeInternalUserFromGroup(pUser.getCwid());
	        			getLdapDAO().removeInternalUserFromLogGroup(pUser.getCwid());
	        		}
	        	}
        	}
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_LDAP_UPDATEUSERGROUPS_FAILED, e);
        }
	}
	
	
	public void updateUser(User pUser, boolean updateRoles,boolean updateLdap) throws Doc41RepositoryException, Doc41BusinessException {
        checkUser(Doc41ErrorMessageKeys.USR_MGT_UPDATE_USER_FAILED);
        
        Doc41Log.get().debug(this.getClass(), "System", "Entering UserManagementRepositoryImpl.updateUser(): " + pUser);
        
        // LDAP part:
        if (pUser.isExternalUser()) {
        	if (StringUtils.isNotBlank(pUser.getPassword())) {
	        	// LDAP part:
	        	try {
	        		getLdapDAO().setPassword(pUser.getCwid(), pUser.getPassword());
	            } catch (Doc41TechnicalException e) {
	            	Doc41Log.get().error(this.getClass(), "System", e);
	                throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_LDAP_SETPASSWORD_FAILED, e);
	            }
	            
	            // inform external user:
	            String body = "The new Password for "+pUser.getCwid()+" is '"+pUser.getPassword()+"'";
	            try {
	                SendMail.get().send("BOE@bayer.com", pUser.getEmail(), "New Password", body);
	            } catch (InitException e) {
	                Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), "no mail send: "+body);
	            } catch (MessagingException e) {
	                Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), "no mail send: "+body);
	            }
	        }
        
        	Doc41Log.get().debug(
        			this.getClass(),"System","UserManagementRepositoryImpl.updateUser(): performing LDAP update for person"+ pUser.getCwid());
        	try {
        		getLdapDAO().updateUser(userMapper.mapToLdapDataCarrier(pUser, new AILAPerson()));
        	} catch (Doc41TechnicalException e) {
        		throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_LDAP_UPDATEUSER_FAILED, e);
        	}
        }
        if (updateLdap) {
        	updateLdapGroup(pUser);
        }
        
     	// UserManagement DB part
        Doc41UserNDC userDC = copyDomainToDCUser(pUser, false);
        try {
        	userManagementDAO.updateUser(userDC);
        } catch (Doc41TechnicalException e) {
            Doc41Log.get().error(this.getClass(), "System", e);
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_UPDATE_USER_FAILED, e);
        }
        
        try {
        	if (updateRoles) {
	            Map<String, Long> userProfileMap = getUserProfileMap(userDC.getObjectID());
	
	            for (int i = 0; i < User.ALL_ROLES.length; i++) {
	                if ((pUser.getRoles() != null) && pUser.getRoles().contains(User.ALL_ROLES[i])) {
	                    // add role
	                    if (!userProfileMap.containsKey(User.ALL_ROLES[i])) {
	                        addUserToProfileInDB(userDC, User.ALL_ROLES[i], pUser.getLocale());
	                    }
	                } else {
	                    // remove role
	                    if (userProfileMap.containsKey(User.ALL_ROLES[i])) {
	                        // profilesOfUser contains DC that know the ObjectIds of the UserProfileDC
	                    	userManagementDAO.removeUserProfile((Long) userProfileMap
	                                .get(User.ALL_ROLES[i]));
	                    }
	                }
	            }
	            
	        } else {
	        	// refresh roles
	        	List<Doc41ProfileNDC> userProfiles = 
	    				userManagementDAO.getProfilesByUser(userDC.getObjectID());
	    		
	            List<String> roles = new ArrayList<String>();
	            for (Doc41ProfileNDC profile : userProfiles) {                    
	                Doc41Log.get().debug(this.getClass(), "System", "getProfilename: " + profile.getProfilename());
	                roles.add(profile.getProfilename());
	            }
	    		pUser.setRoles(roles);		
	        }
        	
        } catch (Doc41TechnicalException e) {
            Doc41Log.get().error(this.getClass(), null, e);
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_UPDATE_USER_FAILED, e);
        }
    }
		

	/**
     * create Map that contains the user's profileNames as key
     * and add the ObjectIds of the UserProfileDC as value
     * @return
	 * @throws Doc41TechnicalException 
     */
    protected Map<String, Long> getUserProfileMap(Long pUserObjectID) throws Doc41RepositoryException {
    	try {
	        List<Doc41ProfileNDC> profilesOfUser = userManagementDAO.getProfilesByUser(pUserObjectID);
	        Map<String, Long> profileMap = new HashMap<String, Long>();
	        if (profilesOfUser != null) {
	            for (Doc41ProfileNDC profile : profilesOfUser) {	     
	                profileMap.put(profile.getProfilename(), profile.getUserProfileId());
	            }
	        }
	        return profileMap;
	    } catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during getUserProfileMap.", e);
		}
    }
    
    
	

    protected void addUserToProfileInDB(Doc41UserNDC pUserDC, String roleName, Locale pLoc) throws Doc41RepositoryException {
        Doc41UserProfileNDC userProfileNDC;
        try {
        	User usr = UserInSession.get();
            userProfileNDC = userManagementDAO.createUserProfile(pLoc);
            Doc41ProfileNDC profile = userManagementDAO.getProfileByName(roleName, pLoc);
            userProfileNDC.setUserId(pUserDC.getObjectID());
            userProfileNDC.setProfileId(profile.getObjectID());
            
            userProfileNDC.setChangedBy(usr.getCwid());
            userProfileNDC.setCreatedBy(usr.getCwid());
            
            userManagementDAO.saveUserProfile(userProfileNDC);
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException("UserManagementRepositoryImpl.addUserToProfileInDB", e);
        }
    }
			
	protected User copyDcToDomainUser(Doc41UserNDC pUserDC) throws Doc41RepositoryException {
		try{
			if (pUserDC == null) return null;

			User domainUser = userMapper.mapToDomainObject(pUserDC, new User());
	 
			List<Doc41ProfileNDC> userProfiles = 
					userManagementDAO.getProfilesByUser(pUserDC.getObjectID());
			
	        List<String> roles = new ArrayList<String>();
	        for (Doc41ProfileNDC profile : userProfiles) {                    
	            Doc41Log.get().debug(this.getClass(), "System", "getProfilename: " + profile.getProfilename());
	            roles.add(profile.getProfilename());
	        }
			domainUser.setRoles(roles);		
			
			return domainUser;
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("UserManagementRepository.copyDcToDomainUser", e);
		}
	}
	
	
	protected Doc41UserNDC copyDomainToDCUser(User pDomainUser, boolean pIsNewUser) throws Doc41RepositoryException {        
        Doc41Log.get().debug(this.getClass(), "System", "Entering copyDomainToDCUser(): "+ pDomainUser);
        Doc41UserNDC userDC = null;
        
		if (pDomainUser != null && pDomainUser.getCwid() != null) {
	        
			if (pIsNewUser) {
                Doc41Log.get().debug(this.getClass(), "System", "isNewUser ...");
                try {
                    userDC = userManagementDAO.createUser(pDomainUser.getLocale());
                } catch (Exception e) {
                    throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_COPY_USER_DOMAIN_DC_FAILED, e);
                }
                userDC.setCwid(pDomainUser.getCwid());
			} else {
                Doc41Log.get().debug(this.getClass(), "System", " NOT isNewUser ...");
                // get user from DB to get all existing attributes
                try {
                    userDC = userManagementDAO.getUserByCWID(pDomainUser.getCwid());
                } catch (Doc41TechnicalException e) {
                    throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_COPY_USER_DOMAIN_DC_FAILED, e);
                }
                if (userDC == null){
                    throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_USER_NOT_FOUND_UPDATE_FAILED, null);
                }
			}
			
			userDC = userMapper.mapToDataCarrier(pDomainUser, userDC);            			
			
            Doc41Log.get().debug(this.getClass(), "System", "UserDC to store / update:" + userDC);
		}
		return userDC;
	}
	
}
