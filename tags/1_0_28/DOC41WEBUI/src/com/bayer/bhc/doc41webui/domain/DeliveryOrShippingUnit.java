package com.bayer.bhc.doc41webui.domain;

import java.io.Serializable;
import java.util.Date;

public class DeliveryOrShippingUnit implements Serializable {
	
	private static final long serialVersionUID = -5383637090527354091L;
	
	public static final String FLAG_DELIVERY="L";
	public static final String FLAG_SHIPPING_UNIT="V";
	
	private String referenceNumber;
	private String flag;
	private String shipToNumber;
	private String soldToNumber;
	private String from;
	private String to;
	private Date goodsIssueDate;
	private String orderingParty;
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getShipToNumber() {
		return shipToNumber;
	}
	public void setShipToNumber(String shipToNumber) {
		this.shipToNumber = shipToNumber;
	}
	public String getSoldToNumber() {
		return soldToNumber;
	}
	public void setSoldToNumber(String soldToNumber) {
		this.soldToNumber = soldToNumber;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Date getGoodsIssueDate() {
		return goodsIssueDate;
	}
	public void setGoodsIssueDate(Date goodsIssueDate) {
		this.goodsIssueDate = goodsIssueDate;
	}
	public String getOrderingParty() {
		return orderingParty;
	}
	public void setOrderingParty(String orderingParty) {
		this.orderingParty = orderingParty;
	}
	public static String getFlagDelivery() {
		return FLAG_DELIVERY;
	}
	public static String getFlagShippingUnit() {
		return FLAG_SHIPPING_UNIT;
	}
	@Override
	public String toString() {
		return "DeliveryOrShippingUnit [referenceNumber=" + referenceNumber
				+ ", flag=" + flag + ", shipToNumber=" + shipToNumber
				+ ", soldToNumber=" + soldToNumber + ", from=" + from + ", to="
				+ to + ", goodsIssueDate=" + goodsIssueDate
				+ ", orderingParty=" + orderingParty + "]";
	}
	
	
	
	
	

}
