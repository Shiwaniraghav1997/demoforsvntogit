package com.bayer.bhc.doc41webui.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.TimeZone;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
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
	
	public static final String ROLE_MATERIAL_SUPPLIER = "doc41_matsup";
	
	public static final String ROLE_PRODUCT_SUPPLIER = "doc41_prodsup";
	
	public static final String ROLE_DEL_CERT_VIEWER_COUNTRY = "doc41_delcertvcountry";
	
	public static final String ROLE_DEL_CERT_VIEWER_CUSTOMER = "doc41_delcertvcust";
	
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
			ROLE_CARRIER, ROLE_CUSTOMS_BROKER, ROLE_MATERIAL_SUPPLIER, ROLE_PRODUCT_SUPPLIER,
			ROLE_DEL_CERT_VIEWER_COUNTRY, ROLE_DEL_CERT_VIEWER_CUSTOMER, ROLE_LAYOUT_SUPPLIER,
			ROLE_PM_SUPPLIER,ROLE_BUSINESS_ADMIN, ROLE_TECH_ADMIN, ROLE_OBSERVER };
	
	public static final String[] ROLES_WITH_CUSTOMERS = new String[] {ROLE_DEL_CERT_VIEWER_CUSTOMER};
	
	public static final String[] ROLES_WITH_VENDORS = new String[] {
		ROLE_CARRIER, ROLE_MATERIAL_SUPPLIER, ROLE_PRODUCT_SUPPLIER,
		ROLE_LAYOUT_SUPPLIER, ROLE_PM_SUPPLIER };
	
	public static final String[] ROLES_WITH_COUNTRY = new String[] {ROLE_DEL_CERT_VIEWER_COUNTRY};
	
	public static final String[] ROLES_WITH_PLANT = new String[] {
		ROLE_MATERIAL_SUPPLIER, ROLE_PRODUCT_SUPPLIER };

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
	
	private List<SapVendor> vendors = new ArrayList<SapVendor>();
	
	private List<SapCustomer> customers = new ArrayList<SapCustomer>();
	
	private List<String> countries = new ArrayList<String>();
	
	private List<String> plants = new ArrayList<String>();
	
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
			Doc41Log.get().error(getClass(),UserInSession.getCwid(),e);
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
		if(roles==null){
			roles = new ArrayList<String>();
		}
		this.roles = roles;
	}
	
	public List<SapVendor> getVendors() {
		return vendors;
	}
	
	public void setVendors(List<SapVendor> vendors) {
		if(vendors ==null){
			vendors = new ArrayList<SapVendor>();
		}
		this.vendors = vendors;
	}
	
	public List<SapCustomer> getCustomers() {
		return customers;
	}
	
	public void setCustomers(List<SapCustomer> customers) {
		if(customers ==null){
			customers = new ArrayList<SapCustomer>();
		}
		this.customers = customers;
	}
	
	public List<String> getCountries() {
		return countries;
	}
	
	public void setCountries(List<String> countries) {
		if(countries ==null){
			countries = new ArrayList<String>();
		}
		this.countries = countries;
	}
	
	public List<String> getPlants() {
		return plants;
	}
	
	public void setPlants(List<String> plants) {
		this.plants = plants;
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
				+ ", type=" + type + ", active=" + active + ", roles=" + roles
				+ ", customers=" + customers + ", vendors=" + vendors
				+ ", countries=" + countries
				+ ", plants=" + plants + ", locale=" + locale + ", readOnly="
				+ readOnly + ", timeZone=" + timeZone + ", permissions="
				+ permissions + ", company=" + company + "]";
	}
	
	public String getWebMetrixOutput() {
		return "cwid=" + cwid + ", surname=" + surname + ", firstname="
				+ firstname + ", email=" + email + ", phone=" + phone
				+ ", type=" + type + ", active=" + active + ", roles=" + roles
				+ ", customers=" + getCustomerNumbers()+ ", vendors=" + getVendorNumbers() + ", countries=" + countries
				+ ", plants=" + plants + ", locale=" + locale + ", readOnly="
				+ readOnly + ", timeZone=" + timeZone + ", company=" + company;
	}
	
	private List<String> getCustomerNumbers() {
		if(customers==null){
			return null;
		}
		List<String> numbers = new ArrayList<String>();
		for (SapCustomer up : customers) {
			numbers.add(up.getNumber());
		}
		return numbers;
	}
	
	private List<String> getVendorNumbers() {
		if(vendors==null){
			return null;
		}
		List<String> numbers = new ArrayList<String>();
		for (SapVendor up : vendors) {
			numbers.add(up.getNumber());
		}
		return numbers;
	}

	public boolean hasCustomer(String customerNumber){
		if(customers!=null){
			for (SapCustomer userCustomer : customers) {
				if(StringTool.equals(customerNumber, userCustomer.getNumber())){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasVendor(String vendorNumber){
		if(vendors!=null){
			for (SapVendor userVendor : vendors) {
				if(StringTool.equals(vendorNumber, userVendor.getNumber())){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasCountry(String countryCode){
		if(countries!=null){
			for (String oneCountryCode : countries) {
				if(StringTool.equals(countryCode, oneCountryCode)){
					return true;
				}
			}
		}
		return false;
	}

}
