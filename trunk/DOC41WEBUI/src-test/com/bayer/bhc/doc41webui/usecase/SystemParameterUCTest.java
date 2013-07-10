package com.bayer.bhc.doc41webui.usecase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bayer.bhc.doc41webui.Doc41TestCase;

public class SystemParameterUCTest extends Doc41TestCase {
	
	@Autowired
	private SystemParameterUC usecase;
	

	
	@Test
	public void testGetDOC41CorrelationId() throws Exception {
		String cid	= usecase.getDOC41CorrelationId();
		assertNotNull(cid);
		assertTrue(cid.startsWith("DOC41-"));
		System.out.println(cid);
	}

}
