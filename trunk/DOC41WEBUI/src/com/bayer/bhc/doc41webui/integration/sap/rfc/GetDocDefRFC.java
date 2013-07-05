package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.container.DocTypeDef;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class GetDocDefRFC extends AbstractDoc41RFC<DocTypeDef> {
	
	private static final String OUT_RETURNCODE = "EV_RETURNCODE";
	private static final String OUT_D41ID = "D41ID";
	private static final String OUT_TECH_ID = "DOCTY";
	private static final String OUT_DESC = "DTEXT";
	private static final String OUT_SAPOBJ = "BUSOB";
	
	private static final String TR_DOCTYPES = "TR_DOCTYPES";
	
	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetDocDefRFC.class, null, "prepareCall():ENTRY");
    	
//        if (pFunction != null) {
//            if (pInputParms != null) {
//            	String corep = (String) pInputParms.get(0);
//                String docId = (String) pInputParms.get(1);
//                
//                JCoParameterList sapInput = pFunction.getImportParameterList();
//				
//				sapInput.setValue(IN_COREP,corep);
//				sapInput.setValue(IN_DOCID,docId);
//            } else {
//                throw new SAPException(
//                        "GetDocStatusRFC pInputParms list is null", null);
//            }
//        } else {
//            throw new SAPException(
//                    "GetDocStatusRFC pFunction list is null", null);
//        }
        Doc41Log.get().debug(GetDocDefRFC.class, null, "prepareCall():EXIT");

	}

	@Override
	public List<DocTypeDef> processResult(JCoFunction pFunction) throws SAPException {
		Doc41Log.get().debug(GetDocDefRFC.class, null, "processResult():ENTRY");
		ArrayList<DocTypeDef> mResult = new ArrayList<DocTypeDef>();
        if (pFunction != null) {
            processReturnTable(pFunction);
            checkReturnCode(pFunction, OUT_RETURNCODE, null);
            JCoTable table = pFunction.getTableParameterList().getTable(TR_DOCTYPES);
            if(table!=null){
            	for(int i=0;i<table.getNumRows();i++){
            		DocTypeDef def = new DocTypeDef();
            		def.setD41id(table.getString(OUT_D41ID));
            		def.setTechnicalId(table.getString(OUT_TECH_ID));
            		def.setDescription(table.getString(OUT_DESC));
            		def.setSapObj(table.getString(OUT_SAPOBJ));

            		table.nextRow();
            		mResult.add(def);
            	}
            }
        }
        Doc41Log.get().debug(GetDocDefRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
