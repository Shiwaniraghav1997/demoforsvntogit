package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Collections;
import java.util.Set;


public abstract class AbstractDeliveryCertDocumentType implements DocumentType {
	
	public static final String ATTRIB_COUNTRY = "COUNTRY";
	public static final String ATTRIB_MATERIAL = "MATERIAL";
	public static final String ATTRIB_BATCH = "BATCH";
	public static final String ATTRIB_PLANT = "PLANT";
	
	public static final String VIEW_ATTRIB_DELIVERY_NUMBER = "delivery";
	
	
	
	//TODO SAP OBJECT???


	@Override
	public String getSapTypeId() {
		return "DOC41.39";
		//TODO remove mock with BOL type
//		return "DOC41.14";//BOL
	}
	
	@Override
	public int getObjectIdFillLength() {
		return 0;
	}
	
	@Override
	public Set<String> getExcludedAttributes() {
		return Collections.emptySet();
	}

}
