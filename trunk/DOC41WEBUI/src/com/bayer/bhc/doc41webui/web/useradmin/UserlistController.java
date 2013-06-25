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

	protected boolean hasRolePermission(User usr) {
		return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
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
				String[] row = new String[16];
				row[0]= displayStatus(request,user,tags);
				row[1]= StringTool.nullToEmpty(user.getSurname());
				row[2]= StringTool.nullToEmpty(user.getFirstname());
				row[3]= StringTool.nullToEmpty(user.getCwid());
				row[4]= StringTool.nullToEmpty(user.getEmail());
				row[5]= StringTool.nullToEmpty(user.getPhone());
				row[6]= StringTool.nullToEmpty(user.getType());
				row[7]= displayRole(request,user.isCarrier(),tags); 
				row[8]= displayRole(request,user.isCustomsBroker(),tags);
				row[9]= displayRole(request,user.isLayoutSupplier(),tags);
				row[10]= displayRole(request,user.isPmSupplier(),tags);
				row[11]= displayRole(request,user.isBusinessAdmin(),tags);
				row[12]= displayRole(request,user.isTechnicalAdmin(),tags);
				row[13]= displayRole(request,user.isObserver(),tags);
				//TODO move HTML to JSP
				row[14]= displayEditButton(request,user.getCwid(),tags);
				row[15]= displayToggleButton(request,user,tags);
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

	private String displayRole(HttpServletRequest request,boolean isRole, Tags tags) {
		if(isRole){
			return "<img src='"+request.getContextPath()+"/resources/img/common/check_green.gif' alt='"+tags.getTag("CheckGreen")+"' style='border: 0px;'>";
		} else {
			return "&nbsp;";
		}
	}

	private String displayStatus(HttpServletRequest request,User user, Tags tags) {
		String altText;
		String iconName;
		if(user.getActive()){
			altText = tags.getTag("Active");
			iconName = "ball_green.gif";
		} else {
			altText = tags.getTag("Inactive");
			iconName = "ball_red.gif";
		}
		return "<img src='"+request.getContextPath()+"/resources/img/common/"+iconName+"' alt='"+altText+"' style='border: 0px;'>";
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
