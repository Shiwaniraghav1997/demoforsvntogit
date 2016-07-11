package com.bayer.bhc.doc41webui.integration.sap.docservice;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.bayer.bhc.doc41webui.web.test.DocserviceTestClientImpl;

public class DocserviceTestClient {

    public static void main(String[] args) throws ClientProtocolException, IOException {
        DocserviceTestClientImpl dsci = new DocserviceTestClientImpl();
        new DocserviceTestClientImpl().run(dsci.URL_PREFIX, dsci.VENDOR, dsci.REF_NO, dsci.DOWNL_PATH, dsci.CWID, true);
    }
    

}
