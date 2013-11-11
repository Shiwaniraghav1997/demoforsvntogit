package com.bayer.bhc.doc41webui.container;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm extends CustomizedDocumentForm{
	
	private String fileId;
	private String fileName;
//	private String typeLabel;
	private transient MultipartFile file;
	private String notificationEMail;
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public String getNotificationEMail() {
		return notificationEMail;
	}
	
	public void setNotificationEMail(String notificationEMail) {
		this.notificationEMail = notificationEMail;
	}

	@Override
	public String toString() {
		return "UploadForm [fileId=" + fileId + ", fileName=" + fileName
				+ ", notificationEMail=" + notificationEMail + "]";
	}
	
}
