package com.bayer.bhc.doc41webui.integration.sap.docservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class DocServiceUploadTestClient {
    
    private static final String URL_PREFIX = "http://localhost:9080/doc41";

    public static void main(String[] args) throws ClientProtocolException, IOException {
        DocServiceUploadTestClient client = new DocServiceUploadTestClient();
        
        String fileName="rfcReport.pdf";
        InputStream fileInputStream = null;
        try{
        fileInputStream = new FileInputStream("C:\\eclipse\\BOEAH\\workspace\\DOC41WEBUI\\docs\\"+fileName);
        String type="AWB";
        String vendorNumber="0007150493";
        String refnumber="0060006010";
        
        HttpEntity result = client.testUpload(URL_PREFIX,fileInputStream,fileName,type,vendorNumber,refnumber);
        System.out.println(""+result);
        } finally {
            if(fileInputStream!=null){
                fileInputStream.close();
            }
        }
    }
    
    
    
    
    
    private HttpEntity testUpload(String urlPrefix, InputStream fileInputStream,
            String fileName, String type, String vendorNumber,
            String refnumber) throws ClientProtocolException, IOException {

        String url=getUploadUrl(urlPrefix);
        HttpPut httpput = new HttpPut(url);
        System.out.println("Executing request " + httpput.getRequestLine());
        
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try{
            
          
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            entity.addPart("fileName", new StringBody(fileName));
            entity.addPart("type", new StringBody(type));
            entity.addPart("vendorNumber", new StringBody(vendorNumber));
            entity.addPart("objectId", new StringBody(refnumber));
            
            InputStreamBody fileBody = new InputStreamBody(fileInputStream, "application/octet-stream",fileName);
            entity.addPart("file", fileBody);

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity result = response.getEntity();
            return result;
            
            
        } finally {
            httpput.releaseConnection();
        }
    }





    private String getUploadUrl(String urlPrefix) {
        return urlPrefix+"/docservice/sdupload";
    }

}
