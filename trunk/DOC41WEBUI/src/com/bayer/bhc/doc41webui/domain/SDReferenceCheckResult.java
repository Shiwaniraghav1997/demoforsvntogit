package com.bayer.bhc.doc41webui.domain;


public class SDReferenceCheckResult extends DomainObject {
	
	private static final long serialVersionUID = -7779990293370875596L;
	
	public static final int TYPE_UNKNOWN=-1;
	public static final int TYPE_DELIVERY_NUMBER=1;
	public static final int TYPE_SHIPPING_UNIT_NUMBER=2;

	private String referenceNumber;
	private int referenceType;
	private String vkOrg;
	private boolean connectedToPartner;
	
	public SDReferenceCheckResult(String referenceNumber, int referenceType,
			boolean connectedToPartner) {
		super();
		this.referenceNumber = referenceNumber;
		this.referenceType = referenceType;
		this.connectedToPartner = connectedToPartner;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public int getReferenceType() {
		return referenceType;
	}
	public String getVkOrg() {
		return vkOrg;
	}
	public void setVkOrg(String vkOrg) {
		this.vkOrg = vkOrg;
	}
	
	public String getError() {
		return getError(false);
	}
	public String getError(boolean ignorePartner) {
		if(isDeliveryNumber()){
			if(!ignorePartner && !connectedToPartner){
				return "DeliveryDoesNotBelongToCarrier";
			}
		} else if (isShippingUnitNumber()){
			if(!ignorePartner && !connectedToPartner){
				return "ShippingUnitDoesNotBelongToCarrier";
			}
		} else {
			return "ReferenceNumberUnknown";
		}
		return null;
	}
	public boolean isOk(){
		return isOk(false);
	}
	public boolean isOk(boolean ignorePartner){
		return referenceType>=0 && (ignorePartner || connectedToPartner);
	}
	
	public boolean isDeliveryNumber(){
		return referenceType == TYPE_DELIVERY_NUMBER;
	}
	
	public boolean isShippingUnitNumber(){
		return referenceType == TYPE_SHIPPING_UNIT_NUMBER;
	}
}
