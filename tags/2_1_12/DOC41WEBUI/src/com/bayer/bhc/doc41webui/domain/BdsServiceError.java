package com.bayer.bhc.doc41webui.domain;

import com.bayer.ecim.foundation.basic.StringTool;

public class BdsServiceError {

    private String errorCode;
    private String field;
    private String rejectedValue;
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public String getRejectedValue() {
        return rejectedValue;
    }
    public void setRejectedValue(String rejectedValue) {
        this.rejectedValue = rejectedValue;
    }
    public boolean isFieldError(){
        return !StringTool.isTrimmedEmptyOrNull(field);
    }
    
}
