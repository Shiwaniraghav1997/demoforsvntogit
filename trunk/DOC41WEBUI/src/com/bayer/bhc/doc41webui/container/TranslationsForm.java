/**
 * File:TranslationsView.Java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.container;

import com.bayer.bhc.doc41webui.integration.db.TranslationsDAO;


/**
 * Translations View Bean.
 * @author ezzqc
 */
public class TranslationsForm  {
	private static final long serialVersionUID = 1L;
	
	private String component="*";
	private String jspName="*";
	private String tagName;
	private String tagValue;
	private String country;
	private String language;
	private String objectID;
	private String orderBy;
	
	private String mandant=TranslationsDAO.SYSTEM_ID;
	

 	
	/**
	 * @return the componentName
	 */
	public String getComponent() {
		return component;
	}
	/**
	 * @param componentName the componentName to set
	 */
	public void setComponent(String componentName) {
		this.component = componentName;
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
	/**
	 * @return the objectID
	 */
	public String getObjectID() {
		return objectID;
	}
	/**
	 * @param objectID the objectID to set
	 */
	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}
	
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
    
    public void reset() {
    	this.tagName=null;
    	this.tagValue=null;
        this.component=null;
        this.jspName=null;
        this.language=null;
        this.orderBy=null;
   }


}
