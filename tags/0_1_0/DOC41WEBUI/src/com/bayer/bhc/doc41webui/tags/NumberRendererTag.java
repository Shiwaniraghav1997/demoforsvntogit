/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.bayer.bhc.doc41webui.common.util.NumberRenderer;


/**
 * NumberRendererTag.
 * 
 * @author ezzqc
 * @id $Id: NumberRendererTag.java,v 1.1 2011/11/22 12:26:00 ezzqc Exp $
 */
public class NumberRendererTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
    protected Object number;
    
    protected Integer fraction;
    
    protected List<Object> uomList;
    
    protected void displayNumber(Number num, JspWriter printer) throws IOException {
        if (fraction != null) {
            printer.print(NumberRenderer.format(num, pageContext.getRequest().getLocale(), fraction.intValue()));
        } else {
            printer.print(NumberRenderer.format(num, pageContext.getRequest().getLocale()));
        }
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        if (number != null) {
            JspWriter printer = pageContext.getOut();
            
            try {
                if (number instanceof List) {
                    String separator = "";
                    Iterator<Object> uomIter = uomList.iterator();
                    
                    for (@SuppressWarnings("unchecked")
					Iterator<Object> iter = ((List<Object>)number).iterator(); iter.hasNext();) {
                        Object listElement = (Object) iter.next();
                        if (listElement instanceof Number) {
                            printer.print(separator);
                            displayNumber((Number)listElement, printer);
                            printer.print(" "+uomIter.next().toString());
                            separator = ", ";
                        }
                    }
                } else if (number instanceof Number) {
                    displayNumber((Number)number, printer);
                } else {
                    printer.print(number.toString());
                }
            } catch (IOException e) {
                throw new JspException("unable to render numbers "+e.getMessage());
            }
        }
        return TagSupport.EVAL_PAGE;
    }

    public Object getNumber() {
        return number;
    }

    public void setNumber(Object number) {
        this.number = number;
    }

    public Integer getFraction() {
        return fraction;
    }

    public void setFraction(Integer fraction) {
        this.fraction = fraction;
    }

    public List<Object> getUomList() {
        return uomList;
    }

    public void setUomList(List<Object> uomList) {
        this.uomList = uomList;
    }
}
