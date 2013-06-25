/**
 * 
 */
package com.bayer.bhc.doc41webui.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.Doc41ErrorMessageKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41RepositoryException;
import com.bayer.bhc.doc41webui.common.paging.FilterPagingRequest;
import com.bayer.bhc.doc41webui.common.paging.PagingData;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.domain.Monitor;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.service.repository.MonitoringRepository;

/**
 * @author MBGHY
 *
 */
@Component
public class MonitoringUC {
	
	@Autowired
	private MonitoringRepository repository;
	
	/**
	 * @return the repository
	 */
	public MonitoringRepository getRepository() {
		return repository;
	}

	/**
	 * @param repository the repository to set
	 */
	public void setRepository(MonitoringRepository repository) {
		this.repository = repository;
	}

	public List<Monitor> getLatestMonitoringEntries() throws Doc41BusinessException {
		try {
			return getRepository().findLatestMonitoringEntries();
		} catch (Doc41RepositoryException e) {
			throw new Doc41BusinessException("getLatestMonitoringEntries",e);
		}
	}

	public PagingResult<Monitor> findMonitoringHistoryByInterface(String iName,PagingData pagingRequest,String orderBy) throws Doc41BusinessException {
		try {
			FilterPagingRequest fPagingRequest=new FilterPagingRequest(pagingRequest);
			fPagingRequest.addFiltering("INTERFACE_NAME",iName);
			fPagingRequest.addFiltering("ORDER_BY",orderBy);
			return getRepository().findMonitoringHistoryByInterface(fPagingRequest);
		} catch (Doc41RepositoryException e) {
			throw new Doc41BusinessException(Doc41ErrorMessageKeys.MONITOR_FIND_HISTORY_FAILED, e);
		}
	}

	public Monitor findInterfaceDetailsByName(String pName) throws Doc41BusinessException {
		try {
			return getRepository().findInterfaceDetailsByName(pName);
		} catch (Doc41RepositoryException e) {
			throw new Doc41BusinessException(Doc41ErrorMessageKeys.MONITOR_FIND_DETAIL_FAILED, e);
		}
	}
	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.usecase.MonitoringUC#editContactPerson(com.bayer.bms.ccp.domain.User)
	 */
	public void editContactPerson(User user) throws Doc41BusinessException {
		try {
			getRepository().editContactPerson(user);
		} catch (Doc41RepositoryException e) {
			throw new Doc41BusinessException(Doc41ErrorMessageKeys.MONITOR_EDIT_CONTACT_FAILED, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.usecase.MonitoringUC#findBackendContactPersonByInterface(java.lang.String)
	 */
	public User findBackendContactPersonByInterface(String pInterfaceName)
			throws Doc41BusinessException {
		try {
			return getRepository().findBackendContactPersonByInterface(pInterfaceName);
		} catch (Doc41RepositoryException e) {
			throw new Doc41BusinessException(Doc41ErrorMessageKeys.MONITOR_FIND_BACKEND_CONTACT_FAILED, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.usecase.MonitoringUC#findEBCContactPersonByInterface(java.lang.String)
	 */
	public User findEBCContactPersonByInterface(String pInterfaceName)
			throws Doc41BusinessException {
		try {
			return getRepository().findEBCContactPersonByInterface(pInterfaceName);
		} catch (Doc41RepositoryException e) {
			throw new Doc41BusinessException(Doc41ErrorMessageKeys.MONITOR_FIND_CONTACT_FAILED, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.usecase.MonitoringUC#findUserById(java.lang.Long)
	 */
	public User findUserById(Long userId) throws Doc41BusinessException {
		try {
			return getRepository().findUserById(userId);
		} catch (Doc41RepositoryException e) {
			throw new Doc41BusinessException(Doc41ErrorMessageKeys.MONITOR_FIND_USER_FAILED, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.usecase.MonitoringUC#addInterface(com.bayer.bms.ccp.domain.Monitoring)
	 */
	public void addInterface(Monitor monitoring) throws Doc41BusinessException {
		try {
			getRepository().addInterface(monitoring);
		} catch (Doc41RepositoryException e) {
			throw new Doc41BusinessException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.usecase.MonitoringUC#addContactPerson(com.bayer.bms.ccp.domain.User)
	 */
	public void addContactPerson(User user) throws Doc41BusinessException {
		try {
			getRepository().addContactPerson(user);
		} catch (Doc41RepositoryException e) {
			throw new Doc41BusinessException(Doc41ErrorMessageKeys.MONITOR_ADD_CONTACT_FAILED, e);
		}
		
	}
	
}
