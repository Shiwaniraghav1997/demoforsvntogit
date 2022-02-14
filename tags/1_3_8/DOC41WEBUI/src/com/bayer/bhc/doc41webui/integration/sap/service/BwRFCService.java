package com.bayer.bhc.doc41webui.integration.sap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.StringTool;

@Component
public class BwRFCService extends AbstractSAPJCOService {
	
	private static final String RFC_NAME_FIND_DOCS = "FindDocs";
    private static final String RFC_NAME_FIND_DOCS_OLD = "FindDocsOld";
    
    private Properties cConfig = null;

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
		Doc41Log.get().debug(this, null, "FindDocsMulti - d41id=" + d41id + ", sapObj=" + sapObj + ", objectIds=" + objectIds +
		        ", maxResult=" +maxResults + ", maxVersionOnly=" + maxVersionOnly + ", attributeValues=" + attributeValues + ", seqToKey = " + seqToKey);
		if (cConfig == null) {
		    cConfig = ConfigMap.get().getSubCfg("documents");
		}
		String mDisableMaxVersionSearchField = StringTool.emptyToNull(cConfig.getProperty("disableMaxVer.SearchField"));
		if ( (mDisableMaxVersionSearchField != null) && (attributeValues.get(mDisableMaxVersionSearchField) != null) && maxVersionOnly) {
		    maxVersionOnly = false;
	        Doc41Log.get().debug(this, null, "FindDocsMulti - d41id=" + d41id + ", sapObj=" + sapObj + ": MAXVERSION disabled, explicite version specified: " + attributeValues.get(mDisableMaxVersionSearchField));
		}
		params.add(d41id);
		params.add(sapObj); // no more used on findocs multi, expected null!!!
		params.add(objectIds);
		params.add(maxResults);
		params.add(maxVersionOnly);
		params.add(attributeValues);
		params.add(seqToKey);
//		System.out.println("param finddocs ::"+params);
		
		List<HitListEntry> result = performRFC(params, RFC_NAME_FIND_DOCS);
		return result;
	}
	
	
	/**
	 * Serach for Documents, FindDocs2
	 * @param d41id SAP RFC-ID for Doc41, see Types, getSapTypeId
	 * @param sapObj list of the different objectId types for one document type in Doc41, e.g. delivery number, material number... (each of them is searched for the object_Id)
	 * @param objectIds list pf material numbers (specify null - not empty - if no numbers available)
	 * @param attributeValues (additional attributes list - custom attributes)
	 * @param maxResults
	 * @param maxVersionOnly
	 * @param seqToKey (mapping sap keys to attribute names, custom attributes)
	 * @return
	 * @throws Doc41ServiceException
	 * @Deprecated
	 */
	@Deprecated
	    public List<HitListEntry> findDocsOld(String d41id,String sapObj,List<String> objectIds,Map<String, String> attributeValues,int maxResults,boolean maxVersionOnly, Map<Integer, String> seqToKey)
	     throws Doc41ServiceException{
	        List<Object> params = new ArrayList<Object>();
	        params.add(d41id);
	        params.add(sapObj);
	        params.add(objectIds);
	        params.add(maxResults);
	        params.add(maxVersionOnly);
	        params.add(attributeValues);
	        params.add(seqToKey);
	        
	        List<HitListEntry> result = performRFC(params, RFC_NAME_FIND_DOCS_OLD);
	        return result;
	    }

	
}
