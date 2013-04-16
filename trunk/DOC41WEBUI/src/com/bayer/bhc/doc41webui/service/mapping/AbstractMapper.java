/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.service.mapping;

import java.util.Date;

import com.bayer.bhc.doc41webui.common.exception.Doc41RepositoryException;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.DomainObject;
import com.bayer.bhc.doc41webui.domain.VersionizedDomainObject;
import com.bayer.bhc.doc41webui.integration.db.dc.Doc41DataCarrier;
import com.bayer.ecim.foundation.dbx.ChangeableDataCarrier;
import com.bayer.ecim.foundation.dbx.StorableDataCarrier;

/**
 * AbstractMapper.
 * 
 * @author ezzqb
 * @author evfpu
 */
public abstract class AbstractMapper {
    
    /** Version number field name on VersionizedDomainObjects. */
	public static final String IDOC_NUMBER = "IdocNumber";
	public static final String FIELD_VERSION_NUMBER = "VersionNumber";

    /**
     * Check given version number with that of the read DC and throw an Doc41RepositoryException if they differ.
     */
    private static final void checkVersionsAndIncrease(final ChangeableDataCarrier foundDC, final Long versionNumber) throws Doc41RepositoryException {
        checkVersions(foundDC, versionNumber);
        if(versionNumber != null) {
            foundDC.set(AbstractMapper.FIELD_VERSION_NUMBER, new Long(versionNumber.longValue() + 1));    
        } else {
            foundDC.set(AbstractMapper.FIELD_VERSION_NUMBER, new Long(0));
        }
    }
    
    
    private static final void checkVersions(final ChangeableDataCarrier foundDC, final Long versionNumber) throws Doc41RepositoryException  {
        if (foundDC != null  && versionNumber != null && foundDC.get(AbstractMapper.FIELD_VERSION_NUMBER) != null) {
            if (!versionNumber.equals(foundDC.get(AbstractMapper.FIELD_VERSION_NUMBER))) {
                throw new Doc41RepositoryException("OptimisticLockingException", null);
            } 
        }
    }
    
    protected <T extends ChangeableDataCarrier> T mapToDataCarrier(final DomainObject pDomainObject, T pDataCarrier) {
        if(null == pDomainObject || null == pDataCarrier) {
            return pDataCarrier;
        }
        pDataCarrier.setChanged(new Date());
        if (UserInSession.getCwid() != null) {
            pDataCarrier.setChangedBy(UserInSession.getCwid());
        } else {
            pDataCarrier.setChangedBy(pDomainObject.getCreatedBy());
        }
        
        pDataCarrier.setCreated(pDomainObject.getCreated());
        pDataCarrier.setCreatedBy(pDomainObject.getCreatedBy());
        
        return pDataCarrier;
    }
    
    protected <T extends ChangeableDataCarrier> T mapToDataCarrier(final VersionizedDomainObject pDomainObject, T pDataCarrier) throws Doc41RepositoryException {
        if(null == pDomainObject || null == pDataCarrier) {
            return pDataCarrier;
        }
        
        checkVersionsAndIncrease(pDataCarrier, pDomainObject.getVersionNumber());
        mapToDataCarrier((DomainObject)pDomainObject, pDataCarrier);
        
        return pDataCarrier;
    }
    
    protected <T extends DomainObject> T mapToDomainObject(final ChangeableDataCarrier pDataCarrier, T pDomainObject) {
        if(null == pDomainObject || null == pDataCarrier) {
            return pDomainObject;
        }
        pDomainObject.setDcId(pDataCarrier.getObjectID());
        pDomainObject.setChanged(pDataCarrier.getChanged());
        pDomainObject.setChangedBy(pDataCarrier.getChangedBy());
        pDomainObject.setCreated(pDataCarrier.getCreated());
        pDomainObject.setCreatedBy(pDataCarrier.getCreatedBy());
        
//        if (pDomainObject instanceof VersionizedDomainObject) {
//            VersionizedDomainObject vdo = (VersionizedDomainObject)pDomainObject;
//            vdo.setVersionNumber((Long)pDataCarrier.get(FIELD_VERSION_NUMBER));
//        }
        
        return pDomainObject;
    }
    
