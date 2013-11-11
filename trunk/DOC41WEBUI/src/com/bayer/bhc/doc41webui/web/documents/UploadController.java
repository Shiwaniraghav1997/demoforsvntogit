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
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.UploadForm;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.integration.db.TranslationsDAO;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForUpdateResult;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.business.sbeanaccess.BATranslationsException;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

public abstract class UploadController extends AbstractDoc41Controller {
	
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
		uploadForm.initPartnerNumber(documentUC.getPartnerNumberType(type));
//		uploadForm.setTypeLabel(documentUC.getTypeLabel(type, language));
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
		uploadForm.initPartnerNumber(documentUC.getPartnerNumberType(type));
		checkPartnerNumber(result,type,uploadForm.getPartnerNumber());
		checkAndFillObjectId(result,type,uploadForm);
		checkFileParameter(result,uploadForm.getFile(),uploadForm.getFileId(),uploadForm.getFileName());
		if(result.hasErrors()){
			return failedURL;
		}
		Map<String, String> attributeValues = uploadForm.getAttributeValues();
		CheckForUpdateResult checkResult = documentUC.checkForUpload(result, type, 
				uploadForm.getPartnerNumber(), uploadForm.getObjectId(), attributeValues,
				uploadForm.getViewAttributes());
		if(result.hasErrors()){
			return failedURL;
		}
		Map<String, String> allAttributeValues = new HashMap<String, String>(attributeValues);
		Map<String, String> additionalAttributes = checkResult.getAdditionalAttributes();
		if(additionalAttributes!=null){
			allAttributeValues.putAll(additionalAttributes);
		}
		
		MultipartFile file = uploadForm.getFile();
		if(StringTool.isTrimmedEmptyOrNull(uploadForm.getFileId())){
			File localFile = documentUC.checkForVirus(file);
			if(localFile==null){
				result.reject("VirusDetected");
				return failedURL;
			}
			String fileName = file.getOriginalFilename();
			String fileId = documentUC.uploadDocument(type,localFile,file.getContentType(),fileName);
			uploadForm.setFileId(fileId);
			uploadForm.setFileName(fileName);
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

	private String getTypeName(String type,boolean userLocale)  {
		try {
			Locale locale;
			if(userLocale){
				locale = LocaleInSession.get();
			} else {
				locale = Locale.ENGLISH;
			}
			Tags tags = new Tags(TranslationsDAO.SYSTEM_ID, "documents", "*", locale);
			String typeName=tags.getTag(type);
			return typeName;
		} catch (BATranslationsException e) {
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

	private void checkPartnerNumber(BindingResult errors, String type,
			String partnerNumber) throws Doc41BusinessException {
		if(documentUC.isPartnerNumberUsed(type)){
			if(StringTool.isTrimmedEmptyOrNull(partnerNumber)){
				errors.rejectValue("partnerNumber","PartnerNumberMissing");
			} else {
				if(!UserInSession.get().hasPartner(partnerNumber)){
					errors.rejectValue("partnerNumber","PartnerNotAssignedToUser");
				}
			}
		}
	}

	private void checkFileParameter(BindingResult errors, MultipartFile file,
			String fileId, String fileName) {
		boolean isfileEmpty = (file==null||file.getSize()==0);
		if(isfileEmpty && StringTool.isTrimmedEmptyOrNull(fileId)){
			errors.rejectValue("file", "uploadFileMissing", "upload file is missing");
		}
		if(isfileEmpty && !StringTool.isTrimmedEmptyOrNull(fileId) && StringTool.isTrimmedEmptyOrNull(fileName)){
			errors.rejectValue("file", "uploadFileNameMissing", "upload fileId is present but fileName is missing");
		}
		if(!isfileEmpty && !StringTool.isTrimmedEmptyOrNull(fileId)){
			errors.rejectValue("file", "FileAndFileId", "both file and fileId filled");
		}
	}

	protected abstract String getFailedURL();
	protected abstract String getSuccessURL();

}
