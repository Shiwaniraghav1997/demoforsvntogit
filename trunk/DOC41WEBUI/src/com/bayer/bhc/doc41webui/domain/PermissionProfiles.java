package com.bayer.bhc.doc41webui.domain;

import java.util.HashMap;

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

    /**
     * @return the permissionCode
     */
    public String getPermissionCode() {
        return PermissionCode;
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

}
