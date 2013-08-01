package com.bayer.bhc.doc41webui.usecase.documenttypes;

public class TempLogDocumentType extends SDDocumentType{

	@Override
	public String getTypeConst() {
		return "TEMPLOG";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.34";
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_TEMPLOG_UP";
	}

}
