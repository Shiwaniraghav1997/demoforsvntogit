/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;

/**
 * Utility class for field operations in DOC41.
 * 
 * @author ezzqc
 */
public final class Doc41Utils {
	public static final long MILLIS_ONE_MINUTE = 60 * 1000;
	public static final long MILLIS_ONE_HOUR = MILLIS_ONE_MINUTE * 60;
	public static final long MILLIS_ONE_DAY = MILLIS_ONE_HOUR * 24;

	private Doc41Utils() {
		// do nothing
	}

	public static String fillNumberField(String field, int fieldLength) {
		if (field != null && field.length() > 0 && field.length() < fieldLength) {
			int prefixLength = fieldLength - field.length();
			StringBuilder prefix = new StringBuilder(prefixLength);
			while (prefix.length() < prefixLength) {
				prefix.append('0');
			}
			field = prefix.toString() + field;
		}
		return field;
	}

	public static long getMillisOfDay(final Date pDate) {
		return pDate.getTime() % (MILLIS_ONE_DAY);
	}

	public static Date withoutMillisOfDay(final Date pDate) {
		if (pDate == null) {
			return null;
		}
		return new Date(pDate.getTime() - (pDate.getTime() % MILLIS_ONE_DAY));
	}

	public static Date generateFromDate(int month, int year, final Locale locale) {
		Calendar cal = new GregorianCalendar(locale);
		cal.set(year, month - 1, 1);
		return cal.getTime();
	}

	public static Date generateToDate(int month, int year, final Locale locale) {
		Calendar cal = new GregorianCalendar(locale);
		cal.set(year, month, 1);
		return cal.getTime();
	}

	public static int getActualYear(final Locale locale) {
		Calendar cal = new GregorianCalendar(locale);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * Converts date to string using format "MM/dd/yyyy".
	 * 
	 * @param date
	 *            - date to convert.
	 * @return string converted.
	 */
	public static String convertDateToString(Date date) {
		return (date != null ? new SimpleDateFormat("MM/dd/yyyy").format(date) : null);
	}

	/**
	 * Converts string using format "MM/dd/yyyy" to date.
	 * 
	 * @param string
	 *            - string to convert.
	 * @return date converted
	 */
	public static Date convertStringToDate(String string) {
		Date date = null;
		try {
			date = new SimpleDateFormat("MM/dd/yyyy").parse(string);
		} catch (ParseException pe) {
			Doc41Log.get().warning(Doc41Utils.class, UserInSession.getCwid(), "String could not be converted to date.");
		}
		return date;
	}
}
