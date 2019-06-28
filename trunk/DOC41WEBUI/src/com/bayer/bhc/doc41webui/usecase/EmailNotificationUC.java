package com.bayer.bhc.doc41webui.usecase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.EmailNotificationBundleUtils;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.EmailNotification;
import com.bayer.bhc.doc41webui.domain.EmailNotificationBundle;
import com.bayer.bhc.doc41webui.domain.SapVendor;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.business.sbcommon.SBSessionManagerSingleton;
import com.bayer.ecim.foundation.business.sbcommon.SBeanException;

/**
 * @author ETZAJ
 * @version 27.06.2019
 * @ticket DW-18
 * 
 *         This class stores email notification bundles in the corresponding
 *         persistent session for storing email notification bundles. This class
 *         also reads stored email notification bundles from the same persistent
 *         session.
 * 
 */
public final class EmailNotificationUC {

	private EmailNotificationUC() {

	}

	public static void storeEmailNotificationBundle(String cwid, LocalDateTime timestamp, String documentName, List<SapVendor> sapVendors, String vendorNumber, String username, String materialNumber, String batch, String purchaseOrderNumber, String documentTypeId, String documentIdentification) throws Doc41TechnicalException {
		try {
			Properties properties = SBSessionManagerSingleton.get().getSessionManager().retrieveSession(Doc41Constants.PERSISTENT_SESSION_ID, Doc41Constants.PERSISTENT_SESSION_COMPONENT, Doc41Constants.PERSISTENT_SESSION_FLAG);
			if (properties == null) {
				properties = new Properties();
			}
			EmailNotificationBundle emailNotificationBundle = createEmailNotificationBundle(EmailNotificationBundleUtils.convertToEmailAddress(cwid), timestamp, documentName, sapVendors, vendorNumber, username, materialNumber, batch, purchaseOrderNumber, documentTypeId, documentIdentification);
			storeEmailNotificationBundle(properties, emailNotificationBundle);
			SBSessionManagerSingleton.get().getSessionManager().storeSession(Doc41Constants.PERSISTENT_SESSION_ID, Doc41Constants.PERSISTENT_SESSION_COMPONENT, properties);
		} catch (SBeanException sbe) {
			Doc41Log.get().error(EmailNotificationUC.class.getName(), UserInSession.getCwid(), sbe);
		} catch (InitException ie) {
			Doc41Log.get().error(EmailNotificationUC.class.getName(), UserInSession.getCwid(), ie);
		}
	}

	private static EmailNotificationBundle createEmailNotificationBundle(String emailAddress, LocalDateTime timestamp, String documentName, List<SapVendor> sapVendors, String vendorNumber, String username, String materialNumber, String batch, String purchaseOrderNumber, String documentTypeId, String documentIdentification) throws Doc41TechnicalException {
		EmailNotificationBundle emailNotificationBundle = new EmailNotificationBundle();
		emailNotificationBundle.setEmailAddress(emailAddress);
		emailNotificationBundle.setEmailNotifications(createEmailNotifications(timestamp, documentName, sapVendors, vendorNumber, username, materialNumber, batch, purchaseOrderNumber, documentTypeId, documentIdentification));
		return emailNotificationBundle;
	}

	private static List<EmailNotification> createEmailNotifications(LocalDateTime timestamp, String documentName, List<SapVendor> sapVendors, String vendorNumber, String username, String materialNumber, String batch, String purchaseOrderNumber, String documentTypeId, String documentIdentification) throws Doc41TechnicalException {
		List<EmailNotification> emailNotifications = new ArrayList<EmailNotification>();
		EmailNotification emailNotification = new EmailNotification();
		emailNotification.setTimestamp(timestamp);
		emailNotification.setDocumentName(documentName);
		emailNotification.setVendorName(EmailNotificationBundleUtils.findVendorNameByVendorNumber(sapVendors, vendorNumber));
		emailNotification.setVendorNumber(vendorNumber);
		emailNotification.setUsername(username);
		emailNotification.setMaterialNumber(materialNumber);
		emailNotification.setBatch(batch);
		emailNotification.setPurchaseOrderNumber(purchaseOrderNumber);
		emailNotification.setDocumentType(EmailNotificationBundleUtils.convertToDocumentType(documentTypeId));
		emailNotification.setDocumentIdentification(documentIdentification);
		emailNotifications.add(emailNotification);
		return emailNotifications;
	}

