package com.bayer.bhc.doc41webui.integration.db;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.paging.FilterPagingRequest;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.integration.db.dc.InterfaceDetailDC;
import com.bayer.bhc.doc41webui.integration.db.dc.MonitoringContactDC;
import com.bayer.bhc.doc41webui.integration.db.dc.MonitoringHistoryDC;

/**
 * Monitoring DAO implementation.
 * 
 * @author mbghy
 */
@Component
public class MonitoringDAO extends AbstractDAOImpl{

	private static final String TEMPLATE_COMPONENT_NAME	= "MonitoringDAO";	
	private static final String FIND_ALL_LATEST_MONITORING_ENTIRES = "findLatestMonitoringEntries";

	private static final String FIND_CANACT_PERSON = "findContactPersonBYInterface";

	private static final String INTERFACE_NAME = "INTERFACE_NAME";

	private static final String FIND_MONITORING_HISTORY = "findMonitoringHistoryByInterface";

	private static final String CONTACT_TYPE = "CONTACT_TYPE";

	private static final String FIND_INTERFACE_DETAILS = "findInterfaceDetailsByName";

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.integration.db.MonitoringDAO#editContactPerson(com.bayer.bms.ccp.integration.db.dc.MonitoringContactDC)
	 */
	public void editContactPerson(MonitoringContactDC contactDC)
			throws Doc41TechnicalException {
		store(contactDC);
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.integration.db.MonitoringDAO#findAllLatestMonitoringEntries()
	 */
	public List<MonitoringHistoryDC> findAllLatestMonitoringEntries() throws Doc41TechnicalException {
		return find(null, null, FIND_ALL_LATEST_MONITORING_ENTIRES,MonitoringHistoryDC.class);
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.integration.db.MonitoringDAO#findContactPersonByInterface(java.lang.String, java.lang.String)
	 */
	public MonitoringContactDC findContactPersonByInterface(String interfaceName, String contactType)
			throws Doc41TechnicalException {
		MonitoringContactDC contactDC=MonitoringContactDC.newInstanceOfMonitoringContactDC();
		String[] pParameterNames = { INTERFACE_NAME,CONTACT_TYPE};
		Object[] pParameterValues = {interfaceName, contactType };
		List<MonitoringContactDC> result = find(pParameterNames, pParameterValues,FIND_CANACT_PERSON, MonitoringContactDC.class);
		if(result!=null &&!result.isEmpty()){
			contactDC=result.get(0);
		}
		return contactDC;
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.integration.db.MonitoringDAO#findMonitoringHistoryByInterface(com.bayer.bms.ccp.domain.container.MonitoringPagingRequest)
	 */
	public PagingResult<MonitoringHistoryDC> findMonitoringHistoryByInterface(
			FilterPagingRequest pagingRequest) throws Doc41TechnicalException {
		return findByFilter(pagingRequest, FIND_MONITORING_HISTORY,MonitoringHistoryDC.class);
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.integration.db.MonitoringDAO#findUserById(java.lang.Long)
	 */
	public MonitoringContactDC findUserById(Long userId)
			throws Doc41TechnicalException {
		return (MonitoringContactDC) find(MonitoringContactDC.class, userId);
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.integration.db.MonitoringDAO#addInterface(com.bayer.bms.ccp.integration.db.dc.InterfaceDetailsDC)
	 */
	public void addInterface(InterfaceDetailDC detailsDC) throws Doc41TechnicalException {
		store(detailsDC);
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.integration.db.MonitoringDAO#findInterfaceDetailsByName(java.lang.String)
	 */
	public InterfaceDetailDC findInterfaceDetailsByName(String pName) throws Doc41TechnicalException {
		InterfaceDetailDC detailsDC=InterfaceDetailDC.newInstanceOfInterfaceDetailDC();
		String[] pParameterNames = { INTERFACE_NAME};
		Object[] pParameterValues = {pName};
		List<InterfaceDetailDC> result = find(pParameterNames, pParameterValues,FIND_INTERFACE_DETAILS, InterfaceDetailDC.class);
		if(result!=null&&!result.isEmpty()){
			detailsDC=result.get(0);
		}
		return detailsDC;
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.integration.db.MonitoringDAO#addContactPerson(com.bayer.bms.ccp.integration.db.dc.MonitoringContactDC)
	 */
	public void addContactPerson(MonitoringContactDC conactDC) throws Doc41TechnicalException {
		store(conactDC);		
	}

	@Override
	public String getTemplateComponentName() {
		return TEMPLATE_COMPONENT_NAME;
	}

}
