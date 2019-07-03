package com.bayer.bhc.doc41webui.usecase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.EmailNotificationBundleUtils;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.EmailNotification;
import com.bayer.bhc.doc41webui.domain.EmailNotificationBundle;
import com.bayer.bhc.doc41webui.domain.SapVendor;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.SendMail;
import com.bayer.ecim.foundation.basic.SendMailMessageContext;
import com.bayer.ecim.foundation.basic.Template;
import com.bayer.ecim.foundation.business.sbcommon.SBSessionManagerSingleton;
import com.bayer.ecim.foundation.business.sbcommon.SBeanException;

/**
 * @author ETZAJ
 * @version 03.07.2019
 * @ticket DW-18
 * 
 *         This class stores email notification bundles in the corresponding
 *         persistent session for storing email notification bundles. This class
 *         also reads stored email notification bundles from the same persistent
 *         session.
 * 
 */
@Component
public class EmailNotificationBundleUC {

	/**
	 * The corresponding persistent session for storing email notification bundles.
	 */
	private Properties properties;

	public EmailNotificationBundleUC() {

	}

	/**
	 * This method retrieves the corresponding persistent session for storing email
	 * notification bundles, creates an email notification bundle and stores it in
	 * the retrieved persistent session.
	 * 
	 * @param cwid
	 * @param timestamp
	 * @param documentName
	 * @param sapVendors
	 * @param vendorNumber
	 * @param username
	 * @param materialNumber
	 * @param batch
	 * @param purchaseOrderNumber
	 * @param documentTypeId
	 * @param documentIdentification
	 * @throws Doc41TechnicalException
	 */
	public void storeEmailNotificationBundle(String cwid, LocalDateTime timestamp, String documentName, List<SapVendor> sapVendors, String vendorNumber, String username, String materialNumber, String batch, String purchaseOrderNumber, String documentTypeId, String documentIdentification) throws Doc41TechnicalException {
		try {
			properties = SBSessionManagerSingleton.get().getSessionManager().retrieveSession(Doc41Constants.PERSISTENT_SESSION_ID, Doc41Constants.PERSISTENT_SESSION_COMPONENT, Doc41Constants.PERSISTENT_SESSION_FLAG);
			if (properties == null) {
				properties = new Properties();
			}
			EmailNotificationBundle emailNotificationBundle = createEmailNotificationBundle(EmailNotificationBundleUtils.convertToEmailAddress(cwid), timestamp, documentName, sapVendors, vendorNumber, username, materialNumber, batch, purchaseOrderNumber, documentTypeId, documentIdentification);
			storeEmailNotificationBundle(emailNotificationBundle);
			SBSessionManagerSingleton.get().getSessionManager().storeSession(Doc41Constants.PERSISTENT_SESSION_ID, Doc41Constants.PERSISTENT_SESSION_COMPONENT, properties);
		} catch (SBeanException sbe) {
			Doc41Log.get().error(EmailNotificationBundleUC.class.getName(), UserInSession.getCwid(), sbe);
		} catch (InitException ie) {
			Doc41Log.get().error(EmailNotificationBundleUC.class.getName(), UserInSession.getCwid(), ie);
		}
	}

	private EmailNotificationBundle createEmailNotificationBundle(String emailAddress, LocalDateTime timestamp, String documentName, List<SapVendor> sapVendors, String vendorNumber, String username, String materialNumber, String batch, String purchaseOrderNumber, String documentTypeId, String documentIdentification) throws Doc41TechnicalException {
		EmailNotificationBundle emailNotificationBundle = new EmailNotificationBundle();
		emailNotificationBundle.setEmailAddress(emailAddress);
		emailNotificationBundle.setEmailNotifications(createEmailNotifications(timestamp, documentName, sapVendors, vendorNumber, username, materialNumber, batch, purchaseOrderNumber, documentTypeId, documentIdentification));
		return emailNotificationBundle;
	}

