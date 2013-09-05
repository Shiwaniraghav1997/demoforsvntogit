package com.bayer.bhc.doc41webui.web.documents;

import java.io.File;
import java.util.List;

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
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.StringTool;

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
		uploadForm.setPartnerNumberUsed(documentUC.isPartnerNumberUsed(type));
//		uploadForm.setTypeLabel(documentUC.getTypeLabel(type, language));
		List<Attribute> attributeDefinitions = documentUC.getAttributeDefinitions(type);
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
		uploadForm.setPartnerNumberUsed(documentUC.isPartnerNumberUsed(type));
		checkPartnerNumber(result,type,uploadForm.getPartnerNumber());
		checkObjectId(result,type,uploadForm.getObjectId());
		checkFileParameter(result,uploadForm.getFile(),uploadForm.getFileId(),uploadForm.getFileName());
		documentUC.checkForUpload(result, type, 
				uploadForm.getPartnerNumber(), uploadForm.getObjectId(), uploadForm.getAttributeValues(),
				uploadForm.getViewAttributes());
		if(result.hasErrors()){
			return failedURL;
		}
		
		MultipartFile file = uploadForm.getFile();
		if(StringTool.isTrimmedEmptyOrNull(uploadForm.getFileId())){
			File localFile = documentUC.checkForVirus(file);
			if(localFile==null){
				result.reject("VirusDetected");
				return failedURL;
			}
			String fileId = documentUC.uploadDocument(type,localFile,file.getContentType());
			uploadForm.setFileId(fileId);
			uploadForm.setFileName(file.getOriginalFilename());
		}
		if(StringTool.isTrimmedEmptyOrNull(uploadForm.getFileId())){
			result.reject("UploadFailed");
			return failedURL;
		} 
		//set attributes in sap
		documentUC.setAttributesForNewDocument(type,uploadForm.getFileId(),uploadForm.getAttributeValues(),uploadForm.getObjectId(),uploadForm.getFileName());
		
		
		return "redirect:"+getSuccessURL()+"?type="+type;
	}

	private void checkObjectId(BindingResult errors, String type,
			String objectId) {
		if(StringTool.isTrimmedEmptyOrNull(objectId)){
			errors.rejectValue("objectId","ObjectId"+type+"Missing");
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
