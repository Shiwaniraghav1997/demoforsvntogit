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

	private static final String IN_VENDOR = "IV_VENDOR";
	private static final String IN_VENDOR_BATCH = "IV_VENDOR_BATCH";
	private static final String IN_PLANT = "IV_PLANT";
	
	private static final String CT_INSPECTIONLOTS = "CT_INSPLOTS4VBATCH";
	
	private static final String OUT_NUMBER = "PRUEFLOS";
	private static final String OUT_MAT_NUMBER = "MATNR";
	private static final String OUT_MAT_TEXT = "MAKTX";
	private static final String OUT_PLANT = "PLANT";
	private static final String OUT_BATCH = "BATCH";
	private static final String OUT_VENDOR = "VENDOR";
	private static final String OUT_VENDOR_BATCH = "VENDOR_BATCH";
	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetInspectionLotsForVendorBatchRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String vendorNumber = (String) pInputParms.get(0);
                String batch = (String) pInputParms.get(1);
                String plant = (String) pInputParms.get(2);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_VENDOR,vendorNumber);
				sapInput.setValue(IN_VENDOR_BATCH,batch);
				sapInput.setValue(IN_PLANT,plant);
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
            JCoTable inspectionlotsTable = pFunction.getChangingParameterList().getTable(CT_INSPECTIONLOTS);
            if(inspectionlotsTable!=null){
            	for(int i=0;i<inspectionlotsTable.getNumRows();i++){
            		InspectionLot inspectionlot = new InspectionLot();
            		inspectionlot.setNumber(inspectionlotsTable.getString(OUT_NUMBER));
            		inspectionlot.setMaterialNumber(inspectionlotsTable.getString(OUT_MAT_NUMBER));
            		inspectionlot.setMaterialText(inspectionlotsTable.getString(OUT_MAT_TEXT));
            		inspectionlot.setPlant(inspectionlotsTable.getString(OUT_PLANT));
            		inspectionlot.setBatch(inspectionlotsTable.getString(OUT_BATCH));
            		inspectionlot.setVendor(inspectionlotsTable.getString(OUT_VENDOR));
            		inspectionlot.setVendorBatch(inspectionlotsTable.getString(OUT_VENDOR_BATCH));
            		
            		mResult.add(inspectionlot);
            		inspectionlotsTable.nextRow();
            	}
            }
        }
        Doc41Log.get().debug(GetInspectionLotsForVendorBatchRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
