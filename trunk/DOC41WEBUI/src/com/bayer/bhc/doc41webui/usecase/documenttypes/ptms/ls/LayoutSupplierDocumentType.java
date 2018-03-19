package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.Doc41ValidationUtils;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForUpdateResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DocumentType;

public abstract class LayoutSupplierDocumentType implements DocumentType{
	
	protected static final String SAP_OBJECT = "MARA";
	
	@Override
	public boolean hasCustomerNumber() {
		return false;
	}
	
	@Override
	public boolean hasVendorNumber() {
		return true;
	}
	
    /**
     * 
     * @param errors
     * @param documentUC
     * @param customerNumber
     * @param vendorNumber
     * @param objectId
     * @param attributeValues
     * @param viewAttributes
     * @return
     * @throws Doc41BusinessException
     */
//  @Override
	public CheckForUpdateResult checkForUpload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber,
			String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes)
			throws Doc41BusinessException {
		Map<String, String> additionalAttributes = new HashMap<String, String>();
		additionalAttributes.put(Doc41Constants.ATTRIB_NAME_VENDOR, vendorNumber);
		
		// no RFC check needed

		
		return new CheckForUpdateResult(SAP_OBJECT,null,additionalAttributes);
	}

	/**
	 * checkForDownload - will be deleted soon, need to be moved to subclasses.
	 * @param errors
	 * @param documentUC
	 * @param customerNumber
	 * @param vendorNumber
	 * @param objectId = materialNumber
	 * @param customVersion 
	 * @param attributeValues - what kind of magic attributes?
	 * @param viewAttributes - what kind of magic attributes?
	 * @return
	 * @throws Doc41BusinessException
	 */
//	@Override
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber, String objectId, String customVersion,
			Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {
		
		Doc41ValidationUtils.checkMaterialNumber(objectId, "objectId", errors, true);
		
		if(checkExistingDocs()){
    		String deliveryCheck = documentUC.checkArtworkLayoutForVendor(vendorNumber, objectId, getSapTypeId());
    		if(deliveryCheck != null){
    			errors.reject(""+deliveryCheck);
    		}
		}
		Map<String, String> additionalAttributes = new HashMap<String, String>();
		additionalAttributes.put(Doc41Constants.ATTRIB_NAME_VENDOR, vendorNumber);
		
		return new CheckForDownloadResult(additionalAttributes,null);
	}
	
	protected abstract boolean checkExistingDocs();

    @Override
	public int getObjectIdFillLength() {
		return Doc41Constants.FIELD_SIZE_MATNR;
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
     * @return the permission code, may be null, if this document is not allowed to download by group membership.
     */
    public String getGroupPermissionDownload() {
        return GROUP_LS_PERM_DOWNL;
    }
    
    /**
     * Get the profile type for download permissions (DOC_SD/QM/LS/PM), see DocumentType.GROUP_* constants.
     * @return
     */
    public String getGroup() {
        return GROUP_LS;
    }
    
}
