package com.bayer.bhc.doc41webui.tasks;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.usecase.EmailNotificationBundleUC;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.business.sbcommon.SBSessionManagerSingleton;
import com.bayer.ecim.foundation.business.sbcommon.SBeanException;
import com.bayer.ecim.foundation.business.sbeans.tas.GenericTask;

/**
 * @author ETZAJ
 * @version 26.06.2019
 * 
 * This class represents a task for sending email notifications. 
 * 
 */
public class EmailNotificationBundleTask extends GenericTask {
	
	@Autowired
	private EmailNotificationBundleUC emailNotificationBundleUC;

	@Override
	public void execute() throws Exception {
		try {
			Properties properties = SBSessionManagerSingleton.get().getSessionManager().retrieveSession(Doc41Constants.PERSISTENT_SESSION_ID, Doc41Constants.PERSISTENT_SESSION_COMPONENT, Doc41Constants.PERSISTENT_SESSION_FLAG);
			if (properties == null) {
				properties = new Properties();
			}
			emailNotificationBundleUC.sendEmailNotificationBundles();
			SBSessionManagerSingleton.get().getSessionManager().storeSession(Doc41Constants.PERSISTENT_SESSION_ID, Doc41Constants.PERSISTENT_SESSION_COMPONENT, new Properties());
		} catch (SBeanException sbe) {
			Doc41Log.get().error(EmailNotificationBundleUC.class.getName(), UserInSession.getCwid(), sbe);
		} catch (InitException ie) {
			Doc41Log.get().error(EmailNotificationBundleUC.class.getName(), UserInSession.getCwid(), ie);
		}
	}

}
