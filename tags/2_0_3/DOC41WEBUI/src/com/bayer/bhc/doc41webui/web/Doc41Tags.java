package com.bayer.bhc.doc41webui.web;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.business.sbeanaccess.BATranslationsException;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;


public class Doc41Tags extends Tags {

	private static final long serialVersionUID = 5719632430028215053L;
	
	private static Map<String,Set<String>> untranslatedLabels = new HashMap<String,Set<String>>();
	
	private String language;

	public Doc41Tags(String pMandant, String pComponent, String pPageName,
			Locale pLocale) throws BATranslationsException {
		super(pMandant, pComponent, pPageName, pLocale);
		language = pLocale.getLanguage();
	}
	
	public static Map<String, Set<String>> getUntranslatedLabels() {
		return untranslatedLabels;
	}
	
	public static void addLabel(String language,String label){
		Set<String> labelsForLanguage = untranslatedLabels.get(language);
		if(labelsForLanguage==null){
			labelsForLanguage = new TreeSet<String>();
			untranslatedLabels.put(language, labelsForLanguage);
		}
		labelsForLanguage.add(label);
	}
	
	@Override
	public String getTagNoEsc(String pTag) {
		String value = getTagNoEscNoUntranslatedMonitor(pTag);
		if(value==null || value.startsWith("[")){
			// memorize untranslated Labels
            addLabel(language,pTag);
		}
		return value;
	}

	public String getTagNoEscNoUntranslatedMonitor(String pTag) {
		return super.getTagNoEsc(pTag);
	}
	
	@Override
	public String getTagNoEsc(String pTag, Object[] values, String[] keys) {
		String value = super.getTagNoEsc(pTag, values, keys);
		if(value==null || value.startsWith("[")){
			// memorize untranslated Labels
			addLabel(language,pTag);
		}
		return value;
	}
	
	public String getTagNoUntranslatedMonitor(String pTag) {
		return StringTool.escapeHTML(getTagNoEscNoUntranslatedMonitor(pTag));
	}
	
	public String getTagJSNoUntranslatedMonitor(String pTag, Object[] values, String[] keys) {
        return StringTool.escapeJS(super.getTagNoEsc(pTag, values, keys));
    }
}
