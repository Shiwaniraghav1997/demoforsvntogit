package com.bayer.bhc.doc41webui.integration.sap.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.ContentRepositoryInfo;
import com.bayer.bhc.doc41webui.domain.DocMetadata;
import com.bayer.bhc.doc41webui.domain.DocTypeDef;
import com.bayer.bhc.doc41webui.domain.DocumentStatus;
import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.bhc.doc41webui.domain.KeyValue;
import com.bayer.ecim.foundation.basic.StringTool;

@Component
public class KgsRFCService extends AbstractSAPJCOService {
	
	private static final String RFC_NAME_GET_DOC_DEF = "GetDocDef";
	private static final String RFC_NAME_GET_CONTENT_REPO = "GetCrepInfo";
	private static final String RFC_NAME_GET_ATTRIBUTES = "GetAttributes";
	private static final String RFC_NAME_GET_ATTR_VALUES = "GetAttrValues";
//	private static final String RFC_NAME_GET_TEXTS = "GetTexts";
	private static final String RFC_NAME_CREATE_HTTP_PUT = "CreateHttpPut";
	private static final String RFC_NAME_GET_DOC_STATUS = "GetDocStatus";
	private static final String RFC_NAME_PROCESS_DR_REQ = "ProcessDrReq";
	

	public Map<String, DocMetadata> getDocMetadata(Set<String> languageCodes) throws Doc41ServiceException {
		Map<String, DocMetadata> metadataMap = new HashMap<String, DocMetadata>();
		//get doctypes
		List<DocTypeDef> docTypeDefs = getDocTypeDefs();
//		Set<String> textKeysToTranslate = new HashSet<String>();
        for (DocTypeDef docTypeDef : docTypeDefs) {
        	String d41id = docTypeDef.getD41id();
        	if(isTypeSupported(d41id)){
        		Doc41Log.get().debug(getClass(), UserInSession.getCwid(), "start Metadata loading for doc type "+d41id);
        		DocMetadata metadata = new DocMetadata(docTypeDef);
        		//content repo
        		metadata.setContentRepository(getContentRepo(d41id));
        		//attributes for all languages
        		List<Attribute> attributes = getAttributes(d41id,languageCodes);
        		metadata.setAttributes(attributes);
        		//predefined attrib values
        		Map<String,List<String>> attrValues = getAttrValues(d41id);
        		for (Attribute attribute : attributes) {
        			List<String> values = attrValues.get(attribute.getName());
        			attribute.setValues(values);
        		}

//        		textKeysToTranslate.add(docTypeDef.getSapObj());
        		metadataMap.put(d41id, metadata);
        		Doc41Log.get().debug(getClass(), UserInSession.getCwid(), "Metadata for doc type "+d41id+" loaded");
        	}
		}
        //translations for doc type names
        //key -> language -> label
//        Map<String,Map<String,String>> translations = getDocTypeTranslations(textKeysToTranslate,languageCodes);
//        for (DocMetadata metadata : metadataMap.values()) {
//			String sapObj = metadata.getDocDef().getSapObj();
//			Map<String,String> translationForLanguages = translations.get(sapObj);
//			metadata.getDocDef().setTranslations(translationForLanguages);
//        }
		
		return metadataMap;
	}

	private boolean isTypeSupported(String d41id) {
		for (String type : Doc41Constants.SUPPORTED_DOC_TYPES) {
			if(StringTool.equals(type, d41id)){
				return true;
			}
		}
		return false;
	}

//	private Map<String, Map<String, String>> getDocTypeTranslations(
//			Set<String> textKeysToTranslate, Collection<String> languageCodes) throws Doc41ServiceException {
//		// /BAY0/GZ_D41_BO_GET_TEXTS for attr labels
//		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
//		for (String language : languageCodes) {
//			List<KeyValue> translationsOneLanguage = getDocTypeTranslations(textKeysToTranslate, language);
//			for (KeyValue attrValue : translationsOneLanguage) {
//				String attrName = attrValue.getKey();
//				Map<String, String> languageToValue = map.get(attrName);
//				if(languageToValue==null){
//					languageToValue = new HashMap<String, String>();
//					map.put(attrName,languageToValue);
//				}
//				languageToValue.put(language,attrValue.getValue());
//			}
//		}
//		return map;
//	}
	
//	private List<KeyValue> getDocTypeTranslations(Set<String> textKeysToTranslate,String language) throws Doc41ServiceException{
//		List<Object> params = new ArrayList<Object>();
//		params.add(textKeysToTranslate);
//		params.add(language);
//		List<KeyValue> values = performRFC(params,RFC_NAME_GET_TEXTS);
//		return values;
//	}

