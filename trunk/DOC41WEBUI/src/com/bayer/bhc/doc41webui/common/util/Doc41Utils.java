/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Utility class for field operations in DOC41.
 * 
 * @author ezzqc
 */
public final class Doc41Utils {
    
    private Doc41Utils() {
        // do nothing
    }
    
    public static final long MILLIS_ONE_MINUTE = 60 * 1000;
    
    public static final long MILLIS_ONE_HOUR = MILLIS_ONE_MINUTE * 60;
    
    public static final long MILLIS_ONE_DAY = MILLIS_ONE_HOUR * 24;
    
    public static String fillNumberField(String field, int fieldLength) {
        
        if (field != null && field.length() > 0 && field.length() < fieldLength) {
            
            int prefixLength = fieldLength-field.length();
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
        if (pDate == null){
            return null;
        }
        return new Date(pDate.getTime() - (pDate.getTime() % MILLIS_ONE_DAY));
    }
    
    public static Date generateFromDate(int month, int year, final Locale locale){
        Calendar cal = new GregorianCalendar(locale);
        cal.set(year, month-1, 1);
        return cal.getTime();   
    }
    
    public static Date generateToDate(int month, int year, final Locale locale){
        Calendar cal = new GregorianCalendar(locale);
        cal.set(year, month, 1);
        return cal.getTime();   
    }  
    
    public static int getActualYear(final Locale locale){
        Calendar cal = new GregorianCalendar(locale);
        return cal.get(Calendar.YEAR);   
    } 
}
