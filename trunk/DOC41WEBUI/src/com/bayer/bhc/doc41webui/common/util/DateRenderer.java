/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * DateRenderer.
 * 
 * @author ezzqc
 */
public final class DateRenderer {

    private static final String SEPARATOR = " ";

    private static Map<Locale, DateFormat> formatterMap = new HashMap<Locale, DateFormat>();

    public static final String SAP_RFC_TIMESTAMP_PATTERN = "yyyyMMddHHmmss";
    
    public static final String SAP_RFC_DATE_PATTERN = "yyyyMMdd";
    
    public static final String SAP_RFC_ISO8601_GMT_TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'+00:00'";
    
    
    public static final String ENGLISH_GB_DATE_PATTERN = "dd/MM/yyyy";

    public static final String ENGLISH_US_DATE_PATTERN = "MM/dd/yyyy";

    public static final String GERMAN_DATE_PATTERN = "dd.MM.yyyy";

    public static final String INTERNATIONAL_DATE_PATTERN = "yyyy-MM-dd";

    private static final DateFormat GERMAN_DATE_AND_TIME;

    private static final DateFormat GERMAN_DATE;

    private static final DateFormat ENGLISH_GB_DATE;
    
    private static final DateFormat ENGLISH_US_DATE;

    private static final DateFormat INTERNATIONAL_DATE;
    
    public static final long DATE_SHORT_THRESHOLD = -59011632000000L;  // GERMAN_DATE.parse("01.01.0100").getTime()
    
    public static final long DATE_SHORT_LONG_OFFSET = 63114076800000L; // "01.01.2000" minus "01.01.0000"

