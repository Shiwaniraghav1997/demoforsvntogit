/*
 * (c)2007 Bayer AG Leverkusen, Bayer Business Solutions
 * All rights reserved.
 * (based on DCGenenerator DC-Definitions: Id: DCGenerator.ini 11016 2017-02-21 13:08:28Z ezfhl )
 *
 * $Id$
 */
package com.bayer.bhc.doc41webui.integration.db.dc;


import java.util.*;

import com.bayer.ecim.foundation.basic.*;
import com.bayer.ecim.foundation.dbx.*;




/**
 * Maps extra column of Doc41 Profiles
 */
public class   UMDoc41ProfileNDC
       extends com.bayer.ecim.foundation.web.usermanagementN.UMProfileNDC {

	/** Maps DB-Column: d41_Order_By */
	Long cD41OrderBy;
	public static final String FIELD_D41ORDERBY = "D41OrderBy";

	/** for compatibility: dummy variable to suppress import warning...*/
	public static final Class<DataCarrier> _DBX_DC_CLASS_UMDOC41PROFILENDC = DataCarrier.class;

	/** The master Class of this class. */
	public static final Class<BasicDataCarrier> MASTER_CLASS_UMDOC41PROFILENDC = BasicDataCarrier.class;

	/** Set the database table-name (null if unspecified) for this DC. */
	static {
		setDBTablename( UMDoc41ProfileNDC.class, "[UM].UM_Profiles" );
	}

	private static final long serialVersionUID = 20180319062714079L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_UMDOC41PROFILENDC = java.math.BigDecimal.class;

	private static final HashMap<String,BasicDCFieldMeta> FIELD_META       = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.web.usermanagementN.UMProfileNDC.localGetFieldMetaMap() );
	private static final String[]                         LOCAL_FIELD_LIST = new String[] {FIELD_D41ORDERBY};
	private static final String[]                         FIELD_LIST       = StringTool.merge( com.bayer.ecim.foundation.web.usermanagementN.UMProfileNDC.localGetFieldList(), LOCAL_FIELD_LIST );

	/** Regeneration check for subclasses. If this line causes an error, you forgot to regenerate the direct superclass */
	public static final int CHECK_UMDOC41PROFILENDC = com.bayer.ecim.foundation.web.usermanagementN.UMProfileNDC.CHECK_UMPROFILENDC;

	static {
		try {
			FIELD_META.put( FIELD_D41ORDERBY,	new BasicDCFieldMeta( "LONG",	"NUMBERING",	Long.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of UMDoc41ProfileNDC!", e );
		}
	}

	/**
	 * The standard Constructor (Framework internal: do not use in applications!).
	 * @deprecated Use: static UMDoc41ProfileNDC newInstanceOfUMDoc41ProfileNDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public UMDoc41ProfileNDC() {
		super( (Boolean)null );
	}

	/**
	 * Constructor for class-hierarchy (Framework internal: do not use in applications!).
	 */
	protected UMDoc41ProfileNDC( Boolean pVal ) {
		super( pVal );
	}

	/**
	 * The Copy Constructor.
	 * @deprecated Use: static UMDoc41ProfileNDC newInstanceOfUMDoc41ProfileNDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public UMDoc41ProfileNDC( BasicDataCarrier pDC ) {
		super( (Boolean)null );
		copyFrom( pDC );
	}


	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * throws an InitException if the instantiation fails.
	 */
	public static UMDoc41ProfileNDC newInstanceOfUMDoc41ProfileNDC() {
		return newInstanceOf( UMDoc41ProfileNDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 */
	public static UMDoc41ProfileNDC newInstanceOfUMDoc41ProfileNDC( Locale pLoc ) {
		return localizeDC( newInstanceOfUMDoc41ProfileNDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pDC DC to copy the attributes from.
	 */
	public static UMDoc41ProfileNDC newInstanceOfUMDoc41ProfileNDC( BasicDataCarrier pDC ) {
		return (UMDoc41ProfileNDC)newInstanceOfUMDoc41ProfileNDC().copyFrom( pDC );
	}

	/**
	 * Copy the parameter DC pOther into this.
	 */
	@Override
	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) {
		super.copyFrom(pOther);
		if (UMDoc41ProfileNDC.class.isAssignableFrom(pOther.getClass())) {
			BasicDCColumnMetaData m;
			for (String field : LOCAL_FIELD_LIST) {
				m = pOther.getBasicDCColumnMetaData(field);
				if ( m != null ) {
					cColumnMetaData.put( field, m );
				}
			}
			for (String field : LOCAL_FIELD_LIST) {
				try {
					set(field, pOther.get(field));
					if ( !pOther.isFieldTouched( field ) ) {
						untouchField( field );
					}
				} catch (BasicDCReflectFailedException e) {
					throw new InitException(
						"BasicDataCarrier.copyFrom() cannot copy field '" + field
							+ "' from DC '" + pOther.getClass().getName()
							+ "' to DC '"   + this  .getClass().getName() + "'.",
						e);
				}
			}
		} else {
			cWasCastedUp = true;
		}
		return this;
	}

	/**
	 * Get the CVS meta-data of this DC and it's used generator.
	 */
	public static BasicDCGeneratorMeta localGetCVSMeta() {
		return new BasicDCGeneratorMeta(
			"com.bayer.bhc.doc41webui.integration.db.dc",
			"UMDoc41ProfileNDC",
			"com.bayer.ecim.foundation.web.usermanagementN.UMProfileNDC",
			"Date: 2017-02-21 14:08:28 +0100 (Di, 21 Feb 2017) ",
			"Revision: 11016 ",
			"Author: ezfhl ",
			"Header",
			"$Date$",
			"$Revision$",
			"$Author$",
			"$Header$/generics"
		);
	}


	/**
	 * Get the CVS meta-data of this DC and it's used generator.
	 */
	@Override
	public BasicDCGeneratorMeta getCVSMeta() {
		return UMDoc41ProfileNDC.localGetCVSMeta();
	}

	/**
	 * Provides a map with field meta information of all fields that are supported by the DC.
	 */
	protected static HashMap<String,BasicDCFieldMeta> localGetFieldMetaMap() {
		return FIELD_META;
	}

	/**
	 * Provides field meta information for a specific field.
	 */
	public static BasicDCFieldMeta localGetFieldMeta( String pFieldName ) {
		return FIELD_META.get( pFieldName );
	}

	@Override
	public BasicDCFieldMeta getFieldMeta( String pFieldName ) {
		return UMDoc41ProfileNDC.localGetFieldMeta( pFieldName );
	}

	/**
	 * Provides a field list of all fields that are supported by the DC.
	 */
	public static String[] localGetFieldList() {
		return FIELD_LIST.clone( );
	}


	// START Method getD41OrderBy generated
	/**
	 * Maps DB-Column: d41_Order_By
	 * Logical type: Numbering
	 */
	public Long getD41OrderBy() {
		return cD41OrderBy;
	}
	// END Method getD41OrderBy


	// START Method setD41OrderBy generated
	/**
	 * Maps DB-Column: d41_Order_By
	 * Logical type: Numbering
	 */
	public void setD41OrderBy( Long pD41OrderBy ) {
		cD41OrderBy = pD41OrderBy;
		forgetOriginalValue( FIELD_D41ORDERBY );
		touchField( FIELD_D41ORDERBY );
	}
	// END Method setD41OrderBy


	// START Method getFormattedD41OrderBy generated
	/**
	 * Maps DB-Column: d41_Order_By
	 * Logical type: Numbering
	 */
	public String getFormattedD41OrderBy() {
		return hasInvalidValue( FIELD_D41ORDERBY ) ? getOriginalValue( FIELD_D41ORDERBY ) : getPool().formatLong( "Numbering", "0", getD41OrderBy() );
	}
	// END Method getFormattedD41OrderBy


	// START Method setFormattedD41OrderBy generated
	/**
	 * Maps DB-Column: d41_Order_By
	 * Logical type: Numbering
	 */
	public void setFormattedD41OrderBy( String pD41OrderBy ) throws java.text.ParseException {
		setD41OrderBy( null );
		memorizeOriginalValue( FIELD_D41ORDERBY, pD41OrderBy );
		setD41OrderBy( getPool().parseLong( "Numbering", "0", pD41OrderBy ) );
	}
	// END Method setFormattedD41OrderBy


	// START Method getFormattedHTMLD41OrderBy generated
	/**
	 * Maps DB-Column: d41_Order_By
	 * Logical type: Numbering
	 */
	public String getFormattedHTMLD41OrderBy() {
		return StringTool.escapeHTML( getFormattedD41OrderBy() );
	}
	// END Method getFormattedHTMLD41OrderBy


	// START Method getToStringD41OrderBy generated
	/**
	 * Maps DB-Column: d41_Order_By
	 * Logical type: Numbering
	 */
	public String getD41OrderByToString() {
		return StringTool.nullToEmpty(getD41OrderBy());
	}
	// END Method getToStringD41OrderBy


	// START Method setFromStringD41OrderBy generated
	/**
	 * Maps DB-Column: d41_Order_By
	 * Logical type: Numbering
	 */
	public void setD41OrderByFromString( String pD41OrderBy ) {
		setD41OrderBy( NumberTool.parseLong( pD41OrderBy, null ) );
	}
	// END Method setFromStringD41OrderBy


	// START Method getColumnMetaDataD41OrderBy generated
	/**
	 * Maps DB-Column: d41_Order_By
	 * Logical type: Numbering
	 */
	public BasicDCColumnMetaData getColumnMetaDataForD41OrderBy() {
		return getBasicDCColumnMetaData( FIELD_D41ORDERBY );
	}
	// END Method getColumnMetaDataD41OrderBy


	// START Protected ( insert your personal code here ) -- do not modify this line!!

    /**
     *
     * $Log$
     */

	// END Protected -- do not modify this line!!
}

/**
 *
 * $Log$
 */
