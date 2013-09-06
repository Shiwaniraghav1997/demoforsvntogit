package com.bayer.bhc.doc41webui.domain;

public class InspectionLot extends DomainObject {

	private static final long serialVersionUID = -1857956837235743559L;
	
	private String number;
	private String materialNumber;
	private String batch;
	private String plant;
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
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	@Override
	public String toString() {
		return "InspectionLot [number=" + number + ", materialNumber="
				+ materialNumber + ", batch=" + batch + ", plant=" + plant
				+ "]";
	}
	
	
	
}
