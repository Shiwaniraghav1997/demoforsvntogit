package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class PagerTag extends TagSupport {
	private static final long serialVersionUID = 9164776760604023479L;
	
	@Override
	public int doEndTag() throws JspException {
		String contextPath = pageContext.getServletContext().getContextPath();
		JspWriter printer = pageContext.getOut();
		try {
			printer.println("<div class='pager'>");
			printer.println("	<img src='"+contextPath+"/resources/img/tablesorter/first.png' class='first' alt='First' title='First page' />");
			printer.println("	<img src='"+contextPath+"/resources/img/tablesorter/prev.png' class='prev' alt='Prev' title='Previous page' />");
			printer.println("	<span class='pagedisplay'></span> <!-- this can be any element, including an input -->");
			printer.println("	<img src='"+contextPath+"/resources/img/tablesorter/next.png' class='next' alt='Next' title='Next page' />");
			printer.println("	<img src='"+contextPath+"/resources/img/tablesorter/last.png' class='last' alt='Last' title= 'Last page' />");
			printer.println("	<select class='pagesize'>");
			printer.println("		<option selected='selected' value='10'>10</option>");
			printer.println("		<option value='20'>20</option>");
			printer.println("		<option value='30'>30</option>");
			printer.println("		<option value='40'>40</option>");
			printer.println("	</select>");
			printer.println("</div>");
		} catch (IOException e) {
			throw new JspException("unable to render PagerTag "+e.getMessage());
		}
		
		return EVAL_PAGE;
	}
	

}
