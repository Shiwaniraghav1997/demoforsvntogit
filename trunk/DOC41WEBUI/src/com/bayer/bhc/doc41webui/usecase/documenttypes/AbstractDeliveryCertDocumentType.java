package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Collections;
import java.util.Set;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.ecim.foundation.basic.StringTool;


public abstract class AbstractDeliveryCertDocumentType implements DocumentType {
	
	public static final String ATTRIB_COUNTRY = "COUNTRY";
	
//	TODO use for attributes
//	public static final String ATTRIB_MATERIAL = "MATERIAL";
//	public static final String ATTRIB_BATCH = "BATCH";
//	public static final String ATTRIB_PLANT = "PLANT";
	
	//TODO use if rfc should be used
	//TODO use for concatenated String
	public static final String VIEW_ATTRIB_MATERIAL = "MATERIAL";
	public static final String VIEW_ATTRIB_BATCH = "BATCH";
	public static final String VIEW_ATTRIB_PLANT = "PLANT";
	
	public static final String VIEW_ATTRIB_DELIVERY_NUMBER = "delivery";
	public static final String VIEW_ATTRIB_MATERIAL_TEXT = "materialText";
	
	
	
	//TODO SAP OBJECT???


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
	
	protected String getObjectIdSearchString(String material, String batch, 
			String plant) {
		
		String searchMaterial = getCPOptionalSubString(material,Doc41Constants.FIELD_SIZE_MATNR);
		String searchBatch = getCPOptionalSubString(batch,Doc41Constants.FIELD_SIZE_BATCH);
		String searchPlant = getCPOptionalSubString(plant,Doc41Constants.FIELD_SIZE_PLANT);
		return searchMaterial+searchBatch+searchPlant;
	}

	private String getCPOptionalSubString(String subString, int fieldSize) {
		if(StringTool.isTrimmedEmptyOrNull(subString)){
			return StringTool.minLString("", fieldSize, '+');
		} else {
			return StringTool.minLString(subString, fieldSize, '0');
		}
	}

}
