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
 * <my description of the dc>
 */
public class   UserCountryDC
       extends com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier {

	/** Maps DB-Column: user_Id */
	Long cUserId;
	public static final String FIELD_USERID = "UserId";

	/** Maps DB-Column: CountryIsoCode */
	String cCountryIsoCode;
	public static final String FIELD_COUNTRYISOCODE = "CountryIsoCode";

	/** for compatibility: dummy variable to suppress import warning...*/
	public static final Class<DataCarrier> _DBX_DC_CLASS_USERCOUNTRYDC = DataCarrier.class;

	/** The master Class of this class. */
	public static final Class<BasicDataCarrier> MASTER_CLASS_USERCOUNTRYDC = BasicDataCarrier.class;

	/** Set the database table-name (null if unspecified) for this DC. */
	static {
		setDBTablename( UserCountryDC.class, "[DOC41WEB_MGR].D41_USER_COUNTRY" );
	}

	private static final long serialVersionUID = 20180319062714090L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_USERCOUNTRYDC = java.math.BigDecimal.class;

	private static final HashMap<String,BasicDCFieldMeta> FIELD_META       = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldMetaMap() );
	private static final String[]                         LOCAL_FIELD_LIST = new String[] {FIELD_USERID, FIELD_COUNTRYISOCODE};
	private static final String[]                         FIELD_LIST       = StringTool.merge( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldList(), LOCAL_FIELD_LIST );

	/** Regeneration check for subclasses. If this line causes an error, you forgot to regenerate the direct superclass */
	public static final int CHECK_USERCOUNTRYDC = com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.CHECK_USERCHANGEABLEDATACARRIER;

	static {
		try {
			FIELD_META.put( FIELD_USERID,	new BasicDCFieldMeta( "IDS",	"ID",	Long.class,	null ) );
			FIELD_META.put( FIELD_COUNTRYISOCODE,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of UserCountryDC!", e );
		}
	}

	/**
	 * The standard Constructor (Framework internal: do not use in applications!).
	 * @deprecated Use: static UserCountryDC newInstanceOfUserCountryDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public UserCountryDC() {
		super( (Boolean)null );
	}

	/**
	 * Constructor for class-hierarchy (Framework internal: do not use in applications!).
	 */
	protected UserCountryDC( Boolean pVal ) {
		super( pVal );
	}

	/**
	 * The Copy Constructor.
	 * @deprecated Use: static UserCountryDC newInstanceOfUserCountryDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public UserCountryDC( BasicDataCarrier pDC ) {
		super( (Boolean)null );
		copyFrom( pDC );
	}


	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * throws an InitException if the instantiation fails.
	 */
	public static UserCountryDC newInstanceOfUserCountryDC() {
		return newInstanceOf( UserCountryDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 */
	public static UserCountryDC newInstanceOfUserCountryDC( Locale pLoc ) {
		return localizeDC( newInstanceOfUserCountryDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pDC DC to copy the attributes from.
	 */
	public static UserCountryDC newInstanceOfUserCountryDC( BasicDataCarrier pDC ) {
		return (UserCountryDC)newInstanceOfUserCountryDC().copyFrom( pDC );
	}

	/**
	 * Copy the parameter DC pOther into this.
	 */
	@Override
	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) {
		super.copyFrom(pOther);
		if (UserCountryDC.class.isAssignableFrom(pOther.getClass())) {
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
			"UserCountryDC",
			"com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier",
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
		return UserCountryDC.localGetCVSMeta();
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
		return UserCountryDC.localGetFieldMeta( pFieldName );
	}

	/**
	 * Provides a field list of all fields that are supported by the DC.
	 */
	public static String[] localGetFieldList() {
		return FIELD_LIST.clone( );
	}


	// START Method getUserId generated
	/**
	 * Maps DB-Column: user_Id
	 * Logical type: Id
	 */
	public Long getUserId() {
		return cUserId;
	}
	// END Method getUserId


	// START Method setUserId generated
	/**
	 * Maps DB-Column: user_Id
	 * Logical type: Id
	 */
	public void setUserId( Long pUserId ) {
		cUserId = pUserId;
		forgetOriginalValue( FIELD_USERID );
		touchField( FIELD_USERID );
	}
	// END Method setUserId


	// START Method getFormattedUserId generated
	/**
	 * Maps DB-Column: user_Id
	 * Logical type: Id
	 */
	public String getFormattedUserId() {
		return hasInvalidValue( FIELD_USERID ) ? getOriginalValue( FIELD_USERID ) : getPool().formatId( "Id", "#", getUserId() );
	}
	// END Method getFormattedUserId


	// START Method setFormattedUserId generated
	/**
	 * Maps DB-Column: user_Id
	 * Logical type: Id
	 */
	public void setFormattedUserId( String pUserId ) throws java.text.ParseException {
		setUserId( null );
		memorizeOriginalValue( FIELD_USERID, pUserId );
		setUserId( getPool().parseId( "Id", "#", pUserId ) );
	}
	// END Method setFormattedUserId


	// START Method getFormattedHTMLUserId generated
	/**
	 * Maps DB-Column: user_Id
	 * Logical type: Id
	 */
	public String getFormattedHTMLUserId() {
		return StringTool.escapeHTML( getFormattedUserId() );
	}
	// END Method getFormattedHTMLUserId


	// START Method getToStringUserId generated
	/**
	 * Maps DB-Column: user_Id
	 * Logical type: Id
	 */
	public String getUserIdToString() {
		return StringTool.nullToEmpty(getUserId());
	}
	// END Method getToStringUserId


	// START Method setFromStringUserId generated
	/**
	 * Maps DB-Column: user_Id
	 * Logical type: Id
	 */
	public void setUserIdFromString( String pUserId ) {
		setUserId( NumberTool.parseLong( pUserId, null ) );
	}
	// END Method setFromStringUserId


	// START Method getColumnMetaDataUserId generated
	/**
	 * Maps DB-Column: user_Id
	 * Logical type: Id
	 */
	public BasicDCColumnMetaData getColumnMetaDataForUserId() {
		return getBasicDCColumnMetaData( FIELD_USERID );
	}
	// END Method getColumnMetaDataUserId


	// START Method getCountryIsoCode generated
	/**
	 * Maps DB-Column: CountryIsoCode
	 * Logical type: STRING
	 */
	public String getCountryIsoCode() {
		return cCountryIsoCode;
	}
	// END Method getCountryIsoCode


	// START Method setCountryIsoCode generated
	/**
	 * Maps DB-Column: CountryIsoCode
	 * Logical type: STRING
	 */
	public void setCountryIsoCode( String pCountryIsoCode ) {
		cCountryIsoCode = StringTool.trimmedEmptyToNull( pCountryIsoCode );
		forgetOriginalValue( FIELD_COUNTRYISOCODE );
		touchField( FIELD_COUNTRYISOCODE );
	}
	// END Method setCountryIsoCode


	// START Method getFormattedCountryIsoCode generated
	/**
	 * Maps DB-Column: CountryIsoCode
	 * Logical type: STRING
	 */
	public String getFormattedCountryIsoCode() {
		return hasInvalidValue( FIELD_COUNTRYISOCODE ) ? getOriginalValue( FIELD_COUNTRYISOCODE ) : getPool().formatString( cCountryIsoCode );
	}
	// END Method getFormattedCountryIsoCode


	// START Method setFormattedCountryIsoCode generated
	/**
	 * Maps DB-Column: CountryIsoCode
	 * Logical type: STRING
	 */
	public void setFormattedCountryIsoCode( String pCountryIsoCode ) throws java.text.ParseException {
		setCountryIsoCode( null );
		memorizeOriginalValue( FIELD_COUNTRYISOCODE, pCountryIsoCode );
		setCountryIsoCode( getPool().parseString( pCountryIsoCode ) );
	}
	// END Method setFormattedCountryIsoCode


	// START Method getFormattedHTMLCountryIsoCode generated
	/**
	 * Maps DB-Column: CountryIsoCode
	 * Logical type: STRING
	 */
	public String getFormattedHTMLCountryIsoCode() {
		return StringTool.escapeHTML( getFormattedCountryIsoCode() );
	}
	// END Method getFormattedHTMLCountryIsoCode


	// START Method getToStringCountryIsoCode generated
	/**
	 * Maps DB-Column: CountryIsoCode
	 * Logical type: STRING
	 */
	public String getCountryIsoCodeToString() {
		return StringTool.nullToEmpty(getCountryIsoCode());
	}
	// END Method getToStringCountryIsoCode


	// START Method setFromStringCountryIsoCode generated
	/**
	 * Maps DB-Column: CountryIsoCode
	 * Logical type: STRING
	 */
	public void setCountryIsoCodeFromString( String pCountryIsoCode ) {
		setCountryIsoCode( StringTool.emptyToNull( pCountryIsoCode ) );
	}
	// END Method setFromStringCountryIsoCode


	// START Method getColumnMetaDataCountryIsoCode generated
	/**
	 * Maps DB-Column: CountryIsoCode
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForCountryIsoCode() {
		return getBasicDCColumnMetaData( FIELD_COUNTRYISOCODE );
	}
	// END Method getColumnMetaDataCountryIsoCode


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
