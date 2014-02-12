package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class GetAttributesRFC extends AbstractDoc41RFC<Attribute> {
	private static final String IN_DOC41ID = "D41ID";
	private static final String IN_LANGUAGE = "LANGUAGE";
	
	private static final String OUT_RETURN = "RETURN";
	private static final String OUT_TABLE = "ATAB";
	private static final String OUT_NAME = "ANAME";
	private static final String OUT_SEQNO = "SEQNO";
	private static final String OUT_DESC = "ATEXT";
	private static final String OUT_MANDATORY = "MFLAG";
	private static final String OUT_LABEL = "ATEXT";

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetAttributesRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
                String doc41Id = (String) pInputParms.get(0);
                String language = (String) pInputParms.get(1);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_DOC41ID,doc41Id);
				sapInput.setValue(IN_LANGUAGE,language.toUpperCase());
            } else {
                throw new SAPException(
                        "GetAttributesRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "GetAttributesRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(GetAttributesRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<Attribute> processResult(JCoFunction pFunction) throws SAPException {
		Doc41Log.get().debug(GetAttributesRFC.class, null, "processResult():ENTRY");
		ArrayList<Attribute> mResult = new ArrayList<Attribute>();
		if (pFunction != null) {
			processReturnTable(pFunction,OUT_RETURN);
			JCoTable table = pFunction.getTableParameterList().getTable(OUT_TABLE);
			if(table!=null){
				for(int i=0;i<table.getNumRows();i++){
					Attribute attr = new Attribute();
					attr.setName(table.getString(OUT_NAME));
					attr.setSeqNumber(table.getInt(OUT_SEQNO));
					attr.setDesc(table.getString(OUT_DESC));
					attr.setMandatory(sapCharToBoolean(table.getChar(OUT_MANDATORY)));
					attr.setTempLabel(table.getString(OUT_LABEL));

					table.nextRow();
					mResult.add(attr);
				}
			}
		}
		Doc41Log.get().debug(GetAttributesRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
