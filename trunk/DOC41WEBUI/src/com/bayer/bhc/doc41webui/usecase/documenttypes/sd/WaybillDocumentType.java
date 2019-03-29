package com.bayer.bhc.doc41webui.usecase.documenttypes.sd;

import java.util.Set;

import com.bayer.bhc.doc41webui.usecase.documenttypes.DirectDownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;


public class WaybillDocumentType extends SDDocumentType implements DownloadDocumentType,DirectDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "WB";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.15";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_WB_DOWN";
	}
	
	@Override
	public String getPermissionDirectDownload() {
		return "DOC_WB_DIRECT_DOWN";
	}

	@Override
	public Set<String> getMandatoryAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

}
