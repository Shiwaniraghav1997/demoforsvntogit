package com.bayer.bhc.doc41webui.integration.sap.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;

@Component
public class AuthorizationRFCService extends AbstractSAPJCOService {
	
//	private static final String RFC_NAME_CHECK_DELIVERY_FOR_PARTNER						= "CheckDeliveryForPartner";
//	private static final String RFC_NAME_GET_DELIVERIES_WITHOUT_DOC						="GetDeliveriesWithoutDocument";
//	private static final String RFC_NAME_CHECK_DELIVERY_EXISTS							="CheckDeliveryNumberExists";
	private static final String RFC_NAME_CHECK_DELIVERY_NUMBER_MATERIAL					="CheckDeliveryNumberMaterial";
//	private static final String RFC_NAME_CHECK_DELIVERY_NUMBER_CONTAINER_PACKING_LIST	="CheckDeliveryNumberContainerPackingList";
//	private static final String RFC_NAME_CHECK_ARTWORK_FOR_VENDOR						="CheckArtworkForVendor";
//	private static final String RFC_NAME_CHECK_LAYOUT_FOR_VENDOR						="CheckLayoutForVendor";
//	private static final String RFC_NAME_CHECK_PO_AND_MATERIAL_FOR_VENDOR				="CheckPOAndMaterialForVendor";
	
	
	public String checkCoADeliveryNumberMaterial(String deliveryNumber, String matNo) throws Doc41ServiceException{
		// logging
        Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"checkCoADeliveryNumberMaterial() - deliveryNumber="+deliveryNumber+", matNo="+matNo+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(deliveryNumber);
        params.add(matNo);
        
        List<String> returnTexts = performRFC(params,RFC_NAME_CHECK_DELIVERY_NUMBER_MATERIAL);
        
        String errorMsg=null;
        if(!returnTexts.isEmpty()){
        	errorMsg = returnTexts.get(0);
        }
		return errorMsg ;
	}


	public boolean checkDeliveryForPartner(String carrier,
			String deliveryNumber, String shippingUnitNumber) throws Doc41ServiceException{
		// TODO use real RFC
		return true;
	}


	public boolean checkDeliveryNumberExists(String deliveryNumber) throws Doc41ServiceException {
		// TODO use real RFC
		return true;
	}
	
}
