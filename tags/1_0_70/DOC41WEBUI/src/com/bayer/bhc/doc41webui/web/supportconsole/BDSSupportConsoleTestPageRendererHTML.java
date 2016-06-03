package com.bayer.bhc.doc41webui.web.supportconsole;

import java.io.Writer;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.web.test.Doc41TestCaseImpl;
import com.bayer.bhc.doc41webui.web.test.DocServiceUploadTestClientImpl;
import com.bayer.bhc.doc41webui.web.test.DocserviceTestClientImpl;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.web.pagerenderer.FdtPageRendererException;
import com.bayer.ecim.foundation.web.pagerenderer.FdtPageRendererWriterImpl;
import com.bayer.ecim.foundation.web.pagerenderer.FdtRequestUtils;
import com.bayer.ecim.foundation.web.supportconsole.FdtSupportConsoleRequestBean;
import com.bayer.ecim.foundation.web.supportconsole.FdtSupportConsoleStartPageRendererHTML;

public class BDSSupportConsoleTestPageRendererHTML
        extends FdtPageRendererWriterImpl {

//  private static final long   serialVersionUID        = 1L;

    @Override
    public void renderPage(String pNamespace, String pClientVariantId, Long pReportId, String pBaseUrlAjax, String pBaseUrlImg, Writer out) throws FdtPageRendererException {
        boolean escJS = false;
        // we may make this configurable to the application in a later version:
        try{
            DocserviceTestClientImpl dsci = new DocserviceTestClientImpl();
            DocServiceUploadTestClientImpl dsuci = new DocServiceUploadTestClientImpl();
            String pUrl = StringTool.nvl( StringTool.trimEmptyToNull((String)((FdtSupportConsoleRequestBean)cRequestBean).getAttribute("URL")), dsci.URL_PREFIX);
            String pVendor = StringTool.nvl( StringTool.trimEmptyToNull((String)((FdtSupportConsoleRequestBean)cRequestBean).getAttribute("VEN")), dsci.VENDOR);
            String pRefNo = StringTool.nvl( StringTool.trimEmptyToNull((String)((FdtSupportConsoleRequestBean)cRequestBean).getAttribute("REF")), dsci.REF_NO);
            String pPath = StringTool.nvl( StringTool.trimEmptyToNull((String)((FdtSupportConsoleRequestBean)cRequestBean).getAttribute("PATH")), dsci.DOWNL_PATH);
            String pFile = StringTool.nvl( StringTool.trimEmptyToNull((String)((FdtSupportConsoleRequestBean)cRequestBean).getAttribute("FILE")), dsuci.UPL_FILE);
            String pType = StringTool.nvl( StringTool.trimEmptyToNull((String)((FdtSupportConsoleRequestBean)cRequestBean).getAttribute("TYPE")), dsuci.TYPE);
            
            Doc41Log.get().debug(this,cExecUser, "Start... Namespace=" + pNamespace + ", ClientVariantId=" + pClientVariantId );
            println(out, "<H2>" + FdtSupportConsoleStartPageRendererHTML.getSupportConsoleReturnToMenuLink() + " BDS Docservice Test</H2>", escJS);
            println(out, "<P/><P>Press buttons to start test. This is a very simple first version.</P>", escJS);
//            println(out, "<INPUT name=\"TEST_DETAILS\" type=\"submit\" value=\"Test w/ DC dumps.\">", escJS);
            println(out, "<P>Url: <input type=\"text\" name=\"URL\" SIZE=\"100\" value=\"" + pUrl + "\"></P>" , escJS);
            println(out, "<P>Local Path: <input type=\"text\" name=\"PATH\" SIZE=\"100\" value=\"" + pPath + "\"></P>" , escJS);
            println(out, "<P>Vendor: <input type=\"text\" name=\"VEN\" SIZE=\"15\" value=\"" + pVendor + "\">" , escJS);
            println(out, " RefNo: <input type=\"text\" name=\"REF\" SIZE=\"15\" value=\"" + pRefNo + "\">" , escJS);
            println(out, " Upl.File: <input type=\"text\" name=\"FILE\" SIZE=\"30\" value=\"" + pFile + "\">" , escJS);
            println(out, " Upl.Type: <input type=\"text\" name=\"TYPE\" SIZE=\"10\" value=\"" + pType + "\"></P>" , escJS);
            println(out, "<INPUT name=\"TEST_DOWN\" type=\"submit\" value=\"Test Download Interface...\">", escJS);
            println(out, " <INPUT name=\"TEST_UP\" type=\"submit\" value=\"Test Upload Interface...\">", escJS);
            println(out, " <INPUT name=\"REFRESH\" type=\"submit\" value=\"Reload empty fields...\">", escJS);
            println(out, "</p>", escJS);
            /*if (FdtRequestUtils.getParameter(cRequestBean.getExternalRequest(),"TEST_DETAILS", false) != null) {
                println(out, "<p><PRE>\n" + StringTool.replace(StringTool.replace(StringTool.escapeHTMLNotLF(OTUserManagementN.get().test( new String[] { "testUsr", "testGrp", "testPro", "testPer", "testOTP", "999", "testOPe", "IMWIF" }, true, true, true)), ST_OK, ST_OK_DISP), ST_FAIL, ST_FAIL_DISP) +  "</PRE></p>", escJS);
            } else*/
            if ((FdtRequestUtils.getParameter(cRequestBean.getExternalRequest(),"TEST_DOWN", false) != null) ||
                (FdtRequestUtils.getParameter(cRequestBean.getExternalRequest(),"TEST_UP", false) != null)) {
                println(out, "<p>", escJS);

                println(out, "Prepare User...<BR>", escJS);
                Doc41TestCaseImpl.setUserInSession();
                println(out, "User prepared<BR>", escJS);

                println(out, "</p><p>", escJS);

                if (FdtRequestUtils.getParameter(cRequestBean.getExternalRequest(),"TEST_DOWN", false) != null) {
                    println(out, "Run DocServiceTestClient...<BR>", escJS);
                    println(out, "<PRE>" +StringTool.escapeHTMLPre(dsci.run(pUrl, pVendor, pRefNo, pPath))+"</PRE>" , escJS);
                    println(out, "DocServiceTestClient done<BR>", escJS);
                }
                
                if (FdtRequestUtils.getParameter(cRequestBean.getExternalRequest(),"TEST_UP", false) != null) {
                    println(out, "Run DocServiceUploadTestClient...<BR>", escJS);
                    println(out, "<PRE>" +StringTool.escapeHTMLPre(dsuci.run(pUrl, pVendor, pRefNo, pPath, pFile, pType))+"</PRE>" , escJS);
                    println(out, "DocServiceUploadTestClient done<BR>", escJS);
                }
                
                println(out, "</p>", escJS);
            }
        } catch (Exception e) {
            renderExceptionTrace(e, out, escJS);
        }
        Doc41TestCaseImpl.resetUserInSession();
        Doc41Log.get().debug(this,cExecUser, "End...");
    }   

}
