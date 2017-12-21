/**
 * File:TranslationValidator.java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.translations;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bayer.bhc.doc41webui.container.TranslationsForm;

/**
 * Valdator class used by Translation controllers to validate input parameter.
 * 
 * @author ezzqc
 */
public class TranslationValidator implements Validator {

  
    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class<?> clazz) {
        return TranslationsForm.class.isAssignableFrom(clazz);
    }
    
    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object obj, Errors errors) {
        validateJspName(errors);
        validateLanguage(errors);
        validateTagValue(errors);
        validateTagName(errors);
        validateComponent(errors);
        validateCountry(errors);
    }
       
    /**
     * Validates Country code.
     * @param errors  The <code>Errors</code> object to upadte it, if validation fails. 
     */
    private void validateCountry(Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "country", "error.country.required", "Country Code is required.");
        
    }

    
    /**
     * Validates Component code.
     * @param errors  The <code>Errors</code> object to upadte it, if validation fails. 
     */
    private void validateComponent(Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "component", "error.component.required", "Component is required.");
  	}

    
    /**
     * Validates Tag Name.
     * @param errors  The <code>Errors</code> object to upadte it, if validation fails. 
     */
	private void validateTagName(Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tagName", "error.tagname.required", "Tag Name is required.");
	}

       
    /**
     * Validates Tag Value.
     * @param translation The <code>Translation</code> containg input parameters
     * @param errors  The <code>Errors</code> object to upadte it, if validation fails. 
     */
	private void validateTagValue(Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tagValue", "error.tagvalue.required", "Tag Value is required.");
   
    }
       
    /**
     * Validates Jsp Name.
     * @param translation The <code>Translation</code> containg input parameters
     * @param errors  The <code>Errors</code> object to upadte it, if validation fails. 
     */
	private void validateJspName(Errors errors) {
         ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jspName", "error.jspname.required", "Page Name is required.");
         
    }
    
       
    /**
     * Validates Laguage code.
     * @param errors  The <code>Errors</code> object to upadte it, if validation fails. 
     */
	private void validateLanguage(Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "language", "error.language.required", "Laguage Code is required.");
	}
    
}
