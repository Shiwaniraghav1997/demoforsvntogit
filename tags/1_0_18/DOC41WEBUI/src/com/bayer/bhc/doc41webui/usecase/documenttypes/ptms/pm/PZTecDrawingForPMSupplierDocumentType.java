package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm;


public class PZTecDrawingForPMSupplierDocumentType extends PMSupplierDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "PZ_PM";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.03";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_PZ_DOWN_PM";
	}

}
