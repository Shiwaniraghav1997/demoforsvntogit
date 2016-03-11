package com.bayer.bhc.doc41webui.integration.sap.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.DeliveryOrShippingUnit;
import com.bayer.bhc.doc41webui.domain.InspectionLot;
import com.bayer.bhc.doc41webui.domain.QMBatchObject;
import com.bayer.bhc.doc41webui.domain.SDReferenceCheckResult;

@Component
public class AuthorizationRFCService extends AbstractSAPJCOService {
	
	private static final String RFC_NAME_CHECK_DELIVERY_FOR_PARTNER						= "CheckDeliveryForPartner";
	private static final String RFC_NAME_GET_DELIVERIES_WITHOUT_DOC						="GetDeliveriesWithoutDocument";
	private static final String RFC_NAME_CHECK_ARTWORK_LAYOUT_FOR_VENDOR				="CheckArtworkLayoutForVendor";
	private static final String RFC_NAME_CHECK_PO_AND_MATERIAL_FOR_VENDOR				="CheckPOAndMaterialForVendor";
    private static final String RFC_NAME_CHECK_MATERIAL_FOR_VENDOR                      ="CheckMaterialForVendor";
	private static final String RFC_NAME_GET_INSPECTION_LOTS_FOR_VENDOR_BATCH			="GetInspectionLotsForVendorBatch";
	private static final String RFC_NAME_GET_BATCH_OBJECTS_FOR_SUPPLIER					="GetBatchObjectsForSupplier";
	private static final String RFC_NAME_GET_BATCH_OBJECTS_FOR_CUSTOMER					="GetBatchObjectsForCustomer";
	

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
	
	public List<DeliveryOrShippingUnit> getOpenDeliveries(String d41id, String carrier, Date fromDate, Date toDate) throws Doc41ServiceException{
		Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"getOpenDeliveries() - d41id="+d41id+", carrier="+carrier+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(carrier);
        params.add(d41id);
        params.add(fromDate);
        params.add(toDate);
        
        List<DeliveryOrShippingUnit> deliveries = performRFC(params,RFC_NAME_GET_DELIVERIES_WITHOUT_DOC);
        
		return deliveries ;
	}
	
    /**
     * Check for the vendor (partner), if already a artwork document exist with the same material number. Current old version only checks, if any ArtWork document existing for vendor.
     * Upload only allowed, if already first document existing (as follow up)
     * @param vendorNumber the vondor/partner number
     * @param materialNumber the material number of their artwork document (new, not yet checked by RFC but prepared TODO)
     * @param sapDocType = sapTypeId
     * @return seems to be null if ok, else an error message (not 100% sure, if also success message may be returned)
	 * @throws Doc41ServiceException
	 */
	public String checkArtworkLayoutForVendor(String vendorNumber, String materialNumber, String sapDocType) throws Doc41ServiceException{
		Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"checkArtworkForVendor() - vendorNumber="+vendorNumber+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(vendorNumber);
        //params.add(materialNumber); // TODO
        params.add(sapDocType);
        
        List<String> returnTexts = performRFC(params,RFC_NAME_CHECK_ARTWORK_LAYOUT_FOR_VENDOR);
        
        String errorMsg=null;
        if(!returnTexts.isEmpty()){
        	errorMsg = returnTexts.get(0);
        }
		return errorMsg ;
	}

	/**
	 * Check PO and Material for Vendor. Now PO number is optional. Check without PO now checks, if there is any PO for Vendor related to specified material.
     * SAP-Developer: IV_PONUMBER ist optional. Es wird nur noch RC = 0 zurück gegebenen wenn es PO Positionen mit nicht gesetztem Endlieferkennzeichen gibt.
	 * @param vendorNumber
	 * @param poNumber optional, no more specified in new version (if specified, auth rfc is expected to only chech for this RFC)
	 * @param materialNumber
	 * @return
	 * @throws Doc41ServiceException
	 */
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
	
	/**
	 * Check if any PO for this vendor exists using the specified material number.
	 * @param vendorNumber
	 * @param materialNumber
	 * @return null if exist, else an error message.
	 * @throws Doc41ServiceException in case of general RFC failure
	 */
    public String checkMaterialForVendor(String vendorNumber, String materialNumber) throws Doc41ServiceException{
        Doc41Log.get().debug(this, UserInSession.getCwid(),
                "vendorNumber="+vendorNumber+", materialNumber="+materialNumber+".");
/**/
        return checkPOAndMaterialForVendor(vendorNumber, null, materialNumber);
/*/
        List<Object> params = new ArrayList<Object>();
        params.add(vendorNumber);
        params.add(materialNumber);

// TODO:
//      List<String> returnTexts = performRFC(params,RFC_NAME_CHECK_MATERIAL_FOR_VENDOR);
        List<String> returnTexts = new ArrayList<String>();
        Doc41Log.get().warning(this, null, "Called emulated RFC, real RFC not yet enabled: " + RFC_NAME_CHECK_MATERIAL_FOR_VENDOR);        
        
        String errorMsg=null;
        if(!returnTexts.isEmpty()){
            errorMsg = returnTexts.get(0);
        }
        return errorMsg ;
/**/
    }
    
	public List<InspectionLot> getInspectionLotsForVendorBatch(String vendor, String vendorBatch, String plant) throws Doc41ServiceException{
		Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"getInspectionLotsForVendorBatch() - vendor="+vendor+", vendorBatch="+vendorBatch+", plant="+plant+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(vendor);
        params.add(vendorBatch);
        params.add(plant);
        
        List<InspectionLot> insplots = performRFC(params,RFC_NAME_GET_INSPECTION_LOTS_FOR_VENDOR_BATCH);
        
		return insplots ;
	}
	
	public List<QMBatchObject> getBatchObjectsForSupplier(String supplier, String plant, String material, String batch, String order) throws Doc41ServiceException{
		Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"getBatchObjectsForSupplier() - supplier="+supplier+", plant="+plant+", material="+material+", batch="+batch+", order="+order+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(supplier);
        params.add(plant);
        params.add(material);
        params.add(batch);
        params.add(order);
        
        List<QMBatchObject> batchObjects = performRFC(params,RFC_NAME_GET_BATCH_OBJECTS_FOR_SUPPLIER);
        
		return batchObjects ;
	}
	
	public List<QMBatchObject> getBatchObjectsForCustomer(String customer, String delivery, String material, String batch, String country) throws Doc41ServiceException{
		Doc41Log.get().debug(this.getClass(), UserInSession.getCwid(),
        		"getBatchObjectsForSupplier() - customer="+customer+", delivery="+delivery+", material="+material+", batch="+batch+", country="+country+".");
       
        List<Object> params = new ArrayList<Object>();
        params.add(customer);
        params.add(delivery);
        params.add(material);
        params.add(batch);
        params.add(country);
        
        List<QMBatchObject> batchObjects = performRFC(params,RFC_NAME_GET_BATCH_OBJECTS_FOR_CUSTOMER);
        
		return batchObjects ;
	}
}

