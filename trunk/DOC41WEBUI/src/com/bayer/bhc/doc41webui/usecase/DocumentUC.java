package com.bayer.bhc.doc41webui.usecase;

import org.springframework.beans.factory.annotation.Autowired;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.integration.sap.service.AuthorizationRFCService;

public class DocumentUC {

	@Autowired
	private AuthorizationRFCService authorizationRFCService;
	
	
	public String checkCoaDeliveryNumberMaterial(String deliveryNumber, String matNo)
	throws Doc41BusinessException{
		try {
			return authorizationRFCService.checkCoADeliveryNumberMaterial(deliveryNumber, matNo);
		} catch (Doc41ServiceException e) {
			throw new Doc41BusinessException("checkCoaDeliveryNumberMaterial",e);
		}
	}
}
