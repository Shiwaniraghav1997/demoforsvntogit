package com.bayer.bhc.doc41webui.domain;

import java.util.List;
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
