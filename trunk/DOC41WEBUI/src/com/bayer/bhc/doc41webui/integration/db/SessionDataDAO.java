/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.integration.db;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.ecim.foundation.business.sbcommon.SessionDataDC;
import com.bayer.ecim.foundation.dbx.StoreException;

/**
 * SessionDataDAO implementation.
 * 
 * @author imrol
 * @id $Id: DisplaytextDAOImpl.java,v 1.2 2012/02/22 14:16:09 ezzqc Exp $
 */
@Component
public class SessionDataDAO extends AbstractDAOImpl{
	private static final String TEMPLATE_COMPONENT_NAME	= "sessionData";	
	//2hours lifetime of session (trigger sets ends of life based on lifetime on seconds)
	private static long SESSION_LIFE_TIME=60*60*4;
	
	public static long getSessionLifeTime(){
		return SESSION_LIFE_TIME;
	}
	
	@Override
	public String getTemplateComponentName() {		
		return TEMPLATE_COMPONENT_NAME;
	}
	
	@Override
	protected void checkUser() throws Doc41TechnicalException {
		// no nothing !
	}
 
    public SessionDataDC getSessionData(String pCwid) throws Doc41TechnicalException{
		try {
			String[] parameterNames			= { "COMPONENT","ID" };
	        Object[] parameterValues		= {  TEMPLATE_COMPONENT_NAME,pCwid };
	        String templateName				= "getSessionData";
	        Class<SessionDataDC> dcClass		= SessionDataDC.class;        
	        
	        SessionDataDC dc			= findDC(parameterNames, parameterValues, templateName, dcClass);	                		
			
			return dc;
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "getSessionData", e);
		}
    }

    public SessionDataDC store(SessionDataDC pDC) throws Doc41TechnicalException{
        try {
        	pDC.setComponent(TEMPLATE_COMPONENT_NAME);
        	pDC.setChanged(new Date());
        	pDC.setTimetolive(SESSION_LIFE_TIME);
            return (SessionDataDC) getOTS().storeDC(pDC);
        } catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "Error during database access.", e);
        }
    	
    }

}
