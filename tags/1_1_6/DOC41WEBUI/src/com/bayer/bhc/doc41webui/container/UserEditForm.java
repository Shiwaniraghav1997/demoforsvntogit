package com.bayer.bhc.doc41webui.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41RuntimeExceptionBase;
import com.bayer.bhc.doc41webui.domain.Profile;
import com.bayer.bhc.doc41webui.domain.SapCustomer;
import com.bayer.bhc.doc41webui.domain.SapVendor;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.UserManagementUC;
import com.bayer.ecim.foundation.basic.ReflectTool;
import com.bayer.ecim.foundation.basic.StringTool;

public class UserEditForm implements Serializable{
	
	private static final long serialVersionUID = 618389489171249351L;
	
	private Long objectID;
	private String cwid;
	private String surname;
	private String firstname;
	private String password;
	private String passwordRepeated;
	private String company;
	private String email;
	private String phone;
	private Long timeZone;
	private String languageCountry;
	private Boolean active;
	private String type;
	private List<String> roles;
	private List<SapCustomer> customers;
	private List<SapVendor> vendors;
	private List<String> countries;
	private List<String> plants;
	private List<Profile> existingRoles;
    private List<String> existingRoleNames;
	

	public void validate(@SuppressWarnings("unused") HttpServletRequest request, Errors errors, UserManagementUC umUC) {
		boolean isExternal = isExternal();
		boolean isNew = StringTool.isTrimmedEmptyOrNull(getCwid());
        if(isExternal){
			checkMandatory("surname",getSurname(),errors);
			checkMandatory("firstname",getFirstname(),errors);
			checkMandatory("email",getEmail(),errors);
			if(isNew){
			    checkMandatory("password",getPassword(),errors);
			    checkMandatory("passwordRepeated",getPasswordRepeated(),errors);
			}
		}
		if(!StringTool.equals(getPassword(), getPasswordRepeated())){
			errors.rejectValue("passwordRepeated", "pwDifferent", "password and passwordRepeated do not match.");
		}
		if(customers!=null){
			for (SapCustomer customer : customers) {
				if(customer==null || customer.getNumber()==null){
					errors.rejectValue("customers", "customerNumberNull", "customer number null");
				} else if(customer.getNumber().length()>Doc41Constants.FIELD_SIZE_CUSTOMER_NUMBER){
					errors.rejectValue("customers", "customerNumberTooLong", "customer number too long");
				} else {
					try {
						Integer.parseInt(customer.getNumber());
					} catch (NumberFormatException e) {
						errors.rejectValue("customers", "OnlyNumbersInCustomersNumber", "only number in customer number allowed");
					}
				}
			}
		}
		if(vendors!=null){
			for (SapVendor vendor : vendors) {
				if(vendor==null || vendor.getNumber()==null){
					errors.rejectValue("vendors", "vendorNumberNull", "vendor number null");
				} else if(vendor.getNumber().length()>Doc41Constants.FIELD_SIZE_VENDOR_NUMBER){
					errors.rejectValue("vendors", "vendorNumberTooLong", "vendor number too long");
				} else {
					try {
						Integer.parseInt(vendor.getNumber());
					} catch (NumberFormatException e) {
						errors.rejectValue("vendors", "OnlyNumbersInVendorNumber", "only number in vendor number allowed");
					}
				}
			}
		}
		if(roles==null || roles.isEmpty()){
			errors.reject("noRoles", "at least one role must be selected.");
		}
		String[] rolesArr = (roles == null) ? new String[0] : roles.toArray(new String[0]); 

		try {
		    
		    if (umUC.userNeedsCustomers(rolesArr) && isEmpty(customers)) {
		        errors.rejectValue("customers", "customerPartnerNeededForRole", "for the selected roles at least one customer is required");
		    }

            if (umUC.userNeedsVendors(rolesArr) && isEmpty(vendors)) {
                errors.rejectValue("vendors", "vendorPartnerNeededForRole", "for the selected roles at least one vendor is required");
            }
		    
            if (umUC.userNeedsCountries(rolesArr) && isEmpty(countries)) {
                errors.rejectValue("countries", "countryNeededForRole", "for the selected roles at least one country is required");
            }
		    
            if (umUC.userNeedsPlants(rolesArr) && isEmpty(plants)) {
                errors.rejectValue("plants", "plantNeededForRole", "for the selected roles at least one plant is required");
            }

		} catch ( Exception e ) {
		    throw new Doc41RuntimeExceptionBase("Validation of User edit failed!", e);
		}
		    
/*		
		if(containsRoleFromList(roles,User.ROLES_WITH_CUSTOMERS) && isEmpty(customers)){
			errors.rejectValue("customers", "customerPartnerNeededForRole", "for the selected roles at least one customer is required");
		}
		
		if(containsRoleFromList(roles,User.ROLES_WITH_VENDORS) && isEmpty(vendors)){
			errors.rejectValue("vendors", "vendorPartnerNeededForRole", "for the selected roles at least one vendor is required");
		}
		
		if(containsRoleFromList(roles,User.ROLES_WITH_COUNTRY) && isEmpty(countries)){
			errors.rejectValue("countries", "countryNeededForRole", "for the selected roles at least one country is required");
		}
		
		if(containsRoleFromList(roles,User.ROLES_WITH_PLANT) && isEmpty(plants)){
			errors.rejectValue("plants", "plantNeededForRole", "for the selected roles at least one plant is required");
		}
*/
	}

