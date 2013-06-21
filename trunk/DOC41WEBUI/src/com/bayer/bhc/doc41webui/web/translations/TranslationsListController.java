/**
 * File:TranslationsListController.Java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.translations;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.common.paging.TableSorterParams;
import com.bayer.bhc.doc41webui.common.paging.TablesorterPagingData;
import com.bayer.bhc.doc41webui.container.TranslationPagingRequest;
import com.bayer.bhc.doc41webui.container.TranslationsForm;
import com.bayer.bhc.doc41webui.domain.Translation;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.service.repository.TranslationsRepository;
import com.bayer.bhc.doc41webui.usecase.TranslationsUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.ConfigMap;

/**
 * Controller to manage Translations view related requests using Translations Usecase.
 * @author ezzqc,evayd
 * 
 */
@Controller
public class TranslationsListController extends AbstractDoc41Controller {
 
    //Constant variables
//    private static final String TRANSLATIONS_LIST= "translationList";
    private static final String LANGUAGE_CODES = "languageCodes";
    private static final String COUNTRY_CODES = "countryCodes";
    private static final String JSP_LIST = "pageList";
    private static final String COMPONENT_LIST = "componentList";
	private static final String[] DB_COL_NAMES = {"MANDANT","COMPONENT","JSP_NAME","TAG_NAME","LANGUAGE_CODE","COUNTRY_CODE","TAG_VALUE"};
	private static final String OBJECTID="objectID";
	
    @Autowired
    private TranslationsUC translationsUC;
    
    @ModelAttribute(LANGUAGE_CODES)
	public Map<String, String> addLanguageCodes(){
		return translationsUC.getLanguageCodes();
	}
    @ModelAttribute(COUNTRY_CODES)
	public Map<String, String> addCountryCodes(){
		return translationsUC.getCountryCodes();
	}
    
    @ModelAttribute(COMPONENT_LIST)
   	public List<String> addComponentList() throws Doc41ExceptionBase{
   		return this.translationsUC.getComponentList();
   	}

    @ModelAttribute("editable")
	public Boolean isEditable() {
        @SuppressWarnings("unchecked")
		Map<Object,Object> fdtConfig = ConfigMap.get().getSubConfig(TranslationsRepository.COMPONENT_KEY, TranslationsRepository.CONFIG_KEY);
        if (fdtConfig != null && "true".equals(fdtConfig.get("enabled"))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    
    @ModelAttribute(JSP_LIST)
    public List<String> getJspList() throws Doc41ExceptionBase{
    	return this.translationsUC.getPageList(null);
    }
    
	protected boolean hasRolePermission(User usr) {
    	return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
    }
	
	@RequestMapping(value="/translations/translationOverview",method=RequestMethod.GET)
    protected void get(HttpServletRequest request,Map<String,Object> model) throws Exception {
        
    }
	
	@RequestMapping(value="/translations/jsontable*", method=RequestMethod.GET,produces="application/json") 
    @ResponseBody
    public Map<String, Object> getTable(HttpServletRequest request,TableSorterParams params) throws Doc41ExceptionBase {
		TranslationsForm translationsForm = new TranslationsForm();
		translationsForm.setMandant(params.getFilter(0));
		translationsForm.setComponent(params.getFilter(1));
		translationsForm.setJspName(params.getFilter(2));
		translationsForm.setTagName(params.getFilter(3));
		translationsForm.setLanguage(params.getFilter(4));
		translationsForm.setCountry(params.getFilter(5));
		translationsForm.setTagValue(params.getFilter(6));
		
		translationsForm.setOrderBy(params.getSortColumn(DB_COL_NAMES));
		
        PagingResult<Translation> result = this.translationsUC.findTags(new TranslationPagingRequest(translationsForm,new TablesorterPagingData(params.getPage(),params.getSize())));
		List<Translation> list = result.getResult();
		List<String[]> rows = new ArrayList<String[]>();
		if(list.isEmpty()){
			String[] row = new String[]{"","","","not found","","",""};
			rows.add(row);
		} else {
			for (Translation translation : list) {
				String[] row = new String[9];
				row[0]= translation.getMandant();
				row[1]= translation.getComponent();
				row[2]= translation.getJspName();
				row[3]= translation.getTagName();
				row[4]= translation.getLanguage();
				row[5]= translation.getCountry();
				row[6]= translation.getTagValue();
				//TODO move HTML to JSP or JS
				row[7]= "<a onclick=\"sendGet('translations/translationEdit', 'objectID="+translation.getDcId()+"')\" href=\"#\"><img src='"+request.getContextPath()+"/resources/img/common/page_edit.gif'/></a>";
				row[8]= "<a onclick=\"sendPostAfterCheck('Do you really want to delete this Item?', 'deletetranslation', 'objectID="+translation.getDcId()+"')\" href=\"#\"><img src='"+request.getContextPath()+"/resources/img/common/trash.png'/></a>";
				rows.add(row);
			}
		}
		
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total_rows", result.getTotalSize());
//        map.put("headers", HEADERS);
        map.put("rows",rows);

        return map;
    }
	
	//DELETE TRANSLATION
	
	@RequestMapping(value="/translations/deletetranslation",method=RequestMethod.POST)
	public String deleteTranslation(@RequestParam(value=OBJECTID) Long tagId) throws Doc41ExceptionBase{
		if(tagId==null){
			return "/translations/translationOverview";
		}
		translationsUC.deleteTagById(tagId);
        
        return "redirect:/translations/translationOverview";
	}
	
    
	
	
	//TODO Distribute Post
	
	//extra Controller: Edit, Add
}
