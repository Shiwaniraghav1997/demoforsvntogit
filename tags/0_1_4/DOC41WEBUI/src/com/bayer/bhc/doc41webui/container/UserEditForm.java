package com.bayer.bhc.doc41webui.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.domain.SapCustomer;
import com.bayer.bhc.doc41webui.domain.SapVendor;
import com.bayer.bhc.doc41webui.domain.User;
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
	

	public void validate(HttpServletRequest request, Errors errors) {
		if(StringTool.equals(getType(), User.TYPE_EXTERNAL)){
			checkMandatory("surname",getSurname(),errors);
			checkMandatory("firstname",getFirstname(),errors);
			checkMandatory("email",getEmail(),errors);
		}
		if(!StringTool.equals(getPassword(), getPasswordRepeated())){
			errors.rejectValue("passwordRepeated", "pwDifferent", "password and passwordRepeated do not match.");
		}
		if(customers!=null){
			for (SapCustomer customer : customers) {
				if(customer==null || customer.getNumber()==null){
					errors.rejectValue("customers", "customerNumberNull", "customer number null");
				}
				if(customer!=null && customer.getNumber()!=null && customer.getNumber().length()>Doc41Constants.FIELD_SIZE_CUSTOMER_NUMBER){
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
				}
				if(vendor!=null && vendor.getNumber()!=null && vendor.getNumber().length()>Doc41Constants.FIELD_SIZE_VENDOR_NUMBER){
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
		if(roles==null && roles.isEmpty()){
			errors.reject("noRoles", "at least one role must be selected.");
		}
		
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
	}

	private static boolean isEmpty(List<?> list) {
		return (list==null || list.isEmpty());
	}

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

	public void setCwid(String cwid) {
		this.cwid = cwid;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(Long timeZone) {
		this.timeZone = timeZone;
	}

	public String getLanguageCountry() {
		return languageCountry;
	}
	
	public void setLanguageCountry(String languageCountry) {
		this.languageCountry = languageCountry;
	}
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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
	
	public Long getObjectID() {
		return objectID;
	}
	public void setObjectID(Long objectID) {
		this.objectID = objectID;
	}
	
	public List<SapCustomer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<SapCustomer> customers) {
		if(customers==null){
			customers = new ArrayList<SapCustomer>();
		}
		this.customers = customers;
	}
	
	public List<SapVendor> getVendors() {
		return vendors;
	}
	public void setVendors(List<SapVendor> vendors) {
		if(vendors==null){
			vendors = new ArrayList<SapVendor>();
		}
		this.vendors = vendors;
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
		setCustomers(customerList );
	}
	
	public void setVendorStrings(List<String> vendorStrings){
		List<SapVendor> vendorList = new ArrayList<SapVendor>();
		for (String vendorString : vendorStrings) {
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
		setVendors(vendorList );
	}
	
	public List<String> getCountries() {
		return countries;
	}
	public void setCountries(List<String> countries) {
		this.countries = countries;
	}
	
	public void setCountryStrings(List<String> countryStrings){
		List<String> countryList = new ArrayList<String>();
		for (String countryString : countryStrings) {
			String[] split = countryString.split("###");
			String country = split[0];
			
			countryList.add(country);
		}
		setCountries(countryList );
	}
	
	
	public List<String> getPlants() {
		return plants;
	}
	public void setPlants(List<String> plants) {
		this.plants = plants;
	}
	
	public void setPlantStrings(List<String> plantStrings){
		List<String> plantList = new ArrayList<String>();
		for (String plantString : plantStrings) {
			String[] split = plantString.split("###");
			String plant = split[0];
			
			plantList.add(plant);
		}
		setPlants(plantList );
	}

}
