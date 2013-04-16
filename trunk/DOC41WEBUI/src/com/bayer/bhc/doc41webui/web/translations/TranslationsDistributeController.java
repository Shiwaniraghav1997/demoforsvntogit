/**
 * File:TranslationsDistributeController.java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.translations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.TranslationsUC;
import com.bayer.bhc.doc41webui.web.Doc41Controller;

/**
 * Controller used distribute newly created Translation from QA to Production environment.
 * @author MBGHY
 * Aug 25, 2008
 */
public class TranslationsDistributeController extends Doc41Controller {
    
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
    
    @SuppressWarnings("deprecation")
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    	this.translationsUC.distributeTags();
        
        return super.onSubmit(request, response, command, errors);
    }
}
