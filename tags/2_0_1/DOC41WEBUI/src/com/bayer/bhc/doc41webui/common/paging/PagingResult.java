/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common.paging;

import java.util.List;

/**
 * PagingResult object.
 * 
 * @author ezzqc
 * @id $Id: PagingResult.java,v 1.1 2012/02/22 10:43:17 ezzqc Exp $
 */
public class PagingResult<E> {

    private List<E> result;

    private int totalSize = Integer.MAX_VALUE;

    
    public PagingResult() {
        super();
    }

    public PagingResult(final List<E> result, final int totalSize) {
        super();
        this.result = result;
        this.totalSize = totalSize;
    }

    /**
     * Set result list.
     * 
     * @param result
     */
    public void setResult(List<E> result) {
        this.result = result;
    }

    /**
     * Set total size of result.
     * 
     * @param hitCount
     */
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<E> getResult() {
        return result;
    }

    public int getTotalSize() {
        return totalSize;
    }

}
