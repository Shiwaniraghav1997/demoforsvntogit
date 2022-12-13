package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.Doc41Utils;
import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.sap3.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class FindDocsRFC extends AbstractDoc41RFC<HitListEntry> {
	private static final String IT_OBJID_RANGE = "IT_OBJID_RANGE";
	private static final String IN_MAXHIT = "IV_MAXHIT";
	private static final String IN_MAX_VER_ONLY = "IV_GET_MAX_VERS";
	private static final String IN_OBJ_TYPE = "IV_OBJ_TYPE";
	private static final String IN_ATT_NAME = "IV_ATT_NAME";
	private static final String IN_SIGN = "SIGN";
	private static final String IN_OPTION = "OPTION";
	private static final String IN_LOW = "LOW";
	private static final String IN_VERID_BOM = "IV_VERID_BOM";
	private static final String IN_PLANT_BOM = "IV_PLANT_BOM";
//	private static final String IV_PO_BOM = "IV_PO_BOM";
	
	private static final String IN_TIME_FRAME_DATE = "IV_TIMEFRAME_DATE";
	private static final String IT_VALUE_RANGE = "IT_VALUE_RANGE";
	private static final String IT_D41ID = "IT_D41ID";
	private static final String D41ID = "D41ID";
	private static final String OT_HITS = "OT_HITS";
	private static final String OUT_D41ID = "D41ID";
	private static final String OUT_DOC_ID = "DOCID";
	private static final String OUT_OBJ_ID = "OBJID";
	private static final String OUT_STOR_DATE = "ADATE";
	private static final String OUT_AL_DATE = "LDATE";
	private static final String OUT_AL_TIME = "LTIME";
	private static final String OUT_OBJ_TYPE = "OBJTY";
	private static final String OUT_DOC_CLASS = "CLASS";
	private static final String OUT_VAL = "VAL0";
//	private static final String OUT_RETURNCODE = "EV_RETURN";
	//private static final String RETURNCODE_BOM_PLANT_NOT_FOUND = "1";

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms) throws SAPException {
		Doc41Log.get().debug(this, null, "ENTRY");
		if (pFunction != null) {
//			System.out.println("pInputParms:"+pInputParms.toString());
			if (pInputParms != null) {
				@SuppressWarnings("unchecked")
				List<String> d41idList = (List<String>) pInputParms.get(0);
//				System.out.println("d41idList::"+d41idList);
				String sapObj = (String) pInputParms.get(1); // expected to be null, when using FindDocsMulti!!!
				@SuppressWarnings("unchecked")
				List<String> objectIds = (List<String>) pInputParms.get(2);
				Integer maxResults = (Integer) pInputParms.get(3);
//				String PurchaseOrder = (String) pInputParms.get(3);
				Boolean maxVersionOnly = (Boolean) pInputParms.get(4);
				@SuppressWarnings("unchecked")
				Map<String, String> attribValues = (Map<String, String>) pInputParms.get(5);
				@SuppressWarnings("unchecked")
				Map<Integer, String> seqToKey = (Map<Integer, String>) pInputParms.get(6);
				JCoParameterList sapInput = pFunction.getImportParameterList();
				JCoParameterList tableParameterList = pFunction.getTableParameterList();
				// Old FindDocs2, only single D41ID
				// sapInput.setValue(IN_D41ID,d41id);
				JCoTable table = sapInput.getTable(IT_D41ID);
				for (String d41id : d41idList) {
					table.appendRow();
					table.setValue(D41ID, d41id);
				}
//				sapInput.setValue(IV_PO_BOM, PurchaseOrder);
				sapInput.setValue(IN_MAXHIT, maxResults);
				// TODO: Here you can temporary disable LAST_VER_SEARCH - new: YOU NEED TO
				// EXPICITLY set FALSE (TRUE is new default!)
				// sapInput.setValue(IN_MAX_VER_ONLY,sapBooleanToChar(Boolean.FALSE));
				sapInput.setValue(IN_MAX_VER_ONLY, sapBooleanToChar(maxVersionOnly));
				if (attribValues.get(IN_PLANT_BOM) != null) {
					sapInput.setValue(IN_PLANT_BOM, attribValues.get(IN_PLANT_BOM));
				}
				if (attribValues.get(IN_VERID_BOM) != null) {
					sapInput.setValue(IN_VERID_BOM, attribValues.get(IN_VERID_BOM));
				}
				if (attribValues.get(IN_TIME_FRAME_DATE) != null) {
					sapInput.setValue(IN_TIME_FRAME_DATE, Doc41Utils.convertStringToDate(attribValues.get(IN_TIME_FRAME_DATE)));
				}
				if (objectIds != null && !objectIds.isEmpty()) {
					setParamObjectIDs(objectIds, sapObj, 1, sapInput, tableParameterList);
				}
				for (int seqno = 1; seqno <= seqToKey.size(); seqno++) {
					String key = seqToKey.get(seqno);
					if (key == null) {
						throw new SAPException("seqno in attributes have to be consecutively numbered starting with 1", null);
					}
					String value = attribValues.get(key);
					if (!StringTool.isTrimmedEmptyOrNull(value)) {
						Doc41Log.get().debug(this, null, "*** CUST SEARCH INPUT-" + seqno + " : '" + key + "'='" + value + "' ***");
						setParamValue(key, value, seqno, sapInput, tableParameterList);
					} else {
						Doc41Log.get().debug(this, null, "*** CUST SEARCH INPUT-" + seqno + " : '" + key + "' IS EMPTY ***");
						setEmptyParam(key, seqno, sapInput);
					}
				}
				if (Doc41Log.get().isDebugActive()) {
					Doc41Log.get().debug(this, null, RFCFunctionDumper.dumpFunction(pFunction));
				}
				Doc41Log.get().debug(this, null, "attributs are set");
			} else {
				throw new SAPException("FindDocsRFC pInputParms list is null", null);
			}
		} else {
			throw new SAPException("FindDocsRFC pFunction list is null", null);
		}
		Doc41Log.get().debug(FindDocsRFC.class, null, "EXIT");
	}

	private void setParamValue(String key, String value, int paramNumber, JCoParameterList sapInput, JCoParameterList tableParameterList) {
		sapInput.setValue(IN_ATT_NAME + paramNumber, key);

		JCoTable table = tableParameterList.getTable(IT_VALUE_RANGE + paramNumber);
		if (value != null && value.contains("###")) {
			String[] singleValues = value.split("###");
			for (String oneValue : singleValues) {
				if (!StringTool.isTrimmedEmptyOrNull(oneValue)) {
					addOneValue(oneValue, table);
				}
			}
		} else {
			addOneValue(value, table);
		}
	}

	private void addOneValue(String value, JCoTable table) {
		table.appendRow();
		table.setValue(IN_SIGN, "I");
		table.setValue(IN_OPTION, "EQ");
		table.setValue(IN_LOW, value /** .toUpperCase() */
		); // removed (IMWIF, caused Test failure, SAP searches case sensitive)
	}

	private void setEmptyParam(String key, int paramNumber, JCoParameterList sapInput) {
		sapInput.setValue(IN_ATT_NAME + paramNumber, key);
	}

	/**
	 * Set InputParameters for FindDocs2/FindDocsMulti.
	 * 
	 * @param objectIds
	 *            list still supported, but only as list within table 1 (see
	 *            paramNumber)
	 * @param sapObj
	 *            not set anymore, new findDocsMulty searches automatically accros
	 *            all sapObj (type of object_Id, e.g. material number, shipping
	 *            unit, delivery number...)
	 * @param paramNumber
	 *            only 1 supported, further tables no more exist in findDocsMulti
	 * @param sapInput
	 * @param tableParameterList
	 */
	private void setParamObjectIDs(List<String> objectIds, String sapObj, int paramNumber, JCoParameterList sapInput, JCoParameterList tableParameterList) {
		if (sapObj != null) { // deprecated, only FinDocs2, no more at FindDocsMulti...
			sapInput.setValue(IN_OBJ_TYPE + paramNumber, sapObj);
		}

		JCoTable table = tableParameterList.getTable(IT_OBJID_RANGE + paramNumber);
		for (String objectId : objectIds) {
			addOneValue(objectId, table);
		}
	}

	@Override
	public List<HitListEntry> processResult(JCoFunction pFunction) throws SAPException {
//		System.out.println("i am in processResult method");
		Doc41Log.get().debug(FindDocsRFC.class, null, "ENTRY");
		ArrayList<HitListEntry> mResult = new ArrayList<HitListEntry>();
		
		if (pFunction != null) {
			if (Doc41Log.get().isDebugActive()) {
				Doc41Log.get().debug(getClass(), null, RFCFunctionDumper.dumpFunction(pFunction));
			}
			processReturnTable(pFunction, "OT_RETURN");
			//JCoParameterList exportParameterList = pFunction.getExportParameterList();
			//String returnCode = exportParameterList.getString(OUT_RETURNCODE);
			//returnCode="1";
//			mResult.add(mapReturnCodeToTag(returnCode));
			/* if (returnCode.equals("1")) {
				 returnCode= Doc41ValidationUtils.ERROR_MESSAGE_BOM_NOT_FOUND;
				 doc.setRetunCode(returnCode);
				 mResult.add(doc);
				 
			} 
			
			 doc.setRetunCode(returnCode);
			 mResult.add(doc);*/
//			ev_return = 1
			JCoTable table = pFunction.getTableParameterList().getTable(OT_HITS);
		//	System.out.println("table:"+table.toString());
			if (table != null) {
				for (int i = 0; i < table.getNumRows(); i++) {
					HitListEntry doc = new HitListEntry();
					doc.setDoc41Id(table.getString(OUT_D41ID));
					doc.setDocId(table.getString(OUT_DOC_ID));
					doc.setObjectId(table.getString(OUT_OBJ_ID));
					doc.setStorageDate(table.getDate(OUT_STOR_DATE));
					String arTime = table.getString(OUT_AL_TIME);
					String arDate = table.getString(OUT_AL_DATE);
					doc.setArchiveLinkDate(mergeSapDateTime(arDate, arTime, "yyyy-MM-dd", "HH:mm:ss"));
					doc.setObjectType(table.getString(OUT_OBJ_TYPE));
					doc.setDocumentClass(table.getString(OUT_DOC_CLASS));

					String[] custValues = new String[Doc41Constants.CUSTOMIZATION_VALUES_COUNT];
					for (int v = 0; v < Doc41Constants.CUSTOMIZATION_VALUES_COUNT; v++) {
						custValues[v] = table.getString(OUT_VAL + (v + 1));
					}
					doc.setCustomizedValues(custValues);
					
					table.nextRow();
					mResult.add(doc);
				}
			}

		}
		Doc41Log.get().debug(FindDocsRFC.class, null, "EXIT");
		//System.out.println("mResult:"+mResult.toString());
		return mResult;
	}


	
}
