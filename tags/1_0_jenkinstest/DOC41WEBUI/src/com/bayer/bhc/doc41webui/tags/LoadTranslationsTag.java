/**
 * File:LoadTranslationsTag.java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.tags;

import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.integration.db.TranslationsDAO;
import com.bayer.ecim.foundation.business.sbeanaccess.BATranslationsException;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

/**
 * Tag handler class to load Translations based on component,jspName and user locale.
 * 
 * @author ezzqc
 */
public class LoadTranslationsTag extends TagSupport {

	private static final long serialVersionUID = 2058357521188620090L;

	private static final String MANDANT = TranslationsDAO.SYSTEM_ID;
    
    private static final String TAGS = "tags";

    private String component;

    private String jspName;

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        Locale pLocale = LocaleInSession.get();
        if (pLocale == null) {
            pLocale = pageContext.getRequest().getLocale();
            if (pLocale == null) {
                pLocale = Locale.UK;// Needs to be correct later
            }
        }
        
        Doc41Log.get().debug(
                LoadTranslationsTag.class,
                null,
                "Loading Translations for Compnent " + component + "and Page " + jspName
                        + " of  Locale  " + pLocale);
        try {
            Tags tags = new Tags(MANDANT, component, jspName, pLocale);
//            if (tags == null) {
//                throw new JspException("Unable to get Translation for Compnent " + component
//                        + "and Page " + jspName + " for Locale  " + pLocale);
//            } else {
                pageContext.getRequest().setAttribute(TAGS, tags);
  //          }

        } catch (BATranslationsException e) {
            e.printStackTrace();
//            throw new JspException("Unable to get Translation for Compnent " + component
//                    + "and Page " + jspName + " for Locale  " + pLocale + e.getMessage());

        }
        return super.doEndTag();
    }

    /**
     * @param component the component to set
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * @param pagName the pagName to set
     */
    public void setJspName(String pageName) {
        this.jspName = pageName;
    }

}
