package com.bayer.bhc.doc41webui.usecase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.ContentRepositoryInfo;
import com.bayer.bhc.doc41webui.domain.Delivery;
import com.bayer.bhc.doc41webui.domain.DocMetadata;
import com.bayer.bhc.doc41webui.domain.DocTypeDef;
import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.bhc.doc41webui.integration.sap.service.AuthorizationRFCService;
import com.bayer.bhc.doc41webui.integration.sap.service.KgsRFCService;
import com.bayer.bhc.doc41webui.service.httpclient.HttpClientService;
import com.bayer.bhc.doc41webui.service.repository.TranslationsRepository;
import com.bayer.bhc.doc41webui.usecase.documenttypes.AWBDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.BOLDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.COODocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.TempLogDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;
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
	
	private final Map<String,DocumentType> documentTypes;
	
	public DocumentUC() {
		documentTypes = new HashMap<String, DocumentType>();
		addDocumentType(new BOLDocumentType());
		addDocumentType(new AWBDocumentType());
		addDocumentType(new TempLogDocumentType());
		addDocumentType(new COODocumentType());
	}
	
	private void addDocumentType(DocumentType documentType) {
		String typeConst = documentType.getTypeConst();
		documentTypes.put(typeConst, documentType);
	}

	//TODO maybe synchronize or make member final and call from constructor
	public DocMetadata getMetadata(String type) throws Doc41BusinessException{
		try {
			if(docMetadataContainer==null){
				Set<String> languageCodes = translationsRepository.getLanguageCodes().keySet();
				docMetadataContainer = kgsRFCService.getDocMetadata(languageCodes,getSupportedSapDocTypes());
			}
			String sapDocType = getDocType(type).getSapTypeId();
			DocMetadata docMetadata = docMetadataContainer.get(sapDocType);
			if(docMetadata==null){
				throw new Doc41BusinessException("document type "+type+"/"+sapDocType+" not found in SAP");
			}
			return docMetadata;
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("getMetadata",e);
		}
	}

	private Set<String> getSupportedSapDocTypes() {
		Set<String> supportedSapTypes = new HashSet<String>();
		for (DocumentType docType : documentTypes.values()) {
			supportedSapTypes.add(docType.getSapTypeId());
		}
		return supportedSapTypes ;
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
	
	public boolean checkDeliveryForPartner(String carrier,String deliveryNumber,String shippingUnitNumber) throws Doc41BusinessException{
		try {
			return authorizationRFCService.checkDeliveryForPartner(carrier, deliveryNumber, shippingUnitNumber);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("checkDeliveryForPartner",e);
		}
	}
	
	public boolean checkDeliveryNumberExists(String deliveryNumber) throws Doc41BusinessException{
		try {
			return authorizationRFCService.checkDeliveryNumberExists(deliveryNumber);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("checkDeliveryNumberExists",e);
		}
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
		File localFile = null;
		try {
			String originalFilename = file.getOriginalFilename();
			int lastIndexOf = originalFilename.lastIndexOf('.');
			String suffix = null;
			if(lastIndexOf>=0 && lastIndexOf<originalFilename.length()){
				suffix = originalFilename.substring(lastIndexOf);
			}
			localFile = File.createTempFile(TEMP_FILE_PREFIX, suffix);
			Doc41Log.get().debug(this.getClass(),UserInSession.getCwid(),"write uploaded file to temp file: "+localFile.getAbsolutePath());
			out = new FileOutputStream(localFile);
	        filecontent = file.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
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
		//infected file is killed by virusscanner after stream is closed
	    if(localFile!=null && localFile.exists()){
			Doc41Log.get().debug(this.getClass(),UserInSession.getCwid(),"virusscan passed");
			return localFile;
		} else {
			Doc41Log.get().error(this.getClass(),UserInSession.getCwid(),"SECURITY WARNING: virusscan failed!");
			return null;
		}
	}
	
	public List<HitListEntry> searchDocuments(String type, String objectId, Map<String, String> attributeValues, int maxResults, boolean maxVersionOnly) throws Doc41BusinessException {
		try{
			DocMetadata metadata = getMetadata(type);
//			ContentRepositoryInfo crepInfo = metadata.getContentRepository();
			DocTypeDef docDef = metadata.getDocDef();
			String d41id = docDef.getD41id();
			String sapObj = docDef.getSapObj();
			return kgsRFCService.findDocs(d41id, sapObj, objectId, attributeValues, maxResults, maxVersionOnly);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("searchDocuments",e);
		}
	}



	public String downloadDocument(HttpServletResponse targetResponse, String type,
			String docId) throws Doc41BusinessException {
		try{
			DocMetadata metadata = getMetadata(type);
			ContentRepositoryInfo crepInfo = metadata.getContentRepository();
//			DocTypeDef docDef = metadata.getDocDef();
		
			URI docURL = kgsRFCService.getDocURL(crepInfo.getContentRepository(), docId);
		
			String statusText = httpClientService.downloadDocumentToResponse(docURL,targetResponse,docId);
		
			return statusText;
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("downloadDocument",e);
		}
	}
	
	public void checkForUpload(Errors errors, String type, MultipartFile file, String fileId, String partnerNumber, String objectId, Map<String, String> attributeValues) throws Doc41BusinessException{
		getDocTypeForUpload(type).checkForUpload(errors, this, file, fileId, partnerNumber, objectId, attributeValues);
	}
	
	public void checkForDownload(Errors errors, String type, String partnerNumber, String objectId, Map<String, String> attributeValues) throws Doc41BusinessException{
		getDocTypeForDownload(type).checkForDownload(errors, this, partnerNumber, objectId, attributeValues);
	}
	
	private UploadDocumentType getDocTypeForUpload(String type) throws Doc41BusinessException{
		DocumentType documentType = getDocType(type);
		if(!(documentType instanceof UploadDocumentType)){
			throw new Doc41BusinessException("doctype not enabled for upload: "+type);
		}
		return (UploadDocumentType) documentType;
	}
	
	private DownloadDocumentType getDocTypeForDownload(String type) throws Doc41BusinessException{
		DocumentType documentType = getDocType(type);
		if(!(documentType instanceof DownloadDocumentType)){
			throw new Doc41BusinessException("doctype not enabled for download: "+type);
		}
		return (DownloadDocumentType) documentType;
	}

	private DocumentType getDocType(String type) throws Doc41BusinessException {
		DocumentType documentType = documentTypes.get(type);
		if(documentType==null){
			throw new Doc41BusinessException("unknown doctype: "+type);
		}
		return documentType;
	}

	public boolean isPartnerNumberUsed(String type) throws Doc41BusinessException {
		return getDocType(type).isPartnerNumberUsed();
	}

	public String getUploadPermission(String type) throws Doc41BusinessException {
		return getDocTypeForUpload(type).getPermissionUpload();
	}
	
	public String getDownloadPermission(String type) throws Doc41BusinessException {
		return getDocTypeForDownload(type).getPermissionDownload();
	}
	
}
