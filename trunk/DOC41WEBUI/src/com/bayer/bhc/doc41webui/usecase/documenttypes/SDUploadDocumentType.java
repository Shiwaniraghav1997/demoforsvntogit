package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.SDReferenceCheckResult;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public abstract class SDUploadDocumentType implements UploadDocumentType {
	
//	private static final String SHIPPING_UNIT_NUMBER = "SHIPPINGUNIT";
	
	private static final String SAP_OBJECT_DELIVERY = "LIKP";
	private static final String SAP_OBJECT_SHIPPING_UNIT = "YTMWF_TRKO";

	@Override
	public boolean isPartnerNumberUsed() {
		return true;
	}

	@Override
	public String checkForUpload(Errors errors, DocumentUC documentUC,
			String partnerNumber, String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes) throws Doc41BusinessException {
		
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
		
		if(deliveryCheck.isDeliveryNumber()){
			return SAP_OBJECT_DELIVERY;
		} else if (deliveryCheck.isShippingUnitNumber()){
			return SAP_OBJECT_SHIPPING_UNIT;
		} else {
			return null;
		}
		
		
	}
	
}
