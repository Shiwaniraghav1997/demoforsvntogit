package com.bayer.bhc.doc41webui.container;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

/**
 * Abstract Cargo form bean.
 * 
 * @author ezzqc
 * $Id: AbstractCommandForm.java,v 1.2 2012/02/22 14:16:09 ezzqc Exp $
 */
public abstract class AbstractCommandForm implements ValidableForm {
	private static final long serialVersionUID = 1L;
	
	protected static final String EMPTY = "";
    
    private String command = "";
    
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
    
    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.domain.container.ValidableForm#validate(javax.servlet.http.HttpServletRequest, org.springframework.validation.Errors)
     */
    public abstract void validate(HttpServletRequest request, Errors errors); 
}
