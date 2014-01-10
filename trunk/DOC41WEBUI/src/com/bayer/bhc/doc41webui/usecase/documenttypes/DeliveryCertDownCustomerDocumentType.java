package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.QMBatchObject;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;


public class DeliveryCertDownCustomerDocumentType extends
		AbstractDeliveryCertDocumentType implements DownloadDocumentType {
	
	@Override
	public boolean hasCustomerNumber() {
		return true;
	}
	
	@Override
	public boolean hasVendorNumber() {
		return false;
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
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber, String objectId,
			Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {
		
		String countryCode = attributeValues.get(ATTRIB_COUNTRY);
		if(StringTool.isTrimmedEmptyOrNull(countryCode)){
			errors.rejectValue("attributeValues['"+ATTRIB_COUNTRY+"']","CountryMissing");
		}
		
		String delivery=viewAttributes.get(VIEW_ATTRIB_DELIVERY_NUMBER);
		if(StringTool.isTrimmedEmptyOrNull(delivery)){
			errors.rejectValue("viewAttributes['"+VIEW_ATTRIB_DELIVERY_NUMBER+"']","DeliveryMissing");
		} else {
			delivery = StringTool.minLString(delivery, Doc41Constants.FIELD_SIZE_SD_REF_NO, '0');
			viewAttributes.put(VIEW_ATTRIB_DELIVERY_NUMBER, delivery);
		}
		
//		//TODO use for attributes
//		String material = attributeValues.get(ATTRIB_MATERIAL);
//		String batch = attributeValues.get(ATTRIB_BATCH);
//		if(StringTool.isTrimmedEmptyOrNull(material) && StringTool.isTrimmedEmptyOrNull(batch)){
//			errors.rejectValue("attributeValues['"+ATTRIB_MATERIAL+"']","MaterialAndBatchMissing");
//			errors.rejectValue("attributeValues['"+ATTRIB_BATCH+"']","MaterialAndBatchMissing");
//		}
		
		//TODO use if rfc should be used
		//TODO use for concatenated String
		String material = viewAttributes.get(VIEW_ATTRIB_MATERIAL);
		String batch = viewAttributes.get(VIEW_ATTRIB_BATCH);
		if(StringTool.isTrimmedEmptyOrNull(material) && StringTool.isTrimmedEmptyOrNull(batch)){
			errors.rejectValue("viewAttributes['"+VIEW_ATTRIB_MATERIAL+"']","MaterialAndBatchMissing");
			errors.rejectValue("viewAttributes['"+VIEW_ATTRIB_BATCH+"']","MaterialAndBatchMissing");
		}
		if(!StringTool.isTrimmedEmptyOrNull(material)){
			material = StringTool.minLString(material, Doc41Constants.FIELD_SIZE_MATNR, '0');
			viewAttributes.put(VIEW_ATTRIB_MATERIAL, material);
		}
		if(!StringTool.isTrimmedEmptyOrNull(batch)){
			batch = StringTool.minLString(batch, Doc41Constants.FIELD_SIZE_BATCH, '0');
			viewAttributes.put(VIEW_ATTRIB_BATCH, batch);
		}
		
		List<String> additionalObjectIds = new ArrayList<String>();
		if(!errors.hasErrors()){
			List<QMBatchObject> bos = documentUC.getBatchObjectsForCustomer(customerNumber, delivery, material, batch, countryCode);
			if(bos.isEmpty()){
				errors.reject("BatchObjectNotFound");
			}

			for (QMBatchObject qmBatchObject : bos) {
				String boObjectId = qmBatchObject.getObjectId();
				if(!additionalObjectIds.contains(boObjectId)){
					additionalObjectIds.add(boObjectId);
				}
			}
		}
		return new CheckForDownloadResult(null,additionalObjectIds);
		
	}
}
