package com.bayer.bhc.doc41webui.domain;

public class BdsServiceUploadDocumentResult {

    private BdsServiceError[] errors;
    public BdsServiceError[] getErrors() {
        return errors;
    }
    public void setErrors(BdsServiceError[] errors) {
        this.errors = errors;
    }
    public BdsServiceUploadDocumentResult(BdsServiceError[] errors) {
        super();
        this.errors = errors;
    }
    public BdsServiceUploadDocumentResult() {
        super();
    }
    
    public boolean hasError(){
        return errors !=null && errors.length>0;
    }
}
