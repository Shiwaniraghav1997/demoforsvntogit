package com.bayer.bhc.doc41webui.usecase.documenttypes.sd;

import java.util.Set;

import com.bayer.bhc.doc41webui.usecase.documenttypes.DirectDownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;


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
	public Set<String> getMandatoryAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
