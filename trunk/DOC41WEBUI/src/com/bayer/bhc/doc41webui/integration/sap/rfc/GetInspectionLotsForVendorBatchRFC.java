package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.InspectionLot;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class GetInspectionLotsForVendorBatchRFC extends AbstractDoc41RFC<InspectionLot>{
	//TODO
	private static final String IN_PARTNER = "IV_PARNR???";
	private static final String IN_BATCH = "???";
//	private static final String OUT_RETURNCODE = "EV_RETURN";
	
	private static final String OT_INSPECTIONLOTS = "???";
	private static final String OUT_NUMBER = "???";
	private static final String OUT_MAT_NUMBER = "???";
	private static final String OUT_BATCH = "???";
	private static final String OUT_PLANT = "???";
	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetInspectionLotsForVendorBatchRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String partnerNumber = (String) pInputParms.get(0);
                String batch = (String) pInputParms.get(1);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_PARTNER,partnerNumber);
				sapInput.setValue(IN_BATCH,batch);
            } else {
                throw new SAPException(
                        "GetInspectionLotsForVendorBatchRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "GetInspectionLotsForVendorBatchRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(GetInspectionLotsForVendorBatchRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<InspectionLot> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(GetInspectionLotsForVendorBatchRFC.class, null, "processResult():ENTRY");
		ArrayList<InspectionLot> mResult = new ArrayList<InspectionLot>();
        if (pFunction != null) {
//            processReturnTable(pFunction);
//            checkReturnCode(pFunction, OUT_RETURNCODE, null);
            JCoTable inspectionlotsTable = pFunction.getTableParameterList().getTable(OT_INSPECTIONLOTS);
            if(inspectionlotsTable!=null){
            	for(int i=0;i<inspectionlotsTable.getNumRows();i++){
            		InspectionLot inspectionlot = new InspectionLot();
            		inspectionlot.setNumber(inspectionlotsTable.getString(OUT_NUMBER));
            		inspectionlot.setMaterialNumber(inspectionlotsTable.getString(OUT_MAT_NUMBER));
            		inspectionlot.setBatch(inspectionlotsTable.getString(OUT_BATCH));
            		inspectionlot.setPlant(inspectionlotsTable.getString(OUT_PLANT));

            		mResult.add(inspectionlot);
            		inspectionlotsTable.nextRow();
            	}
            }
        }
        Doc41Log.get().debug(GetInspectionLotsForVendorBatchRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
