/**
 * File:TranslationsViewController.Java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.translations;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.PagingForm;
import com.bayer.bhc.doc41webui.container.TranslationPagingRequest;
import com.bayer.bhc.doc41webui.container.TranslationsForm;
import com.bayer.bhc.doc41webui.domain.Translation;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.service.repository.TranslationsRepository;
import com.bayer.bhc.doc41webui.usecase.TranslationsUC;
import com.bayer.bhc.doc41webui.web.PagingListController;
import com.bayer.ecim.foundation.basic.ConfigMap;

/**
 * Controller to manage Translations view related requests using Translations Usecase.
 * @author ezzqc
 * 
 */
public class TranslationsViewController extends PagingListController {
 
    //Constant variables
    private static final String COMMAND = "transView";
    private static final String RENDER_VIEW = "viewTrans";
    private static final String TRANSLATIONS_LIST= "translationList";
    private static final String LANGUAGE_CODES = "languageCodes";
    private static final String JSP_LIST = "pageList";
    private static final String COMPONENT_LIST = "componentList";
    private static final String REFRESH = "refresh";
    private static final String TECH_ADMIN = "ccp_tadm";
    
    
    private TranslationsUC translationsUC;
    
	public TranslationsUC getTranslationsUC() {
		return translationsUC;
	}

	public void setTranslationsUC(TranslationsUC translationsUC) {
		this.translationsUC = translationsUC;
	}

	private final Boolean isEditable() {
        @SuppressWarnings("unchecked")
		Map<Object,Object> fdtConfig = ConfigMap.get().getSubConfig(TranslationsRepository.COMPONENT_KEY, TranslationsRepository.CONFIG_KEY);
        if (fdtConfig != null && "true".equals(fdtConfig.get("enabled"))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    
	protected boolean hasRolePermission(User usr) {
    	return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
    }
	
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        TranslationsForm command = (TranslationsForm) request.getSession().getAttribute(COMMAND);
        if (command == null) {
        	command = new TranslationsForm();
            request.getSession().setAttribute(COMMAND, command);
        }
        command.setCommand("");
        return command;
    }
    
    protected ModelAndView getListModel(PagingForm pform, HttpServletRequest request, Object command) throws Exception {
        TranslationsForm filter = (TranslationsForm) command;
        verifyTagsTransferPermission(request,filter);
        
        if(isResetRequest(filter)){
        	filter.reset();
        }
        if(REFRESH.equals(filter.getCommand())){
            filter.setJspName(null);
        }
        pform.setActionName(RENDER_VIEW);
        PagingResult<Translation> result = this.translationsUC.findTags(new TranslationPagingRequest(filter,pform));
        pform.setTotalSize(result.getTotalSize());

        return new ModelAndView().addObject(TRANSLATIONS_LIST,result.getResult())
                                    .addObject(LANGUAGE_CODES,this.translationsUC .getLanguageCodes())
                                        .addObject(JSP_LIST,this.translationsUC .getPageList(filter.getComponent()))
                                            .addObject(COMPONENT_LIST,this.translationsUC.getComponentList())
                                                .addObject("editable", isEditable());
    }
  
    /**
	 * Verfies User roles to Transfer Tags to Production, if user having Technical Admin role,
	 *  then user allowed to transfer tags, else not.
	 * @param request <code>PortletRequest</code>
	 * @param filter <code>TranslationsForm</code>
     * @throws Exception <code>Exception</code> to be thrown,if error occures
	 */
	private void verifyTagsTransferPermission(HttpServletRequest request, TranslationsForm filter) throws Exception {
		if(request.getRemoteUser()!=null){
			User user=UserInSession.get();
			if(!user.getRoles().isEmpty()&&user.getRoles().contains(TECH_ADMIN)){
				//user is part of Tech Admin group.
				filter.setIsTechAdmin(Boolean.TRUE);
			}else{
				//user is not part of Tech Admin group.
				filter.setIsTechAdmin(Boolean.FALSE);
			}
		}
	}
}
