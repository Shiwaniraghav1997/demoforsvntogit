package com.bayer.bhc.doc41webui.integration.sap.docservice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;

import com.bayer.bhc.doc41webui.domain.BdsServiceDocumentEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class DocserviceTestClient {


    private static final String URL_PREFIX = "http://localhost:9080/doc41";

    public static void main(String[] args) throws ClientProtocolException, IOException {
        DocserviceTestClient client = new DocserviceTestClient();

        List<BdsServiceDocumentEntry> documents = client.testSearch(URL_PREFIX,"0007150493","0060006010");
        
        int i=0;
        for (BdsServiceDocumentEntry doc : documents) {
            String filename = doc.getCustomizedValuesByKey().get(BdsServiceDocumentEntry.ATTRIB_NAME_FILENAME);
            OutputStream outputStream = new FileOutputStream("C:\\eclipse\\BOEAH\\wasproj\\doc41webui\\temp\\"+i+"_"+filename);
            client.testDownload(URL_PREFIX,doc,outputStream);
            i++;
        }
    }

    private List<BdsServiceDocumentEntry> testSearch(String urlPrefix,String vendor, String refnumber) throws ClientProtocolException, IOException {
        String url=getSearchUrl(urlPrefix, vendor, refnumber);

        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Accept", "application/json");

        System.out.println("Executing request " + httpget.getRequestLine());

        DefaultHttpClient httpclient = new DefaultHttpClient();
        try{
            final Type listType = new TypeToken<ArrayList<BdsServiceDocumentEntry>>() {}.getType();
            final GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            });
            
            ResponseHandler<List<BdsServiceDocumentEntry>> responseHandler = new ResponseHandler<List<BdsServiceDocumentEntry>>() {

                @Override
                public List<BdsServiceDocumentEntry> handleResponse(
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
                    return gson.fromJson(reader, listType);
                }
            };

            List<BdsServiceDocumentEntry> responseBody = httpclient.execute(httpget, responseHandler);
            for (BdsServiceDocumentEntry entry : responseBody) {
                System.out.println(""+entry+"\n");
            }
            return responseBody;
        } finally {
            httpget.releaseConnection();
        }
    }
    
    private void testDownload(String urlPrefix,BdsServiceDocumentEntry doc, OutputStream target) throws ClientProtocolException, IOException {
        String url=getDownloadUrl(urlPrefix, doc.getKey());

        HttpGet httpget = new HttpGet(url);

        System.out.println("Executing request " + httpget.getRequestLine());

        DefaultHttpClient httpclient = new DefaultHttpClient();
        try{
            HttpResponse response = httpclient.execute(httpget);
            InputStream inputStream = response.getEntity().getContent();
            IOUtils.copy(inputStream, target);
            
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
