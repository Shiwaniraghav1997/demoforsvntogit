package com.bayer.bhc.doc41webui.web.documents;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;

@Controller
public class UploadSuccessfulController extends AbstractDoc41Controller {

	
	@RequestMapping(value="/documents/uploadsuccess",method = RequestMethod.GET)
	public Map<String, String> download(@RequestParam String type, @RequestParam String uploadurl) throws Doc41BusinessException{
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", type);
		map.put("uploadurl", uploadurl);
		return map;
	}
}
