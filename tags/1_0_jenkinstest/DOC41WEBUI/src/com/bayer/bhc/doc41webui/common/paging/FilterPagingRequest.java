/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.paging;
import java.util.HashMap;
import java.util.Map;


/**
 * FilterPagingRequest object.
 * 
 * @author ezzqc
 * @id $Id: FilterPagingRequest.java,v 1.2 2012/02/22 14:16:09 ezzqc Exp $
 */
public class FilterPagingRequest extends PagingRequest {

    protected static final String USER_ID = "USER_ID";
    
    /** Filter map for DB access. */
    protected Map<String, Object> filterings = new HashMap<String, Object>();

    private Object filter;
    
    public FilterPagingRequest(int pageSize) {
    	super(pageSize);
    }
    
    public FilterPagingRequest(PagingData pData) {
        super(pData);
    }
    
	public FilterPagingRequest(final Object pFilter, final PagingData pData){
 		 super(pData);
 		 filter=pFilter;
 	}
	
	
	
    public void addFiltering(final String pFilterKey, final Object pFilterValue) {
        filterings.put(pFilterKey, pFilterValue);
    }

    public boolean isFiltered(final String pFilterKey) {
        return filterings.get(pFilterKey) != null;
    }

//    public Map<String, Object> getFilterings() {
//        return filterings;
//    }
    
    public FilterParameters getFilterParameters(){
    	if(filterings==null){
    		return new FilterParameters(new String[]{}, new Object[]{});
    	}
        String[] parameterNames = (String[]) filterings.keySet().toArray(new String[0]);
        Object[] parameterValues = new Object[parameterNames.length];
        for (int i = 0; i < parameterNames.length; i++) {
			String name = parameterNames[i];
			Object value = filterings.get(name);
			parameterValues[i]=value;
		}
        
        return new FilterParameters(parameterNames, parameterValues);
    }
    
    public static class FilterParameters{
    	private String[] parameterNames;
    	private Object[] parameterValues;
		public FilterParameters(String[] parameterNames,
				Object[] parameterValues) {
			super();
			this.parameterNames = parameterNames;
			this.parameterValues = parameterValues;
		}
    	public String[] getParameterNames() {
			return parameterNames;
		}
    	public Object[] getParameterValues() {
			return parameterValues;
		}
    }

  	/**
  	 * @return the AbstractCommandForm
  	 */
  	public Object getFilter() {
  		return filter;
  	}
    
}
