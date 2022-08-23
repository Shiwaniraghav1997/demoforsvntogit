package com.bayer.bhc.doc41webui.common.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.Dbg;
import com.bayer.ecim.foundation.basic.Singleton;

public class FoundationSpringContextInitializer implements
		ApplicationContextInitializer<ConfigurableWebApplicationContext> {

    public static final String LOGPREFIX = "FoundationSpringContextInitializer.";

	public void initialize(ConfigurableWebApplicationContext applicationContext) {
	    /*
	    System.err.println(
                "----------------------------------------------------------------------------------\n"+
                "APPLNAME: " + applicationContext.getApplicationName() + "\n" +
                "DISPNAME: " + applicationContext.getDisplayName() + "\n" +
                "ID: " + applicationContext.getId() + "\n" +
                "ENV: " + applicationContext.getEnvironment().toString() + "\n" +
                "SrvltCtxCtxPath: " + applicationContext.getServletContext().getContextPath() + "\n" +
                "SrvltCtxRealPath: " + applicationContext.getServletContext().getRealPath("/") + "\n" +
                "----------------------------------------------------------------------------------"
            );
        */
		Dbg.get().println(Dbg.RUN, this, null,
		        "\n===============================================================================================================================\n"+
		        "Setting Spring Config Context by Foundation Config: FoundationPropertySource (@"+Thread.currentThread().getName()+")\n"+
                "===============================================================================================================================\n");
		
        if (!Singleton.isInstanciated(ConfigMap.ID)) { // InitializeConfigMap Servlet is started too late - we need to bridge...
            //PrintStream OUT = ConfigMap.getSysProp( "debug.alterDefaultSetup", false ) ? System.out : System.err;
            String pInstPath = applicationContext.getServletContext().getRealPath("/");
            if (pInstPath != null) {
                pInstPath = pInstPath.replace('\\', '/');
                if (pInstPath.endsWith("/")) {
                    pInstPath = pInstPath.substring(0, pInstPath.length() - 1);
                }
                ConfigMap.setInstallationPath(pInstPath);
                //OUT.println(LOGPREFIX+ "ConfigMap-InstallationPath=" + ConfigMap.getInstallationPath() + ", start to initialize...");
                Dbg.get().println(Dbg.RUN, this, null, LOGPREFIX+ "ConfigMap-InstallationPath=" + ConfigMap.getInstallationPath() + ", start to initialize...");
            }
        }
        
        @SuppressWarnings("unchecked")
		MapPropertySource ps = new MapPropertySource("FoundationPropertySource",ConfigMap.get().getSubConfig("spring"));
		applicationContext.getEnvironment().getPropertySources().addFirst(ps);
	}
}
