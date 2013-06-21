/**
 * File:TranslationPagingRequest.java
 *(C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.container;

import com.bayer.bhc.doc41webui.common.paging.PagingData;
import com.bayer.bhc.doc41webui.common.paging.PagingRequest;


/**
 * Translations Paging Request object.
 * 
 * @author mbghy
 * @id $Id: TranslationPagingRequest.java,v 1.1 2012/03/01 14:46:42 ezzqc Exp $
 */
public class TranslationPagingRequest extends PagingRequest {

    
    private String component;
    private String jspName;
    private String language;
    private String countryCode;
    private String tagName;
    private String tagValue;
    
    /**
     * TranslationPagingRequest constructor .
     * @param pFilter The <code>TranslationsForm</code>
     * @param pForm The <code>PagingForm</code>
     */
    public TranslationPagingRequest(final TranslationsForm pFilter, final PagingData pagingData) {
        super(pagingData);
        this.component=pFilter.getComponent();
        this.jspName=pFilter.getJspName();
        this.language=pFilter.getLanguage();
        this.countryCode=pFilter.getCountry();
        this.tagName=pFilter.getTagName();
        this.tagValue=pFilter.getTagValue();
        setOrderBy(pFilter.getOrderBy());
    }
    
    /**
     * @return the component
     */
    public String getComponent() {
        return component;
    }
    /**
     * @return the jspName
     */
    public String getJspName() {
        return jspName;
    }
    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @return the tagValue
	 */
	public String getTagValue() {
		return tagValue;
	}
}
