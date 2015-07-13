package com.bayer.bhc.doc41webui.domain;

public class InspectionLot extends DomainObject {

	private static final long serialVersionUID = -26518215959436515L;

	private String number;
	private String materialNumber;
	private String materialText;
	private String plant;
	private String batch;
	private String vendor;
	private String vendorBatch;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
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
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getVendorBatch() {
		return vendorBatch;
	}
	public void setVendorBatch(String vendorBatch) {
		this.vendorBatch = vendorBatch;
	}
	@Override
	public String toString() {
		return "InspectionLot [number=" + number + ", materialNumber="
				+ materialNumber + ", materialText=" + materialText
				+ ", plant=" + plant + ", batch=" + batch + ", vendor="
				+ vendor + ", vendorBatch=" + vendorBatch + "]";
	}
	
	
	
}
