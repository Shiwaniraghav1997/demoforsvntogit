package com.bayer.bhc.doc41webui.container;

import java.io.Serializable;

/**
 * UserListFilter.
 * 
 * @author ezzqc
 */
public class UserListFilter implements Serializable {
	private static final long serialVersionUID = -3712923928152812412L;

	public static final String FILTER_ALL_OPTIONS = "all";
    
	private String status = FILTER_ALL_OPTIONS;

    private String surname;

    private String cwid;

    private String company;

    private String type;

    private String role;
    
    private String firstname;
    
    private String email;
    
    private String orderBy;

	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCwid() {
		return cwid;
	}
	public void setCwid(String cwid) {
		this.cwid = cwid;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
    
    /**
     *  @Override
     */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ==> UserListFilter .....\n");
		sb.append("\n\t status:\t\t");
		sb.append(status);
		sb.append("\n\t surname:\t\t");
		sb.append(surname);
		sb.append("\n\t lastname:\t\t");
		sb.append(surname);
		sb.append("\n\t cwid:\t\t");
		sb.append(cwid);
		sb.append("\n\t type:\t\t");
		sb.append(type);
		sb.append("\n\t role:\t\t");
		sb.append(role);
		sb.append("\n\n");
		return sb.toString();
	}
    
    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.domain.container.ResetableForm#reset()
     */
    public void reset() {
        status = FILTER_ALL_OPTIONS;
        surname = null;
        cwid = null;
        company = null;
        type = FILTER_ALL_OPTIONS;
        role = FILTER_ALL_OPTIONS;
    }
 
 }
