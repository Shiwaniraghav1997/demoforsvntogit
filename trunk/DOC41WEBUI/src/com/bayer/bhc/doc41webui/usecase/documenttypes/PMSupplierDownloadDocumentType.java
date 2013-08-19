package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public abstract class PMSupplierDownloadDocumentType implements DownloadDocumentType{

	//TODO
	private static final Object MATERIAL_NUMBER = null;

	@Override
	public boolean isPartnerNumberUsed() {
		return true;
	}

	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC, String partnerNumber,
			String objectId, Map<String, String> attributeValues) throws Doc41BusinessException {
		if(StringTool.isTrimmedEmptyOrNull(partnerNumber)){
			errors.rejectValue("partnerNumber","PartnerNumberMissing");
		} else {
			if(!UserInSession.get().hasPartner(partnerNumber)){
				errors.rejectValue("partnerNumber","PartnerNotAssignedToUser");
			}
		}
		//TODO
		if(StringTool.isTrimmedEmptyOrNull(objectId)){
			errors.rejectValue("objectId","PONumberMissing");
		}
		String matNumber = attributeValues.get(MATERIAL_NUMBER);
		if(StringTool.isTrimmedEmptyOrNull(matNumber)){
			errors.rejectValue("attributeValues['"+MATERIAL_NUMBER+"']","MaterialNumberMissing");
		}
		
		if(errors.hasErrors()){
			return;
		}
		
		String deliveryCheck = documentUC.checkPOAndMaterialForVendor(partnerNumber, objectId, matNumber);
		if(deliveryCheck != null){
			errors.reject(""+deliveryCheck);
		}

	}


}