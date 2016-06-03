package com.bayer.bhc.doc41webui.domain;


public class DocInfoComponent {

	private String compId;
	private int compSize;
	private String mimeType;
	private Long createTime;
	private Long changeTime;
	private boolean binaryFlag;
	private String status;
	public String getCompId() {
		return compId;
	}
	public void setCompId(String compId) {
		this.compId = compId;
	}
	public int getCompSize() {
		return compSize;
	}
	public void setCompSize(int compSize) {
		this.compSize = compSize;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Long changeTime) {
		this.changeTime = changeTime;
	}
	public boolean isBinaryFlag() {
		return binaryFlag;
	}
	public void setBinaryFlag(boolean binaryFlag) {
		this.binaryFlag = binaryFlag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "DocInfoComponent [compId=" + compId + ", compSize=" + compSize
				+ ", mimeType=" + mimeType + ", createTime=" + createTime
				+ ", changeTime=" + changeTime + ", binaryFlag=" + binaryFlag
				+ ", status=" + status + "]";
	}
	
	
}
