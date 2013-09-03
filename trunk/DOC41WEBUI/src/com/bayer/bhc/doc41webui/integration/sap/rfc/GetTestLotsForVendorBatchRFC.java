package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.TestLot;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class GetTestLotsForVendorBatchRFC extends AbstractDoc41RFC<TestLot>{
	//TODO
	private static final String IN_PARTNER = "IV_PARNR???";
	private static final String IN_BATCH = "???";
//	private static final String OUT_RETURNCODE = "EV_RETURN";
	
	private static final String OT_TESTLOTS = "???";
	private static final String OUT_NUMBER = "???";
	private static final String OUT_MAT_NUMBER = "???";
	private static final String OUT_BATCH = "???";
	private static final String OUT_PLANT = "???";
	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetTestLotsForVendorBatchRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String partnerNumber = (String) pInputParms.get(0);
                String batch = (String) pInputParms.get(1);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_PARTNER,partnerNumber);
				sapInput.setValue(IN_BATCH,batch);
            } else {
                throw new SAPException(
                        "GetTestLotsForVendorBatchRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "GetTestLotsForVendorBatchRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(GetTestLotsForVendorBatchRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<TestLot> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(GetTestLotsForVendorBatchRFC.class, null, "processResult():ENTRY");
		ArrayList<TestLot> mResult = new ArrayList<TestLot>();
        if (pFunction != null) {
//            processReturnTable(pFunction);
//            checkReturnCode(pFunction, OUT_RETURNCODE, null);
            JCoTable testlotsTable = pFunction.getTableParameterList().getTable(OT_TESTLOTS);
            if(testlotsTable!=null){
            	for(int i=0;i<testlotsTable.getNumRows();i++){
            		TestLot testlot = new TestLot();
            		testlot.setNumber(testlotsTable.getString(OUT_NUMBER));
            		testlot.setMaterialNumber(testlotsTable.getString(OUT_MAT_NUMBER));
            		testlot.setBatch(testlotsTable.getString(OUT_BATCH));
            		testlot.setPlant(testlotsTable.getString(OUT_PLANT));

            		mResult.add(testlot);
            		testlotsTable.nextRow();
            	}
            }
        }
        Doc41Log.get().debug(GetTestLotsForVendorBatchRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
