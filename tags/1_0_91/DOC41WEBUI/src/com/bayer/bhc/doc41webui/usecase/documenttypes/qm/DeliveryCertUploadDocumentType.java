package com.bayer.bhc.doc41webui.usecase.documenttypes.qm;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.QMBatchObject;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForUpdateResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;
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
		
		String plant = attributeValues.get(ATTRIB_PLANT);
		String material = attributeValues.get(ATTRIB_MATERIAL);
		String batch = attributeValues.get(ATTRIB_BATCH);
		
		List<QMBatchObject> bos = documentUC.getBatchObjectsForSupplier(vendorNumber, plant, material, batch, null);
		if(bos.isEmpty()){
			errors.reject("BatchObjectNotFoundOrNotForSupplier");
		} else if(bos.isEmpty()){
			errors.reject("MoreThanOneBatchObject");
		}
		
		return new CheckForUpdateResult(SAP_OBJECT_BATCH_OBJ,null,null);
	}

}
