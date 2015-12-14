package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm;




public class PackMatSpecForPMSupplierDocumentType extends PMSupplierDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "PMS_PM";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.02";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_PMS_DOWN_PM";
	}
	
	

}
