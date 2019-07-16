package com.bayer.bhc.doc41webui.domain;

import java.io.Serializable;

public class ContentRepositoryInfo implements Serializable {

	private static final long serialVersionUID = 4205667592929472061L;
	
	private String contentRepository;
	private String allowedDocClass;
	
	public String getContentRepository() {
		return contentRepository;
	}
	public void setContentRepository(String contentRepository) {
		this.contentRepository = contentRepository;
	}
	public String getAllowedDocClass() {
		return allowedDocClass;
	}
	public void setAllowedDocClass(String docClass) {
		this.allowedDocClass = docClass;
	}
	@Override
	public String toString() {
		return "ContentRepositoryInfo [contentRepository=" + contentRepository
				+ ", docClass=" + allowedDocClass + "]";
	}
	
	
}
