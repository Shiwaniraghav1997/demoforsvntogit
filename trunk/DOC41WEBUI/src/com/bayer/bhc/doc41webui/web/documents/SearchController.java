package com.bayer.bhc.doc41webui.web.documents;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.container.SearchForm;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.StringTool;

@Controller
public class SearchController extends AbstractDoc41Controller {
	
	private static final int MAX_RESULTS = 100;

	@Autowired
	private DocumentUC documentUC;
	
	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) {
		//TODO remove BOL
		String type = request.getParameter("type");
		if(StringTool.isTrimmedEmptyOrNull(type)){
			return false;
		} else if(type.equals(Doc41Constants.DOC_TYPE_BOL)){
			return usr.hasPermission(Doc41Constants.PERMISSION_DOC_BOL_UP);
		} else if(type.equals(Doc41Constants.DOC_TYPE_COO)){
			return usr.hasPermission(Doc41Constants.PERMISSION_DOC_COO_DOWN);
		} else {
			throw new IllegalArgumentException("unknown type: "+type);
		}
    }
	
	@RequestMapping(value="/documents/documentsearch",method = RequestMethod.GET)
	public SearchForm get(@ModelAttribute SearchForm searchForm,BindingResult result) throws Doc41BusinessException{
		String language = LocaleInSession.get().getLanguage();
		String type = searchForm.getType();
		if(StringTool.isTrimmedEmptyOrNull(type)){
			throw new Doc41BusinessException("typeIsMissing");
		}
		List<Attribute> attributeDefinitions = documentUC.getAttributeDefinitions(type);
		searchForm.initAttributes(attributeDefinitions,language);
		
		if(searchForm.isSearchFilled()){
			List<HitListEntry> documents = documentUC.searchDocuments(type, StringTool.emptyToNull(searchForm.getObjectId()), searchForm.getAttributeValues(), MAX_RESULTS+1, true);
			if(documents.size()>MAX_RESULTS){
				result.rejectValue("table", "ToManyResults");
			} else {
				searchForm.setDocuments(documents);
			}
		}
		
		return searchForm;
	}
	
	@RequestMapping(value="/documents/download",method = RequestMethod.GET)
	public void download(@RequestParam String type, @RequestParam String docId,HttpServletResponse response) throws Doc41BusinessException{
		documentUC.downloadDocument(response,type,docId);
	}
	
}
