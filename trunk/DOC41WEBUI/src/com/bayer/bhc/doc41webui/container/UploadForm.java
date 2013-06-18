package com.bayer.bhc.doc41webui.container;

import java.util.Map;

public class UploadForm {

	private String[] attributeNames;
	private Map<String, String> attributeValues;
	private String fileId;
	
	
	public String[] getAttributeNames() {
		return attributeNames;
	}
	public void setAttributeNames(String[] attributeNames) {
		this.attributeNames = attributeNames;
	}
	public Map<String, String> getAttributeValues() {
		return attributeValues;
	}
	public void setAttributeValues(Map<String, String> attributeValues) {
		this.attributeValues = attributeValues;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
}
