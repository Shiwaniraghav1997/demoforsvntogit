package com.bayer.bhc.doc41webui.usecase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.Attribute;
import com.bayer.bhc.doc41webui.container.ContentRepositoryInfo;
import com.bayer.bhc.doc41webui.container.Delivery;
import com.bayer.bhc.doc41webui.container.DocMetadata;
import com.bayer.bhc.doc41webui.container.DocTypeDef;
import com.bayer.bhc.doc41webui.integration.sap.service.AuthorizationRFCService;
import com.bayer.bhc.doc41webui.integration.sap.service.KgsRFCService;
import com.bayer.bhc.doc41webui.service.httpclient.HttpClientService;
import com.bayer.bhc.doc41webui.service.repository.TranslationsRepository;
import com.bayer.ecim.foundation.basic.StringTool;

@Component
public class DocumentUC {

	private static final String TEMP_FILE_PREFIX = "D41-";

	@Autowired
	private AuthorizationRFCService authorizationRFCService;
	
	@Autowired
	private KgsRFCService kgsRFCService;
	
	@Autowired
	private TranslationsRepository translationsRepository;
	
	@Autowired
	private HttpClientService httpClientService;
	
	private Map<String, DocMetadata> docMetadataContainer;
	
	public DocMetadata getMetadata(String type) throws Doc41BusinessException{
		try {
			if(docMetadataContainer==null){
				Set<String> languageCodes = translationsRepository.getLanguageCodes().keySet();
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
	
	public String getTypeLabel(String doctype,String language) throws Doc41BusinessException{
		DocMetadata metadata = getMetadata(doctype);
		if(metadata!=null){
			DocTypeDef docDef = metadata.getDocDef();
			if(docDef!=null){
				String label = docDef.getTranslation(language);
				if(!StringTool.isTrimmedEmptyOrNull(label)){
					return label;
				}
			}
		}
		return "["+doctype+"]";
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



	public List<Delivery> getOpenDeliveries(String type, String carrier) {
		List<Delivery> deliveries = new ArrayList<Delivery>();
		// TODO use RFC GetDeliveriesWithoutDocumentRFC
		
		Delivery dummy1 = new Delivery();
		dummy1.setDeliveryNumber("80400000");
		dummy1.setGoodsIssueDate(new Date());
		dummy1.setShippingUnitNumber("20001");
		dummy1.setShipToNumber("30001");
		dummy1.setSoldToNumber("40001");
		deliveries.add(dummy1);
		
		Delivery dummy2 = new Delivery();
		dummy2.setDeliveryNumber("80400005");
		dummy2.setGoodsIssueDate(new Date());
		dummy2.setShippingUnitNumber("20002");
		dummy2.setShipToNumber("30002");
		dummy2.setSoldToNumber("40002");
		deliveries.add(dummy2);
		return deliveries ;
	}
	
	public boolean checkDeliveryForPartner(String carrier,String deliveryNumber,String shippingUnitNumber){
		//TODO use RFC CheckDeliveryForPartnerRFC
		return true;
	}



	public String uploadDocument(String type, File localFile, String contentType) throws Doc41BusinessException {
		try{
			DocMetadata metadata = getMetadata(type);
			ContentRepositoryInfo crepInfo = metadata.getContentRepository();
			String crep = crepInfo.getContentRepository();
			//		String d41id = metadata.getDocDef().getD41id();

			//create guid
			String guid = UUID.randomUUID().toString();
			//get put url
			URI putUrl = kgsRFCService.getPutUrl(guid,crep);
			//upload document to put url
			httpClientService.uploadDocumentToUrl(putUrl,localFile,contentType);
			//test docstatus
			boolean docPresent = kgsRFCService.testDocStatus(guid,crep);
			if(docPresent){
				return guid;
			} else {
				return null;
			}
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("uploadDocument",e);
		} finally {
			localFile.delete();
		}
	}


	public void setAttributesForNewDocument(String type, String fileId,
			Map<String, String> attributeValues, String deliveryNumber) throws Doc41BusinessException {
		try{
			DocMetadata metadata = getMetadata(type);
			ContentRepositoryInfo crepInfo = metadata.getContentRepository();
			DocTypeDef docDef = metadata.getDocDef();
			String d41id = docDef.getD41id();
			kgsRFCService.setAttributesForNewDocument(d41id,fileId,crepInfo.getContentRepository(),crepInfo.getDocClass(),deliveryNumber,docDef.getSapObj(),attributeValues);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("setAttributesForNewDocument",e);
		}
	}



	public File checkForVirus(MultipartFile file) throws Doc41BusinessException {
		OutputStream out = null;
		InputStream filecontent = null;
	    try {
			String originalFilename = file.getOriginalFilename();
			int lastIndexOf = originalFilename.lastIndexOf('.');
			String suffix = null;
			if(lastIndexOf>=0 && lastIndexOf<originalFilename.length()){
				suffix = originalFilename.substring(lastIndexOf);
			}
			File localFile = File.createTempFile(TEMP_FILE_PREFIX, suffix);
			Doc41Log.get().debug(this.getClass(),UserInSession.getCwid(),"write uploaded file to temp file: "+localFile.getAbsolutePath());
			out = new FileOutputStream(localFile);
	        filecontent = file.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
			if(localFile.exists()){
				Doc41Log.get().debug(this.getClass(),UserInSession.getCwid(),"virusscan passed");
				return localFile;
			} else {
				Doc41Log.get().error(this.getClass(),UserInSession.getCwid(),"SECURITY WARNING: virusscan failed!");
				return null;
			}
		} catch (IOException e) {
			throw new Doc41BusinessException("checkForVirus",e);
		} finally {
			try {
				if (out != null) {
		            out.close();
		        }
		        if (filecontent != null) {
		            filecontent.close();
		        }
			} catch (IOException e) {
				throw new Doc41BusinessException("checkForVirus",e);
			}
		}
	}
}
