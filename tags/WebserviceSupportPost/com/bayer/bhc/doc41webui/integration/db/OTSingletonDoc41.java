/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.integration.db;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.ecim.foundation.basic.Dbg;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.ReflectTool;
import com.bayer.ecim.foundation.dbx.DBObjectAccess;
import com.bayer.ecim.foundation.dbx.DeleteException;
import com.bayer.ecim.foundation.dbx.OTSingletonDB;
import com.bayer.ecim.foundation.dbx.QueryException;
import com.bayer.ecim.foundation.dbx.StorableDataCarrier;
import com.bayer.ecim.foundation.dbx.StoreException;
import com.bayer.ecim.foundation.web.usermanagementN.OTUserManagementN;

/**
 * OTSingletonDB for Doc41.
 * 
 * @author ezzqc
 */
public class OTSingletonDoc41 extends OTSingletonDB {
    public static final String ID = "OTSingletonDoc41";

    /**
     * @param pSingletonId
     * @throws InitException
     */
    public OTSingletonDoc41(String pSingletonId) throws InitException {
        super(pSingletonId);
        try {
            dbg0 = Dbg.get().getChannelByName("USRB_DBG0", "USRB_DBG0", "usr.sl.dbg0", true);
            dbg1 = Dbg.get().getChannelByName("USRB_DBG1", "USRB_DBG1", "usr.sl.dbg1", false);
            dbg2 = Dbg.get().getChannelByName("USRB_DBG2", "USRB_DBG2", "usr.sl.dbg2", false);
            cCache.setLogChannel( dbg1 );
            DBObjectAccess.get().checkModuleRequiredVersion( "Foundation-UserMgmt", 1, 24 );
            initSucceeded( OTUserManagementN.class );
        } catch (Exception mEx) {
            initFailed( new InitException( "Failed to initialize " + cClassName + "!", mEx) );
        }
    }


    /**
     * Getter methode with fallback, if no instanciation class was defined to Singleton by properties.
     * @return
     * @throws InitException
     */
    public static OTSingletonDoc41 get() throws InitException {
        OTSingletonDoc41 mSing = (OTSingletonDoc41) getSingleton(ID);
        return (mSing != null) ? mSing : new OTSingletonDoc41(ID);
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    public <T extends StorableDataCarrier> T storeTransactional(int pTxId, T pDc) throws StoreException {
    	return (T) super.storeDC(pTxId, pDc);        
    }   
    
	@SuppressWarnings("unchecked")
    public <T extends StorableDataCarrier> List<T> storeTransactional(int pTxId, ArrayList<T> pDcList) throws StoreException {
		return (List<T>) super.storeDCs(pTxId, pDcList);        
    }
    
    public <T extends StorableDataCarrier> void deleteTransactional(int pTxId, T pDc) throws DeleteException {
        super.deleteDC(pTxId, pDc);
    }
    
    public void deleteTransactional(int pTxId, Class<? extends StorableDataCarrier> pClazz, Long pObjectId) throws DeleteException, Doc41TechnicalException {
        try {
            super.deleteDCById(pTxId, (StorableDataCarrier) ReflectTool.newInstance(pClazz), pObjectId);
        } catch (InitException e) {
            throw new Doc41TechnicalException(this.getClass(), "deleteTransactional failed.", e);
        } catch (InstantiationException e) {
            throw new Doc41TechnicalException(this.getClass(), "deleteTransactional failed.", e);
        } catch (IllegalAccessException e) {
            throw new Doc41TechnicalException(this.getClass(), "deleteTransactional failed.", e);
        }
    }
    
    /* (non-Javadoc)
     * @see com.bayer.ecim.foundation.dbx.OTSingletonDB#deleteDCById(com.bayer.ecim.foundation.dbx.StorableDataCarrier, java.lang.Long)
     */
    public void deleteDCById(Class<? extends StorableDataCarrier> pClazz, Long pObjectId) throws DeleteException, Doc41TechnicalException {
        try {
            super.deleteDCById((StorableDataCarrier) ReflectTool.newInstance(pClazz), pObjectId);
        } catch (InitException e) {
            throw new Doc41TechnicalException(this.getClass(), "deleteById failed.", e);
        } catch (InstantiationException e) {
            throw new Doc41TechnicalException(this.getClass(), "deleteById failed.", e);
        } catch (IllegalAccessException e) {
            throw new Doc41TechnicalException(this.getClass(), "deleteById failed.", e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T extends StorableDataCarrier> T getDCByObjectID(final Class<T> pClazz, final Long pObjectId) throws QueryException {
    	return (T) super.getDCByObjectId(pClazz, pObjectId, false);
    }
    
    /**
     * Get DataCarrier for given object id and class.
     * @param transactionId transaction id
     * @param clazz class of data carrier
     * @param objectId object id
     * @return
     * @throws QueryException 
     */
    @SuppressWarnings("unchecked")
    public <T extends StorableDataCarrier> T getDCByObjectID(final int pTxId, final Class<T> pClazz, final Long pObjectId) throws QueryException {
    	return (T) super.getDCByObjectId(pTxId, pClazz, pObjectId, false);        
    }
    
    @SuppressWarnings("unchecked")
    public <T extends StorableDataCarrier> T getDCByObjectIDForUpdate(final int pTxId, final Class<T> pClazz, final Long pObjectId) throws QueryException {
    	return (T) super.getDCByObjectId(pTxId, pClazz, pObjectId, true);        
    }
    
    
}
