package com.bayer.bhc.doc41webui.service.poi;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier;


public interface PoiService {
	
	<T extends UserChangeableDataCarrier> List<T> importExcel(String importName,InputStream inStream, List<T> existingDCs, String userCwid) throws Doc41ServiceException;

	void exportExcel(String exportName,OutputStream outStream, List<? extends UserChangeableDataCarrier> dcs) throws Doc41ServiceException;
	
	
}
