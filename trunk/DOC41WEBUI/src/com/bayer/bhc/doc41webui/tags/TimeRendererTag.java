/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.bayer.bhc.doc41webui.common.util.TimeRenderer;


/**
 * Tag handler class to display a date object in the appropriate time format.
 * 
 * @author ezzqc
 * @id $Id: TimeRendererTag.java,v 1.1 2011/11/22 12:25:59 ezzqc Exp $
 */
public class TimeRendererTag extends TagSupport {

    private static final long serialVersionUID = 8916144555974274966L;
    private Object date;
    private Object zone;
 
    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        //    Locale pLocale = pageContext.getRequest().getLocale();
        
        JspWriter printer = pageContext.getOut();
        try {
            if (date instanceof Date || (date == null)) {
                printer.print(TimeRenderer.formattedTime((Date)this.date, zone));
            } else {
                printer.print(date.toString());
            }        
        } catch (IOException e) {
            throw new JspException("unable to render the time "+e.getMessage(),e);
        }

        return super.doEndTag();
    }

    
    public void setDate(Object date) {
        this.date = date;
    }
    
    public void setZone(Object zone) {
        this.zone = zone;
    }

}
