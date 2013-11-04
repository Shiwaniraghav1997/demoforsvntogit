package com.bayer.bhc.doc41webui.integration.db;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bbs.aila.AilaAccess;
import com.bayer.bbs.aila.exception.AilaException;
import com.bayer.bbs.aila.model.AILAPerson;
import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41InvalidPasswordException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.service.Doc41MonitorService;
import com.bayer.ecim.foundation.basic.ConfigMap;

/**
 * LDAP DAO implementation.
 * 
 * @author ezzqc
 */
@Component
public class LdapDAO {
	
	@Autowired
	private Doc41MonitorService monitorService;

	public static final String LDAP_KEY = "Ldap";


	private static final String CREATE_USER = "createUser";

	private static final String UPDATE_USER = "updateUser";

	private static final String SET_PASSWORD = "setPassword";

	private static final String GROUP_EXU ="doc41_exu";
	private static final String GROUP_INT_PROD ="doc41_int";
	private static final String GROUP_INT_QA ="DOC41_INT_QA";
	private static final String GROUP_LOG_PROD ="doc41_log";
	private static final String GROUP_LOG_QA ="DOC41_LOG_QA";
	

	private AilaAccess getAilaAccess() throws AilaException {
		String stageInfo = ConfigMap.get().getHostId().toUpperCase();
		if (stageInfo.startsWith("PROD")) {
			return new AilaAccess(AilaAccess.APP_DOC41, AilaAccess.ACCESS_PROD); 
		} else {
			return new AilaAccess(AilaAccess.APP_DOC41, AilaAccess.ACCESS_QA); 
		}
	}
	
	private boolean isDevSystem(){
		String stageInfo = ConfigMap.get().getHostId().toUpperCase();
		return stageInfo.startsWith("DEV") || stageInfo.startsWith("WORKSTATION");
	}
	
	private boolean isProdSystem(){
		String stageInfo = ConfigMap.get().getHostId().toUpperCase();
		return stageInfo.startsWith("PROD");
	}
	
	private String getGroupName(boolean internal,boolean log){
		if(internal){
			if(isProdSystem()){
				if(log){
					return GROUP_LOG_PROD;
				} else {
					return GROUP_INT_PROD;
				}
			} else {
				if(log){
					return GROUP_LOG_QA;
				} else {
					return GROUP_INT_QA;
				}
			}
		} else {
			if(log){
				throw new IllegalArgumentException("external users have no log group");
			} else {
				return GROUP_EXU;
			}
		}
	}
	
	public boolean isInternalUserAuthenticated(String cwid, String password) throws Doc41TechnicalException {
		try {
			return getAilaAccess().isAuthenticated(cwid.trim(), password.trim());
		} catch (AilaException e) {
			return false;
		}
	}
	
    public AILAPerson lookupUser(String cwid) throws Doc41TechnicalException  {
		try {
			return getAilaAccess().getIntranetUser(cwid, UserInSession.getCwid());
			
		} catch (AilaException e) {
			throw new Doc41TechnicalException(this.getClass(), "lookupUser", e);
		}
    }

    public AILAPerson createUser(AILAPerson person) throws Doc41TechnicalException {
    	if(isDevSystem()){ //can be replaced by dummy implementation with some database sequence for the cwid
    		if (StringUtils.equals(person.getFirstName(), "Donald") && StringUtils.equals(person.getLastName(), "Demo")) {
    			person.setCwid("DDEMO");
    			return person;
    		}
    		throw new Doc41TechnicalException(getClass(), "no createUser on DEV or WORKSTATION");
    	}
    	long startTime=0;
        long endTime=0;
    	String logDetails = ""+person.getFirstName()+" "+person.getLastName();
		try {
    		startTime=System.currentTimeMillis();
        	AILAPerson newPerson = getAilaAccess().createUser(person, UserInSession.getCwid());
        	endTime=System.currentTimeMillis();
        	getMonitorService().monitor(LDAP_KEY, CREATE_USER, new Date(), Doc41Constants.MONITORING_SUCCESS, null,logDetails,endTime-startTime);
            return newPerson;
        } catch (AilaException e) {
        	endTime=System.currentTimeMillis();
            getMonitorService().monitor(LDAP_KEY, CREATE_USER, new Date(), Doc41Constants.MONITORING_FAILURE, e.getMessage(),logDetails,endTime-startTime);
            throw new Doc41TechnicalException(this.getClass(), "createUser", e);
        }
    }
    
    public void addInternalUserToGroup(String cwid) throws Doc41TechnicalException {
    	addInternalUserToGroup(cwid,false);
    }
    
    public void addInternalUserToLogGroup(String cwid) throws Doc41TechnicalException {
    	addInternalUserToGroup(cwid,true);
    }

