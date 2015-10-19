package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls;

import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;


public class LayoutForLayoutSupplierDocumentType extends LayoutSupplierDocumentType
	implements UploadDocumentType, DownloadDocumentType {
	
	@Override
	public String getTypeConst() {
		return "LAYOUT_LS";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.49";
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_LAYOUT_UP_LS";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_LAYOUT_DOWN_LS";
	}

	@Override
	protected boolean checkExistingDocs() {
	    return true;
	}
}
