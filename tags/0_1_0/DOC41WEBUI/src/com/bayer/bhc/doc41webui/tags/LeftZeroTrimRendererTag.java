/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.bayer.ecim.foundation.basic.StringTool;

/**
 * Tag handler class to trim of leading zeros from String
 * 
 * @author imrol
 * @id $Id: LeftZeroTrimRendererTag.java,v 1.1 2011/11/22 12:25:59 imrol Exp $
 */
public class LeftZeroTrimRendererTag extends TagSupport {

	private static final long serialVersionUID = -234L;

	private String value;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	public int doEndTag() throws JspException {

		if (value != null) {
			String tmpOut = StringTool.ltrim(value, "0");
			JspWriter printer = pageContext.getOut();
			try {
				printer.print(tmpOut);
			} catch (IOException e) {
				throw new JspException(
						"unable to render the trimmed text value "
								+ e.getMessage());
			}
		}
		return super.doEndTag();
	}

	public void setValue(Object pValue) {
		if (pValue != null) {
			value = String.valueOf(pValue);
		} else {
			value = null;
		}
	}
}
