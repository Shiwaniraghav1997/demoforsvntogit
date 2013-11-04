package com.bayer.bhc.doc41webui.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.ecim.foundation.basic.StringTool;

public class HitListEntry implements Serializable {

	private static final long serialVersionUID = 6767482424597660047L;
	
	//not needed: ContentRepo, CreatedUser, plant 
	private String docId;
	private String objectId;
	private Date storageDate;
	private Date archiveLinkDate;
	private String objectType;
	private String documentClass;
	private String[] customizedValues;
	private Map<String, String> customizedValuesByKey;
	private String key;
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
	public void setCustomizedValues(String[] customizedValues) {
		this.customizedValues = customizedValues;
	}
	public Map<String, String> getCustomizedValuesByKey() {
		return customizedValuesByKey;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
	public void initCustValuesMap(Map<Integer, String> seqToKey){
		customizedValuesByKey = new HashMap<String, String>();
		for(int i=0;i<Doc41Constants.CUSTOMIZATION_VALUES_COUNT;i++){
			String key = seqToKey.get(i+1);
			if(!StringTool.isTrimmedEmptyOrNull(key)){
				String value = customizedValues[i];
				customizedValuesByKey.put(key, value);
			}
		}
	}
	
	public String getFileName(){
		return getCustomizedValuesByKey().get(Doc41Constants.ATTRIB_NAME_FILENAME);
	}

}
