package com.bayer.bhc.doc41webui.domain;

public class QMBatchObject extends DomainObject {

	private static final long serialVersionUID = 4981318741741554853L;

	private String objectId;
	private String materialNumber;
	private String materialText;
	private String plant;
	private String batch;
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
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
		return "QMBatchObject [objectId=" + objectId + ", materialNumber="
				+ materialNumber + ", materialText=" + materialText
				+ ", plant=" + plant + ", batch=" + batch + "]";
	}
	
	
}
