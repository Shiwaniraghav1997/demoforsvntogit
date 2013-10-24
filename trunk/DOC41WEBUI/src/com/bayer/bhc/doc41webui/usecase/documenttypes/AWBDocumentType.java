package com.bayer.bhc.doc41webui.usecase.documenttypes;


public class AWBDocumentType extends SDDocumentType implements DownloadDocumentType,UploadDocumentType,DirectDownloadDocumentType{

	@Override
	public String getTypeConst() {
		return "AWB";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.16";
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_AWB_UP";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_AWB_DOWN";
	}

	@Override
	public String getPermissionDirectDownload() {
		return "DOC_AWB_DIRECT_DOWN";
	}

}
