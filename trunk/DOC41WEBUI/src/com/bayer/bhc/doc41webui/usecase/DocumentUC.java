package com.bayer.bhc.doc41webui.usecase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
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
import com.bayer.bhc.doc41webui.domain.DeliveryOrShippingUnit;
import com.bayer.bhc.doc41webui.domain.DocMetadata;
import com.bayer.bhc.doc41webui.domain.DocTypeDef;
import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.bhc.doc41webui.domain.InspectionLot;
import com.bayer.bhc.doc41webui.domain.QMBatchObject;
import com.bayer.bhc.doc41webui.domain.SDReferenceCheckResult;
import com.bayer.bhc.doc41webui.integration.sap.service.AuthorizationRFCService;
import com.bayer.bhc.doc41webui.integration.sap.service.KgsRFCService;
import com.bayer.bhc.doc41webui.service.httpclient.HttpClientService;
import com.bayer.bhc.doc41webui.service.repository.TranslationsRepository;
import com.bayer.bhc.doc41webui.usecase.documenttypes.AWBDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ArtworkDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.BOLDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CMRDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DeliveryCertDownCountryDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DeliveryCertDownCustomerDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DeliveryCertUploadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.FDACertDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.LayoutDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.PZTecDrawingDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.PackMatSpecDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ShippersDeclDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.SupplierCOADocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.TecPackDelReqDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.WaybillDocumentType;
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
		addDocumentType(new PackMatSpecDocumentType());
		addDocumentType(new PZTecDrawingDocumentType());
		addDocumentType(new ShippersDeclDocumentType());
		addDocumentType(new BOLDocumentType());
		addDocumentType(new WaybillDocumentType());
		addDocumentType(new AWBDocumentType());
		addDocumentType(new FDACertDocumentType());
		addDocumentType(new SupplierCOADocumentType());
		addDocumentType(new CMRDocumentType());
		addDocumentType(new DeliveryCertDownCountryDocumentType());
		addDocumentType(new DeliveryCertDownCustomerDocumentType());
		addDocumentType(new DeliveryCertUploadDocumentType());
		addDocumentType(new ArtworkDocumentType());
		addDocumentType(new LayoutDocumentType());
		addDocumentType(new TecPackDelReqDocumentType());
	}
	
	private void addDocumentType(DocumentType documentType) {
		String typeConst = documentType.getTypeConst();
		documentTypes.put(typeConst, documentType);
	}

	public synchronized DocMetadata getMetadata(String type) throws Doc41BusinessException{
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
//			ContentRepositoryInfo crepInfo = metadata.getContentRepository();
			DocTypeDef docDef = metadata.getDocDef();
			String d41id = docDef.getD41id();
			return authorizationRFCService.getOpenDeliveries(d41id,carrier);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("getOpenDeliveries",e);
		}
	}
	
	public SDReferenceCheckResult checkDeliveryForPartner(String carrier,String referenceNumber) throws Doc41BusinessException{
		try {
//			//TODO remove mock SD mock
//			if(true){
//				if(referenceNumber.startsWith("V")){
//					return new SDReferenceCheckResult(referenceNumber, SDReferenceCheckResult.TYPE_SHIPPING_UNIT_NUMBER, null);
//				}
//				return new SDReferenceCheckResult(referenceNumber, SDReferenceCheckResult.TYPE_DELIVERY_NUMBER, null);
//			}
			return authorizationRFCService.checkDeliveryForPartner(carrier, referenceNumber);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("checkDeliveryForPartner",e);
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
			Map<String, String> attributeValues, String objId,String fileName, String sapObject) throws Doc41BusinessException {
		try{
			DocMetadata metadata = getMetadata(type);
			ContentRepositoryInfo crepInfo = metadata.getContentRepository();
			DocTypeDef docDef = metadata.getDocDef();
			String d41id = docDef.getD41id();
			
			if(!StringTool.isTrimmedEmptyOrNull(fileName) && metadata.isFileNameAttribAvailable()){
				attributeValues.put(metadata.getFileNameAttibKey(), fileName);
			}
			kgsRFCService.setAttributesForNewDocument(d41id,fileId,crepInfo.getContentRepository(),crepInfo.getDocClass(),objId,sapObject,attributeValues);
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
			List<String> sapObjList = docDef.getSapObjList();
			List<HitListEntry> allResults = new ArrayList<HitListEntry>();
			for (String sapObj : sapObjList) {
				List<HitListEntry> oneResult = kgsRFCService.findDocs(d41id, sapObj, objectId, attributeValues, maxResults, maxVersionOnly);
				allResults.addAll(oneResult);
			}
			
			return allResults;
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
	
	public String checkForUpload(Errors errors, String type, String partnerNumber, String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes) throws Doc41BusinessException{
		String sapObject = getDocTypeForUpload(type).checkForUpload(errors, this, partnerNumber, objectId, attributeValues,viewAttributes);
		DocMetadata metadata = getMetadata(type);
		DocTypeDef docDef = metadata.getDocDef();
		List<String> sapObjList = docDef.getSapObjList();
		if(StringTool.isTrimmedEmptyOrNull(sapObject)){//no mapping configured, only allowed if only one busob in metadata
			if(sapObjList.size()==1){
				return sapObjList.get(0);
			} else {
				throw new Doc41BusinessException("no SAP_OBJECT mapping in DocType, but "+sapObjList.size()+" possible values in metadata for type "+type);
			}
		} else { //compare sapobj from mapping with metadata
			if(sapObjList.contains(sapObject)){
				return sapObject;
			} else {
				throw new Doc41BusinessException("SAP_OBJECT "+sapObject+" not in metadata of type "+type);
			}
		}
	}
	
	public void checkForDownload(Errors errors, String type, String partnerNumber, List<String> objectIds, Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException{
		getDocTypeForDownload(type).checkForDownload(errors, this, partnerNumber, objectIds, attributeValues,viewAttributes);
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

	public String getPartnerNumberType(String type) throws Doc41BusinessException {
		return getDocType(type).getPartnerNumberType();
	}
	

	public String getUploadPermission(String type) throws Doc41BusinessException {
		return getDocTypeForUpload(type).getPermissionUpload();
	}
	
	public String getDownloadPermission(String type) throws Doc41BusinessException {
		return getDocTypeForDownload(type).getPermissionDownload();
	}

	public String checkArtworkForVendor(String vendorNumber) throws Doc41BusinessException {
		try {
			return authorizationRFCService.checkArtworkForVendor(vendorNumber);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("checkArtworkForVendor",e);
		}
	}

	public String checkLayoutForVendor(String vendorNumber) throws Doc41BusinessException {
		try {
			return authorizationRFCService.checkLayoutForVendor(vendorNumber);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("checkLayoutForVendor",e);
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

	public boolean isPartnerNumberUsed(String type) throws Doc41BusinessException {
		return !StringTool.isTrimmedEmptyOrNull(getPartnerNumberType(type));
	}

}
