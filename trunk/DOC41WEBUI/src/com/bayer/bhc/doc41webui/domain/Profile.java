package com.bayer.bhc.doc41webui.domain;

import com.bayer.bhc.doc41webui.integration.db.dc.UMDoc41ProfileNDC;

/**
 * Profile domain object.
 * 
 * @author imwif
 */
public class Profile extends DomainObject {

	private static final long serialVersionUID = -88472347825468763L;

	/** name of the profile, secondary key. */
	private String Profilename;

	/** description of the profile, to be displayed in profile lists and user management pages. */
	private String Profiledescription;

    /** limitation of the profile - if true: to be used only with external users, if false: to be used only with internal users, if null: not limited = use with internal and external users */
	private Boolean Isexternal;

	/** type of the profile - here usage context, bound to codes of displaytext list 9500 (profiletypes), defining also order of types (in case of generic display) */ 
	private String Type;

	/** owner (mandant) of profile, not used in Doc41/BDS yet - bound to DisplayText list 9501 (profileowners). */
	private String Owner;

	/** generic order of profiles - especially inside a type(profile group), for generic display). */
	private Long D41OrderBy;

	/** mark inside the list of profiles, that there is a type change (first element of new type). (help for layout orientation) */
	private boolean IsFirstOfType = false;
	
    /** mark inside the list of profiles, that there is a type change (last element of new type). (help for layout orientation) */
    private boolean IsLastOfType = false;
    
    /** mark first element inside the list. (help for layout orientation) */
    private boolean IsFirst = false;
    
    /** mark last element inside the list. (help for layout orientation) */
    private boolean IsLast = false;
    
    /** mark even element (2, 4, 6...). (help for layout orientation) */
    private boolean IsEvenInList = false;
    
    /** mark even element inside the type (2, 4, 6...). (help for layout orientation) */
    private boolean IsEvenInType = false;
    
    /** by User detection, if the type of a user (internal/external) matches the type of this role (internal/external/both=null) */
    private boolean UserTypeMatchesRole = false;
    
    /**
     * @return the profilename
     */
    public String getProfilename() {
        return Profilename;
    }

    /**
     * @param profilename the profilename to set
     */
    public void setProfilename(String profilename) {
        Profilename = profilename;
    }

    /**
     * @return the profiledescription
     */
    public String getProfiledescription() {
        return Profiledescription;
    }

    /**
     * @param profiledescription the profiledescription to set
     */
    public void setProfiledescription(String profiledescription) {
        Profiledescription = profiledescription;
    }

    /**
     * @return the isexternal
     */
    public Boolean getIsexternal() {
        return Isexternal;
    }

    /**
     * @param isexternal the isexternal to set
     */
    public void setIsexternal(Boolean isexternal) {
        Isexternal = isexternal;
    }

    /**
     * For better EL handling: return "extern" on Isextern = true, "intern" on Isextern = false, "ext+int" on Isextern = null.
     * @return
     */
    public String getExtType() {
        return (getIsexternal() == null) ? "ext+int" : (getIsexternal().booleanValue() ? "extern" : "intern" );
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return Type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        Type = type;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return Owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        Owner = owner;
    }

    /**
     * @return the d41OrderBy
     */
    public Long getD41OrderBy() {
        return D41OrderBy;
    }

    /**
     * @param d41OrderBy the d41OrderBy to set
     */
    public void setD41OrderBy(Long d41OrderBy) {
        D41OrderBy = d41OrderBy;
    }

    /**
     * @return the IsFirstOfType
     */
    public boolean isIsFirstOfType() {
        return IsFirstOfType;
    }

    /**
     * @param isFirstOfType the isFirstOfType to set
     */
    public void setIsFirstOfType(boolean isFirstOfType) {
        IsFirstOfType = isFirstOfType;
    }

    /**
     * @return the IsLastOfType
     */
    public boolean isIsLastOfType() {
        return IsLastOfType;
    }

    /**
     * @param isLastOfType the isLastOfType to set
     */
    public void setIsLastOfType(boolean isLastOfType) {
        IsLastOfType = isLastOfType;
    }

    /**
     * @return the isFirst
     */
    public boolean isIsFirst() {
        return IsFirst;
    }

    /**
     * @param isFirst the isFirst to set
     */
    public void setIsFirst(boolean isFirst) {
        IsFirst = isFirst;
    }

    /**
     * @return the isLast
     */
    public boolean isIsLast() {
        return IsLast;
    }

    /**
     * @param isLast the isLast to set
     */
    public void setIsLast(boolean isLast) {
        IsLast = isLast;
    }

    /**
     * @return the isEven
     */
    public boolean isIsEvenInList() {
        return IsEvenInList;
    }

    /**
     * @param isEven the isEven to set
     */
    public void setIsEvenInList(boolean isEvenInList) {
        IsEvenInType = isEvenInList;
    }

    /**
     * @return the isEven
     */
    public boolean isIsEvenInType() {
        return IsEvenInType;
    }

    /**
     * @param isEven the isEven to set
     */
    public void setIsEvenInType(boolean isEvenInType) {
        IsEvenInType = isEvenInType;
    }

    /**
     * @return the userTypeMatchesRole
     */
    public boolean isUserTypeMatchesRole() {
        return UserTypeMatchesRole;
    }

    /**
     * @param userTypeMatchesRole the userTypeMatchesRole to set
     */
    public void setUserTypeMatchesRole(boolean userTypeMatchesRole) {
        UserTypeMatchesRole = userTypeMatchesRole;
    }

    /**
     * @param userTypeMatchesRole the userTypeMatchesRole to set
     */
    public void setUserTypeMatchesRoleByUser(boolean userIsExternal) {
        UserTypeMatchesRole = (Isexternal == null) || (Isexternal.booleanValue() == userIsExternal);
    }

    /**
     * Copy Data from corresponding DC.
     * @param dc
     */
    public Profile copyFrom(UMDoc41ProfileNDC dc) {
        if (dc != null) {
            setDcId(dc.getObjectID());
            setCreated(dc.getCreated());
            setChanged(dc.getUserChanged());
            setCreatedBy(dc.getCreatedBy());
            setChangedBy(dc.getLastChangedBy());
            setProfilename(dc.getProfilename());
            setProfiledescription(dc.getProfiledescription());
            setIsexternal(dc.getIsexternal());
            setType(dc.getType());
            setOwner(dc.getOwner());
            setD41OrderBy(dc.getD41OrderBy());
            setUserTypeMatchesRole(false);
        }
        return this;
    }

}
