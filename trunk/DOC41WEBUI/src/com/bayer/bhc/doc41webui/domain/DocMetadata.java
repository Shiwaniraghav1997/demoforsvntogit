package com.bayer.bhc.doc41webui.domain;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.ecim.foundation.basic.StringTool;

public class DocMetadata {
	
	private DocTypeDef docDef;
	private List<Attribute> attributesWithFileName;
	private List<Attribute> attributesWithoutFileName;
	private ContentRepositoryInfo contentRepository;
	private boolean isFileNameAttribAvailable;
	
	public DocTypeDef getDocDef() {
		return docDef;
	}
	
	public List<Attribute> getAttributes(boolean filterFileName) {
		if(filterFileName){
			return attributesWithoutFileName;
		} else {
			return attributesWithFileName;
		}
	}
	
	public void initAttributes(List<Attribute> attributes) {
		attributesWithFileName = new ArrayList<Attribute>();
		attributesWithoutFileName = new ArrayList<Attribute>();
		for (Attribute attribute : attributes) {
			if(!StringTool.equals(attribute.getName(), Doc41Constants.ATTRIB_NAME_FILENAME)){
				attributesWithoutFileName.add(attribute);
			} else {
				isFileNameAttribAvailable = true;
			}
			attributesWithFileName.add(attribute);
		}
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
		return "DocMetadata [docDef=" + docDef + ", attributesWithFileName="
				+ attributesWithFileName + ", attributesWithoutFileName="
				+ attributesWithoutFileName + ", contentRepository="
				+ contentRepository + ", isFileNameAttribAvailable="
				+ isFileNameAttribAvailable + "]";
	}

	public String getFileNameAttibKey() {
		return Doc41Constants.ATTRIB_NAME_FILENAME;
	}
	
	
}
