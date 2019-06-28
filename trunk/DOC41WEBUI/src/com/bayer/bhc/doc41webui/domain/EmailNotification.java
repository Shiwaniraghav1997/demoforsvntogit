package com.bayer.bhc.doc41webui.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import com.bayer.bhc.doc41webui.common.util.QmDocumentType;

/**
 * @author ETZAJ
 * @version 27.06.2019
 * @ticket DW-18
 * 
 *         This class represents an email notification. It contains information
 *         about the document upload, required for filling the corresponding
 *         email template.
 * 
 */
public class EmailNotification {

	private LocalDateTime timestamp;
	private String documentName;
	private String vendorName;
	private String vendorNumber;
	private String username;
	private String materialNumber;
	private String batch;
	private String purchaseOrderNumber;
	private QmDocumentType documentType;
	private String documentIdentification;

	public EmailNotification() {

	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorNumber() {
		return vendorNumber;
	}

	public void setVendorNumber(String vendorNumber) {
		this.vendorNumber = vendorNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
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

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof EmailNotification)) {
			return false;
		}
		EmailNotification emailNotification = (EmailNotification) o;
		return Objects.equals(documentName, emailNotification.getDocumentName()) && Objects.equals(vendorName, emailNotification.getVendorName()) && Objects.equals(vendorNumber, emailNotification.getVendorNumber()) && Objects.equals(username, emailNotification.getUsername()) && Objects.equals(materialNumber, emailNotification.getMaterialNumber()) && Objects.equals(batch, emailNotification.getBatch()) && Objects.equals(purchaseOrderNumber, emailNotification.getPurchaseOrderNumber()) && Objects.equals(documentType, emailNotification.getDocumentType()) && Objects.equals(documentIdentification, emailNotification.getDocumentIdentification());
	}

}
