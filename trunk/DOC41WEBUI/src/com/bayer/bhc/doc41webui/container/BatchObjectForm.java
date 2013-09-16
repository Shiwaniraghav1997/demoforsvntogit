package com.bayer.bhc.doc41webui.container;

public class BatchObjectForm {

	private String type;
	private String partnerNumber;
	private String plant;
	private String material;
	private String batch;
	private String order;
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
	@Override
	public String toString() {
		return "BatchObjectForm [type=" + type + ", partnerNumber="
				+ partnerNumber + ", plant=" + plant + ", material=" + material
				+ ", batch=" + batch + ", order=" + order + "]";
	}
	
	
}
