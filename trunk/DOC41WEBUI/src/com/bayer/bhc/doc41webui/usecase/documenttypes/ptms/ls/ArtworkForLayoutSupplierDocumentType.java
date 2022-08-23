package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.Doc41ValidationUtils;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;


public class ArtworkForLayoutSupplierDocumentType extends LayoutSupplierDocumentType
	implements UploadDocumentType, DownloadDocumentType{
	

	@Override
	public String getTypeConst() {
		return "ARTWORK_LS";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.48";
	}
	
	@Override
	public String getPermissionUpload() {
		return "DOC_ARTWORK_UP_LS";
	}


	@Override
	public String getPermissionDownload() {
		return "DOC_ARTWORK_DOWN_LS";
	}
	
	@Override
	protected boolean checkExistingDocs() {
	    return true;
	}

	   /**
     * checkForDownload
     * @param errors
     * @param documentUC
     * @param customerNumber
     * @param vendorNumber
     * @param objectId = materialNumber
     * @param attributeValues - what kind of magic attributes?
     * @param viewAttributes - what kind of magic attributes?
     * @return
     * @throws Doc41BusinessException
     */
    @Override
    public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC,
            String customerNumber, String vendorNumber, String objectId, String customVersion, Date timeFrame,
            Map<String, String> attributeValues,Map<String, String> viewAttributes, int subType,String purchaseOrder) throws Doc41BusinessException {
        
        Doc41ValidationUtils.checkMaterialNumber(objectId, "objectId", errors, true);
        
        String deliveryCheck = documentUC.checkArtworkLayoutForVendor(vendorNumber, objectId, getSapTypeId());
        if(deliveryCheck != null){
            errors.reject(""+deliveryCheck);
        }
        Map<String, String> additionalAttributes = new HashMap<String, String>();
        additionalAttributes.put(Doc41Constants.ATTRIB_NAME_VENDOR, vendorNumber);
        
        return new CheckForDownloadResult(additionalAttributes,null,null);
    }

	@Override
	public Set<String> getMandatoryAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public boolean isNotificationEMailHidden() {
		return false;
	}

	//Addedd by force ELERJ
	/*@Override
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC, String customerNumber,
			String vendorNumber, String objectId, String customVersion, Date timeFrame,
			Map<String, String> attributeValues, Map<String, String> viewAttributes, int subType,String purchaseOrder)
			throws Doc41BusinessException {
		// TODO Auto-generated method stub
		return null;
	}*/
	

    	
}
