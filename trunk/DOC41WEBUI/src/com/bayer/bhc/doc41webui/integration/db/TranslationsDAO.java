/**
 * File:TranslationsDAO.Java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.integration.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.container.TranslationPagingRequest;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.business.sbcommon.SBTranslationsSingleton;
import com.bayer.ecim.foundation.business.sbcommon.SBeanException;
import com.bayer.ecim.foundation.business.sbcommon.TranslationsDC;

/**
 * Implementaion of Translations Data Access object to handle all required database transactions for Translation portlet.
 * 
 * @author MBGHY Aug 11, 2008
 * @id $Id: TranslationsDAO.java,v 1.3 2012/03/07 17:44:22 ezzqc Exp $
 */
@Component
public class TranslationsDAO extends AbstractDAOImpl {
	private static final String TEMPLATE_COMPONENT_NAME	= "CMN";
	private static final String TEMPLATE_SUB_COMPONENT_NAME	= "translations";
	
	@Override
	public String getTemplateComponentName() {		
		return TEMPLATE_COMPONENT_NAME;
	}
	
	@Override
	public String getTemplateSubComponentName() {
		return TEMPLATE_SUB_COMPONENT_NAME;
	}

	public  static final String SYSTEM_ID = "DOC41";

    private static final String MANDANT = "MANDANT";
    private static final String COMPONENT = "COMPONENT";
    private static final String JSP_NAME = "JSP_NAME";
    private static final String LANGUAGE_CODE = "LANGUAGE_CODE";
    private static final String COUNTRY_CODE = "COUNTRY_CODE";
    private static final String TRANSLATIONS_OVERVIEW_QUERY = "GetTranslationData";
    private static final String TAG_NAME ="TAG_NAME";
    private static final String IS_TAG_EXIST_QUERY = "isTagExist";
	private static final String TAG_VALUE = "TAG_VALUE";

    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.TranslationsDAO#findTags(com.bayer.bhc.doc41webui.domain.container.TranslationPagingRequest)
     */
    public PagingResult<TranslationsDC> findTags(TranslationPagingRequest pagingRequest) throws Doc41TechnicalException {
        Doc41Log.get().debug(TranslationsDAO.class, null, "findTags():ENTRY ");
        String[] parameterNames = { MANDANT, COMPONENT, JSP_NAME, LANGUAGE_CODE, COUNTRY_CODE,TAG_NAME,TAG_VALUE };
        Object[] parameterValues = { SYSTEM_ID, pagingRequest.getComponent(),
                pagingRequest.getJspName(), pagingRequest.getLanguage(),pagingRequest.getCountryCode(),
                pagingRequest.getTagName(),pagingRequest.getTagValue() };
        return find(parameterNames, parameterValues, TRANSLATIONS_OVERVIEW_QUERY,
                TranslationsDC.class, pagingRequest.getTotalSize(), pagingRequest.getStartIndex(),
                pagingRequest.getEndIndex());
    }

    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.TranslationsDAO#findTags4Component(java.lang.String, java.lang.String)
     */
    public List<TranslationsDC> findTags4Component(String component, String language) throws Doc41TechnicalException{
        Doc41Log.get().debug(TranslationsDAO.class, null, "findTags():ENTRY ");
        String[] parameterNames = { MANDANT, COMPONENT, JSP_NAME, LANGUAGE_CODE, COUNTRY_CODE };
        Object[] parameterValues = { SYSTEM_ID, component,"*",language, "*"};
        return find(parameterNames, parameterValues, TRANSLATIONS_OVERVIEW_QUERY,TranslationsDC.class);
    }
    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.TranslationsDAO#findTags4Component(java.lang.String, java.lang.String, java.lang.String)
     */
    public List<TranslationsDC> findTags4Component(String component, String language, String tagName) throws Doc41TechnicalException {
    	Doc41Log.get().debug(TranslationsDAO.class, null, "findTags():ENTRY ");
        String[] parameterNames = { MANDANT, COMPONENT, JSP_NAME, LANGUAGE_CODE, COUNTRY_CODE, TAG_NAME};
        Object[] parameterValues = { SYSTEM_ID, component,"*",language, "*", tagName};
        return find(parameterNames, parameterValues, TRANSLATIONS_OVERVIEW_QUERY,TranslationsDC.class);
    }
    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.TranslationsDAO#storeTag(com.bayer.ecim.foundation.business.sbcommon.TranslationsDC)
     */
    public void storeTag(TranslationsDC translationDC) throws Doc41TechnicalException{
        Doc41Log.get().debug(TranslationsDAO.class, null, "storeTag():ENTRY ");
        translationDC.setMandant(SYSTEM_ID);
           store(translationDC);
        Doc41Log.get().debug(TranslationsDAO.class, null, "storeTag():EXIT ");
    }

    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.TranslationsDAO#getTagById(java.lang.Long)
     */
    public TranslationsDC getTagById(Long pObjectId) throws Doc41TechnicalException{
        Doc41Log.get().debug(TranslationsDAO.class, null, "getTagById():ENTRY ");
            return find(TranslationsDC.class, pObjectId);
    }

    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.TranslationsDAO#getComponentList()
     */
    @SuppressWarnings("unchecked")
	public List<TranslationsDC> getComponentList()  throws Doc41TechnicalException{
        Doc41Log.get().debug(TranslationsDAO.class, null, "getComponentList():ENTRY ");
        List<TranslationsDC> componentList = new ArrayList<TranslationsDC>();
        try {
            componentList = SBTranslationsSingleton.get().getSBTranslations().getTRComponentList(SYSTEM_ID, null);
        } catch (InitException e) {
            throw new Doc41TechnicalException(TranslationsDAO.class,"TranslationsDAO.getComponentList",e);
        } catch (SBeanException e) {
            throw new Doc41TechnicalException(TranslationsDAO.class,"TranslationsDAO.getComponentList",e);
        }
        Doc41Log.get().debug(TranslationsDAO.class, null, "getComponentList():EXIT ");
        return componentList;
    }

    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.TranslationsDAO#getPageList(java.lang.String)
     */
    @SuppressWarnings("unchecked")
	public List<TranslationsDC> getPageList(String pComponent) throws Doc41TechnicalException {
        Doc41Log.get().debug(TranslationsDAO.class, null, "getPageList():ENTRY ");
        List<TranslationsDC> jspList = new ArrayList<TranslationsDC>();
        try {
            jspList = SBTranslationsSingleton.get().getSBTranslations().getTRJSPNameList(SYSTEM_ID, pComponent, null);
        } catch (InitException e) {
            throw new Doc41TechnicalException(TranslationsDAO.class,"TranslationsDAO.getPageList",e);
        } catch (SBeanException e) {
            throw new Doc41TechnicalException(TranslationsDAO.class,"TranslationsDAO.getPageList",e);
        }
        Doc41Log.get().debug(TranslationsDAO.class, null, "getPageList():ENTRY ");
        return jspList;
    }

    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.TranslationsDAO#distributeTags()
     */
    public String distributeTags() throws Doc41TechnicalException{
        Doc41Log.get().debug(TranslationsDAO.class, null, "distributeTags:ENTRY ");
        String mts = null;
        try {
            mts = SBTranslationsSingleton.get().getSBTranslations()
                    .distributeTags(1, MANDANT, null);
        } catch (SBeanException e) {
            throw new Doc41TechnicalException(TranslationsDAO.class,
                    "TranslationsDAO.distributeTags", e);
        }
        Doc41Log.get().debug(TranslationsDAO.class, null, "distributeTags:EXIT ");
        return mts;
    }

    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.TranslationsDAO#deleteTagById(java.lang.Long)
     */
    public void deleteTagById(Long tagId) throws Doc41TechnicalException {
        Doc41Log.get().debug(TranslationsDAO.class, null, "deleteTagById():ENTRY ");
        deleteById(TranslationsDC.class, tagId);
        Doc41Log.get().debug(TranslationsDAO.class, null, "deleteTagById():EXIT ");
    }

    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.TranslationsDAO#isTagExist(com.bayer.ecim.foundation.business.sbcommon.TranslationsDC)
     */
    public int isTagExist(TranslationsDC dc) throws Doc41TechnicalException{
        Doc41Log.get().debug(TranslationsDAO.class, null, "isTagExist():ENTRY ");
        String[] parameterNames = { MANDANT, COMPONENT, JSP_NAME, LANGUAGE_CODE, COUNTRY_CODE, TAG_NAME, };
        Object[] parameterValues = { SYSTEM_ID, dc.getComponent(), dc.getJSPName(),
                dc.getLanguageCode(), dc.getCountryCode(), dc.getTagName() };
        return count(parameterNames, parameterValues, IS_TAG_EXIST_QUERY);
    }
}
