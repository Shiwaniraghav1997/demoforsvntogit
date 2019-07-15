package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm;

import java.util.Set;

public class PZTecDrawingForPMSupplierDocumentType extends PMSupplierDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "PZ_PM";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.03";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_PZ_DOWN_PM";
	}

	@Override
	public Set<String> getMandatoryAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

}
