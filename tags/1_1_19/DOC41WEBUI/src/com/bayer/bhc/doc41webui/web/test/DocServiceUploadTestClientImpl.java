package com.bayer.bhc.doc41webui.web.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.StringTool;

public class DocServiceUploadTestClientImpl {
    
    public String URL_PREFIX = "http://localhost:9080/doc41";
    public String VENDOR = "0007150493";
    public String REF_NO = "0060006010";
    public String UPL_PATH = "C:\\eclipse\\BOEAH\\workspace\\DOC41WEBUI\\docs\\";
    public String UPL_FILE = "rfcReport.pdf";
    public String TYPE = "AWB";
    public String CWID = "ABCDE";

    private StringBuffer out = new StringBuffer();
   
    public DocServiceUploadTestClientImpl() {
        Properties mTestPr = ConfigMap.get().getSubCfg("test","docservice");
        URL_PREFIX  = mTestPr.getProperty("urlprefix"   , URL_PREFIX);
        VENDOR      = mTestPr.getProperty("vendor"      , VENDOR    );
        REF_NO      = mTestPr.getProperty("refno"       , REF_NO    );
        UPL_PATH    = mTestPr.getProperty("path"        , UPL_PATH  );
        UPL_FILE    = mTestPr.getProperty("file"        , UPL_FILE  );
        TYPE        = mTestPr.getProperty("type"        , TYPE      );
        CWID        = mTestPr.getProperty("type"        , CWID      );
    }
    
    public void println(String mStr) {
        out.append(""+mStr+"\n");
        System.out.println(mStr);
    }

    public String run(String pUrlPrefix, String pVendor, String pRefNo, String pUploadPath, String pUploadFile, String pType, String pCwid) throws ClientProtocolException, IOException {

        InputStream fileInputStream = null;
        try{
        fileInputStream = new FileInputStream(pUploadPath+pUploadFile);
        
        HttpEntity result = testUpload(pUrlPrefix,fileInputStream,pUploadFile,pType,pVendor,pRefNo, pCwid);
        char[] mBuf = new char[20000];
        new InputStreamReader(result.getContent()).read(mBuf);
        println(""+ new String(mBuf));
        } finally {
            if(fileInputStream!=null){
                fileInputStream.close();
            }
        }
        return out.toString();
    }
    
    private HttpEntity testUpload(String urlPrefix, InputStream fileInputStream,
            String fileName, String type, String vendorNumber,
            String refnumber, String pCwid) throws ClientProtocolException, IOException {

        String url=getUploadUrl(urlPrefix);
        HttpPost httpPost = new HttpPost(url);
        addUser(httpPost, pCwid);
        println("Executing request " + httpPost.getRequestLine());
        
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try{
            
          
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            entity.addPart("fileName", new StringBody(fileName));
            entity.addPart("type", new StringBody(type));
            entity.addPart("vendorNumber", new StringBody(vendorNumber));
            entity.addPart("objectId", new StringBody(refnumber));
            
            InputStreamBody fileBody = new InputStreamBody(fileInputStream, "application/octet-stream",fileName);
            entity.addPart("file", fileBody);

            httpPost.setEntity(entity);
            HttpResponse response = httpclient.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                Doc41Log.get().error(this, null, "UploadTest failed, response: " + statusLine.getReasonPhrase() + " (" + statusLine.getStatusCode() + ")  -  " + statusLine.getProtocolVersion() + "\n" + StringTool.list(response.getAllHeaders(), "\n", false) );
                throw new HttpResponseException(
                        statusLine.getStatusCode(),
                        statusLine.getReasonPhrase());
            }
            HttpEntity result = response.getEntity();
            return result;
            
            
        } finally {
            httpPost.releaseConnection();
        }
    }


    private void addUser(HttpRequestBase request, String pCwid) {
        String role = "doc41_carr";
        request.setHeader("docservice-user", pCwid);
        request.setHeader("docservice-password", StringTool.decodePassword(ConfigMap.get().getSubCfg("doc41controller", "docservicecheck").getProperty("pwd_"+role)));
        request.setHeader("docservice-role", role);
    }


    private String getUploadUrl(String urlPrefix) {
        return urlPrefix+"/docservice/sdupload";
    }

}
