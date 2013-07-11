/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.paging;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.container.QueryRequest;

/**
 * PagingRequest object.
 * 
 * @author ezlwb
 */
public class PagingRequest extends QueryRequest implements PagingData {

    private int startIndex = 1;

    private int endIndex;

    private int totalSize = Integer.MAX_VALUE;

    private int pageSize;
    
    public PagingRequest(int pageSize) {        
        this.pageSize = pageSize;
        setEndIndex(getStartIndex() + pageSize - 1);
    }

    public PagingRequest(final PagingData pData) {
        startIndex	= pData.getStartIndex() > -1 ? pData.getStartIndex() + 1 : pData.getStartIndex();
        endIndex 	= pData.getEndIndex() > -1 ? pData.getEndIndex() + 1 : pData.getEndIndex();
        totalSize 	= pData.getTotalSize();
        Doc41Log.get().debug(
                this.getClass(),
                "System",
                "startIndex: " + startIndex + "; endIndex: " + endIndex + "; totalSize: "
                        + totalSize);
    }

    public void nextPage(PagingResult<? extends Object> pagingResult) {
		setTotalSize(pagingResult.getTotalSize());
		setStartIndex(getStartIndex() + pagingResult.getResult().size());
		setEndIndex(getStartIndex() + pageSize - 1);
    }

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
