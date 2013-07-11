package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.container.DocumentStatus;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

public class GetDocStatusRFC extends AbstractDoc41RFC<DocumentStatus>{
	private static final String IN_COREP = "IV_COREP";
	private static final String IN_DOCID = "IV_DOCID";
	
	private static final String OUT_DOCSTATUS = "EV_DOCSTATUS";
	private static final String OUT_AR_DATE = "EV_ARDATE";
	private static final String OUT_AR_TIME = "EV_ARTIME";
	
	private static final String OUT_RETURNCODE = "EV_RETURNCODE";
	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetDocStatusRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String corep = (String) pInputParms.get(0);
                String docId = (String) pInputParms.get(1);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_COREP,corep);
				sapInput.setValue(IN_DOCID,docId);
            } else {
                throw new SAPException(
                        "GetDocStatusRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "GetDocStatusRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(GetDocStatusRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<DocumentStatus> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(GetDocStatusRFC.class, null, "processResult():ENTRY");
		ArrayList<DocumentStatus> mResult = new ArrayList<DocumentStatus>();
        if (pFunction != null) {
//            processReturnTable(pFunction);
            checkReturnCode(pFunction, OUT_RETURNCODE, null);
            JCoParameterList exportParameterList = pFunction.getExportParameterList();
            DocumentStatus docStat = new DocumentStatus();
            docStat.setStatus(exportParameterList.getString(OUT_DOCSTATUS));

            Date artime = exportParameterList.getTime(OUT_AR_TIME);
            Date ardate = exportParameterList.getDate(OUT_AR_DATE);

            docStat.setArchivingDate(mergeSapDateTime(ardate,artime));
            mResult.add(docStat);
        }
        Doc41Log.get().debug(GetDocStatusRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
