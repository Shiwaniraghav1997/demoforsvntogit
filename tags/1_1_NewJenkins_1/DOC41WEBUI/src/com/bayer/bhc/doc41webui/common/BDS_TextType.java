package com.bayer.bhc.doc41webui.common;

import com.bayer.ecim.foundation.business.sbcommon.DisplayTextTransformerN.FDT_TextType;
import com.bayer.ecim.foundation.business.sbcommon.DisplayTextTransformerN.TextType;

/**
 * Defines the complete list of MWB specific display text types.
 **/
public enum BDS_TextType implements TextType {

    TEXTTYPE_QM_UPL_DOC_IDENT (20L);



	/* the assigned TextTypeId */
	Long	cId	= null;

	/**
	 * Constructor defining the Id for this TextType.
	 *
	 * @param pId
	 */
	BDS_TextType(Long pId) {
		cId = pId;
		FDT_TextType.setTextTypeById(pId, this);
	}

	/**
	 * Return the TextTypeId assigned to this TextType.
	 *
	 * {@inheritDoc}
	 *
	 * @see com.bayer.ecim.foundation.business.sbcommon.DisplayTextTransformerN.TextType#getId()
	 */
	@Override
	public final Long getId() {
		return cId;
	}

	/**
	 * Extends toString by the Id.
	 *
	 * {@inheritDoc}
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public final String toString() {
		return super.toString() + "(" + cId + ")";
	}
}
