package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

public class TopNavigationEntryTag extends TagSupport {
	private static final long serialVersionUID = 954926899527633527L;
	public static final String TAGS = "tags";

	private String id;
	private String href;
	private String title;
	private String cssclass;
	private String permission;
	
	private void displayStartTag() throws IOException {
		
		String tmpActiveTopNav	= (String)pageContext.getAttribute("activeTopNav");
		String tmpCSSClass		= StringUtils.trim(StringUtils.trimToEmpty(cssclass) + (StringUtils.equals(tmpActiveTopNav, id) ? " active" : ""));
		String tmpActTopNavAttr	= StringUtils.isBlank(tmpCSSClass) ? "" : " class=\""+tmpCSSClass+"\"";
		String tmpHref			= pageContext.getServletContext().getContextPath() + "/" + href;
		String tmpTitle			= getTranslation(title);
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("<li%s>", tmpActTopNavAttr));
		sb.append(String.format("<a title=\"%s\" href=\"%s\">%s</a>", tmpTitle, tmpHref, tmpTitle));
		if (!StringUtils.contains(cssclass, "home")) {
			sb.append("<ul>");
		}
		
		JspWriter printer = pageContext.getOut();
		printer.print(sb.toString());
	}
	
	private void displayEndTag() throws IOException {
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.contains(cssclass, "home")) {
			sb.append("</ul>");
		}
		sb.append("</li>");		
		JspWriter printer = pageContext.getOut();
		printer.print(sb.toString());
	}
	
	private boolean hasPermission() {
		if (StringUtils.isBlank(permission)) {
			return true;
		} else if (UserInSession.get() == null) {
			return false;
		} else {
			String tmpPermission = StringUtils.replace(permission, ",", " ");
			String[] permissions = StringUtils.split(tmpPermission);
			boolean hasPermission = UserInSession.get().hasPermission(permissions);
			return hasPermission;
		}
	}
	
	private String getTranslation(String title) {
		Tags translations = (Tags) pageContext.getRequest().getAttribute(TAGS);
		String transText =  translations.getTag(title);
		return transText;
	}
	
	@Override
	public int doStartTag() throws JspException {
		try {
	    	   if (hasPermission()) {
	    		   displayStartTag();
	    		   return EVAL_PAGE;
	    	   } else {
	    		   return SKIP_BODY;
	    	   }
	        } catch (Exception e) {
	            throw new JspException("unable to render navigation entry "+e.getMessage());
	        }
	}
	
	@Override
	public int doEndTag() throws JspException {
       try {
    	   if (hasPermission()) {
    		   displayEndTag();
    	   }
        } catch (Exception e) {
            throw new JspException("unable to render navigation entry "+e.getMessage());
        }
        return EVAL_PAGE;
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
