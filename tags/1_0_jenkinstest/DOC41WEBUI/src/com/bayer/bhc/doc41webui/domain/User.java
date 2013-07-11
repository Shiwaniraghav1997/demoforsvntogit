package com.bayer.bhc.doc41webui.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.TimeZone;
import com.bayer.ecim.foundation.basic.BasicDataCarrier;
import com.bayer.ecim.foundation.basic.LocaleTool;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.dbx.QueryException;
import com.bayer.ecim.foundation.dbx.ResultObject;
import com.bayer.ecim.foundation.web.usermanagementN.OTUserManagementN;
import com.bayer.ecim.foundation.web.usermanagementN.UMPermissionNDC;

/**
 * User domain object.
 * 
 * @author ezzqc
 */
public class User extends DomainObject {

	private static final long serialVersionUID = -88472347824568763L;

	public static final String ROLE_BUSINESS_ADMIN = "doc41_badm";

	public static final String ROLE_TECH_ADMIN = "doc41_tadm";

	public static final String ROLE_CARRIER = "doc41_carr";
	
	public static final String ROLE_CUSTOMS_BROKER = "doc41_cusbr";
	
	public static final String ROLE_LAYOUT_SUPPLIER = "doc41_laysup";
	
	public static final String ROLE_PM_SUPPLIER = "doc41_pmsup";
	
	public static final String ROLE_OBSERVER = "doc41_obsv";

	public static final String TYPE_INTERNAL = "internal";

	public static final String TYPE_EXTERNAL = "external";

	public static final String STATUS_ACTIVE = "active";

	public static final String STATUS_INACTIVE = "inactive";

	// legacy list:
	// public static final String[] ALL_ROLES = new String[] { ROLE_TECH_ADMIN,
	// ROLE_OPER_ADMIN,
	// ROLE_OPER_STAFF, ROLE_CARRIER, ROLE_EXPEDITION, ROLE_OBSERVER };

	public static final String[] ALL_ROLES = new String[] {
			ROLE_CARRIER, ROLE_CUSTOMS_BROKER, ROLE_LAYOUT_SUPPLIER, ROLE_PM_SUPPLIER,ROLE_BUSINESS_ADMIN, ROLE_TECH_ADMIN, ROLE_OBSERVER };

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

	private List<String> permissions = new ArrayList<String>();
	
	public boolean hasPermission(String ... permissions) {
		for (String permission : permissions) {
			if (getPermissions().contains(permission)) {
				return true;
			}
		}

		// Check if permissions exists, throw Error otherwise
		try {
			ResultObject ro = OTUserManagementN.get().getPermissions(null, null, null, null, null, null, null, -1, -1, null, null, null, null, LocaleInSession.get());
			List<String> allPermissions = new ArrayList<String>();
			for (BasicDataCarrier basicDC : ro.getAsArrayResult()) {
				UMPermissionNDC permission = (UMPermissionNDC) basicDC;
				allPermissions.add(permission.getCode());
			}
			for (String permission : permissions) {
				if (!allPermissions.contains(permission)) {
					throw new RuntimeException(String.format("Permission %s doesn't exist.", permission));
				}
			}
		} catch (QueryException e) {
			e.printStackTrace();
		}
	
		return false;
	}
	
	public boolean hasRole(String role) {
		return roles.contains(role);
	}

	
//	private Boolean automaticClose = false;
//	private int closeAfter;

	// convenience method:
	public boolean isExternalUser() {
		return getType().equals(User.TYPE_EXTERNAL);
	}
	
//	public Boolean getAutomaticClose() {
//		return automaticClose;
//	}
//
//	public void setAutomaticClose(Boolean automaticClose) {
//		this.automaticClose = automaticClose;
//	}
//
//	public int getCloseAfter() {
//		return closeAfter;
//	}
//
//	public void setCloseAfter(int closeAfter) {
//		this.closeAfter = closeAfter;
//	}

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

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
//	public boolean isBusinessAdmin() {
//		return roles.contains(ROLE_BUSINESS_ADMIN);
//	}
//
//	public boolean isTechnicalAdmin() {
//		return roles.contains(ROLE_TECH_ADMIN);
//	}
//
//	public boolean isCarrier() {
//		return roles.contains(ROLE_CARRIER);
//	}
//	
//	public boolean isCustomsBroker() {
//		return roles.contains(ROLE_CUSTOMS_BROKER);
//	}
//	
//	public boolean isLayoutSupplier() {
//		return roles.contains(ROLE_LAYOUT_SUPPLIER);
//	}
//	
//	public boolean isPmSupplier() {
//		return roles.contains(ROLE_PM_SUPPLIER);
//	}
//	
//	public boolean isObserver() {
//		return roles.contains(ROLE_OBSERVER);
//	}
	
	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
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

	/**
	 * Needed for EL in JSPs
	 * @return
	 */
	public String[] getALL_ROLES() {
		return User.ALL_ROLES;
	}

	@Override
	public String toString() {
		return "User [cwid=" + cwid + ", surname=" + surname + ", firstname="
				+ firstname + ", email=" + email + ", phone=" + phone
				+ ", type=" + type + ", active=" + active + ", password="
				+ password + ", passwordRepeated=" + passwordRepeated
				+ ", locale=" + locale + ", readOnly=" + readOnly
				+ ", timeZone=" + timeZone + ", permissions=" + permissions
				+ ", company=" + company + "]";
	}
	
}
