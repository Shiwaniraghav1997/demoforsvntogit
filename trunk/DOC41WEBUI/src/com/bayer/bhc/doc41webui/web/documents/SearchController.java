package com.bayer.bhc.doc41webui.web.documents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ClientAbortException;
import com.bayer.bhc.doc41webui.common.exception.Doc41DocServiceException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UrlParamCrypt;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.MultiDownloadForm;
import com.bayer.bhc.doc41webui.container.SearchForm;
import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.BdsServiceDocumentEntry;
import com.bayer.bhc.doc41webui.domain.BdsServiceSearchDocumentsResult;
import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm.PMSupplierDownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.qm.AbstractDeliveryCertDocumentType;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.BooleanTool;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.StringTool;

@Controller
public class SearchController extends AbstractDoc41Controller {
	
	@Autowired
	private DocumentUC documentUC;
	
	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) throws Doc41BusinessException{
		String type = request.getParameter("type");
		if(StringTool.isTrimmedEmptyOrNull(type)){
		    return true;
//			throw new IllegalArgumentException("type is missing");
		}
		return hasPermission(usr, type);
    }

    private boolean hasPermission(User usr, String type)
            throws Doc41BusinessException {
		return
		        !documentUC.getFilteredDocTypesForDownload(type, usr).isEmpty() || // 1. user has a permission on at least one type of a DownloadGroup (includes also check for single DownloadType)
		        usr.hasPermission(documentUC.getDownloadPermission(type), documentUC.getGroupDownloadPermission(type)); // 2. or user has permission on a single DocumentType (single Download alread checked on 1.)
    }
	
	@RequestMapping(value="/documents/documentsearch",method = RequestMethod.GET)
	public SearchForm get(@ModelAttribute SearchForm searchForm,BindingResult result,@RequestParam(required=false) String ButtonSearch,@RequestParam(required=false,defaultValue="true") boolean errorOnNoDocuments) throws Doc41BusinessException{
	    User usr = UserInSession.get();
		String language = LocaleInSession.get().getLanguage();
		String mFormType = searchForm.getType();
		if(StringTool.isTrimmedEmptyOrNull(mFormType)){
			throw new Doc41BusinessException("typeIsMissing");
		}
		
		List<DownloadDocumentType> mDocTypes = documentUC.getFilteredDocTypesForDownload(mFormType, usr);
		Doc41Log.get().debug(this, null, "Search for download for " + mDocTypes.size() + " document types by '" + mFormType );
		searchForm.setDocumentTypes(mDocTypes);
		List<Attribute> attributeDefinitions = new ArrayList<Attribute>();
		HashSet<String> attributeDefinitionNames = new HashSet<String>();
		int objectIdFillLength = -1;
		boolean isFirst = true;
		boolean isKgs = false;
		Properties mMaxVer = ConfigMap.get().getSubCfg("documents", "getOnlyMaxVer"); // Properties with TypeConst, e.g. AWB or GROUP, e.g. DOC_PM (see DocumentType.GROUP_*)
		boolean mOnlyMaxVer = true; // gets false, if at least one of the types not wants only MaxVer
		for (DownloadDocumentType mDocType : mDocTypes) {
		    if (isFirst) {
		        isFirst = false;
		        isKgs = mDocType.isKgs();
		    } else {
		        if (isKgs != mDocType.isKgs()) {
	                throw new Doc41BusinessException("Mixed kind of DocumentType storage technology - KGS & DIRS - not supported!");
		        }
		    }
		    mOnlyMaxVer &= BooleanTool.getBoolean(mMaxVer.getProperty(mDocType.getTypeConst()), BooleanTool.getBoolean(mMaxVer.getProperty(mDocType.getGroup()), false));
		    int objectIdFillLengthLocal = mDocType.getObjectIdFillLength();
		    if (objectIdFillLength == -1) {
		        objectIdFillLength = objectIdFillLengthLocal;
		    } else if (objectIdFillLength != objectIdFillLengthLocal) {
		        throw new Doc41BusinessException("ObjectIdFillLength ambigious for Documents of Type: " + mFormType + " -> " + objectIdFillLength + " <> " + objectIdFillLengthLocal );
		    }
		    String mType = mDocType.getTypeConst();
		    searchForm.initPartnerNumbers(documentUC.hasCustomerNumber(mType),getLastCustomerNumberFromSession(),
		            documentUC.hasVendorNumber(mType),getLastVendorNumberFromSession());
		    List<Attribute> typeAttributeDefinitions = documentUC.getAttributeDefinitions(mType,false);
		    for (Attribute attr : typeAttributeDefinitions) {
		        if (!attributeDefinitionNames.contains(attr.getName())) {
		            Doc41Log.get().debug(this, null, "--->>> ATTR-NAME: '" + attr.getName());
		            attributeDefinitionNames.add(attr.getName());
		            attributeDefinitions.add(attr);
		        }
		    }
		}
		searchForm.setKgs(isKgs);
		searchForm.initAttributes(attributeDefinitions,language);
		
		if(!StringTool.isTrimmedEmptyOrNull(ButtonSearch)){
			if(searchForm.isSearchFilled()){
				String searchFormCustomerNumber = searchForm.getCustomerNumber();
				String searchFormVendorNumber = searchForm.getVendorNumber();
				checkPartnerNumbers(result,searchForm.isCustomerNumberUsed(), searchForm.isVendorNumberUsed(),searchFormCustomerNumber,searchFormVendorNumber);
				
				if(!result.hasErrors()){
					String singleObjectId = searchForm.getObjectId();
					
					Map<String, String> attributeValues = searchForm.getAttributeValues();
					Map<String, String> viewAttributes = searchForm.getViewAttributes();
					checkForbiddenWildcards(result,"objectId",singleObjectId);
					checkForbiddenWildcards(result,"attributeValues['","']",attributeValues);
					checkForbiddenWildcards(result,"viewAttributes['","']",viewAttributes);
					if(!result.hasErrors()){
						if(!StringTool.isTrimmedEmptyOrNull(singleObjectId)){
						    // int objectIdFillLength = documentUC.getDocumentFillLength(type);
							if(singleObjectId.length()<objectIdFillLength){
								singleObjectId = StringTool.minLString(singleObjectId, objectIdFillLength, '0');
								searchForm.setObjectId(singleObjectId);
							}
						}
						
						ArrayList<String>searchingTargetTypes = new ArrayList<String>();
                        ArrayList<BeanPropertyBindingResult>results = new ArrayList<BeanPropertyBindingResult>();
                        Map<String, String> allAttributeValues = new HashMap<String, String>();
                        List<String> objectIds = new ArrayList<String>();
						for (DocumentType mDocType: mDocTypes) {
						    // We need to create separate BindingResults per DocType, then see which work fine and choose them, otherwise merge all to show all errors.
						    // hope this way is fine to create local, temporary result...
						    BeanPropertyBindingResult mTmp = new BeanPropertyBindingResult(result.getTarget(), result.getObjectName() );
						    results.add(mTmp);
						    CheckForDownloadResult checkResult = documentUC.checkForDownload(mTmp, mDocType.getTypeConst(), searchFormCustomerNumber,searchFormVendorNumber, singleObjectId, attributeValues, viewAttributes);
						    if (!mTmp.hasErrors()) {
						        searchingTargetTypes.add(mDocType.getTypeConst());
						        // FIXME: We should report the types possible to search for to the mask... (in case of GroupSearch, see DocumentType.GROUP*)
						        allAttributeValues.putAll(attributeValues);
						        Map<String, String> additionalAttributes = checkResult.getAdditionalAttributes();
						        if(additionalAttributes!=null){
						            allAttributeValues.putAll(additionalAttributes);
						        }
						        if(!StringTool.isTrimmedEmptyOrNull(singleObjectId)) {
						            objectIds.add(singleObjectId);
						        }
						        List<String> additionalObjectIds = checkResult.getAdditionalObjectIds();
						        if(additionalObjectIds!=null && !additionalObjectIds.isEmpty()) {
						            // FIXME: Will we hava a Duplicates Problem???
						            objectIds.addAll(additionalObjectIds);
						        }
						    }
						}
					    if (searchingTargetTypes.isEmpty()) {
					        boolean allHaveFieldErrors = true;
					        HashSet<ObjectError> mKnown = new HashSet<ObjectError>();
					        for (BeanPropertyBindingResult mTmp : results) {
					            allHaveFieldErrors &= mTmp.hasFieldErrors();
					            List<ObjectError> mErrors = mTmp.getAllErrors();
					            for (ObjectError mErr : mErrors) {
					                if (!mKnown.contains(mErr)) {
					                    mKnown.add(mErr);
					                    result.addError(mErr);
					                }
					            }
					            //result.addAllErrors(mTmp);
					        }
					        if(allHaveFieldErrors) { // no DocumentType has full setting of mandatory fields.
                                result.reject("PleaseEnterMandatoryFields");
                            }
					    } else {
							int maxResults = searchForm.getMaxResults();
							//ArrayList<HitListEntry> documents = new ArrayList<HitListEntry>();
							//for (String mType : searchingTargetTypes) {
							//  documents.addAll( documentUC.searchDocuments(mType, objectIds, allAttributeValues, maxResults+1, false) );
							//}
							List<HitListEntry> documents = documentUC.searchDocuments(searchingTargetTypes, objectIds, allAttributeValues, maxResults, mOnlyMaxVer);
							if(documents.isEmpty()){
							    if(errorOnNoDocuments){
							        result.reject("NoDocumentsFound");
							    }
							} else	if(documents.size()>maxResults){
								result.reject("ToManyResults");
                                result.reject("" + documents.size() + " / " + maxResults);
							} else {
								searchForm.setDocuments(documents);
							}
							// such things should be send to the webmetrix...
							Doc41Log.get().debug(this, null, "Searched Documents of Types: '" + StringTool.list(searchingTargetTypes, ", ", false) +"' (for " + mFormType + "), object_Id(s): " + StringTool.list(objectIds, ", ", false) + ", onlyMaxVers: " + mOnlyMaxVer + " finding results: " + documents.size());
					    }
					}
				}
			} else {
				result.reject("NoSearchWithoutSearchParameters");
			}
		}
		return searchForm;
	}
	
	@RequestMapping(value="/docservice/sdsearch",method = RequestMethod.GET,produces={"application/json; charset=utf-8"})
    public @ResponseBody BdsServiceSearchDocumentsResult getSDListForService( @RequestParam(required=true) String vendor,@RequestParam String refnumber) throws Doc41DocServiceException{
	    try {
            List<BdsServiceDocumentEntry> documents = new ArrayList<BdsServiceDocumentEntry>();
            
            List<String> types = documentUC.getAvailableSDDownloadDocumentTypes();
            for (String type : types) {
                
                if(hasPermission(UserInSession.get(), type)){
                    SearchForm searchForm = new SearchForm();
                    searchForm.setType(type);
                    searchForm.setVendorNumber(vendor);
                    searchForm.setObjectId(refnumber);
                    searchForm.setMaxResults(2000);
                    
                    BindingResult result = new BeanPropertyBindingResult(searchForm, "searchForm");
                    get(searchForm, result, "DocServiceSearch",false);
                    if(!result.hasErrors()){
                        List<HitListEntry> docsOneType = searchForm.getDocuments();
                        if(docsOneType!=null){
                            for (HitListEntry hitListEntry : docsOneType) {
                                BdsServiceDocumentEntry entry = new BdsServiceDocumentEntry();
                                entry.setDocId(hitListEntry.getDocId());
                                entry.setObjectId(hitListEntry.getObjectId());
                                entry.setStorageDate(hitListEntry.getStorageDate());
                                entry.setArchiveLinkDate(hitListEntry.getArchiveLinkDate());
                                entry.setObjectType(hitListEntry.getObjectType());
                                entry.setDocumentClass(hitListEntry.getDocumentClass());
                                entry.setCustomizedValuesByKey(hitListEntry.getCustomizedValuesByKey());
                                entry.setKey(hitListEntry.getKey());
                                entry.setType(hitListEntry.getType());
                                documents.add(entry);
                            }
                        }
                    } else {
                        return new BdsServiceSearchDocumentsResult(getAllErrorsAsServiceErrors(result));
                    }
                }
            }
            return new BdsServiceSearchDocumentsResult(documents);
        } catch (Exception e) {
            throw new Doc41DocServiceException("getSDListForService", e);
        }
    }
	
    private void checkForbiddenWildcards(BindingResult result, String fieldNamePrefix,
			String fieldNameSuffix, Map<String, String> attributes) {
		Set<Entry<String, String>> entrySet = attributes.entrySet();
		for (Entry<String, String> entry : entrySet) {
			String fieldName = fieldNamePrefix+entry.getKey()+fieldNameSuffix;
			checkForbiddenWildcards(result, fieldName , entry.getValue());			
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
		SearchForm searchForm2 = get(searchForm, result, ButtonSearch,true);
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
		SearchForm searchForm2 = get(searchForm, result, ButtonSearch,true);
		map.addAttribute(searchForm2);
		
		map.addAttribute("keyCountry",AbstractDeliveryCertDocumentType.ATTRIB_COUNTRY);
		map.addAttribute("keyBatch",AbstractDeliveryCertDocumentType.ATTRIB_BATCH);
		map.addAttribute("keyMaterial",AbstractDeliveryCertDocumentType.ATTRIB_MATERIAL);
		map.addAttribute("keyDeliveryNumber",AbstractDeliveryCertDocumentType.VIEW_ATTRIB_DELIVERY_NUMBER);
		
		List<SelectionItem> allCountries = getDisplaytextUC().getCountryCodes();
		map.addAttribute("allCountryList",allCountries);
		return map;
	}

	/**
	 * Map the searchDocument request, adding extra attributes for PMSupplierDownload
	 * @param searchForm
	 * @param result
	 * @param ButtonSearch
	 * @return
	 * @throws Doc41BusinessException
	 */
	@RequestMapping(value="/documents/searchpmsupplier",method = RequestMethod.GET)
	public ModelMap getPMSupplier(@ModelAttribute SearchForm searchForm,BindingResult result,@RequestParam(required=false) String ButtonSearch) throws Doc41BusinessException{
		ModelMap map = new ModelMap();
        String type = searchForm.getType();
        DocumentType docType = documentUC.getDocType(type);
		SearchForm searchForm2 = get(searchForm, result, ButtonSearch,true);
		map.addAttribute(searchForm2);

// check-PO-mode:
//		map.addAttribute("keyPONumber",PMSupplierDownloadDocumentType.VIEW_ATTRIB_PO_NUMBER);
		if (docType.isDirs()) {
		    map.addAttribute("keyFileName",PMSupplierDownloadDocumentType.VIEW_ATTRIB_FILENAME);
		}
		
		return map;
	}
	
	
	
    /**
     * Map the searchDocument request, adding extra attributes for PMSupplierDownload (global)
     * @param searchForm
     * @param result
     * @param ButtonSearch
     * @return
     * @throws Doc41BusinessException
     */
    @RequestMapping(value="/documents/searchpmsupplierGlobal",method = RequestMethod.GET)
    public ModelMap getPMSupplierGlobal(@ModelAttribute SearchForm searchForm,BindingResult result,@RequestParam(required=false) String ButtonSearch) throws Doc41BusinessException{
        ModelMap map = new ModelMap();
//      String type = searchForm.getType(); // DOC_PM
//      DocumentType docType = documentUC.getDocType(type);
        SearchForm searchForm2 = get(searchForm, result, ButtonSearch,true);
        map.addAttribute(searchForm2);

// check-PO-mode:
//      map.addAttribute("keyPONumber",PMSupplierDownloadDocumentType.VIEW_ATTRIB_PO_NUMBER);
//      if (docType.isDirs()) {
//          map.addAttribute("keyFileName",PMSupplierDownloadDocumentType.VIEW_ATTRIB_FILENAME);
//      }
        
        return map;
    }

    /**
     * Map the searchDocument request, adding extra attributes for SDSupplierDownload (global)
     * @param searchForm
     * @param result
     * @param ButtonSearch
     * @return
     * @throws Doc41BusinessException
     */
    @RequestMapping(value="/documents/searchsdsupplierGlobal",method = RequestMethod.GET)
    public ModelMap getSDSupplierGlobal(@ModelAttribute SearchForm searchForm,BindingResult result,@RequestParam(required=false) String ButtonSearch) throws Doc41BusinessException{
        ModelMap map = new ModelMap();
//      String type = searchForm.getType(); // DOC_SD
//      DocumentType docType = documentUC.getDocType(type);
        SearchForm searchForm2 = get(searchForm, result, ButtonSearch,true);
        map.addAttribute(searchForm2);

// check-PO-mode:
//      map.addAttribute("keyPONumber",PMSupplierDownloadDocumentType.VIEW_ATTRIB_PO_NUMBER);
//      if (docType.isDirs()) {
//          map.addAttribute("keyFileName",PMSupplierDownloadDocumentType.VIEW_ATTRIB_FILENAME);
//      }
        
        return map;
    }
    
    @RequestMapping(value="/documents/downloadMulti",method = RequestMethod.POST)
    public void downloadMulti(@ModelAttribute MultiDownloadForm values,HttpServletResponse response) throws Doc41BusinessException {
        boolean mDoThrowEx = true;
        StringBuffer mComments = new StringBuffer();
        ZipOutputStream mOut = null;
        String generateFileName = null;

        try {
            List<String> mSelected = values.getDocSel();
            if ((mSelected == null) || mSelected.isEmpty()) {
                mSelected = values.getDocAll();
                Doc41Log.get().debug(this, null, "No explicitly selected Items, assuming user want to download all documents: " + ((mSelected == null) ? "n/a" : "" + mSelected.size()) );
            } else {
                Doc41Log.get().debug(this, null, "User selected Items for download:" + mSelected.size() );
            }
            if (mSelected == null) {
                throw new Doc41BusinessException("no result to download");
            }
            //alternate possibility to handle duplicates, but not perfect...
            //HashMap<String, Integer> mAddedFiles = new HashMap<String, Integer>();
            for (String mParm : mSelected) {
                Doc41Log.get().debug(this, null, "Download DOC: " + mParm);
                String[] mParmArr = StringTool.split(mParm,'|');  
                String key = mParmArr[0];
                @SuppressWarnings("unused") // currently not used (on original download even completely ignored by download servlet
                String formType = mParmArr[0]; 
                Map<String, String> decryptParameters = UrlParamCrypt.decryptParameters(key);
                String type = decryptParameters.get(Doc41Constants.URL_PARAM_TYPE);
                String docId = decryptParameters.get(Doc41Constants.URL_PARAM_DOC_ID);
                String cwid = decryptParameters.get(Doc41Constants.URL_PARAM_CWID);
                String sapObjId = decryptParameters.get(Doc41Constants.URL_PARAM_SAP_OBJ_ID);
                String sapObjType = decryptParameters.get(Doc41Constants.URL_PARAM_SAP_OBJ_TYPE);
                String filename = StringTool.emptyToNull(StringTool.decodeURLWithDefaultFileEnc(decryptParameters.get(Doc41Constants.URL_PARAM_FILENAME)));
                Doc41Log.get().debug(this, null, "Download DOC FILE: " + filename);
                if (StringTool.isTrimmedEmptyOrNull(docId)) {
                    new Doc41BusinessException("docId is missing in download link");
                    mComments.append("\n\n*** FAILED (docId): " + StringTool.nvl(filename,"n/a") + " ***");
                } else if (StringTool.isTrimmedEmptyOrNull(type)) {
                    new Doc41BusinessException("type is missing in download link");
                    mComments.append("\n\n*** FAILED (type): " + StringTool.nvl(filename,"n/a") + " (" + docId + ") ***");
                } else if (cwid==null || !cwid.equalsIgnoreCase(UserInSession.getCwid())) {
                    new Doc41BusinessException("download link for different user");
                    mComments.append("\n\n*** FAILED (cwid): " + StringTool.nvl(filename,"n/a") + " (" + docId + ") ***");
                } else {
                    if (mOut == null) {
                        generateFileName = "BDS_" + cwid + "_" + sapObjId + "__" + (new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss").format(new Date(System.currentTimeMillis())) +".zip");
                        mDoThrowEx = false; // OutputStream in USE, redirection no more possible...
                        mOut = documentUC.createZipResponse(response, generateFileName);
                    }
                    // all exceptions catched internal, status added to comment, if not ok...
                    documentUC.addDownloadDocumentToZip(mOut, type, docId, filename, sapObjId, sapObjType, mComments /*, mAddedFiles*/);
                }
            }
            // if this fails, we can not report to user, only to log... (add comment to ZIP & close ZIP).
            if (mOut != null) {
                mComments.append("\n");
                documentUC.closeZipDownload(response, mOut, generateFileName, mComments.toString());
            }
        } catch (Doc41BusinessException ie) {
            if (mDoThrowEx) {
                throw ie;
            }
        } catch (Doc41ClientAbortException mCAEx) {
            Doc41Log.get().warning(this, null, "User aborted ZIP MultiDownload: " + StringTool.nvl(generateFileName, "n/a (early abort)"));
        } catch (Exception e) {
            Doc41BusinessException mEx = new Doc41BusinessException("unexpected download zip failure", e);
            if (mDoThrowEx) {
                throw mEx;
            }
        }
        Doc41Log.get().debug(this, null, "*** MULTIDOWNLOAD SUCC ***");
    }
    
    
	@RequestMapping(value={"/documents/download","/docservice/download"},method = RequestMethod.GET)
	public void download(@RequestParam String key,HttpServletResponse response) throws Doc41BusinessException{
		Map<String, String> decryptParameters = UrlParamCrypt.decryptParameters(key);
		String type = decryptParameters.get(Doc41Constants.URL_PARAM_TYPE);
		String docId = decryptParameters.get(Doc41Constants.URL_PARAM_DOC_ID);
		String cwid = decryptParameters.get(Doc41Constants.URL_PARAM_CWID);
		String sapObjId = decryptParameters.get(Doc41Constants.URL_PARAM_SAP_OBJ_ID);
		String sapObjType = decryptParameters.get(Doc41Constants.URL_PARAM_SAP_OBJ_TYPE);
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
		try {
		    String mRes = documentUC.downloadDocument(response,type,docId,filename,sapObjId,sapObjType);
	        Doc41Log.get().debug(this, null, "*** DOWNLOAD " + mRes + " ***");
		} catch (Doc41ClientAbortException e) {
            Doc41Log.get().warning(this, null, "User aborted Download, filename: " + filename + ", Doc41Id: " + docId + ", SapObjectId: " + sapObjId );
		}
		    
	}
	
	private void checkPartnerNumbers(BindingResult errors, boolean hasCustomerNumber, boolean hasVendorNumber,
			String customerNumber,String vendorNumber) throws Doc41BusinessException {
		//customer
		if(hasCustomerNumber){
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
		if(hasVendorNumber){
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
