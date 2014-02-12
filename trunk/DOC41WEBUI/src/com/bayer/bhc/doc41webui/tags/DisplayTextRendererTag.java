/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.bayer.ecim.foundation.business.sbcommon.DisplayTextDC;
import com.bayer.ecim.foundation.business.sbcommon.DisplayTextTransformer;

/**
 * Tag handler class to resolve a DisplayText by code and texttype
 * 
 * @author imrol
 * @id $Id: DisplayTextRendererTag.java,v 1.1 2011/11/22 12:25:59 imrol Exp $
 */
public class DisplayTextRendererTag extends TagSupport {

	private static final long serialVersionUID = -234L;

	private Long type;

	private String code;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	public int doEndTag() throws JspException {

		//preferably use the LocaleInSession to display, but seems that Locale of the request is not determined by LocaleInSession
		//so override by setting ENGLISH
		DisplayTextDC tmpDC = DisplayTextTransformer.get().getByType(type,
				code, Locale.ENGLISH);
		String tmpOut;
		if (tmpDC == null) {
			tmpOut = code;
		} else {
			tmpOut = tmpDC.getFormattedHTMLText();
		}
		JspWriter printer = pageContext.getOut();
		try {
			printer.print(tmpOut);
		} catch (IOException e) {
			throw new JspException("unable to render the displaytext "
					+ e.getMessage(),e);
		}
		return super.doEndTag();
	}

	public void setType(Object pType) {
		type = Long.valueOf(String.valueOf(pType));
	}

	public void setCode(Object pCode){
		code=String.valueOf(pCode);
	}
}
