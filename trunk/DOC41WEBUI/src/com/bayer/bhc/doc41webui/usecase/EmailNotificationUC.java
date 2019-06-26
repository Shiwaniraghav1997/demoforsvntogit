package com.bayer.bhc.doc41webui.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.EmailNotification;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.business.sbcommon.SBSessionManagerSingleton;
import com.bayer.ecim.foundation.business.sbcommon.SBeanException;
import com.bayer.ecim.foundation.dbx.QueryException;
import com.bayer.ecim.foundation.web.usermanagementN.OTUserManagementN;
import com.bayer.ecim.foundation.web.usermanagementN.UMCwidDetails2NDC;

/**
 * @author ETZAJ
 * @version 14.06.2019
 */
public final class EmailNotificationUC {

	private static final String DOT = ".";
	private static final Integer ZERO = 0;
	private static final String QUOTES = "\"";
	private static final String COMMA = ",";
	private static final String SEPARATOR = QUOTES + COMMA + QUOTES;

	private EmailNotificationUC() {
	}

	public static void addEmailNotification(EmailNotification emailNotification) {
		try {
			Properties properties = SBSessionManagerSingleton.get().getSessionManager().retrieveSession(Doc41Constants.PERSISTENT_SESSION_ID, Doc41Constants.PERSISTENT_SESSION_COMPONENT, Doc41Constants.PERSISTENT_SESSION_FLAG);
			if (properties == null) {
				properties = new Properties();
			}
			String emailAddress = getEmailAddressByCwid(emailNotification.getCwid());
			if (emailAddress != null) {
				emailNotification.setEmailAddress(emailAddress);
				addRecipient(properties, emailNotification);
			}
			SBSessionManagerSingleton.get().getSessionManager().storeSession(Doc41Constants.PERSISTENT_SESSION_ID, Doc41Constants.PERSISTENT_SESSION_COMPONENT, properties);
		} catch (SBeanException sbe) {
			Doc41Log.get().error(EmailNotificationUC.class.getName(), UserInSession.getCwid(), sbe);
		} catch (InitException ie) {
			Doc41Log.get().error(EmailNotificationUC.class.getName(), UserInSession.getCwid(), ie);
		}
	}

	private static String getEmailAddressByCwid(String cwid) throws InitException {
		String recipient = null;
		if (StringUtils.isNotBlank(cwid)) {
			try {
				UMCwidDetails2NDC umCwidDetails2NDC = OTUserManagementN.get().getCwidDetails2ByCwid(cwid, Locale.US);
				if (umCwidDetails2NDC != null) {
					recipient = umCwidDetails2NDC.getMailAddress();
				}
			} catch (QueryException qe) {
				throw new InitException("CWID \"" + cwid + "\" could not be resolved to email address.", qe);
			}
		}
		return recipient;
	}

	private static void addRecipient(Properties properties, EmailNotification emailNotification) {
		if (isNewRecipient(properties, emailNotification.getEmailAddress())) {
			addNewRecipient(properties, emailNotification);
		} else {
			addNotification(properties, getRecipientIndex(properties, emailNotification.getEmailAddress()), extractNotification(emailNotification));
		}
	}

	private static boolean isNewRecipient(Properties properties, String recipient) {
		return !getRecipients(properties).contains(recipient);
	}

