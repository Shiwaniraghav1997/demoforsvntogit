/**
 * 
 */
package com.bayer.bhc.doc41webui.domain;

import java.util.Date;

/**
 * @author MBGHY
 *
 */
public class Monitor extends DomainObject {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String desc;
	private String action;
	private String remarks;
	private String details;
	private long responseTime;
	private Date lastRequest;
	private boolean status;
	
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * @return the responseTime
	 */
	public long getResponseTime() {
		return responseTime;
	}
	/**
	 * @param responseTime the responseTime to set
	 */
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}
	/**
	 * @return the lastRequest
	 */
	public Date getLastRequest() {
		return lastRequest;
	}
	/**
	 * @param lastRequest the lastRequest to set
	 */
	public void setLastRequest(Date lastRequest) {
		this.lastRequest = lastRequest;
	}
}
