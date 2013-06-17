package com.bayer.bhc.doc41webui.container;

import java.io.Serializable;

public class ContentRepositoryInfo implements Serializable {

	private static final long serialVersionUID = 4205667592929472061L;
	
	private String contentRepository;
	private String docClass;
	
	public String getContentRepository() {
		return contentRepository;
	}
	public void setContentRepository(String contentRepository) {
		this.contentRepository = contentRepository;
	}
	public String getDocClass() {
		return docClass;
	}
	public void setDocClass(String docClass) {
		this.docClass = docClass;
	}
	
	
}
