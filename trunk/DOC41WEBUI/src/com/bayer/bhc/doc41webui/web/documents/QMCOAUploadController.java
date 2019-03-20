package com.bayer.bhc.doc41webui.web.documents;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.container.UploadForm;

/**
 * @author ETZAJ
 * @version 14.03.2019
 *
 *          Controller class for QMCoA document type uploads.
 *
 */
@Controller
public class QMCOAUploadController extends UploadController {

	/**
	 * Handles GET requests for uploading QMCoA document types. Prepares view
	 * (upload form) for entering input parameters for QMCoA document type uploads.
	 * 
	 * @param type
	 *            used for determination of document type being uploaded.
	 * @return upload form for entering input parameters for QMCoA document type
	 *         uploads.
	 */
	@RequestMapping(value = "/documents/qmcoaupload", method = RequestMethod.GET)
	public UploadForm get(@RequestParam() String type) throws Doc41BusinessException {
		return super.get(type);
	}

	/**
	 * Handles POST requests for uploading QMCoA document types. Handles entered
	 * values of input parameters and calls specific SAP function for uploading
	 * QMCoA document types.
	 * 
	 * @param upload
	 *            form with entered input parameter values for QMCoA document type
	 *            uploads.
	 * @param binding
	 *            result used for defining result of POST request call.
	 * @return success URL, if document type is successfully uploaded, or failure
	 *         URL, if document type upload had failed.
	 */
	@RequestMapping(value = "/documents/qmcoauploadpost", method = RequestMethod.POST)
	public String postUpload(@ModelAttribute UploadForm uploadForm, BindingResult bindingResult)
			throws Doc41BusinessException {
		return super.postUpload(uploadForm, bindingResult);
	}

	@Override
	protected String getFailedURL() {
		return "/documents/qmcoaupload";
	}

	@Override
	protected String getSuccessURL() {
		return "qmcoaupload";
	}

}
