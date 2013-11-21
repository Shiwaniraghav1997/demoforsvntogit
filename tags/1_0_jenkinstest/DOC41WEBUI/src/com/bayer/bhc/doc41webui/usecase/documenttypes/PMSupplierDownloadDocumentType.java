package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Collections;
import java.util.List;
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
	public String getPartnerNumberType() {
		return Doc41Constants.PARTNER_TYPE_VENDOR_MASTER; //VENDOR;
	}

	@Override
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC, String partnerNumber,
			List<String> objectIds, Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {

		String matNumber = null;
		if(objectIds.size()==0){
			errors.rejectValue("objectId","MatNoMissing");
		} else {
			matNumber = objectIds.get(0);
		}
		String poNumber = viewAttributes.get(VIEW_ATTRIB_PO_NUMBER);
		if(StringTool.isTrimmedEmptyOrNull(poNumber)){
			errors.rejectValue("viewAttributes['"+VIEW_ATTRIB_PO_NUMBER+"']","PONumberMissing");
		}
		
		if(!errors.hasErrors()){
			String deliveryCheck = documentUC.checkPOAndMaterialForVendor(partnerNumber, poNumber, matNumber);
			if(deliveryCheck != null){
				errors.reject(""+deliveryCheck);
			}
		}
		return new CheckForDownloadResult(null);

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