package com.bayer.bhc.doc41webui.usecase.documenttypes.sd;

import java.util.Set;

import com.bayer.bhc.doc41webui.usecase.documenttypes.DirectDownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;


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

	@Override
	public Set<String> getMandatoryAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

}
