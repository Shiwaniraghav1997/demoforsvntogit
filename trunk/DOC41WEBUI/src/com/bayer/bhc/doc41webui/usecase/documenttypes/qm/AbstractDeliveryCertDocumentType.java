package com.bayer.bhc.doc41webui.usecase.documenttypes.qm;

import java.util.Collections;
import java.util.Set;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DocumentType;


public abstract class AbstractDeliveryCertDocumentType implements DocumentType {
	
	public static final String ATTRIB_COUNTRY = "COUNTRY";
	
	public static final String ATTRIB_MATERIAL = "MATERIAL";
	public static final String ATTRIB_BATCH = "BATCH";
	public static final String ATTRIB_PLANT = Doc41Constants.ATTRIB_NAME_PLANT;
	
	
	public static final String VIEW_ATTRIB_DELIVERY_NUMBER = "delivery";
	public static final String VIEW_ATTRIB_MATERIAL_TEXT = "materialText";
	
	protected static final String SAP_OBJECT_BATCH_OBJ = "BUS1001002";
	
	@Override
	public String getSapTypeId() {
		return "DOC41.39";
	}
	
	@Override
	public int getObjectIdFillLength() {
		return 0;
	}
	
	@Override
	public Set<String> getExcludedAttributes() {
		return Collections.emptySet();
	}
	
    /**
     * Flag to determine, if document uses DIRS store.
     * @return true, if using DIRS. 
     */
    @Override
    public boolean isDirs() {
        return false;
    }

    /**
     * Flag to determine, if document uses KGS store.
     * @return true, if using KGS. 
     */
    @Override
    public boolean isKgs() {
        return true;
    }
    
    /**
     * Set the profile type for download permissions (DOC_SD/QM/LS/PM), see DocumentType.GROUP_* constants.
     * @param pPermType
     */
    public void setDownloadPermissionType(String pPermType) {
        cDownloadPermType = pPermType;
    }

    /**
     * Get the profile type for download permissions (DOC_SD/QM/LS/PM), see DocumentType.GROUP_* constants.
     * @return
     */
    public String getDownloadPermissionType() {
        return cDownloadPermType;
    }
    
    /** Type of the Profile for Download Permissions. */
    String cDownloadPermType = null; 

    /**
     * The permissions allowing download for all documents of the same document group (= permission type).
     * Take care, permission type is DOC_xx, corresponding permission is DOC_GLO_xx...
     * (currently set to null for QM)
     * @return the permission code, may be null, if this document is not allowed to download by group membership.
     */
    public String getGroupPermissionDownload() {
        return GROUP_QM_PERM_DOWNL;
    }
    
    /**
     * Get the profile type for download permissions (DOC_SD/QM/LS/PM), see DocumentType.GROUP_* constants.
     * @return
     */
    public String getGroup() {
        return GROUP_QM;
    }
    
}
