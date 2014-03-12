/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.domain;

import java.util.StringTokenizer;


/**
 * VersionizedDomainObject. Used for optimistic locking.
 * 
 * @author ezzqc
 */
public abstract class VersionizedDomainObject extends DomainObject {
	private static final long serialVersionUID = 1896220100046239278L;

	/** Separates id and version number. */
    private static final String SEPARATOR = ",";
    
    private Long versionNumber;

    public String getId() {
        if(versionNumber == null) {
            return super.getId();
        } else {
            return super.getId() + SEPARATOR + versionNumber;
        }
    }
    
    public Long getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }
    
    /**
     * 
     * Get Object id from the given id
     * @param id
     * @return
     */
    public static Long getDcId(String id) {
        if (id == null) {
            return null;
        }
        
        StringTokenizer st = new StringTokenizer(id, SEPARATOR);
        return Long.valueOf(st.nextToken());
    }
    
    /**
     * 
     * Get Object id from the given id
     * @param id
     * @return
     */
    public static Long getVersionId(String id) {
        if (id == null) {
            return null;
        }
        
        StringTokenizer st = new StringTokenizer(id, SEPARATOR);
        st.nextToken();
        
        if (!st.hasMoreTokens()) {
            return null;
        } else {
            return Long.valueOf(st.nextToken());
        }
    }
}
