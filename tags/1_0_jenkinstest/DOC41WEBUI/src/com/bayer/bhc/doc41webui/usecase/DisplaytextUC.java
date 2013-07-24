/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.bhc.doc41webui.service.repository.DisplaytextRepository;


/**
 * Usecase implementation to handle the display texts shown in comboboxes f.e.
 * 
 * @author ezzqc
 */
@Component
public class DisplaytextUC {
	@Autowired
    DisplaytextRepository displaytextRepository;

    /**
     * @return the displaytextRepository
     */
    public DisplaytextRepository getDisplaytextRepository() {
        return displaytextRepository;
    }


    //------------------------------------- Textliste ------------------------------------
    
    public List<SelectionItem> getFederalStates() {
        return getDisplaytextRepository().getFederalStates();
    }
    
    /**
     * Assembles the country display texts according to a given language.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>List</code> containing the country names.
     */
    public List<SelectionItem> getCountries() {
        return getDisplaytextRepository().getCountries();
    }

    /**
     * Assembles the country display texts according to a given language.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>List</code> containing the country names.
     */
    public List<SelectionItem> getCountryCodes() {
        return getDisplaytextRepository().getCountryCodes();
    }
    
    /**
     * Assembles the languages according to a given language.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>List</code> containing the language names.
     */
    public List<SelectionItem> getLanguageCountryCodes() {
        return getDisplaytextRepository().getLanguageCountryCodes();
    }
    

    /**
     * Assembles the timezone display texts according to a given language.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>List</code> containing the timezone names.
     */
    public List<SelectionItem> getTimezones() {
        return getDisplaytextRepository().getTimezones();
    }

    /**
     * Assembles the timezone code texts according to a given language.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>List</code> containing the timezone names.
     */
    public List<SelectionItem> getTimezoneCodes() {
        return getDisplaytextRepository().getTimezoneCodes();
    }
    
    /**
     * Determine the code of a certain timezone according to a given language.
     * @param id        <code>textid</code> of the timezone to search for.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>String</code> containing the timezone code.
     */
    public String getTimezoneCode(long id) {
        return getDisplaytextRepository().getTimezoneCodeById(id);
    }


    /**
     * Assembles a list of display texts used to represent the months according to a given language.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>List</code> containing the month display texts.
     */
    public List<SelectionItem> getMonths() {
        return getDisplaytextRepository().getMonths();
    }

}
