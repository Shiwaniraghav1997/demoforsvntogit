package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;




public class ArtworkForPMSupplierDocumentType extends PMSupplierDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "ARTWORK_PM";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.48";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_ARTWORK_DOWN_PM";
	}
	
	
	@Override
	public Set<String> getExcludedAttributes() {
		return Collections.singleton(Doc41Constants.ATTRIB_NAME_VENDOR);
	}
	
	@Override
	public CheckForDownloadResult checkForDownload(Errors errors,
			DocumentUC documentUC, String customerNumber, String vendorNumber,
			String objectId, Map<String, String> attributeValues,
			Map<String, String> viewAttributes) throws Doc41BusinessException {
		CheckForDownloadResult result = super.checkForDownload(errors, documentUC, customerNumber, vendorNumber,
						objectId, attributeValues, viewAttributes);
		
		Map<String, String> additionalAttributes = new HashMap<String, String>();
		additionalAttributes.put(Doc41Constants.ATTRIB_NAME_VENDOR, vendorNumber);
		result.setAdditionalAttributes(additionalAttributes);
		
		return result;
	}
	//TODO downloadcheck wie pm
}
