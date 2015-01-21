package com.bayer.bhc.doc41webui.usecase.documenttypes.sd;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.SDReferenceCheckResult;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForUpdateResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DocumentType;
import com.bayer.ecim.foundation.basic.StringTool;

public abstract class SDDocumentType implements DocumentType {
	
	protected static final String SAP_OBJECT_DELIVERY = "LIKP";
	protected static final String SAP_OBJECT_SHIPPING_UNIT = "YTMSA";

	@Override
	public boolean hasCustomerNumber() {
		return false;
	}
	
	@Override
	public boolean hasVendorNumber() {
		return true;
	}
	
	//implements method from UploadDocumentType
	public CheckForUpdateResult checkForUpload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber, String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes) throws Doc41BusinessException {
		
		if(errors.hasErrors()){
			return null;
		}
		
		SDReferenceCheckResult deliveryCheck = documentUC.checkDeliveryForPartner(vendorNumber, objectId);
		if(!deliveryCheck.isOk()){
			errors.rejectValue("objectId",""+deliveryCheck.getError());
		} 
		
		String vkOrg = deliveryCheck.getVkOrg();
		
		if(deliveryCheck.isDeliveryNumber()){
			return new CheckForUpdateResult(SAP_OBJECT_DELIVERY,vkOrg,null);
		} else if (deliveryCheck.isShippingUnitNumber()){
			return new CheckForUpdateResult(SAP_OBJECT_SHIPPING_UNIT,vkOrg,null);
		} else {
			return new CheckForUpdateResult(null,vkOrg,null);
		}
		
		
	}

	//implements method from DownloadDocumentType
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber, String objectId,
			Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {
		if(StringTool.isTrimmedEmptyOrNull(objectId)){
			errors.rejectValue("objectId","MandatoryField");
		} else {
			SDReferenceCheckResult deliveryCheck = documentUC.checkDeliveryForPartner(vendorNumber, objectId);
			if(!deliveryCheck.isOk()){
				errors.rejectValue("objectId",""+deliveryCheck.getError());
			} 
		}
		return new CheckForDownloadResult(null,null);
	}
	
	public CheckForDownloadResult checkForDirectDownload(DocumentUC documentUC, String objectId)throws Doc41BusinessException{
		if(StringTool.isTrimmedEmptyOrNull(objectId)){
			throw new Doc41BusinessException("objectId missing");
		}
		SDReferenceCheckResult deliveryCheck = documentUC.checkDeliveryForPartner(null, objectId);
		if(!deliveryCheck.isOk(true)){
			throw new Doc41BusinessException(""+deliveryCheck.getError());
		}
		return new CheckForDownloadResult(null,null);
	}
	
	@Override
	public int getObjectIdFillLength() {
		return Doc41Constants.FIELD_SIZE_SD_REF_NO;
	}
	
	@Override
	public Set<String> getExcludedAttributes() {
		return Collections.emptySet();
	}
}
