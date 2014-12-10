/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.domain.VersionizedDomainObject;
import com.bayer.bhc.doc41webui.integration.db.Doc41TransactionManager;

/**
 * AbstractRepository. Base class for repositories. Uses Doc41TransactionManager from Integration layer.
 * 
 * @author ezzqc
 */
@Component
public abstract class AbstractRepository {
	@Autowired
    private Doc41TransactionManager transactionManager;

    public Doc41TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void abortTransaction(int txid) {
		getTransactionManager().abortTransaction(txid);
    }

    public int beginTransaction() throws Doc41TechnicalException {
        return getTransactionManager().beginTransaction(true);
    }
    
    public int beginTransaction(boolean withCache) throws Doc41TechnicalException {
        return getTransactionManager().beginTransaction(withCache);
    }

    public void endTransaction(int txid) throws Doc41TechnicalException {
    	getTransactionManager().endTransaction(txid);
    }
    
    /**
     * Convenience method - get DC id from the given id
     * @param id
     * @return
     */
    public Long getDcId(String id) {
        return VersionizedDomainObject.getDcId(id);
    }
    
    /**
     * Convenience method - get version id from the given id
     * @param id
     * @return
     */
    public Long getVersionId(String id) {
        return VersionizedDomainObject.getVersionId(id);
    }
    
}
