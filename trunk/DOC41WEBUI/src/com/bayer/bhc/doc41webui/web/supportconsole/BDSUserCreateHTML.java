package com.bayer.bhc.doc41webui.web.supportconsole;

import java.io.StringReader;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

import org.springframework.context.ApplicationContext;

import com.bayer.bhc.doc41webui.common.ApplicationContextProvider;
import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.paging.PagingRequest;
import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.container.UserListFilter;
import com.bayer.bhc.doc41webui.domain.SapVendor;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.UserManagementUC;
import com.bayer.bhc.doc41webui.web.test.Doc41TestCaseImpl;
import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.NestingException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.bayer.ecim.foundation.basic.Template;
import com.bayer.ecim.foundation.db.exchange.CSVReader;
import com.bayer.ecim.foundation.db.exchange.DBDataRow;
import com.bayer.ecim.foundation.web.pagerenderer.FdtPageRendererException;
import com.bayer.ecim.foundation.web.pagerenderer.FdtPageRendererWriterImpl;
import com.bayer.ecim.foundation.web.pagerenderer.FdtRequestUtils;
import com.bayer.ecim.foundation.web.supportconsole.FdtSupportConsoleRequestBean;
import com.bayer.ecim.foundation.web.supportconsole.FdtSupportConsoleStartPageRendererHTML;

