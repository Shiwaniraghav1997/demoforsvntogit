package com.bayer.bhc.doc41webui.domain;

import java.util.HashMap;

import com.bayer.ecim.foundation.basic.StringTool;

/**
 * User domain object.
 * 
 * @author ezzqc
 */
public class PermissionProfiles extends DomainObject {

    /**
     * User domain object.
     * 
     * @author ezzqc
     */

    private static final long serialVersionUID = -88472347824568736L;

   
    private String PermissionCode = null;
    
    private String PermissionName = null;
        
    private String PermissionDescription = null;
        
    private HashMap<String,Long> profiles = new HashMap<String, Long>();
    
    private String Type = null;

    private boolean HasCustomer = false;
    
    private boolean HasVendor = false;
    
    private boolean HasCountry = false;
    
    private boolean HasPlant = false;
    
    
    /**
     * @return the permissionCode
     */
    public String getPermissionCode() {
        return PermissionCode;
    }

    /**
     * @return the permissionCode
     */
    public String getPermissionCodeHTML() {
        return StringTool.escapeHTML(getPermissionCode().replace(' ', (char)160));
    }

    /**
     * @param permissionCode the permissionCode to set
     */
    public void setPermissionCode(String permissionCode) {
        PermissionCode = permissionCode;
    }

    /**
     * @return the permissionName
     */
    public String getPermissionName() {
        return PermissionName;
    }

    /**
     * @return the permissionName
     */
    public String getPermissionNameHTML() {
        return StringTool.escapeHTML(getPermissionName().replace(' ', (char)160));
    }

    /**
     * @param permissionName the permissionName to set
     */
    public void setPermissionName(String permissionName) {
        PermissionName = permissionName;
    }

    /**
     * @return the permissionDescription
     */
    public String getPermissionDescription() {
        return PermissionDescription;
    }

    /**
     * @return the permissionDescription
     */
    public String getPermissionDescriptionHTML() {
        return StringTool.escapeHTML(getPermissionDescription().replace(' ', (char)160));
    }

    /**
     * @param permissionDescription the permissionDescription to set
     */
    public void setPermissionDescription(String permissionDescription) {
        PermissionDescription = permissionDescription;
    }

    /**
     * @return the profiles
     */
    public HashMap<String, Long> getProfiles() {
        return profiles;
    }

    /**
     * @param profiles the profiles to set
     */
    public void setProfiles(HashMap<String, Long> profiles) {
        if (profiles == null) {
            profiles = new HashMap<String,Long>();
        }
        this.profiles = profiles;
    }

    /**
     * @return the type
     */
    public String getType() {
        return Type;
    }

    /**
     * @return the type
     */
    public String getTypeHTML() {
        return StringTool.escapeHTML(StringTool.nullToEmpty(getType()).replace(' ', (char)160));
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        Type = type;
    }

    /**
     * @return the hasCustomer
     */
    public boolean isHasCustomer() {
        return HasCustomer;
    }

    /**
     * @param hasCustomer the hasCustomer to set
     */
    public void setHasCustomer(boolean hasCustomer) {
        HasCustomer = hasCustomer;
    }

    /**
     * @return the hasVendor
     */
    public boolean isHasVendor() {
        return HasVendor;
    }

    /**
     * @param hasVendor the hasVendor to set
     */
    public void setHasVendor(boolean hasVendor) {
        HasVendor = hasVendor;
    }

    /**
     * @return the hasCountry
     */
    public boolean isHasCountry() {
        return HasCountry;
    }

    /**
     * @param hasCountry the hasCountry to set
     */
    public void setHasCountry(boolean hasCountry) {
        HasCountry = hasCountry;
    }

    /**
     * @return the hasPlant
     */
    public boolean isHasPlant() {
        return HasPlant;
    }

    /**
     * @param hasPlant the hasPlant to set
     */
    public void setHasPlant(boolean hasPlant) {
        HasPlant = hasPlant;
    }

    /**
     * Get a Status representation (associated/required objects).
     * @return
     */
    public String getPermissionStatus() {
        return
                ( isHasVendor() ? "Vnd" : "---" ) +
                ( isHasPlant() ? "Plt" : "---" ) +
                ( isHasCustomer() ? "Cst" : "---" ) +
                ( isHasCountry() ? "Cty" : "---" );
    }

    /**
     * Get a Status representation (associated/required objects).
     * @return
     */
    public String getPermissionStatusHTML() {
        return StringTool.escapeHTML(getPermissionStatus().replace(' ', (char)160));
    }

}
