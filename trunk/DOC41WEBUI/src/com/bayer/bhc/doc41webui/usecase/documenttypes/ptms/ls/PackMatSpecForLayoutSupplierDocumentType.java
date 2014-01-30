package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls;

import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;

public class PackMatSpecForLayoutSupplierDocumentType extends LayoutSupplierDocumentType
implements DownloadDocumentType{

	@Override
	public String getTypeConst() {
		return "TPACKDELREQ_LS";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.51";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_TPACKDELREQ_DOWN_LS";
	}
	
}
