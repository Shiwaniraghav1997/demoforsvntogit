package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.sap3.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

public class ProcessDrReqRFC extends AbstractDoc41RFC<Integer> {
	
	private static final String IN_COREP = "ARCHIV_ID";
	private static final String IN_DOCID = "ARC_DOC_ID";
	private static final String IN_DATE = "AR_DATE";
	private static final String IN_D41ID = "AR_OBJECT";
	private static final String IN_OBJ_ID = "OBJECT_ID";
	private static final String IN_SAP_OBJ = "SAP_OBJECT";
	private static final String IN_ATTRIBS = "USER_DATA";
	private static final String IN_DOC_TYPE = "DOC_TYPE";
	
	private static final String OUT_ATTCOUNT = "ATTCOUNT";


	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(ProcessDrReqRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String d41id = (String) pInputParms.get(0);
            	String docid = (String) pInputParms.get(1);
                String corep = (String) pInputParms.get(2);
                String docClass = (String) pInputParms.get(3);
                String objId = (String) pInputParms.get(4);
                String sapObj = (String) pInputParms.get(5);
                @SuppressWarnings("unchecked")
				Map<String, String> attribValues = (Map<String, String>) pInputParms.get(6);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_COREP,corep);
				sapInput.setValue(IN_DOCID,docid);
				sapInput.setValue(IN_DATE,new Date());
				sapInput.setValue(IN_D41ID,d41id);
				sapInput.setValue(IN_OBJ_ID,objId);
				sapInput.setValue(IN_SAP_OBJ,sapObj);
				if(!StringTool.isTrimmedEmptyOrNull(docClass)){
					sapInput.setValue(IN_DOC_TYPE,docClass);
				}
				sapInput.setValue(IN_ATTRIBS,getAttribString(attribValues));
				if (Doc41Log.get().isDebugActive()) {
                    Doc41Log.get().debug(this, null, RFCFunctionDumper.dumpFunction(pFunction));
                }
				Doc41Log.get().debug(ProcessDrReqRFC.class, null, "prepareCall():attributs are set");
            } else {
                throw new SAPException(
                        "ProcessDrReqRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "ProcessDrReqRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(ProcessDrReqRFC.class, null, "prepareCall():EXIT");
	}

	private String getAttribString(Map<String, String> attribValues) {
		StringBuilder sb = new StringBuilder();
		sb.append('|');
		if(!attribValues.containsKey(Doc41Constants.ATTRIB_NAME_PLANT)){
			sb.append(Doc41Constants.ATTRIB_NAME_PLANT);
			sb.append("=XXXX|");
		}
		if(!attribValues.containsKey(Doc41Constants.ATTRIB_NAME_WEBUI_USER)){
			sb.append(Doc41Constants.ATTRIB_NAME_WEBUI_USER);
			sb.append("=");
			sb.append(UserInSession.getCwid());
			sb.append("|");
		}
		Set<Entry<String, String>> entries = attribValues.entrySet();
		for (Entry<String, String> entry : entries) {
			if(entry.getValue()!=null){
				sb.append(entry.getKey());
				sb.append('=');
				sb.append(entry.getValue());
				sb.append('|');
			}
		}
		
		
		return sb.toString();
	}

	@Override
	public List<Integer> processResult(JCoFunction pFunction) throws SAPException {
		Doc41Log.get().debug(ProcessDrReqRFC.class, null, "processResult():ENTRY");
		ArrayList<Integer> mResult = new ArrayList<Integer>();
        if (pFunction != null) {
            JCoParameterList exportParameterList = pFunction.getExportParameterList();
            int attCount = exportParameterList.getInt(OUT_ATTCOUNT);

            mResult.add(attCount);
        }
        if (Doc41Log.get().isDebugActive()) {
            Doc41Log.get().debug(getClass(), null, RFCFunctionDumper.dumpFunction(pFunction));
        }
        Doc41Log.get().debug(ProcessDrReqRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
