package com.bayer.bhc.doc41webui.service.mapping;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.domain.Monitor;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.integration.db.dc.InterfaceDetailDC;
import com.bayer.bhc.doc41webui.integration.db.dc.MonitoringContactDC;
import com.bayer.bhc.doc41webui.integration.db.dc.MonitoringHistoryDC;

/**
 * MonitoringMapper implementation
 * 
 * @author MBGHY
 */
@Component
public class MonitoringMapper extends AbstractMapper{

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.service.mapping.MonitoringMapper#mapToDc(com.bayer.bms.ccp.domain.Monitoring, com.bayer.bms.ccp.integration.db.dc.MonitoringHistoryDC)
	 */
	public MonitoringHistoryDC mapToDc(Monitor monitoring,MonitoringHistoryDC historyDC) {
		if(monitoring!=null){
			super.mapToDataCarrier(monitoring, historyDC);
			historyDC.setActionType(monitoring.getAction());
			historyDC.setInterfaceName(monitoring.getName());
			//historyDC.setStatus(monitoring.getStatus());
			historyDC.setActionRemarks(monitoring.getRemarks());
		}
		return historyDC;
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.service.mapping.MonitoringMapper#mapToDc(com.bayer.bms.ccp.domain.User, com.bayer.bms.ccp.integration.db.dc.MonitoringContactDC)
	 */
	public MonitoringContactDC mapToDc(User user, MonitoringContactDC contactDC) {
		if(user!=null){
			super.mapToDataCarrier(user, contactDC);
			contactDC.setFirstName(user.getFirstname());
			contactDC.setLastName(user.getSurname());
			contactDC.setCwid(user.getCwid());
			contactDC.setEmail(user.getEmail());
			contactDC.setContactType(user.getType());
			contactDC.setPhone1(user.getPhone());
			contactDC.setChangedBy(user.getChangedBy());
			}
		return contactDC;
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.service.mapping.MonitoringMapper#mapToDO(com.bayer.bms.ccp.integration.db.dc.InterfaceDetailsDC)
	 */
	public Monitor mapToDO(InterfaceDetailDC detailsDC) {
		Monitor monitoring=new Monitor();
		super.mapToDomainObject(detailsDC, monitoring);
		monitoring.setDcId(detailsDC.getObjectID());
		monitoring.setName(detailsDC.getInterfaceName());
		monitoring.setDesc(detailsDC.getInterfaceDescription());
		return monitoring;
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.service.mapping.MonitoringMapper#mapToDO(com.bayer.bms.ccp.integration.db.dc.MonitoringHistoryDC)
	 */
	public Monitor mapToDO(MonitoringHistoryDC historyDC) {
		Monitor monitoring=new Monitor();
		super.mapToDomainObject(historyDC, monitoring);
		monitoring.setDcId(historyDC.getObjectID());
		monitoring.setName(historyDC.getInterfaceName());
		monitoring.setAction(historyDC.getActionType());
		monitoring.setDetails(historyDC.getActionDetails());
		if(historyDC.getActionStatus().equals(Doc41Constants.MONITORING_SUCCESS)){
			monitoring.setStatus(true);
		}
		monitoring.setRemarks(historyDC.getActionRemarks());
//		monitoring.setLastRequest(historyDC.getLatestRequest());
		monitoring.setResponseTime(historyDC.getResponseTime());
		return monitoring;
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.service.mapping.MonitoringMapper#mapToUser(com.bayer.bms.ccp.integration.db.dc.MonitoringContactDC)
	 */
	public User mapToUser(MonitoringContactDC contactDC) {
		User user=new User();
		if(contactDC!=null){
			super.mapToDomainObject(contactDC, user);
			user.setDcId(contactDC.getObjectID());
			user.setFirstname(contactDC.getFirstName());
			user.setSurname(contactDC.getLastName());
			user.setCwid(contactDC.getCwid());
			user.setEmail(contactDC.getEmail());
			user.setType(contactDC.getContactType());
			user.setPhone(contactDC.getPhone1());
			user.setChangedBy(contactDC.getChangedBy());
			user.setCreatedBy(contactDC.getCreatedBy());
		}
		return user;
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.service.mapping.MonitoringMapper#mapToDc(com.bayer.bms.ccp.domain.Monitoring, com.bayer.bms.ccp.integration.db.dc.InterfaceDetailsDC)
	 */
	public InterfaceDetailDC mapToDc(Monitor monitoring, InterfaceDetailDC detailsDC) {
		if(monitoring!=null){
			super.mapToDataCarrier(monitoring, detailsDC);
			detailsDC.setInterfaceName(monitoring.getName());
			detailsDC.setInterfaceDescription(monitoring.getDesc());
		}
		return detailsDC;
	}

	/* (non-Javadoc)
	 * @see com.bayer.bms.ccp.service.mapping.MonitoringMapper#mapToDc(com.bayer.bms.ccp.domain.User)
	 */
	public MonitoringContactDC mapToDc(User user) {
		MonitoringContactDC contactDC=new MonitoringContactDC();	
		if(user!=null){
			super.mapToDataCarrier(user, contactDC);
			contactDC.setInterfaceName(user.getCompany());
			contactDC.setContactType(user.getType());
			contactDC.setFirstName(user.getFirstname());
			contactDC.setLastName(user.getSurname());
			contactDC.setCwid(user.getCwid());
			contactDC.setEmail(user.getEmail());
			contactDC.setContactType(user.getType());
			contactDC.setPhone1(user.getPhone());
			}
		return contactDC;
	}

}
