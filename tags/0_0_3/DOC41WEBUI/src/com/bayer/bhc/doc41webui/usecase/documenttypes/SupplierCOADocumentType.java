package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.InspectionLot;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public class SupplierCOADocumentType implements UploadDocumentType {

	public static final String ATTRIB_VENDOR_BATCH = "VENDOR-BATCH";
	public static final String ATTRIB_PLANT = "PLANT";
	public static final String ATTRIB_BATCH = "BATCH";
	public static final String ATTRIB_MATERIAL = "MATERIAL";
	
	public static final String VIEW_ATTRIB_MATERIAL_TEXT = "materialText";

	@Override
	public String getPartnerNumberType() {
		return Doc41Constants.PARTNER_TYPE_VENDOR_MASTER; //SUPPLIER;
	}

	@Override
	public String getTypeConst() {
		return "SUPCOA";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.22";
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_SUPCOA_UP";
	}

	@Override
	public CheckForUpdateResult checkForUpload(Errors errors, DocumentUC documentUC,
			String partnerNumber,
			String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes)
			throws Doc41BusinessException {

		String vendorBatch = attributeValues.get(ATTRIB_VENDOR_BATCH);
		if(StringTool.isTrimmedEmptyOrNull(vendorBatch)){
			errors.reject("VendorBatchMissing");
		}
		String plant = attributeValues.get(ATTRIB_PLANT);
		if(StringTool.isTrimmedEmptyOrNull(plant)){
			errors.reject("PlantMissing");
		}
		
		if(errors.hasErrors()){
			return null;
		}
		
		checkInspectionLot(errors, documentUC, partnerNumber, vendorBatch, plant, objectId);
		
		Map<String, String> additionalAttributes = new HashMap<String, String>();
		additionalAttributes.put(Doc41Constants.ATTRIB_NAME_VENDOR, partnerNumber);

		
		//TODO SAP OBJECT
		return new CheckForUpdateResult(null,null,additionalAttributes);
	}

	private void checkInspectionLot(Errors errors, DocumentUC documentUC,
			String partnerNumber, String vendorBatch, String plant, String objectId) throws Doc41BusinessException {
		List<InspectionLot> deliveryCheck = documentUC.getInspectionLotsForVendorBatch(partnerNumber, vendorBatch, plant);
		for (InspectionLot inspectionLot : deliveryCheck) {
			if(StringTool.equals(inspectionLot.getNumber(), objectId)){
				return;
			}
		}
		errors.reject("inspectionLot does not belong to vendor and batch");
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
