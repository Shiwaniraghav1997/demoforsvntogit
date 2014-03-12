package com.bayer.bhc.doc41webui.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41RepositoryException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.integration.db.SysParamDAO;
import com.bayer.bhc.doc41webui.integration.db.dc.masterdata.MDSysParamDC;

@Component
public class SystemParameterRepository {
    /* Parameter Names */
    private static final String PARAM_NAME_DB_PERSISTENCE   = "DB_SESSION_PERSISTENCE";
    
    private static final long REFRESH_TIMEOUT = 30000;  // 30 seconds
    
	private Boolean dbPersistence = Boolean.FALSE;
	private long lastRefreshStamp = 0L;
	
	@Autowired
    private SysParamDAO sysParamDAO;
	
	public synchronized void setLastRefreshStamp(long lastRefreshStamp) {
		this.lastRefreshStamp = lastRefreshStamp;
	}
	
	public synchronized Boolean isDBSessionPersistence() throws Doc41RepositoryException {
		if ((System.currentTimeMillis() - lastRefreshStamp) > REFRESH_TIMEOUT) {
		
			try {
				MDSysParamDC dc 	= sysParamDAO.getSysParamDC(PARAM_NAME_DB_PERSISTENCE);
				Boolean isDBPersistence	= dc.getParamBooleanvalue();
				
				setLastRefreshStamp(System.currentTimeMillis());
				dbPersistence=isDBPersistence;
				return dbPersistence;
			} catch (Doc41TechnicalException e) {
				throw new Doc41RepositoryException("Error during isDBSessionPersistence.", e);
			}
		} 
		return dbPersistence;
	}
	
	/**
	 * @return New (unique) Object-ID
	 * @throws Doc41RepositoryException
	 */
	public Long getOID() throws Doc41RepositoryException {
		try {
			return sysParamDAO.getOID();
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException("Error during getOID.", e);
		}
	}
	
}
