package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

public class NavigationEntryTag extends TagSupport {
	private static final long serialVersionUID = 954926899527633527L;
	public static final String TAGS = "tags";

	private String id;
	private String href;
	private String title;
	private String cssclass;
	private String permission;
	
	private void displayEntry() throws IOException {
		
		String tmpCSSClass		= StringUtils.trim("leftNavigation " + cssclass);
		String tmpHref			= pageContext.getServletContext().getContextPath() + "/" + href;
		String tmpActiveNav		= (String)pageContext.getAttribute("activeNav");
		String tmpActNavAttr	= StringUtils.equals(tmpActiveNav, id) ? " class=\"active\"" : "";
		String tmpTitle			= getTranslation(title);
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("<li class=\"%s\">", tmpCSSClass));
		sb.append(String.format("<a title=\"%s\" href=\"%s\"%s>%s</a>", tmpTitle, tmpHref, tmpActNavAttr, tmpTitle));
		sb.append("</li>");
		
		JspWriter printer = pageContext.getOut();
		printer.print(sb.toString());
	}
	
	private boolean hasPermission() {
		if (StringUtils.isBlank(permission)) {
			return true;
		} else {
			// TODO implement permission check
			System.err.println("TODO implement permission check");
			return true;
		}
	}
	
	private String getTranslation(String title) {
		Tags translations = (Tags) pageContext.getRequest().getAttribute(TAGS);
		String transText =  translations.getTag(title);
		return transText;
	}
	
	@Override
	public int doEndTag() throws JspException {
       try {
    	   if (hasPermission()) {
    		   displayEntry();
    	   }
        } catch (Exception e) {
            throw new JspException("unable to render navigation entry "+e.getMessage());
        }
        return super.doEndTag();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCssclass() {
		return cssclass;
	}

	public void setCssclass(String cssclass) {
		this.cssclass = cssclass;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}
