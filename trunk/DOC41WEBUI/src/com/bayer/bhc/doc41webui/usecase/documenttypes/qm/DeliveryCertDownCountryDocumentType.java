package com.bayer.bhc.doc41webui.usecase.documenttypes.qm;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.Doc41ValidationUtils;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
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
	
//	TODO use for attributes
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
		Doc41ValidationUtils.checkMaterialNumber(material, "attributeValues['"+ATTRIB_MATERIAL+"']", errors, false);
		if(!StringTool.isTrimmedEmptyOrNull(material)){
			material = StringTool.minLString(material, Doc41Constants.FIELD_SIZE_MATNR, '0');
			attributeValues.put(ATTRIB_MATERIAL, material);
		}
		if(!StringTool.isTrimmedEmptyOrNull(batch)){
			batch = batch.toUpperCase();
			attributeValues.put(ATTRIB_BATCH, batch);
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
	
	//TODO use for concatenated String
//	@Override
//	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC,
//			String customerNumber, String vendorNumber, String objectId,
//			Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {
//		
//		String countryCode = attributeValues.get(ATTRIB_COUNTRY);
//		if(StringTool.isTrimmedEmptyOrNull(countryCode)){
//			errors.rejectValue("attributeValues['"+ATTRIB_COUNTRY+"']","CountryMissing");
//		}
//		
//		boolean hasCountry = UserInSession.get().hasCountry(countryCode);
//		
//		if(!hasCountry){
//			errors.rejectValue("viewAttributes['"+ATTRIB_COUNTRY+"']","Country does not belong to User");
//		}
//		
//		String material = viewAttributes.get(VIEW_ATTRIB_MATERIAL);
//		String batch = viewAttributes.get(VIEW_ATTRIB_BATCH);
//		if(StringTool.isTrimmedEmptyOrNull(material) && StringTool.isTrimmedEmptyOrNull(batch)){
//			errors.rejectValue("viewAttributes['"+VIEW_ATTRIB_MATERIAL+"']","MaterialAndBatchMissing");
//			errors.rejectValue("viewAttributes['"+VIEW_ATTRIB_BATCH+"']","MaterialAndBatchMissing");
//		}
//		if(!StringTool.isTrimmedEmptyOrNull(material)){
//			material = StringTool.minLString(material, Doc41Constants.FIELD_SIZE_MATNR, '0');
//			viewAttributes.put(VIEW_ATTRIB_MATERIAL, material);
//		}
////		if(!StringTool.isTrimmedEmptyOrNull(batch)){
////			batch = StringTool.minLString(batch, Doc41Constants.FIELD_SIZE_BATCH, '0');
////			viewAttributes.put(VIEW_ATTRIB_BATCH, batch);
////		}
//		
//		
//		List<String> additionalObjectIds = new ArrayList<String>();
//		String addObjectId = getObjectIdSearchString(material,batch,null);
//		additionalObjectIds.add(addObjectId);
//		
//		return new CheckForDownloadResult(null,additionalObjectIds);
//	}

}
