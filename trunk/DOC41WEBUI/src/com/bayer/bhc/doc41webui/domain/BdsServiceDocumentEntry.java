package com.bayer.bhc.doc41webui.domain;

import java.util.Date;
import java.util.Map;

/**
 * light weight version of HitListEntry for the docservice (for security reasons)
 * @author EVAYD
 *
 */
public class BdsServiceDocumentEntry {
    public static final String ATTRIB_NAME_FILENAME = "FILENAME";

    private String docId;
    private String objectId;
    private Date storageDate;
    private Date archiveLinkDate;
    private String objectType;
    private String documentClass;
    private Map<String, String> customizedValuesByKey;
    private String key;
    private String type;
    
    public BdsServiceDocumentEntry() {
    }
    public BdsServiceDocumentEntry(HitListEntry hitListEntry) {
        this();
        this.docId=hitListEntry.getDocId();
        this.objectId=hitListEntry.getObjectId();
        this.storageDate=hitListEntry.getStorageDate();
        this.archiveLinkDate=hitListEntry.getArchiveLinkDate();
        this.objectType=hitListEntry.getObjectType();
        this.documentClass=hitListEntry.getDocumentClass();
        this.customizedValuesByKey=hitListEntry.getCustomizedValuesByKey();
        this.key=hitListEntry.getKey();
        this.type=hitListEntry.getType();
    }
    
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
    public Map<String, String> getCustomizedValuesByKey() {
        return customizedValuesByKey;
    }
    public void setCustomizedValuesByKey(
            Map<String, String> customizedValuesByKey) {
        this.customizedValuesByKey = customizedValuesByKey;
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
        return "BdsDocumentEntry [docId=" + docId + ", objectId=" + objectId
                + ", storageDate=" + storageDate + ", archiveLinkDate="
                + archiveLinkDate + ", objectType=" + objectType
                + ", documentClass=" + documentClass
                + ", customizedValuesByKey=" + customizedValuesByKey + ", key="
                + key + ", type=" + type + "]";
    }

}
