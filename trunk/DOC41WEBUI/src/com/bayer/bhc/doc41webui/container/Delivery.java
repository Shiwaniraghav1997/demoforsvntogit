package com.bayer.bhc.doc41webui.container;

import java.io.Serializable;

public class Delivery implements Serializable {
	private static final long serialVersionUID = -8681479691481502207L;
	
	private String deliveryNumber;
	private String shippingUnitNumber;
	private String shipToNumber;
	private String SoldToNumber;
	
	public String getDeliveryNumber() {
		return deliveryNumber;
	}
	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
	public String getShippingUnitNumber() {
		return shippingUnitNumber;
	}
	public void setShippingUnitNumber(String shippingUnitNumber) {
		this.shippingUnitNumber = shippingUnitNumber;
	}
	public String getShipToNumber() {
		return shipToNumber;
	}
	public void setShipToNumber(String shipToNumber) {
		this.shipToNumber = shipToNumber;
	}
	public String getSoldToNumber() {
		return SoldToNumber;
	}
	public void setSoldToNumber(String soldToNumber) {
		SoldToNumber = soldToNumber;
	}
	
	

}
