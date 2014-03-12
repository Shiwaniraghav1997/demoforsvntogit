package com.bayer.bhc.doc41webui.web.maintenance;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.TranslationsUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.bhc.doc41webui.web.Doc41Tags;

@Controller
public class UntranslatedLabelsController extends AbstractDoc41Controller {
	
	@Autowired
    private TranslationsUC translationsUC;
	
	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) {
		return usr.hasPermission(Doc41Constants.PERMISSION_UNTRANSLATEDLABELS);
	}
	
	@RequestMapping(value="/maintenance/untranslatedLabels", method=RequestMethod.GET)
    public void get(ModelMap model) throws Doc41TechnicalException {
		// Untranslated Labels
		StringBuilder sb = new StringBuilder();
		Map<String, Set<String>> untranslatedLabels = Doc41Tags.getUntranslatedLabels();
		for (Entry<String, Set<String>> oneLanguageEntry : untranslatedLabels.entrySet()) {
		    String language = oneLanguageEntry.getKey();
            sb.append("<hr>\n");
			sb.append("Language: ");
			sb.append(language);
			sb.append("<br>\n");
			sb.append("<hr>\n");
			sb.append("<ul>\n");
			Set<String> untranslatedLabelsSet = oneLanguageEntry.getValue();
			for (String label : untranslatedLabelsSet) {
				//<li><a href="../translations/translationAdd?tagName=label&language=language">label</a></li>
				sb.append("<li><a href='../translations/translationAdd?tagName=");
				sb.append(label);
				sb.append("&language=");
				sb.append(language);
				sb.append("'>");
				sb.append(label);
				sb.append("</a></li>\n");
			}
			sb.append("</ul>\n");
		}
		
		
		Form form = new Form();
		form.setUntranslatedLabels(untranslatedLabels);
		form.setEditable(translationsUC.isEditable());
		model.addAttribute("command", form);
    }
	
	@RequestMapping(value="/maintenance/untranslatedLabels/reset",method = RequestMethod.POST)
	public String reset() {
		Doc41Tags.getUntranslatedLabels().clear();
		return "redirect:/maintenance/untranslatedLabels";
	}

	
	
	public static class Form {
		private Map<String, Set<String>> untranslatedLabels;
		private boolean editable;

		public Set<String> getLanguages(){
			return untranslatedLabels.keySet();
		}
		public Map<String, Set<String>> getUntranslatedLabels() {
			return untranslatedLabels;
		}

		public void setUntranslatedLabels(Map<String, Set<String>> untranslatedLabels) {
			this.untranslatedLabels = untranslatedLabels;
		}
		
		public boolean isEditable(){
			return editable;
		}
		
		public void setEditable(boolean editable) {
			this.editable = editable;
		}
	}
}
