package com.bayer.bhc.doc41webui.usecase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.EmailNotificationBundleUtils;
import com.bayer.bhc.doc41webui.domain.EmailNotification;
import com.bayer.bhc.doc41webui.domain.EmailNotificationBundle;
import com.bayer.bhc.doc41webui.domain.SapVendor;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.SendMail;
import com.bayer.ecim.foundation.basic.SendMailMessageContext;
import com.bayer.ecim.foundation.business.sbcommon.SBSessionManagerSingleton;
import com.bayer.ecim.foundation.business.sbcommon.SBeanException;
import com.bayer.ecim.foundation.business.sbeanaccess.BATranslationsException;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

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
	/**
	 * The client for sending email notification bundles.
	 */
	private SendMailMessageContext sendMailMessageContext;
	/**
	 * The default email address, if the email address is not specified by SAP.
	 */
	private String defaultEmailAddress;
	/**
	 * The foundation class used to retrieve translations from the DOC41 dedicated
	 * database table.
	 */
	private Tags tags;

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
		EmailNotificationBundle emailNotificationBundle = createEmailNotificationBundle(EmailNotificationBundleUtils.convertToEmailAddress(cwid), timestamp, documentName, sapVendors, vendorNumber, username, materialNumber, batch, purchaseOrderNumber, documentTypeId, documentIdentification, EmailNotificationBundleUtils.convertToLocale(cwid));
		retrievePersistentSession();
		storeEmailNotificationBundle(emailNotificationBundle);
		storePersistentSession();
	}

	private EmailNotificationBundle createEmailNotificationBundle(String emailAddress, LocalDateTime timestamp, String documentName, List<SapVendor> sapVendors, String vendorNumber, String username, String materialNumber, String batch, String purchaseOrderNumber, String documentTypeId, String documentIdentification, Locale locale) throws Doc41TechnicalException {
		EmailNotificationBundle emailNotificationBundle = new EmailNotificationBundle();
		emailNotificationBundle.setEmailAddress(emailAddress);
		emailNotificationBundle.setEmailNotifications(createEmailNotifications(timestamp, documentName, sapVendors, vendorNumber, username, materialNumber, batch, purchaseOrderNumber, documentTypeId, documentIdentification));
		emailNotificationBundle.setLocale(locale);
		emailNotificationBundle.setProcessed(false);
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

	private void retrievePersistentSession() {
		try {
			properties = SBSessionManagerSingleton.get().getSessionManager().retrieveSession(Doc41Constants.PERSISTENT_SESSION_ID, Doc41Constants.PERSISTENT_SESSION_COMPONENT, Doc41Constants.PERSISTENT_SESSION_FLAG);
			if (properties == null) {
				properties = new Properties();
			}
		} catch (SBeanException sbe) {
			Doc41Log.get().error(EmailNotificationBundleUC.class.getName(), "Session could not be created.", sbe);
		} catch (InitException ie) {
			Doc41Log.get().error(EmailNotificationBundleUC.class.getName(), "Session could not be initialized.", ie);
		}
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
				Doc41Log.get().error(EmailNotificationBundleUC.class.getName(), "Number of email notification bundles could not be read.", nfe);
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
		emailNotificationBundle.setLocale(EmailNotificationBundleUtils.convertToLocale(properties.getProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleLanguage(emailNotificationBundleIndex)), properties.getProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleCountry(emailNotificationBundleIndex))));
		emailNotificationBundle.setProcessed(false);
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
				Doc41Log.get().error(EmailNotificationBundleUC.class.getName(), "Number of email notifications could not be read.", nfe);
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
		properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleLanguage(emailNotificationBundleIndex), emailNotificationBundle.getLocale().getLanguage());
		properties.setProperty(EmailNotificationBundleUtils.getPropertyNameEmailNotificationBundleCountry(emailNotificationBundleIndex), emailNotificationBundle.getLocale().getCountry());
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

	private void storePersistentSession() {
		try {
			SBSessionManagerSingleton.get().getSessionManager().storeSession(Doc41Constants.PERSISTENT_SESSION_ID, Doc41Constants.PERSISTENT_SESSION_COMPONENT, properties);
		} catch (SBeanException sbe) {
			Doc41Log.get().error(EmailNotificationBundleUC.class.getName(), "Session could not be created.", sbe);
		} catch (InitException ie) {
			Doc41Log.get().error(EmailNotificationBundleUC.class.getName(), "Session could not be initialized.", ie);
		}
	}

	/**
	 * This method configures the common and initial parameters for sending email
	 * notification bundles.
	 * 
	 * @param mimeType
	 *            - defines the MIME type used in the email notification.
	 * @param from
	 *            - defines the sender of the email notification.
	 * @param defaultSendTo
	 *            - defines the default recipient of the email notification.
	 * @param copyTo
	 *            - defines the recipient of a copy of the email notification.
	 * @param replyTo
	 *            - defines the recipient of a reply to the email notification.
	 */
	public void configureEmailNotificationBundles(String mimeType, String from, String defaultSendTo, String copyTo, String replyTo) {
		sendMailMessageContext = new SendMailMessageContext();
		sendMailMessageContext.setMimeType(mimeType);
		sendMailMessageContext.setFrom(from);
		if (StringUtils.isNotBlank(copyTo)) {
			sendMailMessageContext.setCopyTo(new String[] { copyTo });
		}
		if (StringUtils.isNotBlank(replyTo)) {
			sendMailMessageContext.setReplyTo(replyTo);
		}
		defaultEmailAddress = defaultSendTo;
	}

	/**
	 * This method reads stored email notification bundles from the corresponding
	 * persistent session for storing email notification bundles and sends an email
	 * for each email notification bundle. It designates email notification bundles
	 * which were successfully processed.
	 * 
	 * @return - successfully and unsuccessfully processed (designated) email
	 *         notification bundles.
	 * @throws Doc41TechnicalException
	 */
	public List<EmailNotificationBundle> sendEmailNotificationBundles() throws Doc41TechnicalException {
		retrievePersistentSession();
		List<EmailNotificationBundle> emailNotificationBundles = readStoredEmailNotificationBundles();
		for (EmailNotificationBundle emailNotificationBundle : emailNotificationBundles) {
			sendEmailNotificationBundle(emailNotificationBundle);
		}
		return emailNotificationBundles;
	}

	private void sendEmailNotificationBundle(EmailNotificationBundle emailNotificationBundle) throws Doc41TechnicalException {
		sendMailMessageContext.setSendTo(determineSendTo(emailNotificationBundle.getEmailAddress()));
		try {
			tags = new Tags("DOC41", "tasks", "emailNotification", emailNotificationBundle.getLocale().getLanguage(), emailNotificationBundle.getLocale().getCountry());
		} catch (BATranslationsException bate) {
			Doc41Log.get().error(EmailNotificationBundleUC.class.getName(), "Translation tags could not be created.", bate);
		}
		sendMailMessageContext.setSubject(tags.getTagNoEsc("subjectTemplate"));
		sendMailMessageContext.setBody(tags.getTagNoEsc("bodyTemplate", EmailNotificationBundleUtils.getEmailTemplateParameterValues(emailNotificationBundle.getEmailNotifications()), EmailNotificationBundleUtils.getEmailTemplateParameterNames()));
		try {
			SendMail.get().send(sendMailMessageContext);
		} catch (MessagingException me) {
			throw new Doc41TechnicalException(EmailNotificationBundleUC.class, "Email could not be sent.", me);
		}
		emailNotificationBundle.setProcessed(true);
	}

	private String[] determineSendTo(String emailAddress) {
		String[] sendTo = null;
		if (!Objects.equals(emailAddress, EmailNotificationBundleUtils.getEmailAddressPlaceholder())) {
			sendTo = new String[] { emailAddress };
		} else {
			sendTo = new String[] { defaultEmailAddress };
		}
		return sendTo;
	}

	/**
	 * This method filters email notification bundles that were not processed during
	 * the sending of email notifications. It also filters new email notification
	 * bundles that were possibly stored during the sending of email notifications.
	 * Afterwards it stores all unprocessed email notification bundles in the
	 * corresponding persistent session for storing email notification bundles.
	 * 
	 * @param processedAndUnprocessedEmailNotificationBundles
	 * @throws Doc41TechnicalException
	 */
	public void storeUnprocessedEmailNotificationBundles(List<EmailNotificationBundle> processedAndUnprocessedEmailNotificationBundles) throws Doc41TechnicalException {
		List<EmailNotificationBundle> unprocessedEmailNotificationBundles = filterUnprocessedEmailNotificationBundles(processedAndUnprocessedEmailNotificationBundles);
		unprocessedEmailNotificationBundles.addAll(filterNewUnprocessedEmailNotificationBundles(processedAndUnprocessedEmailNotificationBundles));
		properties = new Properties();
		for (EmailNotificationBundle emailNotificationBundle : unprocessedEmailNotificationBundles) {
			storeEmailNotificationBundle(emailNotificationBundle);
		}
		storePersistentSession();
	}

	private List<EmailNotificationBundle> filterUnprocessedEmailNotificationBundles(List<EmailNotificationBundle> processedAndUnprocessedEmailNotificationBundles) {
		List<EmailNotificationBundle> unprocessedEmailNotificationBundles = new ArrayList<EmailNotificationBundle>();
		for (EmailNotificationBundle emailNotificationBundle : processedAndUnprocessedEmailNotificationBundles) {
			if (!emailNotificationBundle.isProcessed()) {
				unprocessedEmailNotificationBundles.add(emailNotificationBundle);
			}
		}
		return unprocessedEmailNotificationBundles;
	}

	private List<EmailNotificationBundle> filterNewUnprocessedEmailNotificationBundles(List<EmailNotificationBundle> processedAndUnprocessedEmailNotificationBundles) throws Doc41TechnicalException {
		List<EmailNotificationBundle> newUnprocessedEmailNotificationBundles = new ArrayList<EmailNotificationBundle>();
		retrievePersistentSession();
		List<EmailNotificationBundle> storedEmailNotificationBundles = readStoredEmailNotificationBundles();
		for (EmailNotificationBundle newEmailNotificationBundle : storedEmailNotificationBundles) {
			Boolean isNew = true;
			for (EmailNotificationBundle emailNotificationBundle : processedAndUnprocessedEmailNotificationBundles) {
				if (Objects.equals(newEmailNotificationBundle, emailNotificationBundle)) {
					isNew = false;
					break;
				}
			}
			if (isNew) {
				newUnprocessedEmailNotificationBundles.add(newEmailNotificationBundle);
			}
		}
		return newUnprocessedEmailNotificationBundles;
	}

}
