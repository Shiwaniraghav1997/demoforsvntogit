package com.bayer.bhc.doc41webui.web.documents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm.PMSupplierDownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.qm.AbstractDeliveryCertDocumentType;
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
		searchForm.initPartnerNumbers(documentUC.hasCustomerNumber(type),getLastCustomerNumberFromSession(),
				documentUC.hasVendorNumber(type),getLastVendorNumberFromSession());
		List<Attribute> attributeDefinitions = documentUC.getAttributeDefinitions(type,false);
		searchForm.initAttributes(attributeDefinitions,language);
		
		if(!StringTool.isTrimmedEmptyOrNull(ButtonSearch)){
			if(searchForm.isSearchFilled()){
				String searchFormCustomerNumber = searchForm.getCustomerNumber();
				String searchFormVendorNumber = searchForm.getVendorNumber();
				checkPartnerNumbers(result,type,searchFormCustomerNumber,searchFormVendorNumber);
				
				if(!result.hasErrors()){
					String singleObjectId = searchForm.getObjectId();
					
					Map<String, String> attributeValues = searchForm.getAttributeValues();
					Map<String, String> viewAttributes = searchForm.getViewAttributes();
					checkForbiddenWildcards(result,"objectId",singleObjectId);
					checkForbiddenWildcards(result,"attributeValues['","']",attributeValues);
					checkForbiddenWildcards(result,"viewAttributes['","']",viewAttributes);
					if(!result.hasErrors()){
						if(!StringTool.isTrimmedEmptyOrNull(singleObjectId)){
							int objectIdFillLength = documentUC.getDocumentFillLength(type);
							if(singleObjectId.length()<objectIdFillLength){
								singleObjectId = StringTool.minLString(singleObjectId, objectIdFillLength, '0');
								searchForm.setObjectId(singleObjectId);
							}
						}
						
						CheckForDownloadResult checkResult = documentUC.checkForDownload(result, type, searchFormCustomerNumber,searchFormVendorNumber, singleObjectId, attributeValues, viewAttributes);
						Map<String, String> allAttributeValues = new HashMap<String, String>(attributeValues);
						Map<String, String> additionalAttributes = checkResult.getAdditionalAttributes();
						if(additionalAttributes!=null){
							allAttributeValues.putAll(additionalAttributes);
						}
						List<String> objectIds = new ArrayList<String>();
						if(!StringTool.isTrimmedEmptyOrNull(singleObjectId)){
							objectIds.add(singleObjectId);
						}
						List<String> additionalObjectIds = checkResult.getAdditionalObjectIds();
						if(additionalObjectIds!=null && !additionalObjectIds.isEmpty()){
							objectIds.addAll(additionalObjectIds);
						}
						if(!result.hasErrors()){
							List<HitListEntry> documents = documentUC.searchDocuments(type, 
									objectIds, allAttributeValues, MAX_RESULTS+1, false);
							if(documents.isEmpty()){
								result.reject("NoDocumentsFound");
							} else	if(documents.size()>MAX_RESULTS){
								result.reject("ToManyResults");
							} else {
								searchForm.setDocuments(documents);
							}
						} else {
							if(result.hasFieldErrors()){
								result.reject("PleaseEnterMandatoryFields");
							}
						}
					}
				}
			} else {
				result.reject("NoSearchWithoutSearchParameters");
			}
		}
		
		return searchForm;
	}
	
	private void checkForbiddenWildcards(BindingResult result, String fieldNamePrefix,
			String fieldNameSuffix, Map<String, String> attributes) {
		Set<String> keySet = attributes.keySet();
		for (String key : keySet) {
			String value = attributes.get(key);
			String fieldName = fieldNamePrefix+key+fieldNameSuffix;
			checkForbiddenWildcards(result, fieldName , value);			
		}
	}

	private void checkForbiddenWildcards(BindingResult result, String fieldName,
			String value) {
		if(value!=null && (value.indexOf('*')>=0 || value.indexOf('+')>=0)){
			result.rejectValue(fieldName,"NoWildCardsAllowed");
		}
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
	
	private void checkPartnerNumbers(BindingResult errors, String type,
			String customerNumber,String vendorNumber) throws Doc41BusinessException {
		//customer
		if(documentUC.hasCustomerNumber(type)){
			if(StringTool.isTrimmedEmptyOrNull(customerNumber)){
				errors.rejectValue("customerNumber","CustomerNumberMissing");
			} else {
				if(!UserInSession.get().hasCustomer(customerNumber)){
					errors.rejectValue("customerNumber","CustomerNotAssignedToUser");
				} else {
					setLastCustomerNumberFromSession(customerNumber);
				}
			}
		}
		
		//vendor
		if(documentUC.hasVendorNumber(type)){
			if(StringTool.isTrimmedEmptyOrNull(vendorNumber)){
				errors.rejectValue("vendorNumber","VendorNumberMissing");
			} else {
				if(!UserInSession.get().hasVendor(vendorNumber)){
					errors.rejectValue("vendorNumber","VendorNotAssignedToUser");
				} else {
					setLastVendorNumberFromSession(vendorNumber);
				}
			}
		}
	}
	
}
