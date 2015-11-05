package com.bayer.bhc.doc41webui.domain;

import java.io.Serializable;
import java.util.Arrays;
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
	private String type;
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
		this.customizedValues = Arrays.copyOf(customizedValues, customizedValues.length);
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
	public String getType() {
        return type;
    }
	public void setType(String type) {
        this.type = type;
    }
	
	
	@Override
    public String toString() {
        return "HitListEntry [docId=" + docId + ", objectId=" + objectId
                + ", storageDate=" + storageDate + ", archiveLinkDate="
                + archiveLinkDate + ", objectType=" + objectType
                + ", documentClass=" + documentClass + ", customizedValues="
                + Arrays.toString(customizedValues) + ", customizedValuesByKey="
                + customizedValuesByKey + ", key=" + key + ", type=" + type
                + "]";
    }
    public void initCustValuesMap(Map<Integer, String> seqToKey){
		customizedValuesByKey = new HashMap<String, String>();
		for(int i=0;i<Doc41Constants.CUSTOMIZATION_VALUES_COUNT;i++){
			String oneKey = seqToKey.get(i+1);
			if(!StringTool.isTrimmedEmptyOrNull(oneKey)){
				String value = customizedValues[i];
				customizedValuesByKey.put(oneKey, value);
			}
		}
	}
	
	public String getFileName(){
		return getCustomizedValuesByKey().get(Doc41Constants.ATTRIB_NAME_FILENAME);
	}

}
