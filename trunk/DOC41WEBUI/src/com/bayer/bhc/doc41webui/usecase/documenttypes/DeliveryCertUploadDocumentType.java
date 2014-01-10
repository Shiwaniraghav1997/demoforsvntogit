package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.QMBatchObject;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;


public class DeliveryCertUploadDocumentType extends
		AbstractDeliveryCertDocumentType implements UploadDocumentType{
	
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
		return "DELCERT";
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_DELCERT_UP";
	}

	@Override
	public CheckForUpdateResult checkForUpload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber,
			String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes)
			throws Doc41BusinessException {

		String country = attributeValues.get(ATTRIB_COUNTRY);
		if(StringTool.isTrimmedEmptyOrNull(country)){
			errors.rejectValue("attributeValues['"+ATTRIB_COUNTRY+"']","CountryMissing");
		}
		
		if(errors.hasErrors()){
			return null;
		}
		
//		//TODO use for attributes
//		String plant = attributeValues.get(ATTRIB_PLANT);
//		String material = attributeValues.get(ATTRIB_MATERIAL);
//		String batch = attributeValues.get(ATTRIB_BATCH);
		
		//TODO use if rfc should be used
		//TODO use for concatenated String
		String plant = viewAttributes.get(VIEW_ATTRIB_PLANT);
		String material = viewAttributes.get(VIEW_ATTRIB_MATERIAL);
		String batch = viewAttributes.get(VIEW_ATTRIB_BATCH);
		List<QMBatchObject> bos = documentUC.getBatchObjectsForSupplier(vendorNumber, plant, material, batch, null);
		if(bos.size()==0){
			errors.reject("BatchObjectNotFoundOrNotForSupplier");
		} else if(bos.size()==0){
			errors.reject("MoreThanOneBatchObject");
		}
		
		
		//TODO SAP OBJECT
		return new CheckForUpdateResult(null,null,null);
	}

}
