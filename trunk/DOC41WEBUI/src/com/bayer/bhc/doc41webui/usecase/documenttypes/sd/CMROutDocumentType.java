package com.bayer.bhc.doc41webui.usecase.documenttypes.sd;

import java.util.Set;

import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;


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
		return "DOC_CMROUT_DOWN";
	}

	@Override
	public Set<String> getMandatoryAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

}
