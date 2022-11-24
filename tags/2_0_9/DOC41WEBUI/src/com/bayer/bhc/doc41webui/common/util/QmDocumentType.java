package com.bayer.bhc.doc41webui.common.util;

/**
 * @author ETZAJ
 * @version 25.06.2019
 * 
 *          This class contains IDs and titles for QM document types used for
 *          creation of email notifications in DW-18.
 * 
 */
public enum QmDocumentType {

	COA("DOC41.39", "CoA Certificate of Analysis external"), COC("DOC41.55", "CoC Certificate of Conformance external"), OID("DOC41.71", "Other Inbound documents");

	private String id;
	private String title;

	private QmDocumentType(String id, String title) {
		this.id = id;
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

}