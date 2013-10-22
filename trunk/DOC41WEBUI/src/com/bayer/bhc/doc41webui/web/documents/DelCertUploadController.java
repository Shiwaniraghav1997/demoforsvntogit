package com.bayer.bhc.doc41webui.web.documents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.BatchObjectForm;
import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.bhc.doc41webui.container.UploadForm;
import com.bayer.bhc.doc41webui.domain.QMBatchObject;
import com.bayer.bhc.doc41webui.domain.UserPartner;
import com.bayer.bhc.doc41webui.usecase.documenttypes.AbstractDeliveryCertDocumentType;
import com.bayer.ecim.foundation.basic.StringTool;

@Controller
public class DelCertUploadController extends UploadController {
	
	@RequestMapping(value="/documents/delcertupinput",method = RequestMethod.GET)
	public BatchObjectForm getInput(@RequestParam() String type) throws Doc41BusinessException{
		BatchObjectForm form = new BatchObjectForm();
		form.setType(type);
		List<UserPartner> partners = UserInSession.get().getPartnersByType(documentUC.getPartnerNumberType(type));
		form.setPartners(partners);
		return form;
	}
	
	@RequestMapping(value="/documents/delcertuplist",method = RequestMethod.GET)
	public ModelAndView getInspLots(String type,BatchObjectForm batchObjectForm,BindingResult result, ModelAndView mav) throws Doc41BusinessException{
		
		String partnerNumber = batchObjectForm.getPartnerNumber();
		if(StringTool.isTrimmedEmptyOrNull(partnerNumber)){
			result.rejectValue("partnerNumber","VendorMissing");
		}
		String plant = batchObjectForm.getPlant();
		if(StringTool.isTrimmedEmptyOrNull(plant)){
			result.rejectValue("plant","PlantMissing");
		}
		
		String material = batchObjectForm.getMaterial();
		String batch = batchObjectForm.getBatch();
		String order = batchObjectForm.getOrder();
		if(StringTool.isTrimmedEmptyOrNull(material) && StringTool.isTrimmedEmptyOrNull(batch) && StringTool.isTrimmedEmptyOrNull(order)){
			result.rejectValue("material","MaterialBatchOrderMissing");
		}
		
		if(result.hasErrors()){
			List<UserPartner> partners = UserInSession.get().getPartnersByType(documentUC.getPartnerNumberType(type));
			batchObjectForm.setPartners(partners);
			mav.addObject(batchObjectForm);
			result.reject("PleaseEnterMandatoryFields");
			mav.setViewName("documents/delcertupinput");
			return mav;
		}
		
		List<QMBatchObject> bos = documentUC.getBatchObjectsForSupplier(partnerNumber,plant, material, batch, order);
		if(bos.isEmpty()){
			mav.addObject(batchObjectForm);
			result.reject("NoBatchObjectFound");
			mav.setViewName("documents/delcertupinput");
		} else if(bos.size()==1){
			QMBatchObject bo = bos.get(0);
			mav.setViewName("redirect:/documents/delcertupload?type="+type+"&objectId="+bo.getObjectId()
					+ "&materialNumber="+bo.getMaterialNumber()
					+ "&materialText="+bo.getMaterialText()
					+ "&plant="+bo.getPlant()
					+ "&batch="+bo.getBatch()
					+ "&supplier="+partnerNumber
					);
		} else {
			mav.addObject("type",type);
			mav.addObject("supplier",partnerNumber);
			mav.addObject(bos);
			mav.setViewName("documents/delcertuplist");
		}
		
		
		return mav;
	}
	
	@RequestMapping(value="/documents/delcertupload",method = RequestMethod.GET)
	public ModelMap getUpload(@RequestParam() String type,QMBatchObject batchObject,String supplier) throws Doc41BusinessException{
		ModelMap map = new ModelMap();
		UploadForm uploadForm =  super.get(type);
		uploadForm.setObjectId(batchObject.getObjectId());
		uploadForm.setPartnerNumber(supplier);
		Map<String, String> avalues = new HashMap<String, String>();
		avalues.put(AbstractDeliveryCertDocumentType.ATTRIB_PLANT,batchObject.getPlant());
		avalues.put(AbstractDeliveryCertDocumentType.ATTRIB_BATCH,batchObject.getBatch());
		avalues.put(AbstractDeliveryCertDocumentType.ATTRIB_MATERIAL,batchObject.getMaterialNumber());
		uploadForm.setAttributeValues(avalues);
		map.addAttribute(uploadForm);
		map.addAttribute("materialText",batchObject.getMaterialText());
		
		map.addAttribute("keyCountry",AbstractDeliveryCertDocumentType.ATTRIB_COUNTRY);
		
		map.addAttribute("keyPlant",AbstractDeliveryCertDocumentType.ATTRIB_PLANT);
		map.addAttribute("keyBatch",AbstractDeliveryCertDocumentType.ATTRIB_BATCH);
		map.addAttribute("keyMaterial",AbstractDeliveryCertDocumentType.ATTRIB_MATERIAL);
		
		List<SelectionItem> userCountries = displaytextUC.getCountrySIs(UserInSession.get().getCountries());
		map.addAttribute("userCountrySIList",userCountries);
		
		
		return map;
	}
	
	@RequestMapping(value="/documents/delcertuploadpost",method = RequestMethod.POST)
	public String postUpload(UploadForm uploadForm,BindingResult result) throws Doc41BusinessException { //ggf. kein modelattribute wegen sessionattribute
		return super.postUpload(uploadForm, result);
	}
	
	protected String getFailedURL() {
		return "/documents/delcertupload";
	}
	
	@Override
	protected String getSuccessURL() {
		return "delcertupload";
	}
}
