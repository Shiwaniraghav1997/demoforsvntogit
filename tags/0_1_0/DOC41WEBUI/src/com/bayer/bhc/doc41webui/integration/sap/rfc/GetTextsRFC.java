package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.KeyValue;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class GetTextsRFC extends AbstractDoc41RFC<KeyValue> {
	
	private static final String IN_LANG = "IV_LANG";
	private static final String OT_KEYS = "TS_BUSOBS";
	
	private static final String OUT_RETURNCODE = "EV_RETURNCODE";
	private static final String OUT_TABLE = "TS_TEXTS";
	private static final String OUT_NAME = "OBJECT_KEY";
	private static final String OUT_VALUE = "OBJECT_TEXT";

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetTextsRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	@SuppressWarnings("unchecked")
				Set<String> textKeysToTranslate = (Set<String>) pInputParms.get(0);
            	String language = (String) pInputParms.get(1);
            	
            	JCoParameterList sapInput = pFunction.getImportParameterList();
				sapInput.setValue(IN_LANG,language.toUpperCase());
            	
                JCoTable table = pFunction.getTableParameterList().getTable(OT_KEYS);
                for (String key : textKeysToTranslate) {
					table.appendRow();
					table.setValue("ID", key);
				}
				
            } else {
                throw new SAPException(
                        "GetTextsRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "GetTextsRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(GetTextsRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<KeyValue> processResult(JCoFunction pFunction) throws SAPException {
		Doc41Log.get().debug(GetTextsRFC.class, null, "processResult():ENTRY");
		ArrayList<KeyValue> mResult = new ArrayList<KeyValue>();
		if (pFunction != null) {
//			processReturnTable(pFunction);
			checkReturnCode(pFunction, OUT_RETURNCODE, null);
			JCoTable table = pFunction.getTableParameterList().getTable(OUT_TABLE);
			if(table!=null){
				for(int i=0;i<table.getNumRows();i++){
					KeyValue kv = new KeyValue();
					kv.setKey(table.getString(OUT_NAME));
					kv.setValue(table.getString(OUT_VALUE));

					table.nextRow();
					mResult.add(kv);
				}
			}
		}
		Doc41Log.get().debug(GetTextsRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
