package com.bayer.bhc.doc41webui.container;

import java.io.Serializable;
import java.util.Date;

public class DocumentStatus implements Serializable {

	private static final long serialVersionUID = -6751748122291713843L;
	
	private String status;
	private Date archivingDate;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getArchivingDate() {
		return archivingDate;
	}
	public void setArchivingDate(Date archivingDate) {
		this.archivingDate = archivingDate;
	}
	
	
}
