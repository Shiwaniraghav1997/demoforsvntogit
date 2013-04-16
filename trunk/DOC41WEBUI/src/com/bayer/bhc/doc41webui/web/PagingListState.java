package com.bayer.bhc.doc41webui.web;

import java.io.Serializable;

/**
 * holds the state of paging for a controller for a user/browser window. To successfully
 * retrieve this paging State, every request MUST have a request param
 * tabId transmitted
 * 
 * @author imrol
 * 
 */
public class PagingListState extends BrowserTabAttributes implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private int pageSize=10;
	private int pageStart;
	private int pageCount;


	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ==> PagingListState .....\n");
		sb.append("\n\t tabId:\t\t");
		sb.append(getTabId());
		sb.append("\n\t pageSize:\t\t");
		sb.append(pageSize);
		sb.append("\n\t pageStart:\t\t");
		sb.append(pageStart);
		sb.append("\n\t pageCount:\t\t");
		sb.append(pageCount);
		sb.append("\n\n");
		return sb.toString();
	}
}
