package com.bayer.bhc.doc41webui.container;

import java.util.Comparator;


public class SelectionItemComparator implements Comparator<SelectionItem> {

	public String compareBy;

	@Override
	public int compare(SelectionItem o1, SelectionItem o2) {

		return o1.getLabel().compareTo(o2.getLabel());
	}

}