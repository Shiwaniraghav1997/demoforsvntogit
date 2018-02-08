package com.bayer.bhc.doc41webui.integration.db;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.integration.db.dc.masterdata.MDSysParamDC;

@Component
public class SysParamDAO extends AbstractDAOImpl {
	private static final String TEMPLATE_COMPONENT_NAME	= "sysParam";	
	
	@Override
	public String getTemplateComponentName() {		
		return TEMPLATE_COMPONENT_NAME;
	}
	
	private static final String GET_SYS_PARAMETER		= "getSysParameter";
	private static final String GET_OID					= "getOID";
	
	public MDSysParamDC getSysParamDC(String sysParamName) throws Doc41TechnicalException {
		String[] parameterNames			= { "PARAM_NAME" };
        Object[] parameterValues		= { sysParamName };
        String templateName				= GET_SYS_PARAMETER;
        Class<MDSysParamDC> dcClass		= MDSysParamDC.class;        
        
        MDSysParamDC dc = findDC(parameterNames, parameterValues, templateName, dcClass);	                		
		
		return dc;
	}
	
	public MDSysParamDC store(MDSysParamDC sysParamDC) throws Doc41TechnicalException {
		return super.store(sysParamDC);
	}
	
	public Long getOID() throws Doc41TechnicalException {
		String[] parameterNames			= {  };
        Object[] parameterValues		= {  };
        String templateName				= GET_OID;
        Class<MDSysParamDC> dcClass		= MDSysParamDC.class;        
        
        MDSysParamDC dc = findDC(parameterNames, parameterValues, templateName, dcClass);	                		
		return dc.getObjectID();
	}
}
