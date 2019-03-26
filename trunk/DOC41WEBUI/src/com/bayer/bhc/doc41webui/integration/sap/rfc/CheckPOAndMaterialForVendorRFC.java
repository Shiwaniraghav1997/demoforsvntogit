package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.Doc41ValidationUtils;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.sap3.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

public class CheckPOAndMaterialForVendorRFC extends AbstractDoc41RFC<String>{
	private static final String IN_VENDOR = "IV_VENDOR";
	private static final String IN_PO = "IV_PONUMBER";
	private static final String IN_MATERIAL = "IV_MATNR";
	private static final String IN_VERID = "IV_VERID";
	
	private static final String OUT_RETURNCODE = "EV_RETURN";
	private static final String OUT_PLANT = "EV_PLANT";
	
	private static final String RETURNCODE_OK = "0";
	private static final String RETURNCODE_ORDER_NOT_EXISTING = "1";
	private static final String RETURNCODE_ORDER_NOT_FOUND = "2";

	
	/**
	 * Check PO and material(old) / check material(new) - for vendor.
	 * SAP-Developer: IV_PONUMBER ist optional. Es wird nur noch RC = 0 zurück gegebenen wenn es PO Positionen mit nicht gesetztem Endlieferkennzeichen gibt.
	 * @param JCoFunction sap function object to be called.
	 * @param pInputParams parameters to be feeded: 1. vendornumber (obligatory), 2. poNumber (optional = may be null), 3. materialNumber(obligatory)
	 */
	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(this, null, "ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String vendorNumber = (String) pInputParms.get(0);
                String poNumber = (String) pInputParms.get(1);
                String materialNumber = (String) pInputParms.get(2);
                String customVersion = (String) pInputParms.get(3);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_VENDOR,vendorNumber);
				if (poNumber != null) {
				    sapInput.setValue(IN_PO,poNumber);
				}
				sapInput.setValue(IN_MATERIAL,materialNumber);
				if(customVersion != null){
					sapInput.setValue(IN_VERID, customVersion);
				}
					
                if (Doc41Log.get().isDebugActive()) {
                    Doc41Log.get().debug(this, null, RFCFunctionDumper.dumpFunction(pFunction));
                }
                Doc41Log.get().debug(this, null, "attributs are set");
            } else {
                throw new SAPException(
                        "CheckPOAndMaterialForVendorRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "CheckPOAndMaterialForVendorRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(this, null, "prepareCall():EXIT");
	}

	@Override
	public List<String> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(this, null, "processResult():ENTRY");
		ArrayList<String> mResult = new ArrayList<String>();
        if (pFunction != null) {
            if (Doc41Log.get().isDebugActive()) {
                Doc41Log.get().debug(getClass(), null, RFCFunctionDumper.dumpFunction(pFunction));
            }
            JCoParameterList exportParameterList = pFunction.getExportParameterList();
            String returnCode = exportParameterList.getString(OUT_RETURNCODE);
            String evPlant = exportParameterList.getString(OUT_PLANT);
            mResult.add(mapReturnCodeToTag(returnCode));
            if(StringTool.emptyToNull(evPlant) != null){
            	mResult.add(evPlant);
            }
        }
        Doc41Log.get().debug(this, null, "EXIT");
        return mResult;
	}
	
	private String mapReturnCodeToTag(String returnCode) {
		if (StringTool.equals(returnCode, RETURNCODE_OK)) {
			return null;
		} else if (StringTool.equals(returnCode, RETURNCODE_ORDER_NOT_EXISTING)) {
			return Doc41ValidationUtils.ERROR_MESSAGE_ORDER_NOT_EXISTING;
		} else if (StringTool.equals(returnCode, RETURNCODE_ORDER_NOT_FOUND)) {
			return Doc41ValidationUtils.ERROR_MESSAGE_ORDER_NOT_FOUND;
		}
		return Doc41ValidationUtils.ERROR_MESSAGE_UNKNOWN_RETURN_CODE;
	}

}
