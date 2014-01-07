package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

public class CheckArtworkLayoutForVendorRFC extends AbstractDoc41RFC<String>{
	private static final String IN_VENDOR = "IV_VENDOR";
	private static final String IN_DOC_TYPE = "IV_DOCTYPE_ID";
	private static final String OUT_RETURNCODE = "EV_RETURN";
	
	private static final String RETURNCODE_OK = "0";
	private static final String RETURNCODE_NOT_FOUND = "4";

	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(CheckArtworkLayoutForVendorRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
                String vendorNumber = (String) pInputParms.get(0);
                String doctype = (String) pInputParms.get(1);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_VENDOR,vendorNumber);
				sapInput.setValue(IN_DOC_TYPE,doctype);
            } else {
                throw new SAPException(
                        "CheckArtworkLayoutForVendorRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "CheckArtworkLayoutForVendorRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(CheckArtworkLayoutForVendorRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<String> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(CheckArtworkLayoutForVendorRFC.class, null, "processResult():ENTRY");
		ArrayList<String> mResult = new ArrayList<String>();
        if (pFunction != null) {
//            processReturnTable(pFunction);
            JCoParameterList exportParameterList = pFunction.getExportParameterList();
            String returnCode = exportParameterList.getString(OUT_RETURNCODE);
            mResult.add(mapReturnCodeToTag(returnCode));
        }
        Doc41Log.get().debug(CheckArtworkLayoutForVendorRFC.class, null, "processResult():EXIT");
        return mResult;
	}
	
	private String mapReturnCodeToTag(String returnCode) {
		if(StringTool.equals(returnCode, RETURNCODE_OK)){
			return null;
		} else if(StringTool.equals(returnCode, RETURNCODE_NOT_FOUND)){
			return "NoDocumentFound";
		}
		return "UnknownReturnCode";
	}

}
