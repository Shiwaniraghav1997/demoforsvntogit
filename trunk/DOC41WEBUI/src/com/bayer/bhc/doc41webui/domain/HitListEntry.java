package com.bayer.bhc.doc41webui.domain;

import java.io.Serializable;
import java.util.Date;

public class HitListEntry implements Serializable {

	private static final long serialVersionUID = 6767482424597660047L;
	
	//not needed: ContentRepo, CreatedUser, plant 
	private String docId;
	private String objectId;
	private Date storageDate;
	private Date archiveLinkDate;
	private String objectType;
	private String documentClass;
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public Date getStorageDate() {
		return storageDate;
	}
	public void setStorageDate(Date storageDate) {
		this.storageDate = storageDate;
	}
	public Date getArchiveLinkDate() {
		return archiveLinkDate;
	}
	public void setArchiveLinkDate(Date archiveLinkDate) {
		this.archiveLinkDate = archiveLinkDate;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getDocumentClass() {
		return documentClass;
	}
	public void setDocumentClass(String documentClass) {
		this.documentClass = documentClass;
	} 
	
	
	
	
	
	
	//TODO Custom Attributes

}
