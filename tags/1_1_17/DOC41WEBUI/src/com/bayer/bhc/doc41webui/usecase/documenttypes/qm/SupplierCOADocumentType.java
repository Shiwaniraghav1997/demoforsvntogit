package com.bayer.bhc.doc41webui.usecase.documenttypes.qm;

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
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForUpdateResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;
import com.bayer.ecim.foundation.basic.StringTool;

public class SupplierCOADocumentType implements UploadDocumentType {

	public static final String ATTRIB_VENDOR_BATCH = "VENDORBATCH";
	public static final String ATTRIB_PLANT = Doc41Constants.ATTRIB_NAME_PLANT;
	public static final String ATTRIB_BATCH = "BATCH";
	public static final String ATTRIB_MATERIAL = "MATERIAL";
	
	public static final String VIEW_ATTRIB_MATERIAL_TEXT = "materialText";

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
			String customerNumber, String vendorNumber,
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
		
		checkInspectionLot(errors, documentUC, vendorNumber, vendorBatch, plant, objectId);
		
		Map<String, String> additionalAttributes = new HashMap<String, String>();
		additionalAttributes.put(Doc41Constants.ATTRIB_NAME_VENDOR, vendorNumber);

		
		//TODO SAP OBJECT
		return new CheckForUpdateResult(null,null,additionalAttributes);
	}

	private void checkInspectionLot(Errors errors, DocumentUC documentUC,
			String vendorNumber, String vendorBatch, String plant, String objectId) throws Doc41BusinessException {
		List<InspectionLot> deliveryCheck = documentUC.getInspectionLotsForVendorBatch(vendorNumber, vendorBatch, plant);
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

    /**
     * Flag to determine, if document uses DIRS store.
     * @return true, if using DIRS. 
     */
    @Override
    public boolean isDirs() {
        return false;
    }

    /**
     * Flag to determine, if document uses KGS store.
     * @return true, if using KGS. 
     */
    @Override
    public boolean isKgs() {
        return true;
    }
    
    /**
     * Set the profile type for download permissions (DOC_SD/QM/LS/PM), see DocumentType.GROUP_* constants.
     * @param pPermType
     */
    public void setDownloadPermissionType(String pPermType) {
        cDownloadPermType = pPermType;
    }

    /**
     * Get the profile type for download permissions (DOC_SD/QM/LS/PM), see DocumentType.GROUP_* constants.
     * @return
     */
    public String getDownloadPermissionType() {
        return cDownloadPermType;
    }
    
    /** Type of the Profile for Download Permissions. */
    String cDownloadPermType = null; 

    /**
     * The permissions allowing download for all documents of the same document group (= permission type).
     * Take care, permission type is DOC_xx, corresponding permission is DOC_GLO_xx...
     * (currently set to null for QM)
     * @return the permission code, may be null, if this document is not allowed to download by group membership.
     */
    public String getGroupPermissionDownload() {
        return GROUP_QM_PERM_DOWNL;
    }
    
    /**
     * Get the profile type for download permissions (DOC_SD/QM/LS/PM), see DocumentType.GROUP_* constants.
     * @return
     */
    public String getGroup() {
        return GROUP_QM;
    }
    
}
