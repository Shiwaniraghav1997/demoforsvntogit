package com.bayer.bhc.doc41webui.domain;

import java.util.List;

public class BdsServiceSearchDocumentsResult {

    private BdsServiceError[] errors;
    private List<BdsServiceDocumentEntry> entries;
    public BdsServiceError[] getErrors() {
        return errors;
    }
    public void setErrors(BdsServiceError[] errors) {
        this.errors = errors;
    }
    public List<BdsServiceDocumentEntry> getEntries() {
        return entries;
    }
    public void setEntries(List<BdsServiceDocumentEntry> entries) {
        this.entries = entries;
    }
    public BdsServiceSearchDocumentsResult(BdsServiceError[] errors) {
        super();
        this.errors = errors;
    }
    public BdsServiceSearchDocumentsResult(
            List<BdsServiceDocumentEntry> entries) {
        super();
        this.entries = entries;
    }
    
    public boolean hasError(){
        return errors !=null && errors.length>0;
    }
}
