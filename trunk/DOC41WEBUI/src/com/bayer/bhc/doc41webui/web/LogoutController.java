package com.bayer.bhc.doc41webui.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController extends AbstractDoc41Controller {

	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String get(HttpSession session) {
		session = null;
		return "redirect:/login/login";
	}
}
