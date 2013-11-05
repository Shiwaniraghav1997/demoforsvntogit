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
	
	@Override
	protected String getSapObjectShippingUnit() {
		return SAP_OBJECT_SHIPPING_UNIT;
	}

}
