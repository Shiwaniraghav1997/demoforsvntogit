package com.bayer.bhc.doc41webui.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.business.sbcommon.SBSessionManagerSingleton;
import com.bayer.ecim.foundation.business.sbcommon.SBeanException;
import com.bayer.ecim.foundation.web.usermanagementN.OTUserManagementNSendMailAddressResolverProxy;

/**
 * @author ETZAJ
 * @version 14.06.2019
 */
public final class EmailNotificationUtils {

	private static final OTUserManagementNSendMailAddressResolverProxy otUserManagementNSendMailAddressResolverProxy = new OTUserManagementNSendMailAddressResolverProxy();
	private static final String DOT = ".";
	private static final Integer ZERO = 0;
	private static final String QUOTES = "\"";
	private static final String COMMA = ",";

	private EmailNotificationUtils() {
	}

	public static void addEmailNotification(String documentType, String documentName, String cwid) {
		try {
			Properties properties = SBSessionManagerSingleton.get().getSessionManager().retrieveSession(
					Doc41Constants.PERSISTENT_SESSION_ID, Doc41Constants.PERSISTENT_SESSION_COMPONENT,
					Doc41Constants.PERSISTENT_SESSION_FLAG);
			if (properties == null) {
				properties = new Properties();
			}
			String recipient = getRecipientByCwid(cwid);
			if (recipient != null) {
				addRecipient(properties, recipient, documentType, documentName);
			}
			SBSessionManagerSingleton.get().getSessionManager().storeSession(Doc41Constants.PERSISTENT_SESSION_ID,
					Doc41Constants.PERSISTENT_SESSION_COMPONENT, properties);
		} catch (SBeanException sbe) {
			Doc41Log.get().error(EmailNotificationUtils.class.getName(), UserInSession.getCwid(), sbe);
		}
	}

	private static String getRecipientByCwid(String cwid) {
		String recipient = null;
		if (StringUtils.isNotBlank(cwid)) {
			try {
				recipient = otUserManagementNSendMailAddressResolverProxy.lookUpAddress(cwid);
			} catch (InitException ie) {
				Doc41Log.get().error(EmailNotificationUtils.class.getName(), UserInSession.getCwid(), ie);
			}
		}
		return recipient;
	}

	private static void addRecipient(Properties properties, String recipient, String documentType,
			String documentName) {
		if (isNewRecipient(properties, recipient)) {
			addNewRecipient(properties, recipient, documentType, documentName);
		} else {
			addNotification(properties, getRecipientIndex(properties, recipient),
					generateNotification(documentType, documentName));
		}
	}

	private static boolean isNewRecipient(Properties properties, String recipient) {
		return !getRecipients(properties).contains(recipient);
	}

	private static List<String> getRecipients(Properties properties) {
		List<String> recipients = new ArrayList<String>();
		for (int i = 0; i < getRecipientNumber(properties); i++) {
			String recipient = properties.getProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + (i + 1) + DOT
					+ Doc41Constants.PROPERTY_NAME_RECIPIENT_EMAIL_ADDRESS);
			if (StringUtils.isNotBlank(recipient)) {
				recipients.add(recipient);
			}
		}
		return recipients;
	}

	private static int getRecipientNumber(Properties properties) {
		Integer recipientNumber = ZERO;
		String recipientNumberString = properties
				.getProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + Doc41Constants.PROPERTY_NAME_NUMBER);
		if (StringUtils.isNotBlank(recipientNumberString)) {
			try {
				recipientNumber = Integer.valueOf(recipientNumberString);
			} catch (NumberFormatException nfe) {
				Doc41Log.get().error(EmailNotificationUtils.class.getName(), UserInSession.getCwid(), nfe);
			}
		} else {
			properties.setProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + Doc41Constants.PROPERTY_NAME_NUMBER,
					recipientNumber.toString());
		}
		return recipientNumber;
	}

	private static void addNewRecipient(Properties properties, String recipient, String documentType,
			String documentName) {
		Integer recipientNumber = getRecipientNumber(properties);
		properties.setProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + Doc41Constants.PROPERTY_NAME_NUMBER,
				(++recipientNumber).toString());
		properties.setProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + recipientNumber + DOT
				+ Doc41Constants.PROPERTY_NAME_RECIPIENT_EMAIL_ADDRESS, recipient);
		addNotification(properties, recipientNumber, generateNotification(documentType, documentName));
	}

	private static String generateNotification(String documentType, String documentName) {
		return QUOTES + UserInSession.getCwid() + QUOTES + COMMA + QUOTES + documentType + QUOTES + COMMA + QUOTES
				+ documentName + QUOTES;
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
			properties.setProperty(
					Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + recipientIndex.toString() + DOT
							+ Doc41Constants.PROPERTY_NAME_NOTIFICATION + DOT + Doc41Constants.PROPERTY_NAME_NUMBER,
					(++notificationNumber).toString());
			properties.setProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + recipientIndex.toString() + DOT
					+ Doc41Constants.PROPERTY_NAME_NOTIFICATION + DOT + notificationNumber.toString() + DOT
					+ Doc41Constants.PROPERTY_NAME_CONTENT, notification);
		}
	}

	private static boolean isNewNotification(Properties properties, Integer recipientIndex, String notification) {
		return !getNotifications(properties, recipientIndex).contains(notification);
	}

	private static List<String> getNotifications(Properties properties, Integer recipientIndex) {
		List<String> notifications = new ArrayList<String>();
		for (int i = 0; i < getNotificationNumber(properties, recipientIndex); i++) {
			String notification = properties
					.getProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + recipientIndex.toString() + DOT
							+ Doc41Constants.PROPERTY_NAME_NOTIFICATION + DOT + (i + 1) + DOT + Doc41Constants.PROPERTY_NAME_CONTENT);
			if (StringUtils.isNotBlank(notification)) {
				notifications.add(notification);
			}
		}
		return notifications;
	}

	private static Integer getNotificationNumber(Properties properties, Integer recipientIndex) {
		Integer notificationNumber = ZERO;
		String notificationNumberString = properties
				.getProperty(Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + recipientIndex.toString() + DOT
						+ Doc41Constants.PROPERTY_NAME_NOTIFICATION + DOT + Doc41Constants.PROPERTY_NAME_NUMBER);
		if (StringUtils.isNotBlank(notificationNumberString)) {
			try {
				notificationNumber = Integer.valueOf(notificationNumberString);
			} catch (NumberFormatException nfe) {
				Doc41Log.get().error(EmailNotificationUtils.class.getName(), UserInSession.getCwid(), nfe);
			}
		} else {
			properties.setProperty(
					Doc41Constants.PROPERTY_NAME_RECIPIENT + DOT + recipientIndex + DOT
							+ Doc41Constants.PROPERTY_NAME_NOTIFICATION + DOT + Doc41Constants.PROPERTY_NAME_NUMBER,
					notificationNumber.toString());
		}
		return notificationNumber;
	}

}
