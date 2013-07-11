package com.bayer.bhc.doc41webui.service.poi;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.integration.db.dc.Doc41DataCarrier;

public interface PoiService {
	
	<T extends Doc41DataCarrier> List<T> importExcel(String importName,InputStream inStream, List<T> existingDCs) throws Doc41ServiceException;

	void exportExcel(String exportName,OutputStream outStream, List<? extends Doc41DataCarrier> dcs) throws Doc41ServiceException;
	
	
}
