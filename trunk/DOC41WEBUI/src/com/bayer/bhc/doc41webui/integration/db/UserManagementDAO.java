package com.bayer.bhc.doc41webui.integration.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41AccessDeniedException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.UserPagingRequest;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.dbx.DeleteException;
import com.bayer.ecim.foundation.dbx.QueryException;
import com.bayer.ecim.foundation.dbx.ResultObject;
import com.bayer.ecim.foundation.dbx.StoreException;
import com.bayer.ecim.foundation.web.usermanagementN.OTUserManagementN;
import com.bayer.ecim.foundation.web.usermanagementN.UMProfileNDC;
import com.bayer.ecim.foundation.web.usermanagementN.UMUserNDC;
import com.bayer.ecim.foundation.web.usermanagementN.UMUserProfileNDC;
import com.bayer.ecim.foundation.web.usermanagementN.UM_CONSTS_N;

/**
 * @author EVFPU
 *
 */
@Component
public class UserManagementDAO {
	/**
     * Observers do not have permissions to store and delete functions.
     * 
     * @throws Doc41AccessDeniedException
     */
	private void checkUser() throws Doc41TechnicalException {
        if (UserInSession.isReadOnly()) {
            throw new Doc41AccessDeniedException(this.getClass());
        }
    }
    
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
            if (!StringTool.isTrimmedEmptyOrNull(pUserRequest.getRole())) {
                UMProfileNDC profileNDC = getProfileByName(pUserRequest.getRole(), LocaleInSession.get());
                resultObject = OTUserManagementN.get().getUsersByProfile(profileNDC.getObjectID(),null, pUserRequest.getSurname(), null, objectState, pUserRequest.getIsExternal(), pUserRequest.getStartIndex(), pUserRequest.getEndIndex(), pUserRequest.getCompany(), null, orderBy, new Long(pUserRequest.getTotalSize()), LocaleInSession.get());
                Doc41Log.get().debug(this.getClass(), "System", "getUsers(...) getUsersByProfile returned (hitCount): " + resultObject.getHitCount());
            } else {
                // company as quicksearch:
                resultObject = OTUserManagementN.get().getUsers(pUserRequest.getCwid(), null, null, pUserRequest.getSurname(), null, objectState, null, pUserRequest.getIsExternal(), pUserRequest.getStartIndex(), pUserRequest.getEndIndex(), pUserRequest.getCompany(), null, orderBy, new Long(pUserRequest.getTotalSize()), LocaleInSession.get());
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

}
