package com.bayer.bhc.doc41webui.container;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.ecim.foundation.basic.StringTool;

public class UserEditForm extends AbstractCommandForm {
	
	private static final long serialVersionUID = 2807977121122930112L;
	
	private User user;
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

	@Override
	public void validate(HttpServletRequest request, Errors errors) {
		if(!StringTool.equals(getPassword(), getPasswordRepeated())){
			errors.rejectValue("passwordRepeated", "pwDifferent", "password and passwordRepeated do not match.");
		}
	}
	
	public UserEditForm(User user) {
		super();
		this.user = user;
		
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

	}


	public User copyToDomainUser(){
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
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return "UserEditForm [user=" + user + ", cwid=" + cwid + ", surname="
				+ surname + ", firstname=" + firstname + ", password="
				+ password + ", passwordRepeated=" + passwordRepeated
				+ ", company=" + company + ", email=" + email + ", phone="
				+ phone + ", timeZone=" + timeZone + ", languageCountry="
				+ languageCountry 
				+ ", active=" + active
				+ ", type=" + type + ", roles=" + roles + "]";
	}
	
	
	
}
