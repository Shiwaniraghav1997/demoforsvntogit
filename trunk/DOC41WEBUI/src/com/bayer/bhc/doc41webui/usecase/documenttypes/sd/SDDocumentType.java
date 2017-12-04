package com.bayer.bhc.doc41webui.usecase.documenttypes.sd;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.SDReferenceCheckResult;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForUpdateResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DocumentType;
import com.bayer.ecim.foundation.basic.StringTool;

public abstract class SDDocumentType implements DocumentType {
	
	protected static final String SAP_OBJECT_DELIVERY = "LIKP";
	protected static final String SAP_OBJECT_SHIPPING_UNIT = "YTMSA";

	// global:  
    public static final String VIEW_ATTRIB_DOC_TYPE = "docType";

	@Override
	public boolean hasCustomerNumber() {
		return false;
	}
	
	@Override
	public boolean hasVendorNumber() {
		return true;
	}
	
	/**
	 * implements method from UploadDocumentType
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
	public CheckForUpdateResult checkForUpload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber, String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes) throws Doc41BusinessException {
		
		if(errors.hasErrors()){
			return null;
		}
		
		SDReferenceCheckResult deliveryCheck = documentUC.checkDeliveryForPartner(vendorNumber, objectId);
		if(!deliveryCheck.isOk()){
		    Doc41Log.get().warning(this, null, "Delivery: " + objectId + " not allowed for Partner: " + vendorNumber + ", Upload " + getTypeConst() + "/" + getSapTypeId() + " denied!");
			errors.rejectValue("objectId",""+deliveryCheck.getError());
		} 
		
		String vkOrg = deliveryCheck.getVkOrg();
		
		if(deliveryCheck.isDeliveryNumber()){
			return new CheckForUpdateResult(SAP_OBJECT_DELIVERY,vkOrg,null);
		} else if (deliveryCheck.isShippingUnitNumber()){
			return new CheckForUpdateResult(SAP_OBJECT_SHIPPING_UNIT,vkOrg,null);
		} else {
			return new CheckForUpdateResult(null,vkOrg,null);
		}
		
		
	}

	/**
	 * implements method from DownloadDocumentType
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
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC,
			String customerNumber, String vendorNumber, String objectId, String customVersion,
			Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {
		if(StringTool.isTrimmedEmptyOrNull(objectId)){
			errors.rejectValue("objectId","MandatoryField");
		} else {
			SDReferenceCheckResult deliveryCheck = documentUC.checkDeliveryForPartner(vendorNumber, objectId);
			if(!deliveryCheck.isOk()){
	            Doc41Log.get().warning(this, null, "Delivery: " + objectId + " not allowed for Partner: " + vendorNumber + ", Download " + getTypeConst() + "/" + getSapTypeId() + " denied!");
				errors.rejectValue("objectId",""+deliveryCheck.getError());
			} 
		}
		return new CheckForDownloadResult(null,null);
	}
	
	public CheckForDownloadResult checkForDirectDownload(DocumentUC documentUC, String objectId)throws Doc41BusinessException{
		if(StringTool.isTrimmedEmptyOrNull(objectId)){
			throw new Doc41BusinessException("objectId missing");
		}
		SDReferenceCheckResult deliveryCheck = documentUC.checkDeliveryForPartner(null, objectId);
		if(!deliveryCheck.isOk(true)){
            Doc41Log.get().warning(this, null, "Delivery: " + objectId + " not allowed for Partner: null, DirectDownload " + getTypeConst() + "/" + getSapTypeId() + " aborted!");
			throw new Doc41BusinessException(""+deliveryCheck.getError());
		}
		return new CheckForDownloadResult(null,null);
	}
	
	@Override
	public int getObjectIdFillLength() {
		return Doc41Constants.FIELD_SIZE_SD_REF_NO;
	}
	
	@Override
	public Set<String> getExcludedAttributes() {
		return Collections.emptySet();
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
        return GROUP_SD_PERM_DOWNL;
    }
    
    /**
     * Get the profile type for download permissions (DOC_SD/QM/LS/PM), see DocumentType.GROUP_* constants.
     * @return
     */
    public String getGroup() {
        return GROUP_SD;
    }
    
}
