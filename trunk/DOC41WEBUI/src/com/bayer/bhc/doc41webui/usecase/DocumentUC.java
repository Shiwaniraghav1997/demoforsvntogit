package com.bayer.bhc.doc41webui.usecase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.logging.Doc41LogEntry;
import com.bayer.bhc.doc41webui.common.util.UrlParamCrypt;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.ContentRepositoryInfo;
import com.bayer.bhc.doc41webui.domain.DeliveryOrShippingUnit;
import com.bayer.bhc.doc41webui.domain.DocInfoComponent;
import com.bayer.bhc.doc41webui.domain.DocMetadata;
import com.bayer.bhc.doc41webui.domain.DocTypeDef;
import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.bhc.doc41webui.domain.InspectionLot;
import com.bayer.bhc.doc41webui.domain.PermissionProfiles;
import com.bayer.bhc.doc41webui.domain.QMBatchObject;
import com.bayer.bhc.doc41webui.domain.SDReferenceCheckResult;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.integration.sap.service.AuthorizationRFCService;
import com.bayer.bhc.doc41webui.integration.sap.service.BwRFCService;
import com.bayer.bhc.doc41webui.integration.sap.service.DirsRFCService;
import com.bayer.bhc.doc41webui.integration.sap.service.KgsRFCService;
import com.bayer.bhc.doc41webui.service.httpclient.HttpClientService;
import com.bayer.bhc.doc41webui.service.repository.TranslationsRepository;
import com.bayer.bhc.doc41webui.service.repository.UserManagementRepository;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForUpdateResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DirectDownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls.ArtworkForLayoutSupplierDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls.LayoutForLayoutSupplierDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls.PZTecDrawingForLayoutSupplierDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls.PackMatSpecForLayoutSupplierDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm.AntiCounSpecForPMSupplierDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm.ArtworkForPMSupplierDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm.LayoutForPMSupplierDocumentType;
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
	
    @SuppressWarnings("unused")
    @Autowired
    private DirsRFCService dirsRFCService;
    
	@Autowired
	private BwRFCService bwRFCService;
	
	@Autowired
	private TranslationsRepository translationsRepository;
	
    @Autowired
    private UserManagementRepository userManagementRepository;
    
	@Autowired
	private HttpClientService httpClientService;
	
	private Map<String, DocMetadata> docMetadataContainer;
	
	private final Map<String,DocumentType> documentTypes;
    private final Map<String,DocumentType> documentTypesBySapId;
	private Map<String,ArrayList<String>> documentTypesByDownloadPermissionType = null;
	
	public DocumentUC() {
		documentTypes = new HashMap<String, DocumentType>();
        documentTypesBySapId = new HashMap<String, DocumentType>();
		
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
		addDocumentType(new PackMatSpecForLayoutSupplierDocumentType());
		addDocumentType(new PZTecDrawingForLayoutSupplierDocumentType());
		
		addDocumentType(new ArtworkForPMSupplierDocumentType());
		addDocumentType(new LayoutForPMSupplierDocumentType());
		addDocumentType(new PackMatSpecForPMSupplierDocumentType());
		addDocumentType(new PZTecDrawingForPMSupplierDocumentType());
		addDocumentType(new TecPackDelReqForPMSupplierDocumentType());
        addDocumentType(new AntiCounSpecForPMSupplierDocumentType());
	}
	
	private void addDocumentType(DocumentType documentType) {
		documentTypes.put(documentType.getTypeConst(), documentType);
        documentTypesBySapId.put(documentType.getSapTypeId(), documentType);
	}

	/**
	 * Get al list of all document types belonging to the same permission type.
	 * This is required to allow global search/download for document types of the same permission type, e.g. DOC_LS, DOC_PM, DOC_SD and eventually DOC_QM.
	 * This way we know, which permissions belong together. But still needed: check for each document type, taht the user has the corresponding permission for download.
	 * Permission types (representing a document group) should all start with DOC_ as prefix to differentiate for a single document type. 
	 * @param mPermissionType the permission type (used instead of a document type) to handle a group of document types instead of a single one.
	 * @return
	 */
	public ArrayList<String> getAllDownloadDocumentTypesOfSamePermissionType(String mPermissionType) throws Doc41TechnicalException {
	    if (documentTypesByDownloadPermissionType == null) {
	        Map<String,ArrayList<String>> mDocumentTypesByDownloadPermissionType = new HashMap<String, ArrayList<String>>();
	        HashMap<String,PermissionProfiles>mProfileByCode = new HashMap<String, PermissionProfiles>();
	        List <PermissionProfiles> mPermList = userManagementRepository.getAllPermissions();
	        for (PermissionProfiles mPP : mPermList) {
	            mProfileByCode.put(mPP.getPermissionCode(), mPP);
	        }
	        for (DocumentType documentType : documentTypes.values()) {
	            String typeConst = documentType.getTypeConst();
	            if (documentType instanceof DownloadDocumentType) {
	                String mPerm = ((DownloadDocumentType)documentType).getPermissionDownload();
	                PermissionProfiles mPP = mProfileByCode.get(mPerm); 
	                String mPermType = (mPP == null) ? null : mPP.getType();
	                ((DownloadDocumentType) documentType).setDownloadPermissionType(mPermType);
	                if (mPermType == null) {
	                    if (mPP != null) {
	                        Doc41Log.get().warnMessageOnce(this, null, "Download-Permission with code: " + mPerm + " for document type: " + typeConst + " has no permission type!");
	                    }
	                } else {
	                    ArrayList<String> mList = mDocumentTypesByDownloadPermissionType.get(mPermType);
	                    if (mList == null) {
	                        mList = new ArrayList<String>();
	                        mDocumentTypesByDownloadPermissionType.put(mPermType, mList);
	                    }
	                    mList.add(typeConst);
	                }
	            }
	        }
	        documentTypesByDownloadPermissionType = mDocumentTypesByDownloadPermissionType;
	    }
	    return documentTypesByDownloadPermissionType.get(mPermissionType);
	}
	
	public DocMetadata getMetadata(String type) throws Doc41BusinessException{
	    DocumentType dt = getDocType(type);
	    if (dt == null) {
	        throw new Doc41BusinessException("document type "+type+" unknown!");
	    }
		String sapDocType = dt.getSapTypeId();
		DocMetadata docMetadata = null;
		if (dt.isKgs()) {
		    docMetadata = getDocMetadataBySapDocType(sapDocType);
	        if( docMetadata == null ) {
	            throw new Doc41BusinessException("document type "+type+"/"+sapDocType+" not found in SAP");
	        }
		} else if (dt.isDirs()) {
		    docMetadata = createTempMetaDataForDIRS(dt);
		} else {
            throw new Doc41BusinessException("document type "+type+"/"+sapDocType+", unknown type of document store (not KGS and not DIRS!!!)");
		}
		return docMetadata;
	}

	private DocMetadata createTempMetaDataForDIRS( DocumentType dt ) {
        DocTypeDef docDef = new DocTypeDef();
        docDef.setD41id(dt.getSapTypeId());
        docDef.setTechnicalId(null);                                //  currently needed only in setAttributesForNewDocument(KGS)
        docDef.setDescription("DIRS emulated: " + dt.getTypeConst());
        docDef.setSapObjList(new ArrayList<String>());              // additional search (and upload) attributes used for search only, if no objectIds specified(matNos), but they are obligatory for DIRS
        docDef.setDvs(false);                                       // KGS only, marks extra DocInfoComponent available via extra RFC...
        docDef.setTranslations(new HashMap<String, String>());      // what kind of translations? Seems not to be used...

        ContentRepositoryInfo contentRepository = new ContentRepositoryInfo();
        //FIXME: do we need that for DIRS? currently needed only in Upload(KGS), Download(KGS), setAttributesForNewDocument(KGS)
        //contentRepository.setContentRepository("...");
        contentRepository.setAllowedDocClass("*");
        DocMetadata docMetadata = new DocMetadata(docDef);
        docMetadata.setAttributes(new ArrayList<Attribute>());
        docMetadata.setContentRepository(contentRepository);
	    return docMetadata;
	}
	
	private synchronized Map<String, DocMetadata> getDocMetadataContainer() throws Doc41BusinessException {
		try{
			if(docMetadataContainer==null){
				Set<String> languageCodes = translationsRepository.getLanguageCodes().keySet();
				docMetadataContainer = kgsRFCService.getDocMetadata(languageCodes, getAllDocTypesBySapTypeIdMap() /*getSupportedSapDocTypes()*/);
			}
			return docMetadataContainer;
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("getMetadata",e);
		}
	}

	/**
	 * This returns real Metadata on a KGS Custumized RFC. In case of DIRS an "empty" MetaData object is created.
	 * @param sapDocType
	 * @return
	 * @throws Doc41BusinessException
	 */
	public DocMetadata getDocMetadataBySapDocType(String sapDocType) throws Doc41BusinessException {
		Map<String, DocMetadata> mdContainer = getDocMetadataContainer();
		return mdContainer.get(sapDocType);
	}
	
    /**
     * This returns a list of real Metadata Objects on KGS Custumized RFCs. No DIRS Metadata in this list.
     * @param sapDocType
     * @return
     * @throws Doc41BusinessException
     */
	private Set<String> getSapDocTypesFromMetadata() throws Doc41BusinessException {
		return getDocMetadataContainer().keySet();
	}
	
	/**
	 * Get a list of all DocumentType Doc41Ids/SapTypeIds by defined DocumentTypes (including not only DocTypes with Custumizing RFCs - KGS but also those without - DIRS).
	 * @return
	 */
    @SuppressWarnings("unused")
    private Set<String> getSapDocTypesList() {
        return getAllDocTypesBySapTypeIdMap().keySet();
    }
    
    /**
     * Create a selection list for KGS Customizing Metadata (not including Types having no Customizing Metadata, e.g. DIRS)
     * @return
     * @throws Doc41BusinessException
     */
	public List<SelectionItem> getDocMetadataSelectionItems() throws Doc41BusinessException {
		List<SelectionItem> items = new ArrayList<SelectionItem>();
		
		for (String sapDocType : getSapDocTypesFromMetadata()) {
			DocMetadata docMetadata = getDocMetadataBySapDocType(sapDocType);
			DocTypeDef docDef = docMetadata.getDocDef();
			String description = docDef.getDescription();
			DocumentType dt = getDocTypeBySapId(sapDocType);
			String group = (dt == null) ? "???" : dt.getGroup(); 
			
			SelectionItem item = new SelectionItem(sapDocType, "" + group + " - " + sapDocType + ": " + description);
			items.add(item);
		}
		Collections.sort(items, new Comparator<SelectionItem>() {
			@Override
			public int compare(SelectionItem o1, SelectionItem o2) {
				return o1.getLabel().compareTo(o2.getLabel());
			}
		});
		return items ;
	}

    /**
     * Get the Collection of all DocumentType by TypeConstant
     * @return
     */
    @SuppressWarnings("unused")
    private Map<String,DocumentType> getAllDocTypesMap() {
        return documentTypes;
    }
    
    /**
     * Get the Collection of all DocumentType by SapTypeId 
     * @return
     */
    private Map<String,DocumentType> getAllDocTypesBySapTypeIdMap() {
        return documentTypesBySapId;
    }
    
	/**
	 * Get a Set of all document types sapIds.
	 * @return
	 */
	@SuppressWarnings("unused")
    private Set<String> getSupportedSapDocTypes() {
	    return documentTypesBySapId.keySet();
/*	    
        Set<String> supportedSapTypes = new HashSet<String>();
        for (DocumentType docType : documentTypes.values()) {
            supportedSapTypes.add(docType.getSapTypeId());
        }
        return supportedSapTypes ;
*/
	}
	
	public List<String> getAvailableSDDownloadDocumentTypes() throws Doc41TechnicalException {
	    return getAllDownloadDocumentTypesOfSamePermissionType(DocumentType.GROUP_SD); // should be defined as Constants soon
/*
	    Set<String> sdDLTypes = new HashSet<String>();
        for (DocumentType docType : documentTypes.values()) {
            if(docType instanceof SDDocumentType && docType instanceof DownloadDocumentType){
                sdDLTypes.add(docType.getTypeConst());
            }
        }
        return sdDLTypes ;
 */
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
        DocumentType documentType = documentTypes.get(doctype);
        List<Attribute> filteredAttributes = new ArrayList<Attribute>();
        if (documentType.isKgs()) {
            DocMetadata metadata = getMetadata(doctype);
            List<Attribute> kgsAttributes = metadata.getAttributes();
            Set<String> excludedAttributes = documentType.getExcludedAttributes();
            for (Attribute attribute : kgsAttributes) {
                String name = attribute.getName();
                if(excludedAttributes==null || !excludedAttributes.contains(name)){
                    boolean fileNameFilterCheck = !filterFileName || !StringTool.equals(name, Doc41Constants.ATTRIB_NAME_FILENAME);
                    boolean globalExcludeCheck = !StringTool.equals(name, Doc41Constants.ATTRIB_NAME_WEBUI_USER);
                    if(fileNameFilterCheck && globalExcludeCheck){
                        filteredAttributes.add(attribute);
                    }
                }
            }
        }
		//Doc41Constants.ATTRIB_NAME_FILENAME
		return filteredAttributes;
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

	public String uploadDocument(String type, File localFile, String contentType, String fileName, String sapObjId, String sapObjType) throws Doc41BusinessException {
		try{
			DocMetadata metadata = getMetadata(type);
			ContentRepositoryInfo crepInfo = metadata.getContentRepository();
			String crep = crepInfo.getContentRepository();
			DocTypeDef docDef = metadata.getDocDef();

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
				logWebMetrix("DOC_UPLOADED",guid,type, sapObjId, sapObjType,fileName);
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
	
	public void checkFileTypeBeforeUpload(String type,String fileName) throws Doc41BusinessException{
	    DocMetadata metadata = getMetadata(type);
        ContentRepositoryInfo crepInfo = metadata.getContentRepository();
        getDocClass(fileName,crepInfo.getAllowedDocClass());
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
				kgsAttributeValues.put(Doc41Constants.ATTRIB_NAME_I_DVSOBJTYPE, sapObject);
				kgsAttributeValues.put(Doc41Constants.ATTRIB_NAME_I_DVSDOCTYPE, docDef.getTechnicalId());
				kgsAttributeValues.put(Doc41Constants.ATTRIB_NAME_I_NAMETYPE, "2");
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
		String[] mFileSeg = StringTool.split(fileNameUpper, '.');
		String mFileExt = (mFileSeg.length == 1) ? "" : mFileSeg[mFileSeg.length - 1];
		
		docClass = (String)ConfigMap.get().getSubConfig("documents", "filetype").get(mFileExt);
		/*
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
		*/
		if (docClass == null) {
		    //String extension = fileNameUpper.substring(fileNameUpper.indexOf('.'));
			throw new UnknownExtensionException(mFileExt);
		}
		
		if(!isAllClassesAllowed && !StringTool.equals(docClass, allowedDocClass.toUpperCase())){
			throw new DocClassNotAllowed(docClass,allowedDocClass);
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
	
	public List<HitListEntry> searchDocuments(ArrayList<String> pTypes, List<String> objectIds,
			Map<String, String> attributeValues, int maxResults, boolean maxVersionOnly)
					throws Doc41BusinessException {
		try{
            List<HitListEntry> allResults = new ArrayList<HitListEntry>();
            ArrayList<String> d41idList = new ArrayList<String>();
            Map<Integer, String> seqToKeyGlo = new HashMap<Integer, String>();
            Map<String, Map<Integer, String>> seqToKeyAllTypes = new HashMap<String, Map<Integer,String>>();
            HashSet<String>mKnownKeys = new HashSet<String>();
            for (String mType : pTypes) {
                Map<Integer, String> seqToKey = new HashMap<Integer, String>();
                //DocumentType docType = getDocType(mType);
                DocMetadata metadata = getMetadata(mType);
                DocTypeDef docDef = metadata.getDocDef();
                d41idList.add( docDef.getD41id() );
                checkAttribsWithCustomizing(attributeValues,mType);
                getSeqToKeyFromDefinitions(mType, seqToKey, seqToKeyGlo, mKnownKeys);
                seqToKeyAllTypes.put(mType, seqToKey);
            }
            //List<String> sapObjList = docDef.getSapObjList();
            //if (docType.isKgs()) {
			
            if (objectIds != null && objectIds.isEmpty()) {
                objectIds = null;
            }
		        //if(objectIds == null){
		        allResults = bwRFCService.findDocs(d41idList, null, objectIds, attributeValues, maxResults, maxVersionOnly,seqToKeyGlo);
		        /* NO MORE USED, FindDocsMulti no takes care itself for al sapObj (types of objectIds = numbers, e.g. material number, po number, delivery number, ...)
		          } else {
		            for (String sapObj : sapObjList) {
		                List<HitListEntry> oneResult = bwRFCService.findDocs(d41id, sapObj, objectIds, attributeValues, maxResults, maxVersionOnly,seqToKey);
		                allResults.addAll(oneResult);
		            }
		        }*/
		        for (HitListEntry hitListEntry : allResults) {
		            String mDoc41Id = hitListEntry.getDoc41Id();
		            DocumentType dt = getDocTypeBySapId(mDoc41Id);
                    hitListEntry.setType(dt.getTypeConst());
// TODO: Bug on switch of DocType, prev. DocTypes Cust-Attr still filled (const, stay last value)
		            hitListEntry.initCustValuesMap(/*/seqToKeyGlo/*/ seqToKeyAllTypes.get(dt.getTypeConst()) /**/ );
				
		            Map<String,String> params = new LinkedHashMap<String, String>();
		            params.put(Doc41Constants.URL_PARAM_TYPE,hitListEntry.getType());
		            params.put(Doc41Constants.URL_PARAM_DOC_ID,hitListEntry.getDocId());
		            params.put(Doc41Constants.URL_PARAM_CWID,UserInSession.getCwid());
		            params.put(Doc41Constants.URL_PARAM_SAP_OBJ_ID,hitListEntry.getObjectId());
		            params.put(Doc41Constants.URL_PARAM_SAP_OBJ_TYPE,hitListEntry.getObjectType());
		            params.put(Doc41Constants.URL_PARAM_FILENAME,StringTool.encodeURLWithDefaultFileEnc(StringTool.nullToEmpty(hitListEntry.getFileName())));
		            String key = UrlParamCrypt.encryptParameters(params);
		            hitListEntry.setKey(key);
		        }
		    //}
			return allResults;
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("searchDocuments",e);
		}
	}



	private Map<Integer, String> getSeqToKeyFromDefinitions(String type, Map<Integer, String> attributeSeqToKey, Map<Integer, String> attributeSeqToKeyGlo, HashSet<String>mKnownKeys) throws Doc41BusinessException {
		List<Attribute> attributeDefinitions = getMetadata(type).getAttributes();
		for (Attribute attribute : attributeDefinitions) {
			String key = attribute.getName();
			Integer seq = attribute.getSeqNumber();
			attributeSeqToKey.put(seq, key);
			if (!mKnownKeys.contains(key)) {
			    mKnownKeys.add(key);
			    attributeSeqToKeyGlo.put(Integer.valueOf(mKnownKeys.size()), key);
			    Doc41Log.get().debug(this, null, "**********************************************  SEQ2KEY new: " + key + ": " + mKnownKeys.size() );
			}
			Doc41Log.get().debug(this, null, "**********************************************  SEQ2KEY - " + type + " - " + key + ": " + seq );
		}
		return attributeSeqToKey;
	}

	public String downloadDocument(HttpServletResponse targetResponse, String type,
			String docId,String fileName, String sapObjId, String sapObjType) throws Doc41BusinessException {
		try{
			DocMetadata metadata = getMetadata(type);
			ContentRepositoryInfo crepInfo = metadata.getContentRepository();
			// FIXME: DIRS support needed
			String crepId = crepInfo.getContentRepository();
			DocTypeDef docDef = metadata.getDocDef();
			String compId = null;
			if(docDef.isDvs()){
				DocInfoComponent comp = kgsRFCService.getDocInfo(crepId,docId);
				compId = comp.getCompId();
			}
		
			URI docURL = kgsRFCService.getDocURL(crepId, docId,compId);
		
			String statusText = httpClientService.downloadDocumentToResponse(docURL,targetResponse,docId,fileName);
			logWebMetrix("DOC_DOWNLOADED",docId,type,sapObjId,sapObjType,fileName);
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
    
    /**
     * Get a certain DownloadDocumentType or filtered DownloadDocumentType Group.
     * @param type may be an explicite document type name of a name of a DocumentType group, see DocumentType.GROUP_* for available groups (identified by the permission group of related Document permissions)
     * @param type usr to filter list also by usr permissions - may result in an empty list...
     * @return
     * @throws Doc41BusinessException
     */
	public List<DownloadDocumentType> getFilteredDocTypesForDownload(String type, User usr) throws Doc41BusinessException{
	    List<String> mTypeNames;
        try {
            mTypeNames = getAllDownloadDocumentTypesOfSamePermissionType(type);
        } catch (Doc41TechnicalException e) {
            throw new Doc41BusinessException("Failed to build/resolve DocumentGroups, occurred on chec for DocumentType(s): " + type, e);
        }
	    List<DownloadDocumentType> mDocTypes = new ArrayList<DownloadDocumentType>();
	    if (mTypeNames == null) { // no TypeGroup, so is explicite (single) Type
	        mTypeNames = new ArrayList<String>();
	        mTypeNames.add( type );
	    }
	    for (String mType: mTypeNames) {
	        DocumentType mDocType = getDocType(mType);
	        if (mDocType == null) {
	            throw new Doc41BusinessException("Undefined DocumentType requested: " + type + "." + mType);
	        }
	        if (mDocType instanceof DownloadDocumentType) {
	            if (usr.hasPermission( ((DownloadDocumentType)mDocType).getPermissionDownload() )) {
	                mDocTypes.add((DownloadDocumentType)mDocType);
	            }
	        }
	    }
		if( mDocTypes.isEmpty() ) {
			Doc41Log.get().warnMessageOnce(this, null, "Requested DocumentType(Group) is/has no DownloadDocumentType(s), List is empty: " + type);
		}
		return mDocTypes;
	}
	
	private DirectDownloadDocumentType getDocTypeForDirectDownload(String type) throws Doc41BusinessException{
		DocumentType documentType = getDocType(type);
		if(!(documentType instanceof DirectDownloadDocumentType)){
			throw new Doc41BusinessException("doctype not enabled for direct download: "+type);
		}
		return (DirectDownloadDocumentType) documentType;
	}

	/**
	 * Get a DocumentType by its TypeConstant.
	 * @param typeConstant
	 * @return
	 * @throws Doc41BusinessException thrown, if not available
	 */
	public DocumentType getDocType(String typeConstant) throws Doc41BusinessException {
		DocumentType documentType = documentTypes.get(typeConstant);
		if(documentType==null){
			throw new Doc41BusinessException("unknown doctype, typeConstant: "+typeConstant);
		}
		return documentType;
	}

	/**
	 * Get a DocumentType by its SapTypeId
	 * @param sapTypeId
	 * @return
	 * @throws Doc41BusinessException thrown, if not available
	 */
    public DocumentType getDocTypeBySapId(String sapTypeId) throws Doc41BusinessException {
        DocumentType documentType = documentTypesBySapId.get(sapTypeId);
        if(documentType==null){
            throw new Doc41BusinessException("unknown doctype, sapTypeId: "+sapTypeId);
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

	/**
	 * Check for the vendor (partner), if already a artwork document exist with the same material number.
	 * Upload only allowed, if already first document existing (as follow up)
	 * @param vendorNumber the vondor/partner number
	 * @param materialNumber the material number of their artwork document (new, possibly not yet checked but prepared TODO)
	 * @param sapTypeId
	 * @return seems to be null if ok, else an error message (not 100% sure, if also success message may be returned)
	 * @throws Doc41BusinessException
	 */
	public String checkArtworkLayoutForVendor(String vendorNumber, String materialNumber, String sapTypeId) throws Doc41BusinessException {
		try {
			return authorizationRFCService.checkArtworkLayoutForVendor(vendorNumber, materialNumber, sapTypeId);
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
	
    public String checkMaterialForVendor(String vendorNumber, String materialNumber) throws Doc41BusinessException {
        try {
            return authorizationRFCService.checkMaterialForVendor(vendorNumber, materialNumber);
        } catch (Doc41ServiceException e) {
            throw new Doc41BusinessException("checkPOAndMaterialForVendor",e);
        }
    }
    
	public List<InspectionLot> getInspectionLotsForVendorBatch(String vendor,
			String vendorBatch, String plant) throws Doc41BusinessException {
		try{
			return authorizationRFCService.getInspectionLotsForVendorBatch(vendor,vendorBatch,plant);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("getInspectionLotsForVendorBatch",e);
		}
	}
	
	public List<QMBatchObject> getBatchObjectsForSupplier(String supplier, String plant, String material,
			String batch, String order) throws Doc41BusinessException {
		try{
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
	
	private void logWebMetrix(String action, String docId, String docType, String sapObjId, String sapObjType,String fileName) {
		String loggedInUser = UserInSession.getCwid();
		Doc41Log.get().logWebMetrix(this.getClass(),new Doc41LogEntry(loggedInUser, loggedInUser, "DOCUMENTS", action, 
				docId, docType, sapObjId, sapObjType, fileName, null, null, null, null),loggedInUser);
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
		List<Attribute> attributeDefinitions = getMetadata(type).getAttributes();
		
		Set<String> definedAttribKeys = new HashSet<String>();
		for (Attribute attribute : attributeDefinitions) {
			definedAttribKeys.add(attribute.getName());
		}
		
		StringBuilder missingAttrKeysInCustomizing = new StringBuilder();
		if( (attributeValues != null) ){
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
			Doc41Log.get().debug(getClass(), UserInSession.getCwid(), "CustomizingMissmatch: attributes "+missingAttrKeysInCustomizing+" no longer in customizing");
		}
		
	}

}
