package com.bayer.bhc.doc41webui.integration.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.container.UserPagingRequest;
import com.bayer.bhc.doc41webui.domain.Profile;
import com.bayer.bhc.doc41webui.integration.db.dc.ProfilePermissionDC;
import com.bayer.bhc.doc41webui.integration.db.dc.SapCustomerDC;
import com.bayer.bhc.doc41webui.integration.db.dc.SapVendorDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UMDoc41PermissionNDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UMDoc41ProfileNDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UserCountryDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UserCustomerDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UserPlantDC;
import com.bayer.bhc.doc41webui.integration.db.dc.UserVendorDC;
import com.bayer.ecim.foundation.basic.ArrayTool;
import com.bayer.ecim.foundation.basic.BasicDataCarrier;
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
	
	private static final String GET_CUSTOMERS_BY_USER		= "getCustomersByUser";
	private static final String GET_USER_CUSTOMER			= "getUserCustomer";
	private static final String GET_CUSTOMER_BY_NUMBER		= "getCustomerByNumber";
	private static final String GET_VENDORS_BY_USER			= "getVendorsByUser";
	private static final String GET_USER_VENDOR				= "getUserVendor";
	private static final String GET_VENDOR_BY_NUMBER		= "getVendorByNumber";
	private static final String GET_COUNTRIES_BY_USER		= "getCountriesByUser";
	private static final String GET_USER_COUNTRY			= "getUserCountry";
	private static final String GET_PLANTS_BY_USER			= "getPlantsByUser";
	private static final String GET_USER_PLANT				= "getUserPlant";
	private static final String GET_PROFILE_PERMISSIONS		= "getProfilePermissions";
	
	@Override
	public String getTemplateComponentName() {		
		return TEMPLATE_COMPONENT_NAME;
	}
    
	public UMProfileNDC getProfileByName(String pName, Locale pLocale) throws Doc41TechnicalException {
		try {
	        UMProfileNDC profile = null;
	        ResultObject<UMProfileNDC> profiles = OTUserManagementN.get().getProfiles(null, null, pName, -1, -1, null, null, null, null, pLocale);
	        if (profiles != null && profiles.getResult() != null && !profiles.getResult().isEmpty()) {
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

	
	/**
	 * Check if user has a permissions requiring at least one Customer assigned.
	 * @param pCwid
	 * @return
	 * @throws Doc41TechnicalException
	 */
	public boolean userNeedsCustomers(String pCwid) throws Doc41TechnicalException {
	    UMUserNDC mUser = getUserByCWID(pCwid);
	    for (UMPermissionNDC mPerm : mUser.getResolvedUserPermissions()) {
	        UMDoc41PermissionNDC mDoc41Perm = (UMDoc41PermissionNDC)mPerm;
	        if (mDoc41Perm.getHasCustomer()) {
	            return true;
	        }
	    }
	    return false;
	}

    /**
     * Check if user has a permissions requiring at least one Country assigned.
     * @param pCwid
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsCountries(String pCwid) throws Doc41TechnicalException {
        UMUserNDC mUser = getUserByCWID(pCwid);
        for (UMPermissionNDC mPerm : mUser.getResolvedUserPermissions()) {
            UMDoc41PermissionNDC mDoc41Perm = (UMDoc41PermissionNDC)mPerm;
            if (mDoc41Perm.getHasCountry()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user has a permissions requiring at least one Vendor assigned.
     * @param pCwid
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsVendors(String pCwid) throws Doc41TechnicalException {
        UMUserNDC mUser = getUserByCWID(pCwid);
        for (UMPermissionNDC mPerm : mUser.getResolvedUserPermissions()) {
            UMDoc41PermissionNDC mDoc41Perm = (UMDoc41PermissionNDC)mPerm;
            if (mDoc41Perm.getHasVendor()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user has a permissions requiring at least one Plant assigned.
     * @param pCwid
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsPlants(String pCwid) throws Doc41TechnicalException {
        UMUserNDC mUser = getUserByCWID(pCwid);
        for (UMPermissionNDC mPerm : mUser.getResolvedUserPermissions()) {
            UMDoc41PermissionNDC mDoc41Perm = (UMDoc41PermissionNDC)mPerm;
            if (mDoc41Perm.getHasPlant()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user has a permissions requiring at least one Customer assigned.
     * @param pProfiles
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsCustomers(String[] pProfiles) throws Doc41TechnicalException {
        if ((pProfiles == null) || (pProfiles.length == 0)) {
            return false;
        }
        ArrayList<UMPermissionNDC> mList = getProfilePermissions(pProfiles);
        for (UMPermissionNDC mPerm : mList) {
            UMDoc41PermissionNDC mDoc41Perm = (UMDoc41PermissionNDC)mPerm;
            if (mDoc41Perm.getHasCustomer()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user has a permissions requiring at least one Country assigned.
     * @param pProfiles
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsCountries(String[] pProfiles) throws Doc41TechnicalException {
        if ((pProfiles == null) || (pProfiles.length == 0)) {
            return false;
        }
        ArrayList<UMPermissionNDC> mList = getProfilePermissions(pProfiles);
        for (UMPermissionNDC mPerm : mList) {
            UMDoc41PermissionNDC mDoc41Perm = (UMDoc41PermissionNDC)mPerm;
            if (mDoc41Perm.getHasCountry()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user has a permissions requiring at least one Vendor assigned.
     * @param pProfiles
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsVendors(String[] pProfiles) throws Doc41TechnicalException {
        if ((pProfiles == null) || (pProfiles.length == 0)) {
            return false;
        }
        ArrayList<UMPermissionNDC> mList = getProfilePermissions(pProfiles);
        for (UMPermissionNDC mPerm : mList) {
            UMDoc41PermissionNDC mDoc41Perm = (UMDoc41PermissionNDC)mPerm;
            if (mDoc41Perm.getHasVendor()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user has a permissions requiring at least one Plant assigned.
     * @param pProfiles
     * @return
     * @throws Doc41TechnicalException
     */
    public boolean userNeedsPlants(String[] pProfiles) throws Doc41TechnicalException {
        if ((pProfiles == null) || (pProfiles.length == 0)) {
            return false;
        }
        ArrayList<UMPermissionNDC> mList = getProfilePermissions(pProfiles);
        for (UMPermissionNDC mPerm : mList) {
            UMDoc41PermissionNDC mDoc41Perm = (UMDoc41PermissionNDC)mPerm;
            if (mDoc41Perm.getHasPlant()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get All Profile(Beans). Transform DC to internal Profile bean.
     * @return
     * @throws Doc41TechnicalException
     */
    public List<Profile> getAllProfiles() throws Doc41TechnicalException {
        ArrayList<Profile>res = new ArrayList<Profile>();
        String mLastType = null;
        boolean mIsEvenInList = false;
        boolean mIsEvenInType = false;
        Profile mProf = null;
        Profile mPrevProf = null;
        for (UMProfileNDC mDC : getAllProfileDCs()) {
            UMDoc41ProfileNDC mDCD = (UMDoc41ProfileNDC)mDC;
            mPrevProf = mProf;
            mProf = new Profile().copyFrom(mDCD);
            mProf.setIsFirstOfType((mLastType == null) || !mLastType.equals(mProf.getType()));
            if (mProf.isIsFirstOfType()) {
                if (mPrevProf != null) {
                    mPrevProf.setIsLastOfType(true);
                }
                mIsEvenInType = false;
            }
            mProf.setIsEvenInList(mIsEvenInList);
            mProf.setIsEvenInType(mIsEvenInType);
            mIsEvenInList = !mIsEvenInList;
            mIsEvenInType = !mIsEvenInType;
            mLastType = mProf.getType();
            res.add(mProf);
        }
        if (res.size() > 0) {
            res.get(0).setIsFirst(true);
            res.get(res.size() - 1).setIsLast(true);
        }
        return res;
    }
    
    /**
     * Get the ordered List of all ProfileNames of currently existing profiles (not deleted)
     * @return
     * @throws Doc41TechnicalException
     */
    public List<String>getAllProfileNamesList() throws Doc41TechnicalException {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<String> mRes = (List<String>)(List)ArrayTool.toList(BasicDataCarrier.getFieldsAsObjectArray(getAllProfileDCs(), UMProfileNDC.FIELD_PROFILENAME));
        return mRes;
    }
    
    /**
     * Get the HashSet of all ProfileNames of currently existing profiles (not deleted)
     * @return
     * @throws Doc41TechnicalException
     */
    public HashSet<String>getAllProfileNames() throws Doc41TechnicalException {
        @SuppressWarnings("unchecked")
        HashSet<String> mRes = (HashSet<String>)BasicDataCarrier.getFieldHashSet(getAllProfileDCs(), UMProfileNDC.FIELD_PROFILENAME);
        return mRes;
    }
    
    /**
     * Get the list of all ProfileNames of currently existing profiles (not deleted).
     * @return
     * @throws Doc41TechnicalException
     */
	protected ArrayList<UMProfileNDC> getAllProfileDCs() throws Doc41TechnicalException {
	    try {
            ArrayList<UMProfileNDC> result = (ArrayList<UMProfileNDC>)OTUserManagementN.get().getProfiles(null, null, null, -1, -1, null, null, "d41_Order_By", null, LocaleInSession.get()).getResult();
            return result;
        } catch (Exception e) {
            throw new Doc41TechnicalException(getClass(), "getAllProfiles", e);
        }
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
            ResultObject<UMUserNDC> resultObject = null;
            if (!StringTool.isTrimmedEmptyOrNull(pUserRequest.getRole()) && StringTool.isTrimmedEmptyOrNull(pUserRequest.getCwid())) {
                UMProfileNDC profileNDC = getProfileByName(pUserRequest.getRole(), LocaleInSession.get());
                resultObject = OTUserManagementN.get().getUsersByProfile(profileNDC.getObjectID(),pUserRequest.getFirstname(), pUserRequest.getSurname(), pUserRequest.getEmail(), objectState, pUserRequest.getIsExternal(), pUserRequest.getStartIndex(), pUserRequest.getEndIndex(), pUserRequest.getCompany(), null, orderBy, Long.valueOf(pUserRequest.getTotalSize()), LocaleInSession.get());
                Doc41Log.get().debug(this.getClass(), "System", "getUsers(...) getUsersByProfile returned (hitCount): " + resultObject.getHitCount());
            } else {
                // company as quicksearch:
                resultObject = OTUserManagementN.get().getUsers(pUserRequest.getCwid(), null, pUserRequest.getFirstname(), pUserRequest.getSurname(), pUserRequest.getEmail(), objectState, null, pUserRequest.getIsExternal(), pUserRequest.getStartIndex(), pUserRequest.getEndIndex(), pUserRequest.getCompany(), null, orderBy, Long.valueOf(pUserRequest.getTotalSize()), LocaleInSession.get());
            }
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
			user = OTUserManagementN.get().getUserByCWID(pCwid, Locale.US);
		} catch (InitException e) {
            throw new Doc41TechnicalException(this.getClass(), "getUserByCwid", e);
		} catch (QueryException e) {
            throw new Doc41TechnicalException(this.getClass(), "getUserByCwid", e);
		}
		return user;
	}
	
    public ArrayList<UMPermissionNDC> getProfilePermissions(final String[] pProfiles) throws Doc41TechnicalException {
        try {
            return OTUserManagementN.get().getPermissionsByProfile(null, null, pProfiles, null, null, Locale.US);
        } catch (InitException e) {
            throw new Doc41TechnicalException(this.getClass(), "getProfilePermissions", e);
        } catch (QueryException e) {
            throw new Doc41TechnicalException(this.getClass(), "getProfilePermissions", e);
        }
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
	
	
	public List<UMProfileNDC> getProfilesByUser(Long pUserId) throws Doc41TechnicalException {
		List<UMProfileNDC> profiles;
        try {
            profiles = OTUserManagementN.get().getProfilesByUser(pUserId, null, null, LocaleInSession.get());
        } catch (QueryException e) {
            throw new Doc41TechnicalException(this.getClass(), "getProfilesByUser", e);
        }
        return profiles;
	}
	
	
	protected void checkObligatoryParameter( Object pParamValue, String pParamName, String pCallingMethod ) throws Doc41TechnicalException {
        if ( pParamValue == null ){
            throw new Doc41TechnicalException(this.getClass(), getClass().getSimpleName() + "." + pCallingMethod + ": Obligatory parameter '" + pParamName + "' not available, value is null!", null );
        }
    }

	//---- customers
	
	public List<SapCustomerDC> getCustomersByUser(Long objectID) throws Doc41TechnicalException {
		try {
			String[] parameterNames			= { "USER_ID" };
	        Object[] parameterValues		= { objectID };
	        String templateName				= GET_CUSTOMERS_BY_USER;
	        Class<SapCustomerDC> dcClass	= SapCustomerDC.class;        
	        
	        List<SapCustomerDC> dcs = find(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dcs;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getCustomersByUser", e);
		}
	}

	public UserCustomerDC createUserCustomer(Locale locale) throws Doc41TechnicalException {
		checkUser();
        return (UserCustomerDC)OTUserManagementN.localizeDC(UserCustomerDC.newInstanceOfUserCustomerDC(), (locale == null) ? Locale.US : locale);
	}

	public void saveUserCustomer(UserCustomerDC newCustomer) throws Doc41TechnicalException {
		checkUser();
        try {
            OTUserManagementN.get().storeDC(newCustomer);
        } catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "saveUserCustomer", e);
        }
	}

	public void deleteUserCustomer(UserCustomerDC customer) throws Doc41TechnicalException {
		checkUser();
        try {
            OTUserManagementN.get().deleteDC(customer);
        } catch (DeleteException e) {
            throw new Doc41TechnicalException(this.getClass(), "deleteUserCustomer", e);
        }
	}

	public UserCustomerDC getUserCustomer(Long userId, String customerNumber) throws Doc41TechnicalException {
		try {
			String[] parameterNames			= { "USER_ID", "PARTNER_NUMBER" };
	        Object[] parameterValues		= { userId, customerNumber };
	        String templateName				= GET_USER_CUSTOMER;
	        Class<UserCustomerDC> dcClass	= UserCustomerDC.class;        
	        
	        UserCustomerDC dc = findDC(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dc;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getUserCustomer", e);
		}
	}
	
	public SapCustomerDC getSapCustomerByNumber(String customerNumber) throws Doc41TechnicalException {
		try {
			String[] parameterNames			= { "PARTNER_NUMBER" };
	        Object[] parameterValues		= { customerNumber };
	        String templateName				= GET_CUSTOMER_BY_NUMBER;
	        Class<SapCustomerDC> dcClass		= SapCustomerDC.class;        
	        
	        SapCustomerDC dc = findDC(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dc;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getSapCustomerByNumber", e);
		}
	}
	
	//---- vendors
	
	public List<SapVendorDC> getVendorsByUser(Long objectID) throws Doc41TechnicalException {
		try {
			String[] parameterNames			= { "USER_ID" };
	        Object[] parameterValues		= { objectID };
	        String templateName				= GET_VENDORS_BY_USER;
	        Class<SapVendorDC> dcClass	= SapVendorDC.class;        
	        
	        List<SapVendorDC> dcs = find(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dcs;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getVendorsByUser", e);
		}
	}

	public UserVendorDC createUserVendor(Locale locale) throws Doc41TechnicalException {
		checkUser();
        return (UserVendorDC)OTUserManagementN.localizeDC(UserVendorDC.newInstanceOfUserVendorDC(), (locale == null) ? Locale.US : locale);
	}

	public void saveUserVendor(UserVendorDC newVendor) throws Doc41TechnicalException {
		checkUser();
        try {
            OTUserManagementN.get().storeDC(newVendor);
        } catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "saveUserVendor", e);
        }
	}

	public void deleteUserVendor(UserVendorDC vendor) throws Doc41TechnicalException {
		checkUser();
        try {
            OTUserManagementN.get().deleteDC(vendor);
        } catch (DeleteException e) {
            throw new Doc41TechnicalException(this.getClass(), "deleteUserVendor", e);
        }
	}

	public UserVendorDC getUserVendor(Long userId, String vendorNumber) throws Doc41TechnicalException {
		try {
			String[] parameterNames			= { "USER_ID", "PARTNER_NUMBER" };
	        Object[] parameterValues		= { userId, vendorNumber };
	        String templateName				= GET_USER_VENDOR;
	        Class<UserVendorDC> dcClass	= UserVendorDC.class;        
	        
	        UserVendorDC dc = findDC(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dc;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getUserVendor", e);
		}
	}
	
	public SapVendorDC getSapVendorByNumber(String vendorNumber) throws Doc41TechnicalException {
		try {
			String[] parameterNames			= { "PARTNER_NUMBER" };
	        Object[] parameterValues		= { vendorNumber };
	        String templateName				= GET_VENDOR_BY_NUMBER;
	        Class<SapVendorDC> dcClass		= SapVendorDC.class;        
	        
	        SapVendorDC dc = findDC(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dc;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getSapVendorByNumber", e);
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
		return (UserCountryDC)OTUserManagementN.localizeDC(UserCountryDC.newInstanceOfUserCountryDC(), (locale == null) ? Locale.US : locale);
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
        return (UserPlantDC)OTUserManagementN.localizeDC(UserPlantDC.newInstanceOfUserPlantDC(), (locale == null) ? Locale.US : locale);
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

	
	public ArrayList<UMPermissionNDC> getPermissionsByProfile(Long profileId, Locale pLoc) throws Doc41TechnicalException {
        try {
            return OTUserManagementN.get().getPermissionsByProfile(profileId,null,null, null, null,pLoc);
        } catch (QueryException e) {
            throw new Doc41TechnicalException(this.getClass(), "getPermissionsByProfile", e);
        }
	}

	/**
	 * Get a list of all Permission codes currently available.
	 * @return
	 * @throws Doc41TechnicalException
	 */
    public HashSet<String> getAllPermissionCodes() throws Doc41TechnicalException {
        try {
            @SuppressWarnings("unchecked")
            HashSet<String> fieldHashSet = (HashSet<String>)UMPermissionNDC.getFieldHashSet(OTUserManagementN.get().getPermissions(null, null, null, null, null, null, null, -1, -1, null, null, null, null, Locale.US).getResult(), UMPermissionNDC.FIELD_CODE);
            return fieldHashSet;
        } catch (QueryException e) {
            throw new Doc41TechnicalException(this.getClass(), "getPermissionsByProfile", e);
        }
    }

}

