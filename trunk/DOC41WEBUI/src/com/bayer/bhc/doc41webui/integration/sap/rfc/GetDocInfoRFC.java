package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.DocInfoComponent;
import com.bayer.ecim.foundation.sap3.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class GetDocInfoRFC extends AbstractDoc41RFC<DocInfoComponent>{
	private static final String IN_CREP_ID = "IV_CREP_ID";
	private static final String IN_DOC_ID = "IV_DOC_ID";
	
	private static final String OUT_RETURNCODE = "EV_RETURNCODE";
	
	private static final String OT_COMPONENTS = "TR_COMPONENTS";
	
	private static final String OUT_COMP_ID = "COMP_ID";
	private static final String OUT_COMP_SIZE = "COMP_SIZE";
	private static final String OUT_MIMETYPE = "MIMETYPE";
	private static final String OUT_CREATED_TIME = "CREA_TIME";
	private static final String OUT_CHANGED_TIME = "CHNG_TIME";
	private static final String OUT_BINARY_FLAG = "BINARY_FLG";
	private static final String OUT_STATUS = "STATUS";
	
	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetDocInfoRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String crepId = (String) pInputParms.get(0);
                String docId = (String) pInputParms.get(1);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_CREP_ID,crepId);
				sapInput.setValue(IN_DOC_ID,docId);
            } else {
                throw new SAPException(
                        "GetDocInfoRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "GetDocInfoRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(GetDocInfoRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<DocInfoComponent> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(GetDocInfoRFC.class, null, "processResult():ENTRY");
		ArrayList<DocInfoComponent> mResult = new ArrayList<DocInfoComponent>();
		if (pFunction != null) {
			checkReturnCode(pFunction, OUT_RETURNCODE,null);
			processReturnTable(pFunction);

			JCoTable componentsTable = pFunction.getTableParameterList().getTable(OT_COMPONENTS);
			if(componentsTable!=null){
            	for(int i=0;i<componentsTable.getNumRows();i++){
            		DocInfoComponent docInfoComponent = new DocInfoComponent();
            		docInfoComponent.setCompId(componentsTable.getString(OUT_COMP_ID));
            		docInfoComponent.setCompSize(componentsTable.getInt(OUT_COMP_SIZE));
            		docInfoComponent.setMimeType(componentsTable.getString(OUT_MIMETYPE));
            		docInfoComponent.setCreateTime(componentsTable.getLong(OUT_CREATED_TIME));
            		docInfoComponent.setChangeTime(componentsTable.getLong(OUT_CHANGED_TIME));
            		docInfoComponent.setBinaryFlag(sapCharToBoolean(componentsTable.getChar(OUT_BINARY_FLAG)));
            		docInfoComponent.setStatus(componentsTable.getString(OUT_STATUS));
            		
            		mResult.add(docInfoComponent);
            		componentsTable.nextRow();
            	}
            }
		}
		Doc41Log.get().debug(GetDocInfoRFC.class, null, "processResult():EXIT");
		return mResult;
	}

}
