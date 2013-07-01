package com.bayer.bhc.doc41webui.container;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {

	private String[] attributeNames;
	private Map<String, String> attributeValues;
	private String fileId;
	private String type;
	private transient MultipartFile file;
	
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
