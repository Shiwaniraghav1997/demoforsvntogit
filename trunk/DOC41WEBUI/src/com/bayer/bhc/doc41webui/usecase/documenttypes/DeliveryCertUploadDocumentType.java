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
	public boolean isPartnerNumberUsed() {
		return true;
	}
	
	@Override
	public String getTypeConst() {
		return "DELCERTUP";
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_DELCERT_UP";
	}

	@Override
	public String checkForUpload(Errors errors, DocumentUC documentUC,
			String partnerNumber,
			String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes)
			throws Doc41BusinessException {

		String country = attributeValues.get(VIEW_ATTRIB_COUNTRY);
		if(StringTool.isTrimmedEmptyOrNull(country)){
			errors.rejectValue("attributeValues['"+VIEW_ATTRIB_COUNTRY+"']","CountryMissing");
		}
		
		if(errors.hasErrors()){
			return null;
		}
		
		String plant = attributeValues.get(VIEW_ATTRIB_PLANT);
		String material = attributeValues.get(VIEW_ATTRIB_MATERIAL);
		String batch = attributeValues.get(VIEW_ATTRIB_BATCH);
		List<QMBatchObject> bos = documentUC.getBatchObjectsForSupplier(partnerNumber, plant, material, batch, null);
		if(bos.size()==0){
			errors.reject("BatchObjectNotFoundOrNotForSupplier");
		} else if(bos.size()==0){
			errors.reject("MoreThanOneBatchObject");
		}
		
		
		//TODO SAP OBJECT
		return null;
	}

}
