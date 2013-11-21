package com.bayer.bhc.doc41webui.web.maintenance;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.bhc.doc41webui.web.Doc41Tags;

@Controller
public class UntranslatedLabelsController extends AbstractDoc41Controller {
	
	
	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) {
		return usr.hasPermission(Doc41Constants.PERMISSION_UNTRANSLATEDLABELS);
	}
	
	@RequestMapping(value="/maintenance/untranslatedLabels", method=RequestMethod.GET)
    public void get(ModelMap model) throws Doc41TechnicalException {
		// Untranslated Labels
		Set<String> untranslatedLabelsSet = Doc41Tags.getUntranslatedLabels();
		StringBuilder sb = new StringBuilder();
		for (String label : untranslatedLabelsSet) {
			sb.append(label + "\n");
		}
		
		Form form = new Form();
		form.setUntranslatedLabels(sb.toString());
		model.addAttribute("command", form);
    }
	
	@RequestMapping(value="/maintenance/untranslatedLabels/reset",method = RequestMethod.POST)
	public String reset() {
		Doc41Tags.getUntranslatedLabels().clear();
		return "redirect:/maintenance/untranslatedLabels";
	}

	
	
	public static class Form {
		private String untranslatedLabels;

		public String getUntranslatedLabels() {
			return untranslatedLabels;
		}

		public void setUntranslatedLabels(String untranslatedLabels) {
			this.untranslatedLabels = untranslatedLabels;
		}
	}
}
