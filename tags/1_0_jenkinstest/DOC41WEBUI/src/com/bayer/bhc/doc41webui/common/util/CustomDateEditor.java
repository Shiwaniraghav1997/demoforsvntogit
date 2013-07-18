package com.bayer.bhc.doc41webui.common.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.springframework.util.StringUtils;

/**
 * public class CustomDateEditor extends PropertyEditorSupport {
 */

	public class CustomDateEditor extends PropertyEditorSupport {
	private final Locale locale;
	private final Long timeZone;


	public CustomDateEditor(Locale locale) {
		this.locale=locale;
		this.timeZone=null;
	}

	public CustomDateEditor(Locale locale, Long userTimeZone) {
		this.locale=locale;
		this.timeZone=userTimeZone;
	}


	/**
	 * Parse the Date from the given text, using the specified DateFormat.
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		}
		else {
			try {
				setValue(DateRenderer.parse(text, this.timeZone, this.locale));
			}
			catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		return (value != null ? DateRenderer.formattedDate(value, this.timeZone, this.locale) : "");
	}

}
