package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.SDReferenceCheckResult;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

public class CheckDeliveryForPartnerRFC extends AbstractDoc41RFC<SDReferenceCheckResult>{
	
	private static final String IN_PARTNER = "IV_PARNR";
	private static final String IN_REFERENCE = "IV_REF_NO";
	
	private static final String OUT_RETURNCODE = "EV_RETURN";
	private static final String OUT_VKORG = "EV_VKORG";
	
	private static final String RETURNCODE_SHIPPING_UNIT_NUMBER_OK = "A";
	private static final String RETURNCODE_SHIPPING_UNIT_NOT_FOR_PARTNER = "B";
	private static final String RETURNCODE_DELIVERY_NUMBER_OK = "C";
	private static final String RETURNCODE_DELIVERY_NOT_FOR_PARTNER = "D";
	private static final String RETURNCODE_UNKNOWN_NUMBER = "E";
	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(CheckDeliveryForPartnerRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String partnerNumber = (String) pInputParms.get(0);
                String referenceNumber = (String) pInputParms.get(1);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_PARTNER,partnerNumber);
				sapInput.setValue(IN_REFERENCE,referenceNumber);
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
	public List<SDReferenceCheckResult> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(CheckDeliveryForPartnerRFC.class, null, "processResult():ENTRY");
		ArrayList<SDReferenceCheckResult> mResult = new ArrayList<SDReferenceCheckResult>();
        if (pFunction != null) {
//            processReturnTable(pFunction);
            JCoParameterList exportParameterList = pFunction.getExportParameterList();
            String returnCode = exportParameterList.getString(OUT_RETURNCODE);
            
            JCoParameterList sapInput = pFunction.getImportParameterList();
            String referenceNumber = sapInput.getString(IN_REFERENCE);
            
			SDReferenceCheckResult result = mapReturnCodeToResult(returnCode,referenceNumber);
			
			if(hasElement(exportParameterList,OUT_VKORG)){
				String vkOrg = exportParameterList.getString(OUT_VKORG);
				result.setVkOrg(vkOrg);
			}
			
			mResult.add(result);
        }
        Doc41Log.get().debug(CheckDeliveryForPartnerRFC.class, null, "processResult():EXIT");
        return mResult;
	}

	private SDReferenceCheckResult mapReturnCodeToResult(String returnCode,String referenceNumber) throws SAPException {
		if(StringTool.equals(returnCode, RETURNCODE_SHIPPING_UNIT_NUMBER_OK)){
			return new SDReferenceCheckResult(referenceNumber, SDReferenceCheckResult.TYPE_SHIPPING_UNIT_NUMBER, true);
		} else if(StringTool.equals(returnCode, RETURNCODE_SHIPPING_UNIT_NOT_FOR_PARTNER)){
			return new SDReferenceCheckResult(referenceNumber, SDReferenceCheckResult.TYPE_SHIPPING_UNIT_NUMBER, false);
		} else if(StringTool.equals(returnCode, RETURNCODE_DELIVERY_NUMBER_OK)){
			return new SDReferenceCheckResult(referenceNumber, SDReferenceCheckResult.TYPE_DELIVERY_NUMBER, true);
		} else if(StringTool.equals(returnCode, RETURNCODE_DELIVERY_NOT_FOR_PARTNER)){
			return new SDReferenceCheckResult(referenceNumber, SDReferenceCheckResult.TYPE_DELIVERY_NUMBER, false);
		} else if(StringTool.equals(returnCode, RETURNCODE_UNKNOWN_NUMBER)){
			return new SDReferenceCheckResult(referenceNumber, SDReferenceCheckResult.TYPE_UNKNOWN, false);
		} else {
			throw new SAPException("CheckDeliveryForPartnerRFC: unknown return code from SAP: "+returnCode,null);
		}
	}

}
