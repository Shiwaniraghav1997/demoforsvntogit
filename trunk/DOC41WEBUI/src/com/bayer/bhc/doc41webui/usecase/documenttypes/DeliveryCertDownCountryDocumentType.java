package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;


public class DeliveryCertDownCountryDocumentType extends
		AbstractDeliveryCertDocumentType implements DownloadDocumentType {
	
	@Override
	public boolean hasCustomerNumber() {
		return false;
	}
	
	@Override
	public boolean hasVendorNumber() {
		return false;
	}
	
	@Override
	public String getTypeConst() {
		return "DELCERTDOWNCOUNTRY";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_DELCERT_DOWN_COUNTRY";
	}
	
	@Override
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber, String objectId,
			Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {
		
		String countryCode = attributeValues.get(ATTRIB_COUNTRY);
		if(StringTool.isTrimmedEmptyOrNull(countryCode)){
			errors.rejectValue("attributeValues['"+ATTRIB_COUNTRY+"']","CountryMissing");
		}
		
		boolean hasCountry = UserInSession.get().hasCountry(countryCode);
		
		if(!hasCountry){
			errors.rejectValue("attributeValues['"+ATTRIB_COUNTRY+"']","Country does not belong to User");
		}
		
		String material = attributeValues.get(ATTRIB_MATERIAL);
		String batch = attributeValues.get(ATTRIB_BATCH);
		if(StringTool.isTrimmedEmptyOrNull(material) && StringTool.isTrimmedEmptyOrNull(batch)){
			errors.rejectValue("attributeValues['"+ATTRIB_MATERIAL+"']","MaterialAndBatchMissing");
			errors.rejectValue("attributeValues['"+ATTRIB_BATCH+"']","MaterialAndBatchMissing");
		}
		return new CheckForDownloadResult(null,null);
	}
	
	//TODO use if rfc should be used
//	@Override
//	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC,
//			String customerNumber, String vendorNumber, String objectId,
//			Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {
//		
//		String countryCode = viewAttributes.get(ATTRIB_COUNTRY);
//		if(StringTool.isTrimmedEmptyOrNull(countryCode)){
//			errors.rejectValue("viewAttributes['"+ATTRIB_COUNTRY+"']","CountryMissing");
//		}
//		
//		boolean hasCountry = UserInSession.get().hasCountry(countryCode);
//		
//		if(!hasCountry){
//			errors.rejectValue("viewAttributes['"+ATTRIB_COUNTRY+"']","Country does not belong to User");
//		}
//		
//		String material = viewAttributes.get(ATTRIB_MATERIAL);
//		String batch = viewAttributes.get(ATTRIB_BATCH);
//		if(StringTool.isTrimmedEmptyOrNull(material) && StringTool.isTrimmedEmptyOrNull(batch)){
//			errors.rejectValue("viewAttributes['"+ATTRIB_MATERIAL+"']","MaterialAndBatchMissing");
//			errors.rejectValue("viewAttributes['"+ATTRIB_BATCH+"']","MaterialAndBatchMissing");
//		}
//		
//		List<String> additionalObjectIds = new ArrayList<String>();
//		List<QMBatchObject> bos = documentUC.getBatchObjectsForSupplier(null,null, material, batch, null);or other rfc
//		if(bos.isEmpty()){
//			errors.reject("NoBatchObjectFound");
//		} else {
//			for (QMBatchObject qmBatchObject : bos) {
//				String boObjectId = qmBatchObject.getObjectId();
//				if(!additionalObjectIds.contains(boObjectId)){
//					additionalObjectIds.add(boObjectId);
//				}
//			}
//		}
//		
//		return new CheckForDownloadResult(null,additionalObjectIds);
//	}
}
