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

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.SAPPrice;

/**
 * AmountRendererTag.
 * 
 * @author ezzqc
 */
public class AmountRendererTag extends NumberRendererTag {
	private static final long serialVersionUID = 1L;
	
	private static final Map<String, String> CURRENCY_MAP = new HashMap<String, String>();
	static {
		CURRENCY_MAP.put("USDN","$");
		CURRENCY_MAP.put("USD", "$");
		CURRENCY_MAP.put("EUR", "€");
		CURRENCY_MAP.put("JPY", "¥");
		CURRENCY_MAP.put("CNY", "¥");
		CURRENCY_MAP.put("RMB", "¥");
	}
	
	protected Object currency;
	
	protected void displayCurrency(Object currency, JspWriter printer) throws IOException {
		String currencySign = null;
		if (currency != null) {
			currencySign = CURRENCY_MAP.get(currency.toString());
		}
		if (currencySign == null){
		    currencySign = "$";
		}
		if ("USDN".equals(currency)){
		    setFraction(5);
		}
		
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
					} catch (IOException e) {
					    Doc41Log.get().error(getClass(), UserInSession.getCwid(), e);
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
