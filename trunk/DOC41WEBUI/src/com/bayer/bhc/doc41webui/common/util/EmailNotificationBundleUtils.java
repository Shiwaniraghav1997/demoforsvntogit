package com.bayer.bhc.doc41webui.common.util;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.EmailNotification;
import com.bayer.bhc.doc41webui.domain.SapVendor;
import com.bayer.bhc.doc41webui.usecase.EmailNotificationBundleUC;
import com.bayer.ecim.foundation.basic.ServerException;
import com.bayer.ecim.foundation.db.exchange.CSVReader;
import com.bayer.ecim.foundation.db.exchange.DBDataRow;
import com.bayer.ecim.foundation.dbx.QueryException;
import com.bayer.ecim.foundation.web.usermanagementN.OTUserManagementN;
import com.bayer.ecim.foundation.web.usermanagementN.UMCwidDetails2NDC;
import com.bayer.ecim.foundation.web.usermanagementN.UMUserNDC;

/**
 * @author ETZAJ
 * @version 03.07.2019
 * @ticket DW-18
 * 
 *         Utility class for email notification bundles.
 * 
 */
public final class EmailNotificationBundleUtils {

	private EmailNotificationBundleUtils() {

	}

	/**
	 * This methods finds the vendor name of a vendor with the corresponding vendor
	 * number.
	 * 
	 * @param sapVendors
	 * @param vendorNumber
	 * @return
	 */
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

