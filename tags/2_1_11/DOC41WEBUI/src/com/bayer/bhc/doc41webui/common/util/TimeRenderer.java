/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.bayer.bhc.doc41webui.common.Doc41ErrorMessageKeys;

/**
 * TimeRenderer. Parses and formats a <code>java.util.Date</code> from/into string with pattern "HH:mm".
 * Only German time format supported.
 * @author ezzqc
 */
public final class TimeRenderer {

    private static final String BLANK = "";

    public static final String GERMAN_TIME_PATTERN = "HH:mm";
    
    private static final SimpleDateFormat TIME_FORMATTER;

    static {
        TIME_FORMATTER = new SimpleDateFormat(GERMAN_TIME_PATTERN);
        // use GMT+0 as base (UTC-0) - compare new Date() !
        TIME_FORMATTER.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private TimeRenderer() {
        // do not construct util class
    }
    
    public static String formattedTime(Date pDate) {
        return formattedTime(pDate, null);
    }
    
    public static synchronized String formattedTime(Date pDate, Object zoneId) {
        if (pDate == null) {
            return BLANK; 
        }
        if (pDate.getTime() == Doc41Utils.MILLIS_ONE_DAY) {
            return "24:00";
        } else {
            if (zoneId != null) {
                int offset = com.bayer.bhc.doc41webui.common.util.TimeZone.getOffset(pDate.getTime(), new Long(""+zoneId) );
                return TIME_FORMATTER.format(new Date(pDate.getTime()+offset));
            } else {
                return TIME_FORMATTER.format(pDate);
            }
        }
    }

    public static synchronized Date parse(String timeString) throws ParseException {
        if (null == timeString || timeString.trim().length() == 0) {
            return null;
        }
        timeString = timeString.trim();
        
        // only H:mm given:
        if (timeString.length() == 4) {
            timeString = "0"+timeString;
        }
        
        Date parsedTime = TIME_FORMATTER.parse(timeString);
        // allow for 24:00:
        if (!timeString.equals(TIME_FORMATTER.format(parsedTime)) && !timeString.equals("24:00")) {
            throw new ParseException(Doc41ErrorMessageKeys.UNPARSABLE_TIME, 0);
        }
        return parsedTime;
    }
    
    public static void main(String[] args) throws ParseException {
        System.out.println("00:00: "+TimeRenderer.parse("00:00").getTime());
        System.out.println("24:00: "+TimeRenderer.parse("24:00").getTime());
        
        System.out.println(formattedTime(new Date(), "293"));
    }
    
}
