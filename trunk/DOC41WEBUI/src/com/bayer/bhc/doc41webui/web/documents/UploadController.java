package com.bayer.bhc.doc41webui.web.documents;

import java.util.List;

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
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.Attribute;
import com.bayer.bhc.doc41webui.container.UploadForm;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class UploadController extends AbstractDoc41Controller {
	
	@Autowired
	private DocumentUC documentUC;
	
	protected boolean hasRolePermission(User usr) {
    	return usr.isCarrier();
    }

	@RequestMapping(value="/documents/documentupload",method = RequestMethod.GET)
	public UploadForm get(@RequestParam() String type,@RequestParam(required=false) String fileid) throws Doc41BusinessException{
		UploadForm uploadForm = new UploadForm();
		uploadForm.setType(type);
		uploadForm.setFileId(fileid);
		List<Attribute> attributeDefinitions = documentUC.getAttributeDefinitions(type);
		uploadForm.initAttributes(attributeDefinitions,LocaleInSession.get().getLanguage());
		return uploadForm;
	}
	
	@RequestMapping(value="/documents/upload",method = RequestMethod.POST)
	public String postUpload(@ModelAttribute UploadForm uploadForm,BindingResult result,@RequestParam(value="file",required=false) MultipartFile file){ //ggf. kein modelattribute wegen sessionattribute
		if((file==null||file.getSize()==0) && uploadForm.getFileId()==null){
			result.reject("uploadFileMissing");
		} else if(documentUC.checkDeliveryForPartner(UserInSession.get().getCompany(), uploadForm.getDeliveryNumber(), uploadForm.getShippingUnitNumber())){
			//TODO use Carrier field instead of Company
			result.reject("DeliveryNotAllowedForCarrier");
		}
		if (result.hasErrors()) {
            return "/documents/documentupload";
        }
		
		//TODO 
		
		return "redirect:/documents/documentupload";
	}
	
}
