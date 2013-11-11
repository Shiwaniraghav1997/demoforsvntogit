package com.bayer.bhc.doc41webui.service.repository;

import java.util.ArrayList;
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
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.domain.UserPartner;
import com.bayer.bhc.doc41webui.integration.db.LdapDAO;
import com.bayer.bhc.doc41webui.integration.db.UserManagementDAO;
import com.bayer.bhc.doc41webui.integration.db.dc.UserCountryDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UserPartnerDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UserPlantDC;
import com.bayer.bhc.doc41webui.service.mapping.UserMapper;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.SendMail;
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
                	List<UserPartner> partners = pUser.getPartners();
                	if (partners != null) {
                		for (UserPartner partner : partners) {                			
                			addPartnerToUserInDB(userDC, partner, pUser.getLocale());
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
                    SendMail.get().send("DOC41@bayer.com", pUser.getEmail(), "New Password", body);
                    
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
	            List<UserPartnerDC> partnersInDB = getPartnersFromDB(userDC.getObjectID());
	            Set<String> partnersToDelete = new HashSet<String>();
	            for (UserPartnerDC userPartnerDC : partnersInDB) {
	            	partnersToDelete.add(userPartnerDC.getPartnerNumber());
				}
	            Set<UserPartner> partnersFromInput = new LinkedHashSet<UserPartner>(pUser.getPartners());
	            
	            for (UserPartner partner : partnersFromInput) {
					if(!partnersToDelete.remove(partner.getPartnerNumber())){
						addPartnerToUserInDB(userDC, partner, pUser.getLocale());
					}
				}
	            
	            for (String partnerToDelete : partnersToDelete) {
					removePartnerFromUserInDB(userDC, partnerToDelete, pUser.getLocale());
				}
	        } else {
	        	// refresh partners
	        	List<UserPartnerDC> partnersInDB = getPartnersFromDB(userDC.getObjectID());
	    		pUser.setPartners(copyDcToDomainPartners(partnersInDB) );		
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
					removeCountryFromUserInDB(userDC, countryToDelete, pUser.getLocale());
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
					removePlantFromUserInDB(userDC, plantToDelete, pUser.getLocale());
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
	private List<UserPartner> copyDcToDomainPartners(
			List<UserPartnerDC> partnersInDB) {
		List<UserPartner> newPartnerList = new ArrayList<UserPartner>();
		for (UserPartnerDC userPartnerDC : partnersInDB) {
			UserPartner newPartner = new UserPartner();
			newPartner.setPartnerNumber(userPartnerDC.getPartnerNumber());
			newPartner.setPartnerName1(userPartnerDC.getPartnerName1());
			newPartner.setPartnerName2(userPartnerDC.getPartnerName2());
			newPartner.setPartnerType(userPartnerDC.getPartnerType());
			newPartnerList.add(newPartner );
		}
		return newPartnerList;
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
    
   //----- partners
    
	private List<UserPartnerDC> getPartnersFromDB(Long objectID) throws Doc41RepositoryException {
		try {
			List<UserPartnerDC> partners = userManagementDAO.getPartnersByUser(objectID);
			return partners;
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during getPartnersFromDB.", e);
		}
	}
	
	private void addPartnerToUserInDB(UMUserNDC userDC, UserPartner partner,
			Locale locale) throws Doc41RepositoryException {
		try{
			User usr = UserInSession.get();
            UserPartnerDC newPartner = userManagementDAO.createUserPartner(locale);
			newPartner.setUserId(userDC.getObjectID());
			newPartner.setPartnerNumber(partner.getPartnerNumber());
			newPartner.setPartnerName1(partner.getPartnerName1());
			newPartner.setPartnerName2(partner.getPartnerName2());
			newPartner.setPartnerType(partner.getPartnerType());
			newPartner.setChangedBy(usr.getCwid());
			newPartner.setCreatedBy(usr.getCwid());
			userManagementDAO.saveUserPartner(newPartner);
		}catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during addPartnerToUserInDB.", e);
		}
	}
	
	private void removePartnerFromUserInDB(UMUserNDC userDC,
			String partnerToDelete, Locale locale) throws Doc41RepositoryException {
		try{
			UserPartnerDC partner = userManagementDAO.getUserPartner(userDC.getObjectID(),partnerToDelete);
			userManagementDAO.deleteUserPartner(partner);
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during removePartnerFromUserInDB.", e);
		}
	}
	
	//----- countries
	
	private List<UserCountryDC> getCountriesFromDB(Long objectID) throws Doc41RepositoryException {
		try {
			List<UserCountryDC> countries = userManagementDAO.getCountriesByUser(objectID);
			return countries;
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
			String countryToDelete, Locale locale) throws Doc41RepositoryException {
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
			List<UserPlantDC> plants = userManagementDAO.getPlantsByUser(objectID);
			return plants;
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
			String plantToDelete, Locale locale) throws Doc41RepositoryException {
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
			if (pUserDC == null) return null;

			User domainUser = userMapper.mapToDomainObject(pUserDC, new User());
	 
			List<UMProfileNDC> userProfiles = 
					userManagementDAO.getProfilesByUser(pUserDC.getObjectID());
			
	        List<String> roles = new ArrayList<String>();
	        for (UMProfileNDC profile : userProfiles) {                    
	            Doc41Log.get().debug(this.getClass(), "System", "getProfilename: " + profile.getProfilename());
	            roles.add(profile.getProfilename());
	        }
			domainUser.setRoles(roles);
			
			List<UserPartnerDC> partnersInDB = getPartnersFromDB(pUserDC.getObjectID());
			domainUser.setPartners(copyDcToDomainPartners(partnersInDB));
			
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
	
}
