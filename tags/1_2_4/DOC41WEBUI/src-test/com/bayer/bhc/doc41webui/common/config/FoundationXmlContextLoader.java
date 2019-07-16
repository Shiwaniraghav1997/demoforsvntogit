package com.bayer.bhc.doc41webui.common.config;

import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.support.GenericXmlContextLoader;

import com.bayer.ecim.foundation.basic.ConfigMap;

public class FoundationXmlContextLoader extends GenericXmlContextLoader {

	@Override
	protected void loadBeanDefinitions(GenericApplicationContext context,
			MergedContextConfiguration mergedConfig) {
		@SuppressWarnings("unchecked")
		MapPropertySource ps = new MapPropertySource(
				"FoundationPropertySource", ConfigMap.get().getSubConfig("spring"));

		context.getEnvironment().getPropertySources().addLast(ps);
		super.loadBeanDefinitions(context, mergedConfig);
	}
}
