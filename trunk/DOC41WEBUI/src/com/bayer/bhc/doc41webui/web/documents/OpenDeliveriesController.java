/**
 * File:OpenDeliveriesController.Java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.documents;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.paging.TableSorterParams;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.Delivery;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.business.sbeanaccess.BATranslationsException;

/**
 * Controller to manage Translations view related requests using Translations Usecase.
 * @author ezzqc,evayd
 * 
 */
@Controller
public class OpenDeliveriesController extends AbstractDoc41Controller {
	@Autowired
	private DocumentUC documentUC;
	
	protected boolean hasRolePermission(User usr) {
    	return usr.isCarrier();
    }
	
	@RequestMapping(value="/documents/opendeliveries",method = RequestMethod.GET)
	public void get()  {
        
    }
	
	@RequestMapping(value="/documents/jsontable", method=RequestMethod.GET,produces="application/json") 
    @ResponseBody
    public Map<String, Object> getTable(HttpServletRequest request,TableSorterParams params,
    		@RequestParam String type) throws Doc41ExceptionBase, BATranslationsException {
//		Tags tags = new Tags(TranslationsDAO.SYSTEM_ID, "TADMN", "translationOverview", LocaleInSession.get());
//		TranslationsForm translationsForm = new TranslationsForm();
//		translationsForm.setMandant(params.getFilter(0));
//		translationsForm.setComponent(params.getFilter(1));
//		translationsForm.setJspName(params.getFilter(2));
//		translationsForm.setTagName(params.getFilter(3));
//		translationsForm.setLanguage(params.getFilter(4));
//		translationsForm.setCountry(params.getFilter(5));
//		translationsForm.setTagValue(params.getFilter(6));
//		
//		translationsForm.setOrderBy(params.getSortColumn(DB_COL_NAMES));
		
		String carrier = UserInSession.get().getCompany();//TODO use real carrier field
		List<Delivery> list = documentUC.getOpenDeliveries(type, carrier);
		
		List<String[]> rows = new ArrayList<String[]>();
		if(list.isEmpty()){
			String[] row = new String[]{"","","","not found","","",""};
			rows.add(row);
		} else {
			for (Delivery delivery : list) {
				String[] row = new String[9];
				row[0]= "<a onclick=\"test('"+delivery.getDeliveryNumber()+"')\" href=\"#\">"+delivery.getDeliveryNumber()+"</a>";
				row[1]= delivery.getShippingUnitNumber();
				row[2]= delivery.getShipToNumber();
				row[3]= delivery.getSoldToNumber();
				row[4]= ""+delivery.getGoodsIssueDate(); //TODO formatting
				rows.add(row);
			}
		}
		
        Map<String, Object> map = new HashMap<String, Object>();
        Object totalRows = list.size();//TODO for paging: result.getTotalSize();
		map.put("total_rows", totalRows);
//        map.put("headers", HEADERS);
        map.put("rows",rows);

        return map;
    }
	
}
