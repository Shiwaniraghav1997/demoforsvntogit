package com.bayer.bhc.doc41webui.web.maintenance;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPSingleton;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.InitException;
import com.sap.conn.jco.JCoException;

@Controller
public class RFCMetadataController extends AbstractDoc41Controller {

	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) {
		return usr.hasPermission(Doc41Constants.PERMISSION_RFCMETADATA);
	}
	
	@RequestMapping(value="/maintenance/rfcMetadata", method=RequestMethod.GET)
    public void get(ModelMap model, @RequestParam(value="rfcName",required=false) String rfcName) throws Doc41TechnicalException {
		// RFC Names Selection Box
		Set<String> rfcNamesSet = SAPSingleton.get().getRFCNames();
		List<SelectionItem> rfcNames = new ArrayList<SelectionItem>();
		for (String tmpRfcName : rfcNamesSet) {
			rfcNames.add(new SelectionItem(tmpRfcName, tmpRfcName));
		}

		// RFC Dump
		String rfcDump = "";
		try {
			if (!StringUtils.isBlank(rfcName)) {
				rfcDump = SAPSingleton.get().dumpMetadata(rfcName);
			}
		} catch (InitException e) {
			rfcDump = "InitException:\n" + e.getMessage() + "\n\n" + ExceptionUtils.getStackTrace(e);
		} catch (SAPException e) {
			rfcDump = "SAPException:\n" + e.getMessage() + "\n\n" + ExceptionUtils.getStackTrace(e);
		} catch (JCoException e) {
			rfcDump = "JCoException:\n" + e.getMessage() + "\n\n" + ExceptionUtils.getStackTrace(e);
		} catch (Exception e) {
			rfcDump = "Exception:\n" + e.getMessage() + "\n\n" + ExceptionUtils.getStackTrace(e);
		}
		
		Form form = new Form();
		form.setRfcName(rfcName);
		form.setRfcNames(rfcNames);
		form.setRfcDump(rfcDump);
		model.addAttribute("command", form);
    }
	
	@RequestMapping(value="/maintenance/selectRFCName", method=RequestMethod.POST)
	 public String select(HttpServletRequest request, @ModelAttribute Form form, BindingResult result) {
		return "redirect:/maintenance/rfcMetadata?rfcName="+form.getRfcName();
	}

	
	
	public static class Form {
		private String rfcName;
		private List<SelectionItem> rfcNames;
		private String rfcDump;
		
		public String getRfcName() {
			return rfcName;
		}

		public void setRfcName(String rfcName) {
			this.rfcName = rfcName;
		}

		public List<SelectionItem> getRfcNames() {
			return rfcNames;
		}

		public void setRfcNames(List<SelectionItem> rfcNames) {
			this.rfcNames = rfcNames;
		}

		public String getRfcDump() {
			return rfcDump;
		}

		public void setRfcDump(String rfcDump) {
			this.rfcDump = rfcDump;
		}
	}
}
