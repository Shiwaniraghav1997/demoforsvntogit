/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.service.mapping;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.domain.Translation;
import com.bayer.ecim.foundation.business.sbcommon.TranslationsDC;

/**
 * TranslationMapper implementation.
 * 
 * @author ezzqc
 */
@Component
public class TranslationsMapper extends AbstractMapper {

	/**
     * Maps the fields form The <code>TranslationDC</code> the <code>Translation</code> DO.
     * @param pTransDC The <code>TranslationDC</code>
     * @return Returns the <code>Translation</code> DO
     */
    public Translation mapToDO(TranslationsDC pTransDC, Translation pTranslation) {
        if(pTransDC != null && pTranslation != null) {
            mapToDomainObject(pTransDC, pTranslation);
            pTranslation.setDcId(pTransDC.getObjectID());
            pTranslation.setComponent(pTransDC.getComponent());
            pTranslation.setCountry(pTransDC.getCountryCode());
            pTranslation.setTagName(pTransDC.getTagName());
            pTranslation.setTagValue(pTransDC.getTagValue());
            pTranslation.setLanguage(pTransDC.getLanguageCode());
            pTranslation.setJspName(pTransDC.getJSPName());
        }
        return pTranslation;

    }

    /**
     * Maps the fields form The <code>Translation</code> domain object to
     *  the <code>TranslationDC</code>.
     * @param pTranslation The <code>Translation</code> DO
     * @return translationDC Returns the <code>TranslationDC</code>
     */
    public TranslationsDC mapToDC(Translation pTranslation, TranslationsDC pTransDC) {
        if(pTransDC != null && pTranslation != null) {
            mapToDataCarrier(pTranslation, pTransDC);
            pTransDC.setComponent(pTranslation.getComponent());
            pTransDC.setCountryCode(pTranslation.getCountry());
            pTransDC.setTagName(pTranslation.getTagName());
            pTransDC.setTagValue(pTranslation.getTagValue());
            pTransDC.setLanguageCode(pTranslation.getLanguage());
            pTransDC.setJSPName(pTranslation.getJspName());
        }
        return pTransDC;
    }
}
