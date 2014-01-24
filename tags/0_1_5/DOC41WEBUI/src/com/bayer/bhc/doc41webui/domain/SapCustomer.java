package com.bayer.bhc.doc41webui.domain;

import com.bayer.ecim.foundation.basic.StringTool;

public class SapCustomer extends DomainObject {

	private static final long serialVersionUID = 2511145389307051601L;
	private String number;
	private String name1;
	private String name2;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	@Override
	public String toString() {
		return "SapCustomer [number=" + number + ", name1=" + name1
				+ ", name2=" + name2 + "]";
	}
	public String getLabel(){
		return ""+number+" "+StringTool.nullToEmpty(name1)+" "+StringTool.nullToEmpty(name2);
	}
	
	
}
