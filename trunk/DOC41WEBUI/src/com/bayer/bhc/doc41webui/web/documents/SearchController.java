package com.bayer.bhc.doc41webui.web.documents;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.container.SearchForm;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class SearchController extends AbstractDoc41Controller {

	@Autowired
	private DocumentUC documentUC;
	
	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) {
		return true;
		//TODO
//		String type = request.getParameter("type");
//		if(StringTool.isTrimmedEmptyOrNull(type)){
//			return false;
//		} else if(type.equals(Doc41Constants.DOC_TYPE_BOL)){
//			return usr.hasPermission(Doc41Constants.PERMISSION_DOC_BOL_UP);
//		} else if(type.equals(Doc41Constants.DOC_TYPE_AIRWAY)){
//			return usr.hasPermission(Doc41Constants.PERMISSION_DOC_AWB_UP);
//		} else {
//			throw new IllegalArgumentException("unknown type: "+type);
//		}
    }
	
	@RequestMapping(value="/documents/documentsearch",method = RequestMethod.GET)
	public SearchForm get(@RequestParam() String type) throws Doc41BusinessException{
		String language = LocaleInSession.get().getLanguage();
		SearchForm searchForm = new SearchForm();
		searchForm.setType(type);
		List<Attribute> attributeDefinitions = documentUC.getAttributeDefinitions(type);
		searchForm.initAttributes(attributeDefinitions,language);
		return searchForm;
	}
	
	@RequestMapping(value="/documents/search",method = RequestMethod.POST)
	public String search(@ModelAttribute SearchForm searchForm,BindingResult result) {
		//TODO
		System.err.println("search");
		return "/documents/documentsearch";
	}
	
	@RequestMapping(value="/documents/search",method = RequestMethod.POST, params="reset")
	public String reset(@ModelAttribute SearchForm searchForm,BindingResult result) {
		//TODO
		System.err.println("reset");
		return "/documents/documentsearch";
	}
	
	
}
