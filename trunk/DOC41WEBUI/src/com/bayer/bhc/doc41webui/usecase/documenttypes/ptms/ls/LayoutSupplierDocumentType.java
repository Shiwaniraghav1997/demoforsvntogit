package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.Doc41ValidationUtils;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForUpdateResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DocumentType;

public abstract class LayoutSupplierDocumentType implements DocumentType{
	
	protected static final String SAP_OBJECT = "MARA";
	
	@Override
	public boolean hasCustomerNumber() {
		return false;
	}
	
	@Override
	public boolean hasVendorNumber() {
		return true;
	}
	
//	@Override
	public CheckForUpdateResult checkForUpload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber,
			String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes)
			throws Doc41BusinessException {
		Map<String, String> additionalAttributes = new HashMap<String, String>();
		additionalAttributes.put(Doc41Constants.ATTRIB_NAME_VENDOR, vendorNumber);
		
		// no RFC check needed

		
		return new CheckForUpdateResult(SAP_OBJECT,null,additionalAttributes);
	}

	/**
	 * checkForDownload - will be deleted soon, need to be moved to subclasses.
	 * @param errors
	 * @param documentUC
	 * @param customerNumber
	 * @param vendorNumber
	 * @param objectId = materialNumber
	 * @param attributeValues - what kind of magic attributes?
	 * @param viewAttributes - what kind of magic attributes?
	 * @return
	 * @throws Doc41BusinessException
	 */
//	@Override
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber, String objectId,
			Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {
		
		Doc41ValidationUtils.checkMaterialNumber(objectId, "objectId", errors, true);
		
		if(checkExistingDocs()){
    		String deliveryCheck = documentUC.checkArtworkLayoutForVendor(vendorNumber, objectId, getSapTypeId());
    		if(deliveryCheck != null){
    			errors.reject(""+deliveryCheck);
    		}
		}
		Map<String, String> additionalAttributes = new HashMap<String, String>();
		additionalAttributes.put(Doc41Constants.ATTRIB_NAME_VENDOR, vendorNumber);
		
		return new CheckForDownloadResult(additionalAttributes,null);
	}
	
	protected abstract boolean checkExistingDocs();

    @Override
	public int getObjectIdFillLength() {
		return Doc41Constants.FIELD_SIZE_MATNR;
	}
	
	@Override
	public Set<String> getExcludedAttributes() {
		return Collections.singleton(Doc41Constants.ATTRIB_NAME_VENDOR);
	}
}
