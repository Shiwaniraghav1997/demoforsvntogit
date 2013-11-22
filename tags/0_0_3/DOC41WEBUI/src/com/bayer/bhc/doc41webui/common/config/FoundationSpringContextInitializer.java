package com.bayer.bhc.doc41webui.common.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import com.bayer.ecim.foundation.basic.ConfigMap;

public class FoundationSpringContextInitializer implements
		ApplicationContextInitializer<ConfigurableWebApplicationContext> {

	public void initialize(ConfigurableWebApplicationContext applicationContext) {
		@SuppressWarnings("unchecked")
		MapPropertySource ps = new MapPropertySource("FoundationPropertySource",ConfigMap.get().getSubConfig("spring"));
		applicationContext.getEnvironment().getPropertySources().addFirst(ps);
	}
}
