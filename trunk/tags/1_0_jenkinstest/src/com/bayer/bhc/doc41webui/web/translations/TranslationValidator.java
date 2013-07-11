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
      
        TranslationsForm translation = (TranslationsForm)obj;
        validateJspName(translation, errors);
        validateLaguage(translation, errors);
        validateTagValue(translation, errors);
        validateTagName(translation, errors);
        validateComponent(translation, errors);
        validateCountry(translation, errors);

    }
    
    
       
    /**
     * Validates Country code.
     * @param translation The <code>Translation</code> containg input parameters
     * @param errors  The <code>Errors</code> object to upadte it, if validation fails. 
     */
    private void validateCountry(TranslationsForm translation, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "country", "error.country.required", "Country Code is required.");
        
    }

    
    /**
     * Validates Component code.
     * @param translation The <code>Translation</code> containg input parameters
     * @param errors  The <code>Errors</code> object to upadte it, if validation fails. 
     */
    public void validateComponent(TranslationsForm translation, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "component", "error.component.required", "Component is required.");
  	}

    
    /**
     * Validates Tag Name.
     * @param translation The <code>Translation</code> containg input parameters
     * @param errors  The <code>Errors</code> object to upadte it, if validation fails. 
     */
	public void validateTagName(TranslationsForm translation, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tagName", "error.tagname.required", "Tag Name is required.");
	}

       
    /**
     * Validates Tag Value.
     * @param translation The <code>Translation</code> containg input parameters
     * @param errors  The <code>Errors</code> object to upadte it, if validation fails. 
     */
	public void validateTagValue(TranslationsForm translation, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tagValue", "error.tagvalue.required", "Tag Value is required.");
   
    }
       
    /**
     * Validates Jsp Name.
     * @param translation The <code>Translation</code> containg input parameters
     * @param errors  The <code>Errors</code> object to upadte it, if validation fails. 
     */
	public void validateJspName(TranslationsForm translation, Errors errors) {
         ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jspName", "error.jspname.required", "Page Name is required.");
         
    }
    
       
    /**
     * Validates Laguage code.
     * @param translation The <code>Translation</code> containg input parameters
     * @param errors  The <code>Errors</code> object to upadte it, if validation fails. 
     */
	public void validateLaguage(TranslationsForm translation, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "language", "error.language.required", "Laguage Code is required.");
	}
    
}
