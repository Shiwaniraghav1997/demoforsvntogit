package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;

public class CheckArtworkForVendorRFC extends AbstractDoc41RFC<Boolean>{
	//TODO
	private static final String IN_VENDOR = "";
	private static final String OUT_RETURNCODE = "EV_RETURNCODE";
	
	//TODO
	private static final String RETURNCODE_OK = "OK";

	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(CheckArtworkForVendorRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
                String vendorNumber = (String) pInputParms.get(0);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_VENDOR,vendorNumber);
            } else {
                throw new SAPException(
                        "CheckArtworkForVendorRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "CheckArtworkForVendorRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(CheckArtworkForVendorRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<Boolean> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(CheckArtworkForVendorRFC.class, null, "processResult():ENTRY");
		ArrayList<Boolean> mResult = new ArrayList<Boolean>();
        if (pFunction != null) {
            processReturnTable(pFunction);
            JCoParameterList exportParameterList = pFunction.getExportParameterList();
            String returnCode = exportParameterList.getString(OUT_RETURNCODE);
            mResult.add(StringTool.equals(returnCode, RETURNCODE_OK));
        }
        Doc41Log.get().debug(CheckArtworkForVendorRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
