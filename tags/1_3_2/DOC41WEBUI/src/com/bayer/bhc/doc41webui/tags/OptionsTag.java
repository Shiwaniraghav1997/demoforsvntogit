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

import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.ecim.foundation.basic.StringTool;


/**
 * NumberRendererTag.
 * 
 * @author ezzqc
 * @id $Id: NumberRendererTag.java,v 1.1 2011/11/22 12:26:00 ezzqc Exp $
 */
public class OptionsTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	private List<Object> items;
    
    private Object itemCode;
    
    protected boolean ignoreDefault = false;
    
    protected boolean displayOnlyValue = false;
    
    protected boolean displayOption(SelectionItem item, boolean selectionFinished, JspWriter printer) throws IOException {
        if (item != null) {
        	boolean inactive = item.isInactive();
        	boolean isDefaultValue = item.isDefaultValue();
        	String code = item.getCode();
        	String value = item.getLabel();
        	
        	selectionFinished = displayOption(selectionFinished, printer,inactive, isDefaultValue, code, value);
        }
        return selectionFinished;
    }
    


	private boolean displayOption(boolean selectionFinished, JspWriter printer,
			boolean inactive, boolean isDefaultValue, Object code, String value) throws IOException {
	    StringBuilder out = new StringBuilder();
		
		out.append("<option ");
		if (inactive){
		    out.append("disabled ");
		}
		if (!selectionFinished) {
			if (!isIgnoreDefault() && isDefaultValue) {
				out.append("selected ");
			} else if (isEqual(code,itemCode)) {
				out.append("selected ");
				selectionFinished = true;
			}
		}
		
		out.append("value=\"").append(StringTool.escapeHTML(code.toString())).append("\">");
		out.append(StringTool.escapeHTML(value)).append("</option>");
		
		printer.print(out.toString());
		return selectionFinished;
	}
    
    protected boolean isEqual(Object code, Object code2) {
		if(code==null && code2==null){
			return true;
		} else if(code==null || code2==null){
			return false;
		} else {
			return code.toString().equals(code2.toString());
		}
	}

	/* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        if (items != null) {
            JspWriter printer = pageContext.getOut();
            
            try {
            	boolean selectionFinished = false;
                for (Iterator<Object> iter = items.iterator(); iter.hasNext();) {
                    Object listElement = (Object) iter.next();
                    if (listElement instanceof SelectionItem) {
                    	selectionFinished = displayOption((SelectionItem)listElement, selectionFinished, printer);
                    } 
                }
            } catch (IOException e) {
                throw new JspException("unable to render options "+e.getMessage(),e);
            }
        }
        return TagSupport.EVAL_PAGE;
    }

	public List<Object> getItems() {
		return items;
	}
	public void setItems(List<Object> items) {
		this.items = items;
	}
	public Object getItemCode() {
		return itemCode;
	}
	public void setItemCode(Object itemCode) {
		this.itemCode = itemCode;
	}
	public boolean isIgnoreDefault() {
		return ignoreDefault;
	}
	public void setIgnoreDefault(boolean ignoreDefault) {
		this.ignoreDefault = ignoreDefault;
	}
	public void setDisplayOnlyValue(boolean displayOnlyValue) {
		this.displayOnlyValue = displayOnlyValue;
	}
	
}
