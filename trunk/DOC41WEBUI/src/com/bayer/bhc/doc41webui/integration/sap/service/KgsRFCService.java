package com.bayer.bhc.doc41webui.integration.sap.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.Attribute;
import com.bayer.bhc.doc41webui.container.ContentRepositoryInfo;
import com.bayer.bhc.doc41webui.container.DocMetadata;
import com.bayer.bhc.doc41webui.container.DocTypeDef;
import com.bayer.bhc.doc41webui.container.KeyValue;
import com.bayer.ecim.foundation.basic.StringTool;

@Component
public class KgsRFCService extends AbstractSAPJCOService {
	
	//TODO Filter for doctypes

	private static final String RFC_NAME_GET_DOC_DEF = "GetDocDef";
	private static final String RFC_NAME_GET_CONTENT_REPO = "GetCrepInfo";
	private static final String RFC_NAME_GET_ATTRIBUTES = "GetAttributes";
	private static final String RFC_NAME_GET_ATTR_VALUES = "GetAttrValues";
	private static final String RFC_NAME_GET_TEXTS = "GetTexts";
	

	public Map<String, DocMetadata> getDocMetadata(Collection<String> languageCodes) throws Doc41ServiceException {
		Map<String, DocMetadata> metadataMap = new HashMap<String, DocMetadata>();
		List<DocTypeDef> docTypeDefs = getDocTypeDefs();
		List<String> textKeysToTranslate = new ArrayList<String>();
        for (DocTypeDef docTypeDef : docTypeDefs) {
        	String d41id = docTypeDef.getD41id();
        	if(isTypeSupported(d41id)){
        		Doc41Log.get().debug(getClass(), UserInSession.getCwid(), "start Metadata loading for doc type "+d41id);
        		DocMetadata metadata = new DocMetadata(docTypeDef);

        		metadata.setContentRepository(getContentRepo(d41id));

        		List<Attribute> attributes = getAttributes(d41id);
        		metadata.setAttributes(attributes);
        		Map<String,List<String>> attrValues = getAttrValues(d41id);
        		for (Attribute attribute : attributes) {
        			textKeysToTranslate.add(attribute.getName());
        			List<String> values = attrValues.get(attribute.getName());
        			attribute.setValues(values);
        		}

        		metadataMap.put(d41id, metadata);
        		Doc41Log.get().debug(getClass(), UserInSession.getCwid(), "Metadata for doc type "+d41id+" loaded");
        	}
		}
        //key -> language -> label
        Map<String,Map<String,String>> translations = getTranslations(textKeysToTranslate,languageCodes);
        for (DocMetadata metadata : metadataMap.values()) {
			metadata.getAttributes();
			for (Attribute attr : metadata.getAttributes()) {
				String key = attr.getName();
				Map<String,String> translationForLanguages = translations.get(key);
				attr.setTranslations(translationForLanguages);
			}
		}
		
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

	private Map<String, Map<String, String>> getTranslations(
			List<String> textKeysToTranslate, Collection<String> languageCodes) throws Doc41ServiceException {
		// /BAY0/GZ_D41_BO_GET_TEXTS for attr labels
		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
		for (String language : languageCodes) {
			List<KeyValue> translationsOneLanguage = getTranslations(textKeysToTranslate, language);
			for (KeyValue attrValue : translationsOneLanguage) {
				String attrName = attrValue.getKey();
				Map<String, String> languageToValue = map.get(attrName);
				if(languageToValue==null){
					languageToValue = new HashMap<String, String>();
					map.put(attrName,languageToValue);
				}
				languageToValue.put(language,attrValue.getValue());
			}
		}
		return map;
	}
	
	private List<KeyValue> getTranslations(List<String> textKeysToTranslate,String language) throws Doc41ServiceException{
		List<Object> params = new ArrayList<Object>();
		params.add(textKeysToTranslate);
		params.add(language);
		List<KeyValue> values = performRFC(params,RFC_NAME_GET_TEXTS);
		return values;
	}

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

	private List<Attribute> getAttributes(String d41id) throws Doc41ServiceException {
		// /BAY0/GZ_D41_GET_ATTR_DEF_LIST
		List<Object> params = new ArrayList<Object>();
		params.add(d41id);
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

}
