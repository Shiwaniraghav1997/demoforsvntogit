package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public abstract class PMSupplierDownloadDocumentType implements DownloadDocumentType{

	//TODO
	private static final Object MATERIAL_NUMBER = null;

	@Override
	public String getPartnerNumberType() {
		return Doc41Constants.PARTNER_TYPE_VENDOR;
	}

	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC, String partnerNumber,
			List<String> objectIds, Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {

		//TODO
		if(objectIds.size()==0){
			errors.rejectValue("objectId","PONumberMissing");
		}
		String objectId = objectIds.get(0);
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