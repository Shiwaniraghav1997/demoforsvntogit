package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.DocTypeDef;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class GetDocDefRFC extends AbstractDoc41RFC<DocTypeDef> {
	
	private static final String OUT_RETURNCODE = "EV_RETURNCODE";
	private static final String OUT_D41ID = "D41ID";
	private static final String OUT_TECH_ID = "DOCTY";
	private static final String OUT_DESC = "DTEXT";
	private static final String OUT_ISDVS = "ISDVS";
	private static final String OUT_SAPOBJ = "BUSOB";
	private static final String OUT_SAPOBJ2 = "BUSO2";
	private static final String OUT_SAPOBJ3 = "BUSO3";
	private static final String OUT_SAPOBJ4 = "BUSO4";
	private static final String OUT_SAPOBJ5 = "BUSO5";
	private static final String OUT_SAPOBJ6 = "BUSO6";
	private static final String OUT_SAPOBJ7 = "BUSO7";
	private static final String OUT_SAPOBJ8 = "BUSO8";
	private static final String OUT_SAPOBJ9 = "BUSO9";
	
	private static final String TR_DOCTYPES = "TR_DOCTYPES";
	
	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetDocDefRFC.class, null, "prepareCall():ENTRY");
    	
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
            		def.setDvs(sapCharToBoolean(table.getChar(OUT_ISDVS)));
            		List<String> sapObjList = new ArrayList<String>();
					def.setSapObjList(sapObjList);
            		addNotNullOrEmptyToList(sapObjList,table.getString(OUT_SAPOBJ));
            		addNotNullOrEmptyToList(sapObjList,table.getString(OUT_SAPOBJ2));
            		addNotNullOrEmptyToList(sapObjList,table.getString(OUT_SAPOBJ3));
            		addNotNullOrEmptyToList(sapObjList,table.getString(OUT_SAPOBJ4));
            		addNotNullOrEmptyToList(sapObjList,table.getString(OUT_SAPOBJ5));
            		addNotNullOrEmptyToList(sapObjList,table.getString(OUT_SAPOBJ6));
            		addNotNullOrEmptyToList(sapObjList,table.getString(OUT_SAPOBJ7));
            		addNotNullOrEmptyToList(sapObjList,table.getString(OUT_SAPOBJ8));
            		addNotNullOrEmptyToList(sapObjList,table.getString(OUT_SAPOBJ9));

            		table.nextRow();
            		mResult.add(def);
            	}
            }
        }
        Doc41Log.get().debug(GetDocDefRFC.class, null, "processResult():EXIT");
        return mResult;
	}

	private void addNotNullOrEmptyToList(List<String> list, String text) {
		if(!StringTool.isTrimmedEmptyOrNull(text)){
			list.add(text);
		}
		
	}

}
