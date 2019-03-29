package com.bayer.bhc.doc41webui.usecase.documenttypes.sd;

import java.util.Set;

import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;


public class CMRDocumentType extends SDDocumentType implements UploadDocumentType{

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

	@Override
	public Set<String> getMandatoryAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

}
