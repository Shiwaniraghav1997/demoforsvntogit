package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm;




public class AntiCounSpecForPMSupplierDocumentType extends PMSupplierDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "ACS_PM";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.02";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_ACS_DOWN_PM";
	}
	
	

}
