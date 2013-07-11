/**
 * File:TranslaitonEditController.java
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
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.TranslationsForm;
import com.bayer.bhc.doc41webui.domain.Translation;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.TranslationsUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class TranslationEditController extends AbstractDoc41Controller {

    //Constant variables
    private static final String OBJECTID = "objectID";
    private static final String LANGUAGE_CODES = "languageCodes";
    private static final String COUNTRY_CODES = "countryCodes";

   
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
    
    @RequestMapping(value="/translations/translationEdit",method = RequestMethod.GET)
    public TranslationsForm get(@RequestParam(value=OBJECTID) Long tagId) throws Doc41ExceptionBase {
		Translation translation = this.translationsUC.getTagById(tagId);
		TranslationsForm translationForm = getTranslationForm(translation);
		return translationForm;
    }
    
    @RequestMapping(value="/translations/updatetranslation",method = RequestMethod.POST)
    public String save(@ModelAttribute TranslationsForm translationForm, BindingResult result) throws Doc41ExceptionBase{
		new TranslationValidator().validate(translationForm, result);
    	if (result.hasErrors()) {
    		return "/translations/translationEdit";
        }
		
        this.translationsUC.editTag(getTranslation(translationForm));	

        return "redirect:/translations/translationOverview";
    }

    
    protected boolean hasPermission(User usr) {
    	return usr.hasPermission(Doc41Constants.PERMISSION_BUSINESS_ADMIN, Doc41Constants.PERMISSION_TECHNICAL_ADMIN);
    }

    /**
     * Fills domain object with input params from command object.
     * @param translationsCommand               The <code>TranslationsForm</code>
     * @return translations                      The <code>Translations</code>
     */
    private Translation getTranslation(TranslationsForm translationsCommand) {
        Translation translation=new Translation();
        //Map from TranslationFORM to Translation  domain Object
        translation.setDcId(Long.valueOf(translationsCommand.getObjectID()));
        translation.setTagName(translationsCommand.getTagName());
        translation.setComponent(translationsCommand.getComponent());
        translation.setTagValue(translationsCommand.getTagValue());
        translation.setCountry(translationsCommand.getCountry());
        translation.setLanguage(translationsCommand.getLanguage());
        translation.setJspName(translationsCommand.getJspName());
        translation.setChangedBy(UserInSession.getCwid());
        return translation;
    }
    
    /**
     * Maps from Translation DO to TranslationForm.
     * @param translation   The <code>Translation</code>
     * @return command       The <code>TranslationsForm</code>
     */
    private TranslationsForm getTranslationForm(Translation translation) {
        TranslationsForm command = new TranslationsForm();
        // Map from Translation DO to TranslationForm
        command.setObjectID(translation.getDcId().toString());
        command.setJspName(translation.getJspName());
        command.setCountry(translation.getCountry());
        command.setLanguage(translation.getLanguage());
        command.setComponent(translation.getComponent());
        command.setTagName(translation.getTagName());
        command.setTagValue(translation.getTagValue());
        return command;
    }
    
}
