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

import com.bayer.bhc.doc41webui.common.exception.Doc41RuntimeExceptionBase;
import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.ecim.foundation.basic.BasicDCOnlySoftDeletableDataCarrier;
import com.bayer.ecim.foundation.basic.BasicDCReflectFailedException;
import com.bayer.ecim.foundation.basic.ReflectTool;
import com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier;


/**
 * @author MBGHY
 *
 */
public class PoiReflectionMapper implements PoiMapper {
	
	private static final long OBJECTSTATE_DELETED = 0L;
	private Class<? extends UserChangeableDataCarrier> dcClass;
	private Set<String> excludeColumns;
	private Set<String> defaultExcludeColumns;
	private List<String> keyColumns;
	private String fixMethodName;
	
	public PoiReflectionMapper(Class<? extends UserChangeableDataCarrier> dcClass,List<String> keyColumns) {
		super();
		this.dcClass = dcClass;
		this.keyColumns = keyColumns;
		
		defaultExcludeColumns = new HashSet<String>();
		this.defaultExcludeColumns.add(UserChangeableDataCarrier.FIELD_OBJECTID);
		this.defaultExcludeColumns.add("ClientId");
		this.defaultExcludeColumns.add(UserChangeableDataCarrier.FIELD_CREATED);
		this.defaultExcludeColumns.add(UserChangeableDataCarrier.FIELD_CHANGED);
		this.defaultExcludeColumns.add(UserChangeableDataCarrier.FIELD_CREATEDBY);
		this.defaultExcludeColumns.add(UserChangeableDataCarrier.FIELD_CHANGEDBY);
		this.defaultExcludeColumns.add(UserChangeableDataCarrier.FIELD_CREATEDON);
		this.defaultExcludeColumns.add(UserChangeableDataCarrier.FIELD_CHANGEDON);
	}
	
	public void setExcludeColumns(List<String> excludeColumns) {
		this.excludeColumns = new HashSet<String>(excludeColumns);
	}

/* not understanding, what this is good for... esp. because such methode needs parameters, use interface to implement...	
	@Override
	public String getFixMethodName() {
		return fixMethodName;
	}
	
	public void setFixMethodName(String fixMethodName) {
		this.fixMethodName = fixMethodName;
	}
*/
	
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
	public Class<? extends UserChangeableDataCarrier> getDCClass() {
		return dcClass;
	}

	/**
	 * @see com.bayer.bhc.doc41webui.service.poi.PoiMapper#getColumnOrder()
	 */
	@Override
	public String[] getColumnOrder() {
		return getColumns();
	}

	private UserChangeableDataCarrier getDCInstance(){
		try {
			return ReflectTool.newInstance(dcClass);
		} catch (Exception e) {
			throw new Doc41RuntimeExceptionBase("GetDCInstance failed: " + dcClass.getName(), e);
		} 
	}

	/**
     * Mark a DC supporting soft deletion as deleted. If DC does not support soft deletion, print a warning once.
     * @param dc the DC to deleted.
     * @return true, if dc was changed and needs to be stored. 
     */
	public <T extends UserChangeableDataCarrier> boolean markAsDeleted(T dc, String userCwid) throws Doc41ServiceException {
	    if (dc instanceof BasicDCOnlySoftDeletableDataCarrier) {
	        if (((BasicDCOnlySoftDeletableDataCarrier)dc).isDeleted()) {
	            return false;
	        }
	        ((BasicDCOnlySoftDeletableDataCarrier)dc).setDeleted(true);
	        dc.setChangedBy(userCwid);
	        return true;
	    }
	    Doc41Log.get().warnMessageOnce(this, null, "Try to softdelete DC, but dc does not support: " + dc.getClass().getSimpleName());
	    return false;
	}
	
	public <T extends UserChangeableDataCarrier> String getObjectKey(T dc)  throws Doc41ServiceException {
		try {
			StringBuilder sb = new StringBuilder();
			for (String column : keyColumns) {
				if(sb.length()>0){
					sb.append(',');
				}
				sb.append(column);
				sb.append(':');
				sb.append(dc.get(column));
			}
			return sb.toString();
		} catch (BasicDCReflectFailedException e) {
			throw new Doc41ServiceException("getObjectKey", e);
		}
	}

}
