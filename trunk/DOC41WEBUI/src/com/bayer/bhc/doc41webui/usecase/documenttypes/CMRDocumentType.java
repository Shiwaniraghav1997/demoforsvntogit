package com.bayer.bhc.doc41webui.usecase.documenttypes;


public class CMRDocumentType extends SDUploadDocumentType {

	@Override
	public String getTypeConst() {
		return "CMR";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.35";
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_CMR_UP";
	}

}
