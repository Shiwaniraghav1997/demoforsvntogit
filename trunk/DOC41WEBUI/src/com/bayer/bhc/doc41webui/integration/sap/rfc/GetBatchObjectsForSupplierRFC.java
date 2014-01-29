package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.QMBatchObject;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class GetBatchObjectsForSupplierRFC extends AbstractDoc41RFC<QMBatchObject> {

	private static final String IN_SUPPLIER = "IV_SUPPLIER";
	private static final String IN_PLANT = "IV_PLANT";
	private static final String IN_MATERIAL = "IV_MATNR";
	private static final String IN_BATCH = "IV_BATCH";
	private static final String IN_ORDER = "IV_DOCNUMBER";
	
	//		private static final String OUT_RETURNCODE = "EV_RETURN";

	private static final String CT_BATCHES = "CT_OBJECTS";
	
//	private static final String OUT_OBJECT_ID = "OBJID";
	private static final String OUT_MAT_NUMBER = "MATNR";
	private static final String OUT_MAT_TEXT = "MAKTX";
	private static final String OUT_PLANT = "PLANT";
	private static final String OUT_BATCH = "BATCH";

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetBatchObjectsForSupplierRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String supplier = (String) pInputParms.get(0);
                String plant = (String) pInputParms.get(1);
                String material = (String) pInputParms.get(2);
                String batch = (String) pInputParms.get(3);
                String order = (String) pInputParms.get(4);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_SUPPLIER,supplier);
				sapInput.setValue(IN_PLANT,plant);
				sapInput.setValue(IN_MATERIAL,material);
				sapInput.setValue(IN_BATCH,batch);
				sapInput.setValue(IN_ORDER,order);
				Doc41Log.get().debug(GetBatchObjectsForSupplierRFC.class, null, "prepareCall():attributs are set");
            } else {
                throw new SAPException(
                        "GetBatchObjectsForSupplierRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "GetBatchObjectsForSupplierRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(GetBatchObjectsForSupplierRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<QMBatchObject> processResult(JCoFunction pFunction) throws SAPException {
		Doc41Log.get().debug(GetBatchObjectsForSupplierRFC.class, null, "processResult():ENTRY");
		ArrayList<QMBatchObject> mResult = new ArrayList<QMBatchObject>();
        if (pFunction != null) {
//            processReturnTable(pFunction);
//            checkReturnCode(pFunction, OUT_RETURNCODE, null);
            JCoTable batchesTable = pFunction.getChangingParameterList().getTable(CT_BATCHES);
            if(batchesTable!=null){
            	for(int i=0;i<batchesTable.getNumRows();i++){
            		QMBatchObject batchObject = new QMBatchObject();
//            		batchObject.setObjectId(batchesTable.getString(OUT_OBJECT_ID));
            		batchObject.setMaterialNumber(batchesTable.getString(OUT_MAT_NUMBER));
            		batchObject.setMaterialText(batchesTable.getString(OUT_MAT_TEXT));
            		batchObject.setPlant(batchesTable.getString(OUT_PLANT));
            		batchObject.setBatch(batchesTable.getString(OUT_BATCH));
            		
            		mResult.add(batchObject);
            		batchesTable.nextRow();
            	}
            }
        }
        Doc41Log.get().debug(GetBatchObjectsForSupplierRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
