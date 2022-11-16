package com.bayer.bhc.doc41webui.tasks;

import java.util.List;

import com.bayer.bhc.doc41webui.domain.EmailNotificationBundle;
import com.bayer.bhc.doc41webui.usecase.EmailNotificationBundleUC;
import com.bayer.ecim.foundation.business.sbeans.tas.GenericTask;

/**
 * @author ETZAJ
 * @version 03.07.2019
 * 
 *          This class represents a task for sending email notifications. Email
 *          notifications are sent every day at 10:00 AM and 03:00 PM.
 * 
 */
public class EmailNotificationBundleTask extends GenericTask {

	/**
	 * The use case class for sending email notification bundles.
	 */
	private EmailNotificationBundleUC emailNotificationBundleUC;

	/**
	 * This method initializes the initial task parameters required for the task
	 * execution.
	 */
	@Override
	public void initialize() {
		emailNotificationBundleUC = new EmailNotificationBundleUC();
		emailNotificationBundleUC.configureEmailNotificationBundles(getParameterAsString("mimeType"), getParameterAsString("from"), getParameterAsString("defaultSendTo"), getOptionalParameterAsString("copyTo", ""), getOptionalParameterAsString("replyTo", ""));
	}

	/**
	 * This method sends an email for each email notification bundle and stores
	 * unprocessed email notification bundles.
	 */
	@Override
	public void execute() throws Exception {
		List<EmailNotificationBundle> processedAndUnprocessedEmailNotificationBundles = emailNotificationBundleUC.sendEmailNotificationBundles();
		emailNotificationBundleUC.storeUnprocessedEmailNotificationBundles(processedAndUnprocessedEmailNotificationBundles);
	}

}
