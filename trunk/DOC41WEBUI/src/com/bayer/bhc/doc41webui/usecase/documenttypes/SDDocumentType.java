package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.SDReferenceCheckResult;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public abstract class SDDocumentType implements DocumentType {
	
//	private static final String SHIPPING_UNIT_NUMBER = "SHIPPINGUNIT";
	
	private static final String SAP_OBJECT_DELIVERY = "LIKP";
	private static final String SAP_OBJECT_SHIPPING_UNIT = "YTMWF_TRKO";

	@Override
	public boolean isPartnerNumberUsed() {
		return true;
	}

	//implements method from UploadDocumentType
	public String checkForUpload(Errors errors, DocumentUC documentUC,
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
		
		if(deliveryCheck.isDeliveryNumber()){
			return SAP_OBJECT_DELIVERY;
		} else if (deliveryCheck.isShippingUnitNumber()){
			return SAP_OBJECT_SHIPPING_UNIT;
		} else {
			return null;
		}
		
		
	}
	
	//implements method from DownloadDocumentType
	public void checkForDownload(Errors errors, DocumentUC documentUC,
			String partnerNumber, List<String> objectIds,
			Map<String, String> attributeValues) throws Doc41BusinessException {
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
	
}
