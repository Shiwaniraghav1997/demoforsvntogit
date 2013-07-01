package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

public class CreateHttpPutRFC extends AbstractDoc41RFC<URL>{
	//TODO
	private static final String IN_CREP_ID = "CREP_ID";
	private static final String IN_DOC_ID = "DOC_ID";
	
	private static final String OUT_RETURNCODE = "RETURN_CODE";
	private static final String OUT_URL = "URL";
	private static final String OUT_RETURN_MESSAGE = "RETURN_MESSAGE";
	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(CreateHttpPutRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
                String crepId = (String) pInputParms.get(0);
                String docId = (String) pInputParms.get(0);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_CREP_ID,crepId);
				sapInput.setValue(IN_DOC_ID,docId);
            } else {
                throw new SAPException(
                        "CreateHttpPutRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "CreateHttpPutRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(CreateHttpPutRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<URL> processResult(JCoFunction pFunction)
			throws SAPException {
		try {
			Doc41Log.get().debug(CreateHttpPutRFC.class, null, "processResult():ENTRY");
			ArrayList<URL> mResult = new ArrayList<URL>();
			if (pFunction != null) {
			    processReturnTable(pFunction);
			    checkReturnCode(pFunction, OUT_RETURNCODE,OUT_RETURN_MESSAGE);
			    JCoParameterList exportParameterList = pFunction.getExportParameterList();
			    mResult.add(new URL(exportParameterList.getString(OUT_URL)));
			}
			Doc41Log.get().debug(CreateHttpPutRFC.class, null, "processResult():EXIT");
			return mResult;
		} catch (MalformedURLException e) {
			throw new SAPException("invalid URL", e);
		}
	}

}
