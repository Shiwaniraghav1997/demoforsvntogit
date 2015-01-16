package com.bayer.bhc.doc41webui.usecase;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;

public class UnknownExtensionException extends Doc41BusinessException {
    
    private String extension;

    public UnknownExtensionException(String extension) {
        super("unknown extension :"+extension);
        this.extension = extension;
    }
    
    public String getExtension() {
        return extension;
    }

}
