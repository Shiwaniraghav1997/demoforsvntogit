package com.bayer.bhc.doc41webui.container;

import java.util.List;

import com.bayer.bhc.doc41webui.domain.UserPartner;

public class VendorBatchForm {

	private String type;
	private String partnerNumber;
	private String vendorBatch;
	private String plant;
	private List<UserPartner> partners;
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
	public List<UserPartner> getPartners() {
		return partners;
	}
	public void setPartners(List<UserPartner> partners) {
		this.partners = partners;
	}
	@Override
	public String toString() {
		return "VendorBatchForm [type=" + type + ", partnerNumber="
				+ partnerNumber + ", vendorBatch=" + vendorBatch + ", plant="
				+ plant + ", partners=" + partners + "]";
	}
	
	
}
