/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.util;

import java.util.Locale;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


/**
 * Make the current locale available at the Repository- and DAO-layers.
 * 
 * @author ezzqc
 */
public final class LocaleInSession {
	
	private static final String RCH_KEY_LOCALE = "RCH_KEY_LOCALE";

    private LocaleInSession() {
        // do not construct utility class.
    }
    
    private static ThreadLocal<Locale> sessionLocaleFallback = new ThreadLocal<Locale>() {
        protected synchronized Locale initialValue() {
            return null;
        }
    };

    public static Locale get() {
    	RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    	if(requestAttributes==null){
    		return ((Locale) sessionLocaleFallback.get());
    	}
    	return (Locale) requestAttributes.getAttribute(RCH_KEY_LOCALE, RequestAttributes.SCOPE_REQUEST);
    }

    public static void put(Locale loc) {
    	RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    	if(requestAttributes!=null){
    		requestAttributes.setAttribute(RCH_KEY_LOCALE, loc, RequestAttributes.SCOPE_REQUEST);
    	} else {
    		sessionLocaleFallback.set(loc);
    	}
    }
}
