package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.DeliveryOrShippingUnit;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class GetDeliveriesWithoutDocumentRFC extends AbstractDoc41RFC<DeliveryOrShippingUnit>{
	private static final String IN_PARTNER = "IV_PARNR";
	private static final String IN_DOCUMENT_TYPE = "IV_DOCTYPE_ID";
//	private static final String OUT_RETURNCODE = "EV_RETURN";
	
	//TODO
	private static final String OT_DELIVERY = "TS_DELIVERIES";
	private static final String OUT_REFERENCE = "???";
	private static final String OUT_FLAG = "???";
	private static final String OUT_SHIP_TO = "KUNWE";
	private static final String OUT_SOLD_TOY = "KUNAG";
	private static final String OUT_FROM = "???";
	private static final String OUT_TO = "???";
	private static final String OUT_GOODS_ISSUE_DATE = "WADAT_IST";
	private static final String OUT_ORDERING_PARTY = "???";
	
	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetDeliveriesWithoutDocumentRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
            	String partnerNumber = (String) pInputParms.get(0);
                String docType = (String) pInputParms.get(1);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_PARTNER,partnerNumber);
				sapInput.setValue(IN_DOCUMENT_TYPE,docType);
            } else {
                throw new SAPException(
                        "GetDeliveriesWithoutDocumentRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "GetDeliveriesWithoutDocumentRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(GetDeliveriesWithoutDocumentRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<DeliveryOrShippingUnit> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(GetDeliveriesWithoutDocumentRFC.class, null, "processResult():ENTRY");
		ArrayList<DeliveryOrShippingUnit> mResult = new ArrayList<DeliveryOrShippingUnit>();
        if (pFunction != null) {
//            processReturnTable(pFunction);
//            checkReturnCode(pFunction, OUT_RETURNCODE, null);
            JCoTable deliveriesTable = pFunction.getTableParameterList().getTable(OT_DELIVERY);
            if(deliveriesTable!=null){
            	for(int i=0;i<deliveriesTable.getNumRows();i++){
            		DeliveryOrShippingUnit del = new DeliveryOrShippingUnit();
            		del.setReferenceNumber(deliveriesTable.getString(OUT_REFERENCE));
            		del.setFlag(deliveriesTable.getString(OUT_FLAG));
            		del.setShipToNumber(deliveriesTable.getString(OUT_SHIP_TO));
            		del.setSoldToNumber(deliveriesTable.getString(OUT_SOLD_TOY));
            		del.setFrom(deliveriesTable.getString(OUT_FROM));
            		del.setTo(deliveriesTable.getString(OUT_TO));
            		del.setGoodsIssueDate(deliveriesTable.getDate(OUT_GOODS_ISSUE_DATE));
            		del.setOrderingParty(deliveriesTable.getString(OUT_ORDERING_PARTY));

            		mResult.add(del);
            		deliveriesTable.nextRow();
            	}
            }
        }
        Doc41Log.get().debug(GetDeliveriesWithoutDocumentRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
