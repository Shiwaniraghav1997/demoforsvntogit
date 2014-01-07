package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public abstract class PMSupplierDownloadDocumentType implements DownloadDocumentType{

	public static final String VIEW_ATTRIB_PO_NUMBER = "poNumber";

	@Override
	public boolean hasCustomerNumber() {
		return false;
	}
	
	@Override
	public boolean hasVendorNumber() {
		return true;
	}

	@Override
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC, String customerNumber, String vendorNumber,
			String objectId, Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {

		String matNumber = null;
		if(StringTool.isTrimmedEmptyOrNull(objectId)){
			errors.rejectValue("objectId","MatNoMissing");
		} else {
			matNumber = objectId;
		}
		String poNumber = viewAttributes.get(VIEW_ATTRIB_PO_NUMBER);
		if(StringTool.isTrimmedEmptyOrNull(poNumber)){
			errors.rejectValue("viewAttributes['"+VIEW_ATTRIB_PO_NUMBER+"']","PONumberMissing");
		}
		
		if(!errors.hasErrors()){
			String deliveryCheck = documentUC.checkPOAndMaterialForVendor(vendorNumber, poNumber, matNumber);
			if(deliveryCheck != null){
				errors.reject(""+deliveryCheck);
			}
		}
		return new CheckForDownloadResult(null,null);

	}

	@Override
	public int getObjectIdFillLength() {
		return Doc41Constants.FIELD_SIZE_MATNR;
	}
	
	@Override
	public Set<String> getExcludedAttributes() {
		return Collections.emptySet();
	}

}