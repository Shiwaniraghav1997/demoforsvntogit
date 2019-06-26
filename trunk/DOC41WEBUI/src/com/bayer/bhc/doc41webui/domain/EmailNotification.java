package com.bayer.bhc.doc41webui.domain;

import java.time.LocalDateTime;

import com.bayer.bhc.doc41webui.common.util.QmDocumentType;

/**
 * @author ETZAJ
 * @version 25.06.2019
 * 
 *          This class contains what is required for sending an e-mail
 *          notification for QM document types (DW-18).
 * 
 */
public class EmailNotification {

	private String cwid;
	private String emailAddress;
	private String vendorNumber;
	private String vendorName;
	private String batch;
	private String materialNumber;
	private String purchaseOrderNumber;
	private String documentName;
	private QmDocumentType documentType;
	private String documentIdentification;
	private String username;
	private LocalDateTime timestamp;

	public EmailNotification(String cwid, String vendorNumber, String vendorName, String batch, String materialNumber, String purchaseOrderNumber, String documentName, QmDocumentType documentType, String documentIdentification, String username, LocalDateTime timestamp) {
		this.cwid = cwid;
		this.vendorNumber = vendorNumber;
		this.vendorName = vendorName;
		this.batch = batch;
		this.materialNumber = materialNumber;
		this.purchaseOrderNumber = purchaseOrderNumber;
		this.documentName = documentName;
		this.documentType = documentType;
		this.documentIdentification = documentIdentification;
		this.username = username;
		this.timestamp = timestamp;
	}

	public String getCwid() {
		return cwid;
	}

	public void setCwid(String cwid) {
		this.cwid = cwid;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getVendorNumber() {
		return vendorNumber;
	}

	public void setVendorNumber(String vendorNumber) {
		this.vendorNumber = vendorNumber;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public QmDocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(QmDocumentType documentType) {
		this.documentType = documentType;
	}

	public String getDocumentIdentification() {
		return documentIdentification;
	}

	public void setDocumentIdentification(String documentIdentification) {
		this.documentIdentification = documentIdentification;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
