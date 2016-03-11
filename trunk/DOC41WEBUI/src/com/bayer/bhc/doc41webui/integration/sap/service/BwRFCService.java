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

	/**
	 * Serach for Documents, FindDocs2, new: FindDocsMulti
	 * @param d41id SAP RFC-ID for Doc41, see Types, getSapTypeId
	 * @param sapObj deprecated (no more used on FindDocsMulti): list of the different objectId types for one document type in Doc41, e.g. delivery number, material number... (each of them is searched for the object_Id, on new rfc no more specified = ignored)
	 * @param objectIds list pf material numbers (specify null - not empty - if no numbers available)
	 * @param attributeValues (additional attributes list - custom attributes)
	 * @param maxResults
	 * @param maxVersionOnly
	 * @param seqToKey (mapping sap keys to attribute names, custom attributes)
	 * @return
	 * @throws Doc41ServiceException
	 */
	 public List<HitListEntry> findDocs(String d41id,String sapObj,List<String> objectIds,Map<String, String> attributeValues,int maxResults,boolean maxVersionOnly, Map<Integer, String> seqToKey)
	   throws Doc41ServiceException {
	     ArrayList<String> d41idList = new ArrayList<String>();
	     d41idList.add(d41id);
	     return findDocs(d41idList, sapObj, objectIds, attributeValues, maxResults, maxVersionOnly, seqToKey);
	 }
	 
	 
    /**
     * Serach for Documents, FindDocs2, new: FindDocsMulti
     * @param d41id LIST, SAP RFC-ID for Doc41, see Types, getSapTypeId
     * @param sapObj deprecated (no more used on FindDocsMulti): list of the different objectId types for one document type in Doc41, e.g. delivery number, material number... (each of them is searched for the object_Id, on new rfc no more specified = ignored)
     * @param objectIds list pf material numbers (specify null - not empty - if no numbers available)
     * @param attributeValues (additional attributes list - custom attributes)
     * @param maxResults
     * @param maxVersionOnly
     * @param seqToKey (mapping sap keys to attribute names, custom attributes)
     * @return
     * @throws Doc41ServiceException
     */
	public List<HitListEntry> findDocs(List<String> d41id,String sapObj,List<String> objectIds,Map<String, String> attributeValues,int maxResults,boolean maxVersionOnly, Map<Integer, String> seqToKey)
	  throws Doc41ServiceException{
		List<Object> params = new ArrayList<Object>();
		params.add(d41id);
		params.add(sapObj); // no more used on findocs multi, expected null!!!
		params.add(objectIds);
		params.add(maxResults);
		params.add(maxVersionOnly);
		params.add(attributeValues);
		params.add(seqToKey);
		
		List<HitListEntry> result = performRFC(params, RFC_NAME_FIND_DOCS);
		return result;
	}
	
}
