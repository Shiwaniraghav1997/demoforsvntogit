package com.bayer.bhc.doc41webui.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bayer.bhc.doc41webui.common.Doc41SessionKeys;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.integration.db.SessionDataDAO;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.business.sbcommon.SessionDataDC;

@Controller
public class LogoutController extends AbstractDoc41Controller {

	@Autowired
	private SessionDataDAO sessionDataDAO;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String get(HttpServletRequest request, HttpServletResponse response/*HttpSession session*/) throws IOException {
		User user = (User) request.getSession().getAttribute(Doc41SessionKeys.DOC41_USER);
    	
    	request.getSession().invalidate();

    	if(user!=null){
    		SessionDataDC dbSessionDC = null;
    		try {
    			//fill user for backend
    			UserInSession.put(user);
    			dbSessionDC = sessionDataDAO.getSessionData(user.getCwid());
        		if (dbSessionDC != null){
        			sessionDataDAO.delete(dbSessionDC);
        		}
        		UserInSession.put(null);
        		request.getSession().removeAttribute(Doc41SessionKeys.DOC41_USER);
    		} catch (Doc41TechnicalException e1) {
    			Doc41Log.get().error(this.getClass(), user.getCwid(), "DB SessionData access failed:"+e1.getMessage());
    			Doc41Log.get().error(getClass(),UserInSession.getCwid(),e1);
    		}
    		boolean externalUser = user.isExternalUser();
    		@SuppressWarnings("unchecked")
    		Map<String, String> subConfig = ConfigMap.get().getSubConfig("web", "logout");

    		String url;
    		if(externalUser){
    			url = (String) subConfig.get("external");
    		} else {
    			url = (String) subConfig.get("internal");
    		}

    		if(url!=null){
    			return "redirect:"+url;
    		}
    	}
    	return "/login/login";
	}
}
