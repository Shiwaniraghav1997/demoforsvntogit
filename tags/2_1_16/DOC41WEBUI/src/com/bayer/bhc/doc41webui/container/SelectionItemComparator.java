package com.bayer.bhc.doc41webui.container;

import java.io.Serializable;
import java.util.Comparator;


public class SelectionItemComparator implements Comparator<SelectionItem>,Serializable {

    private static final long serialVersionUID = 8125736325247369860L;

    @Override
	public int compare(SelectionItem o1, SelectionItem o2) {

		return o1.getLabel().compareTo(o2.getLabel());
	}

}