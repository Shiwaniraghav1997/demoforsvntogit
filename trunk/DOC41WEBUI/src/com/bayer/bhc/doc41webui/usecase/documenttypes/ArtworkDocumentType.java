package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public class ArtworkDocumentType implements DownloadDocumentType {

	@Override
	public boolean hasCustomerNumber() {
		return false;
	}
	
	@Override
	public boolean hasVendorNumber() {
		return true;
	}

	@Override
	public String getTypeConst() {
		return "ARTWORK";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.48";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_ARTWORK_DOWN";
	}

	@Override
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber, String objectId,
			Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {
		
		if(StringTool.isTrimmedEmptyOrNull(objectId)){
			errors.rejectValue("objectId","MatNoMissing");
		}
		
		String deliveryCheck = documentUC.checkArtworkLayoutForVendor(vendorNumber,getSapTypeId());
		if(deliveryCheck != null){
			errors.reject(""+deliveryCheck);
		}
		Map<String, String> additionalAttributes = new HashMap<String, String>();
		additionalAttributes.put(Doc41Constants.ATTRIB_NAME_VENDOR, vendorNumber);
		
		return new CheckForDownloadResult(additionalAttributes,null);
	}
	
	@Override
	public int getObjectIdFillLength() {
		return Doc41Constants.FIELD_SIZE_MATNR;
	}

	@Override
	public Set<String> getExcludedAttributes() {
		return Collections.singleton(Doc41Constants.ATTRIB_NAME_VENDOR);
	}

}
