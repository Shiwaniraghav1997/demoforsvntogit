package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.container.Attribute;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class DvsCreateRFC extends AbstractDoc41RFC<Boolean>{
	//TODO
	private static final String IN_DOCTYPE = "IV_DOC41ID";
	private static final String IN_PLANT = "IV_PLANT";
	private static final String IN_DOC_ID = "IV_DOC_ID";
	private static final String IN_MATNR = "IV_MATNR";
	//private static final String IN_OBJCOUNT = "IV_OBJCOUNT";
	private static final String IN_DVSDOCNR = "IV_DVSDOCNR";
//	private static final String IN_LANG = "IV_LANG";
	
	
	private static final String IT_ATTRIBUTES = "IS_ATTRIBUTES";
	
	
	private static final String OUT_RETURNCODE = "EV_RETURNCODE";
	
	//TODO
	private static final String RETURNCODE_OK = "OK";

	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(DvsCreateRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
                String docType = (String) pInputParms.get(0);
                String plant = (String) pInputParms.get(1);
                String docId = (String) pInputParms.get(2);
                String matnr = (String) pInputParms.get(3);
                String dvsdocnr = (String) pInputParms.get(4);
                @SuppressWarnings("unchecked")
				List<Attribute> attribs = (List<Attribute>) pInputParms.get(5);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_DOCTYPE,docType);
				sapInput.setValue(IN_PLANT,plant);
				sapInput.setValue(IN_DOC_ID,docId);
				sapInput.setValue(IN_MATNR,matnr);
//				sapInput.setValue(IN_OBJCOUNT,vendorNumber);
				sapInput.setValue(IN_DVSDOCNR,dvsdocnr);
//				sapInput.setValue(IN_LANG,vendorNumber);
				
				JCoTable attrTable = pFunction.getTableParameterList().getTable(IT_ATTRIBUTES);
				for (Attribute attr : attribs) {
					attrTable.appendRow();
//					attrTable.setValue(arg0, arg1);
				}
            } else {
                throw new SAPException(
                        "DvsCreateRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "DvsCreateRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(DvsCreateRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<Boolean> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(DvsCreateRFC.class, null, "processResult():ENTRY");
		ArrayList<Boolean> mResult = new ArrayList<Boolean>();
        if (pFunction != null) {
            processReturnTable(pFunction);
            JCoParameterList exportParameterList = pFunction.getExportParameterList();
            String returnCode = exportParameterList.getString(OUT_RETURNCODE);
            mResult.add(StringTool.equals(returnCode, RETURNCODE_OK));
        }
        Doc41Log.get().debug(DvsCreateRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
