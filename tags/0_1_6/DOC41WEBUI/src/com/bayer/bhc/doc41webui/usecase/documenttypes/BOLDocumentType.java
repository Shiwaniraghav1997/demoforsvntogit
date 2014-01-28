package com.bayer.bhc.doc41webui.usecase.documenttypes;


public class BOLDocumentType extends SDDocumentType implements DownloadDocumentType,UploadDocumentType,DirectDownloadDocumentType{

	@Override
	public String getTypeConst() {
		return "BOL";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.14";
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_BOL_UP";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_BOL_DOWN";
	}
	
	@Override
	public String getPermissionDirectDownload() {
		return "DOC_BOL_DIRECT_DOWN";
	}

}
