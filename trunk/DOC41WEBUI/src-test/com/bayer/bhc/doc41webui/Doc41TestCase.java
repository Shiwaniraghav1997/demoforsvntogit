package com.bayer.bhc.doc41webui;

import java.util.Locale;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
//import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.config.FoundationXmlContextLoader;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=FoundationXmlContextLoader.class,locations={"classpath:cfg/spring/applicationContext.xml",
		"classpath:cfg/spring/spring-integration.xml",
		"classpath:cfg/spring/spring-services.xml"
		})

public class Doc41TestCase extends TestCase {	
	
	@BeforeClass
	public static void setUserInSession() {
		System.out.println("@BeforeClass");
//		RequestAttributes attributes = new ServletRequestAttributes(new MockHttpServletRequest()); 
//		RequestContextHolder.setRequestAttributes(attributes);
		User tmpUser	= new User();
		tmpUser.setReadOnly(Boolean.FALSE);
		tmpUser.setCwid(Doc41Constants.USERNAME_TEST);
		UserInSession.put(tmpUser);
		Locale tmpLocale= new Locale("en", "US");
		LocaleInSession.put(tmpLocale);
		System.setProperty("spring.profiles.active", "mock");
	}		
}
