package com.bayer.bhc.doc41webui.container;

import java.util.HashMap;
import java.util.Map;

import com.bayer.bhc.doc41webui.usecase.documenttypes.SupplierCOADocumentType;

public class SupCoaUploadForm extends UploadForm {

	private String batch;
	
	public String getBatch() {
		return batch;
	}
	
	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	@Override
	public Map<String, String> getViewAttributes() {
		Map<String, String> viewAttribs = new HashMap<String, String>();
		viewAttribs.put(SupplierCOADocumentType.VIEW_ATTRIB_BATCH,batch);
		return viewAttribs;
	}
}
