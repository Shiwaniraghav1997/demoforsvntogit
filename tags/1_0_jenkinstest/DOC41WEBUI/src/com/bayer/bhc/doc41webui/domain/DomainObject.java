package com.bayer.bhc.doc41webui.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Date;

import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.util.UserInSession;

/**
 * Domain Object. Base class for all domain objects.
 * 
 */
public abstract class DomainObject implements java.io.Serializable {

	private static final long serialVersionUID = -5518471855937512232L;

	private Long dcId = null;
//    private String viewId;
//    public String getViewId() {
//    	return dcId.toString();
//    }
//    public void setViewId(String viewId) {
//    	dcId = Long.valueOf(viewId);
//    }
	
	private String changedBy;
    
    private String createdBy;
    
    private Date changed;
    
    private Date created;
    
    public boolean equals(Object o) {
        if(o instanceof DomainObject) {
            
            if(dcId == null) {
                return super.equals(o);
            } else {
                return this.dcId.equals(((DomainObject)o).getDcId()); 
            }
        }
        return false;
    }

    public DomainObject() {
        this.created = new Date();
        if (UserInSession.get() != null) {
            this.createdBy = UserInSession.getCwid();
        }
    }
    
    public String getId() {
        if(dcId == null) {
            return null;
        } else {
            return dcId.toString();
        }
    }
    
    /**
     * Override hashCode.
     *
     * @return the Objects hashcode.
     */
    public int hashCode() {
    	if (dcId == null) {
            return super.hashCode();
        } else {
        	return dcId.hashCode();
        }
    }

    // convenience method - search domain object by dcId in a given list of domain objects:
    public static final DomainObject findById(Collection<DomainObject> domainObjectList, Long id) {
        if (id == null) {
            return null;
        }
        return findById(domainObjectList, id.longValue());
    }
    
    // convenience method - search domain object by dcId in a given list of domain objects:
    public static final DomainObject findById(Collection<DomainObject> domainObjectList, long id) {
        if (domainObjectList == null || id == 0) {
            return null;
        }
        
        for (DomainObject bo : domainObjectList) {            
            if (bo.getDcId() != null && bo.getDcId().longValue() == id) {
                return bo;
            }
        }
        return null;
    }
    
    // GETTER and SETTER
    public Long getDcId() {
        return dcId;
    }

    public void setDcId(Long id) {
        this.dcId = id;
    }
    
    public Date getChanged() {
        return changed;
    }

    public void setChanged(Date changed) {
        this.changed = changed;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    /**
     * Erzeuge (deep-)Kopie über Serialisierung
     * @author Josef Wasel
     * @throws Doc41TechnicalException 
     */
    public Object deepCopy() throws Doc41TechnicalException {
    
        try {
            // compare reset()
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream outS = new ObjectOutputStream(byteStream);
    
            outS.writeObject(this);
            outS.flush();
    
            byte[] buf = byteStream.toByteArray();

            return (DomainObject) new ObjectInputStream(new ByteArrayInputStream(buf)).readObject();
    
        } catch (Exception e) {
            throw new Doc41TechnicalException(this.getClass(), "DomainObject.deepCopy", e);
        }
    }
}
