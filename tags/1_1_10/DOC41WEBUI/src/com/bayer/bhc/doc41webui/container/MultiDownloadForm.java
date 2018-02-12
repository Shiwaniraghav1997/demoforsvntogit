package com.bayer.bhc.doc41webui.container;

import java.util.List;

public class MultiDownloadForm {

    /* list of selected items (documents) */
    private List<String> docSel;

    /* list of all items (documents) */
    private List<String> docAll;

    /**
     * @return the docSel
     */
    public List<String> getDocSel() {
        return docSel;
    }

    /**
     * @param docSel the docSel to set
     */
    public void setDocSel(List<String> docSel) {
        this.docSel = docSel;
    }

    /**
     * @return the docAll
     */
    public List<String> getDocAll() {
        return docAll;
    }

    /**
     * @param docAll the docAll to set
     */
    public void setDocAll(List<String> docAll) {
        this.docAll = docAll;
    }

}
