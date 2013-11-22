package com.bayer.bhc.doc41webui.integration.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.container.UserPagingRequest;
import com.bayer.bhc.doc41webui.integration.db.dc.ProfilePermissionDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UserCountryDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UserPartnerDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UserPlantDC;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.dbx.DeleteException;
import com.bayer.ecim.foundation.dbx.QueryException;
import com.bayer.ecim.foundation.dbx.ResultObject;
import com.bayer.ecim.foundation.dbx.StoreException;
import com.bayer.ecim.foundation.web.usermanagementN.OTUserManagementN;
import com.bayer.ecim.foundation.web.usermanagementN.UMPermissionNDC;
import com.bayer.ecim.foundation.web.usermanagementN.UMProfileNDC;
import com.bayer.ecim.foundation.web.usermanagementN.UMUserNDC;
import com.bayer.ecim.foundation.web.usermanagementN.UMUserProfileNDC;
import com.bayer.ecim.foundation.web.usermanagementN.UM_CONSTS_N;

/**
 * @author EVFPU
 *
 */
@Component
public class UserManagementDAO extends AbstractDAOImpl {
	
	private static final String TEMPLATE_COMPONENT_NAME	= "userManagement";	
	
	private static final String GET_PARTNERS_BY_USER		= "getPartnersByUser";
	private static final String GET_USER_PARTNER			= "getUserPartner";
	private static final String GET_COUNTRIES_BY_USER		= "getCountriesByUser";
	private static final String GET_USER_COUNTRY			= "getUserCountry";
	private static final String GET_PLANTS_BY_USER			= "getPlantsByUser";
	private static final String GET_USER_PLANT				= "getUserPlant";
	private static final String GET_PROFILE_PERMISSIONS		= "getProfilePermissions";
	
	@Override
	public String getTemplateComponentName() {		
		return TEMPLATE_COMPONENT_NAME;
	}
	
//	/**
//     * Observers do not have permissions to store and delete functions.
//     * 
//     * @throws Doc41AccessDeniedException
//     */
//	private void checkUser() throws Doc41TechnicalException {
//        if (UserInSession.isReadOnly()) {
//            throw new Doc41AccessDeniedException(this.getClass());
//        }
//    }
    
	public UMProfileNDC getProfileByName(String pName, Locale pLocale) throws Doc41TechnicalException {
		try {
	        UMProfileNDC profile = null;
	        ResultObject profiles = OTUserManagementN.get().getProfiles(null, null, pName, -1, -1, null, null, null, null, pLocale);
	        if (profiles != null && profiles.getResult() != null && profiles.getResult().size() > 0) {
	            // get profile by name should return a unique result
	            profile = (UMProfileNDC) profiles.getResult().get(0);
	        }
	        return profile;
		} catch (QueryException e) {
			throw new Doc41TechnicalException(this.getClass(), "Error during getProfileByName.", e);
		}
    }
    
