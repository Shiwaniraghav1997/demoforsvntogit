/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.container;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

/**
 * 
 * @author ezzqc
 */
public class AbortableCommandForm extends AbstractCommandForm implements AbortableForm {
	private static final long serialVersionUID = 1L;

	public void validate(HttpServletRequest request, Errors errors) {
	}
}
