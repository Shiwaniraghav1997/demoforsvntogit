package com.bayer.bhc.doc41webui.integration.sap.docservice;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.bayer.bhc.doc41webui.web.test.DocServiceUploadTestClientImpl;

public class DocServiceUploadTestClient {
    
    public static void main(String[] args) throws ClientProtocolException, IOException {
        DocServiceUploadTestClientImpl dsuci = new DocServiceUploadTestClientImpl();
        new DocServiceUploadTestClientImpl().run(dsuci.URL_PREFIX, dsuci.VENDOR, dsuci.REF_NO, dsuci.UPL_PATH, dsuci.UPL_FILE, dsuci.TYPE);
    }
    
}
