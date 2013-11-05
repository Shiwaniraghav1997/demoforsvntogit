package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Set;


public interface DocumentType {

	public String getPartnerNumberType();
	public String getTypeConst();
	public String getSapTypeId();
	public int getObjectIdFillLength();
	public Set<String> getExcludedAttributes();
}
