package com.bayer.bhc.doc41webui.integration.sap.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.DeliveryOrShippingUnit;
import com.bayer.bhc.doc41webui.domain.InspectionLot;
import com.bayer.bhc.doc41webui.domain.SDReferenceCheckResult;
import com.bayer.bhc.doc41webui.domain.UserPartner;

@Component
public class AuthorizationRFCService extends AbstractSAPJCOService {
	
	private static final String RFC_NAME_CHECK_DELIVERY_FOR_PARTNER						= "CheckDeliveryForPartner";
	private static final String RFC_NAME_GET_DELIVERIES_WITHOUT_DOC						="GetDeliveriesWithoutDocument";
//	private static final String RFC_NAME_CHECK_DELIVERY_EXISTS							="CheckDeliveryNumberExists";
	private static final String RFC_NAME_CHECK_DELIVERY_NUMBER_MATERIAL					="CheckDeliveryNumberMaterial";
//	private static final String RFC_NAME_CHECK_DELIVERY_NUMBER_CONTAINER_PACKING_LIST	="CheckDeliveryNumberContainerPackingList";
	private static final String RFC_NAME_CHECK_ARTWORK_FOR_VENDOR						="CheckArtworkForVendor";
	private static final String RFC_NAME_CHECK_LAYOUT_FOR_VENDOR						="CheckLayoutForVendor";
	private static final String RFC_NAME_CHECK_PO_AND_MATERIAL_FOR_VENDOR				="CheckPOAndMaterialForVendor";
	private static final String RFC_NAME_CHECK_PARTNER									="CheckPartner";
	private static final String RFC_NAME_CHECK_MATERIAL_AND_BATCH_FOR_VENDOR			="CheckMaterialAndBatchForVendor";
	private static final String RFC_NAME_GET_INSPECTION_LOTS_FOR_VENDOR_BATCH			="GetInspectionLotsForVendorBatch";
	
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


	public SDReferenceCheckResult checkDeliveryForPartner(String carrier,
			String referenceNumber) throws Doc41ServiceException{
		 Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"checkDeliveryForPartner() - deliveryNumber="+referenceNumber+", carrier="+carrier+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(carrier);
        params.add(referenceNumber);
        
        List<SDReferenceCheckResult> results = performRFC(params,RFC_NAME_CHECK_DELIVERY_FOR_PARTNER);
        
        if(!results.isEmpty()){
        	return results.get(0);
        } else {
        	throw new Doc41ServiceException("no result from RFC: "+RFC_NAME_CHECK_DELIVERY_FOR_PARTNER);
        }
	}
	
	public List<DeliveryOrShippingUnit> getOpenDeliveries(String d41id, String carrier) throws Doc41ServiceException{
		Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"getOpenDeliveries() - d41id="+d41id+", carrier="+carrier+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(carrier);
        params.add(d41id);
        
        List<DeliveryOrShippingUnit> deliveries = performRFC(params,RFC_NAME_GET_DELIVERIES_WITHOUT_DOC);
        
		return deliveries ;
	}
	
	public UserPartner checkPartner(String partner) throws Doc41ServiceException{
		 Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"checkPartner() - partner="+partner+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(partner);
        
        List<UserPartner> ups = performRFC(params,RFC_NAME_CHECK_PARTNER);
        
        UserPartner up=null;
        if(!ups.isEmpty()){
        	up = ups.get(0);
        }
		return up ;
	}


	public String checkArtworkForVendor(String vendorNumber) throws Doc41ServiceException{
		Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"checkArtworkForVendor() - vendorNumber="+vendorNumber+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(vendorNumber);
        
        List<String> returnTexts = performRFC(params,RFC_NAME_CHECK_ARTWORK_FOR_VENDOR);
        
        String errorMsg=null;
        if(!returnTexts.isEmpty()){
        	errorMsg = returnTexts.get(0);
        }
		return errorMsg ;
	}


	public String checkLayoutForVendor(String vendorNumber) throws Doc41ServiceException{
		Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"checkLayoutForVendor() - vendorNumber="+vendorNumber+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(vendorNumber);
        
        List<String> returnTexts = performRFC(params,RFC_NAME_CHECK_LAYOUT_FOR_VENDOR);
        
        String errorMsg=null;
        if(!returnTexts.isEmpty()){
        	errorMsg = returnTexts.get(0);
        }
		return errorMsg ;
	}
	
	public String checkPOAndMaterialForVendor(String vendorNumber, String poNumber, String materialNumber) throws Doc41ServiceException{
		Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"checkPOAndMaterialForVendor() - vendorNumber="+vendorNumber+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(vendorNumber);
        params.add(poNumber);
        params.add(materialNumber);
        
        List<String> returnTexts = performRFC(params,RFC_NAME_CHECK_PO_AND_MATERIAL_FOR_VENDOR);
        
        String errorMsg=null;
        if(!returnTexts.isEmpty()){
        	errorMsg = returnTexts.get(0);
        }
		return errorMsg ;
	}
	
	public String checkMaterialAndBatchForVendor(String matNumber, String batch, String vendorNumber) throws Doc41ServiceException{
		Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"checkMaterialAndBatchForVendor() - matNumber="+matNumber+", batch="+batch+", vendorNumber="+vendorNumber+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(matNumber);
        params.add(batch);
        params.add(vendorNumber);
        
        List<String> returnTexts = performRFC(params,RFC_NAME_CHECK_MATERIAL_AND_BATCH_FOR_VENDOR);
        
        String errorMsg=null;
        if(!returnTexts.isEmpty()){
        	errorMsg = returnTexts.get(0);
        }
		return errorMsg ;
	}
	
	public List<InspectionLot> getInspectionLotsForVendorBatch(String vendor, String vendorBatch, String plant) throws Doc41ServiceException{
		Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"getInspectionLotsForVendorBatch() - vendor="+vendor+", vendorBatch="+vendorBatch+", plant="+plant+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(vendor);
        params.add(vendorBatch);
        params.add(plant);
        
        List<InspectionLot> deliveries = performRFC(params,RFC_NAME_GET_INSPECTION_LOTS_FOR_VENDOR_BATCH);
        
		return deliveries ;
	}
}

