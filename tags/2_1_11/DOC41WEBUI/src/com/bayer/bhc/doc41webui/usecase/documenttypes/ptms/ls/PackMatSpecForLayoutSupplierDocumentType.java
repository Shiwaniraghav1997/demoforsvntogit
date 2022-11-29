package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls;

import java.util.Set;

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
	
/*//	Addedd by force ELERJ

	@Override
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC, String customerNumber,
			String vendorNumber, String objectId, String customVersion, Date timeFrame,
			Map<String, String> attributeValues, Map<String, String> viewAttributes, int subType,String purchaseOrder)
			throws Doc41BusinessException {
		// TODO Auto-generated method stub
		return null;
	}*/
	
}