	private List<EmailNotification> createEmailNotifications(LocalDateTime timestamp, String documentName, List<SapVendor> sapVendors, String vendorNumber, String username, String materialNumber, String batch, String purchaseOrderNumber, String documentTypeId, String documentIdentification) throws Doc41TechnicalException {
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

	private void storeEmailNotificationBundle(EmailNotificationBundle emailNotificationBundle) throws Doc41TechnicalException {
		List<EmailNotificationBundle> emailNotificationBundles = readStoredEmailNotificationBundles();
		if (isNewEmailNotificationBundle(emailNotificationBundle, emailNotificationBundles)) {
			storeNewEmailNotificationBundle(emailNotificationBundle);
		} else {
			storeExistingEmailNotificationBundle(emailNotificationBundle);
		}
	}

	private Boolean isNewEmailNotificationBundle(EmailNotificationBundle emailNotificationBundle, List<EmailNotificationBundle> emailNotificationBundles) {
		for (EmailNotificationBundle eNB : emailNotificationBundles) {
			if (emailNotificationBundle.equals(eNB)) {
				return false;
			}
		}
		return true;
	}

	public List<EmailNotificationBundle> readStoredEmailNotificationBundles() throws Doc41TechnicalException {
		List<EmailNotificationBundle> emailNotificationBundles = new ArrayList<EmailNotificationBundle>();
		for (int emailNotificationBundleIndex = 1; emailNotificationBundleIndex <= readStoredEmailNotificationBundleNumber(); emailNotificationBundleIndex++) {
			emailNotificationBundles.add(readStoredEmailNotificationBundle(emailNotificationBundleIndex));
		}
		return emailNotificationBundles;
	}

	private Integer readStoredEmailNotificationBundleNumber() {
		Integer emailNotificationBundleNumber = 0;
		String emailNotificationBundleNumberString = properties.getProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleNumber());
		if (StringUtils.isNotBlank(emailNotificationBundleNumberString)) {
			try {
				emailNotificationBundleNumber = Integer.valueOf(emailNotificationBundleNumberString);
			} catch (NumberFormatException nfe) {
				Doc41Log.get().error(EmailNotificationBundleUC.class.getName(), UserInSession.getCwid(), nfe);
			}
		} else {
			properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleNumber(), emailNotificationBundleNumber.toString());
		}
		return emailNotificationBundleNumber;
	}

	private EmailNotificationBundle readStoredEmailNotificationBundle(Integer emailNotificationBundleIndex) throws Doc41TechnicalException {
		EmailNotificationBundle emailNotificationBundle = new EmailNotificationBundle();
		emailNotificationBundle.setEmailAddress(properties.getProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleEmailAddress(emailNotificationBundleIndex)));
		emailNotificationBundle.setEmailNotifications(readStoredEmailNotifications(emailNotificationBundleIndex));
		return emailNotificationBundle;
	}

	private List<EmailNotification> readStoredEmailNotifications(Integer emailNotificationBundleIndex) throws Doc41TechnicalException {
		List<EmailNotification> emailNotifications = new ArrayList<EmailNotification>();
		for (int emailNotificationIndex = 1; emailNotificationIndex <= readStoredEmailNotificationNumber(emailNotificationBundleIndex); emailNotificationIndex++) {
			emailNotifications.add(readStoredEmailNotification(emailNotificationBundleIndex, emailNotificationIndex));
		}
		return emailNotifications;
	}

	private Integer readStoredEmailNotificationNumber(Integer emailNotificationBundleIndex) {
		Integer emailNotificationNumber = 0;
		String emailNotificationNumberString = properties.getProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationNumber(emailNotificationBundleIndex));
		if (StringUtils.isNotBlank(emailNotificationNumberString)) {
			try {
				emailNotificationNumber = Integer.valueOf(emailNotificationNumberString);
			} catch (NumberFormatException nfe) {
				Doc41Log.get().error(EmailNotificationBundleUC.class.getName(), UserInSession.getCwid(), nfe);
			}
		} else {
			properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationNumber(emailNotificationBundleIndex), emailNotificationNumber.toString());
		}
		return emailNotificationNumber;
	}

	private EmailNotification readStoredEmailNotification(Integer emailNotificationBundleIndex, Integer emailNotificationIndex) throws Doc41TechnicalException {
		EmailNotification emailNotification = null;
		String emailNotificationString = properties.getProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationContent(emailNotificationBundleIndex, emailNotificationIndex));
		emailNotification = EmailNotificationBundleUtils.convertToEmailNotification(emailNotificationString);
		return emailNotification;
	}

	private void storeNewEmailNotificationBundle(EmailNotificationBundle emailNotificationBundle) throws Doc41TechnicalException {
		Integer emailNotificationBundleNumber = readStoredEmailNotificationBundleNumber();
		Integer emailNotificationBundleIndex = emailNotificationBundleNumber + 1;
		properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleNumber(), (emailNotificationBundleIndex).toString());
		properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleEmailAddress(emailNotificationBundleIndex), emailNotificationBundle.getEmailAddress());
		storeNewEmailNotification(emailNotificationBundle.getEmailNotifications().get(0), emailNotificationBundleIndex);
	}

	private void storeNewEmailNotification(EmailNotification emailNotification, Integer emailNotificationBundleIndex) throws Doc41TechnicalException {
		Integer emailNotificationNumber = readStoredEmailNotificationNumber(emailNotificationBundleIndex);
		Integer emailNotificationIndex = emailNotificationNumber + 1;
		properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationNumber(emailNotificationBundleIndex), (emailNotificationIndex).toString());
		properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationContent(emailNotificationBundleIndex, emailNotificationIndex), EmailNotificationBundleUtils.convertToCsvString(emailNotification));
	}

	private void storeExistingEmailNotificationBundle(EmailNotificationBundle emailNotificationBundle) throws Doc41TechnicalException {
		List<EmailNotificationBundle> emailNotificationBundles = readStoredEmailNotificationBundles();
		Integer emailNotificationBundleIndex = findEmailNotificationBundleIndex(emailNotificationBundle.getEmailAddress(), emailNotificationBundles);
		storeEmailNotification(emailNotificationBundle.getEmailNotifications().get(0), emailNotificationBundleIndex);
	}

	private Integer findEmailNotificationBundleIndex(String emailAddress, List<EmailNotificationBundle> emailNotificationBundles) {
		Integer emailNotificationBundleIndex = 0;
		for (EmailNotificationBundle emailNotificationBundle : emailNotificationBundles) {
			emailNotificationBundleIndex++;
			if (emailAddress.equals(emailNotificationBundle.getEmailAddress())) {
				break;
			}
		}
		return emailNotificationBundleIndex;
	}

	private void storeEmailNotification(EmailNotification emailNotification, Integer emailNotificationBundleIndex) throws Doc41TechnicalException {
		List<EmailNotification> emailNotifications = readStoredEmailNotifications(emailNotificationBundleIndex);
		if (isNewEmailNotification(emailNotification, emailNotifications)) {
			storeNewEmailNotification(emailNotification, emailNotificationBundleIndex);
		}
	}

	private Boolean isNewEmailNotification(EmailNotification emailNotification, List<EmailNotification> emailNotifications) {
		for (EmailNotification eN : emailNotifications) {
			if (emailNotification.equals(eN)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method sends an email for each email notification bundle stored in the
	 * corresponding persistent session for storing email notification bundles.
	 * 
	 * @throws Doc41TechnicalException
	 */
	public void sendEmailNotificationBundles() throws Doc41TechnicalException {
		List<EmailNotificationBundle> emailNotificationBundles = readStoredEmailNotificationBundles();
		for (EmailNotificationBundle emailNotificationBundle : emailNotificationBundles) {
			sendEmailNotificationBundle(emailNotificationBundle);
		}
	}

	private void sendEmailNotificationBundle(EmailNotificationBundle emailNotificationBundle) throws Doc41TechnicalException {
		Properties properties = null;
		try {
			properties = ConfigMap.get().getSubCfg("emailNotificationBundle", "emailNotification");
		} catch (InitException ie) {
			throw new Doc41TechnicalException(EmailNotificationBundleUC.class, UserInSession.getCwid(), ie);
		}
		SendMailMessageContext sendMailMessageContext = new SendMailMessageContext();
		sendMailMessageContext.setMimeType("text/html");
		sendMailMessageContext.setFrom(properties.getProperty("from"));
		sendMailMessageContext.setSendTo(getSendTo(emailNotificationBundle.getEmailAddress(), properties));
		sendMailMessageContext.setCopyTo(new String[] { properties.getProperty("copyTo") });
		sendMailMessageContext.setSubject(properties.getProperty("subject"));
		sendMailMessageContext.setBody(Template.expand(properties.getProperty("bodyTemplate." + UserInSession.get().getLocale().getLanguage()), EmailNotificationBundleUtils.getEmailTemplateParameterValues(emailNotificationBundle.getEmailNotifications()), EmailNotificationBundleUtils.getEmailTemplateParameterNames()));
		try {
			SendMail.get().send(sendMailMessageContext);
		} catch (MessagingException me) {
			throw new Doc41TechnicalException(EmailNotificationBundleUC.class, UserInSession.getCwid(), me);
		}
	}

	private String[] getSendTo(String emailAddress, Properties properties) {
		String[] sendTo = null;
		if (isEmailAddressInitialized(emailAddress)) {
			sendTo = new String[] { emailAddress };
		} else {
			sendTo = new String[] { properties.getProperty("sendTo") };
		}
		return sendTo;
	}

	private Boolean isEmailAddressInitialized(String emailAddress) {
		return !Objects.equals(emailAddress, EmailNotificationBundleUtils.getEmailAddressPlaceholder());
	}

}
