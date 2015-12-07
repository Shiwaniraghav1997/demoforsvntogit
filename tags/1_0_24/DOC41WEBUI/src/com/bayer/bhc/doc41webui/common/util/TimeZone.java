/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * TimeZone constants. Matching values in foundation display text table.
 * 
 */
public final class TimeZone {
    
    private TimeZone() {
        // do not construct utility class.
    }
    
    public static final int GMT_M11  = 5;
    public static final int GMT_M10 = 21;
    public static final int GMT_M8  = 35;
    public static final int GMT_M7  = 71;
    public static final int GMT_M6  = 93;
    public static final int GMT_M5  = 126;
    public static final int GMT_M4  = 171;
    public static final int GMT_M3  = 212;
    public static final int GMT_M1  = 219;
    public static final int GMT_4   = 399;
    public static final int GMT_0   = 262;
    public static final int GMT_1   = 293;
    public static final int GMT_2   = 327;
    public static final int GMT_3   = 384;
    public static final int GMT_5   = 424;
    public static final int GMT_6   = 432;
    public static final int GMT_7   = 450;
    public static final int GMT_8   = 460;
    public static final int GMT_9   = 488;
    public static final int GMT_10  = 523;
    public static final int GMT_13  = 554;
    
    public static int gmtOffsetHours(Long pZone) {
        if(null == pZone){
            return 0;
        }
        return gmtOffsetHours(pZone.longValue());
    }
    
    public static int gmtOffsetHours(long pZone) {
        if (pZone == GMT_M11) return -11;
        if (pZone == GMT_M10) return -10;
        if (pZone == GMT_M8) return -8;
        if (pZone == GMT_M7) return -7;
        if (pZone == GMT_M6) return -6;
        if (pZone == GMT_M5) return -5;
        if (pZone == GMT_M4) return -4;
        if (pZone == GMT_M3) return -3;
        if (pZone == GMT_M1) return -1;
        if (pZone == GMT_1) return 1;
        if (pZone == GMT_2) return 2;
        if (pZone == GMT_3) return 3;
        if (pZone == GMT_4) return 4;
        if (pZone == GMT_5) return 5;
        if (pZone == GMT_6) return 6;
        if (pZone == GMT_7) return 7;
        if (pZone == GMT_8) return 8;
        if (pZone == GMT_9) return 9;
        if (pZone == GMT_10) return 10;
        if (pZone == GMT_13) return 13;
        
        return 0;
    }
    
    public static int getOffset(long timeStamp, java.util.TimeZone tz) {
        Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(timeStamp);
        return cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
    }
    
    public static int getOffset(long timeStamp, Long pZone) {
        return getOffset(timeStamp, java.util.TimeZone.getTimeZone(getZoneCode(pZone)));
    }
    
    public static int getOffset(Long pZone) {
        return getOffset(System.currentTimeMillis(), java.util.TimeZone.getTimeZone(getZoneCode(pZone)));
    }
    
    public static Date getZoneDate(Object zoneId) {
        if (zoneId != null) {
            int offset = getOffset(new Long(""+zoneId) );
            return new Date(System.currentTimeMillis()+offset);
        }
        return new Date(System.currentTimeMillis());
    }
    
    public static String getZoneCode(Long pZone) {
        if (null == pZone){
            return "Greenwich";
        }
        return getZoneCode(pZone.longValue());
    }
    
    // Vergleiche DisplayText with TextId = 7:
    public static String getZoneCode(long pZone) {
        if (pZone == 5) return "Pacific/Midway";
        if (pZone == 21) return "US/Hawaii";
        if (pZone == 35) return "America/Los_Angeles";
        if (pZone == 71) return "US/Arizona";
        if (pZone == 93) return "Canada/Central";
        if (pZone == 126) return "America/New_York";
        if (pZone == 171) return "America/Santiago";
        if (pZone == 212) return "Brazil/East";
        if (pZone == 219) return "Atlantic/Azores";
        if (pZone == 262) return "Greenwich";
        if (pZone == 293) return "Europe/Berlin";
        if (pZone == 327) return "Africa/Johannesburg";
        if (pZone == 384) return "Europe/Moscow";
        if (pZone == 399) return "Asia/Dubai";
        if (pZone == 424) return "Asia/Calcutta";
        if (pZone == 432) return "Asia/Dhaka";
        if (pZone == 450) return "Asia/Saigon";
        if (pZone == 460) return "Asia/Hong_Kong";
        if (pZone == 488) return "Asia/Tokyo";
        if (pZone == 523) return "Australia/LHI";
        if (pZone == 554) return "Pacific/Enderbury";
        
        return "Greenwich";
    }
}
