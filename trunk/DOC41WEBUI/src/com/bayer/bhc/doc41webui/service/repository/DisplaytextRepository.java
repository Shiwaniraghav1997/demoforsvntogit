/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.service.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.bhc.doc41webui.integration.db.DisplaytextDAO;
import com.bayer.ecim.foundation.business.sbcommon.DisplayTextDC;

/**
 * Repository implementation to retrieve the display texts shown in comboboxes
 * f.e. from the persistence layer
 * 
 * @author ezzqc
 */
@Component
public class DisplaytextRepository {

	/* not yet used in the application */
	// private static final long TEXTTYPE_SALUTATION = 1;
	// private static final long TEXTTYPE_SEGMENT = 2;
	// private static final long TEXTTYPE_MEMBERSHIP = 3;
	// private static final long TEXTTYPE_LANGUAGE = 5;
	// private static final long TEXTTYPE_POSITION = 6;
	// private static final long TEXTTYPE_REGIONAL_OPERATIONS = 8;
	// private static final long TEXTTYPE_REGISTRATIONSTATE = 9;
	// private static final long TEXTTYPE_OBJECTSTATE = 10;
	// private static final long TEXTTYPE_CURRENCY = 11;

	private static final long TEXTTYPE_COUNTRY = 4;
	private static final long TEXTTYPE_TIMEZONE = 7;

	private static final long TEXTTYPE_FEDERAL_STATE = 20;
	
	// IDs over 20 only
	private static final long TEXTTYPE_MONTH = 32;

	private static final long TEXTTYPE_PRICING_ERROR_STATE = 21;
	private static final long TEXTTYPE_TRANSACTION_TYPE = 22;
	private static final long TEXTTYPE_PRICING_ERROR_ORDER_BY = 23;
	private static final long TEXTTYPE_ADJUSTMENT_REASON = 24;

	private static final String DISPLAYTEXT_TEXT = "text";
	private static final String DISPLAYTEXT_CODE = "code";

	@Autowired
	private DisplaytextDAO displaytextDAO;

	/**
	 * @return the displaytextDAO
	 */
	public DisplaytextDAO getDisplaytextDAO() {
		return displaytextDAO;
	}

	// -------------------------------- Textliste

	public List<SelectionItem> getFederalStates() {

		List<SelectionItem> stateList = new ArrayList<SelectionItem>();

		List<DisplayTextDC> displaytextDCs = getDisplaytextDAO().getDisplaytextList(TEXTTYPE_FEDERAL_STATE);
		Iterator<DisplayTextDC> dispIt = displaytextDCs.iterator();

		while (dispIt.hasNext()) {
			stateList.add(mapToSICode((DisplayTextDC) dispIt.next(), DISPLAYTEXT_TEXT));
		}

		return stateList;
	}
	
	/**
     * Assembles the country display texts according to a given language.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>List</code> containing the country names.
     */
	public List<SelectionItem> getCountries() {

		List<SelectionItem> countryList = new ArrayList<SelectionItem>();

		List<DisplayTextDC> displaytextDCs = getDisplaytextDAO().getDisplaytextList(TEXTTYPE_COUNTRY);
		Iterator<DisplayTextDC> dispIt = displaytextDCs.iterator();

		while (dispIt.hasNext()) {
			countryList.add(mapToSI((DisplayTextDC) dispIt.next(), DISPLAYTEXT_TEXT));
		}

		return countryList;
	}

	/**
     * Assembles the country codes according to a given language.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>List</code> containing the country names.
     */
	public List<SelectionItem> getCountryCodes() {
		List<SelectionItem> countryList = new ArrayList<SelectionItem>();

		List<DisplayTextDC> displaytextDCs = getDisplaytextDAO().getDisplaytextList(
				TEXTTYPE_COUNTRY);
		Iterator<DisplayTextDC> dispIt = displaytextDCs.iterator();

		while (dispIt.hasNext()) {
			countryList.add(mapToSICode((DisplayTextDC) dispIt.next(),
					DISPLAYTEXT_TEXT));
		}

		return countryList;
	}

	/**
     * Assembles the locale codes according to a given language.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>List</code> containing the language names.
     */
	public List<SelectionItem> getLanguageCountryCodes() {

		List<SelectionItem> languageCountryList = new ArrayList<SelectionItem>();

		List<DisplayTextDC> displaytextDCs = getDisplaytextDAO().getLanguageCountryCodes();
		Iterator<DisplayTextDC> dispIt = displaytextDCs.iterator();

		while (dispIt.hasNext()) {
			languageCountryList.add(mapToSICode((DisplayTextDC) dispIt.next(),
					DISPLAYTEXT_TEXT));
		}

		return languageCountryList;
	}



