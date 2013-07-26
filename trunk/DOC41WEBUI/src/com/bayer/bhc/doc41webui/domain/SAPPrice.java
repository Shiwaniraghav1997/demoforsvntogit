package com.bayer.bhc.doc41webui.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * example:<br>
 * a material price of US$8 for 1 piece<br>
 * <ul>
 * <li>rate=8</li>
 * <li>currency=US$</li>
 * <li>conditionPricingUnit=1</li>
 * <li>conditionUnit=pieces(???)</li>
 * </ul>
 * @author EVAYD, EZZQC
 *
 */
public class SAPPrice implements Serializable {
	private static final long serialVersionUID = -2907550427431793792L;
	
	private BigDecimal rate;
	private String currency;
	private BigDecimal conditionPricingUnit;
	private String conditionUnit;
	
	public SAPPrice(BigDecimal rate, String currency, BigDecimal conditionPricingUnit, String conditionUnit) {
		super();
		this.rate = rate;
		this.currency = currency;
		this.conditionPricingUnit = conditionPricingUnit;
		this.conditionUnit = conditionUnit;
	}
	
	public BigDecimal getRate() {
		return rate;
	}
	public String getCurrency() {
		return currency;
	}
	public BigDecimal getConditionPricingUnit() {
		return conditionPricingUnit;
	}
	public String getConditionUnit() {
		return conditionUnit;
	}
	@Override
	public String toString() {
		return "SAPPrice [rate=" + rate + ", currency=" + currency
				+ ", conditionPricingUnit=" + conditionPricingUnit
				+ ", conditionUnit=" + conditionUnit + "]";
	}
	
	
}
