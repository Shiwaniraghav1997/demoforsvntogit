package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.container.ContentRepositoryInfo;
import com.bayer.bhc.doc41webui.container.Delivery;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class GetCrepInfoRFC extends AbstractDoc41RFC<ContentRepositoryInfo>{
	//TODO
	private static final String IN_OBJECT_TYPE = "IV_OBJECT_TYPE";
	private static final String IN_DOCTYPE = "IV_DOCTYPE";
	private static final String IN_DOC41ID = "IV_DOC41ID";
	
	private static final String OUT_CONT_REP = "EV_CONT_REP";
	private static final String OUT_DOC_CLASS = "EV_DOC_CLASS";
	
	private static final String OUT_RETURNCODE = "EV_RETURNCODE";
	
	//TODO
	private static final String RETURNCODE_OK = "OK";

	

	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetCrepInfoRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String partnerNumber = (String) pInputParms.get(0);
                String docType = (String) pInputParms.get(1);
                String doc41Id = (String) pInputParms.get(2);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_OBJECT_TYPE,partnerNumber);
				sapInput.setValue(IN_DOCTYPE,docType);
				sapInput.setValue(IN_DOC41ID,doc41Id);
            } else {
                throw new SAPException(
                        "GetCrepInfoRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "GetCrepInfoRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(GetCrepInfoRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<ContentRepositoryInfo> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(GetCrepInfoRFC.class, null, "processResult():ENTRY");
		ArrayList<ContentRepositoryInfo> mResult = new ArrayList<ContentRepositoryInfo>();
        if (pFunction != null) {
            processReturnTable(pFunction);
            JCoParameterList exportParameterList = pFunction.getExportParameterList();
            String returnCode = exportParameterList.getString(OUT_RETURNCODE);
            if(StringTool.equals(returnCode, RETURNCODE_OK)){
            	ContentRepositoryInfo crep = new ContentRepositoryInfo();
            	crep.setContentRepository(exportParameterList.getString(OUT_CONT_REP));
            	crep.setDocClass(exportParameterList.getString(OUT_DOC_CLASS));
            	mResult.add(crep);
            }
        }
        Doc41Log.get().debug(GetCrepInfoRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
