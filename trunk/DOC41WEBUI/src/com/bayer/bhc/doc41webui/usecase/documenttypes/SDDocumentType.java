package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.SDReferenceCheckResult;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public abstract class SDDocumentType implements DocumentType {
	
//	private static final String SHIPPING_UNIT_NUMBER = "SHIPPINGUNIT";
	
	private static final String SAP_OBJECT_DELIVERY = "LIKP";
	private static final String SAP_OBJECT_SHIPPING_UNIT = "YTMWF_TRKO";

	@Override
	public String getPartnerNumberType() {
		return Doc41Constants.PARTNER_TYPE_VENDOR_MASTER; //CARRIER;
	}

	//implements method from UploadDocumentType
	public CheckForUpdateResult checkForUpload(Errors errors, DocumentUC documentUC,
			String partnerNumber, String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes) throws Doc41BusinessException {
//		if(true)return SAP_OBJECT_DELIVERY;
		
//		String shippingUnitNumber = attributeValues.get(SHIPPING_UNIT_NUMBER);
//		if(StringTool.isTrimmedEmptyOrNull(shippingUnitNumber)){
//			errors.rejectValue("attributeValues['"+SHIPPING_UNIT_NUMBER+"']","ShippingUnitNumberMissing");
//		}
		
		if(errors.hasErrors()){
			return null;
		}
		
		SDReferenceCheckResult deliveryCheck = documentUC.checkDeliveryForPartner(partnerNumber, objectId);
		if(!deliveryCheck.isOk()){
			errors.rejectValue("objectId",""+deliveryCheck.getError());
		} 
		
		String vkOrg = deliveryCheck.getVkOrg();
		
		if(deliveryCheck.isDeliveryNumber()){
			return new CheckForUpdateResult(SAP_OBJECT_DELIVERY,vkOrg);
		} else if (deliveryCheck.isShippingUnitNumber()){
			return new CheckForUpdateResult(getSapObjectShippingUnit(),vkOrg);
		} else {
			return new CheckForUpdateResult(null,vkOrg);
		}
		
		
	}

	protected String getSapObjectShippingUnit() {
		return SAP_OBJECT_SHIPPING_UNIT;
	}
	
	//implements method from DownloadDocumentType
	public void checkForDownload(Errors errors, DocumentUC documentUC,
			String partnerNumber, List<String> objectIds,
			Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {
//		if(true)return;
		
		if(objectIds.size()==0){
			throw new Doc41BusinessException("no objectId");
		} else if(objectIds.size()>1){
			throw new Doc41BusinessException("more than one objectId");
		}
		
		SDReferenceCheckResult deliveryCheck = documentUC.checkDeliveryForPartner(partnerNumber, objectIds.get(0));
		if(!deliveryCheck.isOk()){
			errors.rejectValue("objectId",""+deliveryCheck.getError());
		} 
	}
	
	public void checkForDirectDownload(DocumentUC documentUC, String objectId)throws Doc41BusinessException{
		if(StringTool.isTrimmedEmptyOrNull(objectId)){
			throw new Doc41BusinessException("objectId missing");
		}
		SDReferenceCheckResult deliveryCheck = documentUC.checkDeliveryForPartner(null, objectId);
		if(!deliveryCheck.isOk(true)){
			throw new Doc41BusinessException(""+deliveryCheck.getError());
		}
	}
	
}
