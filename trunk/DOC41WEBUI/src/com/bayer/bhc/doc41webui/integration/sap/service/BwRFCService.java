package com.bayer.bhc.doc41webui.integration.sap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.domain.HitListEntry;

@Component
public class BwRFCService extends AbstractSAPJCOService {
	
	private static final String RFC_NAME_FIND_DOCS = "FindDocs";


	public List<HitListEntry> findDocs(String d41id,String sapObj,List<String> objectIds,Map<String, String> attributeValues,int maxResults,boolean maxVersionOnly, Map<Integer, String> seqToKey)
	 throws Doc41ServiceException{
		List<Object> params = new ArrayList<Object>();
		params.add(d41id);
		params.add(sapObj);
		params.add(objectIds);
		params.add(maxResults);
		params.add(maxVersionOnly);
		params.add(attributeValues);
		params.add(seqToKey);
		
		List<HitListEntry> result = performRFC(params, RFC_NAME_FIND_DOCS);
		return result;
	}
}
