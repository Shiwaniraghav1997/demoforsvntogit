package com.bayer.bhc.doc41webui.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.SapCustomer;
import com.bayer.bhc.doc41webui.domain.SapVendor;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
import com.bayer.ecim.foundation.basic.StringTool;

public abstract class CustomizedDocumentForm {

	private String type;
	private boolean isKgs = false;
	private List<DownloadDocumentType> documentTypes;
	private String objectId;
	private Map<String, String> attributeLabels;
	private Map<String, String> attributeValues;
	private Map<String, List<String>> attributePredefValues;
	private Map<String, String> attributePredefValuesAsString;
	private Map<String, Boolean> attributeMandatory;
	private Map<String, String> viewAttributes = new HashMap<String, String>();
	private String customerNumber;
	private boolean isCustomerNumberUsed = false;
	private List<SapCustomer> customers;
	private String vendorNumber;
	private boolean isVendorNumberUsed = false;
	private String versionIdBom;
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date timeFrame;
	private List<SapVendor> vendors;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isKgs() {
		return isKgs;
	}

	public void setKgs(boolean isKgs) {
		this.isKgs = isKgs;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Map<String, String> getAttributeLabels() {
		return attributeLabels;
	}

	public void setAttributeLabels(Map<String, String> attributeLabels) {
		this.attributeLabels = attributeLabels;
	}

	public Map<String, String> getAttributeValues() {
		if (attributeValues == null) {
			attributeValues = new HashMap<String, String>();
		}
		return attributeValues;
	}

	public void setAttributeValues(Map<String, String> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public Map<String, List<String>> getAttributePredefValues() {
		return attributePredefValues;
	}

	public void setAttributePredefValues(Map<String, List<String>> attributePredefValues) {
		this.attributePredefValues = attributePredefValues;
	}

	public Map<String, String> getAttributePredefValuesAsString() {
		return attributePredefValuesAsString;
	}

	public void setAttributePredefValuesAsString(Map<String, String> attributePredefValuesAsString) {
		this.attributePredefValuesAsString = attributePredefValuesAsString;
	}

	public Map<String, Boolean> getAttributeMandatory() {
		return attributeMandatory;
	}

	/**
	 * Set the DocumentType, make specific attributes available to the SearchForm.
	 * 
	 * @param pDocType
	 */
	public void setDocumentTypes(List<DownloadDocumentType> pDocTypes) {
		documentTypes = pDocTypes;
	}

	/**
	 * Get the corresponding DocumentType object.
	 * 
	 * @return
	 */
	public List<DownloadDocumentType> getDocumentTypes() {
		return documentTypes;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public boolean isCustomerNumberUsed() {
		return isCustomerNumberUsed;
	}

	private void initCustomerNumber(boolean isCustomerNumberUsed, String lastCustomerNumberFromSession) {
		this.isCustomerNumberUsed |= isCustomerNumberUsed;
		if (isCustomerNumberUsed) {
			customers = UserInSession.get().getCustomers();
			if (StringTool.isTrimmedEmptyOrNull(getCustomerNumber()) && !StringTool.isTrimmedEmptyOrNull(lastCustomerNumberFromSession)) {
				Doc41Log.get().debug(this, null, "Initialize Form form Session (customer: " + lastCustomerNumberFromSession + ")");
				setCustomerNumber(lastCustomerNumberFromSession);
			}
		}
	}

	public List<SapCustomer> getCustomers() {
		return customers;
	}

	public String getVendorNumber() {
		return vendorNumber;
	}

	public void setVendorNumber(String vendorNumber) {
		vendorNumber = StringTool.trim(vendorNumber);
		if ((StringTool.emptyToNull(vendorNumber) != null) && (vendorNumber.length() != 10)) {
			vendorNumber = StringTool.normLString(vendorNumber, 10, '0');
		}
		this.vendorNumber = vendorNumber;
	}

	public boolean isVendorNumberUsed() {
		return isVendorNumberUsed;
	}

	private void initVendorNumber(boolean isVendorNumberUsed, String lastVendorNumberFromSession) {
		this.isVendorNumberUsed |= isVendorNumberUsed;
		if (isVendorNumberUsed) {
			vendors = UserInSession.get().getVendors();
			if (StringTool.isTrimmedEmptyOrNull(getVendorNumber()) && !StringTool.isTrimmedEmptyOrNull(lastVendorNumberFromSession)) {
				Doc41Log.get().debug(this, null, "Initialize Form form Session (vendor: " + lastVendorNumberFromSession + ")");
				setVendorNumber(lastVendorNumberFromSession);
			}
		}
	}

	public List<SapVendor> getVendors() {
		return vendors;
	}

	public void initPartnerNumbers(boolean isCustomerNumberUsed, String lastCustomerNumberFromSession, boolean isVendorNumberUsed, String lastVendorNumberFromSession) {
		initCustomerNumber(isCustomerNumberUsed, lastCustomerNumberFromSession);
		initVendorNumber(isVendorNumberUsed, lastVendorNumberFromSession);
	}

	public void initAttributes(List<Attribute> attributeDefinitions, String languageCode) {
		attributeLabels = new LinkedHashMap<String, String>();
		Map<String, String> oldAttributeValuesMap = null;
		if (attributeValues != null) {
			oldAttributeValuesMap = attributeValues;
		}
		attributeValues = new LinkedHashMap<String, String>();
		attributePredefValues = new HashMap<String, List<String>>();
		attributePredefValuesAsString = new HashMap<String, String>();
		attributeMandatory = new HashMap<String, Boolean>();
		for (Attribute attribute : attributeDefinitions) {
			String key = attribute.getName();
			String label = attribute.getTranslation(languageCode);
			attributeLabels.put(key, label);
			if (oldAttributeValuesMap == null || !oldAttributeValuesMap.containsKey(key)) {
				attributeValues.put(key, "");
			} else {
				attributeValues.put(key, oldAttributeValuesMap.remove(key));
			}
			List<String> predefValues = attribute.getValues();
			attributePredefValues.put(key, predefValues);
			attributeMandatory.put(key, attribute.getMandatory());
			String predefValuesAsString = null;
			if (predefValues != null) {
				StringBuilder sb = new StringBuilder();
				for (String oneValue : predefValues) {
					if (sb.length() > 0) {
						sb.append("###");
					}
					sb.append(oneValue);
				}
				predefValuesAsString = sb.toString();
			}
			attributePredefValuesAsString.put(key, predefValuesAsString);
		}
		if (oldAttributeValuesMap != null && !oldAttributeValuesMap.isEmpty()) {
			attributeValues.putAll(oldAttributeValuesMap);
		}
	}

	@Override
	public String toString() {
		return "CustomizedDocumentForm [type=" + type + ", objectId=" + objectId + ", attributeLabels=" + attributeLabels + ", attributeValues=" + attributeValues + ", attributePredefValues=" + attributePredefValues + ", attributeMandatory=" + attributeMandatory + ", viewAttributes=" + viewAttributes + ", customerNumber=" + customerNumber + ", isCustomerNumberUsed=" + isCustomerNumberUsed + ", customers=" + customers + ", vendorNumber=" + vendorNumber + ", isVendorNumberUsed=" + isVendorNumberUsed + ", vendors=" + vendors + "]";
	}

	public List<String> getCustomizedValuesLabels() {
		Collection<String> labels = attributeLabels.values();
		return new ArrayList<String>(labels);
	}

	public int getCustColPercent() {
		int colCount = getCustAttributeCount();
		return 50 / colCount;
	}

	public int getCustAttributeCount() {
		return attributeLabels.size();
	}

	public Map<String, String> getViewAttributes() {
		return viewAttributes;
	}

	public String getVersionIdBom() {
		return versionIdBom;
	}

	public void setVersionIdBom(String versionIdBom) {
		this.versionIdBom = versionIdBom;
	}

	/**
	 * Getter for time frame input.
	 * 
	 * @return time frame
	 */
	public Date getTimeFrame() {
		return timeFrame;
	}

	/**
	 * Setter for time frame input.
	 * 
	 * @param timeFrame
	 *            - time frame.
	 */
	public void setTimeFrame(Date timeFrame) {
		this.timeFrame = timeFrame;
	}
}