    protected <T extends VersionizedDomainObject> T mapToDomainObject(final ChangeableDataCarrier pDataCarrier, T pDomainObject) throws Doc41RepositoryException {
    	if(null == pDomainObject || null == pDataCarrier) {
            return pDomainObject;
        }
        
        checkVersionsAndIncrease(pDataCarrier, pDomainObject.getVersionNumber());
        mapToDomainObject(pDataCarrier, (DomainObject) pDomainObject);
        
        return pDomainObject;
    }
    
    protected <T extends DomainObject> T mapToDomainObject(final StorableDataCarrier pDataCarrier, T pDomainObject) {
        if(null == pDomainObject || null == pDataCarrier) {
            return pDomainObject;
        }
        pDomainObject.setDcId(pDataCarrier.getObjectID());
        return pDomainObject;
    }
    
	/* Doc41DataCarrier */
	protected <T extends Doc41DataCarrier> T mapToDataCarrier(final DomainObject pDomainObject, T pDataCarrier) {
		if(null == pDomainObject || null == pDataCarrier) {
			return pDataCarrier;
		}
		
		
		mapToDataCarrier(pDomainObject, (ChangeableDataCarrier) pDataCarrier);
		
		return pDataCarrier;
	}
	
//	protected <T extends Doc41DataCarrier> T mapToDataCarrier(final VersionizedDomainObject pDomainObject, T pDataCarrier) throws Doc41RepositoryException {
//		if(null == pDomainObject || null == pDataCarrier) {
//			return pDataCarrier;
//		}
//		
//		checkVersionsAndIncrease(pDataCarrier, pDomainObject.getVersionNumber());
//		mapToDataCarrier((DomainObject)pDomainObject, pDataCarrier);
//		
//		return pDataCarrier;
//	}
	
	protected <T extends DomainObject> T mapToDomainObject(final Doc41DataCarrier pDataCarrier, T pDomainObject) {
		if(null == pDomainObject || null == pDataCarrier) {
			return pDomainObject;
		}
		mapToDomainObject((ChangeableDataCarrier) pDataCarrier,  pDomainObject);
		
		return pDomainObject;
	}
	
//	protected <T extends VersionizedDomainObject> T mapToDomainObject(final Doc41DataCarrier pDataCarrier, T pDomainObject) throws Doc41RepositoryException {
//		if(null == pDomainObject || null == pDataCarrier) {
//			return pDomainObject;
//		}
//		
//		checkVersionsAndIncrease(pDataCarrier, pDomainObject.getVersionNumber());
//		mapToDomainObject(pDataCarrier, (DomainObject) pDomainObject);
//		
//		return pDomainObject;
//	}      
	/* Doc41DataCarrier */
    
    /**
     * Synchronizes two data carriers timestamps.
     * @param fromDC copy timestamps from this data carrier
     * @param toDC target data carrier
     */
    protected void synchronizeDataCarriers(ChangeableDataCarrier fromDC, ChangeableDataCarrier toDC) {
        toDC.setChanged(fromDC.getChanged());
        toDC.setChangedBy(fromDC.getChangedBy());
        
        toDC.setCreated(fromDC.getCreated());
        toDC.setCreatedBy(fromDC.getCreatedBy());
    }


    protected Long integer2Long(Integer pI) {
        if(pI == null) {
            return null;
        }
        return new Long(pI.longValue());
    }


    protected Integer long2Integer(Long pL) {
        if(pL == null) {
            return null;
        }
        return new Integer(pL.intValue());
    }
    
    protected int long2Int(Long pL) {
        if(pL == null) {
            return 0;
        }
        return pL.intValue();
    }
    
    protected int String2Int(String p) {
        if (p == null) {
            return 0;
        }
        try {
            return Integer.parseInt(p);
        } catch (Exception e) {
            return 0;
        }
    }
    
    protected int long2Int(Long pL, int nullValue) {
        if(pL == null) {
            return nullValue;
        }
        return pL.intValue();
    }
    
    protected Long int2Long(int intValue, int nullValue) {
        if(intValue == nullValue) {
            return null;
        }
        return new Long(intValue);
    }
}
