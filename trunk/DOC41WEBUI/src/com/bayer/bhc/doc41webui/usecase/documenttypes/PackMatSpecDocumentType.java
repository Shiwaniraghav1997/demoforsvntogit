package com.bayer.bhc.doc41webui.usecase.documenttypes;




public class PackMatSpecDocumentType extends PMSupplierDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "PMS";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.02";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_PMS_DOWN";
	}
	
	

}
