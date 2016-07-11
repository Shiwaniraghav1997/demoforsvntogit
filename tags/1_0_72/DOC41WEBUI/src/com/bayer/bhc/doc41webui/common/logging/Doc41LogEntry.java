/**
 * File:DOC41LogEntry.Java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.logging;

import java.util.Date;

import com.bayer.ecim.foundation.basic.DebugLogEntry;

/**
 * DOC41 specific version of the DebugLogEntry class for logging
 * named data especially for use with special DebugOutputStreams
 * requiring named data to store it column-wise.
 * @author ezzqc
 */
public class Doc41LogEntry extends DebugLogEntry {
	
	/** the id of the executing user. */
	private final String cExecUser;

	/** the component. */
    private final String cComponent;

	/** the type of the action. */
    private final String cActionType;

	/** details about the action (1). */
    private final String cData1;

	/** details about the action (2). */
    private final String cData2;

	/** details about the action (3). */
    private final String cData3;

	/** details about the action (4). */
    private final String cData4;

	/** details about the action (5). */
    private final String cData5;

	/** special date details about the action. */
    private final Date cDataDate;

	/** special numerical details about the action (1). */
    private final Number cDataNumber1;

	/** special numerical details about the action (2). */
    private final Number cDataNumber2;

	/** special numerical details about the action (3). */
    private final Number cDataNumber3;

	
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
        final Number pDataNumber3 ) {
    		super( pUser, null );
    		cExecUser = pExecUser;
    		cComponent = pComponent;
    		cActionType = pActionType;
    		cData1 = pData1;
    		cData2 = pData2;
    		cData3 = pData3;
    		cData4 = pData4;
    		cData5 = pData5;
    		cDataDate = pDataDate;
    		cDataNumber1 = pDataNumber1;
    		cDataNumber2 = pDataNumber2;
    		cDataNumber3 = pDataNumber3;
	}

	/**
	 * Get more specific user informations for the prefix (null if n/a).
	 * @return java.lang.String the string representing this user (null if n/a).
	 */
	public String getUser() {
		return (cUser != null) ? cUser + "|" + ((cExecUser != null) ? cExecUser : cUser) : null;
	}

	/**
	 * Get the unique shortname of this LogEntry object.
	 * @return java.lang.String the unique shortname of this LogEntry object.
	 */
	public String getLogEntryShortName() {
		return "DOC41_LE";
	}

	/**
	 * Get the names of the columns for named data (null entries NOT allowed!!!).
	 * @return java.lang.String[] a StringArray of the names of the columns.
	 */
	public String[] getColumnNames() {
		return new String[] { "user", "execUser", "component", "actionType", "data1", "data2", "data3", "data4", "data5", "logChannelNo", "logChannel", "physChannelNo", "className", "thread", "vmTS", "dataDate", "dataNumber1", "dataNumber2", "dataNumber3", };
	}

	/**
	 * Get the values of the columns for named data (same order AND length like the names!!!).
	 * @return java.lang.Object[] a StringArray of the values of the columns.
	 */
	public Object[] getColumnValues() {
		return new Object[] { cUser, cExecUser, cComponent, cActionType, cData1, cData2, cData3, cData4, cData5, cLogChannelNo, cLogChannel, cPhysChannelNo, cClassName, cThread, cTS, cDataDate, cDataNumber1, cDataNumber2, cDataNumber3 };
	}

}

