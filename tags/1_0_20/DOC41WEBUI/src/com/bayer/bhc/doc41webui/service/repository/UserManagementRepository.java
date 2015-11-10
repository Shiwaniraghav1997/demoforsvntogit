package com.bayer.bhc.doc41webui.service.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bbs.aila.model.AILAPerson;
import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.Doc41ErrorMessageKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41AccessDeniedException;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41RepositoryException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.UserPagingRequest;
import com.bayer.bhc.doc41webui.domain.SapCustomer;
import com.bayer.bhc.doc41webui.domain.SapVendor;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.integration.db.LdapDAO;
import com.bayer.bhc.doc41webui.integration.db.UserManagementDAO;
import com.bayer.bhc.doc41webui.integration.db.dc.SapCustomerDC;
import com.bayer.bhc.doc41webui.integration.db.dc.SapVendorDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UserCountryDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UserCustomerDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UserPlantDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UserVendorDC;
import com.bayer.bhc.doc41webui.service.mapping.UserMapper;
import com.bayer.ecim.foundation.basic.BasicDataCarrier;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.SendMail;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.dbx.QueryException;
import com.bayer.ecim.foundation.web.usermanagementN.UMPermissionNDC;
import com.bayer.ecim.foundation.web.usermanagementN.UMProfileNDC;
import com.bayer.ecim.foundation.web.usermanagementN.UMUserNDC;
import com.bayer.ecim.foundation.web.usermanagementN.UMUserProfileNDC;

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
	        UMUserNDC userDC = userManagementDAO.getUserByCWID(pCwid);
	        if (userDC == null ){
	            return null;
	        }
	        User domainUser = null;
	        domainUser = copyDcToDomainUser(userDC);
			if (domainUser.getLocale() == null) {
			    domainUser.setLocale(Locale.US);
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

			PagingResult<UMUserNDC> result = userManagementDAO.getUMUserNDCs(pUserPagingRequest);
			List<UMUserNDC> userDCList = result.getResult();
			List<User> domainUserList = new ArrayList<User>();
			for (UMUserNDC userDC : userDCList) {
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
        if(person==null){
            return null;
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
            UMUserNDC userDC = userManagementDAO.getUserByCWID(pUser.getCwid());
            
            
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
                	List<SapCustomer> customers = pUser.getCustomers();
                	if (customers != null) {
                		for (SapCustomer customer : customers) {                			
                			addCustomerToUserInDB(userDC, customer, pUser.getLocale());
                		}
                	}
                	List<SapVendor> vendors = pUser.getVendors();
                	if (vendors != null) {
                		for (SapVendor vendor : vendors) {                			
                			addVendorToUserInDB(userDC, vendor, pUser.getLocale());
                		}
                	}
                	List<String> countriesFromUserParam = pUser.getCountries();
                    if(countriesFromUserParam!=null){
                        Set<String> countriesFromInput = new LinkedHashSet<String>(countriesFromUserParam);
                        
                        for (String country : countriesFromInput) {
                            addCountryToUserInDB(userDC, country, pUser.getLocale());
                        }
                    }
                    List<String> plantsFromUserParam = pUser.getPlants();
                    if(plantsFromUserParam!=null){
                        Set<String> plantsFromInput = new LinkedHashSet<String>(plantsFromUserParam);
                        
                        for (String plant : plantsFromInput) {
                            addPlantToUserInDB(userDC, plant, pUser.getLocale());
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
        if (pUser.isExternalUser() && StringUtils.isNotBlank(pUser.getPassword())) {
            String body = "The new Password for "+pUser.getCwid()+" is '"+pUser.getPassword()+"'";
            try {
                SendMail.get().send("DOC41@bayer.com", pUser.getEmail(), "New Password", body);
            } catch (InitException e) {
                Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), "no mail send: "+body);
                Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), e);
            } catch (MessagingException e) {
                Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), "no mail send: "+body);
                Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), e);
            }
        }
	}

	/**
	 * Get the list of all ProfileNames of currently existing profiles (not deleted)
	 * @return
	 * @throws Doc41RepositoryException
	 */
	public HashSet<String>getAllProfileNames() throws Doc41RepositoryException {
        try {
            @SuppressWarnings("unchecked")
            HashSet<String> mRes = (HashSet<String>)BasicDataCarrier.getFieldHashSet(getUserManagementDAO().getAllProfiles(), UMProfileNDC.FIELD_PROFILENAME);
            return mRes;
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException("getAllProfileNames", e);
        }
	    
	}

	
	
	private void updateLdapGroup(User pUser) throws Doc41RepositoryException {
		try {
			addPermissionsToUser(pUser);
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
	        			if(pUser.hasPermission(Doc41Constants.PERMISSION_ADDINTERNALUSERTOLOGGROUP)){
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
	
	
	public void addPermissionsToUser(User pUser) throws Doc41RepositoryException {
		try{
			List<String> oldPermissions = pUser.getPermissions();
			if(oldPermissions!=null && !oldPermissions.isEmpty()){
				return;
			}
			if(StringTool.isTrimmedEmptyOrNull(pUser.getCwid())){
				throw new IllegalArgumentException("addPermissionsToUser: cwid is empty");
			}
			
			List<String> permissions = new ArrayList<String>();
			List<String> roles = pUser.getRoles();
			for (String roleName : roles) {
				UMProfileNDC profile = userManagementDAO.getProfileByName(roleName, pUser.getLocale());
				ArrayList<UMPermissionNDC> permissionDCs = userManagementDAO.getPermissionsByProfile(profile.getObjectID(),pUser.getLocale());
				for (UMPermissionNDC permissionDC : permissionDCs) {
		            permissions.add(permissionDC.getCode());
				}
			}
			pUser.setPermissions(permissions);
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("UserManagementRepository.addPermissionsToUser", e);
		}
	}
	public void updateUser(User pUser, boolean updateRoles,boolean updateLdap,boolean updatePartner,boolean updateCountries,boolean updatePlants) throws Doc41RepositoryException, Doc41BusinessException {
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
	                SendMail.get().send("DOC41@bayer.com", pUser.getEmail(), "New Password", body);
	            } catch (InitException e) {
	                Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), "no mail send: "+body);
	                Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), e);
	            } catch (MessagingException e) {
	                Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), "no mail send: "+body);
	                Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), e);
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
        UMUserNDC userDC = copyDomainToDCUser(pUser, false);
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
	        	List<UMProfileNDC> userProfiles = 
	    				userManagementDAO.getProfilesByUser(userDC.getObjectID());
	    		
	            List<String> roles = new ArrayList<String>();
	            for (UMProfileNDC profile : userProfiles) {                    
	                Doc41Log.get().debug(this.getClass(), "System", "getProfilename: " + profile.getProfilename());
	                roles.add(profile.getProfilename());
	            }
	    		pUser.setRoles(roles);		
	        }
        	
        	if (updatePartner) {
        		//Customers
	            List<SapCustomerDC> customersInDB = getCustomersFromDB(userDC.getObjectID());
	            Set<String> customersToDelete = new HashSet<String>();
	            for (SapCustomerDC sapCustomerDC : customersInDB) {
	            	customersToDelete.add(sapCustomerDC.getPartnerNumber());
				}
	            Set<SapCustomer> customersFromInput = new LinkedHashSet<SapCustomer>(pUser.getCustomers());
	            
	            for (SapCustomer customer : customersFromInput) {
					if(!customersToDelete.remove(customer.getNumber())){
						addCustomerToUserInDB(userDC, customer, pUser.getLocale());
					}
				}
	            
	            for (String customerToDelete : customersToDelete) {
					removeCustomerFromUserInDB(userDC, customerToDelete);
				}

	            //Vendors
	            List<SapVendorDC> vendorsInDB = getVendorsFromDB(userDC.getObjectID());
	            Set<String> vendorsToDelete = new HashSet<String>();
	            for (SapVendorDC sapVendorDC : vendorsInDB) {
	            	vendorsToDelete.add(sapVendorDC.getPartnerNumber());
				}
	            Set<SapVendor> vendorsFromInput = new LinkedHashSet<SapVendor>(pUser.getVendors());
	            
	            for (SapVendor vendor : vendorsFromInput) {
					if(!vendorsToDelete.remove(vendor.getNumber())){
						addVendorToUserInDB(userDC, vendor, pUser.getLocale());
					}
				}
	            
	            for (String vendorToDelete : vendorsToDelete) {
					removeVendorFromUserInDB(userDC, vendorToDelete);
				}
	        } else {
	        	// refresh customers
	        	List<SapCustomerDC> customersInDB = getCustomersFromDB(userDC.getObjectID());
	    		pUser.setCustomers(copyDcToDomainCustomers(customersInDB) );
	    		
	    		// refresh vendors
	        	List<SapVendorDC> vendorsInDB = getVendorsFromDB(userDC.getObjectID());
	    		pUser.setVendors(copyDcToDomainVendors(vendorsInDB) );
	        }
        	
        	if (updateCountries) {
	            List<UserCountryDC> countriesInDB = getCountriesFromDB(userDC.getObjectID());
	            Set<String> countriesToDelete = new HashSet<String>();
	            for (UserCountryDC usercountryDC : countriesInDB) {
	            	countriesToDelete.add(usercountryDC.getCountryIsoCode());
				}
	            List<String> countriesFromUserParam = pUser.getCountries();
	            if(countriesFromUserParam!=null){
					Set<String> countriesFromInput = new LinkedHashSet<String>(countriesFromUserParam);
		            
		            for (String country : countriesFromInput) {
						if(!countriesToDelete.remove(country)){
							addCountryToUserInDB(userDC, country, pUser.getLocale());
						}
					}
	            }
	            
	            for (String countryToDelete : countriesToDelete) {
					removeCountryFromUserInDB(userDC, countryToDelete);
				}
	        } else {
	        	// refresh countries
	        	List<UserCountryDC> countriesInDB = getCountriesFromDB(userDC.getObjectID());
	    		pUser.setCountries(copyDcToDomainCountries(countriesInDB) );		
	        }
        	
        	if (updatePlants) {
	            List<UserPlantDC> plantsInDB = getPlantsFromDB(userDC.getObjectID());
	            Set<String> plantsToDelete = new HashSet<String>();
	            for (UserPlantDC userplantDC : plantsInDB) {
	            	plantsToDelete.add(userplantDC.getPlant());
				}
	            List<String> plantsFromUserParam = pUser.getPlants();
	            if(plantsFromUserParam!=null){
					Set<String> plantsFromInput = new LinkedHashSet<String>(plantsFromUserParam);
		            
		            for (String plant : plantsFromInput) {
						if(!plantsToDelete.remove(plant)){
							addPlantToUserInDB(userDC, plant, pUser.getLocale());
						}
					}
	            }
	            
	            for (String plantToDelete : plantsToDelete) {
					removePlantFromUserInDB(userDC, plantToDelete);
				}
	        } else {
	        	// refresh plants
	        	List<UserPlantDC> plantsInDB = getPlantsFromDB(userDC.getObjectID());
	    		pUser.setPlants(copyDcToDomainPlants(plantsInDB) );		
	        }
        	
        } catch (Doc41TechnicalException e) {
            Doc41Log.get().error(this.getClass(), null, e);
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.USR_MGT_UPDATE_USER_FAILED, e);
        }
    }
	
	private List<SapCustomer> copyDcToDomainCustomers(
			List<SapCustomerDC> customersInDB) {
		List<SapCustomer> newCustomerList = new ArrayList<SapCustomer>();
		for (SapCustomerDC sapCustomerDC : customersInDB) {
			SapCustomer newCustomer = new SapCustomer();
			newCustomer.setNumber(sapCustomerDC.getPartnerNumber());
			newCustomer.setName1(sapCustomerDC.getName1());
			newCustomer.setName2(sapCustomerDC.getName2());
			newCustomerList.add(newCustomer );
		}
		return newCustomerList;
	}
	
	private List<SapVendor> copyDcToDomainVendors(
			List<SapVendorDC> vendorsInDB) {
		List<SapVendor> newVendorList = new ArrayList<SapVendor>();
		for (SapVendorDC sapVendorDC : vendorsInDB) {
			SapVendor newVendor = new SapVendor();
			newVendor.setNumber(sapVendorDC.getPartnerNumber());
			newVendor.setName1(sapVendorDC.getName1());
			newVendor.setName2(sapVendorDC.getName2());
			newVendorList.add(newVendor );
		}
		return newVendorList;
	}
	
	private List<String> copyDcToDomainCountries(
			List<UserCountryDC> countriesInDB) {
		List<String> newCountryList = new ArrayList<String>();
		for (UserCountryDC userCountryDC : countriesInDB) {
			String newCountry = userCountryDC.getCountryIsoCode();
			newCountryList.add(newCountry );
		}
		return newCountryList;
	}
	
	private List<String> copyDcToDomainPlants(
			List<UserPlantDC> plantsInDB) {
		List<String> newPlantsList = new ArrayList<String>();
		for (UserPlantDC userPlantDC : plantsInDB) {
			String newPlant = userPlantDC.getPlant();
			newPlantsList.add(newPlant );
		}
		return newPlantsList;
	}
		
	/**
     * create Map that contains the user's profileNames as key
     * and add the ObjectIds of the UserProfileDC as value
     * @return
	 * @throws Doc41TechnicalException 
     */
    protected Map<String, Long> getUserProfileMap(Long pUserObjectID) throws Doc41RepositoryException {
    	try {
	        List<UMProfileNDC> profilesOfUser = userManagementDAO.getProfilesByUser(pUserObjectID);
	        Map<String, Long> profileMap = new HashMap<String, Long>();
	        if (profilesOfUser != null) {
	            for (UMProfileNDC profile : profilesOfUser) {	     
	                profileMap.put(profile.getProfilename(), profile.getUserProfileId());
	            }
	        }
	        return profileMap;
	    } catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during getUserProfileMap.", e);
		}
    }
    
    
	

    protected void addUserToProfileInDB(UMUserNDC pUserDC, String roleName, Locale pLoc) throws Doc41RepositoryException {
        UMUserProfileNDC userProfileNDC;
        try {
        	User usr = UserInSession.get();
            userProfileNDC = userManagementDAO.createUserProfile(pLoc);
            UMProfileNDC profile = userManagementDAO.getProfileByName(roleName, pLoc);
            userProfileNDC.setUserId(pUserDC.getObjectID());
            userProfileNDC.setProfileId(profile.getObjectID());
            
            userProfileNDC.setChangedBy(usr.getCwid());
            userProfileNDC.setCreatedBy(usr.getCwid());
            
            userManagementDAO.saveUserProfile(userProfileNDC);
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException("UserManagementRepositoryImpl.addUserToProfileInDB", e);
        }
    }
    
   //----- customers
    
	private List<SapCustomerDC> getCustomersFromDB(Long objectID) throws Doc41RepositoryException {
		try {
			return userManagementDAO.getCustomersByUser(objectID);
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during getCustomersFromDB.", e);
		}
	}
	
	private void addCustomerToUserInDB(UMUserNDC userDC, SapCustomer customer,
			Locale locale) throws Doc41RepositoryException {
		try{
			SapCustomerDC sapCustomer = userManagementDAO.getSapCustomerByNumber(customer.getNumber());
			if(sapCustomer==null){
				throw new Doc41RepositoryException("customer "+customer.getNumber()+" not found", null);
			}
			User usr = UserInSession.get();
            UserCustomerDC newCustomer = userManagementDAO.createUserCustomer(locale);
			newCustomer.setUserId(userDC.getObjectID());
			newCustomer.setCustomerId(sapCustomer.getObjectID());
			newCustomer.setChangedBy(usr.getCwid());
			newCustomer.setCreatedBy(usr.getCwid());
			userManagementDAO.saveUserCustomer(newCustomer);
		}catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during addCustomerToUserInDB.", e);
		}
	}
	
	private void removeCustomerFromUserInDB(UMUserNDC userDC,
			String customerToDelete) throws Doc41RepositoryException {
		try{
			UserCustomerDC customer = userManagementDAO.getUserCustomer(userDC.getObjectID(),customerToDelete);
			userManagementDAO.deleteUserCustomer(customer);
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during removeCustomerFromUserInDB.", e);
		}
	}
	
	//----- vendors

	private List<SapVendorDC> getVendorsFromDB(Long objectID) throws Doc41RepositoryException {
		try {
			return userManagementDAO.getVendorsByUser(objectID);
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during getVendorsFromDB.", e);
		}
	}
	
	private void addVendorToUserInDB(UMUserNDC userDC, SapVendor vendor,
			Locale locale) throws Doc41RepositoryException {
		try{
			SapVendorDC sapVendor = userManagementDAO.getSapVendorByNumber(vendor.getNumber());
			if(sapVendor==null){
				throw new Doc41RepositoryException("vendor "+vendor.getNumber()+" not found", null);
			}
			User usr = UserInSession.get();
            UserVendorDC newVendor = userManagementDAO.createUserVendor(locale);
			newVendor.setUserId(userDC.getObjectID());
			newVendor.setVendorId(sapVendor.getObjectID());
			newVendor.setChangedBy(usr.getCwid());
			newVendor.setCreatedBy(usr.getCwid());
			userManagementDAO.saveUserVendor(newVendor);
		}catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during addVendorToUserInDB.", e);
		}
	}
	
	private void removeVendorFromUserInDB(UMUserNDC userDC,
			String vendorToDelete) throws Doc41RepositoryException {
		try{
			UserVendorDC vendor = userManagementDAO.getUserVendor(userDC.getObjectID(),vendorToDelete);
			userManagementDAO.deleteUserVendor(vendor);
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during removeVendorFromUserInDB.", e);
		}
	}
	
	//----- countries
	
	private List<UserCountryDC> getCountriesFromDB(Long objectID) throws Doc41RepositoryException {
		try {
			return userManagementDAO.getCountriesByUser(objectID);
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during getCountriesFromDB.", e);
		}
	}
	
	private void addCountryToUserInDB(UMUserNDC userDC, String country,
			Locale locale) throws Doc41RepositoryException {
		try{
			User usr = UserInSession.get();
            UserCountryDC newCountry = userManagementDAO.createUserCountry(locale);
			newCountry.setUserId(userDC.getObjectID());
			newCountry.setCountryIsoCode(country);
			newCountry.setChangedBy(usr.getCwid());
			newCountry.setCreatedBy(usr.getCwid());
			userManagementDAO.saveUserCountry(newCountry);
		}catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during addCountryToUserInDB.", e);
		}
	}
	
	private void removeCountryFromUserInDB(UMUserNDC userDC,
			String countryToDelete) throws Doc41RepositoryException {
		try{
			UserCountryDC country = userManagementDAO.getUserCountry(userDC.getObjectID(),countryToDelete);
			userManagementDAO.deleteUserCountry(country);
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during removeCountryFromUserInDB.", e);
		}
	}
	
	//----- countries
	
	private List<UserPlantDC> getPlantsFromDB(Long objectID) throws Doc41RepositoryException {
		try {
			return userManagementDAO.getPlantsByUser(objectID);
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during getPlantsFromDB.", e);
		}
	}
	
	private void addPlantToUserInDB(UMUserNDC userDC, String plant,
			Locale locale) throws Doc41RepositoryException {
		try{
			User usr = UserInSession.get();
            UserPlantDC newPlant = userManagementDAO.createUserPlant(locale);
			newPlant.setUserId(userDC.getObjectID());
			newPlant.setPlant(plant);
			newPlant.setChangedBy(usr.getCwid());
			newPlant.setCreatedBy(usr.getCwid());
			userManagementDAO.saveUserPlant(newPlant);
		}catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during addPlantToUserInDB.", e);
		}
	}
	
	private void removePlantFromUserInDB(UMUserNDC userDC,
			String plantToDelete) throws Doc41RepositoryException {
		try{
			UserPlantDC plant = userManagementDAO.getUserPlant(userDC.getObjectID(),plantToDelete);
			userManagementDAO.deleteUserPlant(plant);
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during removePlantFromUserInDB.", e);
		}
	}
	
	//-----
			
	protected User copyDcToDomainUser(UMUserNDC pUserDC) throws Doc41RepositoryException {
		try{
			if (pUserDC == null){
			    return null;
			}

			User domainUser = userMapper.mapToDomainObject(pUserDC, new User());
	 
			List<UMProfileNDC> userProfiles = 
					userManagementDAO.getProfilesByUser(pUserDC.getObjectID());
			
	        List<String> roles = new ArrayList<String>();
	        for (UMProfileNDC profile : userProfiles) {                    
	            Doc41Log.get().debug(this.getClass(), "System", "getProfilename: " + profile.getProfilename());
	            roles.add(profile.getProfilename());
	        }
			domainUser.setRoles(roles);
			
			List<SapCustomerDC> customersInDB = getCustomersFromDB(pUserDC.getObjectID());
			domainUser.setCustomers(copyDcToDomainCustomers(customersInDB));
			
			List<SapVendorDC> vendorsInDB = getVendorsFromDB(pUserDC.getObjectID());
			domainUser.setVendors(copyDcToDomainVendors(vendorsInDB));
			
			List<UserCountryDC> countriesInDB = getCountriesFromDB(pUserDC.getObjectID());
			domainUser.setCountries(copyDcToDomainCountries(countriesInDB));
			
			List<UserPlantDC> plantsInDB = getPlantsFromDB(pUserDC.getObjectID());
			domainUser.setPlants(copyDcToDomainPlants(plantsInDB));
			
			pUserDC.loadResolvedUserPermissions(LocaleInSession.get());
			@SuppressWarnings("unchecked")
			List<UMPermissionNDC> permissionDCs = pUserDC.getResolvedUserPermissions();
			List<String> permissions = new ArrayList<String>();
			for (UMPermissionNDC permissionDC : permissionDCs) {                    
	            Doc41Log.get().debug(this.getClass(), "System", "getPermission Code: " + permissionDC.getCode());
	            permissions.add(permissionDC.getCode());
	        }
			domainUser.setPermissions(permissions);
			
			return domainUser;
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("UserManagementRepository.copyDcToDomainUser", e);
		} catch (QueryException e) {
			throw new Doc41RepositoryException("UserManagementRepository.copyDcToDomainUser", e);
		}
	}
	
	
	protected UMUserNDC copyDomainToDCUser(User pDomainUser, boolean pIsNewUser) throws Doc41RepositoryException {        
        Doc41Log.get().debug(this.getClass(), "System", "Entering copyDomainToDCUser(): "+ pDomainUser);
        UMUserNDC userDC = null;
        
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

	public SapCustomer loadSAPCustomer(String customerNumber) throws Doc41RepositoryException {
		try {
			Doc41Log.get().debug(this.getClass(), "System", "Entering UserManagementRepositoryImpl.loadSAPCustomer(): " + customerNumber);
			SapCustomerDC customerDC = userManagementDAO.getSapCustomerByNumber(customerNumber);
			if(customerDC==null){
			    return null;
			}
			SapCustomer domainCustomer = null;
			List<SapCustomer> domainCustomerList = copyDcToDomainCustomers(Collections.singletonList(customerDC));
			domainCustomer=domainCustomerList.get(0);

			Doc41Log.get().debug(this.getClass(), "System", "Exiting UserManagementRepositoryImpl.loadSAPCustomer(): " + customerNumber);
			return domainCustomer;
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during loadSAPCustomer.", e);
		}
	}
	
	public SapVendor loadSAPVendor(String vendorNumber) throws Doc41RepositoryException {
		try {
			Doc41Log.get().debug(this.getClass(), "System", "Entering UserManagementRepositoryImpl.loadSAPVendor(): " + vendorNumber);
			SapVendorDC vendorDC = userManagementDAO.getSapVendorByNumber(vendorNumber);
			if(vendorDC==null){
			    return null;
			}
			SapVendor domainVendor = null;
			List<SapVendor> domainVendorList = copyDcToDomainVendors(Collections.singletonList(vendorDC));
			domainVendor=domainVendorList.get(0);

			Doc41Log.get().debug(this.getClass(), "System", "Exiting UserManagementRepositoryImpl.loadSAPVendor(): " + vendorNumber);
			return domainVendor;
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during loadSAPVendor.", e);
		}
	}
	
	
}
