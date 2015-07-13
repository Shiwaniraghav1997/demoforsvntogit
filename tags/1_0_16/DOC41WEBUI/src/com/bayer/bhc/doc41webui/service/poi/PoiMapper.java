package com.bayer.bhc.doc41webui.service.poi;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier;


public interface PoiMapper {

	String[] getHeaderLabels();

	Class<? extends UserChangeableDataCarrier> getDCClass();

	String[] getColumnOrder();
	
	<T extends UserChangeableDataCarrier> boolean markAsDeleted(T dc) throws Doc41ServiceException;
	
	<T extends UserChangeableDataCarrier> String getObjectKey(T dc) throws Doc41ServiceException;

	String getFixMethodName();

}
