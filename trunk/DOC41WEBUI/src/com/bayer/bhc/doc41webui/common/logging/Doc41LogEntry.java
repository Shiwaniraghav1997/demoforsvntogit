/**
 * File:DOC41LogEntry.Java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.logging;

import java.util.Date;

import com.bayer.ecim.foundation.basic.DebugXLogEntry;

/**
 * DOC41 specific version of the DebugLogEntry class for logging
 * named data especially for use with special DebugOutputStreams
 * requiring named data to store it column-wise.
 * @author ezzqc
 */
public class Doc41LogEntry extends DebugXLogEntry {
	
	/**
	 * Constructor for Doc41LogEntry.
	 * @param pUser java.lang.String the cwid of the user.
	 * @param pExecUser java.lang.String the cwid of the executing user.
	 * @param pComponent java.lang.String the component.
	 * @param pActionType java.lang.String the type of the action.
	 * @param pData1 java.lang.String details about the action (1).
	 * @param pData2 java.lang.String details about the action (2).
	 * @param pData3 java.lang.String details about the action (3).
	 * @param pData4 java.lang.String details about the action (4).
	 * @param pData5 java.lang.String details about the action (5).
	 * @param pDataDate java.util.Date special date details about the action.
	 * @param pDataNumber1 java.lang.Number special numerical details about the action (1).
	 * @param pDataNumber2 java.lang.Number special numerical details about the action (2).
	 * @param pDataNumber3 java.lang.Number special numerical details about the action (3).
	 */
	public Doc41LogEntry(
		final String pUser,
        final String pExecUser,
        final String pComponent,
        final String pActionType,
        final String pData1,
        final String pData2,
        final String pData3,
        final String pData4,
        final String pData5,
        final Date   pDataDate,
        final Number pDataNumber1,
        final Number pDataNumber2,
        final Number pDataNumber3 )
	{
	    super(pUser, pExecUser, pComponent, pActionType, pData1, pData2, pData3, pData4, pData5, pDataDate, pDataNumber1, pDataNumber2, pDataNumber3, /*pDataNumber4*/null, /*pDataNumber5*/null, /*pDataId1*/null, /*pDataId2*/null, /*pDataId3*/null, /*pDataDspTextCode*/null, /*pDataTextTypeId*/null, /*pMandant*/null, /*pRegion*/null, /*pClient*/null, /*pModel*/null, /*pComponent*/null);
	}

	/**
	 * Get the unique shortname of this LogEntry object.
	 * @return java.lang.String the unique shortname of this LogEntry object.
	 */
	@Override
	public String getLogEntryShortName() {
		return "DOC41_LE";
	}

}

