package com.bayer.bhc.doc41webui.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.domain.EmailNotification;
import com.bayer.bhc.doc41webui.domain.EmailNotificationBundle;
import com.bayer.bhc.doc41webui.domain.SapVendor;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.dbx.QueryException;
import com.bayer.ecim.foundation.web.usermanagementN.OTUserManagementN;
import com.bayer.ecim.foundation.web.usermanagementN.UMCwidDetails2NDC;

/**
 * @author ETZAJ
 * @version 27.06.2019
 * @ticket DW-18
 * 
 *         Utility class for email notification bundles.
 * 
 */
public final class EmailNotificationBundleUtils {

	private EmailNotificationBundleUtils() {

	}

	public static String findVendorNameByVendorNumber(List<SapVendor> sapVendors, String vendorNumber) {
		String vendorName = null;
		if (sapVendors != null && !sapVendors.isEmpty()) {
			for (SapVendor sapVendor : sapVendors) {
				if (sapVendor.getNumber().equals(vendorNumber)) {
					vendorName = (sapVendor.getName1() != null ? sapVendor.getName1() : sapVendor.getName2());
					break;
				}
			}
		}
		return vendorName;
	}

	public static QmDocumentType convertToDocumentType(String documentTypeId) throws Doc41TechnicalException {
		QmDocumentType qmDocumentType = null;
		if (documentTypeId == null || (!documentTypeId.equals(QmDocumentType.COA.getId()) && !documentTypeId.equals(QmDocumentType.COC.getId()) && !documentTypeId.equals(QmDocumentType.OID.getId()))) {
			throw new Doc41TechnicalException(EmailNotificationBundleUtils.class, "Document type could not be converted.");
		}
		if (documentTypeId.equals(QmDocumentType.COA.getId())) {
			qmDocumentType = QmDocumentType.COA;
		} else if (documentTypeId.equals(QmDocumentType.COC.getId())) {
			qmDocumentType = QmDocumentType.COC;
		} else if (documentTypeId.equals(QmDocumentType.OID.getId())) {
			qmDocumentType = QmDocumentType.OID;
		}
		return qmDocumentType;
	}

	public static String convertToEmailAddress(String cwid) {
		String emailAddress = null;
		if (StringUtils.isNotBlank(cwid)) {
			emailAddress = EmailNotificationBundleUtils.getEmailAddressByCwid(cwid);
		} else {
			emailAddress = "EmailAddressPlaceholder";
		}
		return emailAddress;
	}

	private static String getEmailAddressByCwid(String cwid) throws InitException {
		String emailAddress = null;
		try {
			UMCwidDetails2NDC umCwidDetails2NDC = OTUserManagementN.get().getCwidDetails2ByCwid(cwid, Locale.US);
			if (umCwidDetails2NDC != null) {
				emailAddress = umCwidDetails2NDC.getMailAddress();
			}
		} catch (QueryException qe) {
			throw new InitException("CWID \"" + cwid + "\" could not be resolved to email address.", qe);
		}
		return emailAddress;
	}

	public static String getPropertyNameEmailNotificationBundleNumber() {
		return Doc41Constants.PROPERTY_NAME_EMAIL_NOTIFICATION_BUNDLE + "." + Doc41Constants.PROPERTY_NAME_NUMBER;
	}

	public static String getPropertyNameEmailNotificationBundleEmailAddress(Integer emailNotificationBundleIndex) {
		return Doc41Constants.PROPERTY_NAME_EMAIL_NOTIFICATION_BUNDLE + "." + emailNotificationBundleIndex.toString() + "." + Doc41Constants.PROPERTY_NAME_EMAIL_ADDRESS;
	}

	public static String getPropertyNameEmailNotificationNumber(Integer emailNotificationBundleIndex) {
		return Doc41Constants.PROPERTY_NAME_EMAIL_NOTIFICATION_BUNDLE + "." + emailNotificationBundleIndex.toString() + "." + Doc41Constants.PROPERTY_NAME_NOTIFICATION + "." + Doc41Constants.PROPERTY_NAME_NUMBER;
	}

	public static String getPropertyNameEmailNotificationContent(Integer emailNotificationBundleIndex, Integer emailNotificationIndex) {
		return Doc41Constants.PROPERTY_NAME_EMAIL_NOTIFICATION_BUNDLE + "." + emailNotificationBundleIndex.toString() + "." + Doc41Constants.PROPERTY_NAME_NOTIFICATION + "." + emailNotificationIndex.toString() + "." + Doc41Constants.PROPERTY_NAME_CONTENT;
	}

	public static String getSeparator() {
		return ";";
	}

	public static LocalDateTime convertToTimestamp(String timestampString) throws Doc41TechnicalException {
		LocalDateTime timestamp = null;
		try {
			timestamp = LocalDateTime.parse(timestampString);
		} catch (DateTimeParseException dtpe) {
			throw new Doc41TechnicalException(EmailNotificationBundleUtils.class, "Timestamp could not be converted.");
		}
		return timestamp;
	}

	public static Boolean isNewEmailNotificationBundle(EmailNotificationBundle emailNotificationBundle, List<EmailNotificationBundle> emailNotificationBundles) {
		Boolean isNew = true;
		for (EmailNotificationBundle eNB : emailNotificationBundles) {
			if (emailNotificationBundle.equals(eNB)) {
				isNew = false;
				break;
			}
		}
		return isNew;
	}

	public static String convertToString(EmailNotification emailNotification) {
		String emailNotificationString = "";
		emailNotificationString += emailNotification.getTimestamp().toString() + getSeparator();
		emailNotificationString += emailNotification.getDocumentName() + getSeparator();
		emailNotificationString += emailNotification.getVendorName() + getSeparator();
		emailNotificationString += emailNotification.getVendorNumber() + getSeparator();
		emailNotificationString += emailNotification.getUsername() + getSeparator();
		emailNotificationString += emailNotification.getMaterialNumber() + getSeparator();
		emailNotificationString += emailNotification.getBatch() + getSeparator();
		emailNotificationString += emailNotification.getPurchaseOrderNumber() + getSeparator();
		emailNotificationString += emailNotification.getDocumentType().getTitle() + getSeparator();
		emailNotificationString += emailNotification.getDocumentType().getId() + getSeparator();
		emailNotificationString += emailNotification.getDocumentIdentification();
		return emailNotificationString;
	}

	public static Integer findEmailNotificationBundleIndex(String emailAddress, List<EmailNotificationBundle> emailNotificationBundles) {
		Integer emailNotificationBundleIndex = 0;
		for (EmailNotificationBundle emailNotificationBundle : emailNotificationBundles) {
			emailNotificationBundleIndex++;
			if (emailAddress.equals(emailNotificationBundle.getEmailAddress())) {
				break;
			}
		}
		return emailNotificationBundleIndex;
	}

	public static Boolean isNewEmailNotification(EmailNotification emailNotification, List<EmailNotification> emailNotifications) {
		Boolean isNew = true;
		for (EmailNotification eN : emailNotifications) {
			if (emailNotification.equals(eN)) {
				isNew = false;
				break;
			}
		}
		return isNew;
	}

}
