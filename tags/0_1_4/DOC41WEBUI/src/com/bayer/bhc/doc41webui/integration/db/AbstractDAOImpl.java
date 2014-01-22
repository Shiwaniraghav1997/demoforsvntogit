/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.integration.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bayer.bhc.doc41webui.common.exception.Doc41AccessDeniedException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.paging.FilterPagingRequest;
import com.bayer.bhc.doc41webui.common.paging.FilterPagingRequest.FilterParameters;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.DomainObject;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.SQLStringTool;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.dbx.ChangeableDataCarrier;
import com.bayer.ecim.foundation.dbx.DBObjectAccess;
import com.bayer.ecim.foundation.dbx.DataCarrier;
import com.bayer.ecim.foundation.dbx.DatabaseException;
import com.bayer.ecim.foundation.dbx.DeleteException;
import com.bayer.ecim.foundation.dbx.MappingException;
import com.bayer.ecim.foundation.dbx.QueryException;
import com.bayer.ecim.foundation.dbx.ReflectFailedException;
import com.bayer.ecim.foundation.dbx.StorableDataCarrier;
import com.bayer.ecim.foundation.dbx.StoreException;

/**
 * Abstract DAO. Database access object with convenience methods for database access. Uses Foundation object server.
 * 
 * @author ezzqc
 */
public abstract class AbstractDAOImpl {
    public static final String DEFAULT_SUB_COMPONENT_NAME	= "QueryTemplates";	   
    
    /** Locking keyword in SQL templates. */
    protected static final String KEYWORD_LOCKED = "LOCKED";
    
    @Autowired
    private Doc41TransactionManager transactionManager;
    
    protected Doc41TransactionManager getTransactionManager() {
		return transactionManager;
	}

    /**
     * Begin Transaction with Cache
     * @return Transaction ID
     * @throws Doc41TechnicalException
     */
    public int beginTransaction() throws Doc41TechnicalException {
        return getTransactionManager().beginTransaction(true);   
    }

    public int beginTransaction(boolean withCache) throws Doc41TechnicalException {
        return getTransactionManager().beginTransaction(withCache);   
    }
    
    public final void endTransaction(int txid) throws Doc41TechnicalException {
        getTransactionManager().endTransaction(txid);
       
    }

    public final void abortTransaction(int txid) {
        getTransactionManager().abortTransaction(txid);        
    }

    protected final DBObjectAccess getDBOX() {
        return (DBObjectAccess) DBObjectAccess.get();
    }

    protected final OTSingletonDoc41 getOTS() {
        return (OTSingletonDoc41) OTSingletonDoc41.get();
    }

    /**
     * Observers do not have permissions to store and delete functions.
     * 
     * @throws Doc41AccessDeniedException
     */
    protected void checkUser() throws Doc41TechnicalException {
        if (UserInSession.isReadOnly()) {
            throw new Doc41AccessDeniedException(this.getClass());
        }
    }
    
