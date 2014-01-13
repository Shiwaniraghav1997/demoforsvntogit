package com.bayer.bhc.doc41webui.domain;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.ecim.foundation.basic.StringTool;

public class QMBatchObject extends DomainObject {

	private static final long serialVersionUID = 4981318741741554853L;

	private String materialNumber;
	private String materialText;
	private String plant;
	private String batch;
	
	public String getMaterialNumber() {
		return materialNumber;
	}
	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}
	public String getMaterialText() {
		return materialText;
	}
	public void setMaterialText(String materialText) {
		this.materialText = materialText;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	@Override
	public String toString() {
		return "QMBatchObject [materialNumber="
				+ materialNumber + ", materialText=" + materialText
				+ ", plant=" + plant + ", batch=" + batch + "]";
	}
	
	public String getObjectId() {
		return getObjectId(materialNumber, batch, plant);
	}
	
	public static String getObjectId(String material, String batch, 
			String plant) {
		String paddedMaterial = StringTool.minLString(material, Doc41Constants.FIELD_SIZE_MATNR, '0');
		String paddedBatch = StringTool.minRString(batch, Doc41Constants.FIELD_SIZE_BATCH, ' ');
		String paddedPlant = StringTool.minRString(plant, Doc41Constants.FIELD_SIZE_PLANT, ' ');
		return paddedMaterial+paddedBatch+paddedPlant;
	}
}
