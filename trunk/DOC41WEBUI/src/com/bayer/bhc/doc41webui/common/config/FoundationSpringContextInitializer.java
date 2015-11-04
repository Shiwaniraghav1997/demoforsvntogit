package com.bayer.bhc.doc41webui.common.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.Dbg;

public class FoundationSpringContextInitializer implements
		ApplicationContextInitializer<ConfigurableWebApplicationContext> {

	public void initialize(ConfigurableWebApplicationContext applicationContext) {
		Dbg.get().println(Dbg.RUN, this, null,
		        "===============================================================================================================================\n"+
		        "Setting Spring Config Context by Foundation Config: FoundationPropertySource (@"+Thread.currentThread().getName()+")\n"+
                "===============================================================================================================================");
        @SuppressWarnings("unchecked")
		MapPropertySource ps = new MapPropertySource("FoundationPropertySource",ConfigMap.get().getSubConfig("spring"));
		applicationContext.getEnvironment().getPropertySources().addFirst(ps);
	}
}
