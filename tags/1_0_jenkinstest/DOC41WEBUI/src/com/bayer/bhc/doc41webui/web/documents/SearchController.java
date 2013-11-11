package com.bayer.bhc.doc41webui.web.documents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UrlParamCrypt;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.SearchForm;
import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.AbstractDeliveryCertDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.PMSupplierDownloadDocumentType;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.StringTool;

@Controller
public class SearchController extends AbstractDoc41Controller {
	
	private static final int MAX_RESULTS = 100;

	@Autowired
	private DocumentUC documentUC;
	
	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) throws Doc41BusinessException{
		String type = request.getParameter("type");
		if(StringTool.isTrimmedEmptyOrNull(type)){
			throw new IllegalArgumentException("type is missing");
		}
		String permission = documentUC.getDownloadPermission(type);
		return usr.hasPermission(permission);
    }
	
	@RequestMapping(value="/documents/documentsearch",method = RequestMethod.GET)
	public SearchForm get(@ModelAttribute SearchForm searchForm,BindingResult result,@RequestParam(required=false) String ButtonSearch) throws Doc41BusinessException{
		String language = LocaleInSession.get().getLanguage();
		String type = searchForm.getType();
		if(StringTool.isTrimmedEmptyOrNull(type)){
			throw new Doc41BusinessException("typeIsMissing");
		}
		searchForm.initPartnerNumber(documentUC.getPartnerNumberType(type));
		List<Attribute> attributeDefinitions = documentUC.getAttributeDefinitions(type,false);
		searchForm.initAttributes(attributeDefinitions,language);
		
		if(!StringTool.isTrimmedEmptyOrNull(ButtonSearch)){
			if(searchForm.isSearchFilled()){
				checkPartnerNumber(result,type,searchForm.getPartnerNumber());
				if(!result.hasErrors()){
					String singleObjectId = searchForm.getObjectId();
					List<String> objectIds = new ArrayList<String>();
					if(!StringTool.isTrimmedEmptyOrNull(singleObjectId)){
						int objectIdFillLength = documentUC.getDocumentFillLength(type);
						if(singleObjectId.length()<objectIdFillLength){
							singleObjectId = StringTool.minLString(singleObjectId, objectIdFillLength, '0');
							searchForm.setObjectId(singleObjectId);
						}
						objectIds.add(singleObjectId);
					}
					Map<String, String> attributeValues = searchForm.getAttributeValues();
					CheckForDownloadResult checkResult = documentUC.checkForDownload(result, type, searchForm.getPartnerNumber(), objectIds, attributeValues, searchForm.getViewAttributes());
					Map<String, String> allAttributeValues = new HashMap<String, String>(attributeValues);
					Map<String, String> additionalAttributes = checkResult.getAdditionalAttributes();
					if(additionalAttributes!=null){
						allAttributeValues.putAll(additionalAttributes);
					}
					if(!result.hasErrors()){
							List<HitListEntry> documents = documentUC.searchDocuments(type, 
									objectIds, allAttributeValues, MAX_RESULTS+1, true);
						if(documents.size()>MAX_RESULTS){
							result.rejectValue("table", "ToManyResults");
						} else {
							searchForm.setDocuments(documents);
						}
					} else {
						result.reject("PleaseEnterMandatoryFields");
					}
				}
			} else {
				result.reject("NoSearchWithoutSearchParameters");
			}
		}
		
		return searchForm;
	}
	
	@RequestMapping(value="/documents/searchdelcertcountry",method = RequestMethod.GET)
	public ModelMap getDelCertCountry(@ModelAttribute SearchForm searchForm,BindingResult result,@RequestParam(required=false) String ButtonSearch) throws Doc41BusinessException{
		ModelMap map = new ModelMap();
		SearchForm searchForm2 = get(searchForm, result, ButtonSearch);
		map.addAttribute(searchForm2);
		
		map.addAttribute("keyCountry",AbstractDeliveryCertDocumentType.ATTRIB_COUNTRY);
		map.addAttribute("keyBatch",AbstractDeliveryCertDocumentType.ATTRIB_BATCH);
		map.addAttribute("keyMaterial",AbstractDeliveryCertDocumentType.ATTRIB_MATERIAL);
		
		List<SelectionItem> userCountries = displaytextUC.getCountrySIs(UserInSession.get().getCountries());
		map.addAttribute("userCountrySIList",userCountries);
		return map;
	}
	
	@RequestMapping(value="/documents/searchdelcertcustomer",method = RequestMethod.GET)
	public ModelMap getDelCertCustomer(@ModelAttribute SearchForm searchForm,BindingResult result,@RequestParam(required=false) String ButtonSearch) throws Doc41BusinessException{
		ModelMap map = new ModelMap();
		SearchForm searchForm2 = get(searchForm, result, ButtonSearch);
		map.addAttribute(searchForm2);
		
		map.addAttribute("keyCountry",AbstractDeliveryCertDocumentType.ATTRIB_COUNTRY);
		map.addAttribute("keyBatch",AbstractDeliveryCertDocumentType.ATTRIB_BATCH);
		map.addAttribute("keyMaterial",AbstractDeliveryCertDocumentType.ATTRIB_MATERIAL);
		map.addAttribute("keyDeliveryNumber",AbstractDeliveryCertDocumentType.VIEW_ATTRIB_DELIVERY_NUMBER);
		
		List<SelectionItem> allCountries = getDisplaytextUC().getCountryCodes();
		map.addAttribute("allCountryList",allCountries);
		return map;
	}
	
	@RequestMapping(value="/documents/searchpmsupplier",method = RequestMethod.GET)
	public ModelMap getPMSupplier(@ModelAttribute SearchForm searchForm,BindingResult result,@RequestParam(required=false) String ButtonSearch) throws Doc41BusinessException{
		ModelMap map = new ModelMap();
		SearchForm searchForm2 = get(searchForm, result, ButtonSearch);
		map.addAttribute(searchForm2);
		
		map.addAttribute("keyPONumber",PMSupplierDownloadDocumentType.VIEW_ATTRIB_PO_NUMBER);
		
		return map;
	}
	
	
	
	@RequestMapping(value="/documents/download",method = RequestMethod.GET)
	public void download(@RequestParam String key,HttpServletResponse response) throws Doc41BusinessException{
		Map<String, String> decryptParameters = UrlParamCrypt.decryptParameters(key);
		String type = decryptParameters.get(Doc41Constants.URL_PARAM_TYPE);
		String docId = decryptParameters.get(Doc41Constants.URL_PARAM_DOC_ID);
		String cwid = decryptParameters.get(Doc41Constants.URL_PARAM_CWID);
		String filename = StringTool.emptyToNull(StringTool.decodeURLWithDefaultFileEnc(decryptParameters.get(Doc41Constants.URL_PARAM_FILENAME)));
		if(StringTool.isTrimmedEmptyOrNull(type)){
			throw new Doc41BusinessException("type is missing in download link");
		}
		if(StringTool.isTrimmedEmptyOrNull(docId)){
			throw new Doc41BusinessException("docId is missing in download link");
		}
		if(cwid==null || !cwid.equalsIgnoreCase(UserInSession.getCwid())){
			throw new Doc41BusinessException("download link for different user");
		}
		documentUC.downloadDocument(response,type,docId,filename);
	}
	
	private void checkPartnerNumber(BindingResult errors, String type,
			String partnerNumber) throws Doc41BusinessException {
		if(documentUC.isPartnerNumberUsed(type)){
			if(StringTool.isTrimmedEmptyOrNull(partnerNumber)){
				errors.rejectValue("partnerNumber","PartnerNumberMissing");
			} else {
				if(!UserInSession.get().hasPartner(partnerNumber)){
					errors.rejectValue("partnerNumber","PartnerNotAssignedToUser");
				}
			}
		}
	}
	
}
