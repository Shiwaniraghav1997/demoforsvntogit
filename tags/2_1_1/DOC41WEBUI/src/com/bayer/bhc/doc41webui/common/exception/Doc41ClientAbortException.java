/**
 * 
 */
package com.bayer.bhc.doc41webui.common.exception;

import java.util.HashSet;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.ecim.foundation.basic.ArrayTool;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.StringTool;

/**
 * @author imwif
 *
 */
public class Doc41ClientAbortException extends Doc41ExceptionBase {

    /**
     * Inspects an exception for a portal specific, configurable list ofexceptions known to be ClientAbortExceptions.
     * @param pSuperCause
     * @param message to be used, if detecting a ClientAbortException.
     * @param doAutoLog if true, auto log to log (without trace). if false, do not log (logging done later for more informations about context).
     * @param pStartMs
     * @throws Doc41ClientAbortException (independent of portal technology), if the cause is detected to be one of the configured known ClientAbortExceptions.
     */
    public static void inspectForClientAbortException(Throwable pSuperCause, String message, long pStartMs, boolean doAutoLog) throws Doc41ClientAbortException {
        Throwable cause = (pSuperCause == null) ? null : pSuperCause.getCause();
        String[] mExceptions = StringTool.splitString(ConfigMap.get().getSubCfg("Exceptions").getProperty("ClientAbortExceptions",""), ',');
        HashSet<String> mExMap = new HashSet<String>(ArrayTool.toList(mExceptions));
        if ( mExMap.contains((cause == null) ? " " : cause.getClass().getName()) || mExMap.contains((cause == null) ? " " : cause.getClass().getSimpleName()) ) {
            String mMsg = StringTool.nullToEmpty(pSuperCause.getMessage());
            final String mParm = "&expiration=";
            int mParmP = mMsg.indexOf(mParm);
            int mParmP2 = (mParmP == -1) ? -1 : mMsg.indexOf("&", mParmP+1);
            String mParmExpire = (mParmP2 == -1) ? null : mMsg.substring(mParmP + mParm.length(), mParmP2);
            Doc41Log.get().debug(Doc41ClientAbortException.class, null, pSuperCause.getClass().getSimpleName() + "/" + cause.getClass().getSimpleName() + ": " + mMsg);
            throw new Doc41ClientAbortException(message, cause, mParmExpire, doAutoLog, true);
        }
    }
    
