package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls;

import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;


public class ArtworkForLayoutSupplierDocumentType extends LayoutSupplierDocumentType
	implements UploadDocumentType, DownloadDocumentType{
	

	@Override
	public String getTypeConst() {
		return "ARTWORK_LS";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.48";
	}
	
	@Override
	public String getPermissionUpload() {
		return "DOC_ARTWORK_UP_LS";
	}


	@Override
	public String getPermissionDownload() {
		return "DOC_ARTWORK_DOWN_LS";
	}

}
