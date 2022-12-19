package com.bayer.bhc.doc41webui.web.documents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
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
import com.bayer.bhc.doc41webui.integration.db.TranslationsDAO;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm.PMSupplierDownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.qm.AbstractDeliveryCertDocumentType;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.bhc.doc41webui.web.Doc41Tags;
import com.bayer.ecim.foundation.basic.BooleanTool;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.business.sbeanaccess.BATranslationsException;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

/**
 * Search- & Download-Controller. This Controler implements Search and Download
 * functions.
 */
@Controller
public class SearchController extends AbstractDoc41Controller {

	@Autowired
	private DocumentUC documentUC;

	/**
	 * Get a required permission to perform a certain operation, can be overwritten
	 * to enforce specific permission.
	 * 
	 * @param user
	 *            - user.
	 * @param httpServletRequest
	 *            - HTTP servlet request.
	 * @return null, if no specific permission required.
	 * @throws Doc41BusinessException
	 */
	@Override
	protected String[] getReqPermission(User user, HttpServletRequest httpServletRequest)
			throws Doc41BusinessException {
		String type = httpServletRequest.getParameter("type");
		if (StringTool.isTrimmedEmptyOrNull(type)) {
			return null;
		}
		return getReqPermission(user, type);
	}

	private String[] getReqPermission(User user, String documentType) throws Doc41BusinessException {
		return !documentUC.getFilteredDocTypesForDownload(documentType, user).isEmpty() ? null
				: new String[] { documentUC.getDownloadPermission(documentType),
						documentUC.getGroupDownloadPermission(documentType) };
	}