	/**
	 * This method converts a document type ID (string) to the corresponding
	 * document type (enumeration).
	 * 
	 * @param documentTypeId
	 * @return
	 * @throws Doc41TechnicalException
	 */
	public static QmDocumentType convertToDocumentType(String documentTypeId) throws Doc41TechnicalException {
		QmDocumentType qmDocumentType = null;
		if (documentTypeId == null || (!documentTypeId.equals(QmDocumentType.COA.getId()) && !documentTypeId.equals(QmDocumentType.COC.getId()) && !documentTypeId.equals(QmDocumentType.OID.getId()))) {
			throw new Doc41TechnicalException(EmailNotificationBundleUtils.class, UserInSession.getCwid());
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

	/**
	 * This method converts a CWID to the corresponding email address, if there is
	 * one. Otherwise this method converts a CWID to an email address placeholder,
	 * which is used to indicate that the default email address must be used.
	 * 
	 * @param cwid
	 * @return
	 */
	public static String convertToEmailAddress(String cwid) {
		String emailAddress = null;
		if (StringUtils.isNotBlank(cwid)) {
			try {
				emailAddress = EmailNotificationBundleUtils.getEmailAddressByCwid(cwid);
			} catch (Doc41TechnicalException d41te) {
				Doc41Log.get().warning(EmailNotificationBundleUC.class, "Email address could not be converted.", d41te);
			}
		} else {
			emailAddress = getEmailAddressPlaceholder();
		}
		return emailAddress;
	}

	private static String getEmailAddressByCwid(String cwid) throws Doc41TechnicalException {
		String emailAddress = null;
		try {
			UMCwidDetails2NDC umCwidDetails2NDC = OTUserManagementN.get().getCwidDetails2ByCwid(cwid, Locale.US);
			if (umCwidDetails2NDC != null) {
				emailAddress = umCwidDetails2NDC.getMailAddress();
			}
		} catch (QueryException qe) {
			throw new Doc41TechnicalException(EmailNotificationBundleUtils.class, "Email address could not be retrieved.", qe);
		}
		return emailAddress;
	}

	public static String getEmailAddressPlaceholder() {
		return "EmailAddressPlaceholder";
	}

	/**
	 * This method converts a CWID to the corresponding locale, if there is one.
	 * Otherwise this method converts a CWID to the default locale (en_US).
	 * 
	 * @param cwid
	 * @return
	 * @throws Doc41TechnicalException
	 */
	public static Locale convertToLocale(String cwid) {
		Locale locale = null;
		if (StringUtils.isNotBlank(cwid)) {
			try {
				locale = getLocaleByCwid(cwid);
			} catch (Doc41TechnicalException d41te) {
				Doc41Log.get().warning(EmailNotificationBundleUC.class, "Locale could not be converted.", d41te);
			}
		}
		if (locale == null) {
			locale = Locale.US;
		}
		return locale;
	}

	private static Locale getLocaleByCwid(String cwid) throws Doc41TechnicalException {
		Locale locale = null;
		try {
			UMUserNDC umUserNDC = OTUserManagementN.get().getUserByCWID(cwid, Locale.US);
			if (umUserNDC != null) {
				locale = umUserNDC.getDisplayLocale();
			}
		} catch (QueryException qe) {
			throw new Doc41TechnicalException(EmailNotificationBundleUtils.class, "Locale could not be retrieved.", qe);
		}
		return locale;
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

	public static String getPropertyNameEmailNotificationBundleLanguage(Integer emailNotificationBundleIndex) {
		return Doc41Constants.PROPERTY_NAME_EMAIL_NOTIFICATION_BUNDLE + "." + emailNotificationBundleIndex.toString() + "." + Doc41Constants.PROPERTY_NAME_LANGUAGE;
	}

	public static String getPropertyNameEmailNotificationBundleCountry(Integer emailNotificationBundleIndex) {
		return Doc41Constants.PROPERTY_NAME_EMAIL_NOTIFICATION_BUNDLE + "." + emailNotificationBundleIndex.toString() + "." + Doc41Constants.PROPERTY_NAME_COUNTRY;
	}

	/**
	 * This methods uses the provided language and country and converts them to a
	 * locale.
	 * 
	 * @param language
	 * @param country
	 * @return
	 */
	public static Locale convertToLocale(String language, String country) {
		Locale locale = null;
		if (StringUtils.isNotBlank(language) && StringUtils.isNotBlank(country)) {
			locale = new Locale(language, country);
		}
		return locale;
	}

	/**
	 * This method converts an email notification string (CSV format) to an email
	 * notification (POJO).
	 * 
	 * @param emailNotificationString
	 * @return
	 * @throws Doc41TechnicalException
	 */
	public static EmailNotification convertToEmailNotification(String emailNotificationString) throws Doc41TechnicalException {
		EmailNotification emailNotification = null;
		DBDataRow dBDataRow = null;
		try {
			StringReader stringReader = new StringReader(emailNotificationString);
			CSVReader csvReader = new CSVReader(stringReader, ',', '"', "Timestamp,DocumentName,VendorName,VendorNumber,Username,MaterialNumber,Batch,PurchaseOrderNumber,DocumentTitle,DocumentId,DocumentIdentification");
			dBDataRow = csvReader.getRow();
			csvReader.close();
		} catch (ServerException se) {
			throw new Doc41TechnicalException(EmailNotificationBundleUtils.class, UserInSession.getCwid(), se);
		}
		emailNotification = new EmailNotification();
		emailNotification.setTimestamp(convertToTimestamp(dBDataRow.getValue(0)));
		emailNotification.setDocumentName(dBDataRow.getValue(1));
		emailNotification.setVendorName(dBDataRow.getValue(2));
		emailNotification.setVendorNumber(dBDataRow.getValue(3));
		emailNotification.setUsername(dBDataRow.getValue(4));
		emailNotification.setMaterialNumber(dBDataRow.getValue(5));
		emailNotification.setBatch(dBDataRow.getValue(6));
		emailNotification.setPurchaseOrderNumber(dBDataRow.getValue(7));
		emailNotification.setDocumentType(convertToDocumentType(dBDataRow.getValue(9)));
		emailNotification.setDocumentIdentification(dBDataRow.getValue(10));
		return emailNotification;
	}

	private static LocalDateTime convertToTimestamp(String timestampString) throws Doc41TechnicalException {
		LocalDateTime localDateTime = null;
		try {
			localDateTime = LocalDateTime.parse(timestampString);
		} catch (DateTimeParseException dtpe) {
			throw new Doc41TechnicalException(EmailNotificationBundleUtils.class, UserInSession.getCwid(), dtpe);
		}
		return localDateTime;
	}

	/**
	 * This method converts an email notification (POJO) to the corresponding email
	 * notification string (CSV format).
	 * 
	 * @param emailNotification
	 * @return
	 */
	public static String convertToCsvString(EmailNotification emailNotification) {
		String csvString = null;
		String[] emailNotificationArray = new String[] { StringEscapeUtils.escapeCsv(emailNotification.getTimestamp().toString()), StringEscapeUtils.escapeCsv(emailNotification.getDocumentName()), StringEscapeUtils.escapeCsv(emailNotification.getVendorName()), StringEscapeUtils.escapeCsv(emailNotification.getVendorNumber()), StringEscapeUtils.escapeCsv(emailNotification.getUsername()), StringEscapeUtils.escapeCsv(emailNotification.getMaterialNumber()), StringEscapeUtils.escapeCsv(emailNotification.getBatch()), StringEscapeUtils.escapeCsv(emailNotification.getPurchaseOrderNumber()), StringEscapeUtils.escapeCsv(emailNotification.getDocumentType().getTitle()), StringEscapeUtils.escapeCsv(emailNotification.getDocumentType().getId()), StringEscapeUtils.escapeCsv(emailNotification.getDocumentIdentification()) };
		csvString = StringUtils.join(emailNotificationArray, ",");
		return csvString;
	}
	
	public static String[] getEmailTemplateParameterNames() {
		return new String[] { "EMAIL_NOTIFICATIONS", "TIMESTAMPS", "DOCUMENT_NAMES", "VENDOR_NAMES", "VENDOR_NUMBERS", "USERNAMES", "MATERIAL_NUMBERS", "BATCHES", "PURCHASE_ORDER_NUMBERS", "DOCUMENT_TITLES", "DOCUMENT_IDS", "DOCUMENT_IDENTIFICATIONS" };
	}

	public static Object[] getEmailTemplateParameterValues(List<EmailNotification> emailNotifications) {
		List<String> timestamps = new ArrayList<String>();
		List<String> documentNames = new ArrayList<String>();
		List<String> vendorNames = new ArrayList<String>();
		List<String> vendorNumbers = new ArrayList<String>();
		List<String> usernames = new ArrayList<String>();
		List<String> materialNumbers = new ArrayList<String>();
		List<String> batches = new ArrayList<String>();
		List<String> purchaseOrderNumbers = new ArrayList<String>();
		List<String> documentTitles = new ArrayList<String>();
		List<String> documentIds = new ArrayList<String>();
		List<String> documentIdentifications = new ArrayList<String>();
		for (EmailNotification emailNotification : emailNotifications) {
			timestamps.add(emailNotification.getTimestamp().toString());
			documentNames.add(emailNotification.getDocumentName());
			vendorNames.add(emailNotification.getVendorName());
			vendorNumbers.add(emailNotification.getVendorNumber());
			usernames.add(emailNotification.getUsername());
			materialNumbers.add(emailNotification.getMaterialNumber());
			batches.add(emailNotification.getBatch());
			purchaseOrderNumbers.add(emailNotification.getPurchaseOrderNumber());
			documentTitles.add(emailNotification.getDocumentType().getTitle());
			documentIds.add(emailNotification.getDocumentType().getId());
			documentIdentifications.add(emailNotification.getDocumentIdentification());
		}
		return new Object[] { emailNotifications, timestamps, documentNames, vendorNames, vendorNumbers, usernames, materialNumbers, batches, purchaseOrderNumbers, documentTitles, documentIds, documentIdentifications };
	}

}
