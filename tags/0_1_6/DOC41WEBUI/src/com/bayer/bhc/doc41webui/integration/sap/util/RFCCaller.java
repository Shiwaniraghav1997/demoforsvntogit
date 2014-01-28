/**
 * $Id: RFCCaller.java,v 1.1 2006/08/03 14:29:47 kei Exp $
 */

package com.bayer.bhc.doc41webui.integration.sap.util;

import java.util.List;

import com.sap.conn.jco.JCoFunction;

/**
 * @author IMKLJ
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface RFCCaller<E> {
	public void prepareCall(JCoFunction pFunction, List<?> pInParms) throws SAPException;
	public List<E> processResult(JCoFunction pFunction) throws SAPException;
	public void handleException(Exception ex) throws SAPException;
}

