package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public abstract class SDUploadDocumentType implements UploadDocumentType {
	
//	private static final Object SHIPPING_UNIT_NUMBER = "SHIPPINGUNIT";

	@Override
	public boolean isPartnerNumberUsed() {
		return true;
	}

	@Override
	public void checkForUpload(Errors errors, DocumentUC documentUC,
			String partnerNumber, String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes) throws Doc41BusinessException {
		
//		String shippingUnitNumber = attributeValues.get(SHIPPING_UNIT_NUMBER);
//		if(StringTool.isTrimmedEmptyOrNull(shippingUnitNumber)){
//			errors.rejectValue("attributeValues['"+SHIPPING_UNIT_NUMBER+"']","ShippingUnitNumberMissing");
//		}
		
		if(errors.hasErrors()){
			return;
		}
		
//		String deliveryCheck = documentUC.checkDeliveryForPartner(partnerNumber, objectId, shippingUnitNumber);
//		if(deliveryCheck != null){
//			errors.reject(""+deliveryCheck);
//		}
	}
	
}
