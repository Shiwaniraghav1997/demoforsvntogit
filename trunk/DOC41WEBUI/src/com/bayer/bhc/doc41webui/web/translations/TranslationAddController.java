/**
 * File:TranslationAddController.java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.translations;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bayer.bhc.doc41webui.common.Doc41ErrorMessageKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.container.TranslationsForm;
import com.bayer.bhc.doc41webui.domain.Translation;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.TranslationsUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class TranslationAddController extends AbstractDoc41Controller {
   
    private static final String LANGUAGE_CODES = "languageCodes";
    private static final String COUNTRY_CODES = "countryCodes";

    private String lastUsedLanguage = "";
    
    @Autowired
    private TranslationsUC translationsUC;
  
    @ModelAttribute(LANGUAGE_CODES)
	public Map<String, String> addLanguageCodes(){
		return translationsUC.getLanguageCodes();
	}
    @ModelAttribute(COUNTRY_CODES)
	public Map<String, String> addCountryCodes(){
		return translationsUC.getCountryCodes();
	}
    
    protected boolean hasRolePermission(User usr) {
    	return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
    }
    
    @RequestMapping(value="/translations/translationAdd",method = RequestMethod.GET)
    public TranslationsForm get() throws Doc41ExceptionBase {
		TranslationsForm translationForm = new TranslationsForm();
    	translationForm.setLanguage(lastUsedLanguage);
    	return translationForm;
    }
    
    @RequestMapping(value="/translations/inserttranslation",method = RequestMethod.POST)
    public String save(@ModelAttribute TranslationsForm translationForm, BindingResult result) throws Doc41ExceptionBase{
		new TranslationValidator().validate(translationForm, result);
    	if (result.hasErrors()) {
    		return "/translations/translationAdd";
        }
		
        isTagExist(translationForm);
        lastUsedLanguage = translationForm.getLanguage();
        this.translationsUC.createTag(getTranslation(translationForm));

        return "redirect:/translations/translationOverview";
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
