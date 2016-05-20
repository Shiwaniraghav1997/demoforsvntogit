package com.bayer.bhc.doc41webui.service.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.Doc41ErrorMessageKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41RepositoryException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.paging.FilterPagingRequest;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.domain.Monitor;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.integration.db.MonitoringDAO;
import com.bayer.bhc.doc41webui.integration.db.dc.InterfaceDetailDC;
import com.bayer.bhc.doc41webui.integration.db.dc.MonitoringHistoryDC;
import com.bayer.bhc.doc41webui.service.mapping.MonitoringMapper;

/**
 * Interface Monitoring Repository Implementation.
 * 
 * @author mbghy
 */
@Component
public class MonitoringRepository extends AbstractRepository{

	private static final String EBC_CONTACT_TYPE = "EBC";

	private static final String BACKEND_CONTACT_TYPE = "BACKEND";

	/**
     * mapper The spring managed bean<code>MonitoringMapper</code>.
     */
	@Autowired
	private MonitoringMapper mapper;
	
    /**
     * monitoringDAO The spring managed bean<code>MonitoringDAO</code>.
     */
	@Autowired
    private MonitoringDAO monitoringDAO;
    
    /**
     * @return the monitoringDAO
     */
    public MonitoringDAO getMonitoringDAO() {
        return monitoringDAO;
    }

    /**
     * @param monitoringDAO the monitoringDAO to set
     */
    public void setMonitoringDAO(MonitoringDAO monitoringDAO) {
        this.monitoringDAO = monitoringDAO;
    }

	/**
	 * @return the mapper
	 */
	public MonitoringMapper getMapper() {
		return mapper;
	}

	/**
	 * @param mapper the mapper to set
	 */
	public void setMapper(MonitoringMapper mapper) {
		this.mapper = mapper;
	}
	
    /* (non-Javadoc)
     * @see com.bayer.bms.ccp.service.repository.MonitoringRepository#findLatestMonitoringEntries()
     */
    public List<Monitor> findLatestMonitoringEntries()throws Doc41RepositoryException  {
       List<Monitor> entries = new ArrayList<Monitor>();
		List<MonitoringHistoryDC> result = null;
		try {
			result = getMonitoringDAO().findAllLatestMonitoringEntries();
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException(Doc41ErrorMessageKeys.MONITOR_FIND_LATEST_FAILED, e);
		}
		if (result != null) {
			for (MonitoringHistoryDC dc:result) {
				entries.add(getMapper().mapToDO(dc));
			}
		}
		return entries;
    }

    /* (non-Javadoc)
     * @see com.bayer.bms.ccp.service.repository.MonitoringRepository#findMonitoringHistoryByInterface(com.bayer.bms.ccp.domain.container.MonitoringPagingRequest)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public PagingResult<Monitor> findMonitoringHistoryByInterface(FilterPagingRequest pagingRequest)
			throws Doc41RepositoryException {
    	 List<Monitor> entries = new ArrayList<Monitor>();
 		PagingResult result = null;
 		try {
 			result = getMonitoringDAO().findMonitoringHistoryByInterface(pagingRequest);
 		} catch (Doc41TechnicalException e) {
 			throw new Doc41RepositoryException(Doc41ErrorMessageKeys.MONITOR_FIND_HISTORY_FAILED, e);
 		}
 		if (result != null) {
 			for (Iterator iter = result.getResult().iterator(); iter.hasNext();) {
 				entries.add(getMapper().mapToDO((MonitoringHistoryDC) iter.next()));
 			}
 			result.setResult(entries);
 		}
 		return result;
	}

    /* (non-Javadoc)
     * @see com.bayer.bms.ccp.service.repository.MonitoringRepository#editContactPerson(com.bayer.bms.ccp.domain.User)
     */
    public void editContactPerson(User user) throws Doc41RepositoryException {
		try {
			getMonitoringDAO().editContactPerson(
					getMapper().mapToDc(user,
							getMonitoringDAO().findUserById(user.getDcId())));
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException(Doc41ErrorMessageKeys.MONITOR_EDIT_CONTACT_FAILED, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.service.repository.MonitoringRepository#findBackendContactPersonByInterface(java.lang.String)
	 */
	public User findBackendContactPersonByInterface(String interfaceName) throws Doc41RepositoryException {
		try {
			return getMapper().mapToUser(
					getMonitoringDAO().findContactPersonByInterface(
							interfaceName, BACKEND_CONTACT_TYPE));
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException(Doc41ErrorMessageKeys.MONITOR_FIND_BACKEND_CONTACT_FAILED, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.service.repository.MonitoringRepository#findEBCContactPersonByInterface(java.lang.String)
	 */
	public User findEBCContactPersonByInterface(String interfaceName)throws Doc41RepositoryException {
		   try {
			return getMapper().mapToUser(
					getMonitoringDAO().findContactPersonByInterface(
							interfaceName, EBC_CONTACT_TYPE));
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException(Doc41ErrorMessageKeys.MONITOR_FIND_CONTACT_FAILED,
					e);
		}
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.service.repository.MonitoringRepository#findUserById(java.lang.Long)
	 */
	public User findUserById(Long userId) throws Doc41RepositoryException {
		try {
			return getMapper().mapToUser(getMonitoringDAO().findUserById(userId));
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException(Doc41ErrorMessageKeys.MONITOR_FIND_USER_FAILED,e);
		}
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.service.repository.MonitoringRepository#addInterface(com.bayer.bms.ccp.domain.Monitoring)
	 */
	public void addInterface(Monitor monitoring) throws Doc41RepositoryException {
		try {
		InterfaceDetailDC interfaceDetail = getMapper().mapToDc(monitoring, new InterfaceDetailDC());
		String interfaceName = interfaceDetail.getInterfaceName();
		if(findInterfaceDetailsByName(interfaceName).getName()!=null){
			throw new Doc41RepositoryException(Doc41ErrorMessageKeys.MONITOR_INTERFACE_ALREADY_EXISTS,null);
		}
		getMonitoringDAO().addInterface(interfaceDetail);
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException(Doc41ErrorMessageKeys.MONITOR_ADD_INTERFACE_FAILED,e);
		}
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.service.repository.MonitoringRepository#findInterfaceDetailsByName(java.lang.String)
	 */
	public Monitor findInterfaceDetailsByName(String pName) throws Doc41RepositoryException {
		try {
			return getMapper().mapToDO(
					getMonitoringDAO().findInterfaceDetailsByName(pName));
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException(Doc41ErrorMessageKeys.MONITOR_FIND_DETAIL_FAILED, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.service.repository.MonitoringRepository#addContactPerson(com.bayer.bms.ccp.domain.User)
	 */
	public void addContactPerson(User user) throws Doc41RepositoryException {
		try {
		//Adding new user.
		getMonitoringDAO().addContactPerson(getMapper().mapToDc(user));
		} catch (Doc41TechnicalException e) {
			throw new Doc41RepositoryException(Doc41ErrorMessageKeys.MONITOR_ADD_CONTACT_FAILED, e);
		}
	}

}
