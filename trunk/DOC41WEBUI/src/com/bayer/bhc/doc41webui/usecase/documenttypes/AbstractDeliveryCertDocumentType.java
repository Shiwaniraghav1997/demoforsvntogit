package com.bayer.bhc.doc41webui.usecase.documenttypes;


public abstract class AbstractDeliveryCertDocumentType implements DocumentType {
	
	//TODO
	public static final String VIEW_ATTRIB_COUNTRY = "country";
	public static final String VIEW_ATTRIB_MATERIAL = "material";
	public static final String VIEW_ATTRIB_BATCH = "batch";
	public static final String VIEW_ATTRIB_PLANT = "plant";
	
	
	
	//TODO SAP OBJECT???


	@Override
	public String getSapTypeId() {
		return "DOC41.39";
	}

}
