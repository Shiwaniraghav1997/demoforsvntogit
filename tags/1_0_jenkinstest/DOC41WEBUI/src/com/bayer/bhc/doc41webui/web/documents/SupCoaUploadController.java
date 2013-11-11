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
import com.bayer.bhc.doc41webui.container.UploadForm;
import com.bayer.bhc.doc41webui.container.VendorBatchForm;
import com.bayer.bhc.doc41webui.domain.InspectionLot;
import com.bayer.bhc.doc41webui.domain.UserPartner;
import com.bayer.bhc.doc41webui.usecase.documenttypes.SupplierCOADocumentType;
import com.bayer.ecim.foundation.basic.StringTool;

@Controller
public class SupCoaUploadController extends UploadController {
	
	@RequestMapping(value="/documents/supcoaupinput",method = RequestMethod.GET)
	public VendorBatchForm getVBatch(@RequestParam() String type) throws Doc41BusinessException{
		VendorBatchForm form = new VendorBatchForm();
		form.setType(type);
		List<UserPartner> partners = UserInSession.get().getPartnersByType(documentUC.getPartnerNumberType(type));
		form.setPartners(partners);
		return form;
	}
	
	@RequestMapping(value="/documents/supcoauplist",method = RequestMethod.GET)
	public ModelAndView getInspLots(String type,VendorBatchForm vendorBatchForm,BindingResult result, ModelAndView mav) throws Doc41BusinessException{
		
		String partnerNumber = vendorBatchForm.getPartnerNumber();
		if(StringTool.isTrimmedEmptyOrNull(partnerNumber)){
			result.rejectValue("partnerNumber","VendorMissing");
		}
		String vendorBatch = vendorBatchForm.getVendorBatch();
		if(StringTool.isTrimmedEmptyOrNull(vendorBatch)){
			result.rejectValue("vendorBatch","VendorBatchMissing");
		}
		String plant = vendorBatchForm.getPlant();
		if(StringTool.isTrimmedEmptyOrNull(plant)){
			result.rejectValue("plant","PlantMissing");
		}
		if(result.hasErrors()){
			List<UserPartner> partners = UserInSession.get().getPartnersByType(documentUC.getPartnerNumberType(type));
			vendorBatchForm.setPartners(partners);
			mav.addObject(vendorBatchForm);
			result.reject("PleaseEnterMandatoryFields");
			mav.setViewName("documents/supcoaupinput");
			return mav;
		}
		
		List<InspectionLot> ilots = documentUC.getInspectionLotsForVendorBatch(partnerNumber,
				vendorBatch, plant);
		if(ilots.isEmpty()){
			mav.addObject(vendorBatchForm);
			result.reject("NoInspectionLotFound");
			mav.setViewName("documents/supcoaupinput");
		} else if(ilots.size()==1){
			InspectionLot ilot = ilots.get(0);
			mav.setViewName("redirect:/documents/supcoaupload?type="+type+"&number="+ilot.getNumber()
					+ "&materialNumber="+ilot.getMaterialNumber()
					+ "&materialText="+ilot.getMaterialText()
					+ "&plant="+ilot.getPlant()
					+ "&batch="+ilot.getBatch()
					+ "&vendor="+ilot.getVendor()
					+ "&vendorBatch="+ilot.getVendorBatch()
					);
		} else {
			mav.addObject("type",type);
			mav.addObject(ilots);
			mav.setViewName("documents/supcoauplist");
		}
		
		
		return mav;
	}
	
	@RequestMapping(value="/documents/supcoaupload",method = RequestMethod.GET)
	public ModelMap getUpload(@RequestParam() String type,InspectionLot inspectionLot) throws Doc41BusinessException{
		ModelMap map = new ModelMap();
		UploadForm uploadForm =  super.get(type);
		uploadForm.setObjectId(inspectionLot.getNumber());
		uploadForm.setPartnerNumber(inspectionLot.getVendor());
		Map<String, String> avalues = new HashMap<String, String>();
		avalues.put(SupplierCOADocumentType.VIEW_ATTRIB_VENDOR_BATCH,inspectionLot.getVendorBatch());
		avalues.put(SupplierCOADocumentType.VIEW_ATTRIB_PLANT,inspectionLot.getPlant());
		avalues.put(SupplierCOADocumentType.VIEW_ATTRIB_BATCH,inspectionLot.getBatch());
		avalues.put(SupplierCOADocumentType.VIEW_ATTRIB_MATERIAL,inspectionLot.getMaterialNumber());
		uploadForm.setAttributeValues(avalues);
		map.addAttribute(uploadForm);
		map.addAttribute("materialText",inspectionLot.getMaterialText());
		
		map.addAttribute("keyVendorBatch",SupplierCOADocumentType.VIEW_ATTRIB_VENDOR_BATCH);
		map.addAttribute("keyPlant",SupplierCOADocumentType.VIEW_ATTRIB_PLANT);
		map.addAttribute("keyBatch",SupplierCOADocumentType.VIEW_ATTRIB_BATCH);
		map.addAttribute("keyMaterial",SupplierCOADocumentType.VIEW_ATTRIB_MATERIAL);
		
		return map;
	}
	
	@RequestMapping(value="/documents/supcoauploadpost",method = RequestMethod.POST)
	public String postUpload(UploadForm uploadForm,BindingResult result) throws Doc41BusinessException { //ggf. kein modelattribute wegen sessionattribute
		return super.postUpload(uploadForm, result);
	}
	
	protected String getFailedURL() {
		return "/documents/supcoaupload";
	}
	
	@Override
	protected String getSuccessURL() {
		return "supcoaupload";
	}
}
