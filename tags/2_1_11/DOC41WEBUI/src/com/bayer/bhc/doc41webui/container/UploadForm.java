package com.bayer.bhc.doc41webui.container;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm extends CustomizedDocumentForm {

	private String fileId;
	private String fileName;
	private transient MultipartFile file;
	private boolean notificationEmailHidden;
	private String notificationEmail;

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

	public boolean isNotificationEmailHidden() {
		return notificationEmailHidden;
	}

	public void setNotificationEmailHidden(boolean notificationEMailHidden) {
		this.notificationEmailHidden = notificationEMailHidden;
	}

	public String getNotificationEmail() {
		return notificationEmail;
	}

	public void setNotificationEmail(String notificationEMail) {
		this.notificationEmail = notificationEMail;
	}

	@Override
	public String toString() {
		return "UploadForm [fileId=" + fileId + ", fileName=" + fileName + ", notificationEMail=" + notificationEmail
				+ "]";
	}

}
