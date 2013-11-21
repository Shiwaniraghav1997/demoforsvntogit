package com.bayer.bhc.doc41webui.service.httpclient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.ecim.foundation.basic.StringTool;

@Component
public class HttpClientService {
	
	@Autowired
	private RestTemplate restTemplate;

	public void uploadDocumentToUrl(URI putUrl, File localFile,final String mimeType) throws Doc41ServiceException {
		InputStream fis = null;
		try {
			fis = new BufferedInputStream(new FileInputStream(localFile));
			final InputStream ffis = fis;
			final RequestCallback requestCallback = new RequestCallback() {
			     @Override
			    public void doWithRequest(final ClientHttpRequest request) throws IOException {
			        request.getHeaders().setContentType(MediaType.valueOf(mimeType));
			        IOUtils.copy(ffis, request.getBody());
			     }
			};
			final ResponseExtractor<String> responseExtractor =
			    new HttpMessageConverterExtractor<String>(String.class, restTemplate.getMessageConverters());
			restTemplate.execute(putUrl, HttpMethod.PUT, requestCallback, responseExtractor);
//			restTemplate.put(putUrl, localFile);
		} catch (IOException e) {
			throw new Doc41ServiceException("uploadDocumentToUrl", e);
		} finally {
			try {
				if(fis!=null){
					fis.close();
				}
			} catch (IOException e) {
				throw new Doc41ServiceException("uploadDocumentToUrl", e);
			}
		}
	}
	
	public String downloadDocumentToResponse(URI getUrl, final HttpServletResponse targetResponse,final String docId,final String fileName) throws Doc41ServiceException{
		final ResponseExtractor<String> responseExtractor = new ResponseExtractor<String>() {
			
			@Override
			public String extractData(ClientHttpResponse response) throws IOException {
				HttpHeaders headers = response.getHeaders();
				
				MediaType contentType = headers.getContentType();
				targetResponse.setContentType(""+contentType);
				targetResponse.setHeader("Content-Disposition", "attachment; filename=\""+generateFileName(fileName,docId,contentType)+"\"");
//		      targetResponse.setHeader("Cache-Control", "no-cache");
//				targetResponse.setHeader("Pragma", "no-cache");
				targetResponse.setDateHeader("Expires", 0);
				targetResponse.setCharacterEncoding("cp1252");
				targetResponse.setContentLength((int)headers.getContentLength());
				
				InputStream inputStream = response.getBody();
				
				IOUtils.copy(inputStream,targetResponse.getOutputStream());
				
				return response.getStatusText();
			}

			private String generateFileName(String fileName,String docId, MediaType contentType) {
				if(!StringTool.isTrimmedEmptyOrNull(fileName)){
					return fileName;
				}
				String extension="";
				if(contentType.isCompatibleWith(MediaType.valueOf("application/pdf"))){
					extension=".pdf";
				}
				return docId+extension;
			}
		};
		return restTemplate.execute(getUrl, HttpMethod.GET, null, responseExtractor);
	}
}
