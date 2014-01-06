package com.bayer.bhc.doc41webui.container;

import java.util.List;

import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.SapVendor;

public class BatchObjectForm {

	private String type;
	private String vendorNumber;
	private String plant;
	private String material;
	private String batch;
	private String order;
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
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public List<SapVendor> getVendors() {
		return vendors;
	}
	public void initVendors() {
		vendors = UserInSession.get().getVendors();
	}
	@Override
	public String toString() {
		return "BatchObjectForm [type=" + type + ", vendorNumber="
				+ vendorNumber + ", plant=" + plant + ", material=" + material
				+ ", batch=" + batch + ", order=" + order + ", vendors="
				+ vendors + "]";
	}
	
	
}
