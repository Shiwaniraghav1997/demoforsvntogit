/**
 * File:MaterialAtLoadinLocationRFC.java
 *(C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */

package com.bayer.bhc.doc41webui.integration.sap.rfc;
import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.integration.sap.util.RFCCaller;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

/**
 * MaterialAtLoadingLocation Remote Function Call.
 * 
 * @author evayd
 */
public class DummySysoutRFC<E> implements RFCCaller<E> {
   
    /* (non-Javadoc)
     * @see com.bayer.ecim.foundation.sap.RFCCaller#handleException(java.lang.Exception)
     */
    public void handleException(Exception ex) throws SAPException {
        throw new SAPException(ex.getMessage(),ex);

    }

    /* (non-Javadoc)
     * @see com.bayer.ecim.foundation.sap.RFCCaller#prepareCall(com.sap.mw.jco.JCO.Function, java.util.ArrayList)
     */
    public void prepareCall(JCoFunction pFunction, List<?> pInputParms) throws SAPException {
    	Doc41Log.get().debug(DummySysoutRFC.class, null, "prepareCall():ENTRY");
    	
        outputFunction(pFunction);
        Doc41Log.get().debug(DummySysoutRFC.class, null, "prepareCall():EXIT");
        throw new SAPException("DummySysoutRFC can not be executed", null);
    }

	private void outputFunction(JCoFunction pFunction) throws SAPException {
		if (pFunction != null) {
        	// input parameters
        	JCoParameterList sapInput = pFunction.getImportParameterList();
        	outputFieldIterator("Input Parameters:",sapInput.getParameterFieldIterator(),0);
        	System.out.println("\n\n");
        	
        	//export parameters
        	JCoParameterList sapExport = pFunction.getExportParameterList();
        	if(sapExport!=null){
        		outputFieldIterator("Export Parameters:",sapExport.getParameterFieldIterator(),0);
        	} else {
        		System.out.println("Export Parameters: null");
        	}
        	System.out.println("\n\n");
        	
        	//tables
        	JCoParameterList tableParameterList = pFunction.getTableParameterList();
        	if(tableParameterList!=null){
        		outputFieldIterator("Table Parameters:",tableParameterList.getParameterFieldIterator(),0);
        	} else {
        		System.out.println("Table Parameters: null");
        	}
        	System.out.println("\n\n");
        	
        } else {
            throw new SAPException(
                    "DummySysoutRFC pFunction list is null", null);
        }
	}

	private void outputFieldIterator(String title,
			JCoFieldIterator parameterFieldIterator,
			int indentCount) {
		String tabPrefix="";
		for (int i = 0; i < indentCount; i++) {
			tabPrefix=" "+tabPrefix;
		}
		System.out.println(tabPrefix+title);
		System.out.println(tabPrefix+"-----------------");
		while(parameterFieldIterator.hasNextField()){
			JCoField nextField = parameterFieldIterator.nextField();
			String name = nextField.getName();
			if(nextField.isStructure()){
				outputStructure(name,nextField.getStructure(),indentCount+2);
			} else if(nextField.isTable()) {
				outputTable(name,nextField.getTable(),indentCount+2);
			} else {
				String typeAsString = nextField.getTypeAsString();
				System.out.println(tabPrefix+name+":     "+typeAsString);
			}
		}
		System.out.println(tabPrefix+"-----------------");
	}

    private void outputStructure(String name, JCoStructure structure,int indentCount) {
    	JCoFieldIterator fieldIterator = structure.getFieldIterator();
    	outputFieldIterator("structure: "+name,fieldIterator,indentCount);
	}

	private void outputTable(String name, JCoTable table,int indentCount) {
    	JCoFieldIterator fieldIterator = table.getFieldIterator();
    	outputFieldIterator("table: "+name,fieldIterator,indentCount);
	}

	/* (non-Javadoc)
     * @see com.bayer.ecim.foundation.sap.RFCCaller#processResult(com.sap.mw.jco.JCO.Function)
     */
    public List<E> processResult(JCoFunction pFunction) throws SAPException {
    	outputFunction(pFunction);
    	return new ArrayList<E>();
    }
}
