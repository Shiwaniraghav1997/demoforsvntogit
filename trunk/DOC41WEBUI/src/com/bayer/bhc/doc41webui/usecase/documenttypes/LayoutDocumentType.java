package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public class LayoutDocumentType implements DownloadDocumentType,
		UploadDocumentType {
	
	private static final String SAP_OBJECT = "MARA";

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
		return "LAYOUT";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.49";
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_LAYOUT_UP";
	}

	@Override
	public CheckForUpdateResult checkForUpload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber,
			String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes)
			throws Doc41BusinessException {
		Map<String, String> additionalAttributes = new HashMap<String, String>();
		additionalAttributes.put(Doc41Constants.ATTRIB_NAME_VENDOR, vendorNumber);
		
		// no RFC check needed

		
		return new CheckForUpdateResult(SAP_OBJECT,null,additionalAttributes);
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_LAYOUT_DOWN";
	}

	@Override
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber, List<String> objectIds,
			Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {
		
		if(objectIds.size()==0){
			errors.rejectValue("objectId","MatNoMissing");
		}
		
		String deliveryCheck = documentUC.checkArtworkLayoutForVendor(vendorNumber,getSapTypeId());
		if(deliveryCheck != null){
			errors.reject(""+deliveryCheck);
		}
		
		Map<String, String> additionalAttributes = new HashMap<String, String>();
		additionalAttributes.put(Doc41Constants.ATTRIB_NAME_VENDOR, vendorNumber);
		
		return new CheckForDownloadResult(additionalAttributes);
	}
	
	@Override
	public int getObjectIdFillLength() {
		return 0;
	}
	
	@Override
	public Set<String> getExcludedAttributes() {
		return Collections.singleton(Doc41Constants.ATTRIB_NAME_VENDOR);
	}

}
