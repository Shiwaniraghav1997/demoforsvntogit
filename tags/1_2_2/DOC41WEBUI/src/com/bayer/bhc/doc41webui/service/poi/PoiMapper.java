package com.bayer.bhc.doc41webui.service.poi;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier;


public interface PoiMapper {

	String[] getHeaderLabels();

	Class<? extends UserChangeableDataCarrier> getDCClass();

	String[] getColumnOrder();
	
    /**
     * Mark a DC supporting soft deletion as deleted. If DC does not support soft deletion, print a warning once.
     * @param dc the DC to deleted.
     * @param userCwid the DC to deleted.
     * @return true, if dc was changed and needs to be stored. 
     */
	<T extends UserChangeableDataCarrier> boolean markAsDeleted(T dc, String userCwid) throws Doc41ServiceException;
	
	<T extends UserChangeableDataCarrier> String getObjectKey(T dc) throws Doc41ServiceException;

	//String getFixMethodName(); // not understanding, what this is good for... esp. because such methode needs parameters, use interface to implement...

}
