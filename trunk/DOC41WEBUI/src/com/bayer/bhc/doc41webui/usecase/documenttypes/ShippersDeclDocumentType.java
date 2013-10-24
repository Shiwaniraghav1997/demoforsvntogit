package com.bayer.bhc.doc41webui.usecase.documenttypes;


public class ShippersDeclDocumentType extends SDDocumentType implements DownloadDocumentType,DirectDownloadDocumentType {

	@Override
	public String getTypeConst() {
		return "SHIPDECL";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.10";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_SHIPDECL_DOWN";
	}
	
	@Override
	public String getPermissionDirectDownload() {
		return "DOC_SHIPDECL_DIRECT_DOWN";
	}
	
	//TODO maybe YTMSA will become standard for sd
	@Override
	protected String getSapObjectShippingUnit() {
		return "YTMSA";
	}

}
