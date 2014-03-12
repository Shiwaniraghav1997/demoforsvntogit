/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.integration.db;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.ecim.foundation.dbx.DBObjectAccess;
import com.bayer.ecim.foundation.dbx.StorableDataCarrier;
import com.bayer.ecim.foundation.dbx.TransactionFailedException;

/**
 * Transaction Manager implementation.
 * 
 * @author ezzqb
 * @id $Id: Doc41TransactionManagerImpl.java,v 1.3 2012/02/22 14:16:09 ezzqc Exp $
 */
@Component
public class Doc41TransactionManager {
    
    // key: trxId -> Cache (Map: objectId, DC)
    static Map<String,Map<Object,Object>> transctionalCache = new Hashtable<String, Map<Object,Object>>();
    
    // no need to have synchronized methods, as each cache is 'serialized' via the txid
    //  (there can't be two threads on the same transaction)

    public final void putToCache(int txid, StorableDataCarrier dc) {
        if (dc != null) {
            String cacheKey = txid+"";
            Map<Object,Object> cache = transctionalCache.get(cacheKey);
            if (cache != null) {
                cache.put(dc.getObjectID(), dc);
                //System.out.println("put to cache: "+dc.getClass().getName()+ dc.getObjectID());
            }
        }
    }
    
    public final void putToCache(int txid, List<? extends StorableDataCarrier> dcs) {
        if (dcs != null) {
            String cacheKey = txid+"";
            Map<Object,Object> cache = transctionalCache.get(cacheKey);
            if (cache != null) {
            	for (StorableDataCarrier dc : dcs) {
                    cache.put(dc.getObjectID(), dc);
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public final <T extends StorableDataCarrier> T findInCache(int txid, Long objectId) {
        if (objectId != null) {
            String cacheKey = txid+"";
            Map<Object,Object> cache = transctionalCache.get(cacheKey);
            if (cache != null) {                
				return (T)cache.get(objectId);
            }
        }
        return null;
    }
    
    public final void removeFromCache(int txid, StorableDataCarrier dc) {
        if (dc != null) {
            removeFromCache(txid, dc.getObjectID());
        }
    }
    
    public final void removeFromCache(int txid, Long objectId) {
        if (objectId != null) {
            String cacheKey = txid+"";
            Map<Object,Object> cache = transctionalCache.get(cacheKey);
            if (cache != null) {
                cache.remove(objectId);
            }
        }
    }
    
    private final Long destroyCache(int txid) {
        String cacheKey = txid+"";
        Map<Object,Object> cache = transctionalCache.get(cacheKey);
        if (cache != null) {
            cache.clear();
            transctionalCache.remove(cacheKey);
            return (Long)cache.get("t0");
        }
        return null;
    }
    
    
    private DBObjectAccess getDBOX() {
        return (DBObjectAccess) DBObjectAccess.get();
    }
    
    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.Doc41TransactionManager#beginTransaction(boolean)
     */
    public int beginTransaction(boolean withCache) throws Doc41TechnicalException {
        try {
            int txid = getDBOX().beginTransaction();
            if (withCache) {
            	Map<Object,Object> trxCache = new HashMap<Object, Object>();
                trxCache.put("t0", Long.valueOf(System.currentTimeMillis()));
                transctionalCache.put(txid+"", trxCache);
            }
            return txid;
        } catch (TransactionFailedException e) {
            throw new Doc41TechnicalException(this.getClass(), "Error during beginTransction.", e);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.Doc41TransactionManager#endTransaction(int)
     */
    public final void endTransaction(int txid) throws Doc41TechnicalException {
        try {
            getDBOX().endTransaction(txid);
            Long t0 = destroyCache(txid);
            if (t0 != null) {
                Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(), "trx duration: : "+txid+"-"+ (System.currentTimeMillis()-t0.longValue()));
            }
        } catch (TransactionFailedException e) {
            throw new Doc41TechnicalException(this.getClass(), "Error during endTransction.", e);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.Doc41TransactionManager#abortTransaction(int)
     */
    public final void abortTransaction(int txid) {
        getDBOX().abortTransaction(txid);
        destroyCache(txid);
    }
}
