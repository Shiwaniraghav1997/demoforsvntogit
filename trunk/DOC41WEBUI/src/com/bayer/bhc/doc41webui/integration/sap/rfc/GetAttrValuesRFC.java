package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.KeyValue;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class GetAttrValuesRFC extends AbstractDoc41RFC<KeyValue> {
	
	private static final String IN_DOCRANGE = "TR_DOCRANGE";
	private static final String IN_ID = "ID";
	
	private static final String OUT_NAME = "ANAME";
	private static final String OUT_VALUE = "VALUE";
	private static final String OUT_TABLE = "TR_ATTRIBUTES";

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetAttrValuesRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
                String doc41Id = (String) pInputParms.get(0);
                
                JCoTable d41idtable = pFunction.getTableParameterList().getTable(IN_DOCRANGE);
                d41idtable.appendRow();
                d41idtable.setValue(IN_ID,doc41Id);
            } else {
                throw new SAPException(
                        "GetAttrValuesRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "GetAttrValuesRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(GetAttrValuesRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<KeyValue> processResult(JCoFunction pFunction) throws SAPException {
		Doc41Log.get().debug(GetAttrValuesRFC.class, null, "processResult():ENTRY");
		ArrayList<KeyValue> mResult = new ArrayList<KeyValue>();
		if (pFunction != null) {
			processReturnTable(pFunction);
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
		Doc41Log.get().debug(GetAttrValuesRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
