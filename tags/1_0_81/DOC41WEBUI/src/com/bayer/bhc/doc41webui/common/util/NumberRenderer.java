/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


/**
 * NumberRenderer. Renders numbers dependent on locale setting.
 * 
 * @author ezzqc
 */
public final class NumberRenderer {

    /** maximal 3 fractio digits. */
    private static final int DEFAULT_MAX_FRACTION_DIGITS = 3;

    private NumberRenderer() {
        // do not construct helper classes
    }

    public static NumberFormat getFormatter(Locale pLocale) {
        // get the right formatter:
        NumberFormat nf = null;
        if (LocaleInSession.get() != null) {
            nf = NumberFormat.getNumberInstance(LocaleInSession.get());
            if (nf == null) {
                nf = NumberFormat.getNumberInstance(new Locale(LocaleInSession.get().getLanguage()));
            }
        }
        if (nf == null) {
            nf = NumberFormat.getNumberInstance(pLocale);
            if (nf == null) {
                nf = NumberFormat.getNumberInstance(Locale.US);
            }
        }
        return nf;
    }
    
    /**
     * Renders a <code>Number</code> as string.
     * @param pValue number value
     * @param pLocale locale the value should be rendered into
     * @return formatted string of number
     */
    public static String format(Number pValue, Locale pLocale) {
        return format(pValue, pLocale, -1);
    }

    /**
     * Renders a <code>Number</code> as string using locale and fraction information.
     * 
     * @param pValue number value
     * @param pLocale locale the value should be rendered into
     * @param fraction minimal and maximal fraction
     * @return formatted string of number
     */
    public static synchronized String format(Number pValue, Locale pLocale, int fraction) {
        if (pValue == null) {
            return "";
        }
        NumberFormat nf = getFormatter(pLocale);
        if(fraction > -1) {
            nf.setMinimumFractionDigits(fraction);
            nf.setMaximumFractionDigits(fraction);
        } else {
            nf.setMaximumFractionDigits(DEFAULT_MAX_FRACTION_DIGITS);
        }
        return nf.format(pValue);
    }
    
    public static synchronized BigDecimal parseDecimal(String numberString, Locale pLocale) throws ParseException {
        if (null == numberString || numberString.trim().length() == 0) {
            return null;
        }
        NumberFormat nf = getFormatter(pLocale);
        return BigDecimal.valueOf(nf.parse(numberString).doubleValue());
    }
    
    public static synchronized Long parseLong(String numberString, Locale pLocale) throws ParseException {
        if (null == numberString || numberString.trim().length() == 0) {
            return null;
        }
        NumberFormat nf = getFormatter(pLocale);
        return Long.valueOf(nf.parse(numberString).longValue());
    }
}
