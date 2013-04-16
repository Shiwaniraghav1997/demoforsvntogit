package com.bayer.bhc.doc41webui.integration.db;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bayer.bhc.doc41webui.Doc41TestCase;
import com.bayer.bhc.doc41webui.integration.db.dc.masterdata.MDSysParamDC;

public class SysParamDAOTest extends Doc41TestCase {
	
	@Autowired
	private SysParamDAO dao;		
	
	@Test
	public void testGetOID() throws Exception {
		Long oid	= dao.getOID();
		assertNotNull(oid);
	}
}
