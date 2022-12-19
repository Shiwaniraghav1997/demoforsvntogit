/**
 *(C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.integration.sap.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41AccessDeniedException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.service.Doc41MonitorService;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.sap3.SAPException;
import com.bayer.ecim.foundation.sap3.SAPSingleton;


/**
 * SAP JCO interface Abstract Implementation used by Sub Classes.
 * 
 * @author ezzqc
 */
public abstract class AbstractSAPJCOService  {
	
    
    @Autowired
	private Doc41MonitorService monitorService;
	
    public Doc41MonitorService getMonitorService() {
		return monitorService;
	}
	public void setMonitorService(Doc41MonitorService monitorService) {
		this.monitorService = monitorService;
	}
	
	public <E> List<E> performRFC(List<?> parms, String rfcName) throws Doc41ServiceException {
		return performRFC(parms, rfcName, false);
	}
	
	public <E> List<E> performRFC(List<?> parms, String rfcName, boolean checkUserWriteAccess) throws Doc41ServiceException {
		String serviceName = getClass().getSimpleName();
		List<E> result = null;
		
		if (checkUserWriteAccess && UserInSession.isReadOnly()) {
			throw new Doc41ServiceException("performRFC.accessDenied", new Doc41AccessDeniedException(this.getClass()));
		}
        long startTime=0;
        long endTime=0;
        String logDetails = paramsToLogDetails(parms);
        try {
        	startTime=System.currentTimeMillis();
        	//System.out.println("RFC:"+rfcName +" Param:"+parms);
            result = SAPSingleton.get().performRFC(rfcName, parms);
        //  System.out.println("result:"+result);
            endTime=System.currentTimeMillis();
            getMonitorService().monitor(serviceName, rfcName, new Date(), Doc41Constants.MONITORING_SUCCESS, "result count: "+result.size(),logDetails,endTime-startTime);

        } catch (InitException e) {
        	endTime=System.currentTimeMillis();
            Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), e);
            getMonitorService().monitor(serviceName, rfcName, new Date(), Doc41Constants.MONITORING_FAILURE, e.getMessage(),logDetails,endTime-startTime);
            throw new Doc41ServiceException("performRFC.initError", e);
        } catch (SAPException e) {
        	endTime=System.currentTimeMillis();
        	Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), e);
        	getMonitorService().monitor(serviceName, rfcName, new Date(), Doc41Constants.MONITORING_FAILURE, e.getMessage(),logDetails,endTime-startTime);
            throw new Doc41ServiceException("performRFC.sapError", e);
        } catch (Exception e) {
        	endTime=System.currentTimeMillis();
        	Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), e);
        	getMonitorService().monitor(serviceName, rfcName, new Date(), Doc41Constants.MONITORING_FAILURE, e.getMessage(),logDetails,endTime-startTime);
            throw new Doc41ServiceException("performRFC.communicationError ", e);
        }
        return result;
    }
	private String paramsToLogDetails(List<?> parms) {
		if(parms == null || parms.isEmpty()){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object oneParm : parms) {
			if(sb.length()>0){
				sb.append(",");
			}
			if(oneParm == null){
				sb.append("null");
			} else {
				sb.append(StringTool.maxRString(oneParm.toString(), 30, "..."));
			}
		}
		
		return StringTool.maxRString(sb.toString(),750,"...");
	}
}
