package com.bayer.bhc.doc41webui.domain;


public class DirsDocKey {
	
	private String dvsDocType;
	private String docNumber;
	private String docVersion;
	private String docLanguage;

	@Override
	public String toString() {
		return "DirsDocKey [dvsDocType=" + dvsDocType + ", docNumber=" + docNumber
				+ ", docVersion=" + docVersion + ", docLanguage=" + docLanguage
				+ "]";
	}

    /**
     * @return the dvsDocType
     */
    public String getDvsDocType() {
        return dvsDocType;
    }

    /**
     * @param dvsDocType the dvsDocType to set
     */
    public void setDvsDocType(String dvsDocType) {
        this.dvsDocType = dvsDocType;
    }

    /**
     * @return the docNumber
     */
    public String getDocNumber() {
        return docNumber;
    }

    /**
     * @param docNumber the docNumber to set
     */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    /**
     * @return the docVersion
     */
    public String getDocVersion() {
        return docVersion;
    }

    /**
     * @param docVersion the docVersion to set
     */
    public void setDocVersion(String docVersion) {
        this.docVersion = docVersion;
    }

    /**
     * @return the docLanguage (iso code, upper case)
     */
    public String getDocLanguage() {
        return docLanguage;
    }

    /**
     * @param docLanguage the docLanguage to set (iso code, upper case)
     */
    public void setDocLanguage(String docLanguage) {
        this.docLanguage = docLanguage;
    }

	
}
