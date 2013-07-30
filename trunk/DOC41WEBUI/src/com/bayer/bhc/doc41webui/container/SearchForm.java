package com.bayer.bhc.doc41webui.container;

import java.util.List;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.domain.HitListEntry;

public class SearchForm extends CustomizedDocumentForm {
	
	private List<HitListEntry> documents;
	
	
	public void validate(Errors errors) {
		
	}
	
	public List<HitListEntry> getDocuments() {
		return documents;
	}
	
	public void setDocuments(List<HitListEntry> documents) {
		this.documents = documents;
		for (HitListEntry document : documents) {
			document.initCustValuesMap(getAttributeSeqToKey());
		}
	}

}
