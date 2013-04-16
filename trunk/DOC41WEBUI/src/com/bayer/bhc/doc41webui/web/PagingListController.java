/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bayer.bhc.doc41webui.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.container.PagingForm;

/**
 * The base class handling with the paging form. Specific controller which need Paging
 * should just extend this class and implement the getListModel() method. In order to
 * use it, include paging.jsp in your jsp at any place - but outside of other forms !!!
 *
 * @author ezzqc,imrol
 */
@SuppressWarnings("deprecation")
public abstract class PagingListController extends Doc41Controller {    
	
    private static final String pagingAttribId	= "pagingList";
	protected static final String PAGING_FORM 	= "pagingForm";
	private String pagingAttribParameter		= "";



	// to be overwritten by the specific list controller
    protected abstract ModelAndView getListModel(PagingForm pform, HttpServletRequest request, Object command) throws Exception;
   

    @Override
	protected BrowserTabAttributes createBrowserTabAttributes(String aVersionId) {
    	if (aVersionId.startsWith(pagingAttribId)) {
    		return new PagingListState();
    	}
    	return super.createBrowserTabAttributes(aVersionId);
	}


	private PagingForm prepare4Loading(HttpServletRequest request, boolean isFormSubmission) throws Exception {
    	PagingForm pagingForm = new PagingForm(this.getClass().getName());
    	//System.out.println("prepare4Loading - "+this.getClass().getName());
    	
    	PagingListState pagingListState= (PagingListState) getBrowserTabAttributes(request,pagingAttribId+pagingForm.getFormName()+pagingAttribParameter);
    	int submitCount = 0;
		int sessionPageSize = pagingListState.getPageSize();
		int sessionStartIndex = pagingListState.getPageStart();
		int sessionSubmitCount = pagingListState.getPageCount();
		
        
//        System.out.println(pagingForm.getFormName() +"<- "+sessionPageSize);
//        System.out.println(pagingForm.getFormName() +"<- "+sessionStartIndex);
//        System.out.println(pagingForm.getFormName()+COUNT +"<- "+sessionSubmitCount);
        
		pagingForm.setPageSize(sessionPageSize);

		pagingForm.setStartIndex(sessionStartIndex);

		submitCount = sessionSubmitCount;
        
        // bind form if possible (render events got from paging.jsp)
        ServletRequestDataBinder binder = createBinder(request, pagingForm);
        binder.bind(request);
        
        // recalculate paging form for loading:
        pagingForm.prepareLoading(submitCount, isFormSubmission);
                
        return pagingForm;
    }
    
    
    private void prepareRenderingAndSave(PagingForm pagingForm, HttpServletRequest request) throws Exception {
        // recalculate paging form for loading:
        pagingForm.prepareRendering();
        
    	PagingListState pagingListState= (PagingListState) getBrowserTabAttributes(request,pagingAttribId+pagingForm.getFormName()+pagingAttribParameter);
    	pagingListState.setPageSize(pagingForm.getPageSize());
    	pagingListState.setPageStart(pagingForm.getStartIndex());
    	pagingListState.setPageCount(pagingForm.getSubmitCount());
        // compare getPreparedPagingForm4Loading()
        // keep pageSize, startIndex and submitCounter in session again:
    }
    
    
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		//System.out.println("PagingListController - onSubmit");
		       
        
		PagingForm pf = prepare4Loading(request, true);
		//System.out.println("onSubmit "+command.getClass().getName());
		
		if (command instanceof PagingForm) {
			command = formBackingObject(request);
		}
		ModelAndView listModel = getListModel(pf, request, command);

		prepareRenderingAndSave(pf, request);
		return super.onSubmit(request, response, command, errors)
						.addAllObjects(listModel.getModel())
							.addObject(pf.getFormName(), pf)
								.addObject(PAGING_FORM, pf)
									.addObject("ready", Boolean.TRUE);
	}
	
    
	@SuppressWarnings("rawtypes")
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
		
		//System.out.println("showForm");
		PagingForm pform = prepare4Loading(request, false);
		ModelAndView listModel = getListModel(pform, request, formBackingObject(request));
		 
		// recalculate paging form for rendering:
		prepareRenderingAndSave(pform, request);
		return super.showForm(request, response, errors, listModel.getModel())
								.addObject(pform.getFormName(), pform)
									.addObject(PAGING_FORM, pform)
										.addObject("ready", Boolean.TRUE);
	}
	
    public String getPagingAttribParameter() {
		return pagingAttribParameter;
	}

	public void setPagingAttribParameter(String pagingAttribParameter) {
		this.pagingAttribParameter = pagingAttribParameter;
	}

}
