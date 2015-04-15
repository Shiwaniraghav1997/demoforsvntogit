/**
 * File:Translaiton.java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.domain;

import com.bayer.bhc.doc41webui.integration.db.TranslationsDAO;


/**
 * Translation domain object.
 * @author ezzqc
 * 
 */
public class Translation extends DomainObject {
	private static final long serialVersionUID = 7723485338991408597L;

	private String mandant=TranslationsDAO.SYSTEM_ID;
    private String component;
    private String jspName;
    private String tagName;
    private String tagValue;
    private String country;
    private String language;
    
    /**
     * @return the componentName
     */
    public String getComponent() {
        return component;
    }
    /**
     * @param componentName the componentName to set
     */
    public void setComponent(String component) {
        this.component = component;
    }
    /**
     * @return the jspName
     */
    public String getJspName() {
        return jspName;
    }
    /**
     * @param jspName the jspName to set
     */
    public void setJspName(String jspName) {
        this.jspName = jspName;
    }
    /**
     * @return the tagName
     */
    public String getTagName() {
        return tagName;
    }
    /**
     * @param tagName the tagName to set
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    /**
     * @return the tagValue
     */
    public String getTagValue() {
        return tagValue;
    }
    /**
     * @param tagValue the tagValue to set
     */
    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }
    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }
    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }
    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }
    /**
     * @return the mandant
     */
    public String getMandant() {
        return mandant;
    }
    /**
     * @param mandant the mandant to set
     */
    public void setMandant(String mandant) {
        this.mandant = mandant;
    }
}
