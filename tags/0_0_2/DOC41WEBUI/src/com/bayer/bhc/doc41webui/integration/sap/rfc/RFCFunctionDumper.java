/**
 * File:RFCFunctionDumper.java
 *(C) Copyright 2012 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */

package com.bayer.bhc.doc41webui.integration.sap.rfc;

import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

/**
 * RFCFunctionDumper dumps input and output parameters of an SAP Remote Function
 * Call.
 * 
 * @author evayd,imrol
 */
public class RFCFunctionDumper {

	public static String dumpFunction(JCoFunction pFunction)
			throws SAPException {
		StringBuilder tmpBuffer = new StringBuilder();
		if (pFunction != null) {
			tmpBuffer.append("\nSAP FUNCTION: ");
			tmpBuffer.append(pFunction.getName());
			// input parameters
			JCoParameterList sapInput = pFunction.getImportParameterList();
			outputFieldIterator(tmpBuffer, "\nInput Parameters:",
					sapInput.getParameterFieldIterator(), 0);
			tmpBuffer.append("\n\n");

			// export parameters
			JCoParameterList sapExport = pFunction.getExportParameterList();
			if (sapExport != null) {
				outputFieldIterator(tmpBuffer, "Export Parameters:",
						sapExport.getParameterFieldIterator(), 0);
			} else {
				tmpBuffer.append("Export Parameters: null\n");
			}
			tmpBuffer.append("\n\n");

			// tables
			JCoParameterList tableParameterList = pFunction
					.getTableParameterList();
			if (tableParameterList != null) {
				outputFieldIterator(tmpBuffer, "Table Parameters:",
						tableParameterList.getParameterFieldIterator(), 0);
			} else {
				tmpBuffer.append("Table Parameters: null");
			}
			tmpBuffer.append("\n\n");

		} else {
			tmpBuffer.append("RFC Function list is null!\n");
		}
		return tmpBuffer.toString();
	}

	private static void outputFieldIterator(StringBuilder outputBuffer,
			String title, JCoFieldIterator parameterFieldIterator,
			int indentCount) {
		String tabPrefix = "";
		for (int i = 0; i < indentCount; i++) {
			tabPrefix = " " + tabPrefix;
		}
		outputBuffer.append(tabPrefix);
		outputBuffer.append(title);
		outputBuffer.append('\n');
		outputBuffer.append(tabPrefix);
		outputBuffer.append("-----------------\n");
		while (parameterFieldIterator.hasNextField()) {
			JCoField nextField = parameterFieldIterator.nextField();
			String name = nextField.getName();
			if (nextField.isStructure()) {
				outputStructure(outputBuffer, name, nextField.getStructure(),
						indentCount + 2);
			} else if (nextField.isTable()) {
				outputTable(outputBuffer, name, nextField.getTable(),
						indentCount + 2);
			} else {
				String typeAsString = nextField.getTypeAsString();
				outputBuffer.append(tabPrefix + name + " \t\t(" + typeAsString
						+ " " + nextField.getLength() +(nextField.isInitialized()? ") : \t["+""+nextField.getValue()+"]":")")+"\n");
			}
		}
		outputBuffer.append(tabPrefix + "-----------------\n");
	}

	private static void outputStructure(StringBuilder outputBuffer,
			String name, JCoStructure structure, int indentCount) {
		JCoFieldIterator fieldIterator = structure.getFieldIterator();
		outputFieldIterator(outputBuffer, "structure: " + name, fieldIterator,
				indentCount);
/*
		String tabPrefix = "";
		for (int i = 0; i < indentCount; i++) {
			tabPrefix = " " + tabPrefix;
		}
		outputBuffer.append(tabPrefix);
		outputBuffer.append("--- Values: \n");
		fieldIterator.reset();
		boolean komma = false;
		while (fieldIterator.hasNextField()) {
			JCoField nextField = fieldIterator.nextField();
			if (komma) {
				outputBuffer.append(",\t");
			} else {
				komma = true;
				outputBuffer.append(tabPrefix);
			}
			outputBuffer.append(nextField.getValue());
		}
		outputBuffer.append("\n");

		outputBuffer.append(tabPrefix);
		outputBuffer.append("-----------------\n");
		fieldIterator.reset();
*/
	}

	private static void outputTable(StringBuilder outputBuffer, String name,
			JCoTable table, int indentCount) {
		JCoFieldIterator fieldIterator = table.getFieldIterator();
		outputFieldIterator(outputBuffer, "table: " + name, fieldIterator,
				indentCount);
		String tabPrefix = "";
		for (int i = 0; i < indentCount; i++) {
			tabPrefix = " " + tabPrefix;
		}
		outputBuffer.append(tabPrefix);
		outputBuffer.append("----- Values ----\n");
		if (table.getNumRows() > 0) {
			table.firstRow();
		}
		for (int i = 0; i < table.getNumRows(); i++) {
			boolean komma = false;
			fieldIterator.reset();
			while (fieldIterator.hasNextField()) {
				JCoField nextField = fieldIterator.nextField();
				if (komma) {
					outputBuffer.append(",\t");
				} else {
					outputBuffer.append(tabPrefix);
					outputBuffer.append(i + ":\t");
					komma = true;
				}
				outputBuffer.append(nextField.getValue());
			}
			outputBuffer.append("\n");
			table.nextRow();
		}
		outputBuffer.append(tabPrefix);
		outputBuffer.append("-----------------\n");
		//reset table
		if (table.getNumRows() > 0) {
			fieldIterator.reset();
			table.firstRow();
		}
	}

}