public class BDSUserCreateHTML
        extends FdtPageRendererWriterImpl {

//  private static final long   serialVersionUID        = 1L;
    UserManagementUC userMgmtUC;

    static final String USER_FIELD = "USR";
    static final String PASSW_FIELD = "UPW";
    static final String INITPW_FIELD = "IPW";
    static final String IMP_AREA = "IMPORT";
    static final String RES_AREA = "RESULT";
    static final String GO_BUTTON = "GO";
    static final String SIM_BUTTON = "SIMULATE";
    static final String CSV_FIRST = "FIRSTNAME";
    static final String CSV_LAST = "LASTNAME";
    static final String CSV_EMAIL = "EMAIL";
    static final String CSV_VENDOR = "VENDOR";
    static final String CSV_CWID = "CWID";
    static final String CSV_STATUS = "STATUS";
    static final String UM_ROLE = "doc41_pmsup";
    
    @Override
    public void renderPage(String pNamespace, String pClientVariantId, Long pReportId, String pBaseUrlAjax, String pBaseUrlImg, Writer out) throws FdtPageRendererException {
        boolean escJS = false;
        StringBuffer mProcessing = new StringBuffer(5000);
        // we may make this configurable to the application in a later version:
        try{
            Properties cfg = ConfigMap.get().getSubCfg("umgmt","import", false);
            Doc41Log.get().debug(this,cExecUser, "Start... Namespace=" + pNamespace + ", ClientVariantId=" + pClientVariantId );
            println(out, "<H2>" + FdtSupportConsoleStartPageRendererHTML.getSupportConsoleReturnToMenuLink() + " PTS Ext. User Import</H2>", escJS);

            ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
            userMgmtUC = ctx.getBean(UserManagementUC.class);
            String pUser    = StringTool.nullToEmpty(((FdtSupportConsoleRequestBean)cRequestBean).getAttribute(USER_FIELD));
            String pPw      = StringTool.nullToEmpty(((FdtSupportConsoleRequestBean)cRequestBean).getAttribute(PASSW_FIELD));
            String pInitPw  = StringTool.nullToEmpty(((FdtSupportConsoleRequestBean)cRequestBean).getAttribute(INITPW_FIELD));
            String pInput   = StringTool.nullToEmpty(((FdtSupportConsoleRequestBean)cRequestBean).getAttribute(IMP_AREA));
            String pResult  = StringTool.nullToEmpty(((FdtSupportConsoleRequestBean)cRequestBean).getAttribute(RES_AREA));

            StringBuffer mRowResults = new StringBuffer(5000);
            char mQ = '"';
            char mD = '\t';
            
            if (pInput.length() == 0) {
                pInput = mQ + CSV_LAST + mQ + mD + mQ + CSV_FIRST + mQ + mD + mQ + CSV_EMAIL + mQ + mD + mQ + CSV_VENDOR + mQ;
            }
            if (pInitPw.length() == 0) {
                pInitPw = StringTool.nvl(StringTool.trimEmptyToNull(cfg.getProperty("pw.template")), "[VENDOR]");
            }
            
            println(out, "<P/><P>Paste User CSV List and press GO button.</P>", escJS);

            println(out, "<P>User: <input type=\"text\" name=\"" + USER_FIELD + "\" SIZE=\"10\" value=\"" + StringTool.escapeHTMLNotLF(pUser) + "\">" , escJS);
            println(out, "  Password: <input type=\"password\" name=\"" + PASSW_FIELD + "\" SIZE=\"50\" value=\"" + StringTool.escapeHTMLNotLF(pPw) + "\"></P>" , escJS);
            println(out, "  Init Password: <input type=\"text\" name=\"" + INITPW_FIELD + "\" SIZE=\"50\" value=\"" + StringTool.escapeHTMLNotLF(pInitPw) + "\"></P>" , escJS);
            println(out, "<textarea rows=10 cols=150 name=\"" + IMP_AREA  + "\">" + StringTool.escapeHTMLNotLF( StringTool.nullToEmpty(pInput) )  + "</textarea><BR>", escJS);

            println(out, "<INPUT name=\"" + GO_BUTTON + "\" type=\"submit\" value=\"GO\">", escJS);
            println(out, " <INPUT name=\"" + SIM_BUTTON + "\" type=\"submit\" value=\"SIM\">", escJS);
            println(out, "</p>", escJS);

            boolean isGo = (FdtRequestUtils.getParameter(cRequestBean.getExternalRequest(),GO_BUTTON, false) != null);
            boolean isSim = (FdtRequestUtils.getParameter(cRequestBean.getExternalRequest(),SIM_BUTTON, false) != null);
            if ( isSim || isGo ) {
                println(out, "<p>", escJS);

                mProcessing.append("Prepare User...\n");
                if (!userMgmtUC.isAuthenticated(pUser, pPw)) {
                    mProcessing.append("Authentication failed: " + pUser + "\n");
                } else {
                    mProcessing.append("User authenticated.\n");
                    LocaleInSession.put(Locale.US);
                    User mUsr = userMgmtUC.findUser(pUser.toUpperCase());
                    if (mUsr == null) {
                        mProcessing.append("User not found in BDS: " + pUser + "\n");
                    } else {
                        if (null != mUsr.getCwid()
                                && !mUsr.hasPermission(Doc41Constants.PERMISSION_READ_ONLY)
                                && mUsr.hasPermission(Doc41Constants.PERMISSION_USER_IMPORT) ) {
                            mUsr.setReadOnly(Boolean.FALSE);
                        }
                        mProcessing.append("User found in BDS" + (mUsr.getReadOnly() ? "(read only)" : "") + ".\n");
                        UserInSession.put(mUsr);
                        LocaleInSession.put(mUsr.getLocale());
                        mProcessing.append("User prepared\n");
                        mProcessing.append("Run Import...\n");
                
                        CSVReader mImp = new CSVReader(new StringReader(pInput));
                        String[] mColNames = mImp.getColumnNames();
                        HashMap<String, Integer> mColNamesH = new HashMap<String, Integer>(); 
                        for (int i = 0; i < mColNames.length; i++) {
                            mColNamesH.put(mColNames[i].toUpperCase(), i);
                        }
                        if (!mColNamesH.containsKey(CSV_FIRST) || !mColNamesH.containsKey(CSV_LAST) || !mColNamesH.containsKey(CSV_EMAIL) || !mColNamesH.containsKey(CSV_VENDOR)) {
                            mProcessing.append("CSV-Header missing or bad. Clear input field to receive propper headers.\n");
                        } else {
                            mQ = mImp.getStringDelimiter();
                            mD = mImp.getValueDelimiter();
                            mProcessing.append("CSV-Header ok, processing input rows...\n");
                            mRowResults.append(mQ + CSV_EMAIL + mQ + mD + mQ + CSV_CWID + mQ + mD + mQ + CSV_STATUS + mQ + "\n" );
                            int i = 0;
                            while (mImp.hasMoreRows()) {
                                i++;
                                DBDataRow mRow = mImp.getRow();
                                String mLastName = mRow.getValue(mColNamesH.get(CSV_LAST)).trim();
                                String mFirstName = mRow.getValue(mColNamesH.get(CSV_FIRST)).trim();
                                String mEmail = mRow.getValue(mColNamesH.get(CSV_EMAIL)).trim();
                                String mVendor = mRow.getValue(mColNamesH.get(CSV_VENDOR)).trim();
                                mProcessing.append("- row " + i + ", lastname: " + mLastName + ", firstname: " + mFirstName + ", email: " + mEmail + ", vendor: " + mVendor);
                                mRowResults.append("" + mQ + StringTool.replace(mEmail, ""+mQ, ""+mQ+mQ) + mQ);
                                if ((mFirstName.length() == 0) || (mLastName.length() == 0) || (mEmail.length() == 0) || (mVendor.length() == 0)) {
                                    mProcessing.append(" - FAIL, mandatory field empty!\n");
                                    mRowResults.append("" + mD + mQ + StringTool.replace(" --- ", ""+mQ, ""+mQ+mQ) + mQ + mD + mQ + StringTool.replace("fail, incomplete", ""+mQ, ""+mQ+mQ) + mQ + "\n");
                                } else {
                                    String mVendorOrg = mVendor;
                                    if (mVendor.length() < Doc41Constants.FIELD_SIZE_VENDOR_NUMBER) {
                                        mVendor = StringTool.repeat("0", Doc41Constants.FIELD_SIZE_VENDOR_NUMBER - mVendor.length()) + mVendor;
                                    }
                                    UserListFilter mUlf = new UserListFilter();
                                    mUlf.setEmail(mEmail);
                                    PagingResult<User> mRes = userMgmtUC.findUsers(mUlf, new PagingRequest(1));
                                    if (mRes.getTotalSize() > 0) {
                                        User mUser = mRes.getResult().get(0);
                                        mProcessing.append(" - IGNORE, email exists: " + mUser.getSurname() + ", " + mUser.getFirstname() + ", " + mUser.getEmail() + "\n");
                                        mRowResults.append("" + mD + mQ + StringTool.replace(" --- ", ""+mQ, ""+mQ+mQ) + mQ + mD + mQ + StringTool.replace("fail, email exists", ""+mQ, ""+mQ+mQ) + mQ + "\n");
                                    } else {
                                        SapVendor mSapVen = userMgmtUC.checkVendor(mVendor);
                                        if (mSapVen == null) {
                                            mProcessing.append(" - IGNORE, vendor not found: " + mVendor + "\n");
                                            mRowResults.append("" + mD + mQ + StringTool.replace(" --- ", ""+mQ, ""+mQ+mQ) + mQ + mD + mQ + StringTool.replace("fail, vendor " + mVendor + " not found", ""+mQ, ""+mQ+mQ) + mQ + "\n");
                                        } else {
                                            String mVenName = StringTool.nullToEmpty(mSapVen.getName1()) + StringTool.embed(mSapVen.getName2(), " ", "");
                                            User mNew = new User();
                                            mNew.setFirstname(mFirstName);
                                            mNew.setSurname(mLastName);
                                            mNew.setEmail(mEmail);
                                            mNew.setChangedBy(pUser);
                                            mNew.setCreatedBy(pUser);
                                            mNew.setLocale(Locale.US);
                                            mNew.setVendors(Arrays.asList(mSapVen));
                                            mNew.setType(User.TYPE_EXTERNAL);
                                            mNew.setActive(true);
                                            mNew.setRoles(Arrays.asList(UM_ROLE));
                                            String mExpPw = "";
                                            try {
                                                mExpPw = Template.expand(pInitPw, new Object[] { mVendorOrg }, new String[] { "VENDOR" } );
                                                mNew.setPassword(mExpPw);
                                                mNew.setPasswordRepeated(mExpPw);
                                                if (isSim) {
                                                    mProcessing.append(" - SIM ok, vendor: " + mVenName + ", Roles: " + StringTool.list(mNew.getRoles(), ", ", false) + ", pw: " + mExpPw + "\n");
                                                    mRowResults.append("" + mD + mQ + StringTool.replace(" --- ", ""+mQ, ""+mQ+mQ) + mQ + mD + mQ + StringTool.replace("sim ok, vendor: " + mVenName, ""+mQ, ""+mQ+mQ) + mQ + "\n");
                                                } else {
                                                    userMgmtUC.createUser(mNew);
                                                    mProcessing.append(" - OK, CWID: " + mNew.getCwid() + ", vendor: " + mVenName + ", Roles: " + StringTool.list(mNew.getRoles(), ", ", false) + ", pw: " + mExpPw + "\n");
                                                    mRowResults.append("" + mD + mQ + StringTool.replace(mNew.getCwid(), ""+mQ, ""+mQ+mQ) + mQ + mD + mQ + StringTool.replace("ok, created, vendor: " + mVenName, ""+mQ, ""+mQ+mQ) + mQ + "\n");
                                                }
                                            } catch (Exception e) {
                                                Doc41TechnicalException te = new Doc41TechnicalException(this, "user import failed, email: " + mEmail + ", by user: " + pUser, e );
                                                Throwable e1 = te.getBasicException();
                                                mProcessing.append(" - FAIL: " + e1.getClass().getSimpleName() + " - " + e1.getMessage() + ", pw: " + mExpPw + "\n");
                                                mRowResults.append("" + mD + mQ + StringTool.replace(" --- ", ""+mQ, ""+mQ+mQ) + mQ + mD + mQ + StringTool.replace("fail, " + e1.getClass().getSimpleName(), ""+mQ, ""+mQ+mQ) + mQ + "\n");
                                                NestingException.handlingCompleted("User ignored.", te);
                                            }
                                        }
                                    }
                                }
                            }
                            pResult = mRowResults.toString();
                            mProcessing.append("" + i + " user processed, done.");
                        }
                    }
                }
                println(out, "<textarea rows=10 cols=150 name=\"" + RES_AREA  + "\">" + StringTool.escapeHTMLNotLF( StringTool.nullToEmpty(pResult) )  + "</textarea><BR>", escJS);
                println(out, "<PRE>\n" + StringTool.escapeHTMLPre( mProcessing.toString() )  + "</PRE><BR>", escJS);
            }
        } catch (Exception e) {
            println(out, "<PRE>\n" + StringTool.escapeHTMLPre( mProcessing.toString() )  + "</PRE><BR>", escJS);
            renderExceptionTrace(e, out, escJS);
        }
        Doc41TestCaseImpl.resetUserInSession();
        Doc41Log.get().debug(this,cExecUser, "End...");
    }   

}
