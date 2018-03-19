/**
 * File:TranslationsRepositoryImpl.Java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.service.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.Doc41ErrorMessageKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41RepositoryException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.container.TranslationPagingRequest;
import com.bayer.bhc.doc41webui.domain.Translation;
import com.bayer.bhc.doc41webui.integration.db.TranslationsDAO;
import com.bayer.bhc.doc41webui.service.mapping.TranslationsMapper;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.business.sbcommon.TranslationsDC;


/**
 * Implementaion for Translation repository.
 * @author ezzqc
 */
@Component
public  class TranslationsRepository extends AbstractRepository {

    public static final String COMPONENT_KEY = "translation";

    public static final String CONFIG_KEY = "edit";
    
    /**
     *  translationsDAO spring managed bean The <code>TranslationsDAO </code>
     */
    @Autowired
    private TranslationsDAO translationsDAO;
    @Autowired
    private TranslationsMapper translationsMapper;

    @Resource
	private  Map<String, String> languageCodes;
    @Resource
	private  Map<String, String> countryCodes;
    

    /**
     * Find's <code>Translation </code> Tags based on different input parameters.
     * @param pagingRequest The <code>TranslationPagingRequest </code> object.
     * @return <code>PagingResult</code> countaing The <code>Translation </code> objects.
     * @throws The <code>Doc41RepositoryException</code> to be thrown when error occurs
     */
    public PagingResult<Translation> findTags(TranslationPagingRequest pagingRequest) throws Doc41RepositoryException  {
        // Get the TranslationDc's list from DB
        try {
        	PagingResult<TranslationsDC> dcList = this.translationsDAO.findTags(pagingRequest);
        	List<Translation> tagsList = new ArrayList<Translation>();
            // Mapping from TranslationDC to Translation Domian.
        	for (TranslationsDC dc : dcList.getResult()) {
                tagsList.add(getTranslationsMapper().mapToDO(dc, new Translation()));
            }
            return new PagingResult<Translation>(tagsList,dcList.getTotalSize());
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_FIND_TAGS_FAILED, e);
        }
    }

    /**
     * Adds new <code>Translation </code> Tag in Database.
     * @param translation The <code>Translation </code> object to be added.
     * @throws The <code>Doc41RepositoryException</code> to be thrown when error occurs
     */
    public void createTag(Translation translation) throws Doc41RepositoryException {
        @SuppressWarnings("unchecked")
		Map<String,String> fdtConfig = ConfigMap.get().getSubConfig(COMPONENT_KEY, CONFIG_KEY);
        if (fdtConfig == null || fdtConfig.isEmpty() || !"true".equals(fdtConfig.get("enabled"))) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_CREATE_TAG_FAILED, null);
        }
        
        try {
            this.translationsDAO.storeTag(getTranslationsMapper().mapToDC(translation, TranslationsDC.newInstanceOfTranslationsDC()));
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_CREATE_TAG_FAILED,e);
        }
    }

    /**
     * saves the edited <code>Translation</code> Tag in Database.
     * @param translation The <code>Translation </code> object to be saved.
     * @throws The <code>Doc41RepositoryException</code> to be thrown when error occurs
     */
    public void editTag(Translation pTranslation) throws Doc41RepositoryException {
        TranslationsDC transDC = null;
        
        @SuppressWarnings("unchecked")
		Map<String,String> fdtConfig = ConfigMap.get().getSubConfig(COMPONENT_KEY, CONFIG_KEY);
        if (fdtConfig == null || fdtConfig.isEmpty() || !"true".equals(fdtConfig.get("enabled"))) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_EDIT_TAG_FAILED, null);
        }
        try {
            transDC = this.translationsDAO.getTagById(pTranslation.getDcId());
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_EDIT_TAG_FAILED, e);
        }
        if (transDC != null) {
            transDC.setTagName(pTranslation.getTagName());
            transDC.setTagValue(pTranslation.getTagValue());
            transDC.setChangedBy(pTranslation.getChangedBy());
            try {
                this.translationsDAO.storeTag(transDC);
            } catch (Doc41TechnicalException e) {
                throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_EDIT_TAG_FAILED, e);
            }
        }
    }

    /**
     * Deletes the <code>Translation</code> by ObjectId.
     * @param tagId the <code>Long</code>
     * @throws The <code>Doc41RepositoryException</code> to be thrown when error occurs
     */
    public void deleteTagById(Long tagId) throws Doc41RepositoryException {
        @SuppressWarnings("unchecked")
		Map<String,String> fdtConfig = ConfigMap.get().getSubConfig(COMPONENT_KEY, CONFIG_KEY);
        if (fdtConfig == null || fdtConfig.isEmpty() || !"true".equals(fdtConfig.get("enabled"))) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_DELETE_TAG_FAILED, null);
        }
        
        try {
            this.translationsDAO.deleteTagById(tagId);
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_DELETE_TAG_FAILED,e);
        }
    }

    /**
     * Returns the <code>Translation</code>Tag based on The <code>objectID</code>.
     * @param Long The <code>objectID</code>
     * @return The <code>Translation </code> containg one row
     * @throws The <code>Doc41RepositoryException</code> to be thrown when error occurs
     */
    public Translation getTagById(Long objectID) throws Doc41RepositoryException {
        try {
            return getTranslationsMapper().mapToDO(this.translationsDAO.getTagById(objectID), new Translation());
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_GET_TAG_FAILED,e);
        }
    }

    /**
     * @param languageCodes the languageCodes to set
     */
    public void setLanguageCodes(Map<String, String> languageCodes) {
        this.languageCodes = languageCodes;
    }

    /**
     * Get the <code>Language</code> codes.
     * @return <code>Map</code>    Returns map containg <code>Language</code> codes
     */
    public Map<String, String> getLanguageCodes() {
        return this.languageCodes;
    }

    /**
     * @param countryCodes the countryCodes to set
     */
    public void setCountryCodes(Map<String, String> countryCodes) {
        this.countryCodes = countryCodes;
    }

    /**
     * Get the <code>Country</code> codes.
     * @return <code>Map</code>    Returns map containg <code>Country</code> codes
     */
    public Map<String, String> getCountryCodes() {
        return countryCodes;
    }


    /**
     * Fetching all Components list.
     * @return  <code>List</code>  Returns list containg Components to be displayed
     * @throws The <code>Doc41RepositoryException</code> to be thrown when error occurs
     */
    public List<String> getComponentList()  throws Doc41RepositoryException {
        List<String> compoList = new ArrayList<String>();
        // Get the list of DC's from DB
        List<TranslationsDC> dcList = null;
        try {
            dcList = this.translationsDAO.getComponentList();
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_GET_COMPONENTLIST_FAILED, e);
        }
        if (dcList != null) {
            // Iterate through the DC's and return list with containg Components names
            for (Iterator<TranslationsDC> iter = dcList.iterator(); iter.hasNext();) {
                TranslationsDC element = iter.next();
                compoList.add(element.getComponent());
            }
        }
        return compoList;

    }

    /**
     * Fetching all Jsp names based on Component.
     * @param pComponent The Component name
     * @return  <code>List</code> Returns list containg jsp names to be displayed
     * @throws The <code>Doc41RepositoryException</code> to be thrown when error occurs
     */
    public List<String> getPageList(String pComponent) throws Doc41RepositoryException  {
        List<String> pageList = new ArrayList<String>();
        // Get the list of DC's
        List<TranslationsDC> dcList = null;
        try {
            dcList = this.translationsDAO.getPageList(pComponent);
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_GET_PAGELIST_FAILED, e);
        }
        if (dcList != null) {
            // Iterate through the DC's and returns list with containg jsp names
            for (Iterator<TranslationsDC> iter = dcList.iterator(); iter.hasNext();) {
                pageList.add((iter.next()).getJSPName());
            }
        }
        return pageList;
    }

    /**
     * Finds All <code>Translation </code> tags based on input criteria and returns a map 
     * containing tags names and the corresponding tag values. 
     * @param component  String to match the component attribute of a translation tag.
     * @param language  String to match the language attribute of a translation tag.
     * @return <code>Hashmap</code> containing <code>tag names and values</code>s
     * @throws The <code>Doc41RepositoryException</code> to be thrown when error occurs
     */
    public Map<String, String> findTags4Component(String component, String language) throws Doc41RepositoryException {
        Map<String, String> compMap = new HashMap<String, String>();
        // Get the list of DC's
        List<TranslationsDC> dcList = null;
        try {
            dcList = this.translationsDAO.findTags4Component(component, language);
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_FIND_COMPONENT_TAGS_FAILED, e);
        }
        if (dcList != null) {
            // Iterate through the DC's and returns hasmap  containing tag name - value pairs
        	for (TranslationsDC dc : dcList) {
                compMap.put(dc.getTagName(), dc.getTagValue());
            }
        }
        return compMap;
    }

    /**
     * Distributes the Tags form QA to Production environment.
     * @throws The <code>Doc41RepositoryException</code> to be thrown when error occurs
     */
    public void distributeTags() throws Doc41RepositoryException {
        try {
            this.translationsDAO.distributeTags();
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_DISTRIBUTE_TAGS_FAILED,e);
        }
    }

    /**
     * To prevent duplicate entry, check tag existence before create.
     * @param translation The <code>Translation </code>
     * @throws The <code>Doc41RepositoryException</code> to be thrown when error occurs
     */
    public int isTagExist(Translation pTranslation) throws Doc41RepositoryException {
        try {
            return this.translationsDAO.isTagExist(getTranslationsMapper().mapToDC(pTranslation, TranslationsDC.newInstanceOfTranslationsDC()));
        } catch (Doc41TechnicalException e) {
            throw new Doc41RepositoryException(Doc41ErrorMessageKeys.TRANSL_TAG_EXISTS_FAILED,e);
        }
    }

    // GETTER
    public TranslationsMapper getTranslationsMapper() {
        return translationsMapper;
    }

    public TranslationsDAO getTranslationsDAO() {
        return translationsDAO;
    }
}