	private static List<String> getRecipients(Properties properties) {
		List<String> recipients = new ArrayList<String>();
		for (int i = 0; i < getRecipientNumber(properties); i++) {
			String recipient = properties.getProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + (i + 1) + DOT + Doc41Constants.PROPERTY_NAME_RECIPIENT_EMAIL_ADDRESS);
			if (StringUtils.isNotBlank(recipient)) {
				recipients.add(recipient);
			}
		}
		return recipients;
	}

	private static int getRecipientNumber(Properties properties) {
		Integer recipientNumber = ZERO;
		String recipientNumberString = properties.getProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + Doc41Constants.PROPERTY_NAME_NUMBER);
		if (StringUtils.isNotBlank(recipientNumberString)) {
			try {
				recipientNumber = Integer.valueOf(recipientNumberString);
			} catch (NumberFormatException nfe) {
				Doc41Log.get().error(EmailNotificationUC.class.getName(), UserInSession.getCwid(), nfe);
			}
		} else {
			properties.setProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + Doc41Constants.PROPERTY_NAME_NUMBER, recipientNumber.toString());
		}
		return recipientNumber;
	}

	private static void addNewRecipient(Properties properties, EmailNotification emailNotification) {
		Integer recipientNumber = getRecipientNumber(properties);
		properties.setProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + Doc41Constants.PROPERTY_NAME_NUMBER, (++recipientNumber).toString());
		properties.setProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + recipientNumber + DOT + Doc41Constants.PROPERTY_NAME_RECIPIENT_EMAIL_ADDRESS, emailNotification.getEmailAddress());
		addNotification(properties, recipientNumber, extractNotification(emailNotification));
	}

	private static String extractNotification(EmailNotification emailNotification) {
		return QUOTES + emailNotification.getTimestamp().toString() + SEPARATOR + emailNotification.getDocumentName() + SEPARATOR + emailNotification.getVendorName() + SEPARATOR + emailNotification.getVendorNumber() + SEPARATOR + emailNotification.getUsername() + SEPARATOR + emailNotification.getMaterialNumber() + SEPARATOR + emailNotification.getBatch() + SEPARATOR + emailNotification.getPurchaseOrderNumber() + SEPARATOR + emailNotification.getDocumentType().getTitle() + SEPARATOR + emailNotification.getDocumentType().getId() + SEPARATOR + emailNotification.getDocumentIdentification() + QUOTES;
	}

	private static Integer getRecipientIndex(Properties properties, String recipient) {
		Integer recipientIndex = null;
		List<String> recipients = getRecipients(properties);
		for (int i = 0; i < getRecipientNumber(properties); i++) {
			if (recipients.get(i).equals(recipient)) {
				recipientIndex = i + 1;
				break;
			}
		}
		return recipientIndex;
	}

	private static void addNotification(Properties properties, Integer recipientIndex, String notification) {
		if (isNewNotification(properties, recipientIndex, notification)) {
			Integer notificationNumber = getNotificationNumber(properties, recipientIndex);
			properties.setProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + recipientIndex.toString() + DOT + Doc41Constants.PROPERTY_NAME_NOTIFICATION + DOT + Doc41Constants.PROPERTY_NAME_NUMBER, (++notificationNumber).toString());
			properties.setProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + recipientIndex.toString() + DOT + Doc41Constants.PROPERTY_NAME_NOTIFICATION + DOT + notificationNumber.toString() + DOT + Doc41Constants.PROPERTY_NAME_CONTENT, notification);
		}
	}

	private static boolean isNewNotification(Properties properties, Integer recipientIndex, String notification) {
		return !getNotifications(properties, recipientIndex).contains(notification);
	}

	private static List<String> getNotifications(Properties properties, Integer recipientIndex) {
		List<String> notifications = new ArrayList<String>();
		for (int i = 0; i < getNotificationNumber(properties, recipientIndex); i++) {
			String notification = properties.getProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + recipientIndex.toString() + DOT + Doc41Constants.PROPERTY_NAME_NOTIFICATION + DOT + (i + 1) + DOT + Doc41Constants.PROPERTY_NAME_CONTENT);
			if (StringUtils.isNotBlank(notification)) {
				notifications.add(notification);
			}
		}
		return notifications;
	}

	private static Integer getNotificationNumber(Properties properties, Integer recipientIndex) {
		Integer notificationNumber = ZERO;
		String notificationNumberString = properties.getProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + recipientIndex.toString() + DOT + Doc41Constants.PROPERTY_NAME_NOTIFICATION + DOT + Doc41Constants.PROPERTY_NAME_NUMBER);
		if (StringUtils.isNotBlank(notificationNumberString)) {
			try {
				notificationNumber = Integer.valueOf(notificationNumberString);
			} catch (NumberFormatException nfe) {
				Doc41Log.get().error(EmailNotificationUC.class.getName(), UserInSession.getCwid(), nfe);
			}
		} else {
			properties.setProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + recipientIndex + DOT + Doc41Constants.PROPERTY_NAME_NOTIFICATION + DOT + Doc41Constants.PROPERTY_NAME_NUMBER, notificationNumber.toString());
		}
		return notificationNumber;
	}

}
