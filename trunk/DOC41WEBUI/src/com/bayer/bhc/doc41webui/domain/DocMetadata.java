package com.bayer.bhc.doc41webui.domain;

import java.util.List;

import com.bayer.bhc.doc41webui.common.Doc41Constants;

public class DocMetadata {
	
	private DocTypeDef docDef;
	private List<Attribute> attributes;
	private ContentRepositoryInfo contentRepository;
	private boolean isFileNameAttribAvailable;
	
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
		return isFileNameAttribAvailable;
	}
	
	public DocMetadata(DocTypeDef docDef){
		this.docDef = docDef;
	}

	@Override
	public String toString() {
		return "DocMetadata [docDef=" + docDef + ", attributes="
				+ attributes + ", contentRepository="
				+ contentRepository + ", isFileNameAttribAvailable="
				+ isFileNameAttribAvailable + "]";
	}

	public String getFileNameAttibKey() {
		return Doc41Constants.ATTRIB_NAME_FILENAME;
	}
	
	
}
