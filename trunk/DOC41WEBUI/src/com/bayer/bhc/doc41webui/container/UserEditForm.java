package com.bayer.bhc.doc41webui.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.domain.UserPartner;
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
	private List<UserPartner> partners;
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
		if(partners!=null){
			for (UserPartner partner : partners) {
				if(partner==null || partner.getPartnerNumber()==null){
					errors.rejectValue("partners", "partnerNumberNull", "partner number null");
				}
				if(partner!=null && partner.getPartnerNumber()!=null && partner.getPartnerNumber().length()>Doc41Constants.FIELD_SIZE_PARTNER_NUMBER){
					errors.rejectValue("partners", "partnerNumberTooLong", "partner number too long");
				} else {
					try {
						Integer.parseInt(partner.getPartnerNumber());
					} catch (NumberFormatException e) {
						errors.rejectValue("partners", "OnlyNumbersInPartnerNumber", "only number in partner number allowed");
					}
				}
			}
		}
		if(roles==null && roles.isEmpty()){
			errors.reject("noRoles", "at least one role must be selected.");
		}
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
		setPartners			(user.getPartners());
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
		user.setPartners	(getPartners());
		user.setCountries	(getCountries());
		user.setPlants		(getPlants());
		
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
	
	public List<UserPartner> getPartners() {
		return partners;
	}
	public void setPartners(List<UserPartner> partners) {
		if(partners==null){
			partners = new ArrayList<UserPartner>();
		}
		this.partners = partners;
	}
	
	@Override
	public String toString() {
		return "UserEditForm [objectID=" + objectID + ", cwid=" + cwid
				+ ", surname=" + surname + ", firstname=" + firstname
				+ ", company=" + company + ", email="
				+ email + ", phone=" + phone + ", timeZone=" + timeZone
				+ ", languageCountry=" + languageCountry + ", active=" + active
				+ ", type=" + type + ", roles=" + roles + ", partners="
				+ partners + "]";
	}
	
	public void setPartnerStrings(List<String> partnerStrings){
		List<UserPartner> partnerList = new ArrayList<UserPartner>();
		for (String partnerString : partnerStrings) {
			String[] split = partnerString.split("###");
			UserPartner up = new UserPartner();
			up.setPartnerNumber(split[0]);
			if(split.length>1){
				up.setPartnerName1(split[1]);
				if(split.length>2){
					up.setPartnerName2(split[2]);
					if(split.length>3){
						up.setPartnerType(split[3]);
					}
				}
			}
			
			partnerList.add(up);
		}
		setPartners(partnerList );
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
//			if(split.length>1){
//				up.setPartnerName1(split[1]);
//				if(split.length>2){
//					up.setPartnerName2(split[2]);
//				}
//			}
			
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
//			if(split.length>1){
//				up.setPartnerName1(split[1]);
//				if(split.length>2){
//					up.setPartnerName2(split[2]);
//				}
//			}
			
			plantList.add(plant);
		}
		setPlants(plantList );
	}

}
