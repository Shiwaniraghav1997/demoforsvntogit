package com.bayer.bhc.doc41webui.web.test;

import java.util.Locale;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.User;

public class Doc41TestCaseImpl {	
	
	public static void setUserInSession() {
	    
//		RequestAttributes attributes = new ServletRequestAttributes(new MockHttpServletRequest()); 
//		RequestContextHolder.setRequestAttributes(attributes);
		User tmpUser	= new User();
		tmpUser.setReadOnly(Boolean.FALSE);
		tmpUser.setCwid(Doc41Constants.USERNAME_TEST);
		UserInSession.put(tmpUser);
		Locale tmpLocale= new Locale("en", "US");
		LocaleInSession.put(tmpLocale);
	}		

    public static void resetUserInSession() {
        LocaleInSession.cleanUp();
        UserInSession.cleanUp();
    }

}
