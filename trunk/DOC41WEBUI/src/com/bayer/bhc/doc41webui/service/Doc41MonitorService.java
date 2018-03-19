/**
 *(C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.logging.Doc41InterfaceLogEntry;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.ecim.foundation.basic.Dbg;

/**
 * Doc41 Monitor Service Implementation.
 * 
 * @author mbghy, ezzqc
 */
@Component
public class Doc41MonitorService {

    /** the logging channel short name */
    private static final String SHORT_NAME="IM_LOG";

    /** the logging channel name */
    private static final String NAME="DOC41_IM";

    /** the logging channel property prefix */
    private static final String PROPERTY_PREFIX="doc41.im.sl";

    /** the logging channel */
    private static final int INTERFACE_LOGGING=Dbg.get().getChannelByName(SHORT_NAME,NAME,PROPERTY_PREFIX,false);

    /** a flag indicating whether logging is active / IMWIF: WARNING, THIS IS NOT STATIC!!! And it makes no sense to check on this level, Debug will already do for you just 2 commands later!!! */
    //private static final boolean INTERFACE_LOGGING_ACTIVE=Dbg.get().isLogicalChannelActive(INTERFACE_LOGGING);

    /**
     * Monitors The each and every request processing of a specified back ground Interfaces(like RFC Services, LDAP)
     *  of Bayer Order Entry and logs the entry into data base table.
     * @param pInterfaceName java.lang.String the Monitoring Interface Details.
     * @param pAction java.lang.String the action event.
     * @param pRequested java.util.Date action processing Time stamp.
     * @param pStatus java.lang.String the status Details related to action.
     * @param pRemarks java.lang.String comments related to action .
     * @param pDetails java.lang.String Action related details .
     * @param pResponseTime java.lang.Long response time of action .
     */
    public void monitor(String pInterfaceName, String pAction, Date pRequested, String pStatus, String pRemarks,String pDetails,long pResponseTime) {
    	//if (INTERFACE_LOGGING_ACTIVE) {
        	String usr = UserInSession.getCwid();
            Dbg.get().println(
                    INTERFACE_LOGGING,
                    this,
                    usr,
                    new Doc41InterfaceLogEntry(usr, pInterfaceName, pAction, pRequested,pStatus, pRemarks,pDetails,pResponseTime));
    	//}
    }

}
