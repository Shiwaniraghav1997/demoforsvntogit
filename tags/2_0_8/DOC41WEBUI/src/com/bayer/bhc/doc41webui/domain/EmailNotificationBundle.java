package com.bayer.bhc.doc41webui.domain;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author ETZAJ
 * @version 27.06.2019
 * @ticket DW-18
 * 
 *         This class represents an email notification bundle. It contains an
 *         email address and a list of email notifications bundled to that email
 *         address.
 * 
 */
public class EmailNotificationBundle {

	private String emailAddress;
	private List<EmailNotification> emailNotifications;
	private Locale locale;
	private Boolean processed;

	public EmailNotificationBundle() {

	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public List<EmailNotification> getEmailNotifications() {
		return emailNotifications;
	}

	public void setEmailNotifications(List<EmailNotification> emailNotifications) {
		this.emailNotifications = emailNotifications;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Boolean isProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof EmailNotificationBundle)) {
			return false;
		}
		EmailNotificationBundle emailNotificationBundle = (EmailNotificationBundle) o;
		return Objects.equals(emailAddress, emailNotificationBundle.getEmailAddress());
	}

}
