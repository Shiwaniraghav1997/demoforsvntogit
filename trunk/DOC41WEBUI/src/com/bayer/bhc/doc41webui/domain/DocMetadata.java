package com.bayer.bhc.doc41webui.domain;

import java.util.List;

public class DocMetadata {

	private DocTypeDef docDef;
	private List<Attribute> attributes;
	private ContentRepositoryInfo contentRepository;
	private boolean hasFileName;
	
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
	
	public void setHasFileName(boolean hasFileName) {
		this.hasFileName = hasFileName;
	}
	
	public boolean getHasFileName() {
		return hasFileName;
	}
	
	public DocMetadata(DocTypeDef docDef){
		this.docDef = docDef;
	}

	@Override
	public String toString() {
		return "DocMetadata [docDef=" + docDef + ", attributes=" + attributes
				+ ", contentRepository=" + contentRepository + "]";
	}
	
	
}
