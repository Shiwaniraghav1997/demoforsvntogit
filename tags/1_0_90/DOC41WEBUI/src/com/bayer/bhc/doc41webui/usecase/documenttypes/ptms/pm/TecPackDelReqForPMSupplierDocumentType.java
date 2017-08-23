package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm;


public class TecPackDelReqForPMSupplierDocumentType extends PMSupplierDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "TPACKDELREQ_PM";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.51";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_TPACKDELREQ_DOWN_PM";
	}

}
