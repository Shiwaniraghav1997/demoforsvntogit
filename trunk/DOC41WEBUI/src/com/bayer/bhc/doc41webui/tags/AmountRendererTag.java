/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.bayer.bhc.doc41webui.domain.SAPPrice;

/**
 * AmountRendererTag.
 * 
 * @author ezzqc
 */
public class AmountRendererTag extends NumberRendererTag {
	private static final long serialVersionUID = 1L;
	
	private static final Map<String, String> currencyMap = new HashMap<String, String>();
	static {
		currencyMap.put("USDN","$");
		currencyMap.put("USD", "$");
		currencyMap.put("EUR", "€");
		currencyMap.put("JPY", "¥");
		currencyMap.put("CNY", "¥");
		currencyMap.put("RMB", "¥");
	}
	
	protected Object currency;
	
	protected void displayCurrency(Object currency, JspWriter printer) throws IOException {
		String currencySign = null;
		if (currency != null) {
			currencySign = currencyMap.get(currency.toString());
		}
		if (currencySign == null) currencySign = "$";
		if ("USDN".equals(currency)) setFraction(5);
		
		printer.print(currencySign);
    }
	
    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
    	JspWriter printer = pageContext.getOut();
    	
    	try {
	        if (number != null) {
                if (number instanceof Number) {
                	displayCurrency(currency, printer);
                    displayNumber((Number)number, printer);
                    
                } else if (number instanceof SAPPrice) {
                	SAPPrice price = (SAPPrice)number;
                	displayCurrency(price.getCurrency(), printer);
                	try {
                		displayNumber(price.getRate().divide(price.getConditionPricingUnit(), BigDecimal.ROUND_HALF_UP), printer);
					} catch (Exception e) {
						displayNumber(price.getRate(), printer);
					}
                	
                	
                } else {
                	displayCurrency(currency, printer);
                    printer.print(number.toString());
                }
	        } else {
	        	displayCurrency(currency, printer);
	        }
        } catch (IOException e) {
            throw new JspException("unable to render amount "+e.getMessage(),e);
        }
        return TagSupport.EVAL_PAGE;
    }
    
    public void setCurrency(Object currency) {
		this.currency = currency;
	}
}
