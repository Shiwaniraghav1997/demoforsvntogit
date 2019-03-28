package com.bayer.bhc.doc41webui.integration.sap;

import org.junit.Test;

import com.bayer.bhc.doc41webui.Doc41TestCase;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.sap3.SAPException;
import com.bayer.ecim.foundation.sap3.SAPSingleton;
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
		String rfcName="GetCrepInfo";
		String dump = SAPSingleton.get().dumpMetadata(rfcName);
		System.out.println(dump);
		System.out.println(SAPSingleton.get());
	}
}
