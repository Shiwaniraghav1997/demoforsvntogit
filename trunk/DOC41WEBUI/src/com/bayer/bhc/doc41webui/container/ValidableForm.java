/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.container;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

/**
 * Interface for form with validation support.
 * 
 * @author ezzqc
 */
public interface ValidableForm extends Serializable{
    
    void validate(HttpServletRequest request, Errors errors);
}
