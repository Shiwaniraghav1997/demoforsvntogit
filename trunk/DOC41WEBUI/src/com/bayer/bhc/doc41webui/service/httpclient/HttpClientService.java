package com.bayer.bhc.doc41webui.service.httpclient;

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
import org.springframework.web.multipart.MultipartFile;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;

@Component
public class HttpClientService {
	
	@Autowired
	private RestTemplate restTemplate;

	public void uploadDocumentToUrl(URI putUrl, MultipartFile file) throws Doc41ServiceException {
		try {
			final InputStream fis = file.getInputStream();
			final RequestCallback requestCallback = new RequestCallback() {
			     @Override
			    public void doWithRequest(final ClientHttpRequest request) throws IOException {
			        request.getHeaders().add("Content-type", "application/octet-stream");
			        IOUtils.copy(fis, request.getBody());
			     }
			};
			final ResponseExtractor<String> responseExtractor =
			    new HttpMessageConverterExtractor<String>(String.class, restTemplate.getMessageConverters());
			restTemplate.execute(putUrl, HttpMethod.PUT, requestCallback, responseExtractor);
		} catch (IOException e) {
			throw new Doc41ServiceException("uploadDocumentToUrl", e);
		}
	}

}
