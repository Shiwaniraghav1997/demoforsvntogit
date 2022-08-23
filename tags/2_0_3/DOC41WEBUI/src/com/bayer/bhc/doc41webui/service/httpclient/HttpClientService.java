package com.bayer.bhc.doc41webui.service.httpclient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

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
	
//	public void uploadDocumentToUrl(URI putUrl, File localFile,final String mimeType) throws Doc41ServiceException {
//		try {
//			HttpClient client = new DefaultHttpClient();
//		    HttpPut httpPut = new HttpPut(putUrl);
//
//		    MultipartEntity mpEntity = new MultipartEntity(
//		            HttpMultipartMode.BROWSER_COMPATIBLE);
//		    FileBody contentBody = new FileBody(localFile, mimeType);
//		    mpEntity.addPart("data", contentBody);
//		    httpPut.setEntity(mpEntity);
//		    HttpResponse httpResponse = null;
//		        httpResponse = client.execute(httpPut);
//		} catch (IOException e) {
//			throw new Doc41ServiceException("uploadDocumentToUrl", e);
//		} 
//	}
	
//	public void uploadDocumentToUrl(URI putUrl, File localFile,final String mimeType) throws Doc41ServiceException {
//		InputStream inputStream = null;
//		OutputStream outputStream = null;
//		byte[] buffer = new byte[1024];
//		try {
//			inputStream = new FileInputStream(localFile);
//
//			URL url = putUrl.toURL();
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setRequestProperty("Content-Type", mimeType);
//			connection.setRequestMethod("PUT");
//			connection.setDoOutput(true);
//
//			outputStream = connection.getOutputStream();
//			int read;
//			while ((read = inputStream.read(buffer)) != -1) {
//				outputStream.write(buffer, 0, read);
//			}
//
//			outputStream.flush();
//			outputStream.close();
//			int code = connection.getResponseCode();
//			System.out.println("put response code: "+code);
//		} catch (IOException e) {
//			throw new Doc41ServiceException("uploadDocumentToUrl", e);
//		} finally {
//			if(outputStream!=null){
//				try {
//					outputStream.close();
//				} catch (IOException e) {
//					throw new Doc41ServiceException("uploadDocumentToUrl", e);
//				}
//			}
//			if(inputStream!=null){
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					throw new Doc41ServiceException("uploadDocumentToUrl", e);
//				}
//			}
//		}
//	}	
	
    /**
     * Create and initialize an ZIP OutputStream to return a zip archive of files.
     * @param pTargetResponse
     * @param pFileName Filename to be generated for the ZIP.
     * @return the prepared ZIP OutputStream
     * @throws IOException
     */
	public ZipOutputStream createZipResponse(HttpServletResponse pTargetResponse, String pFileName) throws IOException {
	    pTargetResponse.setContentType("application/zip");
        String encodedFileName = StringTool.encodeURL(pFileName, "UTF-8");
        pTargetResponse.setHeader("Content-Disposition", "attachment; filename=\""+pFileName+"\"; filename*=UTF-8''"+encodedFileName);
        pTargetResponse.setDateHeader("Expires", 0);
        pTargetResponse.setCharacterEncoding("UTF-8");
//      pTargetResponse.setContentLength((int)headers.getContentLength());
	    return new ZipOutputStream(pTargetResponse.getOutputStream());
	}

	
	/**
	 * Add a file to a prepared download outputstream.
	 * see: Doc41ClientAbortException
     * @param pGetUrl The url to get the file from SAP (to be added to the zip).
	 * @param pZOs the zip output stream
	 * @param pDocId
	 * @param pFileName
     * @return the Status responded by the SAP Stream
     * @throws RuntimeException (e.g. ResourceAccessException encapsulation ClientAbortException = IOException)
	 */
	public String addFileToZipDownload(URI pGetUrl, final ZipOutputStream pZOs, final String pDocId, final String pFileName/*, HashMap<String, Integer> pAddedFiles*/) {
       final ResponseExtractor<String> responseExtractor = new ResponseExtractor<String>() {
            @Override
            public String extractData(ClientHttpResponse response) throws IOException {
                HttpHeaders headers = response.getHeaders();
                MediaType contentType = headers.getContentType();
                boolean mRetry = false;
                int mRetryCnt = -1;
                do {
                    mRetryCnt++;
                    String generateFileName = generateFileName(pFileName,pDocId,contentType, mRetryCnt);
                    try {
                        mRetry = false;
                        pZOs.putNextEntry(new ZipEntry(generateFileName));
                    } catch (ZipException e) {
                        mRetry = e.getMessage().startsWith("duplicate entry:");
                    }
                    if (mRetryCnt >= 99) {
                        return "COPY-ABORT";
                    }
                } while (mRetry);
                //String encodedFileName = StringTool.encodeURL(generateFileName, "UTF-8");
                InputStream inputStream = response.getBody();
                IOUtils.copy(inputStream,pZOs);
                pZOs.closeEntry();
                inputStream.close();
                return response.getStatusText();
            }
          };
          return restTemplate.execute(pGetUrl, HttpMethod.GET, null, responseExtractor);
	}
	
	
	/**
	 * Close the ZIP for Download. This may be extended by a comment for the ZIP.
	 * @param pTargetResponse currently not used. We would like to se the length, but not available...
	 * @param pZOs
	 * @return
	 * @throws IOException 
	 */
	public void closeZipDownload(HttpServletResponse pTargetResponse, ZipOutputStream pZOs, String pComment) throws IOException {
        pZOs.setComment(pComment);
	    pZOs.flush();
	    pZOs.close();
        //pTargetResponse.setContentLength((int)headers.getContentLength());
	}

	
	/**
	 * Forward download to the browser client.
	 * This may end up in a ResourceAccessException(ClientAbortException) due to expiry of the download link (expiry parameter part of the downloadlink).
	 * see: Doc41ClientAbortException
	 * @param getUrl
	 * @param targetResponse
	 * @param docId
	 * @param fileName
	 * @return the status text
	 * @throws RuntimeException (e.g. ResourceAccessException encapsulation ClientAbortException = IOException)
	 */
	public String downloadDocumentToResponse(URI getUrl, final HttpServletResponse targetResponse,final String docId,final String fileName) throws Doc41ServiceException { // e.g. ResourceAccessException extends ... RuntimeException (details see below)
	    final ResponseExtractor<String> responseExtractor = new ResponseExtractor<String>() {
			@Override
			public String extractData(ClientHttpResponse response) throws IOException { // e.g. ClientAbortException extends IOException
				HttpHeaders headers = response.getHeaders();
				MediaType contentType = headers.getContentType();
				targetResponse.setContentType(""+contentType);
				String generateFileName = generateFileName(fileName,docId,contentType, 0);
				String encodedFileName = StringTool.encodeURL(generateFileName, "UTF-8");
				targetResponse.setHeader("Content-Disposition", "attachment; filename=\""+generateFileName+"\"; filename*=UTF-8''"+encodedFileName);
				targetResponse.setDateHeader("Expires", 0);
				targetResponse.setCharacterEncoding("UTF-8");
				targetResponse.setContentLength((int)headers.getContentLength());
				InputStream inputStream = response.getBody();
				IOUtils.copy(inputStream,targetResponse.getOutputStream());
				return response.getStatusText();
			}
		  };
		  return restTemplate.execute(getUrl, HttpMethod.GET, null, responseExtractor);
	}
	
	
	/**
	 * Generate a filename.
	 * Use specified filename is available, use the specified filename.
	 * If filename is not available, use the doc_Id - add extension only, if extension is "pdf" (don't know reason for limitation)
	 * If the filename already exists in an archive (pCopyCount > 0), add ~${pCopyCount} to the filename to make it unique.
	 * @param fileName
	 * @param docId
	 * @param contentType
	 * @param pCopyCount
	 * @return
	 */
    String generateFileName(String fileName,String docId, MediaType contentType, int pCopyCount) {
        if(!StringTool.isTrimmedEmptyOrNull(fileName)) {
            if (pCopyCount > 0) {
                int pIdx = fileName.lastIndexOf('.');
                if (pIdx == -1) {
                    fileName = fileName + "~" + pCopyCount;
                } else {
                    fileName = fileName.substring(0, pIdx) + "~" + pCopyCount + fileName.substring(pIdx);
                }
            }
            return fileName;
        }
        String extension="";
        if(contentType.isCompatibleWith(MediaType.valueOf("application/pdf"))){
            extension=".pdf";
        }
        return docId + ((pCopyCount > 0) ? "~" + pCopyCount : "") + extension;
    }

}
