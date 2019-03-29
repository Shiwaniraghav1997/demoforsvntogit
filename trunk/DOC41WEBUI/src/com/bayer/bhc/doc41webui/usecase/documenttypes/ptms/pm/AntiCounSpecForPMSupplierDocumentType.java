package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm;

import java.util.Set;

public class AntiCounSpecForPMSupplierDocumentType extends PMSupplierDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "ACS_PM";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.54";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_ACS_DOWN_PM";
	}

	@Override
	public Set<String> getMandatoryAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
