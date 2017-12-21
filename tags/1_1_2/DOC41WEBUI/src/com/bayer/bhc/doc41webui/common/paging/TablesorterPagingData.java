package com.bayer.bhc.doc41webui.common.paging;

public class TablesorterPagingData implements PagingData {
	private int startIndex;
	private int endIndex;

	@Override
	public int getEndIndex() {
		return endIndex;
	}

	@Override
	public int getStartIndex() {
		return startIndex;
	}

	@Override
	public int getTotalSize() {
		return Integer.MAX_VALUE;
	}
	
	public TablesorterPagingData(int page, int pageSize) {
		startIndex = page*pageSize;
		endIndex = startIndex+pageSize-1;
	}

}
