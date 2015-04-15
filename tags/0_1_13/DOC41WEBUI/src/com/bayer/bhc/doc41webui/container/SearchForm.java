package com.bayer.bhc.doc41webui.container;

import java.util.Collection;
import java.util.List;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.ecim.foundation.basic.StringTool;

public class SearchForm extends CustomizedDocumentForm {
	
	private List<HitListEntry> documents;
	
	
	public void validate(Errors errors) {
	  //no check necessary
	}
	
	public List<HitListEntry> getDocuments() {
		return documents;
	}
	
	public void setDocuments(List<HitListEntry> documents) {
		this.documents = documents;
	}

	public boolean isSearchFilled() {
		if(!StringTool.isTrimmedEmptyOrNull(getObjectId())){
			return true;
		}
		Collection<String> values = getAttributeValues().values();
		for (String value : values) {
			if(!StringTool.isTrimmedEmptyOrNull(value)){
				return true;
			}
		}
		
		return false;
	}

}