	private Map<String, List<String>> getAttrValues(String d41id) throws Doc41ServiceException {
		// /BAY0/GZ_D41_GET_ATTR_VALUES
		List<Object> params = new ArrayList<Object>();
		params.add(d41id);
		List<KeyValue> values = performRFC(params,RFC_NAME_GET_ATTR_VALUES);
		Map<String, List<String>> valueMap = new HashMap<String, List<String>>();
		for (KeyValue keyValue : values) {
			List<String> list = valueMap.get(keyValue.getKey());
			if(list==null){
				list = new ArrayList<String>();
				valueMap.put(keyValue.getKey(), list);
			}
			list.add(keyValue.getValue());
		}
		return valueMap ;
	}
	private List<Attribute> getAttributes(String d41id, Set<String> languageCodes) throws Doc41ServiceException {
		LinkedHashMap<String, Attribute> attribMap = new LinkedHashMap<String, Attribute>();
		
		for (String language : languageCodes) {
			List<Attribute> attributesOneLanguage = getAttributes(d41id, language);
			for (Attribute newAttrib : attributesOneLanguage) {
				String key = newAttrib.getName();
				String label = newAttrib.getTempLabel();
				newAttrib.setTempLabel(null);
				
				Attribute oldAttrib = attribMap.get(key);
				if(oldAttrib==null){
					oldAttrib = newAttrib;
					attribMap.put(key, oldAttrib);
				}
				oldAttrib.addTranslation(label, language);
			}
		}
		
		return new ArrayList<Attribute>(attribMap.values());
	}

	private List<Attribute> getAttributes(String d41id,String language) throws Doc41ServiceException {
		// /BAY0/GZ_D41_GET_ATTR_DEF_LIST
		List<Object> params = new ArrayList<Object>();
		params.add(d41id);
		params.add(language);
		List<Attribute> attr = performRFC(params,RFC_NAME_GET_ATTRIBUTES);
		return attr;
	}

	private ContentRepositoryInfo getContentRepo(String d41id) throws Doc41ServiceException {
		// /BAY0/GZ_D41_GET_CREP_INFO
		List<Object> params = new ArrayList<Object>();
		params.add(d41id);
		List<ContentRepositoryInfo> creps = performRFC(params,RFC_NAME_GET_CONTENT_REPO);
		
		if(creps==null || creps.isEmpty()){
			return null;
		} else if(creps.size()>1){
			throw new Doc41ServiceException("more than one ContentRepository for doc type "+d41id,null);
		} else {
			return creps.get(0);
		}
	}

	private List<DocTypeDef> getDocTypeDefs() throws Doc41ServiceException {
		// /BAY0/GZ_D41_GET_DOC_DEF
        List<Object> params = new ArrayList<Object>();
        
        List<DocTypeDef> docTypeDefs = performRFC(params,RFC_NAME_GET_DOC_DEF);
		return docTypeDefs;
	}

	public URI getPutUrl(String guid, String contentRepository) throws Doc41ServiceException {
		// /BAY0/GZ_D41_CREATE_HTTPPUT
		List<Object> params = new ArrayList<Object>();
		params.add(contentRepository);
		params.add(guid);
		List<URI> result = performRFC(params,RFC_NAME_CREATE_HTTP_PUT);
		if(result ==null || result.isEmpty()){
			return null;
		} else if(result.size()>1){
			throw new Doc41ServiceException("more than one PutURL returned");
		} else {
			return result.get(0);
		}
	}

	public boolean testDocStatus(String guid, String contentRepository) throws Doc41ServiceException {
		// /BAY0/GZ_D41_GET_DOCSTATUS
		List<Object> params = new ArrayList<Object>();
		params.add(contentRepository);
		params.add(guid);
		List<DocumentStatus> result = performRFC(params,RFC_NAME_GET_DOC_STATUS);
		if(result ==null || result.isEmpty()){
			throw new Doc41ServiceException("no status for document "+guid+" and contentRepository "+contentRepository+" returned");
		} else if(result.size()>1){
			throw new Doc41ServiceException("more than one status for document "+guid+" and contentRepository "+contentRepository+" returned");
		} else {
			String status = result.get(0).getStatus();
			return StringTool.equals(status, "ONLINE");
		}
	}

	public int setAttributesForNewDocument(String d41id, String fileId,
			String contentRepository,String docClass, String deliveryNumber, String sapObj,
			Map<String, String> attributeValues) throws Doc41ServiceException {
		List<Object> params = new ArrayList<Object>();
		params.add(d41id);
		params.add(fileId);
		params.add(contentRepository);
		params.add(docClass);
		params.add(deliveryNumber);
		params.add(sapObj);
		params.add(attributeValues);
		
		List<Integer> result = performRFC(params, RFC_NAME_PROCESS_DR_REQ);
		if(result ==null || result.isEmpty()){
			throw new Doc41ServiceException("no return count for file "+fileId);
		} else if(result.size()>1){
			throw new Doc41ServiceException("more than one return count for file "+fileId);
		} else {
			return result.get(0);
		}
	}

	public List<HitListEntry> findDocs(String d41id,Map<String, String> attributeValues,int maxResults,boolean maxVersionOnly)
	 throws Doc41ServiceException{
		//TODO
		return new ArrayList<HitListEntry>();
	}
	
	public URI getDocURL(String contentRepository,String docId)
			 throws Doc41ServiceException{
		//TODO
		try {
			return new URI("http://www.bayer.com/");
		} catch (URISyntaxException e) {
			throw new Doc41ServiceException("findDocs",e);
		}
		
	}
}