	public void saveUserProfile(UMUserProfileNDC pProfile) throws Doc41TechnicalException {
        checkUser();
        
        try {
            OTUserManagementN.get().storeDC(pProfile);
        } catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "saveUserProfile", e);
        }
    }
	
	public void updateUser(final UMUserNDC pUserDC) throws Doc41TechnicalException {
        checkUser();
        
		try {
            OTUserManagementN.get().storeDC(pUserDC);
            
		} catch (InitException e) {
            throw new Doc41TechnicalException(this.getClass(), "updateUser", e);
		} catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "updateUser", e);
		} 
	}
    
	

	public void removeUserProfile(Long pObjectId) throws Doc41TechnicalException {
        checkUser();
        try {
            OTUserManagementN.get().deleteDCById(new UMUserProfileNDC(), pObjectId);
        } catch (DeleteException e) {
            throw new Doc41TechnicalException(this.getClass(), "removeUserProfile", e);
        }
    }
    
	public UMUserNDC insertUser(final UMUserNDC pUserDC) throws Doc41TechnicalException {
        UMUserNDC userDC = null;
        checkUser();
        
		try {
            userDC = (UMUserNDC) OTUserManagementN.get().storeDC(pUserDC);
		} catch (InitException e) {
            throw new Doc41TechnicalException(this.getClass(), "insertUser", e);
		} catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "insertUser", e);
		}
        return userDC;
	}
   
	public PagingResult<UMUserNDC> getUMUserNDCs(UserPagingRequest pUserRequest) throws Doc41TechnicalException {
       Long objectState = null;
        if (Boolean.TRUE.equals(pUserRequest.getIsActive())) {
            objectState = UM_CONSTS_N.STATEACTIVE;
        } else if(Boolean.FALSE.equals(pUserRequest.getIsActive())) {
            objectState = UM_CONSTS_N.STATEINACTIVE;
        }        
        try {
            // default ordering
            String orderBy = StringTool.isTrimmedEmptyOrNull(pUserRequest.getOrderBy()) ? "LASTNAME" : pUserRequest.getOrderBy();
            ResultObject resultObject = null;
            if (!StringTool.isTrimmedEmptyOrNull(pUserRequest.getRole()) && StringTool.isTrimmedEmptyOrNull(pUserRequest.getCwid())) {
                UMProfileNDC profileNDC = getProfileByName(pUserRequest.getRole(), LocaleInSession.get());
                resultObject = OTUserManagementN.get().getUsersByProfile(profileNDC.getObjectID(),pUserRequest.getFirstname(), pUserRequest.getSurname(), pUserRequest.getEmail(), objectState, pUserRequest.getIsExternal(), pUserRequest.getStartIndex(), pUserRequest.getEndIndex(), pUserRequest.getCompany(), null, orderBy, new Long(pUserRequest.getTotalSize()), LocaleInSession.get());
                Doc41Log.get().debug(this.getClass(), "System", "getUsers(...) getUsersByProfile returned (hitCount): " + resultObject.getHitCount());
            } else {
                // company as quicksearch:
                resultObject = OTUserManagementN.get().getUsers(pUserRequest.getCwid(), null, pUserRequest.getFirstname(), pUserRequest.getSurname(), pUserRequest.getEmail(), objectState, null, pUserRequest.getIsExternal(), pUserRequest.getStartIndex(), pUserRequest.getEndIndex(), pUserRequest.getCompany(), null, orderBy, new Long(pUserRequest.getTotalSize()), LocaleInSession.get());
            }
            @SuppressWarnings("unchecked")
			List<UMUserNDC> userDCList = resultObject.getDCListResult();
            
            PagingResult<UMUserNDC> result = new PagingResult<UMUserNDC>();
            result.setResult(userDCList);
            if (resultObject.getHitCount() != null) {
            	result.setTotalSize(resultObject.getHitCount().intValue());
            }
            
            
            return result;
        } catch (InitException e) {
            throw new Doc41TechnicalException(this.getClass(), "getUsers", e);
        } catch (QueryException e) {
            throw new Doc41TechnicalException(this.getClass(), "getUsers", e);
        }
	}
	
	public UMUserNDC getUserByCWID(final String pCwid) throws Doc41TechnicalException {
        UMUserNDC user = null;
		try {
			user = OTUserManagementN.get().getUserByCWID(pCwid, Locale.ENGLISH);
		} catch (InitException e) {
            throw new Doc41TechnicalException(this.getClass(), "getUserByCwid", e);
		} catch (QueryException e) {
            throw new Doc41TechnicalException(this.getClass(), "getUserByCwid", e);
		}
		return user;
	}
	
	public UMUserNDC createUser(Locale pLoc) throws Doc41TechnicalException {
        UMUserNDC user = null;
        checkUser();        
        try {
            user = OTUserManagementN.get().createUserDC(pLoc);
        } catch (InstantiationException e) {
            throw new Doc41TechnicalException(this.getClass(), "createUser", e);
        } catch (IllegalAccessException e) {
            throw new Doc41TechnicalException(this.getClass(), "createUser", e);
        } catch (ClassNotFoundException e) {
            throw new Doc41TechnicalException(this.getClass(), "createUser", e);
        }
        return user;
    }
	
	public UMUserProfileNDC createUserProfile(Locale pLoc) throws Doc41TechnicalException {
        UMUserProfileNDC userProfile = null;
        checkUser();        
        try {
            userProfile = OTUserManagementN.get().createUserProfileDC(pLoc);
        } catch (InitException e) {
            throw new Doc41TechnicalException(this.getClass(), "createUserProfile", e);
        } catch (InstantiationException e) {
            throw new Doc41TechnicalException(this.getClass(), "createUserProfile", e);
        } catch (IllegalAccessException e) {
            throw new Doc41TechnicalException(this.getClass(), "createUserProfile", e);
        } catch (ClassNotFoundException e) {
            throw new Doc41TechnicalException(this.getClass(), "createUserProfile", e);
        }
        return userProfile;
    }
	
	
	@SuppressWarnings("unchecked")
	public List<UMProfileNDC> getProfilesByUser(Long pUserId) throws Doc41TechnicalException {
		List<UMProfileNDC> profiles = new ArrayList<UMProfileNDC>();
        try {
            profiles = OTUserManagementN.get().getProfilesByUser(pUserId, null, null, LocaleInSession.get());
        } catch (QueryException e) {
            throw new Doc41TechnicalException(this.getClass(), "getProfilesByUser", e);
        }
        return profiles;
	}
	
	
	protected void checkObligatoryParameter( Object pParamValue, String pParamName, String pCallingMethod ) throws Doc41TechnicalException {
        if ( pParamValue == null )
            throw new Doc41TechnicalException(this.getClass(), getClass().getSimpleName() + "." + pCallingMethod + ": Obligatory parameter '" + pParamName + "' not available, value is null!", null );
    }

	//---- partners
	
	public List<UserPartnerDC> getPartnersByUser(Long objectID) throws Doc41TechnicalException {
		try {
			String[] parameterNames			= { "USER_ID" };
	        Object[] parameterValues		= { objectID };
	        String templateName				= GET_PARTNERS_BY_USER;
	        Class<UserPartnerDC> dcClass	= UserPartnerDC.class;        
	        
	        List<UserPartnerDC> dcs = find(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dcs;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getPartnersByUser", e);
		}
	}

	public UserPartnerDC createUserPartner(Locale locale) throws Doc41TechnicalException {
		checkUser();
		UserPartnerDC newPartner = new UserPartnerDC();
		return newPartner;
	}

	public void saveUserPartner(UserPartnerDC newPartner) throws Doc41TechnicalException {
		checkUser();
        try {
            OTUserManagementN.get().storeDC(newPartner);
        } catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "saveUserPartner", e);
        }
	}

	public void deleteUserPartner(UserPartnerDC partner) throws Doc41TechnicalException {
		checkUser();
        try {
            OTUserManagementN.get().deleteDC(partner);
        } catch (DeleteException e) {
            throw new Doc41TechnicalException(this.getClass(), "deleteUserPartner", e);
        }
	}

	public UserPartnerDC getUserPartner(Long userId, String partnerNumber) throws Doc41TechnicalException {
		try {
			String[] parameterNames			= { "USER_ID", "PARTNER_NUMBER" };
	        Object[] parameterValues		= { userId, partnerNumber };
	        String templateName				= GET_USER_PARTNER;
	        Class<UserPartnerDC> dcClass	= UserPartnerDC.class;        
	        
	        UserPartnerDC dc = findDC(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dc;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getUserPartner", e);
		}
	}
	
	//---- countries
	
	public List<UserCountryDC> getCountriesByUser(Long objectID) throws Doc41TechnicalException {
		try {
			String[] parameterNames			= { "USER_ID" };
	        Object[] parameterValues		= { objectID };
	        String templateName				= GET_COUNTRIES_BY_USER;
	        Class<UserCountryDC> dcClass	= UserCountryDC.class;        
	        
	        List<UserCountryDC> dcs = find(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dcs;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getCountriesByUser", e);
		}
	}

	public UserCountryDC createUserCountry(Locale locale) throws Doc41TechnicalException {
		checkUser();
		UserCountryDC newCountry = new UserCountryDC();
		return newCountry;
	}

	public void saveUserCountry(UserCountryDC newCountry) throws Doc41TechnicalException {
		checkUser();
        try {
            OTUserManagementN.get().storeDC(newCountry);
        } catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "saveUserCountry", e);
        }
	}

	public void deleteUserCountry(UserCountryDC country) throws Doc41TechnicalException {
		checkUser();
        try {
            OTUserManagementN.get().deleteDC(country);
        } catch (DeleteException e) {
            throw new Doc41TechnicalException(this.getClass(), "deleteUserCountry", e);
        }
	}

	public UserCountryDC getUserCountry(Long userId, String countryCode) throws Doc41TechnicalException {
		try {
			String[] parameterNames			= { "USER_ID", "COUNTRY_CODE" };
	        Object[] parameterValues		= { userId, countryCode };
	        String templateName				= GET_USER_COUNTRY;
	        Class<UserCountryDC> dcClass	= UserCountryDC.class;        
	        
	        UserCountryDC dc = findDC(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dc;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getUserCountry", e);
		}
	}
	
	//---- plants
	
	public List<UserPlantDC> getPlantsByUser(Long objectID) throws Doc41TechnicalException {
		try {
			String[] parameterNames			= { "USER_ID" };
	        Object[] parameterValues		= { objectID };
	        String templateName				= GET_PLANTS_BY_USER;
	        Class<UserPlantDC> dcClass		= UserPlantDC.class;        
	        
	        List<UserPlantDC> dcs = find(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dcs;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getPlantByUser", e);
		}
	}

	public UserPlantDC createUserPlant(Locale locale) throws Doc41TechnicalException {
		checkUser();
		UserPlantDC newPlant = new UserPlantDC();
		return newPlant;
	}

	public void saveUserPlant(UserPlantDC newPlant) throws Doc41TechnicalException {
		checkUser();
        try {
            OTUserManagementN.get().storeDC(newPlant);
        } catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "saveUserPlant", e);
        }
	}

	public void deleteUserPlant(UserPlantDC plant) throws Doc41TechnicalException {
		checkUser();
        try {
            OTUserManagementN.get().deleteDC(plant);
        } catch (DeleteException e) {
            throw new Doc41TechnicalException(this.getClass(), "deleteUserPlant", e);
        }
	}

	public UserPlantDC getUserPlant(Long userId, String plant) throws Doc41TechnicalException {
		try {
			String[] parameterNames			= { "USER_ID", "PLANT" };
	        Object[] parameterValues		= { userId, plant };
	        String templateName				= GET_USER_PLANT;
	        Class<UserPlantDC> dcClass		= UserPlantDC.class;        
	        
	        UserPlantDC dc = findDC(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dc;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getUserPlant", e);
		}
	}
	
	//---- 
	
	
	public List<ProfilePermissionDC> getProfilePermissions() throws Doc41TechnicalException {
		try {
			String[] parameterNames				= { };
	        Object[] parameterValues			= { };
	        String templateName					= GET_PROFILE_PERMISSIONS;
	        Class<ProfilePermissionDC> dcClass	= ProfilePermissionDC.class;        
	        
	        List<ProfilePermissionDC> dcs = find(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dcs;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getProfilePermissions", e);
		}
	}

	
	@SuppressWarnings("unchecked")
	public ArrayList<UMPermissionNDC> getPermissionsByProfile(Long profileId, Locale pLoc) throws Doc41TechnicalException {
        try {
            return OTUserManagementN.get().getPermissionsByProfile(profileId,null,null,pLoc);
        } catch (QueryException e) {
            throw new Doc41TechnicalException(this.getClass(), "getPermissionsByProfile", e);
        }
	}
}