	private static boolean isEmpty(List<?> list) {
		return list==null || list.isEmpty();
	}

	/*
	private static boolean containsRoleFromList(List<String> userRoles,
			String[] rolesToCheck) {
		if(userRoles==null){
			return false;
		}
		for (String roleToCheck : rolesToCheck) {
			if(userRoles.contains(roleToCheck)){
				return true;
			}
		}
		return false;
	}
	*/

	private void checkMandatory(String field, String value, Errors errors) {
		if(StringTool.isTrimmedEmptyOrNull(value)){
			errors.rejectValue(field, ""+field+"Missing", ""+field+" is mandatory");
		}
		
	}

	public UserEditForm(){
		super();
	}
	
	public UserEditForm(User user) {
		super();
		
		setFirstname		(user.getFirstname());
		setSurname			(user.getSurname());  
		setCwid				(user.getCwid());     
		setPassword			(user.getPassword()); 
		setPasswordRepeated(user.getPassword()); 
		setCompany			(user.getCompany());  
		setEmail			(user.getEmail());    
		setPhone			(user.getPhone());    
		setTimeZone			(user.getTimeZone()); 
		setLanguageCountry	(StringTool.toString(user.getLocale()));
		setActive			(user.getActive());   
		setType				(user.getType());     
		setRoles			(user.getRoles());  
		setCustomers		(user.getCustomers());
		setVendors			(user.getVendors());
		setCountries		(user.getCountries());
		setPlants			(user.getPlants());
		setObjectID(user.getDcId());

	}


	public User copyToDomainUser(){
		User user = new User();
		user.setFirstname	(getFirstname	());
		user.setSurname		(getSurname		());
		user.setCwid		(getCwid		());
		user.setPassword	(getPassword	());
		user.setCompany		(getCompany		());
		user.setEmail		(getEmail		());
		user.setPhone		(getPhone		());
		user.setTimeZone	(getTimeZone	());
		user.setLanguageCountry(getLanguageCountry());
		user.setActive		(getActive		());
		user.setType		(getType		());
		user.setRoles		(getRoles		());
		user.setCustomers	(getCustomers	());
		user.setVendors		(getVendors		());
		user.setCountries	(getCountries	());
		user.setPlants		(getPlants		());
		
		return user;
	}

	public String getCwid() {
		return cwid;
	}

	public final void setCwid(String cwid) {
		this.cwid = cwid;
	}

	public String getSurname() {
		return surname;
	}

	public final void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFirstname() {
		return firstname;
	}

	public final void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeated() {
		return passwordRepeated;
	}

