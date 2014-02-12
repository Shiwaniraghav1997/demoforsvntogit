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
	
}
