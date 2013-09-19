package com.bayer.bhc.doc41webui.integration.sap.rfc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.integration.sap.util.RFCCaller;
import com.bayer.bhc.doc41webui.integration.sap.util.SAPException;
import com.bayer.ecim.foundation.basic.StringTool;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public abstract class AbstractDoc41RFC<E> implements RFCCaller<E> {
	
	private static final String OT_RETURN = "TR_RETURN";
	private static final String OTRET_TYPE = "TYPE";
	private static final String OTRET_ID = "ID";
	private static final String OTRET_NUMBER = "NUMBER";
	private static final String OTRET_MESSAGE = "MESSAGE";
	private static final String OTRET_LOG_NO = "LOG_NO";
	private static final String OTRET_LOG_MSG_NO = "LOG_MSG_NO";
	private static final String OTRET_MESSAGE_V1 = "MESSAGE_V1";
	private static final String OTRET_MESSAGE_V2 = "MESSAGE_V2";
	private static final String OTRET_MESSAGE_V3 = "MESSAGE_V3";
	private static final String OTRET_MESSAGE_V4 = "MESSAGE_V4";
	private static final String OTRET_PARAMETER = "PARAMETER";
	private static final String OTRET_ROW = "ROW";
	private static final String OTRET_FIELD = "FIELD";
	private static final String OTRET_SYSTEM = "SYSTEM";
	
	private static final String RETURNCODE_OK = "0";

	public AbstractDoc41RFC() {
		super();
	}

	
	protected void processReturnTable(JCoFunction function) throws SAPException {
		processReturnTable(function, null);
	}
	
	protected void processReturnTable(JCoFunction function, String tableName) throws SAPException {
		List<ReturnTableRow> extractReturnTable = extractReturnTable(function, tableName);
		String errorMsg = null;
		for (ReturnTableRow returnTableRow : extractReturnTable) {
			if(returnTableRow.isError()){
				Doc41Log.get().error(getClass(),UserInSession.getCwid(),"error msg from rfc: "+returnTableRow+ "error number: "+returnTableRow.getNumber());
				
				if (returnTableRow.getNumber().longValue() != 12) {
					if (errorMsg==null) {
						errorMsg=""+returnTableRow.getMessage();
					} else {
						errorMsg+="\n"+returnTableRow.getMessage();
					}
				}
			} else {
				Doc41Log.get().debug(getClass(),UserInSession.getCwid(),"warning msg from rfc: "+returnTableRow);
			}
		}
		if(errorMsg!=null){
			throw new SAPException(errorMsg,null);
		}
	}
	
	protected List<ReturnTableRow> extractReturnTable(JCoFunction function, String tableName) throws SAPException{
		JCoTable table = function.getTableParameterList().getTable(tableName==null?OT_RETURN:tableName);
		List<ReturnTableRow> returnTable = new ArrayList<ReturnTableRow>();
		
		if(table!=null){
        	for(int i=0;i<table.getNumRows();i++){
        		ReturnTableRow row = new ReturnTableRow();
        		row.setType(table.getString(OTRET_TYPE));
        		row.setId(table.getString(OTRET_ID));
        		row.setNumber(table.getLong(OTRET_NUMBER));
        		row.setMessage(table.getString(OTRET_MESSAGE));
        		row.setLogNo(table.getString(OTRET_LOG_NO));
        		row.setLogMsgNo(table.getLong(OTRET_LOG_MSG_NO));
        		row.setMsg1(table.getString(OTRET_MESSAGE_V1));
        		row.setMsg2(table.getString(OTRET_MESSAGE_V2));
        		row.setMsg3(table.getString(OTRET_MESSAGE_V3));
        		row.setMsg4(table.getString(OTRET_MESSAGE_V4));
        		row.setParameter(table.getString(OTRET_PARAMETER));
        		row.setRow(table.getInt(OTRET_ROW));
        		row.setField(table.getString(OTRET_FIELD));
        		row.setSystem(table.getString(OTRET_SYSTEM));
        		
        		table.nextRow();
        		
        		returnTable.add(row);
        	}
		}
		
		return returnTable;
		
	}
	
    /**
     * @see com.bayer.ecim.foundation.sap.RFCCaller#handleException(java.lang.Exception)
     */
    public void handleException(Exception ex) throws SAPException {
        throw new SAPException(ex.getMessage(),ex);

    }
    
    protected boolean sapCharToBoolean(char sapChar) {
		return sapChar=='X';
	}
	
    protected String sapBooleanToChar(Boolean aBoolean) {
    	if (Boolean.TRUE.equals(aBoolean)){
		return "X";
    	}
    	return null;
	}


	protected Date mergeSapDateTime(String ardate, String artime) throws SAPException {//2013-09-17 09:13:31
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date date = sdf.parse(ardate+" "+artime);
			return date;
		} catch (ParseException e) {
			throw new SAPException("parse error: "+ardate+" "+artime, e);
		}
	}
	
	public void checkReturnCode(JCoFunction pFunction, String codeCol, String msgCol) throws SAPException{
		JCoParameterList exportParameterList = pFunction.getExportParameterList();
		String returnCode = exportParameterList.getString(codeCol);
		if(!StringTool.equals(returnCode, RETURNCODE_OK)){
			String msg ="";
			if(!StringTool.isTrimmedEmptyOrNull(msgCol)){
				String msgParam = exportParameterList.getString(msgCol);
				if(!StringTool.isTrimmedEmptyOrNull(msgParam)){
					msg =", msg: "+msgParam;
				}
			}
			throw new SAPException("return code "+codeCol+" is "+returnCode+" but should have been "+RETURNCODE_OK+msg,null);
		}
	}
	
}