	@RequestMapping(value = "/documents/documentsearch", method = RequestMethod.GET)
	public SearchForm get(@ModelAttribute SearchForm searchForm, BindingResult bindingResult,
			@RequestParam(required = false) String ButtonSearch,
			@RequestParam(required = false, defaultValue = "true") boolean errorOnNoDocuments)
			throws Doc41BusinessException, BATranslationsException {
		User user = UserInSession.get();
		String language = LocaleInSession.get().getLanguage();
		String mFormType = searchForm.getType();
		// get flag=X for popwindow
		String p_Flag = null;
		// To get !* digit mat no
		// List<String> multipleLineItem = new ArrayList<String>();
		if (StringTool.isTrimmedEmptyOrNull(mFormType)) {
			throw new Doc41BusinessException("typeIsMissing");
		}
		String mSelectedDocType = StringTool.emptyToNull(searchForm.getDocType());
		List<DownloadDocumentType> mDocTypes = documentUC.getFilteredDocTypesForDownload(mFormType, user);
		Doc41Log.get().debug(this, null,
				"Search for download for " + mDocTypes.size() + " document types by '" + mFormType);
		// System.out.println("mDocTypes::"+mDocTypes);
		searchForm.setDocumentTypes(mDocTypes);
		List<Attribute> attributeDefinitions = new ArrayList<Attribute>();
		HashSet<String> attributeDefinitionNames = new HashSet<String>();
		int objectIdFillLength = -1;
		boolean isFirst = true;
		boolean isKgs = false;
		Properties mMaxVer = ConfigMap.get().getSubCfg("documents", "getOnlyMaxVer");
		boolean mOnlyMaxVer = true;
		Locale locale = LocaleInSession.get();
		Tags tags = new Doc41Tags(TranslationsDAO.SYSTEM_ID, "documents", "*", (locale == null) ? Locale.US : locale);
		ArrayList<SelectionItem> mAllowedDocTypes = new ArrayList<SelectionItem>();
		mAllowedDocTypes.add(new SelectionItem("", tags.getOptTagNoEsc("allDocTypes")));
		for (DownloadDocumentType mDocType : mDocTypes) {
			mAllowedDocTypes.add(new SelectionItem(mDocType.getTypeConst(), tags.getTagNoEsc(mDocType.getTypeConst())));
			if (isFirst) {
				isFirst = false;
				isKgs = mDocType.isKgs();
			} else {
				if (isKgs != mDocType.isKgs()) {
					throw new Doc41BusinessException(
							"Mixed kind of DocumentType storage technology - KGS & DIRS - not supported!");
				}
			}
			mOnlyMaxVer &= BooleanTool.getBoolean(mMaxVer.getProperty(mDocType.getTypeConst()),
					BooleanTool.getBoolean(mMaxVer.getProperty(mDocType.getGroup()), false));
			int objectIdFillLengthLocal = mDocType.getObjectIdFillLength();
			if (objectIdFillLength == -1) {
				objectIdFillLength = objectIdFillLengthLocal;
			} else if (objectIdFillLength != objectIdFillLengthLocal) {
				throw new Doc41BusinessException("ObjectIdFillLength ambigious for Documents of Type: " + mFormType
						+ " -> " + objectIdFillLength + " <> " + objectIdFillLengthLocal);
			}
			String mType = mDocType.getTypeConst();
			searchForm.initPartnerNumbers(documentUC.hasCustomerNumber(mType), getLastCustomerNumberFromSession(),
					documentUC.hasVendorNumber(mType), getLastVendorNumberFromSession());
			List<Attribute> typeAttributeDefinitions = documentUC.getAttributeDefinitions(mType, false);
			for (Attribute attr : typeAttributeDefinitions) {
				if (!attributeDefinitionNames.contains(attr.getName())) {
					Doc41Log.get().debug(this, null, "--->>> ATTR-NAME: '" + attr.getName());
					attributeDefinitionNames.add(attr.getName());
					attributeDefinitions.add(attr);
				}
			}
		}
		
		
		
		searchForm.setAllowedDocTypes(mAllowedDocTypes);
		searchForm.setKgs(isKgs);
		searchForm.initAttributes(attributeDefinitions, language);

		if (!StringTool.isTrimmedEmptyOrNull(ButtonSearch)) {
			if (searchForm.isSearchFilled()) {
				CheckForDownloadResult checkResult = new CheckForDownloadResult();
				
				String searchFormCustomerNumber = searchForm.getCustomerNumber();
				String searchFormVendorNumber = searchForm.getVendorNumber();
				String searchFormCustomVersion = searchForm.getProductionVersion();
				Date searchFormTimeFrame = searchForm.getTimeFrame();
				String searchFormPurchaseOrder = searchForm.getPurchaseOrder();
				validateMandatoryInputFields(bindingResult, searchForm.isCustomerNumberUsed(), searchFormCustomerNumber,
						searchForm.isVendorNumberUsed(), searchFormVendorNumber, searchForm.getSubtype(),
						/* searchFormCustomVersion */ searchFormPurchaseOrder, searchFormTimeFrame);
				if (!bindingResult.hasErrors()) {
					String singleObjectId = searchForm.getObjectId();
					Map<String, String> attributeValues = searchForm.getAttributeValues();
					Map<String, String> viewAttributes = searchForm.getViewAttributes();
					Map<String, String> attributePredefValuesAsString = searchForm.getAttributePredefValuesAsString();
					checkForbiddenWildcards(bindingResult, "objectId", singleObjectId);
					checkForbiddenWildcards(bindingResult, "attributeValues['", "']", attributeValues);
					checkForbiddenWildcards(bindingResult, "viewAttributes['", "']", viewAttributes);
					checkAllOption(bindingResult, "attributeValues['", "']", attributeValues,
							attributePredefValuesAsString);
					checkAllOption(bindingResult, "viewAttributes['", "']", viewAttributes,
							attributePredefValuesAsString);
					if (!bindingResult.hasErrors()) {
						if (!StringTool.isTrimmedEmptyOrNull(singleObjectId)) {
							if (singleObjectId.length() < objectIdFillLength) {
								singleObjectId = StringTool.minLString(singleObjectId, objectIdFillLength, '0');

								searchForm.setObjectId(singleObjectId);
							}
						}
						ArrayList<String> searchingTargetTypes = new ArrayList<String>();
						ArrayList<BeanPropertyBindingResult> results = new ArrayList<BeanPropertyBindingResult>();
						Map<String, String> allAttributeValues = new HashMap<String, String>();
						List<String> objectIds = new ArrayList<String>();
						if (!StringTool.isTrimmedEmptyOrNull(singleObjectId)) {
							objectIds.add(singleObjectId);
							objectIds.add(searchFormPurchaseOrder);
						}
						int i = 0;
						HashSet<String> mChkErrs = new HashSet<String>();
						ArrayList<String> mChkErrTypes = new ArrayList<String>();
						boolean mIsMatNotFoundForVendor = false;
						for (DocumentType mDocType : mDocTypes) {
							i++;
							Doc41Log.get().debug(this, null,
									"Check permission for DocType " + i + "/" + mDocTypes.size() + " : "
											+ mDocType.getTypeConst() + " (" + mDocType.getSapTypeId() + ")");
							// We need to create separate BindingResults per DocType, then see which work
							// fine and choose them, otherwise merge all to show all errors.
							// hope this way is fine to create local, temporary result...
							BeanPropertyBindingResult mTmp = new BeanPropertyBindingResult(bindingResult.getTarget(),
									bindingResult.getObjectName());
							results.add(mTmp);
							/* CheckForDownloadResult */ checkResult = documentUC.checkForDownload(mTmp,
									mDocType.getTypeConst(), searchFormCustomerNumber, searchFormVendorNumber,
									singleObjectId, searchFormCustomVersion, searchFormTimeFrame, attributeValues,
									viewAttributes, searchForm.getSubtype(), searchForm.getPurchaseOrder());
							
							
							if (!mTmp.hasErrors()) {
								if ((mSelectedDocType == null) || mSelectedDocType.equals(mDocType.getTypeConst())) {
									searchingTargetTypes.add(mDocType.getTypeConst());
								}
								searchForm.setSearchingTargetType(searchingTargetTypes);
								String listString = String.join(",", searchingTargetTypes);
								searchForm.setSearchType(listString);
								// FIXME: We should report the types possible to search for to the mask... (in
								// case of GroupSearch, see DocumentType.GROUP*)
								allAttributeValues.putAll(attributeValues);
								Map<String, String> additionalAttributes = checkResult.getAdditionalAttributes();
								if (additionalAttributes != null) {
									allAttributeValues.putAll(additionalAttributes);
								}
								List<String> additionalObjectIds = checkResult.getAdditionalObjectIds();
								if (additionalObjectIds != null && !additionalObjectIds.isEmpty()) {
									// FIXME: Will we have a Duplicates Problem???
									objectIds.addAll(additionalObjectIds);
								}
							} else {
								mChkErrTypes.add("" + mDocType.getTypeConst() + "/" + mDocType.getSapTypeId());
								for (ObjectError mObjErr : mTmp.getAllErrors()) {
									String mObjErrStr = "" + mObjErr.getCode();
									mIsMatNotFoundForVendor |= "[MatNotFoundForVendor]".equals(mObjErrStr);
									mChkErrs.add(mObjErrStr);
								}
								Doc41Log.get().debug(this, null, "==>>> DocType ignored, user has no permission: "
										+ mDocType.getTypeConst() + "(" + mDocType.getSapTypeId() + ")");
								Doc41Log.get().debug(this, null, StringTool.list(mTmp.getAllErrors(), " // ", false));
							}
						}
						Doc41Log.get().debug(this, null,
								"SearchingTargetTypes allowed to search: " + searchingTargetTypes.size() + " ("
										+ StringTool.list(searchingTargetTypes, ", ", false) + ")");
						if (mIsMatNotFoundForVendor) {
							Doc41Log.get().warning(this, null,
									"not allowed, Material: " + singleObjectId + ", Partner: " + searchFormVendorNumber
											+ ", Customer: " + searchFormCustomerNumber + ", Version: "
											+ searchFormCustomVersion + ", Download DENIED, "
											+ (searchingTargetTypes.isEmpty() ? "all Types"
													: StringTool.list(mChkErrTypes, ", ", false))
											+ ", Results: " + StringTool.list(mChkErrs, ", ", false) + "!");
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
										bindingResult.addError(mErr);
									}
								}
							}
							if (allHaveFieldErrors) {
								bindingResult.reject("PleaseEnterMandatoryFields");
							}
						} else {
							int maxResults = searchForm.getMaxResults();
							if (!(searchForm.getSubtype() == 1)) {
								searchingTargetTypes.remove("YBM");
								
								//System.out.println("allAttributeValue in controller:: "+allAttributeValues);
								List<HitListEntry> documents = documentUC.searchDocuments(searchingTargetTypes,
										objectIds, searchFormPurchaseOrder,allAttributeValues, maxResults, mOnlyMaxVer);
								//System.out.println("line no314::"+documents.toString());
								if (documents.isEmpty()) {
									if (errorOnNoDocuments) {
										bindingResult.reject("NoDocumentsFound");
									}
								} else if (documents.size() > maxResults) {
									bindingResult.reject("ToManyResults");
									bindingResult.reject("" + documents.size() + " / " + maxResults);
								} else {
									searchForm.setDocuments(documents);
								}
								System.out.println("before----------");
								
								
								Doc41Log.get().debug(this, null, "Searched Documents of Types: '"
										+ StringTool.list(searchingTargetTypes, ", ", false) + "' (for " + mFormType
										+ "), object_Id(s): " + StringTool.list(objectIds, ", ", false)
										+ ", onlyMaxVers: " + mOnlyMaxVer + " finding results: " + documents.size());
							} else {
								HashSet<String> uniqueValues = new HashSet<String>();
								ArrayList<String> material_list = new ArrayList<String>();
								ArrayList<String> pv_list = new ArrayList<String>();

								ArrayList<String> Mat_textList = new ArrayList<String>();
								for (int i1 = 2; i1 < checkResult.getMaterialList().size(); i1++) {

									if (checkResult.getMaterialList().get(i1).length() == 18) {
										material_list.add(checkResult.getMaterialList().get(i1));
									}

									if (checkResult.getMaterialList().get(i1).length() == 4) {
										pv_list.add(checkResult.getMaterialList().get(i1));
										// additionalAttributes.put("MATERIAL_BOM", deliveryCheck.get(i));
										/*
										 * String val=checkResult.getMaterialList().get(i1); multipleLineItem.add(val);
										 */

									}

									if (!(checkResult.getMaterialList().get(i1).length() == 18
											|| checkResult.getMaterialList().get(i1).length() == 4)) {

										Mat_textList.add(checkResult.getMaterialList().get(i1));
									}
									searchForm.setMaterialText(Mat_textList);
								}

								for (String value : material_list) {

									uniqueValues.add(value);

								}
								p_Flag = checkResult.getMaterialList().get(1);
								
								if (!(p_Flag.equals("X")) || (uniqueValues.size() == 1)) {
//									objectIds.add(searchFormPurchaseOrder);
//									System.out.println("not eq x objectIds:"+objectIds);
									objectIds.addAll(uniqueValues);
									objectIds.add(searchFormPurchaseOrder);
									List<HitListEntry> documents = documentUC.searchDocuments(searchingTargetTypes,
											objectIds, searchFormPurchaseOrder, allAttributeValues, maxResults, mOnlyMaxVer);
									
							        
									if (documents.isEmpty()) {
										if (errorOnNoDocuments) {
											bindingResult.reject("NoDocumentsFound");
											
										}
									} else if (documents.size() > maxResults) {
										bindingResult.reject("ToManyResults");
										bindingResult.reject("" + documents.size() + " / " + maxResults);
									} else {
										searchForm.setDocuments(documents);
									}
									if(checkResult.getMaterialList().get(0)==null) {
										
									}
									
									else if(checkResult.getMaterialList().get(0).equals("NoBomFound")) {
										bindingResult.reject("NoBomFound");
									}
									
									//documents.clear();
									Doc41Log.get().debug(this, null,
											"Searched Documents of Types: '"
													+ StringTool.list(searchingTargetTypes, ", ", false) + "' (for "
													+ mFormType + "), object_Id(s): "
													+ StringTool.list(objectIds, ", ", false) + ", onlyMaxVers: "
													+ mOnlyMaxVer + " finding results: " + documents.size());
								} else {
									searchForm.setLineItemFlag(true);
									searchForm.setMaterialNumberList(material_list);
									searchForm.setMaterialText(Mat_textList);
									searchForm.setProductionversionList(pv_list);

								}
							}
						}
					}
				}
			} else {
				bindingResult.reject("NoSearchWithoutSearchParameters");
			}
		}

		return searchForm;
	}

	private void checkAllOption(BindingResult result, String fieldNamePrefix, String fieldNameSuffix,
			Map<String, String> attributeValues, Map<String, String> attributePredefValuesAsString) {
		Set<String> keySet = attributeValues.keySet();
		if (keySet != null) {
			for (String key : keySet) {

				String value = attributeValues.get(key);
				// System.out.println("attribute key:"+key + " attribute value:: "+value);
				if (value != null && value.contains("###")) {
					String fieldName = fieldNamePrefix + key + fieldNameSuffix;
					String allString = attributePredefValuesAsString.get(key);
					if (!StringTool.equals(allString, value)) {
						result.rejectValue(fieldName, "ForbiddenValue");
					}
				}
			}
		}
	}

	/**
	 * Serch Documents Webservice, used for Spepor (Spepor entry point).
	 * 
	 * @param vendor
	 * @param refnumber
	 * @return
	 * @throws Doc41DocServiceException
	 */
	@RequestMapping(value = "/docservice/sdsearch", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	public @ResponseBody BdsServiceSearchDocumentsResult getSDListForService(
			@RequestParam(required = true) String vendor, @RequestParam String refnumber)
			throws Doc41DocServiceException {
		try {
			List<BdsServiceDocumentEntry> documents = new ArrayList<BdsServiceDocumentEntry>();

			List<String> types = documentUC.getAvailableSDDownloadDocumentTypes();
			for (String type : types) {

				String[] mPerm = getReqPermission(UserInSession.get(), type);
				if ((mPerm == null) || UserInSession.get().hasPermission(mPerm)) {
					SearchForm searchForm = new SearchForm();
					Doc41Log.get().debug(this, null, "Initialize SearchForm for WebService (type: " + type
							+ ", vendor: " + vendor + ", refnumber: " + refnumber + ")");
					searchForm.setType(type);
					searchForm.setVendorNumber(vendor);
					searchForm.setObjectId(refnumber);
					searchForm.setMaxResults(2000);

					BindingResult result = new BeanPropertyBindingResult(searchForm, "searchForm");
					get(searchForm, result, "DocServiceSearch", false);
					if (!result.hasErrors()) {
						List<HitListEntry> docsOneType = searchForm.getDocuments();
						if (docsOneType != null) {
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
			// FIXME: Webservice???
			throw new Doc41DocServiceException("getSDListForService", e);
		}
	}

	private void checkForbiddenWildcards(BindingResult result, String fieldNamePrefix, String fieldNameSuffix,
			Map<String, String> attributes) {
		Set<Entry<String, String>> entrySet = attributes.entrySet();
		for (Entry<String, String> entry : entrySet) {
			String fieldName = fieldNamePrefix + entry.getKey() + fieldNameSuffix;
			checkForbiddenWildcards(result, fieldName, entry.getValue());
		}
	}

	private void checkForbiddenWildcards(BindingResult result, String fieldName, String value) {
		if (value != null && (value.indexOf('*') >= 0 || value.indexOf('+') >= 0)) {
			result.rejectValue(fieldName, "NoWildCardsAllowed");
		}
	}

	@RequestMapping(value = "/documents/searchdelcertcountry", method = RequestMethod.GET)
	public ModelMap getDelCertCountry(@ModelAttribute SearchForm searchForm, BindingResult result,
			@RequestParam(required = false) String ButtonSearch)
			throws Doc41BusinessException, BATranslationsException {
		ModelMap map = new ModelMap();
		SearchForm searchForm2 = get(searchForm, result, ButtonSearch, true);
		map.addAttribute(searchForm2);

		map.addAttribute("keyCountry", AbstractDeliveryCertDocumentType.ATTRIB_COUNTRY);
		map.addAttribute("keyBatch", AbstractDeliveryCertDocumentType.ATTRIB_BATCH);
		map.addAttribute("keyMaterial", AbstractDeliveryCertDocumentType.ATTRIB_MATERIAL);

		List<SelectionItem> userCountries = displaytextUC.getCountrySIs(UserInSession.get().getCountries());
		map.addAttribute("userCountrySIList", userCountries);
		return map;
	}

	@RequestMapping(value = "/documents/searchdelcertcustomer", method = RequestMethod.GET)
	public ModelMap getDelCertCustomer(@ModelAttribute SearchForm searchForm, BindingResult result,
			@RequestParam(required = false) String ButtonSearch)
			throws Doc41BusinessException, BATranslationsException {
		ModelMap map = new ModelMap();
		SearchForm searchForm2 = get(searchForm, result, ButtonSearch, true);
		map.addAttribute(searchForm2);

		map.addAttribute("keyCountry", AbstractDeliveryCertDocumentType.ATTRIB_COUNTRY);
		map.addAttribute("keyBatch", AbstractDeliveryCertDocumentType.ATTRIB_BATCH);
		map.addAttribute("keyMaterial", AbstractDeliveryCertDocumentType.ATTRIB_MATERIAL);
		map.addAttribute("keyDeliveryNumber", AbstractDeliveryCertDocumentType.VIEW_ATTRIB_DELIVERY_NUMBER);

		List<SelectionItem> allCountries = getDisplaytextUC().getCountryCodes();
		map.addAttribute("allCountryList", allCountries);
		return map;
	}

	/**
	 * Map the searchDocument request, adding extra attributes for
	 * PMSupplierDownload
	 * 
	 * @param searchForm
	 * @param result
	 * @param ButtonSearch
	 * @return
	 * @throws Doc41BusinessException
	 * @throws BATranslationsException
	 */
	@RequestMapping(value = "/documents/searchpmsupplier", method = RequestMethod.GET)
	public ModelMap getPMSupplier(@ModelAttribute SearchForm searchForm, BindingResult result,
			@RequestParam(required = false) String ButtonSearch)
			throws Doc41BusinessException, BATranslationsException {
		ModelMap map = new ModelMap();
		String type = searchForm.getType();
		DocumentType docType = documentUC.getDocType(type);
		SearchForm searchForm2 = get(searchForm, result, ButtonSearch, true);
		map.addAttribute(searchForm2);

		// check-PO-mode:
		// map.addAttribute("keyPONumber",PMSupplierDownloadDocumentType.VIEW_ATTRIB_PO_NUMBER);
		if (docType.isDirs()) {
			map.addAttribute("keyFileName", PMSupplierDownloadDocumentType.VIEW_ATTRIB_FILENAME);
		}

		return map;
	}

	@RequestMapping(value = "/documents/data", method = RequestMethod.POST)

	public ModelMap getLineItems(@ModelAttribute SearchForm modalForm, BindingResult bindingResult,
			@RequestParam(required = false) String ButtonSearch) {

		ModelMap modelMap = new ModelMap();
		modalForm = getModalData(modalForm, bindingResult, true);
		return modelMap;

	}

	@RequestMapping(value = "/documents/documentsearchModal", method = RequestMethod.GET)

	private SearchForm getModalData(@ModelAttribute SearchForm modalForm, BindingResult bindingResult,
			@RequestParam(required = false, defaultValue = "true") boolean errorOnNoDocuments) {
		// TODO Auto-generated method stub
		int maxResults = 500;
		boolean mOnlyMaxVer = true;
		Map<String, String> allAttributeValues = new HashMap<String, String>();
		allAttributeValues = modalForm.getAttributeValues();
		 
		
	//IV_VERID_BOM	System.out.println("additionalAttributes::"+additionalAttributes);
		
			allAttributeValues.put("IV_VERID_BOM", modalForm.getProductionVersion());
		
		List<String> matNo = new ArrayList<String>();
		String obj = modalForm.getObjectId();
		matNo.add(obj);
		matNo.add (modalForm.getPurchaseOrder());
		String type = modalForm.getSearchType();
		String[] strSplit = type.split(",");

		ArrayList<String> searchingType = new ArrayList<String>(Arrays.asList(strSplit));
		List<String> deliveryCheck= new ArrayList<String>();
		try {
		try {
			deliveryCheck = documentUC.checkSpecification( modalForm.getPurchaseOrder(),modalForm.getVendorNumber(),modalForm.getProductionVersion(),obj);
			
			if (deliveryCheck.get(0) != null) {
				bindingResult.reject("" + deliveryCheck.get(0));
			}
		} catch (Doc41ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
			List<HitListEntry> documents = documentUC.searchDocuments(searchingType, matNo, modalForm.getPurchaseOrder(), allAttributeValues,
					maxResults, mOnlyMaxVer);
			//System.out.println("documents: 597"+documents.toString());
			allAttributeValues.remove("IV_VERID_BOM");
			if (documents.isEmpty()) {
				
				if (errorOnNoDocuments) {
					
					bindingResult.reject("NoDocumentsFound");
				}
			} else if (documents.size() > maxResults) {
				bindingResult.reject("ToManyResults");
				bindingResult.reject("" + documents.size() + " / " + maxResults);
			} else {
				modalForm.setDocuments(documents);
			}
			/*if(deliveryCheck.get(0).equals("NoBomFound")) {
				bindingResult.reject("NoBomFound");
			}*/
			
		} catch (Doc41BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modalForm;
	}

	/**
	 * Map the searchDocument request, adding extra attributes for
	 * PMSupplierDownload (global).
	 * 
	 * @param searchForm
	 *            - search form.
	 * @param bindingResult
	 *            - object for error handling.
	 * @param ButtonSearch
	 *            - search button string.
	 * @return modelMap - model of search controller.
	 * @throws Doc41BusinessException
	 * @throws BATranslationsException
	 */
	@RequestMapping(value = "/documents/searchpmsupplierGlobal", method = RequestMethod.GET)
	public ModelMap getPMSupplierGlobal(@ModelAttribute SearchForm searchForm, BindingResult bindingResult,
			@RequestParam(required = false) String ButtonSearch)
			throws Doc41BusinessException, BATranslationsException {
		ModelMap modelMap = new ModelMap();

		SearchForm form = get(searchForm, bindingResult, ButtonSearch, true);
	//	System.out.println("before  line item:"+form.getLineItemFlag());
		if (form.getLineItemFlag()) {
			//set flag=1 to display popup modal
			searchForm.setFlag("1");
			//System.out.println("form flag:"+form.getLineItemFlag());
			modelMap.addAttribute(form);
		} else if (!(searchForm.getFlag() == null)) {
			//System.out.println("in else for flag val:"+searchForm.getFlag());
			//System.out.println("in else for mat val:"+searchForm.getObjectId());
			searchForm = getModalData(searchForm, bindingResult, true);
		}
		return modelMap;
	}

	/**
	 * Map the searchDocument request, adding extra attributes for
	 * SDSupplierDownload (global)
	 * 
	 * @param searchForm
	 * @param result
	 * @param ButtonSearch
	 * @return
	 * @throws Doc41BusinessException
	 * @throws BATranslationsException
	 */
	@RequestMapping(value = "/documents/searchsdsupplierGlobal", method = RequestMethod.GET)
	public ModelMap getSDSupplierGlobal(@ModelAttribute SearchForm searchForm, BindingResult result,
			@RequestParam(required = false) String ButtonSearch)
			throws Doc41BusinessException, BATranslationsException {
		ModelMap map = new ModelMap();
		// String type = searchForm.getType(); // DOC_SD
		// DocumentType docType = documentUC.getDocType(type);

		// map.addAttribute("docType",PMSupplierDownloadDocumentType.VIEW_ATTRIB_DOC_TYPE);

		SearchForm searchForm2 = get(searchForm, result, ButtonSearch, true);
		map.addAttribute(searchForm2);

		// check-PO-mode:
		// map.addAttribute("keyPONumber",PMSupplierDownloadDocumentType.VIEW_ATTRIB_PO_NUMBER);
		// if (docType.isDirs()) {
		// map.addAttribute("keyFileName",PMSupplierDownloadDocumentType.VIEW_ATTRIB_FILENAME);
		// }

		return map;
	}

	@RequestMapping(value = "/documents/downloadMulti", method = RequestMethod.POST)
	public void downloadMulti(@ModelAttribute MultiDownloadForm values, HttpServletResponse response)
			throws Doc41BusinessException {
		boolean mDoThrowEx = true;
		StringBuffer mComments = new StringBuffer();
		ZipOutputStream mOut = null;
		String generateFileName = null;
		String type = null;
		String docId = null;
		String cwid = null;
		String sapObjId = null;
		String sapObjType = null;
		String filename = null;
		String res = null;
		long mZIPStartMs = System.currentTimeMillis();
		long mStartMs = System.currentTimeMillis();
		long mEndMs = System.currentTimeMillis();
		long mZIPEndMs = System.currentTimeMillis();
		Doc41Log.get().debug(this, null, "*** DOWNLOAD ZIP... ***");
		try {
			List<String> mSelected = values.getDocSel();
			if ((mSelected == null) || mSelected.isEmpty()) {
				mSelected = values.getDocAll();
				Doc41Log.get().debug(this, null,
						"No explicitly selected Items, assuming user want to download all documents: "
								+ ((mSelected == null) ? "n/a" : "" + mSelected.size()));
			} else {
				Doc41Log.get().debug(this, null, "User selected Items for download:" + mSelected.size());
			}
			if (mSelected == null) {
				throw new Doc41BusinessException("no result to download");
			}
			// alternate possibility to handle duplicates, but not perfect...
			// HashMap<String, Integer> mAddedFiles = new HashMap<String, Integer>();
			for (String mParm : mSelected) {
				Doc41Log.get().debug(this, null, "Download DOC: " + mParm);
				String[] mParmArr = StringTool.split(mParm, '|');
				String key = mParmArr[0];
				@SuppressWarnings("unused") // currently not used (on original download even completely ignored by
											// download
											// servlet
				String formType = mParmArr[0];
				Map<String, String> decryptParameters = UrlParamCrypt.decryptParameters(key);
				type = decryptParameters.get(Doc41Constants.URL_PARAM_TYPE);
				docId = decryptParameters.get(Doc41Constants.URL_PARAM_DOC_ID);
				cwid = decryptParameters.get(Doc41Constants.URL_PARAM_CWID);
				sapObjId = decryptParameters.get(Doc41Constants.URL_PARAM_SAP_OBJ_ID);
				sapObjType = decryptParameters.get(Doc41Constants.URL_PARAM_SAP_OBJ_TYPE);
				filename = StringTool.emptyToNull(StringTool
						.decodeURLWithDefaultFileEnc(decryptParameters.get(Doc41Constants.URL_PARAM_FILENAME)));
				Doc41Log.get().debug(this, null, "Download DOC KEY: " + key);
				Doc41Log.get().debug(this, null, "Download DOC FILE: " + filename);
				if (StringTool.isTrimmedEmptyOrNull(docId)) {
					new Doc41BusinessException("docId is missing in download link");
					mComments.append("\n\n*** FAILED (docId): " + StringTool.nvl(filename, "n/a") + " ***");
				} else if (StringTool.isTrimmedEmptyOrNull(type)) {
					new Doc41BusinessException("type is missing in download link");
					mComments.append(
							"\n\n*** FAILED (type): " + StringTool.nvl(filename, "n/a") + " (" + docId + ") ***");
				} else if (cwid == null || !cwid.equalsIgnoreCase(UserInSession.getCwid())) {
					new Doc41BusinessException("download link for different user");
					mComments.append(
							"\n\n*** FAILED (cwid): " + StringTool.nvl(filename, "n/a") + " (" + docId + ") ***");
				} else {
					if (mOut == null) {
						generateFileName = "BDS_" + cwid + "_" + sapObjId + "__"
								+ (new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss")
										.format(new Date(System.currentTimeMillis())) + ".zip");
						Doc41Log.get().debug(this, null, "...ZIP File: " + generateFileName);
						mDoThrowEx = false; // OutputStream in USE, redirection no more possible...
						mOut = documentUC.createZipResponse(response, generateFileName, mZIPStartMs);
					}
					// all exceptions catched internal, status added to comment, if not ok...
					mStartMs = System.currentTimeMillis();
					Doc41Log.get().debug(this, null,
							"*** DOWNLOAD ZIP ADD File: " + filename + ", add to ZIP " + generateFileName + ", type: "
									+ type + ", docId: " + docId + ", sapObjId: " + sapObjId + ", sapObjType: "
									+ sapObjType + "... ***");
					res = documentUC.addDownloadDocumentToZip(mOut, type, docId, filename, sapObjId, sapObjType,
							mComments, generateFileName, mStartMs /* , mAddedFiles */);
					mEndMs = System.currentTimeMillis();
					Doc41Log.get().debug(this, null,
							"*** DOWNLOAD ZIP ADD File: " + filename + ", add to ZIP " + generateFileName + ", type: "
									+ type + ", docId: " + docId + ", sapObjId: " + sapObjId + ", sapObjType: "
									+ sapObjType + " done, " + ((mEndMs - mStartMs) / 1000.0) + "s, response: " + res
									+ " ***");
				}
			}
			// if this fails, we can not report to user, only to log... (add comment to ZIP
			// & close ZIP).
			if (mOut != null) {
				mComments.append("\n");
				documentUC.closeZipDownload(response, mOut, generateFileName, mComments.toString(), mZIPStartMs);
				mZIPEndMs = System.currentTimeMillis();
				Doc41Log.get().debug(this, null, "*** MULTIDOWNLOAD SUCC: " + generateFileName + ", after overall: "
						+ ((mZIPEndMs - mZIPStartMs) / 1000.0) + "s ***");
			}
		} catch (Doc41BusinessException ie) {
			mEndMs = System.currentTimeMillis();
			mZIPEndMs = System.currentTimeMillis();
			Doc41Log.get().error(this, null,
					"*** DOWNLOAD ZIP FAIL: " + generateFileName + ", last file: " + filename + ", type: " + type
							+ ", docId: " + docId + ", sapObjId: " + sapObjId + ", sapObjType: " + sapObjType
							+ ", after overall: " + ((mZIPEndMs - mZIPStartMs) / 1000.0) + "s, last: "
							+ ((mEndMs - mStartMs) / 1000.0) + "s, last response: " + res + " ***");
			if (mDoThrowEx) {
				throw ie;
			}
		} catch (Doc41ClientAbortException mCAEx) {
			mEndMs = System.currentTimeMillis();
			mZIPEndMs = System.currentTimeMillis();
			Doc41Log.get().warning(this, null,
					"*** DOWNLOAD ZIP FAIL, ClientAbortedException: " + generateFileName + " failed, last file: "
							+ filename + ", type: " + type + ", docId: " + docId + ", sapObjId: " + sapObjId
							+ ", sapObjType: " + sapObjType + ", after overall: " + ((mZIPEndMs - mZIPStartMs) / 1000.0)
							+ "s, last: " + ((mEndMs - mStartMs) / 1000.0) + "s, last response: " + res + " ***");
		} catch (Exception e) {
			mEndMs = System.currentTimeMillis();
			mZIPEndMs = System.currentTimeMillis();
			Doc41BusinessException mEx = new Doc41BusinessException("*** DOWNLOAD ZIP FAIL: " + generateFileName
					+ ", last file: " + filename + ", type: " + type + ", docId: " + docId + ", sapObjId: " + sapObjId
					+ ", sapObjType: " + sapObjType + ", after overall: " + ((mZIPEndMs - mZIPStartMs) / 1000.0)
					+ "s, last: " + ((mEndMs - mStartMs) / 1000.0) + "s, last response: " + res + " ***", e);
			if (mDoThrowEx) {
				throw mEx;
			}
		}
	}

	/**
	 * Implements Download for WebUI and WebService for Spepor (Spepor entry point).
	 * 
	 * @param key
	 * @param response
	 * @throws Doc41BusinessException
	 */
	@RequestMapping(value = { "/documents/download", "/docservice/download" }, method = RequestMethod.GET)
	public void download(@RequestParam String key, HttpServletResponse response) throws Doc41BusinessException {
		Map<String, String> decryptParameters = UrlParamCrypt.decryptParameters(key);
		String type = decryptParameters.get(Doc41Constants.URL_PARAM_TYPE);
		String docId = decryptParameters.get(Doc41Constants.URL_PARAM_DOC_ID);
		String cwid = decryptParameters.get(Doc41Constants.URL_PARAM_CWID);
		String sapObjId = decryptParameters.get(Doc41Constants.URL_PARAM_SAP_OBJ_ID);
		String sapObjType = decryptParameters.get(Doc41Constants.URL_PARAM_SAP_OBJ_TYPE);
		String filename = StringTool.emptyToNull(
				StringTool.decodeURLWithDefaultFileEnc(decryptParameters.get(Doc41Constants.URL_PARAM_FILENAME)));
		String mRes = null;
		long mStartMs = System.currentTimeMillis();
		long mEndMs = System.currentTimeMillis();
		Doc41Log.get().debug(this, null, "Download DOC KEY: " + key);
		Doc41Log.get().debug(this, null, "Download DOC FILE: " + filename);
		if (StringTool.isTrimmedEmptyOrNull(type)) {
			throw new Doc41BusinessException("type is missing in download link");
		}
		if (StringTool.isTrimmedEmptyOrNull(docId)) {
			throw new Doc41BusinessException("docId is missing in download link");
		}
		if (cwid == null || !cwid.equalsIgnoreCase(UserInSession.getCwid())) {
			throw new Doc41BusinessException("download link for different user");
		}
		try {
			mStartMs = System.currentTimeMillis();
			Doc41Log.get().debug(this, null, "*** DOWNLOAD File: " + filename + ", type: " + type + ", docId: " + docId
					+ ", sapObjId: " + sapObjId + ", sapObjType: " + sapObjType + "... ***");
			mRes = documentUC.downloadDocument(response, type, docId, filename, sapObjId, sapObjType);
			mEndMs = System.currentTimeMillis();
			Doc41Log.get().debug(this, null,
					"*** DOWNLOAD File: " + filename + ", type: " + type + ", docId: " + docId + ", sapObjId: "
							+ sapObjId + ", sapObjType: " + sapObjType + " done, " + ((mEndMs - mStartMs) / 1000.0)
							+ "s, response: " + mRes + " ***");
		} catch (Doc41BusinessException ie) {
			mEndMs = System.currentTimeMillis();
			Doc41Log.get().error(this, null,
					"*** DOWNLOAD FAIL: " + filename + " failed, type: " + type + ", docId: " + docId + ", sapObjId: "
							+ sapObjId + ", sapObjType: " + sapObjType + ", after: " + ((mEndMs - mStartMs) / 1000.0)
							+ "s ***");
			throw ie;
		} catch (Doc41ClientAbortException e) {
			mEndMs = System.currentTimeMillis();
			Doc41Log.get().debug(this, null,
					"*** DOWNLOAD FAIL, ClientAbortedException: " + filename + " failed, type: " + type + ", docId: "
							+ docId + ", sapObjId: " + sapObjId + ", sapObjType: " + sapObjType + ", after: "
							+ ((mEndMs - mStartMs) / 1000.0) + "s ***");
		} catch (Exception e) {
			mEndMs = System.currentTimeMillis();
			throw new Doc41BusinessException("*** DOWNLOAD FAIL: " + filename + " failed, type: " + type + ", docId: "
					+ docId + ", sapObjId: " + sapObjId + ", sapObjType: " + sapObjType + ", after: "
					+ ((mEndMs - mStartMs) / 1000.0) + "s ***", e);
		}

		// FIXME: throws Doc41BusinessException, Webservice???
	}

	/**
	 * Validates customer/vendor number, BOM version ID´and BOM time frame.
	 * 
	 * @param bindingResult
	 *            - object for error handling.
	 * @param hasCustomerNumber
	 *            - flag which determines if document type is using customer number
	 *            as identification.
	 * @param customerNumber
	 *            - customer number.
	 * @param hasVendorNumber
	 *            - flag which determines if document type is using vendor number as
	 *            identification.
	 * @param vendorNumber
	 *            - vendor number.
	 * @param subtype
	 *            - the PM document sub-type.
	 * @param versionIdBom
	 *            - BOM version ID.
	 * @param timeFrame
	 *            - BOM time frame.
	 * @throws Doc41BusinessException
	 */
	private void validateMandatoryInputFields(BindingResult bindingResult, boolean hasCustomerNumber,
			String customerNumber, boolean hasVendorNumber, String vendorNumber, int subtype,
			/* String versionIdBom */String purchaseOrder, Date timeFrame) throws Doc41BusinessException {
		if (hasCustomerNumber) {
			if (StringTool.isTrimmedEmptyOrNull(customerNumber)) {
				bindingResult.rejectValue("customerNumber", "CustomerNumberMissing");
			} else {
				if (!UserInSession.get().hasCustomer(customerNumber)) {
					bindingResult.rejectValue("customerNumber", "CustomerNotAssignedToUser");
				} else {
					setLastCustomerNumberFromSession(customerNumber);
				}
			}
		}
		if (hasVendorNumber) {
			if (StringTool.isTrimmedEmptyOrNull(vendorNumber)) {
				bindingResult.rejectValue("vendorNumber", "VendorNumberMissing");
			} else {
				if (!UserInSession.get().hasVendor(vendorNumber)) {
					bindingResult.rejectValue("vendorNumber", "VendorNotAssignedToUser");
				} else {
					setLastVendorNumberFromSession(vendorNumber);
				}
			}
		}

		if (subtype == Doc41Constants.PM_DOCUMENT_SUBTYPE_PRODUCTION_VERSION) {
			if (StringTool.isTrimmedEmptyOrNull(purchaseOrder)) {
				bindingResult.rejectValue("purchaseOrder", "purchaseOrderMissing");
			}
		}

		if (subtype == Doc41Constants.PM_DOCUMENT_SUBTYPE_BOM_TIME_FRAME && timeFrame == null
				&& bindingResult.getFieldError("timeFrame") == null) {
			if (timeFrame == null) {
				bindingResult.rejectValue("timeFrame", "timeFrameMissing");
			}
		}
	}

}
