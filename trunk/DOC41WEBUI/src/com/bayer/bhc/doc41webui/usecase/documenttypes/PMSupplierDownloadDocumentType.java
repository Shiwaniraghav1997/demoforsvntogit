package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public abstract class PMSupplierDownloadDocumentType implements DownloadDocumentType{

	public static final String VIEW_ATTRIB_PO_NUMBER = "poNumber";

	@Override
	public String getPartnerNumberType() {
		return Doc41Constants.PARTNER_TYPE_VENDOR_MASTER; //VENDOR;
	}

	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC, String partnerNumber,
			List<String> objectIds, Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {

		if(objectIds.size()==0){
			errors.rejectValue("objectId","MatNoMissing");
		}
		String matNumber = objectIds.get(0);
		String poNumber = viewAttributes.get(VIEW_ATTRIB_PO_NUMBER);
		if(StringTool.isTrimmedEmptyOrNull(poNumber)){
			errors.rejectValue("viewAttributes['"+VIEW_ATTRIB_PO_NUMBER+"']","PONumberMissing");
		}
		
		if(errors.hasErrors()){
			return;
		}
		
		String deliveryCheck = documentUC.checkPOAndMaterialForVendor(partnerNumber, poNumber, matNumber);
		if(deliveryCheck != null){
			errors.reject(""+deliveryCheck);
		}

	}

	@Override
	public int getObjectIdFillLength() {
		return 18;
	}

}