    static {
        GERMAN_DATE = new SimpleDateFormat(GERMAN_DATE_PATTERN);
        ENGLISH_GB_DATE = new SimpleDateFormat(ENGLISH_GB_DATE_PATTERN);
        ENGLISH_US_DATE = new SimpleDateFormat(ENGLISH_US_DATE_PATTERN);
        INTERNATIONAL_DATE = new SimpleDateFormat(INTERNATIONAL_DATE_PATTERN);
        
        // use GMT+0 as base (UTC-0) - compare new Date() !
        GERMAN_DATE.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
        ENGLISH_GB_DATE.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
        ENGLISH_US_DATE.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
        INTERNATIONAL_DATE.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));

        formatterMap.put(Locale.GERMANY, GERMAN_DATE);
        formatterMap.put(Locale.GERMAN, GERMAN_DATE);
        formatterMap.put(Locale.ENGLISH, ENGLISH_US_DATE);
        formatterMap.put(Locale.UK, ENGLISH_GB_DATE);
        formatterMap.put(Locale.US, ENGLISH_US_DATE);
        formatterMap.put(Locale.CHINESE, INTERNATIONAL_DATE);
        formatterMap.put(Locale.SIMPLIFIED_CHINESE, INTERNATIONAL_DATE);

        GERMAN_DATE_AND_TIME = new SimpleDateFormat(GERMAN_DATE_PATTERN + SEPARATOR
                + TimeRenderer.GERMAN_TIME_PATTERN);
        GERMAN_DATE_AND_TIME.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
    }
    
    private DateRenderer() {
        // do not construct helper classes
    }
    
    public static DateFormat getSAPTimeStampFormatterInstance(){
    	DateFormat df = new SimpleDateFormat(SAP_RFC_TIMESTAMP_PATTERN);
    	df.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
    	return df;
    }

    public static DateFormat getSAPDateFormatterInstance(){
    	DateFormat df = new SimpleDateFormat(SAP_RFC_DATE_PATTERN);
    	df.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
    	return df;
    }

    public static DateFormat getSAPISO8601GMTDateFormatterInstance(){
    	DateFormat df = new SimpleDateFormat(SAP_RFC_ISO8601_GMT_TIMESTAMP_PATTERN);
    	df.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
    	return df;
    }

    private static DateFormat getFormatter(Locale pLocale) {
        // get the right formatter:
        DateFormat df = null;
        if (LocaleInSession.get() != null) {
            df = (DateFormat) formatterMap.get(LocaleInSession.get());
            if (df == null) {
                df = (DateFormat) formatterMap.get(new Locale(LocaleInSession.get().getLanguage()));
            }
        }
        
        if (df == null) {
            df = (DateFormat) formatterMap.get(pLocale);
            if (df == null) {
                df = ENGLISH_US_DATE;
            }
        }
        return df;
    }

    public static String formattedDate(Date pDate,  Locale pLocale) {
        return formattedDate(pDate, null, pLocale);
    }
    
    public static synchronized String formattedDate(Date pDate, Object zoneId, Locale pLocale) {
        if (pDate == null) {
            return "";
        }
        if (zoneId != null) {
            int offset = TimeZone.getOffset(pDate.getTime(), new Long(""+zoneId) );
            return getFormatter(pLocale).format(new Date(pDate.getTime()+offset));
        } else {
            return getFormatter(pLocale).format(pDate);
        }
    }
    
    public static synchronized Date parse(String dateString, Locale pLocale) throws ParseException {
    	return parse(dateString, null, pLocale);
    }
    
    public static synchronized Date parse(String dateString, Object zoneId, Locale pLocale) throws ParseException {
        if (null == dateString || dateString.trim().length() == 0) {
            return null;
        }
        Date parsedDate = getFormatter(pLocale).parse(dateString.trim());;
        
        if (parsedDate.getTime() < DATE_SHORT_THRESHOLD) {
            parsedDate = new Date(parsedDate.getTime() + DATE_SHORT_LONG_OFFSET);
        }
        
        if (zoneId != null) {
            int offset = TimeZone.getOffset(parsedDate.getTime(), new Long(""+zoneId) );
            return new Date(parsedDate.getTime()-offset);
        }
        
        return parsedDate;
    }

    public static synchronized String formatDateAndTime(Date pDate) {
        return GERMAN_DATE_AND_TIME.format(pDate);
    }

    public static synchronized Date parseDateAndTime(String dateString) throws ParseException {
        Date parsedDate = GERMAN_DATE_AND_TIME.parse(dateString);
        
        if (parsedDate.getTime() < DATE_SHORT_THRESHOLD) {
            parsedDate = new Date(parsedDate.getTime() + DATE_SHORT_LONG_OFFSET);
        }
        
        return parsedDate;
    }

    /**
     * Formats date and time.
     * 
     * @param pDate date to be formatted
     * @param pTime time to be formatted.
     * @return date plus separator plus time as string.
     */
    public static synchronized String formatDateAndTime(final Date pDate, final Date pTime) {
        String date = GERMAN_DATE.format(pDate);
        String time = TimeRenderer.formattedTime(pTime);
        String result = date + SEPARATOR + time;
        return result;
    }

    /**
     * Gets the date pattern according to certain locale.
     * Especially used for datepicker javascript.
     * 
     * @param locale
     * @return
     */
    public static String getDatePattern(Locale locale) {
    	
        String retValue = ENGLISH_US_DATE_PATTERN;
        if(Locale.GERMANY.equals(locale)){  retValue = GERMAN_DATE_PATTERN;}
        if(Locale.GERMAN.equals(locale)){   retValue = GERMAN_DATE_PATTERN;}
        if(Locale.ENGLISH.equals(locale)){  retValue = ENGLISH_US_DATE_PATTERN;}
        if(Locale.UK.equals(locale)){       retValue = ENGLISH_GB_DATE_PATTERN;}
        if(Locale.US.equals(locale)){       retValue = ENGLISH_US_DATE_PATTERN;}
        if(Locale.CHINESE.equals(locale)){  retValue = INTERNATIONAL_DATE_PATTERN;}
        if(Locale.SIMPLIFIED_CHINESE.equals(locale)){retValue = INTERNATIONAL_DATE_PATTERN;}
        
        return retValue;
    }
    
    public static void main(String[] args) throws ParseException {
        System.out.println(formatDateAndTime(new Date(), new Date()));
        
        System.out.println(getSAPTimeStampFormatterInstance().parse("20120703100027"));
        System.out.println(getSAPISO8601GMTDateFormatterInstance().parse("2012-07-03T10:00:27+00:00"));
        System.out.println(getSAPISO8601GMTDateFormatterInstance().format(getSAPTimeStampFormatterInstance().parse("20120703100027")));
        System.out.println(getSAPTimeStampFormatterInstance().format(getSAPISO8601GMTDateFormatterInstance().parse("2012-07-03T10:00:27+00:00")));
//        System.out.println(getSAPInputDateFormatterInstance().parse("2012-07-03T10:00:27Z"));
//        System.out.println(getSAPInputDateFormatterInstance().parse("2012-07-03T10:00:27+02:00"));
        
        System.out.println(GERMAN_DATE.parse("01.01.0100").getTime());
        
        System.out.println(GERMAN_DATE.parse("01.01.0000").getTime());
        
        System.out.println(GERMAN_DATE.parse("01.01.2000").getTime()-GERMAN_DATE.parse("01.01.0000").getTime());
        
        System.out.println(GERMAN_DATE.format(new Date(-30609792000000L)));
    }
}
