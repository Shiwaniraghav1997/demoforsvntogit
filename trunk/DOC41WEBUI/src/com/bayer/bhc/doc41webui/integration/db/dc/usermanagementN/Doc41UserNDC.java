/*
 * (c)2007 Bayer AG Leverkusen, Bayer Business Solutions
 * All rights reserved.
 * (based on DCGenenerator DC-Definitions: Id: DCGenerator.ini,v 1.82 2012/10/05 12:22:39 evfpu Exp )
 *
 * $Id$
 */
package com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN;


import java.util.*;

import com.bayer.ecim.foundation.basic.*;
import com.bayer.ecim.foundation.dbx.*;




/**
 * DC for UM_USERS in BOE2_FDT
 */
public class Doc41UserNDC
	extends com.bayer.ecim.foundation.web.usermanagementN.UMUserNDC
{

	/** Maps DB-Column: is_Readonly */
	Boolean cIsReadonly = Boolean.FALSE;
	public static final String FIELD_ISREADONLY = "IsReadonly";

	/** The master Class of this class. */
	public final Class<DataCarrier> MASTER_CLASS = DataCarrier.class;

	/** Set the database tablename (null if unspecified) for this DC. */
	static {
		setDBTablename( Doc41UserNDC.class, "[UM].UM_USERS" );
	}

	private static final long serialVersionUID = 20130409025239109L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_DOC41USERNDC = java.math.BigDecimal.class;

	@SuppressWarnings("unchecked")
	private static final HashMap<String,BasicDCFieldMeta> FIELD_META = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.web.usermanagementN.UMUserNDC.localGetFieldMetaMap() );
	private static final String[] LOCAL_FIELD_LIST = new String[] {FIELD_ISREADONLY};
	private static final String[] FIELD_LIST = StringTool.merge(  com.bayer.ecim.foundation.web.usermanagementN.UMUserNDC.localGetFieldList(), LOCAL_FIELD_LIST );

	static {
		try {
			FIELD_META.put( FIELD_ISREADONLY,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of Doc41UserNDC!", null );
		}
	}

	/**
	 * The Constructor.
	 * (a)deprecated Use: static Doc41UserNDC newInstanceOfDoc41UserNDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public Doc41UserNDC() {
		super( (Boolean)null );
	}

	/**
	 * Temporary Constructor.
	 */
	protected Doc41UserNDC( Boolean pVal ) {
		super( pVal );
		pVal = null;
	}

	/**
	 * The Copy Constructor.
	 * (a)deprecated Use: static Doc41UserNDC newInstanceOfDoc41UserNDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public Doc41UserNDC( BasicDataCarrier pDC )
		throws InitException
	{
		super( (Boolean)null );
		copyFrom( pDC );
	}
	

	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * @throws InitException if the instanciation failes.
	 */
	public static Doc41UserNDC newInstanceOfDoc41UserNDC() throws InitException {
		return (Doc41UserNDC)newInstanceOf( Doc41UserNDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 * @throws InitException if the instanciation failes.
	 */
	public static Doc41UserNDC newInstanceOfDoc41UserNDC( Locale pLoc ) throws InitException {
		return (Doc41UserNDC)localizeDC( newInstanceOfDoc41UserNDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pDC dc to copy the attributes from.
	 * @throws InitException if the instanciation failes.
	 */
	public static Doc41UserNDC newInstanceOfDoc41UserNDC( BasicDataCarrier pDC ) throws InitException {
		return (Doc41UserNDC)newInstanceOfDoc41UserNDC().copyFrom( pDC );
	}

//	/**
//	 * Copy the parameter DC pOther into this.
//	 * THIS METHOD IS A TEMPORARY WORKAROUND.
//	 * (a)deprecated
//	 */
//	public com.bayer.ecim.foundation.dbx.DataCarrier copyFrom(com.bayer.ecim.foundation.dbx.DataCarrier pOther) throws InitException {
//		copyFrom((BasicDataCarrier)pOther);
//		return (((Object)this) instanceof com.bayer.ecim.foundation.dbx.DataCarrier) ? (com.bayer.ecim.foundation.dbx.DataCarrier)(Object)this : null;
//	}
	/** Regeneration check for subclasses. If this line causes an error, you forgot to regenerate the direct superclass */
	public static final int CHECK_DOC41USERNDC = com.bayer.ecim.foundation.web.usermanagementN.UMUserNDC.CHECK_UMUSERNDC;

	/**
	 * Copy the parameter DC pOther into this.
	 */
	@SuppressWarnings("unchecked")

	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) throws InitException {
		super.copyFrom(pOther);
		if (Doc41UserNDC.class.isAssignableFrom(pOther.getClass())) {
			BasicDCColumnMetaData m = null;
			for (int i = 0; i < LOCAL_FIELD_LIST.length; i++) {
				m = pOther.getBasicDCColumnMetaData( LOCAL_FIELD_LIST[i] );
				if ( m != null ) {
					cColumnMetaData.put( LOCAL_FIELD_LIST[i], m );
				}
			}
			for (int i = 0; i < LOCAL_FIELD_LIST.length; i++) {
				String fn = LOCAL_FIELD_LIST[i];
				try {
					set(fn, pOther.get(fn));
					if ( !pOther.isFieldTouched( fn ) ) {
						untouchField( fn );
					}
				} catch (BasicDCReflectFailedException e) {
					throw new InitException(
						"BasicDataCarrier.copyFrom() cannot copy field '"
							+ fn
							+ "' from DC '"
							+ pOther.getClass().getName()
							+ "' to DC '"
							+ this.getClass().getName()
							+ "'.",
						e);
				}
			}
		} else
			cWasCastedUp = true;
		return this;
	}

	/**
	 * Get the CVS metadata of this DC and it's used generator.
	 */
	public static BasicDCGeneratorMeta localGetCVSMeta() {
		return new BasicDCGeneratorMeta(
			"com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN",
			"Doc41UserNDC",
			"com.bayer.ecim.foundation.web.usermanagementN.UMUserNDC",
			"Date: 2012/10/05 12:22:39 ",
			"Revision: 1.82 ",
			"Author: evfpu ",
			"Header: /bo/foundation/resources/DCGenerator.ini,v 1.82 2012/10/05 12:22:39 evfpu Exp ",
			"$Date$",
			"$Revision$",
			"$Author$",
			"$Header$"
		);
	}


	/**
	 * Get the CVS metadata of this DC and it's used generator.
	 */
	public BasicDCGeneratorMeta getCVSMeta() {
		return Doc41UserNDC.localGetCVSMeta();
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
		return (BasicDCFieldMeta)FIELD_META.get( pFieldName );
	}

	public BasicDCFieldMeta getFieldMeta( String pFieldName ) {
		return Doc41UserNDC.localGetFieldMeta( pFieldName );
	}

	/**
	 * Provides a field list of all fields that are supported by the DC.
	 */
	public static String[] localGetFieldList() {
		return FIELD_LIST;
	}


	// START Method getIsReadonly generated
	/**
	 * Maps DB-Column: is_Readonly
	 * Logical type: BOOLEAN
	 */
	public Boolean getIsReadonly() {
		return cIsReadonly;
	}
	// END Method getIsReadonly


	// START Method setIsReadonly generated
	/**
	 * Maps DB-Column: is_Readonly
	 * Logical type: BOOLEAN
	 */
	public void setIsReadonly( Boolean pIsReadonly ) {
		cIsReadonly = pIsReadonly;
		forgetOriginalValue( FIELD_ISREADONLY );
		touchField( FIELD_ISREADONLY );
	}
	// END Method setIsReadonly


	// START Method getFormattedIsReadonly generated
	/**
	 * Maps DB-Column: is_Readonly
	 * Logical type: BOOLEAN
	 */
	public String getFormattedIsReadonly() {
		return hasInvalidValue( FIELD_ISREADONLY ) ? getOriginalValue( FIELD_ISREADONLY ) : getPool().formatBoolean( cIsReadonly );
	}
	// END Method getFormattedIsReadonly


	// START Method setFormattedIsReadonly generated
	/**
	 * Maps DB-Column: is_Readonly
	 * Logical type: BOOLEAN
	 */
	public void setFormattedIsReadonly( String pIsReadonly )
		throws java.text.ParseException
	{
		setIsReadonly( null );
		memorizeOriginalValue( FIELD_ISREADONLY, pIsReadonly );
		setIsReadonly( getPool().parseBoolean( pIsReadonly ) );
	}
	// END Method setFormattedIsReadonly


	// START Method getFormattedHTMLIsReadonly generated
	/**
	 * Maps DB-Column: is_Readonly
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLIsReadonly() {
		return StringTool.escapeHTML( getFormattedIsReadonly() );
	}
	// END Method getFormattedHTMLIsReadonly


	// START Method getToStringIsReadonly generated
	/**
	 * Maps DB-Column: is_Readonly
	 * Logical type: BOOLEAN
	 */
	public String getIsReadonlyToString() {
		return StringTool.nullToEmpty(getIsReadonly());
	}
	// END Method getToStringIsReadonly


	// START Method setFromStringIsReadonly generated
	/**
	 * Maps DB-Column: is_Readonly
	 * Logical type: BOOLEAN
	 */
	public void setIsReadonlyFromString( String pIsReadonly ) {
		setIsReadonly( BooleanTool.parseBoolean( pIsReadonly, null ) );
	}
	// END Method setFromStringIsReadonly


	// START Method getColumnMetaDataIsReadonly generated
	/**
	 * Maps DB-Column: is_Readonly
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForIsReadonly() {
		return getBasicDCColumnMetaData( FIELD_ISREADONLY );
	}
	// END Method getColumnMetaDataIsReadonly


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
