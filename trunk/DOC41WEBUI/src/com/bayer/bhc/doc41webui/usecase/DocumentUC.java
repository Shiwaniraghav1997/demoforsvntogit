package com.bayer.bhc.doc41webui.usecase;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.container.Attribute;
import com.bayer.bhc.doc41webui.container.DocMetadata;
import com.bayer.bhc.doc41webui.integration.sap.service.AuthorizationRFCService;
import com.bayer.bhc.doc41webui.integration.sap.service.KgsRFCService;
import com.bayer.bhc.doc41webui.service.repository.TranslationsRepository;

@Component
public class DocumentUC {

	@Autowired
	private AuthorizationRFCService authorizationRFCService;
	
	@Autowired
	private KgsRFCService kgsRFCService;
	
	@Autowired
	private TranslationsRepository translationsRepository;
	
	private Map<String, DocMetadata> docMetadataContainer;
	
	public DocMetadata getMetadata(String type) throws Doc41BusinessException{
		try {
			if(docMetadataContainer==null){
				Collection<String> languageCodes = translationsRepository.getLanguageCodes().values();
				docMetadataContainer = kgsRFCService.getDocMetadata(languageCodes);
			}
			DocMetadata docMetadata = docMetadataContainer.get(getSapDocType(type));
			return docMetadata;
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("getMetadata",e);
		}
	}
	
	
	
	public String checkCoaDeliveryNumberMaterial(String deliveryNumber, String matNo)
	throws Doc41BusinessException{
		try {
			return authorizationRFCService.checkCoADeliveryNumberMaterial(deliveryNumber, matNo);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("checkCoaDeliveryNumberMaterial",e);
		}
	}
	
	
	public List<Attribute> getAttributeDefinitions(String doctype) throws Doc41BusinessException{
		DocMetadata metadata = getMetadata(doctype);
		return metadata.getAttributes();
	}
	
	private String getSapDocType(String docType) throws Doc41BusinessException{
		if(docType==null || docType.isEmpty()){
			return null;
		} else if(docType.equals(Doc41Constants.DOC_TYPE_AIRWAY)){
			return Doc41Constants.SAP_DOC_TYPE_AIRWAY;
		} else if(docType.equals(Doc41Constants.DOC_TYPE_BOL)){
			return Doc41Constants.SAP_DOC_TYPE_BOL;
		} else {
			throw new Doc41BusinessException("unknown doc type: "+docType);
		}
	}
}
