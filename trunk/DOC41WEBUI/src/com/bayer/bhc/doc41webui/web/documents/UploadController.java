package com.bayer.bhc.doc41webui.web.documents;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.UploadForm;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.integration.db.TranslationsDAO;
import com.bayer.bhc.doc41webui.usecase.DocClassNotAllowed;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.UnknownExtensionException;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForUpdateResult;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.bhc.doc41webui.web.Doc41Tags;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.business.sbeanaccess.BATranslationsException;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

public abstract class UploadController extends AbstractDoc41Controller {
	
	private static final int MAX_FILE_NAME_SIZE=64;
	
	@Autowired
	protected DocumentUC documentUC;
	
	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) throws Doc41BusinessException{
		String type = request.getParameter("type");
		if(StringTool.isTrimmedEmptyOrNull(type)){
			throw new IllegalArgumentException("type is missing");
		}
		String permission = documentUC.getUploadPermission(type);
		return usr.hasPermission(permission);
    }

	//default implementation
	public UploadForm get(@RequestParam() String type) throws Doc41BusinessException{
		String language = LocaleInSession.get().getLanguage();
		UploadForm uploadForm = createNewForm();
		uploadForm.setType(type);
		uploadForm.initPartnerNumbers(documentUC.hasCustomerNumber(type),getLastCustomerNumberFromSession(),
				documentUC.hasVendorNumber(type),getLastVendorNumberFromSession());
		List<Attribute> attributeDefinitions = documentUC.getAttributeDefinitions(type,true);
		uploadForm.initAttributes(attributeDefinitions,language);
		return uploadForm;
	}

	protected UploadForm createNewForm() {
		return new UploadForm();
	}
	
	//default implementation
	public String postUpload(@ModelAttribute UploadForm uploadForm,BindingResult result) throws Doc41BusinessException { //ggf. kein modelattribute wegen sessionattribute
		String failedURL = getFailedURL();
		String type = uploadForm.getType();
		uploadForm.initPartnerNumbers(documentUC.hasCustomerNumber(type),null,
				documentUC.hasVendorNumber(type),null);
		checkPartnerNumbers(result,type,uploadForm.getCustomerNumber(),uploadForm.getVendorNumber());
		checkAndFillObjectId(result,type,uploadForm);
		MultipartFile file = uploadForm.getFile();
        if(StringTool.isTrimmedEmptyOrNull(uploadForm.getFileId())){
		    String fileName = limitFilenameSize(file.getOriginalFilename());
		    uploadForm.setFileName(fileName);
		}
		checkFileParameter(result,uploadForm.getFile(),uploadForm.getFileId(),uploadForm.getFileName(),type);
		if(result.hasErrors()){
			return failedURL;
		}
		Map<String, String> attributeValues = uploadForm.getAttributeValues();
		CheckForUpdateResult checkResult = documentUC.checkForUpload(result, type, 
				uploadForm.getCustomerNumber(), uploadForm.getVendorNumber(), uploadForm.getObjectId(),
				attributeValues, uploadForm.getViewAttributes());
		if(result.hasErrors()){
			return failedURL;
		}
		Map<String, String> allAttributeValues = new HashMap<String, String>(attributeValues);
		Map<String, String> additionalAttributes = checkResult.getAdditionalAttributes();
		if(additionalAttributes!=null){
			allAttributeValues.putAll(additionalAttributes);
		}
		
		if(StringTool.isTrimmedEmptyOrNull(uploadForm.getFileId())){
			File localFile = documentUC.checkForVirus(file);
			if(localFile==null){
				result.reject("VirusDetected");
				return failedURL;
			}
			
			String fileId = documentUC.uploadDocument(type,localFile,file.getContentType(),uploadForm.getFileName(),uploadForm.getObjectId(),checkResult.getSapObject());
			uploadForm.setFileId(fileId);
		}
		if(StringTool.isTrimmedEmptyOrNull(uploadForm.getFileId())){
			result.reject("UploadFailed");
			return failedURL;
		} 
		//set attributes in sap
		documentUC.setAttributesForNewDocument(type,uploadForm.getFileId(),allAttributeValues,uploadForm.getObjectId(),uploadForm.getFileName(),checkResult.getSapObject(),checkResult.getVkOrg());
		
		String notificationEMail = uploadForm.getNotificationEMail();
		if(!StringTool.isTrimmedEmptyOrNull(notificationEMail)){
			documentUC.sendUploadNotification(notificationEMail,getTypeName(type,false),uploadForm.getFileName(),uploadForm.getFileId());
		}
		
		
		return "redirect:/documents/uploadsuccess?type="+type+"&uploadurl="+getSuccessURL();
	}

	private String limitFilenameSize(String originalFilename) {
		if(originalFilename==null || originalFilename.length()<MAX_FILE_NAME_SIZE){
			return originalFilename;
		}
		int indexExtensionDot = originalFilename.lastIndexOf('.');
		String extension;
		String nameWOExtension;
		if(indexExtensionDot>-1){
			extension=originalFilename.substring(indexExtensionDot);
			nameWOExtension=originalFilename.substring(0,indexExtensionDot);
		} else {
			extension="";
			nameWOExtension=originalFilename;
		}
		int maxLengthNameWOExtension = MAX_FILE_NAME_SIZE - extension.length();
		String shortNameWOExtension = nameWOExtension.substring(0, maxLengthNameWOExtension);
		
		return shortNameWOExtension+extension;
	}

	private String getTypeName(String type,boolean userLocale)  {
		try {
			Locale locale;
			if(userLocale){
				locale = LocaleInSession.get();
			} else {
				locale = Locale.ENGLISH;
			}
			Tags tags = new Doc41Tags(TranslationsDAO.SYSTEM_ID, "documents", "*", locale);
			return tags.getTag(type);
		} catch (BATranslationsException e) {
		    Doc41Log.get().error(this.getClass(), UserInSession.getCwid(), e);
			return "["+type+"]";
		}
	}

	private void checkAndFillObjectId(BindingResult errors, String type,
			UploadForm uploadForm) throws Doc41BusinessException {
		String objectId = uploadForm.getObjectId();
		if(StringTool.isTrimmedEmptyOrNull(objectId)){
			errors.rejectValue("objectId","ObjectId"+type+"Missing");
		} else {
			int objectIdFillLength = documentUC.getDocumentFillLength(type);
			if(objectId.length()<objectIdFillLength){
				objectId = StringTool.minLString(objectId, objectIdFillLength, '0');
				uploadForm.setObjectId(objectId);
			}
		}
	}

	private void checkPartnerNumbers(BindingResult errors, String type,
			String customerNumber,String vendorNumber) throws Doc41BusinessException {
		//customer
		if(documentUC.hasCustomerNumber(type)){
			if(StringTool.isTrimmedEmptyOrNull(customerNumber)){
				errors.rejectValue("customerNumber","CustomerNumberMissing");
			} else {
				if(!UserInSession.get().hasCustomer(customerNumber)){
					errors.rejectValue("customerNumber","CustomerNotAssignedToUser");
				} else {
					setLastCustomerNumberFromSession(customerNumber);
				}
			}
		}
		if(documentUC.hasVendorNumber(type)){
			if(StringTool.isTrimmedEmptyOrNull(vendorNumber)){
				errors.rejectValue("vendorNumber","VendorNumberMissing");
			} else {
				if(!UserInSession.get().hasVendor(vendorNumber)){
					errors.rejectValue("partnerNumber","VendorNotAssignedToUser");
				} else {
					setLastVendorNumberFromSession(vendorNumber);
				}
			}
		}
	}

	private void checkFileParameter(BindingResult errors, MultipartFile file,
			String fileId, String fileName,String type) throws Doc41BusinessException {
		boolean isfileNull = file==null;
		boolean isFileEmpty = file.getSize()==0;
		if(isfileNull && StringTool.isTrimmedEmptyOrNull(fileId)){
			errors.rejectValue("file", "uploadFileMissing", "upload file is missing");
		}
		if(isfileNull && !StringTool.isTrimmedEmptyOrNull(fileId) && StringTool.isTrimmedEmptyOrNull(fileName)){
			errors.rejectValue("file", "uploadFileNameMissing", "upload fileId is present but fileName is missing");
		}
		if(!isfileNull && !StringTool.isTrimmedEmptyOrNull(fileId)){
			errors.rejectValue("file", "FileAndFileId", "both file and fileId filled");
		}
		if(!isfileNull&&isFileEmpty){
		    errors.rejectValue("file", "FileEmpty", "file is empty");
		}
		if(!errors.hasErrors()){
    		try{
    		    documentUC.checkFileTypeBeforeUpload(type,fileName);
    		} catch (UnknownExtensionException e){
    		    errors.rejectValue("file", "UnknownExtension", e.getMessage());
    		} catch (DocClassNotAllowed e){
    		    errors.rejectValue("file", "OnlyAllowedDocClass"+e.getAllowedDocClass(), e.getMessage());
    		}
		}
	}

	protected abstract String getFailedURL();
	protected abstract String getSuccessURL();

}