    /**
     * Assembles the timezone display texts according to a given language.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>List</code> containing the timezone names.
     */
	public List<SelectionItem> getTimezones() {
		List<SelectionItem> timezoneList = new ArrayList<SelectionItem>();

		List<DisplayTextDC> displaytextDCs = getDisplaytextDAO().getDisplaytextList(
				TEXTTYPE_TIMEZONE);
		Iterator<DisplayTextDC> dispIt = displaytextDCs.iterator();

		while (dispIt.hasNext()) {
			timezoneList.add(mapToSI((DisplayTextDC) dispIt.next(),
					DISPLAYTEXT_TEXT));
		}

		return timezoneList;
	}

	/**
     * Assembles the timezone code texts according to a given language.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>List</code> containing the timezone names.
     */
	public List<SelectionItem> getTimezoneCodes() {
		List<SelectionItem> timezoneList = new ArrayList<SelectionItem>();

		List<DisplayTextDC> displaytextDCs = getDisplaytextDAO().getDisplaytextList(
				TEXTTYPE_TIMEZONE);
		Iterator<DisplayTextDC> dispIt = displaytextDCs.iterator();

		while (dispIt.hasNext()) {
			timezoneList.add(mapToSI((DisplayTextDC) dispIt.next(),
					DISPLAYTEXT_CODE));
		}

		return timezoneList;
	}

	

	/**
     * Determine the code of a certain timezone according to a given language.
     * @param id        <code>textid</code> of the timezone to search for.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>String</code> containing the timezone code.
     */
	public String getTimezoneCodeById(long id) {
		DisplayTextDC textDC = getDisplaytextDAO().getDisplaytext(
				TEXTTYPE_TIMEZONE, id);

		return textDC.getCode();
	}

	/**
     * Assembles a list of display texts used to represent the months according to a given language.
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @return          A<code>List</code> containing the month display texts.
     */
	public List<SelectionItem> getMonths() {
		List<SelectionItem> mList = new ArrayList<SelectionItem>();

		List<DisplayTextDC> displaytextDCs = getDisplaytextDAO().getDisplaytextList(
				TEXTTYPE_MONTH);
		Iterator<DisplayTextDC> dispIt = displaytextDCs.iterator();

		while (dispIt.hasNext()) {
			mList.add(mapToSI((DisplayTextDC) dispIt.next(), DISPLAYTEXT_TEXT));
		}

		return mList;
	}


	// ----------------------------------------- Mapper
	// ---------------------------------------------------------

	/**
	 * Mapping method retrieves the favoured text from the given
	 * <code>DisplayTextDC</code>.
	 * 
	 * @param textDC
	 *            is the given <code>DisplayTextDC</code>
	 * @param fieldname
	 *            describes the favoured text field. There is the choice
	 *            possible between <code>DisplayTextDC.code</code> or
	 *            <code>DisplayTextDC.text</code>
	 * @return the described text.
	 */
	private SelectionItem mapToSI(DisplayTextDC textDC, String fieldname) {
		SelectionItem si = new SelectionItem();

		si.setValue(textDC.getTextID().longValue() + "");

		if (DISPLAYTEXT_CODE.equals(fieldname)) {
			si.setLabel(textDC.getCode());
		}
		if (DISPLAYTEXT_TEXT.equals(fieldname)) {
			si.setLabel(textDC.getText());
		}
		return si;
	}

	private SelectionItem mapToSICode(DisplayTextDC textDC, String fieldname) {
		SelectionItem si = new SelectionItem();

		si.setValue(textDC.getCode());

		if (DISPLAYTEXT_CODE.equals(fieldname)) {
			si.setLabel(textDC.getCode());
		}
		if (DISPLAYTEXT_TEXT.equals(fieldname)) {
			si.setLabel(textDC.getText());
		}
		return si;
	}

