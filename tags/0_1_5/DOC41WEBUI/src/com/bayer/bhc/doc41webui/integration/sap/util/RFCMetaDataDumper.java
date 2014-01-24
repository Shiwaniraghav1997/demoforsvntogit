/**
 * File:RFCMetaDataDumper.java
 *(C) Copyright 2012 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */

package com.bayer.bhc.doc41webui.integration.sap.util;

import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

/**
 * RFCMetaDataDumper dumps input and output parameters of an SAP Remote Function
 * Call.
 * 
 * @author evayd,imrol
 */
public class RFCMetaDataDumper {

	public static String dumpFunction(JCoFunction pFunction)
			throws SAPException {
		StringBuilder tmpBuffer = new StringBuilder();
		if (pFunction != null) {
			tmpBuffer.append("\nSAP FUNCTION: \n");
			tmpBuffer.append(pFunction.getName()+"\n");
			// input parameters
			JCoParameterList sapInput = pFunction.getImportParameterList();
			outputRecord(tmpBuffer, "\nInput Parameters:",
					sapInput, 0);
			tmpBuffer.append("\n\n\n");

			// export parameters
			JCoParameterList sapExport = pFunction.getExportParameterList();
			if (sapExport != null) {
				outputRecord(tmpBuffer, "Export Parameters:",
						sapExport, 0);
			} else {
				tmpBuffer.append("Export Parameters: null\n\n");
			}
			tmpBuffer.append("\n\n\n");
			
			// changing
			JCoParameterList changingParameterList = pFunction
					.getChangingParameterList();
			if (changingParameterList != null) {
				outputRecord(tmpBuffer, "Changing Parameters:",
						changingParameterList, 0);
			} else {
				tmpBuffer.append("Changing Parameters: null");
			}
			tmpBuffer.append("\n\n\n"); 

			// tables
			JCoParameterList tableParameterList = pFunction
					.getTableParameterList();
			if (tableParameterList != null) {
				outputRecord(tmpBuffer, "Table Parameters:",
						tableParameterList, 0);
			} else {
				tmpBuffer.append("Table Parameters: null");
			}
			tmpBuffer.append("\n\n\n");

		} else {
			tmpBuffer.append("RFC Function list is null!\n\n");
		}
		return tmpBuffer.toString();
	}

	

	private static void outputRecord(StringBuilder tmpBuffer,String title,
			JCoRecord record, 
			int indentCount) {
		String tabPrefix="";
		for (int i = 0; i < indentCount; i++) {
			tabPrefix=" "+tabPrefix;
		}
		tmpBuffer.append(tabPrefix+title+"\n");
		tmpBuffer.append(tabPrefix+"-----------------\n");
		if(record==null){
			tmpBuffer.append("null\n");
		} else {
			tmpBuffer.append(getMetadata(record,tabPrefix));
			JCoFieldIterator parameterFieldIterator = record.getFieldIterator();
			while(parameterFieldIterator  .hasNextField()){
				JCoField nextField = parameterFieldIterator.nextField();
				String name = nextField.getName();
				if(nextField.isStructure()){
					outputStructure(tmpBuffer,name,nextField.getStructure(),indentCount+2);
				} else if(nextField.isTable()) {
					outputTable(tmpBuffer,name,nextField.getTable(),indentCount+2);
				} 
			}
		}
		tmpBuffer.append(tabPrefix+"-----------------"+"\n");
	}

    private static String getMetadata(JCoRecord record, String tabPrefix) {
		String metaData = record.getMetaData().toString();
		metaData = metaData.replace("\n", "\n"+tabPrefix);
		metaData = metaData.trim();
		return tabPrefix+metaData+"\n";
	}



	private static void outputStructure(StringBuilder tmpBuffer,String name, JCoStructure structure,int indentCount) {
    	outputRecord(tmpBuffer,"structure: "+name,structure,indentCount);
	}

	private static void outputTable(StringBuilder tmpBuffer,String name, JCoTable table,int indentCount) {
    	outputRecord(tmpBuffer,"table: "+name,table,indentCount);
	}

}
