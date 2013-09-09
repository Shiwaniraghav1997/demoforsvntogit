package com.bayer.bhc.doc41webui.container;

import java.util.HashMap;
import java.util.Map;

import com.bayer.bhc.doc41webui.usecase.documenttypes.SupplierCOADocumentType;

//TODO maybe not necessary, if vendorBatch is part of the normal attributes
public class SupCoaUploadForm extends UploadForm {

	private String vendorBatch;
	
	public String getVendorBatch() {
		return vendorBatch;
	}
	
	public void setVendorBatch(String vendorBatch) {
		this.vendorBatch = vendorBatch;
	}
	
	@Override
	public Map<String, String> getViewAttributes() {
		Map<String, String> viewAttribs = new HashMap<String, String>();
		viewAttribs.put(SupplierCOADocumentType.VIEW_ATTRIB_VENDOR_BATCH,vendorBatch);
		return viewAttribs;
	}
}