    private void addInternalUserToGroup(String cwid,boolean logGroup) throws Doc41TechnicalException {
    	if(isDevSystem()){
    		return;
    	}
    	try {
    		getAilaAccess().addIntranetUserToGroup(cwid, getGroupName(true, logGroup), UserInSession.getCwid());
		} catch (AilaException e) {
			throw new Doc41TechnicalException(this.getClass(), "addInternalUserToGroup", e);
		}
    }
    
    public void addExternalUserToGroup(String cwid) throws Doc41TechnicalException {
    	if(isDevSystem()){
    		return;
    	}
    	try {
    		String ldapGroup = getGroupName(false, false);
			if(!getAilaAccess().isMember(cwid, ldapGroup , UserInSession.getCwid())){
    			getAilaAccess().addUserToGroup(cwid, ldapGroup, UserInSession.getCwid());
    		}
		} catch (AilaException e) {
			throw new Doc41TechnicalException(this.getClass(), "addExternalUserToGroup", e);
		}
    }
    
    public void removeInternalUserFromGroup(String cwid) throws Doc41TechnicalException {
    	 removeInternalUserFromGroup(cwid,false);
    }
    
    public void removeInternalUserFromLogGroup(String cwid) throws Doc41TechnicalException {
    	removeInternalUserFromGroup(cwid,true);
    }
    
    private void removeInternalUserFromGroup(String cwid,boolean logGroup) throws Doc41TechnicalException {
    	if(isDevSystem()){
    		return;
    	}
    	try {
    		getAilaAccess().removeIntranetUserFromGroup(cwid, getGroupName(true, logGroup), UserInSession.getCwid());
		} catch (AilaException e) {
			throw new Doc41TechnicalException(this.getClass(), "addInternalUserToGroup", e);
		}
    }
    public void removeExternalUserFromGroup(String cwid) throws Doc41TechnicalException {
    	if(isDevSystem()){
    		return;
    	}
    	try {
    		String ldapGroup = getGroupName(false, false);
    		if(getAilaAccess().isMember(cwid, ldapGroup, UserInSession.getCwid())){
    			getAilaAccess().removeUserFromGroup(cwid, ldapGroup, UserInSession.getCwid());
    		}
		} catch (AilaException e) {
			throw new Doc41TechnicalException(this.getClass(), "addExternalUserToGroup", e);
		}
    }

    public void updateUser(AILAPerson person) throws Doc41TechnicalException {
    	if(isDevSystem()){
    		return;
    	}
    	long startTime=0;
        long endTime=0;
    	try {
    		startTime=System.currentTimeMillis();
        	getAilaAccess().modifyUser(person, UserInSession.getCwid());
        	endTime=System.currentTimeMillis();
            getMonitorService().monitor(LDAP_KEY, UPDATE_USER, new Date(), Doc41Constants.MONITORING_SUCCESS, null,person.getCwid(),endTime-startTime);
        } catch (AilaException e) { 
        	endTime=System.currentTimeMillis();
            getMonitorService().monitor(LDAP_KEY, UPDATE_USER, new Date(), Doc41Constants.MONITORING_FAILURE, e.getMessage(),person.getCwid(),endTime-startTime);
            throw new Doc41TechnicalException(this.getClass(), "updateUser", e);
        }
    }

    public void setPassword(String cwid, String password) throws Doc41TechnicalException, Doc41InvalidPasswordException {
    	if(isDevSystem()){
    		return;
    	}
    	long startTime=0;
        long endTime=0;
    	try {
    		startTime=System.currentTimeMillis();
        	getAilaAccess().setPassword(cwid, password, UserInSession.getCwid());
        	
            String currentUser = UserInSession.getCwid();
//            // password will be made invalid if the changed user is not the logged on user
//            // that means that the user will be forced to enter a new password when he logs in again
            if (!currentUser.equals(cwid)) {
            	getAilaAccess().setPasswordInvalid(cwid, UserInSession.getCwid());
            }
            endTime=System.currentTimeMillis();
            getMonitorService().monitor(LDAP_KEY, SET_PASSWORD, new Date(), Doc41Constants.MONITORING_SUCCESS, null,cwid,endTime-startTime);
        } catch (AilaException e) {
        	endTime=System.currentTimeMillis();
        	getMonitorService().monitor(LDAP_KEY, SET_PASSWORD, new Date(), Doc41Constants.MONITORING_FAILURE, e.getMessage(),cwid,endTime-startTime);
        	throw new Doc41TechnicalException(this.getClass(), "LdapDAOImpl.setPassword", e);
        }
    }
    
	/**
	 * @return the monitorService
	 */
	public Doc41MonitorService getMonitorService() {
		return monitorService;
	}

	/**
	 * @param monitorService the monitorService to set
	 */
	public void setMonitorService(Doc41MonitorService monitorService) {
		this.monitorService = monitorService;
	}
}
