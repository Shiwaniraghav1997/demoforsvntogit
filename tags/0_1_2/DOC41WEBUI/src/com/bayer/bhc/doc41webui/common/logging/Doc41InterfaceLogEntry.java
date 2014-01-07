/**
 *(C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.logging;

import java.util.Date;

import com.bayer.ecim.foundation.basic.DebugLogEntry;
import com.bayer.ecim.foundation.basic.StringTool;

/**
 * Cargo Cockpit Back Ground Interfaces(like TM3) monitoring Log Entry Class to be used with 
 * special DebugOutputStreams to store monitoring data column wise in a Table.
 * 
 * @author mbghy
 * @id $Id: Doc41InterfaceLogEntry.java,v 1.1 2012/03/09 10:30:51 ezzqc Exp $
 */
public class Doc41InterfaceLogEntry extends DebugLogEntry {
    

    /** details about the Last Request Date. */
    private final Date cLastRequest;

    /** details about the cInterfcaeName. */
    private final String cInterfaceName;

    /** the id of the executing user. */
    private final String cUser;

    /** the type of the action. */
    private final String cAction;

    /** the status of action . */
    private final String cStatus;

    /** the Comments related to action. */
    private final String cRemarks;

    /** the Comments related to action. */
    private final String cDetails;
    
    /** the response time of action. */
    private final long cResponseTime;
    
    /** the id of the executing user. */
    private final String cCreatedBy;

    /** the id of the executing user. */
    private final String cChangedBy;

    /**
     * Constructor for Doc41InterfaceLogEntry.
     * 
     * @param pUser java.lang.String the cwid of the executing user.
     * @param pInterfaceName java.lang.String the Monitoring Interface Details.
     * @param pAction java.lang.String the action event.
     * @param pRequested java.util.Date action processing Time stamp.
     * @param pStatus java.lang.String the status Details related to action.
     * @param pRemarks java.lang.String comments related to action .
     * @param pDetails java.lang.String Action related details .
     * @param pResponseTime java.lang.Long response time of action .
     */
    public Doc41InterfaceLogEntry(final String pUser,
            final String pInterfaceName, final String pAction, final Date pRequested,
            final String pStatus, final String pRemarks,final String pDetails,final long pResponseTime) {
        super(pUser, null);
        cInterfaceName = StringTool.maxRString(pInterfaceName,50,"...");
        cLastRequest = pRequested;
        cUser = pUser;
        cAction = StringTool.maxRString(pAction,30,"...");
        cStatus = StringTool.maxRString(pStatus,10,"...");
        cRemarks = StringTool.maxRString(pRemarks,100,"...");
        cDetails = StringTool.maxRString(pDetails, 250,"...");
        cResponseTime=pResponseTime;
        cCreatedBy = pUser;
        cChangedBy = pUser;
    }

    /**
     * Get more specific user informations for the prefix (null if n/a).
     * 
     * @return java.lang.String the string representing this user (null if n/a).
     */
    public String getUser() {
        return (cUser != null) ? cUser + "|" + ((cUser != null) ? cUser : cUser) : null;
    }

    /**
     * Get the unique shortname of this LogEntry object.
     * 
     * @return java.lang.String the unique shortname of this LogEntry object.
     */
    public String getLogEntryShortName() {
        return "DOC41_ILE";
    }

    /**
     * Get the names of the columns for named data (null entries NOT allowed!!!).
     * 
     * @return java.lang.String[] a StringArray of the names of the columns.
     */
    public String[] getColumnNames() {
        return new String[] { "user","interfaceName", "actionType","latestRequest","status","remarks","details","responseTime","createdBy", "changedBy" };
    }

    /**
     * Get the values of the columns for named data (same order AND length like the names!!!).
     * 
     * @return java.lang.Object[] a StringArray of the values of the columns.
     */
    public Object[] getColumnValues() {
        return new Object[] { cUser, cInterfaceName, cAction, cLastRequest, cStatus,cRemarks ,cDetails,cResponseTime,cCreatedBy, cChangedBy};
    }

}
