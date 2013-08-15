package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

public class CheckDeliveryForPartnerRFC extends AbstractDoc41RFC<String>{
	private static final String IN_PARTNER = "IV_PARNR";
	private static final String IN_DELIVERY = "IV_DELV_NO";
	private static final String IN_SHIPMENT = "IV_SHPMT_NO";
	private static final String OUT_RETURNCODE = "EV_RETURN";
	
	private static final String RETURNCODE_OK = "0";
	private static final String RETURNCODE_DELIVERY_UNKNOWN = "1";
	private static final String RETURNCODE_WRONG_PARTNER = "2";
	private static final String RETURNCODE_WRONG_SHIPPING_UNIT = "4";

	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(CheckDeliveryForPartnerRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String partnerNumber = (String) pInputParms.get(0);
                String deliveryNumber = (String) pInputParms.get(1);
                String shipmentNumber = (String) pInputParms.get(2);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_PARTNER,partnerNumber);
				sapInput.setValue(IN_DELIVERY,deliveryNumber);
				sapInput.setValue(IN_SHIPMENT,shipmentNumber);
            } else {
                throw new SAPException(
                        "CheckDeliveryForPartnerRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "CheckDeliveryForPartnerRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(CheckDeliveryForPartnerRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<String> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(CheckDeliveryForPartnerRFC.class, null, "processResult():ENTRY");
		ArrayList<String> mResult = new ArrayList<String>();
        if (pFunction != null) {
//            processReturnTable(pFunction);
            JCoParameterList exportParameterList = pFunction.getExportParameterList();
            String returnCode = exportParameterList.getString(OUT_RETURNCODE);
            
            mResult.add(mapReturnCodeToTag(returnCode));
        }
        Doc41Log.get().debug(CheckDeliveryForPartnerRFC.class, null, "processResult():EXIT");
        return mResult;
	}

	private String mapReturnCodeToTag(String returnCode) {
		if(StringTool.equals(returnCode, RETURNCODE_OK)){
			return null;
		} else if(StringTool.equals(returnCode, RETURNCODE_DELIVERY_UNKNOWN)){
			return "DeliveryUnknown";
		} else if(StringTool.equals(returnCode, RETURNCODE_WRONG_PARTNER)){
			return "DeliveryNotAssignedToPartner";
		} else if(StringTool.equals(returnCode, RETURNCODE_WRONG_SHIPPING_UNIT)){
			return "ShippingUnitNotAssignedToPartner";
		}
		return "UnknownReturnCode";
	}

}
