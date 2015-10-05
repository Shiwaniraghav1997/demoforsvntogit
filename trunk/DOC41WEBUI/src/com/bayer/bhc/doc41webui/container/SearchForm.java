package com.bayer.bhc.doc41webui.container;

import java.util.Collection;
import java.util.List;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.ecim.foundation.basic.StringTool;

public class SearchForm extends CustomizedDocumentForm {
    
    private static final int MAX_RESULTS = 100;
    private static final int MAX_MAX_RESULTS = 5000;
	
	private List<HitListEntry> documents;
	private int maxResults = MAX_RESULTS;
	
	
	/**
     * @param errors  
     */
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

	public int getMaxResults() {
	    if(maxResults<=0 || maxResults>MAX_MAX_RESULTS){
	        maxResults=MAX_RESULTS;
	    }
        return maxResults;
    }
	public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
}
