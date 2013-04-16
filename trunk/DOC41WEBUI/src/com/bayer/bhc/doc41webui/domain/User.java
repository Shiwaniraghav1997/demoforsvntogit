package com.bayer.bhc.doc41webui.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.bayer.bhc.doc41webui.common.util.TimeZone;
import com.bayer.ecim.foundation.basic.LocaleTool;
import com.bayer.ecim.foundation.basic.StringTool;

/**
 * User domain object.
 * 
 * @author ezzqc
 */
public class User extends DomainObject {

	private static final long serialVersionUID = -88472347824568763L;

	public static final String ROLE_BUSINESS_ADMIN = "doc41_badm";

	public static final String ROLE_TECH_ADMIN = "doc41_tadm";

	public static final String ROLE_A = "doc41_a";
	
	public static final String ROLE_OBSERVER = "boe_obsv";

	public static final String TYPE_INTERNAL = "internal";

	public static final String TYPE_EXTERNAL = "external";

	public static final String STATUS_ACTIVE = "active";

	public static final String STATUS_INACTIVE = "inactive";

	// legacy list:
	// public static final String[] ALL_ROLES = new String[] { ROLE_TECH_ADMIN,
	// ROLE_OPER_ADMIN,
	// ROLE_OPER_STAFF, ROLE_CARRIER, ROLE_EXPEDITION, ROLE_OBSERVER };

	public static final String[] ALL_ROLES = new String[] {
			ROLE_BUSINESS_ADMIN, ROLE_TECH_ADMIN, ROLE_OBSERVER };

	private String cwid;

	private String surname;

	private String firstname;

	private String email;

	private String phone;

	private String type;

	private Boolean active;

	private String password;

	private String passwordRepeated;
	
	private List<String> roles = new ArrayList<String>();
	
	private Locale locale;
	
	// Default: no write permissions
	private Boolean readOnly = Boolean.TRUE;

	// Default: german time zone - Europe/Berlin
	private Long timeZone = new Long(TimeZone.GMT_1);
		
	private Boolean automaticClose = false;
	private int closeAfter;

	// convenience method:
	public boolean isExternalUser() {
		return getType().equals(User.TYPE_EXTERNAL);
	}
	
	public Boolean getAutomaticClose() {
		return automaticClose;
	}

	public void setAutomaticClose(Boolean automaticClose) {
		this.automaticClose = automaticClose;
	}

	public int getCloseAfter() {
		return closeAfter;
	}

	public void setCloseAfter(int closeAfter) {
		this.closeAfter = closeAfter;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Long getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(Long timeZone) {
		this.timeZone = timeZone;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}	

	public String getCwid() {
		return cwid;
	}

	public void setCwid(String cwid) {
		this.cwid = cwid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String lastname) {
		this.surname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeated() {
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}


	@Override
	public String toString() {
		return "User [cwid=" + cwid + ", surname=" + surname + ", firstname="
				+ firstname + ", email=" + email + ", phone=" + phone
				+ ", type=" + type + ", active=" + active + ", password="
				+ password  + ", roles="
				+ roles
				+ ", locale=" + locale 
				+ ", readOnly=" + readOnly + ", timeZone=" + timeZone
				+ ", automaticClose=" + automaticClose + ", closeAfter="
				+ closeAfter + ", company=" + company + "]";
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public boolean isBusinessAdmin() {
		return roles.contains(ROLE_BUSINESS_ADMIN);
	}

	public boolean isTechnicalAdmin() {
		return roles.contains(ROLE_TECH_ADMIN);
	}

	
	private String company = "";


	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	
	public String getLanguageCountry(){
		return StringTool.toString(getLocale());
	}
	
	public void setLanguageCountry(String languageCountry){
		setLocale(LocaleTool.getLocaleFromString(languageCountry));
	}
	
		
}
