package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.ecim.foundation.basic.NestingException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

public class ExceptionRendererTag extends TagSupport {
	
	private static final long serialVersionUID = 2139821757034053006L;
	
	public static final String TAGS = "tags";
	
	private Exception exception;
	
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriter printer = pageContext.getOut();
		User user = UserInSession.get();
    	boolean internalUser =( user!=null && !user.isExternalUser());
		try {
        	if(internalUser){
	        	Tags translations = (Tags) pageContext.getRequest().getAttribute(TAGS);
	        	if(translations!=null){
	        		String tag = translations.getTag(exception.getMessage());
	        		printer.println(tag+"<br>");
	        	}
        	}
        	StringBuilder ticketNumbers = new StringBuilder();
        	StringBuilder messages = new StringBuilder();
        	
        	Throwable ex=exception;
        	while(ex!=null){
        		if(ex instanceof NestingException){
        			NestingException foundationEx = (NestingException) ex;
        			String ticketId = foundationEx.getTicketId();
        			if(!StringTool.isTrimmedEmptyOrNull(ticketId)  && ticketNumbers.indexOf(ticketId)==-1){
        				if(ticketNumbers.length()>0){
        					ticketNumbers.append(",");
        				}
        				ticketNumbers.append(ticketId);
        			}
        		}
        		messages.append(ex.getClass().getSimpleName());
        		messages.append(": ");
        		messages.append(ex.getMessage());
        		messages.append("<br>\n");
        		
        		ex = getNestedException(ex);
        	}
        	
        	//foundationTicketnumbers
        	if(ticketNumbers.length()>0){
        		printer.println(ticketNumbers+"<br>");
        	}
        	if(internalUser){
        		printer.println(messages);
        	} else {
        		printer.println("unexpected exception, please contact support");
        	}
        	
        } catch (IOException e) {
            throw new JspException("unable to render the time "+e.getMessage(),e);
        }
		return super.doEndTag();
	}

	private Throwable getNestedException(Throwable ex) {
		if(ex.getCause()!=null){
			return ex.getCause();
		}
		if(ex instanceof NestingException){
			return ((NestingException)ex).getInternalException();
		}
		return null;
	}
}
