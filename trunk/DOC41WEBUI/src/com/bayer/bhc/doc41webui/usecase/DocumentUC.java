package com.bayer.bhc.doc41webui.usecase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.logging.Doc41LogEntry;
import com.bayer.bhc.doc41webui.common.util.UrlParamCrypt;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.ContentRepositoryInfo;
import com.bayer.bhc.doc41webui.domain.DeliveryOrShippingUnit;
import com.bayer.bhc.doc41webui.domain.DocInfoComponent;
import com.bayer.bhc.doc41webui.domain.DocMetadata;
import com.bayer.bhc.doc41webui.domain.DocTypeDef;
import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.bhc.doc41webui.domain.InspectionLot;
import com.bayer.bhc.doc41webui.domain.QMBatchObject;
import com.bayer.bhc.doc41webui.domain.SDReferenceCheckResult;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.integration.sap.service.AuthorizationRFCService;
import com.bayer.bhc.doc41webui.integration.sap.service.BwRFCService;
import com.bayer.bhc.doc41webui.integration.sap.service.KgsRFCService;
import com.bayer.bhc.doc41webui.service.httpclient.HttpClientService;
import com.bayer.bhc.doc41webui.service.repository.TranslationsRepository;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForUpdateResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DirectDownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls.ArtworkForLayoutSupplierDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls.LayoutForLayoutSupplierDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm.PZTecDrawingForPMSupplierDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm.PackMatSpecForPMSupplierDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm.TecPackDelReqForPMSupplierDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.qm.DeliveryCertDownCountryDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.qm.DeliveryCertDownCustomerDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.qm.DeliveryCertUploadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.qm.SupplierCOADocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.sd.AWBDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.sd.BOLDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.sd.CMRDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.sd.CMROutDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.sd.FDACertDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.sd.ShippersDeclDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.sd.WaybillDocumentType;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.DateTool;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.SendMail;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.basic.Template;

@Component
public class DocumentUC {

	private static final String TEMP_FILE_PREFIX = "D41-";
	
	@Autowired
	private AuthorizationRFCService authorizationRFCService;
	
	@Autowired
	private KgsRFCService kgsRFCService;
	
	@Autowired
	private BwRFCService bwRFCService;
	
	@Autowired
	private TranslationsRepository translationsRepository;
	
	@Autowired
	private HttpClientService httpClientService;
	
	private Map<String, DocMetadata> docMetadataContainer;
	
	private final Map<String,DocumentType> documentTypes;
	
	public DocumentUC() {
		documentTypes = new HashMap<String, DocumentType>();
		
		addDocumentType(new AWBDocumentType());
		addDocumentType(new BOLDocumentType());
		addDocumentType(new CMRDocumentType());
		addDocumentType(new CMROutDocumentType());
		addDocumentType(new FDACertDocumentType());
		addDocumentType(new ShippersDeclDocumentType());
		addDocumentType(new WaybillDocumentType());
		
		addDocumentType(new DeliveryCertDownCountryDocumentType());
		addDocumentType(new DeliveryCertDownCustomerDocumentType());
		addDocumentType(new DeliveryCertUploadDocumentType());
		addDocumentType(new SupplierCOADocumentType());
		
		addDocumentType(new ArtworkForLayoutSupplierDocumentType());
		addDocumentType(new LayoutForLayoutSupplierDocumentType());
		
		addDocumentType(new PackMatSpecForPMSupplierDocumentType());
		addDocumentType(new PZTecDrawingForPMSupplierDocumentType());
		addDocumentType(new TecPackDelReqForPMSupplierDocumentType());
	}
	
	private void addDocumentType(DocumentType documentType) {
		String typeConst = documentType.getTypeConst();
		documentTypes.put(typeConst, documentType);
	}

