/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Tag handler class to convert spaces to nbsp
 * 
 * @author evayd
 * @id $Id: SpaceToNbspRendererTag.java,v 1.1 2011/11/22 12:25:59 imrol Exp $
 */
public class SpaceToNbspRendererTag extends SimpleTagSupport {

	public void doTag() throws JspException, IOException {
		StringWriter sw = new StringWriter();
		getJspBody().invoke(sw);
		String body = sw.toString();
		getJspContext().getOut().println(body.replace(" ", "&nbsp;"));
	}
}
