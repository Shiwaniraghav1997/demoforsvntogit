package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class FindDocsRFC extends AbstractDoc41RFC<HitListEntry> {

	
	private static final String IT_OBJID_RANGE = "IT_OBJID_RANGE";
	private static final String IN_D41ID = "IV_D41ID";
	private static final String IN_MAXHIT = "IV_MAXHIT";
	private static final String IN_MAX_VER_ONLY = "IV_GET_MAX_VERS";
	private static final String IN_OBJ_TYPE = "IV_OBJ_TYPE";
	private static final String IN_ATT_NAME = "IV_ATT_NAME";
	private static final String IN_SIGN = "SIGN";
	private static final String IN_OPTION = "OPTION";
	private static final String IN_LOW = "LOW";
	private static final String IT_VALUE_RANGE = "IT_VALUE_RANGE";
	private static final String OT_HITS = "OT_HITS";
//	private static final String OT_HITLIST = "OT_HITLIST";
	private static final String OUT_DOC_ID = "DOCID";
	private static final String OUT_OBJ_ID = "OBJID";
	private static final String OUT_STOR_DATE = "ADATE";
	private static final String OUT_AL_DATE = "LDATE";
	private static final String OUT_AL_TIME = "LTIME";
	private static final String OUT_OBJ_TYPE = "OBJTY";
	private static final String OUT_DOC_CLASS = "CLASS";

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(ProcessDrReqRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String d41id = (String) pInputParms.get(0);
            	String sapObj = (String) pInputParms.get(1);
                String objectId = (String) pInputParms.get(2);
                Integer maxResults = (Integer) pInputParms.get(3);
                Boolean maxVersionOnly = (Boolean) pInputParms.get(4);
                @SuppressWarnings("unchecked")
				Map<String, String> attribValues = (Map<String, String>) pInputParms.get(5);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_D41ID,d41id);
				sapInput.setValue(IN_MAXHIT,maxResults);
				sapInput.setValue(IN_MAX_VER_ONLY,sapBooleanToChar(maxVersionOnly));
				int paramNumber=1;
				JCoParameterList tableParameterList = pFunction.getTableParameterList();
				if(!StringTool.isTrimmedEmptyOrNull(objectId)){
					setParamObjectID(objectId,sapObj,paramNumber++,sapInput,tableParameterList);
				}
				for (String key : attribValues.keySet()) {
					String value = attribValues.get(key);
					if(!StringTool.isTrimmedEmptyOrNull(value)){
						setParamValue(key,value,sapObj,paramNumber++,sapInput,tableParameterList);
					}
				}
            } else {
                throw new SAPException(
                        "FindDocsRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "FindDocsRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(FindDocsRFC.class, null, "prepareCall():EXIT");
	}

	private void setParamValue(String key, String value, String sapObj, int paramNumber,
			JCoParameterList sapInput, JCoParameterList tableParameterList) {
		sapInput.setValue(IN_OBJ_TYPE+paramNumber, sapObj);
		sapInput.setValue(IN_ATT_NAME+paramNumber, key);
		
		JCoTable table = tableParameterList.getTable(IT_VALUE_RANGE+paramNumber);
		table.appendRow();
		table.setValue(IN_SIGN, "I");
		table.setValue(IN_OPTION, "EQ");
		table.setValue(IN_LOW,value);
	}

	private void setParamObjectID(String objectId, String sapObj, int paramNumber,
			JCoParameterList sapInput, JCoParameterList tableParameterList) {
		sapInput.setValue(IN_OBJ_TYPE+paramNumber, sapObj);
//		sapInput.setValue(IN_ATT_NAME+paramNumber, arg1);
		
		JCoTable table = tableParameterList.getTable(IT_OBJID_RANGE+paramNumber);
		table.appendRow();
		table.setValue(IN_SIGN, "I");
		table.setValue(IN_OPTION, "EQ");
		table.setValue(IN_LOW,objectId);
		
	}
	
	
	@Override
	public List<HitListEntry> processResult(JCoFunction pFunction) throws SAPException {
		Doc41Log.get().debug(FindDocsRFC.class, null, "processResult():ENTRY");
		ArrayList<HitListEntry> mResult = new ArrayList<HitListEntry>();
        if (pFunction != null) {
            processReturnTable(pFunction,"OT_RETURN");
//            processReturnTable(pFunction,"RETURN");
//            checkReturnCode(pFunction, OUT_RETURNCODE, null);
            JCoTable table = pFunction.getTableParameterList().getTable(OT_HITS);
            if(table!=null){
            	for(int i=0;i<table.getNumRows();i++){
            		HitListEntry doc = new HitListEntry();
            		
            		doc.setDocId(table.getString(OUT_DOC_ID));
            		doc.setObjectId(table.getString(OUT_OBJ_ID));
            		doc.setStorageDate(table.getDate(OUT_STOR_DATE));
            		Date arTime = table.getDate(OUT_AL_TIME);
            		Date arDate = table.getDate(OUT_AL_DATE);
					doc.setArchiveLinkDate(mergeSapDateTime(arDate, arTime));
            		doc.setObjectType(table.getString(OUT_OBJ_TYPE));
            		doc.setDocumentClass(table.getString(OUT_DOC_CLASS));
            		
            		table.nextRow();
            		mResult.add(doc);
            	}
            }
            
        }
        Doc41Log.get().debug(FindDocsRFC.class, null, "processResult():EXIT");
        return mResult;
	}
}
