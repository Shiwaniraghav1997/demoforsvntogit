package com.bayer.bhc.doc41webui.container;

import java.util.List;

import com.bayer.bhc.doc41webui.domain.SapVendor;

public class VendorBatchForm {

	private String type;
	private String vendorNumber;
	private String vendorBatch;
	private String plant;
	private List<SapVendor> vendors;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVendorNumber() {
		return vendorNumber;
	}
	public void setVendorNumber(String vendorNumber) {
		this.vendorNumber = vendorNumber;
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
	public List<SapVendor> getVendors() {
		return vendors;
	}
	public void setVendors(List<SapVendor> vendors) {
		this.vendors = vendors;
	}
	@Override
	public String toString() {
		return "VendorBatchForm [type=" + type + ", vendorNumber="
				+ vendorNumber + ", vendorBatch=" + vendorBatch + ", plant="
				+ plant + ", vendors=" + vendors + "]";
	}
	
	
}
