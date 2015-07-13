package com.bayer.bhc.doc41webui.usecase.documenttypes.qm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.Doc41ValidationUtils;
import com.bayer.bhc.doc41webui.domain.QMBatchObject;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
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
		
		List<String> additionalObjectIds = new ArrayList<String>();
		if(!errors.hasErrors()){
			List<QMBatchObject> bos = documentUC.getBatchObjectsForCustomer(customerNumber, delivery, material, batch, countryCode);
			if(bos.isEmpty()){
				errors.reject("NoBatchObjectFound");
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
