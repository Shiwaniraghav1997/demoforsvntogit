package com.bayer.bhc.doc41webui.service.httpclient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;

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
			        request.getHeaders().add("Content-type", mimeType);
			        IOUtils.copy(ffis, request.getBody());
			     }
			};
			final ResponseExtractor<String> responseExtractor =
			    new HttpMessageConverterExtractor<String>(String.class, restTemplate.getMessageConverters());
			restTemplate.execute(putUrl, HttpMethod.PUT, requestCallback, responseExtractor);
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

}
