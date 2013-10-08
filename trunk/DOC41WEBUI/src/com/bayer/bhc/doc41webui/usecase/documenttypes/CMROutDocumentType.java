package com.bayer.bhc.doc41webui.usecase.documenttypes;


public class CMROutDocumentType extends SDDocumentType implements DownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "CMROUT";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.08";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_CMROUT_DOWNLOAD";
	}

}
