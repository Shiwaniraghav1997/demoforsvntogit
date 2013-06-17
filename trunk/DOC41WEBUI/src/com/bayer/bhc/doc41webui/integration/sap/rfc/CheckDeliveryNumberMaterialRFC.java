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

public class CheckDeliveryNumberMaterialRFC extends AbstractDoc41RFC<String>{
	//TODO
	private static final String IN_DELIVERY = "";
	private static final String IN_MATERIAL = "";
	private static final String OUT_RETURNCODE = "EV_RETURNCODE";
	
	//TODO
	private static final String RETURNCODE_OK = "OK";
	private static final String RETURNCODE_DELIVERY_NOT_FOUND = "DELIVERY_NOT_FOUND";
	private static final String RETURNCODE_MAT_NOT_IN_DEL = "MAT_NOT_IN_DEL";
	
	//TODO
	public static final String ERROR_DELIVERY_NOT_FOUND ="DELIVERY_NOT_FOUND";
	public static final String ERROR_MAT_NOT_IN_DEL = "MAT_NOT_IN_DEL";
	
	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(CheckDeliveryNumberMaterialRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
                String deliveryNumber = (String) pInputParms.get(0);
                String materialNumber = (String) pInputParms.get(1);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_DELIVERY,deliveryNumber);
				sapInput.setValue(IN_MATERIAL,materialNumber);
            } else {
                throw new SAPException(
                        "CheckDeliveryNumberMaterialRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "CheckDeliveryNumberMaterialRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(CheckDeliveryNumberMaterialRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<String> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(CheckDeliveryNumberMaterialRFC.class, null, "processResult():ENTRY");
		ArrayList<String> mResult = new ArrayList<String>();
        if (pFunction != null) {
            processReturnTable(pFunction);
            JCoParameterList exportParameterList = pFunction.getExportParameterList();
            String returnCode = exportParameterList.getString(OUT_RETURNCODE);
            String error;
            if(StringTool.equals(returnCode, RETURNCODE_OK)){
            	error=null;
            } else if (StringTool.equals(returnCode, RETURNCODE_DELIVERY_NOT_FOUND)){
            	error = ERROR_DELIVERY_NOT_FOUND;
            } else if (StringTool.equals(returnCode, RETURNCODE_MAT_NOT_IN_DEL)){
            	error = ERROR_MAT_NOT_IN_DEL;
            } else {
            	throw new SAPException("unknown return code: "+returnCode, null);
            }
            mResult.add(error);
        }
        Doc41Log.get().debug(CheckDeliveryNumberMaterialRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
