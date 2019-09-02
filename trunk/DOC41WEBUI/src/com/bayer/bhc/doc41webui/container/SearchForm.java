package com.bayer.bhc.doc41webui.container;

import java.util.Collection;
import java.util.List;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.ecim.foundation.basic.StringTool;

public class SearchForm extends CustomizedDocumentForm {
	private static final int MAX_RESULTS = 500;
	private static final int MAX_MAX_RESULTS = 5000;

	private List<HitListEntry> documents;
	private List<SelectionItem> allowedDocTypes;
	private String docType;
	private int maxResults = MAX_RESULTS;
	/**
	 * The sub-type for PM documents.
	 */
	private int subtype;

	/**
	 * @param errors
	 */
	public void validate(Errors errors) {
		// no check necessary
	}

	public List<HitListEntry> getDocuments() {
		return documents;
	}

	public void setDocuments(List<HitListEntry> documents) {
		this.documents = documents;
	}

	public List<SelectionItem> getAllowedDocTypes() {
		return this.allowedDocTypes;
	}

	public void setAllowedDocTypes(List<SelectionItem> allowedDocTypes) {
		this.allowedDocTypes = allowedDocTypes;
	}

	public String getDocType() {
		return this.docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public boolean isSearchFilled() {
		if (!StringTool.isTrimmedEmptyOrNull(getObjectId())) {
			return true;
		}
		Collection<String> values = getAttributeValues().values();
		for (String value : values) {
			if (!StringTool.isTrimmedEmptyOrNull(value)) {
				return true;
			}
		}

		return false;
	}

	public int getMaxResults() {
		if (maxResults <= 0 || maxResults > MAX_MAX_RESULTS) {
			maxResults = MAX_RESULTS;
		}
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public int getSubtype() {
		return subtype;
	}

	public void setSubtype(int subtype) {
		this.subtype = subtype;
	}
}
