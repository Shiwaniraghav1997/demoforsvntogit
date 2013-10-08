package com.bayer.bhc.doc41webui.usecase.documenttypes;


public class FDACertDocumentType extends SDDocumentType implements DownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "FDACERT";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.20";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_FDACERT_DOWN";
	}

}
