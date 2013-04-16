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
import com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN.Doc41ProfileNDC;
import com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN.Doc41UserNDC;
import com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN.Doc41UserProfileNDC;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.dbx.DeleteException;
import com.bayer.ecim.foundation.dbx.QueryException;
import com.bayer.ecim.foundation.dbx.ResultObject;
import com.bayer.ecim.foundation.dbx.StoreException;
import com.bayer.ecim.foundation.web.usermanagementN.UM_CONSTS_N;

/**
 * @author EVFPU
 *
 */
@Component
public class UserManagementDAO {
	private Doc41UserManagementN userManagementDAO;
	
	public UserManagementDAO() {
		userManagementDAO = Doc41UserManagementN.get();
	}
	
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
    
	public Doc41ProfileNDC getProfileByName(String pName, Locale pLocale) throws Doc41TechnicalException {
		try {
	        Doc41ProfileNDC profile = null;
	        ResultObject profiles = Doc41UserManagementN.get().getProfiles(null, null, pName, -1, -1, null, null, null, null, pLocale);
	        if (profiles != null && profiles.getResult() != null && profiles.getResult().size() > 0) {
	            // get profile by name should return a unique result
	            profile = (Doc41ProfileNDC) profiles.getResult().get(0);
	        }
	        return profile;
		} catch (QueryException e) {
			throw new Doc41TechnicalException(this.getClass(), "Error during getProfileByName.", e);
		}
    }
    
	public void saveUserProfile(Doc41UserProfileNDC pProfile) throws Doc41TechnicalException {
        checkUser();
        
        try {
            Doc41UserManagementN.get().storeDC(pProfile);
        } catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "saveUserProfile", e);
        }
    }
	
	public void updateUser(final Doc41UserNDC pUserDC) throws Doc41TechnicalException {
        checkUser();
        
		try {
            Doc41UserManagementN.get().storeDC(pUserDC);
            
		} catch (InitException e) {
            throw new Doc41TechnicalException(this.getClass(), "updateUser", e);
		} catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "updateUser", e);
		} 
	}
    
	

	public void removeUserProfile(Long pObjectId) throws Doc41TechnicalException {
        checkUser();
        try {
            Doc41UserManagementN.get().deleteDCById(new Doc41UserProfileNDC(), pObjectId);
        } catch (DeleteException e) {
            throw new Doc41TechnicalException(this.getClass(), "removeUserProfile", e);
        }
    }
    
	public Doc41UserNDC insertUser(final Doc41UserNDC pUserDC) throws Doc41TechnicalException {
        Doc41UserNDC userDC = null;
        checkUser();
        
		try {
            userDC = (Doc41UserNDC) Doc41UserManagementN.get().storeDC(pUserDC);
		} catch (InitException e) {
            throw new Doc41TechnicalException(this.getClass(), "insertUser", e);
		} catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "insertUser", e);
		}
        return userDC;
	}
   
	public PagingResult<Doc41UserNDC> getDoc41UserNDCs(UserPagingRequest pUserRequest) throws Doc41TechnicalException {
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
                Doc41ProfileNDC profileNDC = getProfileByName(pUserRequest.getRole(), LocaleInSession.get());
                resultObject = userManagementDAO.getUsersByProfile(profileNDC.getObjectID(),null, pUserRequest.getSurname(), null, objectState, pUserRequest.getIsExternal(), pUserRequest.getStartIndex(), pUserRequest.getEndIndex(), pUserRequest.getCompany(), null, orderBy, new Long(pUserRequest.getTotalSize()), LocaleInSession.get());
                Doc41Log.get().debug(this.getClass(), "System", "getUsers(...) getUsersByProfile returned (hitCount): " + resultObject.getHitCount());
            } else {
                // company as quicksearch:
                resultObject = userManagementDAO.getUsers(pUserRequest.getCwid(), null, null, pUserRequest.getSurname(), null, objectState, null, pUserRequest.getIsExternal(), pUserRequest.getStartIndex(), pUserRequest.getEndIndex(), pUserRequest.getCompany(), null, orderBy, new Long(pUserRequest.getTotalSize()), LocaleInSession.get());
            }
            @SuppressWarnings("unchecked")
			List<Doc41UserNDC> userDCList = resultObject.getDCListResult();
            
            PagingResult<Doc41UserNDC> result = new PagingResult<Doc41UserNDC>();
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
	
	public Doc41UserNDC getUserByCWID(final String pCwid) throws Doc41TechnicalException {
        Doc41UserNDC user = null;
		try {
			user = Doc41UserManagementN.get().getUserByCWID(pCwid, Locale.ENGLISH);
		} catch (InitException e) {
            throw new Doc41TechnicalException(this.getClass(), "getUserByCwid", e);
		} catch (QueryException e) {
            throw new Doc41TechnicalException(this.getClass(), "getUserByCwid", e);
		}
		return user;
	}
	
	public Doc41UserNDC createUser(Locale pLoc) throws Doc41TechnicalException {
        Doc41UserNDC user = null;
        checkUser();        
        try {
            user = Doc41UserManagementN.get().createUserDC(pLoc);
        } catch (InstantiationException e) {
            throw new Doc41TechnicalException(this.getClass(), "createUser", e);
        } catch (IllegalAccessException e) {
            throw new Doc41TechnicalException(this.getClass(), "createUser", e);
        } catch (ClassNotFoundException e) {
            throw new Doc41TechnicalException(this.getClass(), "createUser", e);
        }
        return user;
    }
	
	public Doc41UserProfileNDC createUserProfile(Locale pLoc) throws Doc41TechnicalException {
        Doc41UserProfileNDC userProfile = null;
        checkUser();        
        try {
            userProfile = Doc41UserManagementN.get().createUserProfileDC(pLoc);
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
	public List<Doc41ProfileNDC> getProfilesByUser(Long pUserId) throws Doc41TechnicalException {
		List<Doc41ProfileNDC> profiles = new ArrayList<Doc41ProfileNDC>();
        try {
            profiles = Doc41UserManagementN.get().getProfilesByUser(pUserId, null, null, LocaleInSession.get());
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
