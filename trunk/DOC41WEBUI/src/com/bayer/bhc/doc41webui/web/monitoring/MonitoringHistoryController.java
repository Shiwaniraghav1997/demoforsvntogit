/**
 * 
 */
package com.bayer.bhc.doc41webui.web.monitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.common.paging.TableSorterParams;
import com.bayer.bhc.doc41webui.common.paging.TablesorterPagingData;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.domain.Monitor;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.integration.db.TranslationsDAO;
import com.bayer.bhc.doc41webui.usecase.MonitoringUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.business.sbeanaccess.BATranslationsException;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

@Controller
public class MonitoringHistoryController extends AbstractDoc41Controller {

	private static final String INTERFACE_DETAILS = "service";

	private static final String[] DB_COL_NAMES = {"OBJECT_ID","ACTION_TYPE","CREATED","ACTION_STATUS","ACTION_DETAILS","ACTION_REMARKS","RESPONSE_TIME"};
	
	@Autowired
    private MonitoringUC monitoringUC;
    
	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request) {
		return usr.hasPermission(Doc41Constants.PERMISSION_BUSINESS_ADMIN, Doc41Constants.PERMISSION_TECHNICAL_ADMIN);
	}
	
	@RequestMapping(value="/monitoring/monitoringHistory",method=RequestMethod.GET)
    public @ModelAttribute(INTERFACE_DETAILS) Monitor get(@RequestParam String serviceName) throws Doc41BusinessException  {
		return monitoringUC.findInterfaceDetailsByName(serviceName);
    }
	
	@RequestMapping(value="/monitoring/jsontable", method=RequestMethod.GET,produces="application/json") 
    @ResponseBody
    public Map<String, Object> getTable(TableSorterParams params,@RequestParam String serviceName) throws Doc41ExceptionBase, BATranslationsException {
		Tags tags = new Tags(TranslationsDAO.SYSTEM_ID, "tAdmin", "monitoringHistory", LocaleInSession.get());
		
		String orderBy = params.getSortColumn(DB_COL_NAMES);
		
		PagingResult<Monitor> result = monitoringUC.findMonitoringHistoryByInterface(serviceName,new TablesorterPagingData(params.getPage(),params.getSize()), orderBy );
		List<Monitor> list = result.getResult();
		List<String[]> rows = new ArrayList<String[]>();
		if(list.isEmpty()){
			String[] row = new String[]{"","","","not found","","",""};
			rows.add(row);
		} else {
			for (Monitor monitor : list) {
				String[] row = new String[7];
				row[0]= monitor.getId();
				row[1]= monitor.getAction();
				row[2]= ""+monitor.getCreated();
				row[3]= displayStatus(monitor.isStatus(),tags);
				row[4]= monitor.getDetails();
				row[5]= monitor.getRemarks();
				row[6]= ""+monitor.getResponseTime();
				rows.add(row);
			}
		}
		
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total_rows", result.getTotalSize());
//        map.put("headers", HEADERS);
        map.put("rows",rows);

        return map;
    }
}
