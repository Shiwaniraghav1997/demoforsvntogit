package com.bayer.bhc.doc41webui.web.documents;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.container.UploadForm;
import com.bayer.bhc.doc41webui.domain.Attribute;

/**
 * @author ETZAJ
 * @version 14.03.2019
 *
 *          Controller class for QMC (QMCoA, QMCoC) document type uploads.
 *
 */
@Controller
public class QMCUploadController extends UploadController {

	/**
	 * Handles GET requests for uploading QMC document types. Prepares view (upload
	 * form) for entering input parameters for QMC document type uploads.
	 * 
	 * @param type
	 *            used for determination of document type being uploaded.
	 * @return upload form for entering input parameters for QMC document type
	 *         uploads.
	 */
	@RequestMapping(value = "/documents/qmcupload", method = RequestMethod.GET)
	public UploadForm get(@RequestParam() String type) throws Doc41BusinessException {
		return super.get(type);
	}

	/**
	 * Handles POST requests for uploading QMC document types. Handles entered
	 * values of input parameters and calls specific SAP function for uploading QMC
	 * document types.
	 * 
	 * @param upload
	 *            form with entered input parameter values for QMC document type
	 *            uploads.
	 * @param binding
	 *            result used for defining result of POST request call.
	 * @return ModelAndView, if document type is successfully uploaded, or MAV with
	 *         failure URL, if document type upload had failed.
	 */
	@RequestMapping(value = "/documents/qmcuploadpost", method = RequestMethod.POST)
	public ModelAndView postUploadMAV(@ModelAttribute UploadForm uploadForm, BindingResult bindingResult)
			throws Doc41BusinessException {
		ModelAndView mav = new ModelAndView();
		String view = super.postUpload(uploadForm, bindingResult);
		List<Attribute> attributeDefinitions = documentUC.getAttributeDefinitions(uploadForm.getType(), true);
		uploadForm.initAttributes(attributeDefinitions, LocaleInSession.get().getLanguage());
		mav.setViewName(view);
		return mav;
	}

	@Override
	protected String getFailedURL() {
		return "/documents/qmcupload";
	}

	@Override
	protected String getSuccessURL() {
		return "qmcupload";
	}

}
