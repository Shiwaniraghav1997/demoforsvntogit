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

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.UploadForm;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.domain.UserPartner;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.StringTool;

@Controller
public class UploadController extends AbstractDoc41Controller {
	
	@Autowired
	private DocumentUC documentUC;
	
	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) {
		String type = request.getParameter("type");
		if(StringTool.isTrimmedEmptyOrNull(type)){
			return false;
		} else if(type.equals(Doc41Constants.DOC_TYPE_BOL)){
			return usr.hasPermission(Doc41Constants.PERMISSION_DOC_BOL_UP);
		} else if(type.equals(Doc41Constants.DOC_TYPE_AIRWAY)){
			return usr.hasPermission(Doc41Constants.PERMISSION_DOC_AWB_UP);
		} else {
			throw new IllegalArgumentException("unknown type: "+type);
		}
    }

	@RequestMapping(value="/documents/documentupload",method = RequestMethod.GET)
	public UploadForm get(@RequestParam() String type,@RequestParam(required=false) String fileid) throws Doc41BusinessException{
		String language = LocaleInSession.get().getLanguage();
		UploadForm uploadForm = new UploadForm();
		uploadForm.setType(type);
		uploadForm.setFileId(fileid);
//		uploadForm.setTypeLabel(documentUC.getTypeLabel(type, language));
		List<Attribute> attributeDefinitions = documentUC.getAttributeDefinitions(type);
		uploadForm.initAttributes(attributeDefinitions,language);
		return uploadForm;
	}
	
	@RequestMapping(value="/documents/upload",method = RequestMethod.POST)
	public String postUpload(@ModelAttribute UploadForm uploadForm,BindingResult result) { //ggf. kein modelattribute wegen sessionattribute
		String failedURL = "/documents/documentupload";
		uploadForm.validate(result);
		if(result.hasErrors()){
			return failedURL;
		}
		String error=postUpdateInternal(uploadForm);
		if(StringTool.isTrimmedEmptyOrNull(error)){
			return "redirect:/documents/documentupload"+"?type="+uploadForm.getType();
		} else {
			result.reject(error);
			return "/documents/documentupload";
		}
	}

	private String postUpdateInternal(UploadForm uploadForm) {
		try{
			if(!checkParterFromFormWithUser(uploadForm.getPartnerNumber())){
				return "PartnerNotAssignedToUser";
			}
			if(!documentUC.checkDeliveryForPartner(uploadForm.getPartnerNumber(), uploadForm.getDeliveryNumber(), uploadForm.getShippingUnitNumber())){
				return "DeliveryNotAllowedForCarrier";
			} 
			MultipartFile file = uploadForm.getFile();
			if(StringTool.isTrimmedEmptyOrNull(uploadForm.getFileId())){
				File localFile = documentUC.checkForVirus(file);
				if(localFile==null){
					return "VirusDetected";
				}
				String fileId = documentUC.uploadDocument(uploadForm.getType(),localFile,file.getContentType());
				uploadForm.setFileId(fileId);
			}
			if(StringTool.isTrimmedEmptyOrNull(uploadForm.getFileId())){
				return "UploadFailed";
			} 
			//set attributes in sap
			documentUC.setAttributesForNewDocument(uploadForm.getType(),uploadForm.getFileId(),uploadForm.getAttributeValues(),uploadForm.getDeliveryNumber());
		} catch (Doc41BusinessException e) {
			return e.getMessage();
		}
		return null;
	}

	private boolean checkParterFromFormWithUser(String partnerNumber) {
		List<UserPartner> partners = UserInSession.get().getPartners();
		if(partners!=null){
			for (UserPartner userPartner : partners) {
				if(StringTool.equals(partnerNumber, userPartner.getPartnerNumber())){
					return true;
				}
			}
		}
		return false;
	}
	
}
