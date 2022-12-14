package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;

public class RawMaterialSpecificationPMSupplierDocumentType  extends PMSupplierDownloadDocumentType {
	
	
//	RowMaterialSystemPMSupplierDocumentType
	

	@Override
	public String getTypeConst() {
		return "Raw_MAT_PM";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.01";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_ROWMATERIAL_DOWN_PM";
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
            String customerNumber, String vendorNumber, String objectId, String customVersion, Date timeFrame,
            Map<String, String> attributeValues,Map<String, String> viewAttributes,int subType,String purchaseOrder) throws Doc41BusinessException {
//    	System.out.println("in rms download class");
        CheckForDownloadResult result = super.checkForDownload(errors, documentUC, customerNumber, vendorNumber,
                objectId, customVersion, timeFrame, attributeValues, viewAttributes,subType,purchaseOrder);
        return result;


    }

	@Override
	public Set<String> getMandatoryAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
    
	//TODO downloadcheck wie pm


}