	public final void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}

	public String getCompany() {
		return company;
	}

	public final void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public final void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public final void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getTimeZone() {
		return timeZone;
	}

	public final void setTimeZone(Long timeZone) {
		this.timeZone = timeZone;
	}

	public String getLanguageCountry() {
		return languageCountry;
	}
	
	public final void setLanguageCountry(String languageCountry) {
		this.languageCountry = languageCountry;
	}
	
	public Boolean getActive() {
		return active;
	}

	public final void setActive(Boolean active) {
		this.active = active;
	}

	public boolean isExternal() {
	    return StringTool.equals(getType(), User.TYPE_EXTERNAL);
	}
	
	public String getType() {
		return type;
	}

	public final void setType(String type) {
		this.type = type;
	}
	
	public List<String> getRoles() {
		return roles;
	}
	
	public final void setRoles(List<String> roles) {
		if(roles==null){
			roles = new ArrayList<String>();
		}
		this.roles = roles;
	}
	
	public Long getObjectID() {
		return objectID;
	}
	public final void setObjectID(Long objectID) {
		this.objectID = objectID;
	}
	
	public List<SapCustomer> getCustomers() {
		return customers;
	}
	public final void setCustomers(List<SapCustomer> customers) {
		if(customers==null){
			customers = new ArrayList<SapCustomer>();
		}
		this.customers = customers;
	}
	
	public List<SapVendor> getVendors() {
		return vendors;
	}
	public final void setVendors(List<SapVendor> vendors) {
		if(vendors==null){
			vendors = new ArrayList<SapVendor>();
		}
		this.vendors = vendors;
	}
	
    public List<Profile> getExistingRoles() {
        return existingRoles;
    }
    public final void setExistingRoles(List<Profile> existingRoles) {
        if(existingRoles==null){
            existingRoles = new ArrayList<Profile>();
        }
        for (Profile role : existingRoles) {
            role.setUserTypeMatchesRoleByUser( isExternal() );
        }
        this.existingRoles = existingRoles;
    }
    
    public List<String> getExistingRoleNames() {
        return existingRoleNames;
    }
    public final void setExistingRoleNames(List<String> existingRoleNames) {
        if(existingRoleNames==null){
            existingRoleNames = new ArrayList<String>();
        }
        this.existingRoleNames = existingRoleNames;
    }
    
    /**
     * Map of all declared ROLE constants to their real role names (created on first request, for supporting constant access on EL 2.1).
     */
    private static Map<String,String> cRoleConstToNameMap = null;
    
    /**
     * get a Map of Constants to Realnames of roles.
     * @return
     */
    public Map<String, String> getRoleConstToNameMap() {
        if (cRoleConstToNameMap == null) {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            HashMap<String, String> hashMap = (HashMap<String, String>)(HashMap)ReflectTool.getStaticFieldMap(User.class, true, "ROLE_", null, false);
            cRoleConstToNameMap = hashMap; 
        }
        return cRoleConstToNameMap;
    }
    
    /**
     * Map of all declared ROLE names to their constant names (created on first request, for supporting constant access on EL 2.1).
     */
    private static Map<String,String> cRoleNameToConstMap = null;
    
    /**
     * get a Map of Constants to Realnames of roles.
     * @return
     */
    public Map<String, String> getRoleNameToConstMap() {
        if (cRoleNameToConstMap == null) {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            HashMap<String, String> hashMap = (HashMap<String, String>)(HashMap)ReflectTool.getStaticFieldMap(User.class, true, "ROLE_", null, true);
            cRoleNameToConstMap = hashMap; 
        }
        return cRoleNameToConstMap;
    }
    
    private Map<String, Integer> existingRoleMap = null;
    
    /**
     * Return Map of Role-Constants and assigned value Integer/String 1 for all Roles existing and having declared constant. 
     * @return
     * @deprecated Will be soon replaced by iteration ofer ProfileList...
     */
    @Deprecated
    public Map<String,Integer> getExistingRoleMap() {
        if (existingRoleMap == null) {
            HashMap<String, Integer> mRes = new HashMap<String, Integer>();
            if (existingRoleNames != null) {
                Map<String, String> mValToConst = getRoleNameToConstMap();
                for (String role : existingRoleNames) {
                    String mConst = mValToConst.get(role);
                    if (mConst != null) {
                        mRes.put(mConst, 1);
                    }
                }
            }
//            return mRes;
            existingRoleMap = mRes;
        }
        return existingRoleMap;
    }
    
    
	@Override
	public String toString() {
		return "UserEditForm [objectID=" + objectID + ", cwid=" + cwid
				+ ", surname=" + surname + ", firstname=" + firstname
				+ ", company=" + company + ", email="
				+ email + ", phone=" + phone + ", timeZone=" + timeZone
				+ ", languageCountry=" + languageCountry + ", active=" + active
				+ ", type=" + type + ", roles=" + roles 
				+ ", customers="+ customers 
				+ ", vendors="+ vendors
				+ "]";
	}
	
	public void setCustomerStrings(List<String> customerStrings){
		List<SapCustomer> customerList = new ArrayList<SapCustomer>();
		for (String customerString : customerStrings) {
		    if(!StringTool.isTrimmedEmptyOrNull(customerString)){
    			String[] split = customerString.split("###");
    			SapCustomer up = new SapCustomer();
    			up.setNumber(split[0]);
    			if(split.length>1){
    				up.setName1(split[1]);
    				if(split.length>2){
    					up.setName2(split[2]);
    				}
    			}
    			
    			customerList.add(up);
		    }
		}
		setCustomers(customerList );
	}
	
	public void setVendorStrings(List<String> vendorStrings){
		List<SapVendor> vendorList = new ArrayList<SapVendor>();
		for (String vendorString : vendorStrings) {
		    if(!StringTool.isTrimmedEmptyOrNull(vendorString)){
    			String[] split = vendorString.split("###");
    			SapVendor up = new SapVendor();
    			up.setNumber(split[0]);
    			if(split.length>1){
    				up.setName1(split[1]);
    				if(split.length>2){
    					up.setName2(split[2]);
    				}
    			}
    			
    			vendorList.add(up);
		    }
		}
		setVendors(vendorList );
	}
	
	public List<String> getCountries() {
		return countries;
	}
	public final void setCountries(List<String> countries) {
		this.countries = countries;
	}
	
	public void setCountryStrings(List<String> countryStrings){
		List<String> countryList = new ArrayList<String>();
		for (String countryString : countryStrings) {
		    if(!StringTool.isTrimmedEmptyOrNull(countryString)){
    			String[] split = countryString.split("###");
    			String country = split[0];
    			
    			countryList.add(country);
		    }
		}
		setCountries(countryList );
	}
	
	
	public List<String> getPlants() {
		return plants;
	}
	public final void setPlants(List<String> plants) {
		this.plants = plants;
	}
	
	public void setPlantStrings(List<String> plantStrings){
		List<String> plantList = new ArrayList<String>();
		for (String plantString : plantStrings) {
		    if(!StringTool.isTrimmedEmptyOrNull(plantString)){
    			String[] split = plantString.split("###");
    			String plant = split[0];
    			
    			plantList.add(plant);
		    }
		}
		setPlants(plantList );
	}

}
