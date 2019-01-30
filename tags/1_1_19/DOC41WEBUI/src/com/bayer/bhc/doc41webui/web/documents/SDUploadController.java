package com.bayer.bhc.doc41webui.web.documents;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41DocServiceException;
import com.bayer.bhc.doc41webui.container.UploadForm;
import com.bayer.bhc.doc41webui.domain.BdsServiceUploadDocumentResult;

@Controller
public class SDUploadController extends UploadController {

	
	@RequestMapping(value="/documents/sdupload",method = RequestMethod.GET)
	public UploadForm get(@RequestParam() String type) throws Doc41BusinessException{
		return super.get(type);
	}
	
	@RequestMapping(value="/documents/sduploadpost",method = RequestMethod.POST)
	public String postUpload(@ModelAttribute UploadForm uploadForm,BindingResult result) throws Doc41BusinessException { //ggf. kein modelattribute wegen sessionattribute
	    return super.postUpload(uploadForm, result);
	}
	
	/**
	 * Upload Document Webservice, used for Spepor (Spepor entry point).
	 * @param uploadForm
	 * @param result
	 * @return
	 * @throws Doc41DocServiceException
	 */
	@RequestMapping(value="/docservice/sdupload",method = RequestMethod.POST,produces={"application/json; charset=utf-8"})
    public @ResponseBody BdsServiceUploadDocumentResult postUploadService(@ModelAttribute UploadForm uploadForm,BindingResult result) throws Doc41DocServiceException { //ggf. kein modelattribute wegen sessionattribute
        try {
            /*String postUpload =*/ super.postUpload(uploadForm, result);
            if(result.hasErrors()){
                return new BdsServiceUploadDocumentResult(getAllErrorsAsServiceErrors(result));
            }
            return new BdsServiceUploadDocumentResult();
        } catch (Doc41BusinessException e) {
// FIXME: Webservice???
            throw new Doc41DocServiceException("postUploadService", e);
        }
    }
	
	protected String getFailedURL() {
		return "/documents/sdupload";
	}

	@Override
	protected String getSuccessURL() {
		return "sdupload";
	}
}
