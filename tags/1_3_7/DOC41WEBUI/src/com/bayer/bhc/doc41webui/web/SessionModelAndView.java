package com.bayer.bhc.doc41webui.web;

import java.io.Serializable;

import org.springframework.ui.ModelMap;

/**
 * This class is used instead of ModelAndView for storing the last Mav in the Session.<br>
 * In contrast to the original, this class is serializable. 
 * CHANGE (IMROL): also serialize the model, it is needed for DB persistence!
 * The ViewName is serialized, the Model is not (transient).
 * So you can still put not-serializable data into the model but will not get an exception on persisting the session.
 * Of course the data in the model is gone then.
 * @author evayd,imrol
 *
 */
public class SessionModelAndView implements Serializable {
	private static final long serialVersionUID = 5788230028758009177L;

	private String viewName;
	
	private  ModelMap model;

	public SessionModelAndView(String viewName, ModelMap model) {
		super();
		this.viewName = viewName;
		this.model = model;
	}

	public String getViewName() {
		return viewName;
	}

	public ModelMap getModel() {
		return model;
	}
	
}
