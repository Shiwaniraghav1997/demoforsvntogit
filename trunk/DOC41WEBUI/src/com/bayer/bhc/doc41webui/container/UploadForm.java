package com.bayer.bhc.doc41webui.container;

import org.springframework.web.multipart.MultipartFile;

import com.bayer.ecim.foundation.basic.StringTool;

public class UploadForm extends CustomizedDocumentForm{
	
	private String fileId;
//	private String typeLabel;
	private transient MultipartFile file;
	private String includeForObjectId;
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public String getIncludeForObjectId() {
		if(StringTool.isTrimmedEmptyOrNull(includeForObjectId)){
			return "includeUploadObjectIdDefault";
		}
		return includeForObjectId;
	}
	public void setIncludeForObjectId(String includeForObjectId) {
		this.includeForObjectId = includeForObjectId;
	}

	@Override
	public String toString() {
		return "UploadForm [fileId=" + fileId + ", toString()="
				+ super.toString() + "]";
	}
	
	private String test1;
	private String test2;
	public String getTest1() {
		return test1;
	}
	public void setTest1(String test1) {
		this.test1 = test1;
	}
	public String getTest2() {
		return test2;
	}
	public void setTest2(String test2) {
		this.test2 = test2;
	}
	
	
}
