/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.web.useradmin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41ExceptionBase;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.common.paging.TableSorterParams;
import com.bayer.bhc.doc41webui.common.paging.TablesorterPagingData;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.container.UserListFilter;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.integration.db.TranslationsDAO;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.business.sbeanaccess.BATranslationsException;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

@Controller
public class UserlistController extends AbstractDoc41Controller {

	private static final String[] DB_COL_NAMES = {"objectState_Id","lastName","firstName","cwid","email","phone1","ISEXTERNAL"};

	protected boolean hasPermission(User usr) {
		return usr.hasPermission(Doc41Constants.PERMISSION_BUSINESS_ADMIN, Doc41Constants.PERMISSION_TECHNICAL_ADMIN);
    }
	
	@RequestMapping(value="/useradmin/userlist",method=RequestMethod.GET)
    protected void get() throws Exception {
        
    }
	
	@RequestMapping(value="/useradmin/jsontable", method=RequestMethod.GET,produces="application/json") 
    @ResponseBody
    public Map<String, Object> getTable(HttpServletRequest request,TableSorterParams params) throws Doc41ExceptionBase, BATranslationsException {
		Tags tags = new Tags(TranslationsDAO.SYSTEM_ID, "useradmin", "list", LocaleInSession.get());
		UserListFilter userListFilter = new UserListFilter();
		userListFilter.setStatus(params.getFilter(0));
		userListFilter.setSurname(params.getFilter(1));
		userListFilter.setFirstname(params.getFilter(2));
		userListFilter.setCwid(params.getFilter(3));
		userListFilter.setEmail(params.getFilter(4));
//		userListFilter.setPhone(params.getFilter(5));
		userListFilter.setType(params.getFilter(6));
		
		userListFilter.setOrderBy(params.getSortColumn(DB_COL_NAMES));
		
        PagingResult<User> result = getUserManagementUC().findUsers(userListFilter, new TablesorterPagingData(params.getPage(),params.getSize()));
        
		List<User> list = result.getResult();
		List<String[]> rows = new ArrayList<String[]>();
		if(list.isEmpty()){
			String[] row = new String[]{"","","","","not found","","","","","","","","","",""};
			rows.add(row);
		} else {
			for (User user : list) {
				String[] row = new String[10];
				row[0]= displayStatus(request,user.getActive(),tags);
				row[1]= StringTool.nullToEmpty(user.getSurname());
				row[2]= StringTool.nullToEmpty(user.getFirstname());
				row[3]= StringTool.nullToEmpty(user.getCwid());
				row[4]= StringTool.nullToEmpty(user.getEmail());
				row[5]= StringTool.nullToEmpty(user.getPhone());
				row[6]= StringTool.nullToEmpty(user.getType());
				row[7]= displayRoles(request,user.getRoles(),tags); 
				//TODO move HTML to JSP
				row[8]= displayEditButton(request,user.getCwid(),tags);
				row[9]= displayToggleButton(request,user,tags);
				rows.add(row);
			}
		}
		
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total_rows", result.getTotalSize());
//        map.put("headers", HEADERS);
        map.put("rows",rows);

        return map;
    }
	
	private String displayToggleButton(HttpServletRequest request, User user,Tags tags) {
		String message;
		String iconName;
		String buttonLabel;
		if(user.getActive()){
			message = tags.getTag("MessageDeactivate");
			iconName = "user_deactivate.gif";
			buttonLabel = tags.getTag("ButtonDeactivate");
		} else {
			message = tags.getTag("MessageActivate");
			iconName = "user_activate.gif";
			buttonLabel = tags.getTag("ButtonActivate");
		}
		return "<a href='#' onclick=\"sendPostAfterCheck('"+message+"', 'toggleuser', 'togglecwid="+user.getCwid()+"')\"><img src='"+request.getContextPath()+"/resources/img/usermanagement/"+iconName+"' alt='"+buttonLabel+"' title='"+buttonLabel+"' style=\"border: 0px;\"></a>";
		
	}

	private String displayEditButton(HttpServletRequest request, String cwid,
			Tags tags) {
		return "<a href='#' onclick=\"sendGet('useradmin/useredit', 'editcwid="+cwid+"')\"><img src='"+request.getContextPath()+"/resources/img/common/page_edit.gif' alt='"+tags.getTag("ButtonEdit")+"' title='"+tags.getTag("ButtonEdit")+"' style=\"border: 0px;\"></a>";
	}

	
	private String displayRoles(HttpServletRequest request, List<String> roles,
			Tags tags) {
		StringBuilder sb = new StringBuilder();
		if(roles!=null){
			for (String role : roles) {
				if(sb.length()>0){
					sb.append(", ");
				}
				sb.append(tags.getTag(role));
			}
		}
		return sb.toString();
	}

	@RequestMapping(value="/useradmin/toggleuser",method=RequestMethod.POST)
	public String deleteTranslation(@RequestParam(value="togglecwid") String cwid) throws Doc41ExceptionBase{
		if(cwid==null){
			return "/useradmin/userlist";
		}
		userManagementUC.toggleUserActivation(cwid);
        
        return "redirect:/useradmin/userlist";
	}
}
