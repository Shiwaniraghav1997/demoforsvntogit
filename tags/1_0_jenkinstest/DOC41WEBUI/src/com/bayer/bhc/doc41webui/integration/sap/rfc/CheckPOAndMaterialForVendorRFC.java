package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

public class CheckPOAndMaterialForVendorRFC extends AbstractDoc41RFC<Boolean>{
	//TODO
	private static final String IN_VENDOR = "";
	private static final String IN_PO = "";
	private static final String IN_MATERIAL = "";
	private static final String OUT_RETURNCODE = "EV_RETURNCODE";
	
	//TODO
	private static final String RETURNCODE_OK = "OK";

	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(CheckPOAndMaterialForVendorRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String vendorNumber = (String) pInputParms.get(0);
                String poNumber = (String) pInputParms.get(1);
                String materialNumber = (String) pInputParms.get(2);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_VENDOR,vendorNumber);
				sapInput.setValue(IN_PO,poNumber);
				sapInput.setValue(IN_MATERIAL,materialNumber);
            } else {
                throw new SAPException(
                        "CheckPOAndMaterialForVendorRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "CheckPOAndMaterialForVendorRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(CheckPOAndMaterialForVendorRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<Boolean> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(CheckPOAndMaterialForVendorRFC.class, null, "processResult():ENTRY");
		ArrayList<Boolean> mResult = new ArrayList<Boolean>();
        if (pFunction != null) {
            processReturnTable(pFunction);
            JCoParameterList exportParameterList = pFunction.getExportParameterList();
            String returnCode = exportParameterList.getString(OUT_RETURNCODE);
            mResult.add(StringTool.equals(returnCode, RETURNCODE_OK));
        }
        Doc41Log.get().debug(CheckPOAndMaterialForVendorRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
