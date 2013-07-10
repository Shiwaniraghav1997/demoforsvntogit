package com.bayer.bhc.doc41webui.common.paging;

public class GetAllPagingData implements PagingData {

	

	@Override
	public int getStartIndex() {
		return -1;
	}
	
	@Override
	public int getEndIndex() {
		return -1;
	}

	@Override
	public int getTotalSize() {
		return Integer.MAX_VALUE;
	}

}