	private static void storeEmailNotificationBundle(Properties properties, EmailNotificationBundle emailNotificationBundle) throws Doc41TechnicalException {
		List<EmailNotificationBundle> emailNotificationBundles = readStoredEmailNotificationBundles(properties);
		if (EmailNotificationBundleUtils.isNewEmailNotificationBundle(emailNotificationBundle, emailNotificationBundles)) {
			storeNewEmailNotificationBundle(properties, emailNotificationBundle);
		} else {
			storeExistingEmailNotificationBundle(properties, emailNotificationBundle);
		}
	}

	private static List<EmailNotificationBundle> readStoredEmailNotificationBundles(Properties properties) throws Doc41TechnicalException {
		List<EmailNotificationBundle> emailNotificationBundles = new ArrayList<EmailNotificationBundle>();
		for (int emailNotificationBundleIndex = 1; emailNotificationBundleIndex <= readStoredEmailNotificationBundleNumber(properties); emailNotificationBundleIndex++) {
			emailNotificationBundles.add(readStoredEmailNotificationBundle(properties, emailNotificationBundleIndex));
		}
		return emailNotificationBundles;
	}

	private static Integer readStoredEmailNotificationBundleNumber(Properties properties) {
		Integer emailNotificationBundleNumber = 0;
		String emailNotificationBundleNumberString = properties.getProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleNumber());
		if (StringUtils.isNotBlank(emailNotificationBundleNumberString)) {
			try {
				emailNotificationBundleNumber = Integer.valueOf(emailNotificationBundleNumberString);
			} catch (NumberFormatException nfe) {
				Doc41Log.get().error(EmailNotificationUC.class.getName(), UserInSession.getCwid(), nfe);
			}
		} else {
			properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleNumber(), emailNotificationBundleNumber.toString());
		}
		return emailNotificationBundleNumber;
	}

	private static EmailNotificationBundle readStoredEmailNotificationBundle(Properties properties, Integer emailNotificationBundleIndex) throws Doc41TechnicalException {
		EmailNotificationBundle emailNotificationBundle = new EmailNotificationBundle();
		emailNotificationBundle.setEmailAddress(properties.getProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleEmailAddress(emailNotificationBundleIndex)));
		emailNotificationBundle.setEmailNotifications(readStoredEmailNotifications(properties, emailNotificationBundleIndex));
		return emailNotificationBundle;
	}

	private static List<EmailNotification> readStoredEmailNotifications(Properties properties, Integer emailNotificationBundleIndex) throws Doc41TechnicalException {
		List<EmailNotification> emailNotifications = new ArrayList<EmailNotification>();
		for (int emailNotificationIndex = 1; emailNotificationIndex <= readStoredEmailNotificationNumber(properties, emailNotificationBundleIndex); emailNotificationIndex++) {
			emailNotifications.add(readStoredEmailNotification(properties, emailNotificationBundleIndex, emailNotificationIndex));
		}
		return emailNotifications;
	}

	private static Integer readStoredEmailNotificationNumber(Properties properties, Integer emailNotificationBundleIndex) {
		Integer emailNotificationNumber = 0;
		String notificationNumberString = properties.getProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationNumber(emailNotificationBundleIndex));
		if (StringUtils.isNotBlank(notificationNumberString)) {
			try {
				emailNotificationNumber = Integer.valueOf(notificationNumberString);
			} catch (NumberFormatException nfe) {
				Doc41Log.get().error(EmailNotificationUC.class.getName(), UserInSession.getCwid(), nfe);
			}
		} else {
			properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationNumber(emailNotificationBundleIndex), emailNotificationNumber.toString());
		}
		return emailNotificationNumber;
	}

	private static EmailNotification readStoredEmailNotification(Properties properties, Integer emailNotificationBundleIndex, Integer emailNotificationIndex) throws Doc41TechnicalException {
		EmailNotification emailNotification = new EmailNotification();
		String emailNotificationContent = properties.getProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationContent(emailNotificationBundleIndex, emailNotificationIndex));
		String[] emailNotificationContentArray = emailNotificationContent.split(EmailNotificationBundleUtils.getSeparator());
		emailNotification.setTimestamp(EmailNotificationBundleUtils.convertToTimestamp(emailNotificationContentArray[0]));
		emailNotification.setDocumentName(emailNotificationContentArray[1]);
		emailNotification.setVendorName(emailNotificationContentArray[2]);
		emailNotification.setVendorNumber(emailNotificationContentArray[3]);
		emailNotification.setUsername(emailNotificationContentArray[4]);
		emailNotification.setMaterialNumber(emailNotificationContentArray[5]);
		emailNotification.setBatch(emailNotificationContentArray[6]);
		emailNotification.setPurchaseOrderNumber(emailNotificationContentArray[7]);
		/*
		 * The document type (8th array element) and document ID (9th array element) are
		 * both set in the next line.
		 */
		emailNotification.setDocumentType(EmailNotificationBundleUtils.convertToDocumentType(emailNotificationContentArray[9]));
		emailNotification.setDocumentIdentification(!emailNotificationContentArray[10].equals("null") ? emailNotificationContentArray[10] : null);
		return emailNotification;
	}

	private static void storeNewEmailNotificationBundle(Properties properties, EmailNotificationBundle emailNotificationBundle) {
		Integer emailNotificationBundleNumber = readStoredEmailNotificationBundleNumber(properties);
		Integer emailNotificationBundleIndex = emailNotificationBundleNumber + 1;
		properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleNumber(), (emailNotificationBundleIndex).toString());
		properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleEmailAddress(emailNotificationBundleIndex), emailNotificationBundle.getEmailAddress());
		storeNewEmailNotification(properties, emailNotificationBundle.getEmailNotifications().get(0), emailNotificationBundleIndex);
	}

	private static void storeNewEmailNotification(Properties properties, EmailNotification emailNotification, Integer emailNotificationBundleIndex) {
		Integer emailNotificationNumber = readStoredEmailNotificationNumber(properties, emailNotificationBundleIndex);
		Integer emailNotificationIndex = emailNotificationNumber + 1;
		properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationNumber(emailNotificationBundleIndex), (emailNotificationIndex).toString());
		properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationContent(emailNotificationBundleIndex, emailNotificationIndex), EmailNotificationBundleUtils.convertToString(emailNotification));
	}

	private static void storeExistingEmailNotificationBundle(Properties properties, EmailNotificationBundle emailNotificationBundle) throws Doc41TechnicalException {
		List<EmailNotificationBundle> emailNotificationBundles = readStoredEmailNotificationBundles(properties);
		Integer emailNotificationBundleIndex = EmailNotificationBundleUtils.findEmailNotificationBundleIndex(emailNotificationBundle.getEmailAddress(), emailNotificationBundles);
		storeEmailNotification(properties, emailNotificationBundle.getEmailNotifications().get(0), emailNotificationBundleIndex);
	}

	private static void storeEmailNotification(Properties properties, EmailNotification emailNotification, Integer emailNotificationBundleIndex) throws Doc41TechnicalException {
		List<EmailNotification> emailNotifications = readStoredEmailNotifications(properties, emailNotificationBundleIndex);
		if (EmailNotificationBundleUtils.isNewEmailNotification(emailNotification, emailNotifications)) {
			storeNewEmailNotification(properties, emailNotification, emailNotificationBundleIndex);
		}
	}

}
