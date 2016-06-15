package com.bayer.bhc.doc41webui.web.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;

import com.bayer.bhc.doc41webui.domain.BdsServiceDocumentEntry;
import com.bayer.bhc.doc41webui.domain.BdsServiceError;
import com.bayer.bhc.doc41webui.domain.BdsServiceSearchDocumentsResult;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.StringTool;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class DocserviceTestClientImpl {

    public String URL_PREFIX = "http://localhost:9080/doc41";
    public String VENDOR = "0007150493";
    public String REF_NO = "0060006010";
    public String DOWNL_PATH = "C:\\eclipse\\BOEAH\\wasproj\\doc41webui\\temp\\";
    public String CWID = "ABCDE";

    private StringBuffer out = new StringBuffer();
   
    public DocserviceTestClientImpl() {
        Properties mTestPr = ConfigMap.get().getSubCfg("test","docservice");
        URL_PREFIX  = mTestPr.getProperty("urlprefix"   , URL_PREFIX);
        VENDOR      = mTestPr.getProperty("vendor"      , VENDOR    );
        REF_NO      = mTestPr.getProperty("refno"       , REF_NO    );
        DOWNL_PATH  = mTestPr.getProperty("path"      , DOWNL_PATH);
    }

    public void println(String mStr) {
        out.append(""+mStr+"\n");
        System.out.println(mStr);
    }
    
    public String run(String pUrlPrefix, String pVendor, String pRefNo, String pDownloadPath, String pCwid) throws ClientProtocolException, IOException {

        Properties mTestPr = ConfigMap.get().getSubCfg("test","docservice");
        URL_PREFIX  = mTestPr.getProperty("urlprefix"   , URL_PREFIX);
        VENDOR      = mTestPr.getProperty("vendor"      , VENDOR    );
        REF_NO      = mTestPr.getProperty("refno"       , REF_NO    );
        DOWNL_PATH  = mTestPr.getProperty("path"        , DOWNL_PATH);
        
        List<BdsServiceDocumentEntry> documents = testSearch(pUrlPrefix,pVendor ,pRefNo, pCwid);
        
        int i=0;
        if (documents != null) {
            println("Found " + documents.size() + " documents, download to directory: " + pDownloadPath);
            File mDownPath = new File(pDownloadPath);
            if (!mDownPath.exists()) {
                mDownPath.mkdirs();
            }
            for (BdsServiceDocumentEntry doc : documents) {
                String filename = doc.getCustomizedValuesByKey().get(BdsServiceDocumentEntry.ATTRIB_NAME_FILENAME);
                FileOutputStream outputStream = null;
                try{
                    String pName = pDownloadPath+i+"_"+filename;
                    outputStream = new FileOutputStream(pName);
                    testDownload(URL_PREFIX,doc,outputStream, pName, pCwid);
                } finally {
                    if(outputStream!=null){
                        outputStream.close();
                    }
                }
                i++;
            }
        }
        return out.toString();
    }

    private List<BdsServiceDocumentEntry> testSearch(String urlPrefix,String vendor, String refnumber, String pCwid) throws ClientProtocolException, IOException {
        String url=getSearchUrl(urlPrefix, vendor, refnumber);

        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Accept", "application/json");
        addUser(httpget, pCwid);

        println("Executing request " + httpget.getRequestLine());

        DefaultHttpClient httpclient = new DefaultHttpClient();
        try{
//          final Type listType = new TypeToken<ArrayList<BdsServiceDocumentEntry>>() {}.getType();
            final Type objType = new TypeToken<BdsServiceSearchDocumentsResult>(){}.getType();
            final GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            });
            
            ResponseHandler/*<List<BdsServiceDocumentEntry>>*/<BdsServiceSearchDocumentsResult> responseHandler = new ResponseHandler/*<List<BdsServiceDocumentEntry>>*/<BdsServiceSearchDocumentsResult>() {

                @Override
                public /*List<BdsServiceDocumentEntry>*/ BdsServiceSearchDocumentsResult handleResponse(
                        final HttpResponse response) throws IOException {
                    StatusLine statusLine = response.getStatusLine();
                    HttpEntity entity = response.getEntity();
                    if (statusLine.getStatusCode() >= 300) {
                        throw new HttpResponseException(
                                statusLine.getStatusCode(),
                                statusLine.getReasonPhrase());
                    }
                    if (entity == null) {
                        throw new ClientProtocolException("Response contains no content");
                    }
                    Gson gson = builder.create();
                    ContentType contentType = ContentType.getOrDefault(entity);
                    Charset charset = contentType.getCharset();
                    Reader reader = new InputStreamReader(entity.getContent(), charset);

                    return gson.fromJson(reader, /*listType*/ objType);
                }
            };

            BdsServiceSearchDocumentsResult res = httpclient.execute(httpget, responseHandler);
            List<BdsServiceDocumentEntry> responseBody = res.getEntries();
            if (responseBody != null) {
                for (BdsServiceDocumentEntry entry : responseBody) {
                    println(""+entry+"\n");
                }
            } else {
                println("result: NULL");
            }
            BdsServiceError[] errors = res.getErrors();
            if ((errors != null) && (errors.length > 0)) {
                println("\nErrors:");
                for (BdsServiceError mErr : errors ) {
                    println("" + mErr.getErrorCode() + (mErr.isFieldError() ? " - " + mErr.getField() + ": '" + mErr.getRejectedValue() + "'" : "") );
                }
            }
            return responseBody;
        } finally {
            httpget.releaseConnection();
        }
    }

    private void addUser(HttpRequestBase request, String pCwid) {
        String role = "doc41_carr";
        request.setHeader("docservice-user", pCwid);
        request.setHeader("docservice-password", StringTool.decodePassword(ConfigMap.get().getSubCfg("doc41controller", "docservicecheck").getProperty("pwd_"+role)));
        request.setHeader("docservice-role", role);
    }
    
    private void testDownload(String urlPrefix,BdsServiceDocumentEntry doc, FileOutputStream target, String pName, String pCwid) throws ClientProtocolException, IOException {
        String url=getDownloadUrl(urlPrefix, doc.getKey());

        HttpGet httpget = new HttpGet(url);
        addUser(httpget, pCwid);

        println("Executing request " + httpget.getRequestLine());

        DefaultHttpClient httpclient = new DefaultHttpClient();
        try{
            HttpResponse response = httpclient.execute(httpget);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(
                        statusLine.getStatusCode(),
                        statusLine.getReasonPhrase());
            }
            InputStream inputStream = response.getEntity().getContent();
            IOUtils.copy(inputStream, target);
            println("Download OK: " + pName);
        } finally {
            httpget.releaseConnection();
        }
    }

    private String getSearchUrl(String urlPrefix, String vendor,
            String refnumber) {
        return urlPrefix+"/docservice/sdsearch.json?vendor="+vendor+"&refnumber="+refnumber;
    }
    
    private String getDownloadUrl(String urlPrefix, String key) {
        return urlPrefix+"/docservice/download?key="+key;
    }

}
