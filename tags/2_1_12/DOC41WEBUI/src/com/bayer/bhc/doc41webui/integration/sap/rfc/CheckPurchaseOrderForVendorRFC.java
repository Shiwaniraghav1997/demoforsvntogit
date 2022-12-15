package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.Doc41ValidationUtils;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.sap3.SAPException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class CheckPurchaseOrderForVendorRFC extends AbstractDoc41RFC<String> {
	private static final String IN_VENDOR = "IV_VENDOR";
	private static final String IN_PO = "IV_PONUMBER";
	private static final String IN_PV ="IV_PVERSION";
	private static final String IN_MAT="IV_MATNR";
	//private static final String IN_FLAG="IV_FLAG";

	private static final String OUT_RETURNCODE = "EV_RETURN";
	private static final String OUT_FLAG = "EV_FLAG";
	private static final String OUT_MATERIAL = "ET_MAT_VER";

	private static final String RETURNCODE_OK = "0";
	private static final String RETURNCODE_PO_NOT_FOUND = "1";
//	 private static final String RETURNCODE_NO_MAT_FOR_PO = "3";
	private static final String RETURNCODE_PV_NOT_FOUND = "2";
	
	/*For BomPLant added by elerj*/
	private static final String RETURNCODE_NO_BOM_PLANT = "3";
	//private static final String RETURNCODE_NO_BOM_USAGE = "6";


	
	// @Override
	public void prepareCall(JCoFunction jCoFunction, List<?> inputParameterList) throws SAPException {
		Doc41Log.get().debug(this, null, "ENTRY");
		/*System.out.println("in Specification RFC call" + inputParameterList);*/
		if (jCoFunction != null) {
			if (inputParameterList != null) {
				//System.out.println("purchaseOrderNumberll:"+inputParameterList.toString());
				String vendorNumber = (String) inputParameterList.get(1);
				String purchaseOrderNumber = (String) inputParameterList.get(0);
				String productionVersion = (String) inputParameterList.get(2);
				String materialNo = (String) inputParameterList.get(3);
				//String flag =  (String) inputParameterList.get(4);
//				System.out.println("purchaseOrderNumber:"+purchaseOrderNumber);
//				System.out.println("vendorNumber:"+vendorNumber);
//				System.out.println("productionVersion:"+productionVersion);
//				System.out.println("materialNo:"+materialNo);
//				System.out.println("flag:"+flag);
				
				

				JCoParameterList sapInput = jCoFunction.getImportParameterList();
				sapInput.setValue(IN_VENDOR, vendorNumber);
				sapInput.setValue(IN_PV, productionVersion);
				if (purchaseOrderNumber != null) {
					sapInput.setValue(IN_PO, purchaseOrderNumber);
				}
				if (materialNo != null) {
					sapInput.setValue(IN_MAT, materialNo);
				}
				/*if (flag != null) {
					sapInput.setValue(IN_FLAG, flag);
				}*/
				if (Doc41Log.get().isDebugActive()) {
					Doc41Log.get().debug(this, null, RFCFunctionDumper.dumpFunction(jCoFunction));
				}
				Doc41Log.get().debug(this, null, "attributs are set");
			} else {
				throw new SAPException("Specification pInputParms list is null", null);
			}
		} else {
			throw new SAPException("Specification pFunction list is null", null);
		}
		Doc41Log.get().debug(this, null, "prepareCall():EXIT");
	}

	//@SuppressWarnings("unchecked")
	@Override
	public List<String> processResult(JCoFunction pFunction) throws SAPException {
		Doc41Log.get().debug(this, null, "processResult():ENTRY");
		List<String> mResult = new ArrayList<String>();
		ArrayList<String> material_list = new ArrayList<String>();
		ArrayList<String> pv_list = new ArrayList<String>();
		ArrayList<String> Mat_Des = new ArrayList<String>();
//		Map<String,ArrayList<Object>> multiMap = new HashMap<String, ArrayList<Object>>();
		if (pFunction != null) {
			if (Doc41Log.get().isDebugActive()) {
				Doc41Log.get().debug(getClass(), null, RFCFunctionDumper.dumpFunction(pFunction));
			}
			JCoParameterList exportParameterList = pFunction.getExportParameterList();
			String returnCode = exportParameterList.getString(OUT_RETURNCODE);
		//	System.out.println("returnCode:"+returnCode);
		//	returnCode="3";
			//System.out.println(" after returnCode:"+returnCode);
			
			mResult.add(mapReturnCodeToTag(returnCode));
			String outFlag = exportParameterList.getString(OUT_FLAG);
			mResult.add(outFlag);
			JCoTable outMaterial = pFunction.getTableParameterList().getTable(OUT_MATERIAL);
			  
			//JCoFieldIterator iter = outMaterial.getFieldIterator();
			
			for (int i = 0; i < outMaterial.getNumRows(); i++)
		    {
				outMaterial.setRow(i);
				 // JCoField field = iter.nextField();
				  String mat= (String) outMaterial.getValue("MATNR");
				  material_list.add(mat);
				  String P_iv= (String) outMaterial.getValue("P_VER");
				  pv_list.add(P_iv);
				  String MAt_Test= (String) outMaterial.getValue("MAKTX");
				  Mat_Des.add(MAt_Test);
				
		    }
			mResult.addAll(material_list);
			  mResult.addAll(pv_list);
			  mResult.addAll(Mat_Des);
			
		}
		Doc41Log.get().debug(this, null, "EXIT");
		return mResult;
	}

	private String mapReturnCodeToTag(String returnCode) {
		if (StringTool.equals(returnCode, RETURNCODE_OK)) {
			return null;
		} else if (StringTool.equals(returnCode, RETURNCODE_PO_NOT_FOUND)) {
			return Doc41ValidationUtils.ERROR_MESSAGE_PO_NOT_FOUND;
		}
	 else if (StringTool.equals(returnCode, RETURNCODE_NO_BOM_PLANT)) {
			return Doc41ValidationUtils.ERROR_MESSAGE_BOM_NOT_FOUND;
		} 
			 
			  else if (StringTool.equals(returnCode, RETURNCODE_PV_NOT_FOUND)) {
			return Doc41ValidationUtils.ERROR_MESSAGE_PV_NOT_FOUND;
		}
		return Doc41ValidationUtils.ERROR_MESSAGE_UNKNOWN_RETURN_CODE;
	}

}



