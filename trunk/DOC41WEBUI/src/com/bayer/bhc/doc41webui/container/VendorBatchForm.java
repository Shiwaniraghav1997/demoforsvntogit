package com.bayer.bhc.doc41webui.container;

public class VendorBatchForm {

	private String type;
	private String partnerNumber;
	private String vendorBatch;
	private String plant;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPartnerNumber() {
		return partnerNumber;
	}
	public void setPartnerNumber(String partnerNumber) {
		this.partnerNumber = partnerNumber;
	}
	public String getVendorBatch() {
		return vendorBatch;
	}
	public void setVendorBatch(String vendorBatch) {
		this.vendorBatch = vendorBatch;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	@Override
	public String toString() {
		return "VendorBatchForm [type=" + type + ", partnerNumber="
				+ partnerNumber + ", vendorBatch=" + vendorBatch + ", plant="
				+ plant + "]";
	}
	
	
}
