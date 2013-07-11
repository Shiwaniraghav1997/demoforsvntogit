package com.bayer.bhc.doc41webui.integration.sap.rfc;

import com.bayer.ecim.foundation.basic.StringTool;

public class ReturnTableRow{
	private String type;
	private String id;
	private Long number;
	private String message;
	private String logNo;
	private Long logMsgNo;
	private String msg1;
	private String msg2;
	private String msg3;
	private String msg4;
	private String parameter;
	private Integer row;
	private String field;
	private String system;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLogNo() {
		return logNo;
	}
	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}
	public Long getLogMsgNo() {
		return logMsgNo;
	}
	public void setLogMsgNo(Long logMsgNo) {
		this.logMsgNo = logMsgNo;
	}
	public String getMsg1() {
		return msg1;
	}
	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}
	public String getMsg2() {
		return msg2;
	}
	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}
	public String getMsg3() {
		return msg3;
	}
	public void setMsg3(String msg3) {
		this.msg3 = msg3;
	}
	public String getMsg4() {
		return msg4;
	}
	public void setMsg4(String msg4) {
		this.msg4 = msg4;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	@Override
	public String toString() {
		return "ReturnTable [type=" + type + ", id=" + id + ", number="
				+ number + ", message=" + message + ", logNo=" + logNo
				+ ", logMsgNo=" + logMsgNo + ", msg1=" + msg1 + ", msg2="
				+ msg2 + ", msg3=" + msg3 + ", msg4=" + msg4
				+ ", parameter=" + parameter + ", row=" + row + ", field="
				+ field + ", system=" + system + "]";
	}
	
	public boolean isError() {
		return StringTool.equals(getType(), "E") || StringTool.equals(getType(), "A");
	}
}