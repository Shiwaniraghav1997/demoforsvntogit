package com.bayer.bhc.doc41webui.web.documents;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.domain.HitListEntry;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.StringTool;

@Controller
public class DirectDownloadController extends AbstractDoc41Controller {
	private static final int MAX_RESULTS = 100;

	@Autowired
	private DocumentUC documentUC;
	
	@Override
	protected boolean hasPermission(User usr, HttpServletRequest request)
			throws Doc41BusinessException {
		String type = request.getParameter("type");
		if(StringTool.isTrimmedEmptyOrNull(type)){
			throw new IllegalArgumentException("type is missing");
		}
		String permission = documentUC.getDirectDownloadPermission(type);
		return usr.hasPermission(permission);
	}
	
	@RequestMapping(value="/documents/directdownload",method = RequestMethod.GET)
	public void directDownload(@RequestParam String type, @RequestParam String refId,HttpServletResponse response) throws Doc41BusinessException{
		documentUC.checkForDirectDownload(type, refId);
	
		Map<String, String> emptyAttributeValues = new HashMap<String,String>();
		List<HitListEntry> documents = documentUC.searchDocuments(type,Collections.singletonList(refId), emptyAttributeValues, MAX_RESULTS+1, true);
		if(documents==null || documents.isEmpty()){
			throw new Doc41BusinessException("NotFound");
		} else if(documents.size()>MAX_RESULTS){
			throw new Doc41BusinessException("ToManyResults");
		} else {
			Date maxDate = new Date(0);
			HitListEntry newestDocument=null;
			for (HitListEntry document : documents) {
				Date date = document.getArchiveLinkDate();
				if(date.after(maxDate)){
					maxDate = date;
					newestDocument = document;
				}
			}
			String docId = newestDocument.getDocId();
			documentUC.downloadDocument(response,type,docId);
		}
	}
	
}
