package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.util.ArrayList;
import java.util.List;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.domain.ContentRepositoryInfo;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

public class GetCrepInfoRFC extends AbstractDoc41RFC<ContentRepositoryInfo>{
	private static final String IN_DOC41ID = "IV_DOC41ID";
	
	private static final String OUT_CONT_REP = "EV_CONT_REP";
	private static final String OUT_DOC_CLASS = "EV_DOC_CLASS";
	
	private static final String OUT_RETURNCODE = "EV_RETURNCODE";
	

	@Override
	public void prepareCall(JCoFunction pFunction, List<?> pInputParms)
			throws SAPException {
		Doc41Log.get().debug(GetCrepInfoRFC.class, null, "prepareCall():ENTRY");
    	
        if (pFunction != null) {
            if (pInputParms != null) {
                String doc41Id = (String) pInputParms.get(0);
                
                JCoParameterList sapInput = pFunction.getImportParameterList();
				
				sapInput.setValue(IN_DOC41ID,doc41Id);
            } else {
                throw new SAPException(
                        "GetCrepInfoRFC pInputParms list is null", null);
            }
        } else {
            throw new SAPException(
                    "GetCrepInfoRFC pFunction list is null", null);
        }
        Doc41Log.get().debug(GetCrepInfoRFC.class, null, "prepareCall():EXIT");
	}

	@Override
	public List<ContentRepositoryInfo> processResult(JCoFunction pFunction)
			throws SAPException {
		Doc41Log.get().debug(GetCrepInfoRFC.class, null, "processResult():ENTRY");
		ArrayList<ContentRepositoryInfo> mResult = new ArrayList<ContentRepositoryInfo>();
        if (pFunction != null) {
        	checkReturnCode(pFunction,OUT_RETURNCODE,null);
            JCoParameterList exportParameterList = pFunction.getExportParameterList();
            ContentRepositoryInfo crep = new ContentRepositoryInfo();
            crep.setContentRepository(exportParameterList.getString(OUT_CONT_REP));
            crep.setAllowedDocClass(exportParameterList.getString(OUT_DOC_CLASS));
            mResult.add(crep);
        }
        Doc41Log.get().debug(GetCrepInfoRFC.class, null, "processResult():EXIT");
        return mResult;
	}

}
