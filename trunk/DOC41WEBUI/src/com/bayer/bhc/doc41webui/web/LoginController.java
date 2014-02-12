package com.bayer.bhc.doc41webui.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController extends AbstractDoc41Controller {

	@RequestMapping(value="/login/login",method = RequestMethod.GET)
	public void get() {
	    //no data necessary
	}


	@RequestMapping(value="/login/post",method = RequestMethod.POST)
	public String post(@RequestParam String cwid, @RequestParam String password){
		return "redirect:/userprofile/myprofile";
	}
}
