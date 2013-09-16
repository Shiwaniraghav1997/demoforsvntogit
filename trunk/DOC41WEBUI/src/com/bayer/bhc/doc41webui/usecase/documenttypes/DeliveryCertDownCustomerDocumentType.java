package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.QMBatchObject;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;


public class DeliveryCertDownCustomerDocumentType extends
		AbstractDeliveryCertDocumentType implements DownloadDocumentType {
	
	@Override
	public boolean isPartnerNumberUsed() {
		return true;
	}
	
	@Override
	public String getTypeConst() {
		return "DELCERTDOWNCUSTOMER";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_DELCERT_DOWN_CUSTOMER";
	}
	
	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC,
			String partnerNumber, List<String> objectIds,
			Map<String, String> attributeValues) throws Doc41BusinessException {
		
		String countryCode = attributeValues.get(VIEW_ATTRIB_COUNTRY);
		if(StringTool.isTrimmedEmptyOrNull(countryCode)){
			errors.rejectValue("attributeValues['"+VIEW_ATTRIB_COUNTRY+"']","CountryMissing");
		}
		
		//TODO delivery mandatory
		String delivery="";
		if(StringTool.isTrimmedEmptyOrNull(delivery)){
//			errors.rejectValue("attributeValues['"+VIEW_ATTRIB_DELIVERY+"']","DeliveryMissing");
		}
		
		//TODO getBatchObjectsForCustomer
		String material = attributeValues.get(VIEW_ATTRIB_MATERIAL);
		String batch = attributeValues.get(VIEW_ATTRIB_BATCH);
		List<QMBatchObject> bos = documentUC.getBatchObjectsForCustomer(partnerNumber, delivery, material, batch, countryCode);
		
		for (QMBatchObject qmBatchObject : bos) {
			String objectId = qmBatchObject.getObjectId();
			if(!objectIds.contains(objectId)){
				objectIds.add(objectId);
			}
		}
		
	}
}
