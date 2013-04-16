package com.bayer.bhc.doc41webui.service.poi;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.integration.db.dc.Doc41DataCarrier;

public interface PoiMapper {

	String[] getHeaderLabels();

	Class<? extends Doc41DataCarrier> getDCClass();

	String[] getColumnOrder();
	
	<T extends Doc41DataCarrier> boolean markAsDeleted(T dc) throws Doc41ServiceException;
	
	<T extends Doc41DataCarrier> String getObjectKey(T dc) throws Doc41ServiceException;

	String getFixMethodName();

}