	/**
     * Assembles the display texts for the states a pricing error may have according to a given language
     * @param locale    <code>Locale</code> object representing the language to filter for.
     * @param defaultsOnly if true, only those displaytexts, that are marked as defaults in the database are delivered
     * @return          A<code>List</code> containing the pricing error state descriptions.
     */
	public List<SelectionItem> getPricingErrorStates(boolean defaultsOnly) {
		List<SelectionItem> priceErrorStateList = new ArrayList<SelectionItem>();

		List<DisplayTextDC> displaytextDCs = getDisplaytextDAO()
				.getDisplaytextList(TEXTTYPE_PRICING_ERROR_STATE);
		// if multilingual support is not set up correctly by now - fall back to
		// default locale EN
		if (displaytextDCs.size() == 0) {
			displaytextDCs = getDisplaytextDAO().getDisplaytextListBackend(
					TEXTTYPE_PRICING_ERROR_STATE, Locale.ENGLISH);
		}

		for (Iterator<DisplayTextDC> iterator = displaytextDCs.iterator(); iterator.hasNext();) {
			DisplayTextDC displayTextDC = (DisplayTextDC) iterator.next();
			if (!defaultsOnly || displayTextDC.getIsDefault()) {
				priceErrorStateList.add(mapToSICode(displayTextDC,
						DISPLAYTEXT_TEXT));
			}

		}

		return priceErrorStateList;
	}
	
	public List<SelectionItem> getPricingErrorOrderCriteria(boolean defaultsOnly) {
		List<SelectionItem> priceErrorOrderList = new ArrayList<SelectionItem>();

		List<DisplayTextDC> displaytextDCs = getDisplaytextDAO()
				.getDisplaytextList(TEXTTYPE_PRICING_ERROR_ORDER_BY);
		// if multilingual support is not set up correctly by now - fall back to
		// default locale EN
		if (displaytextDCs.size() == 0) {
			displaytextDCs = getDisplaytextDAO().getDisplaytextListBackend(
					TEXTTYPE_PRICING_ERROR_ORDER_BY, Locale.ENGLISH);
		}

		for (Iterator<DisplayTextDC> iterator = displaytextDCs.iterator(); iterator.hasNext();) {
			DisplayTextDC displayTextDC = (DisplayTextDC) iterator.next();
			if (!defaultsOnly || displayTextDC.getIsDefault()) {
				priceErrorOrderList.add(mapToSICode(displayTextDC,
						DISPLAYTEXT_TEXT));
			}

		}

		return priceErrorOrderList;
	}
	
	public List<SelectionItem> getTransactionTypeList(boolean defaultsOnly) {
		List<SelectionItem> transTypeList = new ArrayList<SelectionItem>();

		List<DisplayTextDC> displaytextDCs = getDisplaytextDAO()
				.getDisplaytextList(TEXTTYPE_TRANSACTION_TYPE);
		// if multilingual support is not set up correctly by now - fall back to
		// default locale EN
		if (displaytextDCs.size() == 0) {
			displaytextDCs = getDisplaytextDAO().getDisplaytextListBackend(
					TEXTTYPE_TRANSACTION_TYPE, Locale.ENGLISH);
		}

		for (Iterator<DisplayTextDC> iterator = displaytextDCs.iterator(); iterator.hasNext();) {
			DisplayTextDC displayTextDC = (DisplayTextDC) iterator.next();
			if (!defaultsOnly || displayTextDC.getIsDefault()) {
				transTypeList.add(mapToSICode(displayTextDC,
						DISPLAYTEXT_TEXT));
			}

		}

		return transTypeList;
	}
	
	public List<SelectionItem> getAdjustmentReasonList(boolean defaultsOnly) {
		List<SelectionItem> adjustmentList = new ArrayList<SelectionItem>();

		List<DisplayTextDC> displaytextDCs = getDisplaytextDAO()
				.getDisplaytextList(TEXTTYPE_ADJUSTMENT_REASON);
		// if multilingual support is not set up correctly by now - fall back to
		// default locale EN
		if (displaytextDCs.size() == 0) {
			displaytextDCs = getDisplaytextDAO().getDisplaytextListBackend(
					TEXTTYPE_ADJUSTMENT_REASON, Locale.ENGLISH);
		}

		for (Iterator<DisplayTextDC> iterator = displaytextDCs.iterator(); iterator.hasNext();) {
			DisplayTextDC displayTextDC = (DisplayTextDC) iterator.next();
			if (!defaultsOnly || displayTextDC.getIsDefault()) {
				adjustmentList.add(mapToSICode(displayTextDC,
						DISPLAYTEXT_TEXT));
			}

		}

		return adjustmentList;
	}
	

}
