package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public abstract class SDDocumentType implements UploadDocumentType {
	
	private static final Object SHIPPING_UNIT_NUMBER = "SHIPPINGUNIT";

	@Override
	public boolean isPartnerNumberUsed() {
		return true;
	}

	@Override
	public void checkForUpload(Errors errors, DocumentUC documentUC, MultipartFile file, String fileId,
			String partnerNumber, String objectId, Map<String, String> attributeValues) throws Doc41BusinessException {
		
		boolean isfileEmpty = (file==null||file.getSize()==0);
		if(isfileEmpty && StringTool.isTrimmedEmptyOrNull(fileId)){
			errors.rejectValue("file", "uploadFileMissing", "upload file is missing");
		}
		if(isPartnerNumberUsed()){
			if(StringTool.isTrimmedEmptyOrNull(partnerNumber)){
				errors.rejectValue("partnerNumber","PartnerNumberMissing");
			} else {
				if(!UserInSession.get().hasPartner(partnerNumber)){
					errors.rejectValue("partnerNumber","PartnerNotAssignedToUser");
				}
			}
		}
		if(StringTool.isTrimmedEmptyOrNull(objectId)){
			errors.rejectValue("objectId","DeliveryNumberMissing");
		}
		String shippingUnitNumber = attributeValues.get(SHIPPING_UNIT_NUMBER);
		if(StringTool.isTrimmedEmptyOrNull(shippingUnitNumber)){
			errors.rejectValue("attributeValues['SHIPPINGUNIT']","ShippingUnitNumberMissing");
		}
		if(!isfileEmpty && !StringTool.isTrimmedEmptyOrNull(fileId)){
			errors.rejectValue("file", "FileAndFileId", "both file and fileId filled");
		}
		
		if(errors.hasErrors()){
			return;
		}
		
		boolean deliveryCheck = documentUC.checkDeliveryForPartner(partnerNumber, objectId, shippingUnitNumber);
		if(!deliveryCheck){
			errors.reject("DeliveryNotAllowedForCarrier");
		}
	}
	
}
