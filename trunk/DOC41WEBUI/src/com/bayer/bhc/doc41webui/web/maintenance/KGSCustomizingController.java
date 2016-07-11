package com.bayer.bhc.doc41webui.web.maintenance;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.container.SelectionItem;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.ContentRepositoryInfo;
import com.bayer.bhc.doc41webui.domain.DocMetadata;
import com.bayer.bhc.doc41webui.domain.DocTypeDef;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.web.AbstractDoc41Controller;
import com.bayer.ecim.foundation.basic.InitException;

@Controller
public class KGSCustomizingController extends AbstractDoc41Controller {
	
	@Autowired
	protected DocumentUC documentUC;
	
    /**
     * Get a reqired permission to perform a certain operation, can be overwritten to enforce specific permission
     * @param usr
     * @param request 
     * @return null, if no specific permission required - or a list of permissions of which one is required
     * @throws Doc41BusinessException 
     */
    @Override
    protected String[] getReqPermission(User usr, HttpServletRequest request) throws Doc41BusinessException {
        return new String[] {Doc41Constants.PERMISSION_KGSCUSTOMIZING};
//		return usr.hasPermission(Doc41Constants.PERMISSION_KGSCUSTOMIZING);
	}
	
	@RequestMapping(value="/maintenance/kgsCustomizing", method=RequestMethod.GET)
	public void get(ModelMap model, @RequestParam(value="sapDocType",required=false) String sapDocType) throws Doc41TechnicalException {
		List<SelectionItem> docTypes;
		// RFC Dump
		String docTypeDump = "";
		try {
			// RFC Names Selection Box
			docTypes = documentUC.getDocMetadataSelectionItems();
			
			if (!StringUtils.isBlank(sapDocType)) {
				DocMetadata docMetadata = documentUC.getDocMetadataBySapDocType(sapDocType);
				docTypeDump = dumpMetadata(docMetadata);
			}
		} catch (InitException e) {
			docTypeDump = "InitException:\n" + e.getMessage() + "\n\n" + ExceptionUtils.getStackTrace(e);
			docTypes = Collections.emptyList();
		} catch (Exception e) {
			docTypeDump = "Exception:\n" + e.getMessage() + "\n\n" + ExceptionUtils.getStackTrace(e);
			docTypes = Collections.emptyList();
		}

		Form form = new Form();
		form.setSapDocType(sapDocType);
		form.setSapDocTypes(docTypes);
		form.setDump(docTypeDump);
		model.addAttribute("command", form);
	}

	private String dumpMetadata(DocMetadata docMetadata) {
		StringBuilder sb = new StringBuilder();
		sb.append("-------------------------------\n");
		sb.append("DocDef\n");
		sb.append("------\n");
		DocTypeDef docDef = docMetadata.getDocDef();
		sb.append("d41id: ");
		sb.append(docDef.getD41id());
		sb.append('\n');
		
		sb.append("technicalId: ");
		sb.append(docDef.getTechnicalId());
		sb.append('\n');
		
		sb.append("description: ");
		sb.append(docDef.getDescription());
		sb.append('\n');
		
		sb.append("isDvs: ");
		sb.append(docDef.isDvs());
		sb.append('\n');
		
		sb.append("sapObjList: ");
		appendStringCol(sb,docDef.getSapObjList());
		sb.append('\n');
		
		sb.append("translations:\n");
		appendStringMap(sb,docDef.getTranslations());
		
		sb.append("-------------------------------\n");
		sb.append("Content Repository\n");
		sb.append("------------------\n");
		ContentRepositoryInfo contentRepository = docMetadata.getContentRepository();
		
		sb.append("contentRepository: ");
		sb.append(contentRepository.getContentRepository());
		sb.append('\n');
		
		sb.append("allowedDocClass: ");
		sb.append(contentRepository.getAllowedDocClass());
		sb.append('\n');
		
		sb.append("-------------------------------\n");
		sb.append("Attributes\n");
		sb.append("------------------\n");
		
		List<Attribute> attributes = docMetadata.getAttributes();
		for (Attribute attribute : attributes) {
			appendAttribute(sb,attribute);
			sb.append('\n');
		}
		
		sb.append("-------------------------------\n");
		return sb.toString();
	}

	private void appendAttribute(StringBuilder sb, Attribute attribute) {
		sb.append("name: ");
		sb.append(attribute.getName());
		sb.append('\n');
		
		sb.append("seqNumber: ");
		sb.append(attribute.getSeqNumber());
		sb.append('\n');
		
		sb.append("desc: ");
		sb.append(attribute.getDesc());
		sb.append('\n');
		
		sb.append("mandatory: ");
		sb.append(attribute.getMandatory());
		sb.append('\n');
		
		sb.append("translations:\n");
		appendStringMap(sb,attribute.getTranslations());
		
		sb.append("values: ");
		appendStringCol(sb,attribute.getValues());
		sb.append('\n');
		
		sb.append("tempLabel: ");
		sb.append(attribute.getTempLabel());
		sb.append('\n');
		
	}

	private void appendStringMap(StringBuilder sb,
			Map<String, String> map) {
		if(map==null){
			sb.append("  null\n");
		} else {
			for (Entry<String, String> entry : map.entrySet()) {
				sb.append("  ");
				sb.append(entry.getKey());
				sb.append("=");
				sb.append(""+entry.getValue());
				sb.append('\n');
			}
		}
	}

	private void appendStringCol(StringBuilder sb, List<String> stringList) {
		if(stringList==null){
			sb.append("null");
		} else {
			boolean first=true;
			for (String stringElement : stringList) {
				if(first){
					first = false;
				} else {
					sb.append(',');
				}
				sb.append(stringElement);
			}
		}
	}

	@RequestMapping(value="/maintenance/selectDocType", method=RequestMethod.POST)
	public String select(HttpServletRequest request, @ModelAttribute Form form, BindingResult result) {
		return "redirect:/maintenance/kgsCustomizing?sapDocType="+form.getSapDocType();
	}



	public static class Form {
		private String sapDocType;
		private List<SelectionItem> sapDocTypes;
		private String dump;

		public String getSapDocType() {
			return sapDocType;
		}

		public void setSapDocType(String sapDocType) {
			this.sapDocType = sapDocType;
		}

		public List<SelectionItem> getSapDocTypes() {
			return sapDocTypes;
		}

		public void setSapDocTypes(List<SelectionItem> sapDocTypes) {
			this.sapDocTypes = sapDocTypes;
		}

		public String getDump() {
			return dump;
		}

		public void setDump(String docTypeDump) {
			this.dump = docTypeDump;
		}
	}
}
