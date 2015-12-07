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
import com.bayer.ecim.foundation.basic.StringTool;

public abstract class PMSupplierDownloadDocumentType implements DownloadDocumentType{

//	public static final String VIEW_ATTRIB_PO_NUMBER = "poNumber";

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
		
		String fileName = viewAttributes.get(VIEW_ATTRIB_FILENAME);
        if(!StringTool.isTrimmedEmptyOrNull(fileName)){
            attributeValues.put(VIEW_ATTRIB_FILENAME, fileName);
        }
//		if(StringTool.isTrimmedEmptyOrNull(poNumber)){
//			errors.rejectValue("viewAttributes['"+VIEW_ATTRIB_PO_NUMBER+"']","PONumberMissing");
//		}
		
		if(!errors.hasErrors()){
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
	
}