    /**
     * Stores a list of DCs.
     * 
     * @param txid transaction id
     * @param pDcList list of dc.
     * @throws Doc41TechnicalException
     */
    public <T extends ChangeableDataCarrier> List<T> store(int pTxid, ArrayList<T> pDcList) throws Doc41TechnicalException {
        checkUser();
        try {
        	setCreatedByChangedBy(pDcList);
            List<T> storedDcs = getOTS().storeTransactional(pTxid, pDcList);
            getTransactionManager().putToCache(pTxid, storedDcs);
            return storedDcs;
        } catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "Error during database access.", e);
        }
    }

	private <T extends ChangeableDataCarrier> void setCreatedByChangedBy(ArrayList<T> pDcList) {
		for (T dc : pDcList) {
			setCreatedByChangedBy(dc);
		}
	}

	private <T extends ChangeableDataCarrier> void setCreatedByChangedBy(T dc) {
		if(dc.getObjectID()==null){
			if(StringTool.isTrimmedEmptyOrNull(dc.getCreatedBy())){
				dc.setCreatedBy(UserInSession.getCwid());
				
			}
		}
		if(StringTool.isTrimmedEmptyOrNull(dc.getChangedBy())){
			dc.setChangedBy(UserInSession.getCwid());
		}
	}

    public <T extends ChangeableDataCarrier> T store(int pTxid, T pDc)
            throws Doc41TechnicalException {
        checkUser();
        try {
        	setCreatedByChangedBy(pDc);
            T dc = getOTS().storeTransactional(pTxid, pDc);
            getTransactionManager().putToCache(pTxid, dc);
            return dc;
        } catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "Error during database access.", e);
        }
    }

    @SuppressWarnings("unchecked")
	public <T extends ChangeableDataCarrier> T store(T pDc) throws Doc41TechnicalException {
        checkUser();
        try {
        	setCreatedByChangedBy(pDc);
            return (T) getOTS().storeDC(pDc);
        } catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "Error during database access.", e);
        }
    }
    
    @SuppressWarnings("unchecked")
	public <T extends ChangeableDataCarrier> ArrayList<T> store(ArrayList<T> pDcList) throws Doc41TechnicalException {
        checkUser();
        try {
        	setCreatedByChangedBy(pDcList);
            return getOTS().storeDCs(pDcList);
        } catch (StoreException e) {
            throw new Doc41TechnicalException(this.getClass(), "Error during database access.", e);
        }
    }
        

    /*
     * 
     */
    public void delete(StorableDataCarrier pDc) throws Doc41TechnicalException {
        checkUser();
        try {
            getOTS().deleteDC(pDc);
        } catch (DeleteException e) {
            throw new Doc41TechnicalException(this.getClass(), "Database delete operation failed.", e);
        }
    }

    /*
     * 
     */
    public void delete(int transactionId, StorableDataCarrier dc) throws Doc41TechnicalException {
        checkUser();
        try {
            getOTS().deleteTransactional(transactionId, dc);
            getTransactionManager().removeFromCache(transactionId, dc);
        } catch (DeleteException e) {
            throw new Doc41TechnicalException(this.getClass(), "Database delete operation failed.", e);
        }
    }

    /*
     * 
     */
    public void  deleteById(Class<? extends StorableDataCarrier> pClazz, Long pObjectId) throws Doc41TechnicalException {
        checkUser();

        try {
            getOTS().deleteDCById(pClazz, pObjectId);
        } catch (DeleteException e) {
            throw new Doc41TechnicalException(this.getClass(), "delete failed.", e);
        }
    }

    /*
     * 
     */
    public void deleteById(int transactionId, Class<? extends StorableDataCarrier> pClazz, Long pObjectId)
            throws Doc41TechnicalException {
        checkUser();

        if (!StorableDataCarrier.class.isAssignableFrom(pClazz)) {
            throw new Doc41TechnicalException(
                    this.getClass(),
                    "Not able to delete object of that type. Can find only StorableDataCarrier objects.",
                    null);
        }

        try {
            getOTS().deleteTransactional(transactionId, pClazz, pObjectId);
            getTransactionManager().removeFromCache(transactionId, pObjectId);
        } catch (DeleteException e) {
            throw new Doc41TechnicalException(this.getClass(), "delete failed.", e);
        } catch (InitException e) {
            throw new Doc41TechnicalException(this.getClass(), "delete failed.", e);
        }

    }

    public <T extends StorableDataCarrier> T findDC(String[] pParameterNames, Object[] pParameterValues, String pTemplateName,
            Class<T> pClazz) throws Doc41TechnicalException {
    	List<T> dcs	= find(pParameterNames, pParameterValues, pTemplateName, pClazz);
    	if (dcs == null || dcs.size() == 0) {
    		return null;
    	} else if (dcs.size() == 1) {
    		return dcs.get(0);
    	} else {
    		throw new Doc41TechnicalException(this.getClass(), "Query returned more than one DC: " + pTemplateName+" "+Arrays.toString(pParameterNames)+" "+Arrays.toString(pParameterValues));
    	}
    }
    
    protected <T extends DataCarrier> List<T> find(String[] pParameterNames, Object[] pParameterValues, String pTemplateName,
            Class<T> pClazz) throws Doc41TechnicalException {

        int transactionID = -1;
        List<T> result = null;

        try {
            transactionID = getTransactionManager().beginTransaction(false);
            result = find(transactionID, pParameterNames, pParameterValues, pTemplateName, pClazz);
            getTransactionManager().endTransaction(transactionID);

        } finally {
            getTransactionManager().abortTransaction(transactionID);
        }
        return result;
    }

    public <T extends StorableDataCarrier> T findForUpdate(int pTxid, Class<T> pClazz, Long pObjectId)
            throws Doc41TechnicalException {
        try {
            return getOTS().getDCByObjectIDForUpdate(pTxid, pClazz, pObjectId);
        } catch (QueryException e) {
            throw new Doc41TechnicalException(this.getClass(), "findForUpdate by id failed.", e);
        }
    }

    /*
     * 
     */
    protected <T extends DataCarrier> List<T> find(int pTransactionId, String[] pParameterNames, Object[] paramValues,
            String pTemplateName, Class<T> pClazz) throws Doc41TechnicalException {

    	paramValues = SQLStringTool.escape(paramValues);
        String query = getDBOX().expandTemplate((String) getQueryTemplates().get(pTemplateName),
        		paramValues, 
        		pParameterNames);

        try {
            @SuppressWarnings("unchecked")
			ArrayList<T> dcs = getDBOX().query(pTransactionId, query, pClazz);
			return dcs;
        } catch (ReflectFailedException e) {
            throw new Doc41TechnicalException(this.getClass(), "find failed.", e);
        } catch (DatabaseException e) {
            throw new Doc41TechnicalException(this.getClass(), "find failed.", e);
        } catch (MappingException e) {
            throw new Doc41TechnicalException(this.getClass(), "find failed.", e);
        }

    }

    protected int count(String[] pParameterNames, Object[] pParameterValues, String pTemplateName)
            throws Doc41TechnicalException {

        int transactionID = -1;
        int count = 0;

        try {
            transactionID = getTransactionManager().beginTransaction(false);
            count = count(transactionID, pParameterNames, pParameterValues, pTemplateName);
            getTransactionManager().endTransaction(transactionID);

        } finally {
            getTransactionManager().abortTransaction(transactionID);
        }
        return count;
    }

    /*
     * 
     */
    protected int count(int pTransactionId, String[] pParameterNames, Object[] pParameterValues,
            String pTemplateName) throws Doc41TechnicalException {

        String query = getDBOX().expandTemplate((String) getQueryTemplates().get(pTemplateName),
        		pParameterValues, 
        		pParameterNames);

        try {
            return getDBOX().queryCount(pTransactionId, query);
        } catch (ReflectFailedException e) {
            throw new Doc41TechnicalException(this.getClass(), "count failed.", e);
        } catch (DatabaseException e) {
            throw new Doc41TechnicalException(this.getClass(), "count failed.", e);
        }
    }

    /*
     * 
     */
    protected <T extends StorableDataCarrier> T find(int transactionId, Class<T> pClazz, Long objectId)
            throws Doc41TechnicalException {
        try {
            T dc = getTransactionManager().findInCache(transactionId, objectId);
            if (dc == null) {
                dc = getOTS().getDCByObjectID(transactionId, pClazz, objectId);
                getTransactionManager().putToCache(transactionId, dc);
            }
            return dc;
            
        } catch (QueryException e) {
            throw new Doc41TechnicalException(this.getClass(), "find by id failed.", e);
        }
    }

    /*
     * 
     */
    protected <T extends StorableDataCarrier> T find(Class<T> pClassDC, Long pObjectId) throws Doc41TechnicalException {
        try {
            return getOTS().getDCByObjectID(pClassDC, pObjectId);
        } catch (QueryException e) {
            throw new Doc41TechnicalException(this.getClass(), "find by id failed.", e);
        }
    }

    protected Map<String, String> getQueryTemplates() {
        @SuppressWarnings("unchecked")
		Map<String, String> subConfig = ConfigMap.get().getSubConfig(getTemplateComponentName(), getTemplateSubComponentName());
		return subConfig;
    }

    public abstract String getTemplateComponentName();

    public String getTemplateSubComponentName() {
        return DEFAULT_SUB_COMPONENT_NAME;
    }    


    protected <T extends StorableDataCarrier> PagingResult<T> findByFilter(FilterPagingRequest pagingRequest, String templateName,
            Class<T> dcClazz) throws Doc41TechnicalException {

    	FilterParameters filterParameters = pagingRequest.getFilterParameters();

        return find(filterParameters.getParameterNames(), filterParameters.getParameterValues(), templateName, dcClazz, pagingRequest
                .getTotalSize(), pagingRequest.getStartIndex(), pagingRequest.getEndIndex());
    }

    protected <T extends StorableDataCarrier> PagingResult<T> findByFilter(int txid, FilterPagingRequest pagingRequest,
            String templateName, Class<T> dcClazz) throws Doc41TechnicalException {

    	FilterParameters filterParameters = pagingRequest.getFilterParameters();

        return find(txid, filterParameters.getParameterNames(), filterParameters.getParameterValues(), templateName, dcClazz, pagingRequest
                .getTotalSize(), pagingRequest.getStartIndex(), pagingRequest.getEndIndex());
    }

    protected <T extends StorableDataCarrier> PagingResult<T> find(String[] paramNames, Object[] paramValues, String templateName,
            Class<T> dcClass, int lastCount, int fromIndex, int toIndex) throws Doc41TechnicalException {

        PagingResult<T> result = null;
        int txid = -1;
        
        try {
            txid = getTransactionManager().beginTransaction(false);
            result = find(txid, paramNames, paramValues, templateName, dcClass, lastCount,
                    fromIndex, toIndex);
            getTransactionManager().endTransaction(txid);
        } finally {
            getTransactionManager().abortTransaction(txid);
        }
        return result;
    }

    protected <T extends StorableDataCarrier> PagingResult<T> find(int txid, String[] paramNames, Object[] paramValues,
            String templateName, Class<T> dataCarrierClass, int lastCount, int fromIndex, int toIndex)
            throws Doc41TechnicalException {

    		paramValues = SQLStringTool.escape(paramValues);
    		    		
    		return queryWithPagingAutoMode(txid, 
        		expandNames(new String[] { "COUNT" }, paramNames),
                expandValues(new Object[] { "*" }, paramValues), 
                expandNames(new String[] { "COUNT", "TO_IDX" }, paramNames), 
                expandValues(new Object[] { null,  (toIndex < 0) ? new Integer(5000) : new Integer(toIndex + 1) }, paramValues),
                templateName, dataCarrierClass, lastCount, fromIndex, toIndex);
    }
     
    protected <T extends StorableDataCarrier> PagingResult<T> queryWithPagingAutoMode(int pTxId, String[] paramNamesCnt,
            Object[] paramValuesCnt, String[] paramNames, Object[] paramValues,
            String pTemplateName, Class<T> dataCarrierClass, int lastCount, int fromIndex, int toIndex)
            throws Doc41TechnicalException {

        String mode = " (COUNT)";
        String countQuery = null;
        boolean refreshHitCount = true;
        boolean getToEnd = (toIndex == -1);

        //System.out.println("queryWithPagingAutoMode: "+lastCount+","+fromIndex +","+ toIndex);
        String mQueryTemplate = (String) getQueryTemplates().get(pTemplateName);
        PagingResult<T> pagingResult = new PagingResult<T>(new ArrayList<T>(), lastCount);

        try {
            if (!getToEnd && (lastCount == Integer.MAX_VALUE)) {

                countQuery = getDBOX()
                        .expandTemplate(mQueryTemplate, paramValuesCnt, paramNamesCnt);

                pagingResult.setTotalSize(getDBOX().queryCount(pTxId, countQuery));
                refreshHitCount = false;
            }

            if (pagingResult.getTotalSize() > 0) {
                mode = " (LOAD)";
                String loadQuery = getDBOX()
                        .expandTemplate(mQueryTemplate, paramValues, paramNames);

                //System.out.println("queryWithPagingAutoMode - query:"+loadQuery);
                boolean hasMore = (toIndex == 0) ? (pagingResult.getTotalSize() > 0) : getDBOX()
                        .query(pTxId, loadQuery, dataCarrierClass, fromIndex, toIndex,
                                (ArrayList<T>) pagingResult.getResult());

                if (getToEnd) {
                    pagingResult.setTotalSize(((fromIndex < 1) ? 0 : fromIndex-1)
                            + pagingResult.getResult().size());

                } else {
                    if (refreshHitCount) { // The hitCount was not updated by precount, but typically returned here be recall (for paging),
                        // CHECK FOR RECOUNT!
                        int bLastIdx = toIndex + 1;
                        int bCnt = lastCount;

                        if ((hasMore && (bLastIdx >= bCnt)) || (!hasMore && (bLastIdx < bCnt))) {

                        	mode = " (COUNT)";
                            if (countQuery == null) {
                                countQuery = getDBOX().expandTemplate(mQueryTemplate,
                                        paramValuesCnt, paramNamesCnt);
                            }
                            pagingResult.setTotalSize(getDBOX().queryCount(pTxId, countQuery));
                        }
                    }
                }
            }
            return pagingResult;

        } catch (Exception e) {
            throw new Doc41TechnicalException(this.getClass(), "Error during database access for paging - "
                    + mode, e);
        }
    }
        
	public int executeQuery(int pTXID, String[] parameterNames,
			Object[] parameterValues, String templateName)
			throws Doc41TechnicalException {
		try {
			return getDBOX().execute(
					pTXID,
					getDBOX().expandTemplate(getQueryTemplates().get(templateName),
							parameterValues,
							parameterNames));
		} catch (Exception e) {
			throw new Doc41TechnicalException(this.getClass(), "Error during executeQuery",e);
		}
	}
	
	protected int executeQuery(String[] parameterNames,
			Object[] parameterValues, String templateName)
			throws Doc41TechnicalException {

        int transactionID = -1;
        try {
            transactionID = getTransactionManager().beginTransaction(false);
            int result = executeQuery(transactionID, parameterNames, parameterValues, templateName);
            getTransactionManager().endTransaction(transactionID);
            return result;

        } finally {
            getTransactionManager().abortTransaction(transactionID);
        }
    }

    protected final String[] expandNames(String[] additionalNames, String[] paramNames) {
        int addCnt = (additionalNames == null) ? 0 : additionalNames.length;
        int cnt = (paramNames == null) ? 0 : paramNames.length;
        String[] allParamNames = new String[addCnt + cnt];

        System.arraycopy(additionalNames, 0, allParamNames, 0, addCnt);
        if (cnt > 0) {
            System.arraycopy(paramNames, 0, allParamNames, addCnt, cnt);

        }
        return allParamNames;
    }

    protected final Object[] expandValues(Object[] additionalValues, Object[] paramValues) {
        int addCnt = (additionalValues == null) ? 0 : additionalValues.length;
        int cnt = (paramValues == null) ? 0 : paramValues.length;
        Object[] allValues = new Object[addCnt + cnt];

        System.arraycopy(additionalValues, 0, allValues, 0, addCnt);
        if (cnt > 0) {
            System.arraycopy(paramValues, 0, allValues, addCnt, cnt);

        }
        return allValues;
    }
    
    protected final String idsAsString(List<Object> objectList) {
        if (objectList == null || objectList.size() == 0) {
            return "0";  // 0 is an unused objectId
        }
        
        StringBuffer idBuffer = new StringBuffer();
        for (Iterator<Object> iter = objectList.iterator(); iter.hasNext();) {
            Object obj = (Object)iter.next(); 
            if (obj instanceof DomainObject) {
                idBuffer.append(((DomainObject)obj).getDcId());
                
            } else if (obj instanceof StorableDataCarrier) {
                idBuffer.append(((StorableDataCarrier)obj).getObjectID());
            }
            
            if (iter.hasNext()) {
                idBuffer.append(", ");
            }
        }
        
        //System.out.println("idsAsString"+objectList.size());
        //System.out.println("idsAsString"+idBuffer.toString());
        return idBuffer.toString();
    }
    
    protected String getSuffix(Locale locale) {
        String suffix = "";
        if (LocaleInSession.get() != null) locale = LocaleInSession.get();
        
        if (locale != null && 
                Locale.CHINESE.getLanguage().equalsIgnoreCase(locale.getLanguage())) {
            suffix = "ZH";
        } 
        return suffix;
    }
    
}
