package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;

public class PackMatSpecForLayoutSupplierDocumentType extends LayoutSupplierDocumentType
implements DownloadDocumentType{

	@Override
	public String getTypeConst() {
		return "PMS_LS";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.02";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_PMS_DOWN_LS";
	}
	
	@Override
	protected boolean checkExistingDocs() {
	    // TODO Auto-generated method stub
	    return false;
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

//	elerj by force added 
	@Override
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC, String customerNumber,
			String vendorNumber, String objectId, String customVersion, Date timeFrame,
			Map<String, String> attributeValues, Map<String, String> viewAttributes, String puchaseOrder)
			throws Doc41BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
