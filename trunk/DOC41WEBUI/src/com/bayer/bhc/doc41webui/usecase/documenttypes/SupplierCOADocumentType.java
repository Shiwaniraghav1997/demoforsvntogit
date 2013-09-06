package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.InspectionLot;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public class SupplierCOADocumentType implements UploadDocumentType {

	public static final String VIEW_ATTRIB_BATCH = "batch";

	@Override
	public boolean isPartnerNumberUsed() {
		return true;
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
	public void checkForUpload(Errors errors, DocumentUC documentUC,
			String partnerNumber,
			String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes)
			throws Doc41BusinessException {

		String batch = viewAttributes.get(VIEW_ATTRIB_BATCH);
		if(StringTool.isTrimmedEmptyOrNull(batch)){
			errors.reject("BatchMissing");
		}
		
		if(errors.hasErrors()){
			return;
		}
		
		checkInspectionLot(errors, documentUC, partnerNumber, batch, objectId);

	}

	private void checkInspectionLot(Errors errors, DocumentUC documentUC,
			String partnerNumber, String batch, String objectId) throws Doc41BusinessException {
		List<InspectionLot> deliveryCheck = documentUC.getInspectionLotsForVendorBatch(partnerNumber, batch);
		for (InspectionLot inspectionLot : deliveryCheck) {
			if(StringTool.equals(inspectionLot.getNumber(), objectId)){
				return;
			}
		}
		errors.reject("inspectionLot does not belong to vendor and batch");
	}

}
