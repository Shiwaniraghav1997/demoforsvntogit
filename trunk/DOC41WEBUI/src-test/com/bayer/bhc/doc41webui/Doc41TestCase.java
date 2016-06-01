package com.bayer.bhc.doc41webui;


import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
//import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;

import com.bayer.bhc.doc41webui.common.config.FoundationXmlContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=FoundationXmlContextLoader.class,locations={"classpath:cfg/spring/applicationContext.xml",
		"classpath:cfg/spring/spring-services.xml"
		})

public class Doc41TestCase extends TestCase {	
	
	@BeforeClass
	public static void setUserInSession() {
		System.out.println("@BeforeClass");
		com.bayer.bhc.doc41webui.web.test.Doc41TestCaseImpl.setUserInSession();
		System.setProperty("spring.profiles.active", "mock"); // ???????
	}		
}
