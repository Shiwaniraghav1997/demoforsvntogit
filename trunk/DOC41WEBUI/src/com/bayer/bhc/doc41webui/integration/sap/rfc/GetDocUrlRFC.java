package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

public class GetDocUrlRFC extends AbstractDoc41RFC<URI>{
	private static final String IN_CREP_ID = "IV_CREP_ID";
	private static final String IN_DOC_ID = "IV_DOC_ID";
	private static final String IN_COMP_ID = "IV_COMP_ID";
	
	private static final String OUT_URL = "EV_URL";

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetDocUrlRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String crepId = (String) pInputParms.get(0);
                String docId = (String) pInputParms.get(1);
                String compId = (String) pInputParms.get(2);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_CREP_ID,crepId);
				sapInput.setValue(IN_DOC_ID,docId);
				if(!StringTool.isTrimmedEmptyOrNull(compId)){
					sapInput.setValue(IN_COMP_ID,compId);
				}
            } else {
                throw new SAPException(
                        "GetDocUrlRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "GetDocUrlRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(GetDocUrlRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<URI> processResult(JCoFunction pFunction)
			throws SAPException {
		try{
			Doc41Log.get().debug(GetDocUrlRFC.class, null, "processResult():ENTRY");
			ArrayList<URI> mResult = new ArrayList<URI>();
			if (pFunction != null) {
				processReturnTable(pFunction);
				JCoParameterList exportParameterList = pFunction.getExportParameterList();
				mResult.add(new URI(exportParameterList.getString(OUT_URL)));
			}
			Doc41Log.get().debug(GetDocUrlRFC.class, null, "processResult():EXIT");
			return mResult;
		} catch (URISyntaxException e) {
			throw new SAPException("invalid URi", e);
		}
	}

}
