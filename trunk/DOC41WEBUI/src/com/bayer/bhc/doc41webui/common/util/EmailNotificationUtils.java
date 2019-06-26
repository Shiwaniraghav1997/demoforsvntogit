package com.bayer.bhc.doc41webui.common.util;

import java.util.List;

import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.domain.SapVendor;

/**
 * @author ETZAJ
 * @version 25.06.2019
 * 
 *          Utility class for the creation of email notification (DW-18).
 * 
 */
public final class EmailNotificationUtils {

	private EmailNotificationUtils() {
	}
	
	public static String findVendorNameByVendorNumber(List<SapVendor> sapVendors, String vendorNumber) {
		String vendorName = null;
		if (sapVendors != null && !sapVendors.isEmpty()) {
			for (SapVendor sapVendor : sapVendors) {
				if (sapVendor.getNumber().equals(vendorNumber)) {
					vendorName = (sapVendor.getName1() != null ? sapVendor.getName1() : sapVendor.getName2());
					break;
				}
			}
		}
		return vendorName;
	}

	public static QmDocumentType convertDocumentType(String documentType) throws Doc41TechnicalException {
		QmDocumentType qmDocumentType = null;
		if (documentType == null || (!documentType.equals(QmDocumentType.COA.getId()) && !documentType.equals(QmDocumentType.COC.getId()) && !documentType.equals(QmDocumentType.OID.getId()))) {
			throw new Doc41TechnicalException(EmailNotificationUtils.class, "QM document type could not be converted.");
		}
		if (documentType.equals(QmDocumentType.COA.getId())) {
			qmDocumentType = QmDocumentType.COA;
		} else if (documentType.equals(QmDocumentType.COC.getId())) {
			qmDocumentType = QmDocumentType.COC;
		} else if (documentType.equals(QmDocumentType.OID.getId())) {
			qmDocumentType = QmDocumentType.OID;
		}
		return qmDocumentType;
	}

}
