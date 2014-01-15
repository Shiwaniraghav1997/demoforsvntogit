package com.bayer.bhc.doc41webui.usecase.documenttypes;


public class PZTecDrawingDocumentType extends PMSupplierDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "PZ";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.03";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_PZ_DOWN";
	}

}
