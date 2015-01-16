package com.bayer.bhc.doc41webui.usecase;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;

public class DocClassNotAllowed extends Doc41BusinessException {

    private String docClass;
    private String allowedDocClass;
    
    public DocClassNotAllowed(String docClass,
            String allowedDocClass) {
        super("Doc class "+docClass+" is not allowed ("+allowedDocClass+")");
        this.docClass = docClass;
        this.allowedDocClass = allowedDocClass;
    }
    
    public String getDocClass() {
        return docClass;
    }
    
    public String getAllowedDocClass() {
        return allowedDocClass;
    }
}
