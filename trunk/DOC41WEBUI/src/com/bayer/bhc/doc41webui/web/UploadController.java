package com.bayer.bhc.doc41webui.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.bayer.bhc.doc41webui.container.Delivery;
import com.bayer.bhc.doc41webui.container.UploadForm;

@Controller
@SessionAttributes("uploadForm")
public class UploadController extends AbstractDoc41Controller {

	@RequestMapping(value="/documents/new",method = RequestMethod.GET)
	public UploadForm getNew(@MatrixVariable(required=true) String type,@MatrixVariable String deliverynumber, @MatrixVariable String fileid){
		//TODO implement
		return null;
	}
	
	
	@RequestMapping(value="/documents/opendeliveries",method = RequestMethod.GET)
	public List<Delivery> getOpenDeliveries(){
		//TODO
		
		//link nach "new" mit deliverynumber in jsp, deshalb kein post
		
		return null;
	}
	
	@RequestMapping(value="/documents/upload",method = RequestMethod.POST)
	public String postUpload(@ModelAttribute UploadForm uploadForm,BindingResult result){ //ggf. kein modelattribute wegen sessionattribute
		if (result.hasErrors()) {
            return "/documents/new";
        }
		//TODO
		return "redirect:/documents/new";
	}
	
}
