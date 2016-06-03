package com.bayer.bhc.doc41webui.common.paging;

import java.util.List;

public class TableSorterParams {

	private int page;
	private int size;
	private List<String> filter;
	private List<Integer> sort;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public List<String> getFilter() {
		return filter;
	}
	
	public void setFilter(List<String> filter) {
		this.filter = filter;
	}
	
	public List<Integer> getSort() {
		return sort;
	}
	
	public void setSort(List<Integer> sort) {
		this.sort = sort;
	}
	
	public String getFilter(int i){
		if(filter==null || filter.isEmpty() || i>=filter.size()){
			return null;
		}
		return filter.get(i);
	}
	public String getSortColumn(String[] dbColNames) {
		if(dbColNames!=null && sort!=null && !sort.isEmpty()){
			for(int i=0;i<dbColNames.length;i++){
				if(i<sort.size()){
					Integer sortCol = sort.get(i);
					if(sortCol!=null){
						if(sortCol==0){
							return dbColNames[i]+" ASC";
						} else {
							return dbColNames[i]+" DESC";
						}
						
					}
				}
			}
		}
		return null;
	}
	
	
}
