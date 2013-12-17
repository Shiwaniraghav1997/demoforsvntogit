package com.bayer.bhc.doc41webui.domain;

import com.bayer.ecim.foundation.basic.StringTool;

public class SapPartner extends DomainObject {

	private static final long serialVersionUID = 2511145389307051601L;
	private String partnerNumber;
	private String partnerName1;
	private String partnerName2;
	private String partnerType;
	
	public String getPartnerNumber() {
		return partnerNumber;
	}
	public void setPartnerNumber(String partnerNumber) {
		this.partnerNumber = partnerNumber;
	}
	public String getPartnerName1() {
		return partnerName1;
	}
	public void setPartnerName1(String partnerName1) {
		this.partnerName1 = partnerName1;
	}
	public String getPartnerName2() {
		return partnerName2;
	}
	public void setPartnerName2(String partnerName2) {
		this.partnerName2 = partnerName2;
	}
	public String getPartnerType() {
		return partnerType;
	}
	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}
	@Override
	public String toString() {
		return "UserPartner [partnerNumber=" + partnerNumber
				+ ", partnerName1=" + partnerName1 + ", partnerName2="
				+ partnerName2 + ", partnerType=" + partnerType + "]";
	}
	
	public String getPartnerLabel(){
		return ""+partnerNumber+" "+StringTool.nullToEmpty(partnerName1)+" "+StringTool.nullToEmpty(partnerName2);
	}
	
	
}
