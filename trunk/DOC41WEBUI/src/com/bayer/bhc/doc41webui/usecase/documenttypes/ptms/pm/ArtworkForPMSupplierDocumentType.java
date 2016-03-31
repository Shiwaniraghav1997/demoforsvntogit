package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm;

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




public class ArtworkForPMSupplierDocumentType extends PMSupplierDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "ARTWORK_PM";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.48";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_ARTWORK_DOWN_PM";
	}
	
	
	@Override
	public Set<String> getExcludedAttributes() {
		return Collections.singleton(Doc41Constants.ATTRIB_NAME_VENDOR);
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
            String customerNumber, String vendorNumber, String objectId,
            Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {

        CheckForDownloadResult result = super.checkForDownload(errors, documentUC, customerNumber, vendorNumber,
                objectId, attributeValues, viewAttributes);
        return result;

/* no more extra check required anymore...

        Doc41ValidationUtils.checkMaterialNumber(objectId, "objectId", errors, true);
        
        String deliveryCheck = documentUC.checkArtworkLayoutForVendor(vendorNumber, objectId, getSapTypeId());
        if(deliveryCheck != null){
            errors.reject(""+deliveryCheck);
        }
        Map<String, String> additionalAttributes = new HashMap<String, String>();
        additionalAttributes.put(Doc41Constants.ATTRIB_NAME_VENDOR, vendorNumber);
        
        return new CheckForDownloadResult(additionalAttributes,null);
*/
    }
    
	//TODO downloadcheck wie pm
}
