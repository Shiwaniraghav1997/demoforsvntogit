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
import com.bayer.bhc.doc41webui.usecase.documenttypes.AbstractDeliveryCertDocumentType;
import com.bayer.ecim.foundation.basic.StringTool;

@Controller
public class DelCertUploadController extends UploadController {
	
	@RequestMapping(value="/documents/delcertupinput",method = RequestMethod.GET)
	public BatchObjectForm getInput(@RequestParam() String type) throws Doc41BusinessException{
		BatchObjectForm form = new BatchObjectForm();
		form.setType(type);
		String vendorNumber = getLastVendorNumberFromSession();
		if(!StringTool.isTrimmedEmptyOrNull(vendorNumber)){
			form.setVendorNumber(vendorNumber);
		}
		form.initVendors();
		return form;
	}
	
	@RequestMapping(value="/documents/delcertuplist",method = RequestMethod.GET)
	public ModelAndView getInspLots(String type,BatchObjectForm batchObjectForm,BindingResult result, ModelAndView mav) throws Doc41BusinessException{
		batchObjectForm.initVendors();
		
		String vendorNumber = batchObjectForm.getVendorNumber();
		if(StringTool.isTrimmedEmptyOrNull(vendorNumber)){
			result.rejectValue("vendorNumber","VendorMissing");
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
			result.rejectValue("batch","MaterialBatchOrderMissing");
			result.rejectValue("order","MaterialBatchOrderMissing");
		}
		
		if(result.hasErrors()){
			mav.addObject(batchObjectForm);
			result.reject("PleaseEnterMandatoryFields");
			mav.setViewName("documents/delcertupinput");
			return mav;
		}
		
		List<QMBatchObject> bos = documentUC.getBatchObjectsForSupplier(vendorNumber,plant, material, batch, order);
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
					+ "&supplier="+vendorNumber
					);
		} else {
			mav.addObject("type",type);
			mav.addObject("supplier",vendorNumber);
			mav.addObject(bos);
			mav.setViewName("documents/delcertuplist");
		}
		
		
		return mav;
	}
	
	@RequestMapping(value="/documents/delcertupload",method = RequestMethod.GET)
	public ModelMap getUpload(@RequestParam() String type,QMBatchObject batchObject,String supplier) throws Doc41BusinessException{
		UploadForm uploadForm =  super.get(type);
		uploadForm.setObjectId(batchObject.getObjectId());
		uploadForm.setVendorNumber(supplier);
		Map<String, String> avalues = new HashMap<String, String>();
		avalues.put(AbstractDeliveryCertDocumentType.ATTRIB_PLANT,batchObject.getPlant());
		avalues.put(AbstractDeliveryCertDocumentType.ATTRIB_BATCH,batchObject.getBatch());
		avalues.put(AbstractDeliveryCertDocumentType.ATTRIB_MATERIAL,batchObject.getMaterialNumber());
		uploadForm.setAttributeValues(avalues);
		
		Map<String, String> viewAttributes = uploadForm.getViewAttributes();
		viewAttributes.put(AbstractDeliveryCertDocumentType.VIEW_ATTRIB_MATERIAL_TEXT,batchObject.getMaterialText());
		
		ModelMap map = new ModelMap();
		fillModelMap(uploadForm, map);
		
		
		return map;
	}

	private void fillModelMap(UploadForm uploadForm, ModelMap map) {
		map.addAttribute(uploadForm);
		
		map.addAttribute("keyCountry",AbstractDeliveryCertDocumentType.ATTRIB_COUNTRY);
		
		map.addAttribute("keyPlant",AbstractDeliveryCertDocumentType.ATTRIB_PLANT);
		map.addAttribute("keyBatch",AbstractDeliveryCertDocumentType.ATTRIB_BATCH);
		map.addAttribute("keyMaterial",AbstractDeliveryCertDocumentType.ATTRIB_MATERIAL);
		
		map.addAttribute("keyMaterialText",AbstractDeliveryCertDocumentType.VIEW_ATTRIB_MATERIAL_TEXT);
		
		List<SelectionItem> userCountries = displaytextUC.getCountrySIs(UserInSession.get().getCountries());
		map.addAttribute("userCountrySIList",userCountries);
	}
	
	@RequestMapping(value="/documents/delcertuploadpost",method = RequestMethod.POST)
	public ModelAndView postUploadMap(UploadForm uploadForm,BindingResult result) throws Doc41BusinessException { //ggf. kein modelattribute wegen sessionattribute
		ModelAndView mav = new ModelAndView();
		String view = super.postUpload(uploadForm, result);
		ModelMap modelMap = mav.getModelMap();
		fillModelMap(uploadForm, modelMap);
		mav.setViewName(view);
		return mav;
	}
	
	protected String getFailedURL() {
		return "/documents/delcertupload";
	}
	
	@Override
	protected String getSuccessURL() {
		return "delcertupinput";
	}
}
