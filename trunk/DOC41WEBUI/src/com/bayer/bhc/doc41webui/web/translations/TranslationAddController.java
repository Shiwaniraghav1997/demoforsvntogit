/**
 * File:TranslationAddController.java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.translations;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.Doc41ErrorMessageKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.container.TranslationsForm;
import com.bayer.bhc.doc41webui.domain.Translation;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.TranslationsUC;
import com.bayer.bhc.doc41webui.web.Doc41Controller;

/**
 * Controller to Add new Translatins .
 * @author ezzqc
 * 
 */
public class TranslationAddController extends Doc41Controller {
   
    private static final String LANGUAGE_CODES = "languageCodes";
    private static final String COUNTRY_CODES = "countryCodes";

    private String lastUsedLanguage = "";
    
    /**
     *  translationsUC spring managed bean The <code>TranslationsUC </code>
     */
    private TranslationsUC translationsUC;
  
    /**  @param translationsUC the translationsUC to set
     */
    public void setTranslationsUC(TranslationsUC translationsUC) {
        this.translationsUC = translationsUC;
    }
    
    protected boolean hasRolePermission(User usr) {
    	return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
    }
    
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	TranslationsForm f = new TranslationsForm();
    	f.setLanguage(lastUsedLanguage);
    	return f;
    }
    
    @SuppressWarnings("deprecation")
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.handleRequestInternal(request, response)
        		.addObject(LANGUAGE_CODES,this.translationsUC .getLanguageCodes())
        			.addObject(COUNTRY_CODES,this.translationsUC .getCountryCodes());
    }
    
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        TranslationsForm form = (TranslationsForm) command;
        
        isTagExist(form);
        lastUsedLanguage = form.getLanguage();
        this.translationsUC.createTag(getTranslation(form));
        
        //return super.onSubmit(request, response, command, errors);
        return redirectOnAbort(request, response);
    }

    /**
     * To prevent duplicate entry, check tag existence before create.
     * @param form    The <code>TranslationsForm </code>
     * @return retutns true if tag exist already else, false.
     * @throws The <code>Doc41ExceptionBase</code> to be thrown when error occurs
     */
    private void isTagExist(TranslationsForm form) throws Doc41ExceptionBase {
        if (this.translationsUC.isTagExist(getTranslation(form)) > 0) {
            throw new Doc41BusinessException(Doc41ErrorMessageKeys.TRANS_TAG_EXISTS);
        }
    }

    /**
     * Fills domain object with input params from command object.
     * 
     * @param translationsCommand The <code>TranslationsForm</code>
     * @return translation The <code>Translation</code>
     */
    private Translation getTranslation(TranslationsForm translationsCommand) {
        Translation translation = new Translation();
        translation.setTagName(translationsCommand.getTagName());
        translation.setComponent(translationsCommand.getComponent());
        translation.setTagValue(translationsCommand.getTagValue());
        translation.setCountry(translationsCommand.getCountry());
        translation.setLanguage(translationsCommand.getLanguage());
        translation.setJspName(translationsCommand.getJspName());
        return translation;
    }

 
}
