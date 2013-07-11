package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

public class StageInfoTag extends TagSupport {
	private static final long serialVersionUID = 9164776760604023479L;
	public static final String TAGS = "tags";
	
	@Override
	public int doEndTag() throws JspException {
		String cwidString	= "";
		String welcome 		= ""; 	
		
		User user = UserInSession.get();
		if (user != null && !StringUtils.isBlank(user.getCwid())) {
			cwidString 	= user.getCwid() + " / ";
			welcome		= String.format("<b>%s %s %s</b>  ", getTranslation("welcome"), user.getFirstname(), user.getSurname()); 
		}
		String stageInfo = welcome + " <<< " + cwidString + ConfigMap.get().getHostId() +" / "+ConfigMap.get().get("common.all.version")+" >>>";
		
		JspWriter printer = pageContext.getOut();
		try {
			printer.print(stageInfo);
		} catch (IOException e) {
			throw new JspException("unable to render StageInfoTag "+e.getMessage());
		}
		
		return EVAL_PAGE;
	}
	
	private String getTranslation(String text) {
		Tags translations = (Tags) pageContext.getRequest().getAttribute(TAGS);
		String transText =  translations.getTag(text);
		return transText;
	}
}
