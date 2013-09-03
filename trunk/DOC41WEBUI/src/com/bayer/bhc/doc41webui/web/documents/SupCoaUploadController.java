package com.bayer.bhc.doc41webui.web.documents;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.container.SupCoaUploadForm;
import com.bayer.bhc.doc41webui.container.UploadForm;

@Controller
public class SupCoaUploadController extends UploadController {

	
	@RequestMapping(value="/documents/supcoaupload",method = RequestMethod.GET)
	public @ModelAttribute("uploadForm") SupCoaUploadForm get(@RequestParam() String type,@RequestParam(required=false) String fileid) throws Doc41BusinessException{
		return (SupCoaUploadForm) super.get(type, fileid);
	}
	
	@RequestMapping(value="/documents/supcoauploadpost",method = RequestMethod.POST)
	public String postUpload(@ModelAttribute("uploadForm") SupCoaUploadForm uploadForm,BindingResult result) throws Doc41BusinessException { //ggf. kein modelattribute wegen sessionattribute
		return super.postUpload(uploadForm, result);
	}
	
	protected String getFailedURL() {
		return "/documents/supcoaupload";
	}
	
	@Override
	protected UploadForm createNewForm() {
		return new SupCoaUploadForm();
	}
}
