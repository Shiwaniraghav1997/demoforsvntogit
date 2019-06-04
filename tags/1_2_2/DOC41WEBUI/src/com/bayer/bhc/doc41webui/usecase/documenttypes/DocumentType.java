package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Set;



public interface DocumentType {

    public static final String GROUP_SD = "DOC_SD";
    public static final String GROUP_QM = "DOC_QM";
    public static final String GROUP_PM = "DOC_PM";
    public static final String GROUP_PPPI_PM = "DOC_PPPI_PM";
    public static final String GROUP_LS = "DOC_LS";
    
    public static final String GROUP_SD_PERM_DOWNL = "DOC_GLO_SD";
    public static final String GROUP_QM_PERM_DOWNL = null; //"DOC_GLO_QM";
    public static final String GROUP_PM_PERM_DOWNL = "DOC_GLO_PM";
    public static final String GROUP_LS_PERM_DOWNL = "DOC_GLO_LS";
    
	public boolean hasCustomerNumber();
	public boolean hasVendorNumber();
	public String getTypeConst();
	public String getSapTypeId();
	public int getObjectIdFillLength();
	public Set<String> getExcludedAttributes();
	public Set<String> getMandatoryAttributes();

    /**
     * Get the profile type for download permissions (DOC_SD/QM/LS/PT), see DocumentType.GROUP_* constants.
     * @return
     */
    public String getGroup();

	
	/**
	 * Flag to determine, if document uses DIRS store.
	 * @return true, if using DIRS. 
	 */
	public boolean isDirs();

    /**
     * Flag to determine, if document uses KGS store.
     * @return true, if using KGS. 
     */
	public boolean isKgs();
	
}
