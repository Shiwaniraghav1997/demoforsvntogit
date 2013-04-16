/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.container;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.paging.PagingData;

/**
 * Used for general handling of paging information - compare PagingListController
 * 
 * @author ezzqc
 */
public class PagingForm extends AbstractCommandForm implements PagingData {
	private static final long serialVersionUID = 1L;
	
    private static final String COMMAND_FAST_FORWARD = "pagingFastForward";
    private static final String COMMAND_FAST_REWIND = "pagingFastRewind";

    private static final String COMMAND_SUCC_PAGE = "pagingNextPage";
    private static final String COMMAND_PREV_PAGE = "pagingPrevPage";

    

    private int submitCount = 0;
    
    private int pageSize = 25;
    
    private int startIndex = 1;
    
    private int totalSize = Integer.MAX_VALUE;
    
    // used for rendering: 
    private int endIndex = -1;

    private int pageIndex = -1;

    private String formName = null;

    private String actionName = "paging";
    
    public PagingForm() {
        startIndex = -1;
        pageSize = -1;
    }
    
    public PagingForm(final String pClassName) {
        super();
        setFormName("paging"+getLastToken(pClassName, "."));
    }

    public void prepareLoading(int sessionSubmitCount, boolean isFormSubmission) {
    	startIndex -=1;
    	
//        System.out.println("prepareLoading"+getCommand());
//        System.out.println(sessionSubmitCount+", "+submitCount);
//        System.out.println(sessionSubmitCount+", "+isFormSubmission);
//        System.out.println("start prepareLoading: "+startIndex+","+endIndex);
        
        if (isFormSubmission && (getCommand() == null || !getCommand().startsWith("paging"))) {
			// reset pager
        	startIndex = 0;
		}
        
        // avoid duplicate command execution (e.g. refresh of browser)
        if ((getCommand() != null) &&  (sessionSubmitCount == submitCount)) {
            int pageAmount = 1;
            
            if (getCommand().equalsIgnoreCase(COMMAND_FAST_REWIND)) {
                startIndex = 0;
            } else if (getCommand().equalsIgnoreCase(COMMAND_FAST_FORWARD)) {
                int modulo = totalSize % pageSize;
                
                if (modulo == 0) {
                    startIndex = totalSize - pageSize;
                } else {
                    startIndex = totalSize - modulo;
                }
            } else if (getCommand().startsWith(COMMAND_PREV_PAGE)) {
                try {
                    pageAmount = Integer.parseInt(getCommand().substring(14));  // 14: len of prevPage
                } catch (NumberFormatException e) {} // ignore - keep default
                
                startIndex = Math.max(0, startIndex - pageAmount * pageSize);

                
            } else if (getCommand().startsWith(COMMAND_SUCC_PAGE)) {
                try {
                    pageAmount = Integer.parseInt(getCommand().substring(14));
                } catch (NumberFormatException e) {} // ignore - keep default
                
                startIndex = Math.min(totalSize, startIndex + pageAmount * pageSize);
            }
            setCommand(null);
            submitCount++;
        } else {
            submitCount = sessionSubmitCount;
        }
        
        // startIndex at (left-hand) page boundary:
        int modulo = startIndex%pageSize;
        startIndex = startIndex - modulo;
        // never negativ:
        startIndex = Math.max(0, startIndex);
        endIndex = startIndex + pageSize - 1;
        
        //System.out.println("end prepareLoading: "+startIndex+","+endIndex);
    }
    
    public void prepareRendering() {        
        //System.out.println("+ prepareRendering: "+startIndex+","+endIndex+","+totalSize);
        
        if (pageSize < 0) {
            startIndex = 0;
            endIndex = Integer.MAX_VALUE;
        } else {
            endIndex = startIndex+pageSize;
        }
        
        if (endIndex > totalSize) {
            endIndex = totalSize;
        }
        if (startIndex >= endIndex) {
            startIndex = Math.max(0, endIndex-pageSize);
        }
        
        pageIndex = (startIndex / pageSize) + 1;
        
        startIndex +=1;
        //System.out.println("- prepareRendering: "+startIndex+","+endIndex+","+totalSize);
    }

    public int getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(int submitCount) {
        this.submitCount = submitCount;
    }
    
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
    
    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
 
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }   
    
    // helper method:
    public List<Object> getPartialList(List<Object> completeList) {
        
        if (completeList == null){
            setTotalSize(0);
            return completeList;
        }
        setTotalSize(completeList.size());
        
        if (pageSize < 0){
            return completeList;
        }
        if (totalSize <= pageSize){
            return completeList;
        }
        
        int i = 0;
        List<Object> partialList = new ArrayList<Object>();
        for (Iterator<Object> iter = completeList.iterator(); (partialList.size() < pageSize) && iter.hasNext(); i++) {
            Object element = iter.next();
            if (i >= getStartIndex()) {
                partialList.add(element);
            }
        }
        
        return partialList;
    }
    
    // helper:
    private String getLastToken(String className, String delim) {
        String entityName = null;
        // className without package name:
        StringTokenizer st = new StringTokenizer(className, delim);
        while(st.hasMoreTokens()) {
            entityName = st.nextToken();
        }

        return entityName;
    }
    
    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.domain.container.AbstractCommandForm#validate(javax.servlet.http.HttpServletRequest, org.springframework.validation.Errors)
     */
    public void validate(HttpServletRequest request, Errors errors) {
        //  nothing to do here
    }

	
}
