/**
 * File:TranslationsUCImpl.Java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.usecase;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.container.TranslationPagingRequest;
import com.bayer.bhc.doc41webui.domain.Translation;
import com.bayer.bhc.doc41webui.service.repository.TranslationsRepository;

/**
 * Implements the Translations Usecase.
 * @author ezzqc
 * 
 */
@Component
public class TranslationsUC {

    /**
     *  translationsRepository spring managed bean The <code>TranslationsRepository </code>
     */
	@Autowired
    TranslationsRepository translationsRepository;

    

	/**
     * Find's translation Tags based on input parameters.
     * @param pagingRequest  The <code>TranslationPagingRequest </code> object with requested parameter.
     * @return <code>PagingResult</code> countaing The <code>Translation </code> Tags
     * @throws The <code>Doc41ExceptionBase</code> to be thrown when error occurs
     */
    public PagingResult<Translation> findTags(TranslationPagingRequest pagingRequest) throws Doc41ExceptionBase  {
        return this.translationsRepository.findTags(pagingRequest);
    }

    /**
     * Deletes the <code>Translation</code> tag by ObjectId.
     * @param tagId the <code>Long</code>
     */
    public void deleteTagById(Long tagId) throws Doc41ExceptionBase  {
        this.translationsRepository.deleteTagById(tagId);
    }

    /**
     * Creats new translation tag.
     * @param translation The <code>Translation </code> object to be added.
     * @throws The <code>Doc41ExceptionBase</code> to be thrown when error occurs
     */
    public void createTag(Translation translation) throws Doc41ExceptionBase {
        this.translationsRepository.createTag(translation);
    }

    /**
     * Edit's translation tag.
     * @param translation The <code>Translation </code> object to be saved.
     * @throws The <code>Doc41ExceptionBase</code> to be thrown when error occurs
     */
    public void editTag(Translation translation) throws Doc41ExceptionBase{
        this.translationsRepository.editTag(translation);
    }

    /**
     * Returns the <code>Translation </code>tag based on The objectId.
     * @param Long The <code>objectID</code>
     * @return The <code>Translation </code> containg one row
     * @throws The <code>Doc41ExceptionBase</code> to be thrown when error occurs
     */
    public Translation getTagById(Long objectID) throws Doc41ExceptionBase{
        return this.translationsRepository.getTagById(objectID);
    }

    /**
     * Get the <code>Language</code> codes.
     * @return <code>Map</code> Returns map containg <code>Language</code> codes
     */
    public Map<String, String> getLanguageCodes() {
        return this.translationsRepository.getLanguageCodes();
    }

    /**
     * Get the <code>Country</code> codes.
     * @return <code>Map</code>    Returns map containg <code>Country</code> codes
     */
    public Map<String, String> getCountryCodes() {
        return this.translationsRepository.getCountryCodes();
    }

    /**
     * Fetching all Components list.
     * @return  <code>List</code> Returns list containg Components to be displayed
     * @throws The <code>Doc41ExceptionBase</code> to be thrown when error occurs
     */
    public List<String> getComponentList() throws Doc41ExceptionBase {
        return this.translationsRepository.getComponentList();
    }

    /**
     * Fetching all Jsp names based on Component.
     * @param pComponent The Component name
     * @return  <code>List</code> Returns list containg jsp names to be displayed
     * @throws The <code>Doc41ExceptionBase</code> to be thrown when error occurs
     */
    public List<String> getPageList(String pComponent) throws Doc41ExceptionBase {
        return this.translationsRepository.getPageList(pComponent);
    }

    /**
     * Distributes the Tags form QA to Production environment.
     * @throws The <code>Doc41ExceptionBase</code> to be thrown when error occurs
     */
    public void distributeTags() throws Doc41ExceptionBase{
        this.translationsRepository.distributeTags();
    }

    /**
     * To prevent duplicate entry, check tag existence before create.
     * @param translation    The <code>Translation </code>
     * @return retutns int value.
     * @throws The <code>Doc41ExceptionBase</code> to be thrown when error occurs
     */
    public int isTagExist(Translation translation) throws Doc41ExceptionBase {
        return this.translationsRepository.isTagExist(translation);
    }

}
