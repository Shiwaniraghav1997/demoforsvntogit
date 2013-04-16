/**
 * File:TranslaitonEditController.java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.translations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.TranslationsForm;
import com.bayer.bhc.doc41webui.domain.Translation;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.TranslationsUC;
import com.bayer.bhc.doc41webui.web.Doc41Controller;
import com.bayer.ecim.foundation.basic.StringTool;

/**
 * Controller to handle Translations edit.
 * @author ezzqc
 * 
 */
public class TranslationEditController extends Doc41Controller {

    //Constant variables
    private static final String OBJECTID = "objectID";
    private static final String COMMAND = "transView";
    private static final String LANGUAGE_CODES = "languageCodes";
    private static final String COUNTRY_CODES = "countryCodes";

   
    /**
     *  translationsUC spring managed bean The <code>TranslationsUC </code>
     */
    TranslationsUC translationsUC;

    /**
     * @param translationsUC the translationsUC to set
     */
    public void setTranslationsUC(TranslationsUC translationsUC) {
        this.translationsUC = translationsUC;
    }
    
    protected boolean hasRolePermission(User usr) {
    	return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
    }

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    	TranslationsForm form = (TranslationsForm) command;
        this.translationsUC.editTag(getTranslation(form));
        
        //return super.onSubmit(request, response, command, errors);
        return redirectOnAbort(request, response);
    }

    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        TranslationsForm command = (TranslationsForm) request.getSession().getAttribute(COMMAND);
        // if session form is null create new one and store in session.
        if (command == null) {
            command = new TranslationsForm();
            request.getSession().setAttribute(COMMAND, command);
        }
        if (getTagId(request)!=null) {
             command=getTranslationForm(this.translationsUC.getTagById(getTagId(request)));
        }
        command.setCommand("");
        return command;
    }
    
    @SuppressWarnings("deprecation")
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.handleRequestInternal(request, response)
        		.addObject(LANGUAGE_CODES,this.translationsUC .getLanguageCodes())
        			.addObject(COUNTRY_CODES,this.translationsUC .getCountryCodes());
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
    
    /**
     * Extracts The TagId from The request.
     * @param request The<code>HttpServletRequest</code>
     * @return The tagId The<code>Long</code>
     */
    private Long getTagId(HttpServletRequest request) {
        Long tagId=null;
        if(!StringTool.isTrimmedEmptyOrNull(request.getParameter(OBJECTID))){
            tagId=Long.valueOf(request.getParameter(OBJECTID));
        }
        return tagId;
    }
}