    /* The download may abort due to expiry. Expiry parameter is part of the URL:
    <XIWKA> 
    ******************************** EXCEPTION ********************************
     Nesting(Runtime)Exception (com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException):
     Message: 'fatal error: org.springframework.web.client.RestTemplate.doExecute(RestTemplate.java:498)'

     Trace:
     (100002) com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException: fatal error: org.springframework.web.client.RestTemplate.doExecute(RestTemplate.java:498)
     (100002) org.springframework.web.client.ResourceAccessException: I/O error on GET request for "http://by-saparch01.bayer-ag.com:8121/SAPALink/CacheServer?get&pVersion=0045&contRep=O4&docId=0ACD70D9F159DFE1B52C170127132348&compId=HRL_82146547_03.pdf&accessMode=r&authId=CN%3DP2R,OU%3DI0020247049,OU%3DSAPWebAS,O%3DSAPTrustCommunity,C%3DDE&expiration=20170510102752&secKey=MIIBUQYJKoZIhvcNAQcCoIIBQjCCAT4CAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHATGCAR0wggEZAgEBMG8wZDELMAkGA1UEBhMCREUxHDAaBgNVBAoTE1NBUCBUcnVzdCBDb21tdW5pdHkxEzARBgNVBAsTClNBUCBXZWIgQVMxFDASBgNVBAsTC0kwMDIwMjQ3MDQ5MQwwCgYDVQQDEwNQMlICByARCCcVNVMwCQYFKw4DAhoFAKBdMBgGCSqGSIb3DQEJAzELBgkqhkiG9w0BBwEwHAYJKoZIhvcNAQkFMQ8XDTE3MDUxMDA4Mjc1MlowIwYJKoZIhvcNAQkEMRYEFFNYnZIoQw2I6WeyxAVqV%2Beknow9MAkGByqGSM44BAMELjAsAhQyq%2FyFcUPEHftVlx%2FWF%2FJUvu9PugIUCGhfHQoPR7JWJENe4G%2BjxwdZFZ8%3D":null; nested exception is ClientAbortException:  java.io.IOException
     (100002) org.apache.catalina.connector.ClientAbortException: 
     <<< DOC41-PROD21-15BF178BA1D >>>
     java.io.IOException
        at org.apache.coyote.ajp.AjpAprProcessor.flush(AjpAprProcessor.java:1241)
        at org.apache.coyote.ajp.AjpAprProcessor$SocketOutputBuffer.doWrite(AjpAprProcessor.java:1328)
        at org.apache.coyote.Response.doWrite(Response.java:560)
        at org.apache.catalina.connector.OutputBuffer.realWriteBytes(OutputBuffer.java:353)
        at org.apache.tomcat.util.buf.ByteChunk.flushBuffer(ByteChunk.java:434)
        at org.apache.tomcat.util.buf.ByteChunk.append(ByteChunk.java:349)
        at org.apache.catalina.connector.OutputBuffer.writeBytes(OutputBuffer.java:381)
        at org.apache.catalina.connector.OutputBuffer.write(OutputBuffer.java:370)
        at org.apache.catalina.connector.CoyoteOutputStream.write(CoyoteOutputStream.java:89)
        at org.apache.commons.io.IOUtils.copyLarge(IOUtils.java:1384)
        at org.apache.commons.io.IOUtils.copy(IOUtils.java:1357)
        at com.bayer.bhc.doc41webui.service.httpclient.HttpClientService$3.extractData(HttpClientService.java:215)
        at com.bayer.bhc.doc41webui.service.httpclient.HttpClientService$3.extractData(HttpClientService.java:202)
        at org.springframework.web.client.RestTemplate.doExecute(RestTemplate.java:491)
        at org.springframework.web.client.RestTemplate.execute(RestTemplate.java:460)
        at com.bayer.bhc.doc41webui.service.httpclient.HttpClientService.downloadDocumentToResponse(HttpClientService.java:219)
        at com.bayer.bhc.doc41webui.usecase.DocumentUC.downloadDocument(DocumentUC.java:834)
        at com.bayer.bhc.doc41webui.web.documents.SearchController.download(SearchController.java:574)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
        at java.lang.reflect.Method.invoke(Method.java:597)
        at org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)
        at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)
        at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)
        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandleMethod(RequestMappingHandlerAdapter.java:745)
        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:686)
        at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)
        at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:925)
        at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:856)
        at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:936)
        at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:827)
        at javax.servlet.http.HttpServlet.service(HttpServlet.java:617)
        at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:812)
        at javax.servlet.http.HttpServlet.service(HttpServlet.java:723)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:290)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)
        at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)
        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:235)
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)
        at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:233)
        at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:191)
        at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:127)
        at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:102)
        at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:109)
        at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:299)
        at org.apache.coyote.ajp.AjpAprProcessor.process(AjpAprProcessor.java:431)
        at org.apache.coyote.ajp.AjpAprProtocol$AjpConnectionHandler.process(AjpAprProtocol.java:384)
        at org.apache.tomcat.util.net.AprEndpoint$Worker.run(AprEndpoint.java:1556)
        at java.lang.Thread.run(Thread.java:682)
    ***************************************************************************
    <XIWKA> Doc41TechnicalException

    NEW COMPRESSED VERSION without trace:

    10:27:52.520    : <XIXHC> ***** EXCEPTION <<< DOC41-PROD21-16306373946 >>> *****
    10:27:52.520    : <XIXHC> >(100091) java.io.IOException: 
    10:27:52.520    : <XIXHC>  (100091) org.apache.catalina.connector.ClientAbortException: 
    10:27:52.520    : <XIXHC>  (100091) org.springframework.web.client.ResourceAccessException: I/O error on GET request for "http://by-saparch01.bayer-ag.com:8121/SAPALink/CacheServer?get&pVersion=0045&contRep=O4&docId=0ACD70D9F15C3467C2D5170523102354&compId=DRA_7562B.pdf&accessMode=r&authId=CN%3DP2R,OU%3DI0020247049,OU%3DSAPWebAS,O%3DSAPTrustCommunity,C%3DDE&expiration=20180427102751&secKey=MIIBUwYJKoZIhvcNAQcCoIIBRDCCAUACAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHATGCAR8wggEbAgEBMG8wZDELMAkGA1UEBhMCREUxHDAaBgNVBAoTE1NBUCBUcnVzdCBDb21tdW5pdHkxEzARBgNVBAsTClNBUCBXZWIgQVMxFDASBgNVBAsTC0kwMDIwMjQ3MDQ5MQwwCgYDVQQDEwNQMlICByARCCcVNVMwCQYFKw4DAhoFAKBdMBgGCSqGSIb3DQEJAzELBgkqhkiG9w0BBwEwHAYJKoZIhvcNAQkFMQ8XDTE4MDQyNzA4Mjc1MVowIwYJKoZIhvcNAQkEMRYEFAkqAnYo70XTyONkLknoNegF77kDMAkGByqGSM44BAMEMDAuAhUAjTURmGbqNxD9vRFcd7uT3ahd5RQCFQCJ%2BaLAeI8fauNDoOTNnhmFhUi7cQ%3D%3D":null; nested exception is ClientAbortException:  java.io.IOException
    10:27:52.521    : <XIXHC>  (100091) com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException: client/download abort
    10:27:52.521    : <XIXHC> Doc41TechnicalException

    */

    String cParmExpire = null;
    
    /**
     * @param pTitle
     * @param pInternalException
     * @param pParmExpire detected expire timestamp, detected from URL inside exception message, null if not found.
     * @param pAutoTrace
     * @param pNoStacktrace
     */
    private Doc41ClientAbortException(String pTitle,
            Throwable pInternalException, String pParmExpire, boolean pAutoTrace,
            boolean pNoStacktrace) {
        super(pTitle, pInternalException, pAutoTrace, pNoStacktrace);
        cParmExpire = pParmExpire;
        // TODO Auto-generated constructor stub
    }

    /**
     * Get the detected Expire parm.
     * @return null, if expire URL parameter could not be detected at base exceptions message.
     */
    public String getParmExpire() {
        return cParmExpire;
    }
    
}
