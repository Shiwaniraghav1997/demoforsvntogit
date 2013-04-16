/**
 * File:TranslationsDeleteController.Java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.translations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.bayer.ecim.foundation.basic.StringTool;

/**
 * Controller to Delete Translations.
 * 
 * @author MBGHY 
 * Aug 11, 2008
 */
public class TranslationDeleteController extends TranslationsViewController {
    /**
     *  translationsUC spring managed bean The <code>TranslationsUC </code>
     */
    //Constant variables
    private static final String OBJECTID="objectID";
       
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        if (getTagId(request)!=null){
            this.getTranslationsUC().deleteTagById(getTagId(request));
        }
        return redirectOnAbort(request, response);
    }

    /**
     * Extracts The TagId from The request.
     * @param request The<code>HttpServletRequest</code>
     * @return The tagId The<code>Long</code>
     */
    private Long getTagId(HttpServletRequest request) {
        Long tagId=null;
        if (!StringTool.isTrimmedEmptyOrNull(request.getParameter(OBJECTID))){
            tagId=Long.valueOf(request.getParameter(OBJECTID));
        }
        return tagId;
    }
}
