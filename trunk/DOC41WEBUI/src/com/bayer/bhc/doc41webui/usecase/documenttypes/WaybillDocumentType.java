package com.bayer.bhc.doc41webui.usecase.documenttypes;


public class WaybillDocumentType extends SDDocumentType implements DownloadDocumentType {

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

}
