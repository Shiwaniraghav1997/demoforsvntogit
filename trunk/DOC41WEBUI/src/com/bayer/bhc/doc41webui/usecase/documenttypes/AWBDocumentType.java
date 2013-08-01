package com.bayer.bhc.doc41webui.usecase.documenttypes;

public class AWBDocumentType extends SDDocumentType {

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

}
