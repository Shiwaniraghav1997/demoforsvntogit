package com.bayer.bhc.doc41webui.domain;

import com.bayer.ecim.foundation.basic.StringTool;

public class SDReferenceCheckResult extends DomainObject {
	
	private static final long serialVersionUID = -7779990293370875596L;
	
	public static final int TYPE_UNKNOWN=1;
	public static final int TYPE_DELIVERY_NUMBER=1;
	public static final int TYPE_SHIPPING_UNIT_NUMBER=2;

	private String referenceNumber;
	private int referenceType;
	private String error;
	
	public SDReferenceCheckResult(String referenceNumber, int referenceType,
			String error) {
		super();
		this.referenceNumber = referenceNumber;
		this.referenceType = referenceType;
		this.error = error;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public int getReferenceType() {
		return referenceType;
	}
	public String getError() {
		return error;
	}
	
	
	
	public boolean isOk(){
		return StringTool.isTrimmedEmptyOrNull(error);
	}
	
	public boolean isDeliveryNumber(){
		return referenceType == TYPE_DELIVERY_NUMBER;
	}
	
	public boolean isShippingUnitNumber(){
		return referenceType == TYPE_SHIPPING_UNIT_NUMBER;
	}
}
