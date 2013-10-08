package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.UserPartner;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

public class CheckPartnerRFC extends AbstractDoc41RFC<UserPartner>{
	private static final String IN_PARTNER = "IV_LIFNR";
	private static final String OUT_RETURNCODE = "EV_RETURN";
	//TODO
	private static final String OUT_NAME1 = "";
	private static final String OUT_NAME2 = "";
	private static final String OUT_TYPE = "";
	
	private static final String RETURNCODE_OK = "0";

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(CheckPartnerRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String partnerNumber = (String) pInputParms.get(0);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_PARTNER,partnerNumber);
            } else {
                throw new SAPException(
                        "CheckPartnerRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "CheckPartnerRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(CheckPartnerRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<UserPartner> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(CheckPartnerRFC.class, null, "processResult():ENTRY");
		ArrayList<UserPartner> mResult = new ArrayList<UserPartner>();
        if (pFunction != null) {
//            processReturnTable(pFunction);
            JCoParameterList exportParameterList = pFunction.getExportParameterList();
            String returnCode = exportParameterList.getString(OUT_RETURNCODE);
            if(StringTool.equals(returnCode, RETURNCODE_OK)){
            	UserPartner up = new UserPartner();
            	up.setPartnerNumber(pFunction.getImportParameterList().getString(IN_PARTNER));
            	up.setPartnerName1(exportParameterList.getString(OUT_NAME1));
            	up.setPartnerName2(exportParameterList.getString(OUT_NAME2));
            	up.setPartnerType(exportParameterList.getString(OUT_TYPE));
				mResult.add(up);
            }
        }
        Doc41Log.get().debug(CheckPartnerRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
