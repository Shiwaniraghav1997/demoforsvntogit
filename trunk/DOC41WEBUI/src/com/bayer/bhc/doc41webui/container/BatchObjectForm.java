package com.bayer.bhc.doc41webui.container;

import java.util.List;

import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.UserPartner;

public class BatchObjectForm {

	private String type;
	private String partnerNumber;
	private String plant;
	private String material;
	private String batch;
	private String order;
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
	public List<UserPartner> getPartners() {
		return partners;
	}
//	public void setPartners(List<UserPartner> partners) {
//		this.partners = partners;
//	}
	public void initPartnerNumber(String partnerNumberType) {
//		this.partnerNumberUsed = !StringTool.isTrimmedEmptyOrNull(partnerNumberType);
//		if(partnerNumberUsed){
			partners = UserInSession.get().getPartnersByType(partnerNumberType);
//		}
	}
	@Override
	public String toString() {
		return "BatchObjectForm [type=" + type + ", partnerNumber="
				+ partnerNumber + ", plant=" + plant + ", material=" + material
				+ ", batch=" + batch + ", order=" + order + ", partners="
				+ partners + "]";
	}
	
	
}
