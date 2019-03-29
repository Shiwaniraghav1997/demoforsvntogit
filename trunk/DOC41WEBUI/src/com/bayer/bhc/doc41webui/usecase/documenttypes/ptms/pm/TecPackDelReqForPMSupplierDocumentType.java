package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm;

import java.util.Set;

public class TecPackDelReqForPMSupplierDocumentType extends PMSupplierDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "TPACKDELREQ_PM";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.51";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_TPACKDELREQ_DOWN_PM";
	}

	@Override
	public Set<String> getMandatoryAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

}
