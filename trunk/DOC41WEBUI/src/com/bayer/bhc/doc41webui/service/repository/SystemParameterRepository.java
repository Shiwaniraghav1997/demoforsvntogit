package com.bayer.bhc.doc41webui.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41RepositoryException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.integration.db.SysParamDAO;
import com.bayer.bhc.doc41webui.integration.db.dc.masterdata.MDSysParamDC;

@Component
public class SystemParameterRepository {
	
	private static Boolean dbPersistence = Boolean.FALSE;
	private static long lastRefreshStamp = 0L;
	private static long REFRESH_TIMEOUT = 30000;  // 30 seconds
	
	public static synchronized void setLastRefreshStamp(long lastRefreshStamp) {
		SystemParameterRepository.lastRefreshStamp = lastRefreshStamp;
	}
	
	@Autowired
	private SysParamDAO sysParamDAO;
	
	/* Parameter Names */
	private static final String PARAM_NAME_DB_PERSISTENCE	= "DB_SESSION_PERSISTENCE";
	
	
	
	public Boolean isDBSessionPersistence() throws Doc41RepositoryException {
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
