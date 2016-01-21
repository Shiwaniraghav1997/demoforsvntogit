package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Set;



public interface DocumentType {

    public static final String GROUP_SD = "DOC_SD";
    public static final String GROUP_QM = "DOC_QM";
    public static final String GROUP_PM = "DOC_PM";
    public static final String GROUP_LS = "DOC_LS";
    
	public boolean hasCustomerNumber();
	public boolean hasVendorNumber();
	public String getTypeConst();
	public String getSapTypeId();
	public int getObjectIdFillLength();
	public Set<String> getExcludedAttributes();
	
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
