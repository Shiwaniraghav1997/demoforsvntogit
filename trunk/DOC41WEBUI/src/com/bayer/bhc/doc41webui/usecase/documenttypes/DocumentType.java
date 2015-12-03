package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Set;



public interface DocumentType {

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
