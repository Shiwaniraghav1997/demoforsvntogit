package com.bayer.bhc.doc41webui.web;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.bayer.ecim.foundation.business.sbeanaccess.BATranslationsException;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;


public class Doc41Tags extends Tags {

	private static final long serialVersionUID = 5719632430028215053L;
	
	private static Set<String> untranslatedLabels = new HashSet<String>();

	public Doc41Tags(String pMandant, String pComponent, String pPageName,
			Locale pLocale) throws BATranslationsException {
		super(pMandant, pComponent, pPageName, pLocale);
	}

	public Doc41Tags(String pMandant, String pComponent, String pPageName,
			String pLanguage, String pCountry, boolean pAddWatcher,
			boolean pLoadFromDB, boolean pWarnMissing)
			throws BATranslationsException {
		super(pMandant, pComponent, pPageName, pLanguage, pCountry, pAddWatcher,
				pLoadFromDB, pWarnMissing);
	}

	public Doc41Tags(String pMandant, String pComponent, String pPageName,
			String pLanguage, String pCountry) throws BATranslationsException {
		super(pMandant, pComponent, pPageName, pLanguage, pCountry);
	}
	
	public static Set<String> getUntranslatedLabels() {
		return untranslatedLabels;
	}
	
	@Override
	public String getTagNoEsc(String pTag) {
		String value = super.getTagNoEsc(pTag);
		if(value==null || value.startsWith("[")){
			// memorize untranslated Labels
            getUntranslatedLabels().add(pTag);
		}
		return value;
	}
	
	@Override
	public String getTagNoEsc(String pTag, Object[] values, String[] keys) {
		String value = super.getTagNoEsc(pTag, values, keys);
		if(value==null || value.startsWith("[")){
			// memorize untranslated Labels
            getUntranslatedLabels().add(pTag);
		}
		return value;
	}
}
