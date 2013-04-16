/**
 * 
 */
package com.bayer.bhc.doc41webui.service.poi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.integration.db.dc.Doc41DataCarrier;

/**
 * @author MBGHY
 *
 */
public class PoiReflectionMapper implements PoiMapper {
	
	private static final long OBJECTSTATE_DELETED = 0L;
	private Class<? extends Doc41DataCarrier> dcClass;
	private Set<String> excludeColumns;
	private Set<String> defaultExcludeColumns;
	private List<String> keyColumns;
	private String fixMethodName;
	
	public PoiReflectionMapper(Class<? extends Doc41DataCarrier> dcClass,List<String> keyColumns) {
		super();
		this.dcClass = dcClass;
		this.keyColumns = keyColumns;
		
		defaultExcludeColumns = new HashSet<String>();
		this.defaultExcludeColumns.add("ObjectID");
		this.defaultExcludeColumns.add("ClientId");
		this.defaultExcludeColumns.add("Created");
		this.defaultExcludeColumns.add("Changed");
		this.defaultExcludeColumns.add("CreatedBy");
		this.defaultExcludeColumns.add("ChangedBy");
		this.defaultExcludeColumns.add("CreatedOn");
		this.defaultExcludeColumns.add("ChangedOn");
	}
	
	public void setExcludeColumns(List<String> excludeColumns) {
		this.excludeColumns = new HashSet<String>(excludeColumns);
	}
	
	@Override
	public String getFixMethodName() {
		return fixMethodName;
	}
	
	public void setFixMethodName(String fixMethodName) {
		this.fixMethodName = fixMethodName;
	}

	/**
	 * @see com.bayer.bhc.doc41webui.service.poi.PoiMapper#getHeaderLabels()
	 */
	@Override
	public String[] getHeaderLabels() {
		return getColumns();
	}

	private String[] getColumns() {
		String[] columns = getDCInstance().getCSVColumList();
		return filterColumns(columns);
	}

	private String[] filterColumns(String[] columns) {
		List<String> filteredCols = new ArrayList<String>();
		for (String colName : columns) {
			if(!defaultExcludeColumns.contains(colName) && !(excludeColumns!=null && excludeColumns.contains(colName))){
				filteredCols.add(colName);
			}
		}
		
		return filteredCols.toArray(new String[0]);
	}

	/**
	 * @see com.bayer.bhc.doc41webui.service.poi.PoiMapper#getDCClass()
	 */
	@Override
	public Class<? extends Doc41DataCarrier> getDCClass() {
		return dcClass;
	}

	/**
	 * @see com.bayer.bhc.doc41webui.service.poi.PoiMapper#getColumnOrder()
	 */
	@Override
	public String[] getColumnOrder() {
		return getColumns();
	}

	private Doc41DataCarrier getDCInstance(){
		try {
			return dcClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public <T extends Doc41DataCarrier> boolean markAsDeleted(T dc) throws Doc41ServiceException {
		try {
			//check old status
			Method getMethod = dcClass.getMethod("getObjectstateId");
			Long oldStatus = (Long) getMethod.invoke(dc);
			if(oldStatus!=null && oldStatus.longValue()==OBJECTSTATE_DELETED){
				return false;
			}
			Method setMethod = dcClass.getMethod("setObjectstateId", Long.class);
			setMethod.invoke(dc, Long.valueOf(OBJECTSTATE_DELETED));
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		} catch (IllegalArgumentException e) {
			throw new Doc41ServiceException("markAsDeleted", e);
		} catch (IllegalAccessException e) {
			throw new Doc41ServiceException("markAsDeleted", e);
		} catch (InvocationTargetException e) {
			throw new Doc41ServiceException("markAsDeleted", e);
		} 
	}
	
	public <T extends Doc41DataCarrier> String getObjectKey(T dc)  throws Doc41ServiceException {
		try {
			StringBuilder sb = new StringBuilder();
			for (String column : keyColumns) {
				if(sb.length()>0){
					sb.append(',');
				}
				sb.append(column);
				sb.append(':');
				Method getMethod=dc.getClass().getMethod(createGetterName(column));
				Object value = getMethod.invoke(dc);
				sb.append(value);
			}
			return sb.toString();
		} catch (NoSuchMethodException e) {
			throw new Doc41ServiceException("getObjectKey", e);
		} catch (InvocationTargetException e) {
			throw new Doc41ServiceException("getObjectKey", e);
		} catch (IllegalAccessException e) {
			throw new Doc41ServiceException("getObjectKey", e);
		}
	}

	private String createGetterName(String column) {
		return "get"+Character.toUpperCase(column.charAt(0))+column.substring(1);
		
	}

}
