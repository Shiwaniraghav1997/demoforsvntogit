package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.bayer.ecim.foundation.basic.NestingException;
import com.bayer.ecim.foundation.basic.StringTool;

public class ExceptionRendererTag extends TagSupport {
	
	private static final long serialVersionUID = 2139821757034053006L;
	
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
        try {
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
        	printer.println(messages);
        	
        } catch (IOException e) {
            throw new JspException("unable to render the time "+e.getMessage());
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
