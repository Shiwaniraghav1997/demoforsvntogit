package com.bayer.bhc.doc41webui.web.documents;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.container.UploadForm;

@Controller
public class DelCertUploadController extends UploadController {

	
	@RequestMapping(value="/documents/delcertupload",method = RequestMethod.GET)
	public UploadForm get(@RequestParam() String type) throws Doc41BusinessException{
		return super.get(type);
	}
	
	@RequestMapping(value="/documents/delcertuploadpost",method = RequestMethod.POST)
	public String postUpload(@ModelAttribute UploadForm uploadForm,BindingResult result) throws Doc41BusinessException { //ggf. kein modelattribute wegen sessionattribute
		return super.postUpload(uploadForm, result);
	}
	
	protected String getFailedURL() {
		return "/documents/delcertupload";
	}

	@Override
	protected String getSuccessURL() {
		return "/documents/delcertupload";
	}
}
