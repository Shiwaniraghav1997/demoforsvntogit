package com.bayer.bhc.doc41webui.web.documents;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.container.UploadForm;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.StringTool;

@Controller
public class UploadController extends AbstractDoc41Controller {
	
	@Autowired
	private DocumentUC documentUC;
	
	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) throws Doc41BusinessException{
		String type = request.getParameter("type");
		if(StringTool.isTrimmedEmptyOrNull(type)){
			throw new IllegalArgumentException("type is missing");
		}
		String permission = documentUC.getUploadPermission(type);
		return usr.hasPermission(permission);
    }

	@RequestMapping(value="/documents/documentupload",method = RequestMethod.GET)
	public UploadForm get(@RequestParam() String type,@RequestParam(required=false) String fileid) throws Doc41BusinessException{
		String language = LocaleInSession.get().getLanguage();
		UploadForm uploadForm = new UploadForm();
		uploadForm.setType(type);
		uploadForm.setFileId(fileid);
		uploadForm.setPartnerNumberUsed(documentUC.isPartnerNumberUsed(type));
//		uploadForm.setTypeLabel(documentUC.getTypeLabel(type, language));
		List<Attribute> attributeDefinitions = documentUC.getAttributeDefinitions(type);
		uploadForm.initAttributes(attributeDefinitions,language);
		return uploadForm;
	}
	
	@RequestMapping(value="/documents/upload",method = RequestMethod.POST)
	public String postUpload(@ModelAttribute UploadForm uploadForm,BindingResult result) throws Doc41BusinessException { //ggf. kein modelattribute wegen sessionattribute
		String failedURL = "/documents/documentupload";
		uploadForm.setPartnerNumberUsed(documentUC.isPartnerNumberUsed(uploadForm.getType()));
		documentUC.checkForUpload(result, uploadForm.getType(), uploadForm.getFile(), uploadForm.getFileId(),
				uploadForm.getPartnerNumber(), uploadForm.getObjectId(), uploadForm.getAttributeValues());
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
			String fileId = documentUC.uploadDocument(uploadForm.getType(),localFile,file.getContentType());
			uploadForm.setFileId(fileId);
		}
		if(StringTool.isTrimmedEmptyOrNull(uploadForm.getFileId())){
			result.reject("UploadFailed");
			return failedURL;
		} 
		//set attributes in sap
		documentUC.setAttributesForNewDocument(uploadForm.getType(),uploadForm.getFileId(),uploadForm.getAttributeValues(),uploadForm.getObjectId());
		
		
		return "redirect:/documents/documentupload"+"?type="+uploadForm.getType();
	}


}
