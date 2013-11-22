/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.bayer.bhc.doc41webui.domain.User;

/**
 * Make the current user available at the Repository- and DAO-layers.
 * 
 * @author ezzqc
 */
public final class UserInSession {
	
	private static final String RCH_KEY_USER = "RCH_KEY_USER";

    private UserInSession() {
        // do not construct utility class.
    }
    
    private static ThreadLocal<User> sessionUserFallback = new ThreadLocal<User>() {
        protected synchronized User initialValue() {
            return null;
        }
    };

    public static User get() {
    	RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    	if(requestAttributes==null){
    		return ((User) sessionUserFallback.get());
    	}
    	return (User) requestAttributes.getAttribute(RCH_KEY_USER, RequestAttributes.SCOPE_REQUEST);
    }

    public static String getCwid() {
    	if (null == get()) {
            return null;
        }
        return get().getCwid();
    }
    
    public static Long getId() {
        if (null == get()) {
            return null;
        }
        return get().getDcId();
    }
    
    public static boolean isReadOnly() {
        if (null == get()) {
            return false;
        }
        return get().getReadOnly().booleanValue();
    }
    
    public static void put(User user) {
    	RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    	if(requestAttributes!=null){
    		requestAttributes.setAttribute(RCH_KEY_USER, user, RequestAttributes.SCOPE_REQUEST);
    	} else {
    		sessionUserFallback.set(user);
    	}
    }
}
