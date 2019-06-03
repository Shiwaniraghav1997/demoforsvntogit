package com.bayer.bhc.doc41webui.domain;

import java.util.List;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.ecim.foundation.basic.StringTool;

public class DocMetadata {
	
	private DocTypeDef docDef;
	private List<Attribute> attributes;
	private ContentRepositoryInfo contentRepository;
	
	public DocTypeDef getDocDef() {
		return docDef;
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	
	public ContentRepositoryInfo getContentRepository() {
		return contentRepository;
	}
	
	public void setContentRepository(ContentRepositoryInfo contentRepository) {
		this.contentRepository = contentRepository;
	}
	
	public boolean isFileNameAttribAvailable() {
		for (Attribute attribute : attributes) {
			String name = attribute.getName();
			if(StringTool.equals(name, getFileNameAttibKey())){
				return true;
			}
		}
		return false;
	}
	
	public DocMetadata(DocTypeDef docDef){
		this.docDef = docDef;
	}

	@Override
	public String toString() {
		return "DocMetadata [docDef=" + docDef + ", attributes="
				+ attributes + ", contentRepository="
				+ contentRepository + "]";
	}

	public String getFileNameAttibKey() {
		return Doc41Constants.ATTRIB_NAME_FILENAME;
	}
	
	
}
