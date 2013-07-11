package com.bayer.bhc.doc41webui.integration.sap;

import org.junit.Test;

import com.bayer.bhc.doc41webui.Doc41TestCase;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPSingleton;
import com.bayer.ecim.foundation.basic.InitException;
import com.sap.conn.jco.JCoException;


public class SapRfcDumper extends Doc41TestCase{

	@Test
	public void dumpRfc() throws InitException, SAPException, JCoException{
		//String rfcName="getReturnable";
//		String rfcName="searchCustomer";
//		String rfcName="getCustomer";
//		String rfcName="getCustomerPromos";
//		String rfcName="simulateOrder";
//		String rfcName="getCustHistory";
//		String rfcName="getOrderDetails";
		String rfcName="getInvLocationTransactionHistory";
		String dump = SAPSingleton.get().dumpMetadata(rfcName);
		System.out.println(dump);
	}
}
