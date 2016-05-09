package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.Doc41ValidationUtils;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;

public abstract class PMSupplierDownloadDocumentType implements DownloadDocumentType{

// kgs-mode:
	public static final String VIEW_ATTRIB_PO_NUMBER = "poNumber";

// global:	
    public static final String VIEW_ATTRIB_DOC_TYPE = "docType";
	
// dirs-mode only:
    public static final String VIEW_ATTRIB_FILENAME = "FILENAME";

    
	@Override
	public boolean hasCustomerNumber() {
		return false;
	}
	
	@Override
	public boolean hasVendorNumber() {
		return true;
	}

	@Override
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC, String customerNumber, String vendorNumber,
			String objectId, Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {

		Doc41ValidationUtils.checkMaterialNumber(objectId, "objectId", errors, true);
		
// dirs-mode
//		String fileName = viewAttributes.get(VIEW_ATTRIB_FILENAME);
//      if(!StringTool.isTrimmedEmptyOrNull(fileName)){
//          attributeValues.put(VIEW_ATTRIB_FILENAME, fileName);
//      }

// check-PO-mode:
//		if(StringTool.isTrimmedEmptyOrNull(poNumber)){
//			errors.rejectValue("viewAttributes['"+VIEW_ATTRIB_PO_NUMBER+"']","PONumberMissing");
//		}
		
		if(!errors.hasErrors()){
// check-PO-mode:
//			String deliveryCheck = documentUC.checkPOAndMaterialForVendor(vendorNumber, poNumber, objectId);
            String deliveryCheck = documentUC.checkMaterialForVendor(vendorNumber, objectId);
			if(deliveryCheck != null){
				errors.reject(""+deliveryCheck);
			}
		}
		return new CheckForDownloadResult(null,null);

	}

	@Override
	public int getObjectIdFillLength() {
		return Doc41Constants.FIELD_SIZE_MATNR;
	}
	
	@Override
	public Set<String> getExcludedAttributes() {
		return Collections.emptySet();
	}

// kgs-mode / dirs-mode:
	
	
	/**
     * Flag to determine, if document uses DIRS store.
     * @return true, if using DIRS. 
     */
	@Override
    public boolean isDirs() {
        return false; //true;
    }

    /**
     * Flag to determine, if document uses KGS store.
     * @return true, if using KGS. 
     */
    @Override
    public boolean isKgs() {
        return true; //false;
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
     * @return the permission code, may be null, if this document is not allowed to download by group membership.
     */
    @Override
    public String getGroupPermissionDownload() {
        return GROUP_PM_PERM_DOWNL;
    }
    
    /**
     * Get the profile type for download permissions (DOC_SD/QM/LS/PM), see DocumentType.GROUP_* constants.
     * @return
     */
    public String getGroup() {
        return GROUP_PM;
    }
    
}