	public synchronized DocMetadata getMetadata(String type) throws Doc41BusinessException{
		try {
			if(docMetadataContainer==null){
				Set<String> languageCodes = translationsRepository.getLanguageCodes().keySet();
				docMetadataContainer = kgsRFCService.getDocMetadata(languageCodes,getSupportedSapDocTypes(),
						getExcludedAttributesByD41Id());
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

	private Map<String, Set<String>> getExcludedAttributesByD41Id() {
		Map<String, Set<String>> excludedByD41Id = new HashMap<String, Set<String>>();
		for (DocumentType docType : documentTypes.values()) {
			Set<String> excludedAttributes = docType.getExcludedAttributes();
			Set<String> allExcludedAttributes = new HashSet<String>(excludedAttributes);
			allExcludedAttributes.add(Doc41Constants.ATTRIB_NAME_WEBUI_USER);
			excludedByD41Id.put(docType.getSapTypeId(), allExcludedAttributes);
		}
		return excludedByD41Id;
	}

	private Set<String> getSupportedSapDocTypes() {
		Set<String> supportedSapTypes = new HashSet<String>();
		for (DocumentType docType : documentTypes.values()) {
			supportedSapTypes.add(docType.getSapTypeId());
		}
		return supportedSapTypes ;
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
	
	
	public List<Attribute> getAttributeDefinitions(String doctype,boolean filterFileName) throws Doc41BusinessException{
		DocMetadata metadata = getMetadata(doctype);
		List<Attribute> attributes = metadata.getAttributes(filterFileName);
		return attributes;
	}
	
	public List<DeliveryOrShippingUnit> getOpenDeliveries(String type, String carrier) throws Doc41BusinessException {
		try{
			DocMetadata metadata = getMetadata(type);
			DocTypeDef docDef = metadata.getDocDef();
			String d41id = docDef.getD41id();
			Date toDate = new Date();
			Date fromDate = DateTool.modifyDate(toDate,"-90D");
			return authorizationRFCService.getOpenDeliveries(d41id,carrier,fromDate,toDate);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("getOpenDeliveries",e);
		}
	}
	
	public SDReferenceCheckResult checkDeliveryForPartner(String carrier,String referenceNumber) throws Doc41BusinessException{
		try {
			return authorizationRFCService.checkDeliveryForPartner(carrier, referenceNumber);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("checkDeliveryForPartner",e);
		}
	}

	public String uploadDocument(String type, File localFile, String contentType, String fileName) throws Doc41BusinessException {
		try{
			DocMetadata metadata = getMetadata(type);
			ContentRepositoryInfo crepInfo = metadata.getContentRepository();
			String crep = crepInfo.getContentRepository();
			DocTypeDef docDef = metadata.getDocDef();
			//		String d41id = metadata.getDocDef().getD41id();

			//create guid
			String guid = UUID.randomUUID().toString();
			guid = guid.replace("-", "");
			
			//get put url
			URI putUrl;
			if(docDef.isDvs()){
				putUrl = kgsRFCService.getDvsPutUrl(guid,crep,fileName);
			} else {
				putUrl = kgsRFCService.getPutUrl(guid,crep);
			}
			//upload document to put url
			httpClientService.uploadDocumentToUrl(putUrl,localFile,contentType);
			//test docstatus
			boolean docPresent = kgsRFCService.testDocStatus(guid,crep);
			if(docPresent){
				logWebMetrix("DOC_UPLOADED",guid,null);
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
			Map<String, String> attributeValues, String objId,String fileName, String sapObject,String vkOrg) throws Doc41BusinessException {
		try{
			DocMetadata metadata = getMetadata(type);
			ContentRepositoryInfo crepInfo = metadata.getContentRepository();
			DocTypeDef docDef = metadata.getDocDef();
			String d41id = docDef.getD41id();
			
			if(!StringTool.isTrimmedEmptyOrNull(fileName) && metadata.isFileNameAttribAvailable()){
				attributeValues.put(metadata.getFileNameAttibKey(), fileName);
			}
			if(!StringTool.isTrimmedEmptyOrNull(vkOrg)){
				attributeValues.put(Doc41Constants.ATTRIB_NAME_VKORG, vkOrg);
			}
			//TODO do something with vkOrg
			checkAttribsWithCustomizing(attributeValues,type);
			String docClass;
			Map<String, String> kgsAttributeValues;
			if(docDef.isDvs()){
				kgsAttributeValues = new HashMap<String, String>(attributeValues);
				kgsAttributeValues.put(Doc41Constants.ATTRIB_NAME__I_DVSOBJTYPE, sapObject);
				kgsAttributeValues.put(Doc41Constants.ATTRIB_NAME__I_DVSDOCTYPE, docDef.getTechnicalId());
				kgsAttributeValues.put(Doc41Constants.ATTRIB_NAME__I_NAMETYPE, "2");
				docClass=getDocClass(fileName,"*");
			} else {
				kgsAttributeValues=Collections.unmodifiableMap(attributeValues);
				docClass = getDocClass(fileName,crepInfo.getAllowedDocClass());
			}
			kgsRFCService.setAttributesForNewDocument(d41id,fileId,crepInfo.getContentRepository(),docClass,objId,sapObject,kgsAttributeValues);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("setAttributesForNewDocument",e);
		}
	}



	private String getDocClass(String fileName, String allowedDocClass) throws Doc41BusinessException {
		String docClass;
		boolean isAllClassesAllowed = StringTool.equals(allowedDocClass, "*");
		if(StringTool.isTrimmedEmptyOrNull(fileName)){
			if(isAllClassesAllowed){
				throw new Doc41BusinessException("no filename for getDocClass");
			} else {
				return allowedDocClass;
			}
		}
		
		String fileNameUpper = fileName.toUpperCase();
		if(fileNameUpper.endsWith(".PDF")){
			docClass = "PDF";
		} else if (fileNameUpper.endsWith(".TIFF") || fileNameUpper.endsWith(".TIF")){
			docClass = "TIFF";
		} else if (fileNameUpper.endsWith(".BMP")){
			docClass = "BMP";
		} else if (fileNameUpper.endsWith(".JPG") || fileNameUpper.endsWith(".JPEG")){
			docClass = "JPG";
		} else if (fileNameUpper.endsWith(".XLS")){
			docClass = "XLS";
		} else if (fileNameUpper.endsWith(".XLSX")){
			docClass = "XLSX";
		} else if (fileNameUpper.endsWith(".DOC")){
			docClass = "DOC";
		} else if (fileNameUpper.endsWith(".DOCX")){
			docClass = "DOCX";
		} else if (fileNameUpper.endsWith(".TXT")){
			docClass = "TXT";
		} else {
			throw new Doc41BusinessException("unknown extension :"+fileNameUpper);
		}
		
		if(!isAllClassesAllowed && !StringTool.equals(docClass, allowedDocClass.toUpperCase())){
			throw new Doc41BusinessException("Doc class "+docClass+" is not allowed ("+allowedDocClass+")");
		}
		
		return docClass;
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
	
	public List<HitListEntry> searchDocuments(String type, List<String> objectIds,
			Map<String, String> attributeValues, int maxResults, boolean maxVersionOnly)
					throws Doc41BusinessException {
		try{
			checkAttribsWithCustomizing(attributeValues,type);
			
			Map<Integer, String> seqToKey = getSeqToKeyFromDefinitions(type);
			DocMetadata metadata = getMetadata(type);
//			ContentRepositoryInfo crepInfo = metadata.getContentRepository();
			DocTypeDef docDef = metadata.getDocDef();
			String d41id = docDef.getD41id();
			List<String> sapObjList = docDef.getSapObjList();
			List<HitListEntry> allResults = new ArrayList<HitListEntry>();
			for (String sapObj : sapObjList) {
				List<HitListEntry> oneResult = bwRFCService.findDocs(d41id, sapObj, objectIds, attributeValues, maxResults, maxVersionOnly,seqToKey);
				allResults.addAll(oneResult);
			}
			for (HitListEntry hitListEntry : allResults) {
				hitListEntry.initCustValuesMap(seqToKey);
				
				Map<String,String> params = new LinkedHashMap<String, String>();
                params.put(Doc41Constants.URL_PARAM_TYPE,type);
                params.put(Doc41Constants.URL_PARAM_DOC_ID,hitListEntry.getDocId());
                params.put(Doc41Constants.URL_PARAM_CWID,UserInSession.getCwid());
                params.put(Doc41Constants.URL_PARAM_FILENAME,StringTool.encodeURLWithDefaultFileEnc(StringTool.nullToEmpty(hitListEntry.getFileName())));
                String key = UrlParamCrypt.encryptParameters(params);
				hitListEntry.setKey(key);
			}
			
			return allResults;
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("searchDocuments",e);
		}
	}



	private Map<Integer, String> getSeqToKeyFromDefinitions(String type) throws Doc41BusinessException {
		List<Attribute> attributeDefinitions = getMetadata(type).getAttributesWithExcluded();
		Map<Integer, String> attributeSeqToKey = new HashMap<Integer, String>();
		for (Attribute attribute : attributeDefinitions) {
			String key = attribute.getName();
			attributeSeqToKey.put(attribute.getSeqNumber(), key);
		}
		return attributeSeqToKey;
	}

	public String downloadDocument(HttpServletResponse targetResponse, String type,
			String docId,String fileName) throws Doc41BusinessException {
		try{
			DocMetadata metadata = getMetadata(type);
			ContentRepositoryInfo crepInfo = metadata.getContentRepository();
			String crepId = crepInfo.getContentRepository();
			DocTypeDef docDef = metadata.getDocDef();
			String compId = null;
			if(docDef.isDvs()){
				DocInfoComponent comp = kgsRFCService.getDocInfo(crepId,docId);
				compId = comp.getCompId();
			}
		
			URI docURL = kgsRFCService.getDocURL(crepId, docId,compId);
		
			String statusText = httpClientService.downloadDocumentToResponse(docURL,targetResponse,docId,fileName);
			logWebMetrix("DOC_DOWNLOADED",docId,statusText);
			return statusText;
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("downloadDocument",e);
		}
	}
	
	public CheckForUpdateResult checkForUpload(Errors errors, String type, String customerNumber, String vendorNumber, String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes) throws Doc41BusinessException{
		CheckForUpdateResult checkResult = getDocTypeForUpload(type).checkForUpload(errors, this, customerNumber, vendorNumber, objectId, attributeValues,viewAttributes);
		if(errors.hasErrors()){
			return null;
		}
		String sapObjectFromCheck = checkResult.getSapObject();
		DocMetadata metadata = getMetadata(type);
		DocTypeDef docDef = metadata.getDocDef();
		List<String> sapObjList = docDef.getSapObjList();
		String realSapObject;
		if(StringTool.isTrimmedEmptyOrNull(sapObjectFromCheck)){//no mapping configured, only allowed if only one busob in metadata
			if(sapObjList.size()==1){
				realSapObject = sapObjList.get(0);
			} else {
				throw new Doc41BusinessException("no SAP_OBJECT mapping in DocType, but "+sapObjList.size()+" possible values in metadata for type "+type);
			}
		} else { //compare sapobj from mapping with metadata
			if(sapObjList.contains(sapObjectFromCheck)){
				realSapObject = sapObjectFromCheck;
			} else {
				throw new Doc41BusinessException("SAP_OBJECT "+sapObjectFromCheck+" not in metadata of type "+type);
			}
		}
		checkResult.setSapObject(realSapObject);
		return checkResult;
	}
	
	public CheckForDownloadResult checkForDownload(Errors errors, String type, String customerNumber, String vendorNumber, String objectId, Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException{
		return getDocTypeForDownload(type).checkForDownload(errors, this, customerNumber, vendorNumber, objectId, attributeValues,viewAttributes);
	}
	
	public CheckForDownloadResult checkForDirectDownload(String type, String objectId) throws Doc41BusinessException{
		return getDocTypeForDirectDownload(type).checkForDirectDownload(this, objectId);
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
	
	private DirectDownloadDocumentType getDocTypeForDirectDownload(String type) throws Doc41BusinessException{
		DocumentType documentType = getDocType(type);
		if(!(documentType instanceof DirectDownloadDocumentType)){
			throw new Doc41BusinessException("doctype not enabled for direct download: "+type);
		}
		return (DirectDownloadDocumentType) documentType;
	}

	private DocumentType getDocType(String type) throws Doc41BusinessException {
		DocumentType documentType = documentTypes.get(type);
		if(documentType==null){
			throw new Doc41BusinessException("unknown doctype: "+type);
		}
		return documentType;
	}

	public boolean hasCustomerNumber(String type) throws Doc41BusinessException {
		return getDocType(type).hasCustomerNumber();
	}
	
	public boolean hasVendorNumber(String type) throws Doc41BusinessException {
		return getDocType(type).hasVendorNumber();
	}

	public String getUploadPermission(String type) throws Doc41BusinessException {
		return getDocTypeForUpload(type).getPermissionUpload();
	}
	
	public String getDownloadPermission(String type) throws Doc41BusinessException {
		return getDocTypeForDownload(type).getPermissionDownload();
	}
	
	public String getDirectDownloadPermission(String type) throws Doc41BusinessException {
		return getDocTypeForDirectDownload(type).getPermissionDirectDownload();
	}
	
	public int getDocumentFillLength(String type) throws Doc41BusinessException {
		return getDocType(type).getObjectIdFillLength();
	}

	public String checkArtworkLayoutForVendor(String vendorNumber,String sapTypeId) throws Doc41BusinessException {
		try {
			return authorizationRFCService.checkArtworkLayoutForVendor(vendorNumber,sapTypeId);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("checkArtworkForVendor",e);
		}
	}

	
	
	public String checkPOAndMaterialForVendor(String vendorNumber, String poNumber, String materialNumber) throws Doc41BusinessException {
		try {
			return authorizationRFCService.checkPOAndMaterialForVendor(vendorNumber, poNumber, materialNumber);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("checkPOAndMaterialForVendor",e);
		}
	}
	
	public List<InspectionLot> getInspectionLotsForVendorBatch(String vendor,
			String vendorBatch, String plant) throws Doc41BusinessException {
//		//TODO remove mock qm mock
//		if(true){
//			List<InspectionLot> list = new ArrayList<InspectionLot>();
//			try {
//				int count = Integer.parseInt(vendorBatch);
//				for(int i=0;i<count;i++){
//					InspectionLot lot = new InspectionLot();
//					lot.setNumber("Dummy"+i);
//					lot.setMaterialNumber("matno"+i);
//					lot.setMaterialText("mattext"+i);
//					lot.setPlant("plant"+i);
//					lot.setBatch("batch"+i);
//					lot.setVendor(vendor);
//					lot.setVendorBatch(vendorBatch);
//					list.add(lot);
//				}
//			} catch (NumberFormatException e) {
//				e.printStackTrace();
//			}
//			return list ;
//		}
//		//mock
		try{
			return authorizationRFCService.getInspectionLotsForVendorBatch(vendor,vendorBatch,plant);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("getInspectionLotsForVendorBatch",e);
		}
	}
	
	public List<QMBatchObject> getBatchObjectsForSupplier(String supplier, String plant, String material,
			String batch, String order) throws Doc41BusinessException {
		try{
//			//TODO remove mock qm mock
//			if(true){
//				List<QMBatchObject> list = new ArrayList<QMBatchObject>();
//				try {
//					int count = Integer.parseInt(batch);
//					for(int i=0;i<count;i++){
//						QMBatchObject bo = new QMBatchObject();
//						bo.setObjectId("Dummy"+i);
//						bo.setMaterialNumber("matno"+i);
//						bo.setMaterialText("mattext"+i);
//						bo.setPlant("plant"+i);
//						bo.setBatch("batch"+i);
//						list.add(bo);
//					}
//				} catch (NumberFormatException e) {
//					e.printStackTrace();
//				}
//				return list ;
//			}
//			//mock
			return authorizationRFCService.getBatchObjectsForSupplier(supplier, plant, material, batch, order);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("getBatchObjectsForSupplier",e);
		}
	}
	
	public List<QMBatchObject> getBatchObjectsForCustomer(String customer, String delivery, String material,
			String batch, String country) throws Doc41BusinessException {
		try{
			return authorizationRFCService.getBatchObjectsForCustomer(customer, delivery, material, batch, country);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("getBatchObjectsForCustomer",e);
		}
	}
	
	private void logWebMetrix(String action, String docId, String statusText) {
		String loggedInUser = UserInSession.getCwid();
		Doc41Log.get().logWebMetrix(this.getClass(),new Doc41LogEntry(loggedInUser, loggedInUser, "DOCUMENTS", action, 
				docId, statusText, null, null, null, null, null, null, null),loggedInUser);
	}

	public void sendUploadNotification(String notificationEMail, String typeName,  String fileName, String guid) {
		try {
			@SuppressWarnings("unchecked")
			Map<String,String> subConfig = ConfigMap.get().getSubConfig("documents", "notifymail");
			String sender = subConfig.get("sender");
			String subjectTemplate = subConfig.get("subjectTemplate");
			String bodyTemplate = subConfig.get("bodyTemplate");
			
			User user = UserInSession.get();
			String replyTo = user.getEmail();
			
			String[] templateParamNames = {"FILE_NAME","GUID","TYPE_NAME","CWID","FIRSTNAME","SURNAME"};
			Object[] templateParams = {fileName,guid,typeName,user.getCwid(),user.getFirstname(),user.getSurname()};
			
			String body = Template.expand(bodyTemplate,templateParams,templateParamNames);
			String subject = Template.expand(subjectTemplate,templateParams,templateParamNames);
			
			SendMail.get().send(sender, replyTo, notificationEMail,null,null, subject, body,null,null,false);
			
		} catch (InitException e) {
			Doc41Log.get().error(getClass(), UserInSession.getCwid(), 
					"Exception in sendUploadNotification("+notificationEMail+", "+typeName+", "+fileName+", "+guid+")");
			Doc41Log.get().error(getClass(), UserInSession.getCwid(), e);
		} catch (MessagingException e) {
			Doc41Log.get().error(getClass(), UserInSession.getCwid(), 
					"Exception in sendUploadNotification("+notificationEMail+", "+typeName+", "+fileName+", "+guid+")");
			Doc41Log.get().error(getClass(), UserInSession.getCwid(), e);
		}
		
	}
	
	public void checkAttribsWithCustomizing(Map<String, String> attributeValues,String type) throws Doc41BusinessException {
		List<Attribute> attributeDefinitions = getMetadata(type).getAttributesWithExcluded();
		
		Set<String> definedAttribKeys = new HashSet<String>();
		for (Attribute attribute : attributeDefinitions) {
			definedAttribKeys.add(attribute.getName());
		}
		
		StringBuilder missingAttrKeysInCustomizing = new StringBuilder();
		if(attributeValues!=null){
			for (String attrKey : attributeValues.keySet()) {
				if(!definedAttribKeys.contains(attrKey) && !Doc41Constants.ATTRIB_NAME_PLANT.equals(attrKey) && !Doc41Constants.ATTRIB_NAME_VKORG.equals(attrKey)){
					if(missingAttrKeysInCustomizing.length()>0){
						missingAttrKeysInCustomizing.append(", ");
					}
					missingAttrKeysInCustomizing.append(attrKey);
				}
			}
		}
		if(missingAttrKeysInCustomizing.length()>0){
			Doc41Log.get().error(getClass(), UserInSession.getCwid(), "CustomizingMissmatch: attributes "+missingAttrKeysInCustomizing+" no longer in customizing");
		}
		
	}


}
