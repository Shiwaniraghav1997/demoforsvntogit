package com.bayer.bhc.doc41webui.usecase.documenttypes;


public class TecPackDelReqDocumentType extends PMSupplierDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "TPACKDELREQ";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.51";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_TPACKDELREQ_DOWN";
	}

}
