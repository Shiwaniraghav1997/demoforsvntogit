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
public class   SapCustomerDC
       extends com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier {

	/** Maps DB-Column: partner_Number */
	String cPartnerNumber;
	public static final String FIELD_PARTNERNUMBER = "PartnerNumber";

	/** Maps DB-Column: name1 */
	String cName1;
	public static final String FIELD_NAME1 = "Name1";

	/** Maps DB-Column: name2 */
	String cName2;
	public static final String FIELD_NAME2 = "Name2";

	/** Maps DB-Column: street */
	String cStreet;
	public static final String FIELD_STREET = "Street";

	/** Maps DB-Column: postal_Code */
	String cPostalCode;
	public static final String FIELD_POSTALCODE = "PostalCode";

	/** Maps DB-Column: city */
	String cCity;
	public static final String FIELD_CITY = "City";

	/** Maps DB-Column: country_Iso_Code */
	String cCountryIsoCode;
	public static final String FIELD_COUNTRYISOCODE = "CountryIsoCode";

	/** Maps DB-Column: is_Deleted */
	Boolean cIsDeleted;
	public static final String FIELD_ISDELETED = "IsDeleted";

	/** Maps DB-Column: sap_Changed */
	Date cSapChanged;
	public static final String FIELD_SAPCHANGED = "SapChanged";

	/** for compatibility: dummy variable to suppress import warning...*/
	public static final Class<DataCarrier> _DBX_DC_CLASS_SAPCUSTOMERDC = DataCarrier.class;

	/** The master Class of this class. */
	public static final Class<BasicDataCarrier> MASTER_CLASS_SAPCUSTOMERDC = BasicDataCarrier.class;

	/** Set the database table-name (null if unspecified) for this DC. */
	static {
		setDBTablename( SapCustomerDC.class, "[DOC41WEB_MGR].SAP_CUSTOMER" );
	}

	private static final long serialVersionUID = 20180319062713936L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_SAPCUSTOMERDC = java.math.BigDecimal.class;

	private static final HashMap<String,BasicDCFieldMeta> FIELD_META       = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldMetaMap() );
	private static final String[]                         LOCAL_FIELD_LIST = new String[] {FIELD_PARTNERNUMBER, FIELD_NAME1, FIELD_NAME2, FIELD_STREET, FIELD_POSTALCODE, FIELD_CITY, FIELD_COUNTRYISOCODE, FIELD_ISDELETED, FIELD_SAPCHANGED};
	private static final String[]                         FIELD_LIST       = StringTool.merge( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldList(), LOCAL_FIELD_LIST );

	/** Regeneration check for subclasses. If this line causes an error, you forgot to regenerate the direct superclass */
	public static final int CHECK_SAPCUSTOMERDC = com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.CHECK_USERCHANGEABLEDATACARRIER;

	static {
		try {
			FIELD_META.put( FIELD_PARTNERNUMBER,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_NAME1,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_NAME2,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_STREET,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_POSTALCODE,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_CITY,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_COUNTRYISOCODE,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_ISDELETED,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_SAPCHANGED,	new BasicDCFieldMeta( "DATETS",	"TIMESTAMP",	Date.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of SapCustomerDC!", e );
		}
	}

	/**
	 * The standard Constructor (Framework internal: do not use in applications!).
	 * @deprecated Use: static SapCustomerDC newInstanceOfSapCustomerDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public SapCustomerDC() {
		super( (Boolean)null );
	}

	/**
	 * Constructor for class-hierarchy (Framework internal: do not use in applications!).
	 */
	protected SapCustomerDC( Boolean pVal ) {
		super( pVal );
	}

	/**
	 * The Copy Constructor.
	 * @deprecated Use: static SapCustomerDC newInstanceOfSapCustomerDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public SapCustomerDC( BasicDataCarrier pDC ) {
		super( (Boolean)null );
		copyFrom( pDC );
	}


	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * throws an InitException if the instantiation fails.
	 */
	public static SapCustomerDC newInstanceOfSapCustomerDC() {
		return newInstanceOf( SapCustomerDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 */
	public static SapCustomerDC newInstanceOfSapCustomerDC( Locale pLoc ) {
		return localizeDC( newInstanceOfSapCustomerDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pDC DC to copy the attributes from.
	 */
	public static SapCustomerDC newInstanceOfSapCustomerDC( BasicDataCarrier pDC ) {
		return (SapCustomerDC)newInstanceOfSapCustomerDC().copyFrom( pDC );
	}

	/**
	 * Copy the parameter DC pOther into this.
	 */
	@Override
	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) {
		super.copyFrom(pOther);
		if (SapCustomerDC.class.isAssignableFrom(pOther.getClass())) {
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
			"SapCustomerDC",
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
		return SapCustomerDC.localGetCVSMeta();
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
		return SapCustomerDC.localGetFieldMeta( pFieldName );
	}

	/**
	 * Provides a field list of all fields that are supported by the DC.
	 */
	public static String[] localGetFieldList() {
		return FIELD_LIST.clone( );
	}


	// START Method getPartnerNumber generated
	/**
	 * Maps DB-Column: partner_Number
	 * Logical type: STRING
	 */
	public String getPartnerNumber() {
		return cPartnerNumber;
	}
	// END Method getPartnerNumber


	// START Method setPartnerNumber generated
	/**
	 * Maps DB-Column: partner_Number
	 * Logical type: STRING
	 */
	public void setPartnerNumber( String pPartnerNumber ) {
		cPartnerNumber = StringTool.trimmedEmptyToNull( pPartnerNumber );
		forgetOriginalValue( FIELD_PARTNERNUMBER );
		touchField( FIELD_PARTNERNUMBER );
	}
	// END Method setPartnerNumber


	// START Method getFormattedPartnerNumber generated
	/**
	 * Maps DB-Column: partner_Number
	 * Logical type: STRING
	 */
	public String getFormattedPartnerNumber() {
		return hasInvalidValue( FIELD_PARTNERNUMBER ) ? getOriginalValue( FIELD_PARTNERNUMBER ) : getPool().formatString( cPartnerNumber );
	}
	// END Method getFormattedPartnerNumber


	// START Method setFormattedPartnerNumber generated
	/**
	 * Maps DB-Column: partner_Number
	 * Logical type: STRING
	 */
	public void setFormattedPartnerNumber( String pPartnerNumber ) throws java.text.ParseException {
		setPartnerNumber( null );
		memorizeOriginalValue( FIELD_PARTNERNUMBER, pPartnerNumber );
		setPartnerNumber( getPool().parseString( pPartnerNumber ) );
	}
	// END Method setFormattedPartnerNumber


	// START Method getFormattedHTMLPartnerNumber generated
	/**
	 * Maps DB-Column: partner_Number
	 * Logical type: STRING
	 */
	public String getFormattedHTMLPartnerNumber() {
		return StringTool.escapeHTML( getFormattedPartnerNumber() );
	}
	// END Method getFormattedHTMLPartnerNumber


	// START Method getToStringPartnerNumber generated
	/**
	 * Maps DB-Column: partner_Number
	 * Logical type: STRING
	 */
	public String getPartnerNumberToString() {
		return StringTool.nullToEmpty(getPartnerNumber());
	}
	// END Method getToStringPartnerNumber


	// START Method setFromStringPartnerNumber generated
	/**
	 * Maps DB-Column: partner_Number
	 * Logical type: STRING
	 */
	public void setPartnerNumberFromString( String pPartnerNumber ) {
		setPartnerNumber( StringTool.emptyToNull( pPartnerNumber ) );
	}
	// END Method setFromStringPartnerNumber


	// START Method getColumnMetaDataPartnerNumber generated
	/**
	 * Maps DB-Column: partner_Number
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForPartnerNumber() {
		return getBasicDCColumnMetaData( FIELD_PARTNERNUMBER );
	}
	// END Method getColumnMetaDataPartnerNumber


	// START Method getName1 generated
	/**
	 * Maps DB-Column: name1
	 * Logical type: STRING
	 */
	public String getName1() {
		return cName1;
	}
	// END Method getName1


	// START Method setName1 generated
	/**
	 * Maps DB-Column: name1
	 * Logical type: STRING
	 */
	public void setName1( String pName1 ) {
		cName1 = StringTool.trimmedEmptyToNull( pName1 );
		forgetOriginalValue( FIELD_NAME1 );
		touchField( FIELD_NAME1 );
	}
	// END Method setName1


	// START Method getFormattedName1 generated
	/**
	 * Maps DB-Column: name1
	 * Logical type: STRING
	 */
	public String getFormattedName1() {
		return hasInvalidValue( FIELD_NAME1 ) ? getOriginalValue( FIELD_NAME1 ) : getPool().formatString( cName1 );
	}
	// END Method getFormattedName1


	// START Method setFormattedName1 generated
	/**
	 * Maps DB-Column: name1
	 * Logical type: STRING
	 */
	public void setFormattedName1( String pName1 ) throws java.text.ParseException {
		setName1( null );
		memorizeOriginalValue( FIELD_NAME1, pName1 );
		setName1( getPool().parseString( pName1 ) );
	}
	// END Method setFormattedName1


	// START Method getFormattedHTMLName1 generated
	/**
	 * Maps DB-Column: name1
	 * Logical type: STRING
	 */
	public String getFormattedHTMLName1() {
		return StringTool.escapeHTML( getFormattedName1() );
	}
	// END Method getFormattedHTMLName1


	// START Method getToStringName1 generated
	/**
	 * Maps DB-Column: name1
	 * Logical type: STRING
	 */
	public String getName1ToString() {
		return StringTool.nullToEmpty(getName1());
	}
	// END Method getToStringName1


	// START Method setFromStringName1 generated
	/**
	 * Maps DB-Column: name1
	 * Logical type: STRING
	 */
	public void setName1FromString( String pName1 ) {
		setName1( StringTool.emptyToNull( pName1 ) );
	}
	// END Method setFromStringName1


	// START Method getColumnMetaDataName1 generated
	/**
	 * Maps DB-Column: name1
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForName1() {
		return getBasicDCColumnMetaData( FIELD_NAME1 );
	}
	// END Method getColumnMetaDataName1


	// START Method getName2 generated
	/**
	 * Maps DB-Column: name2
	 * Logical type: STRING
	 */
	public String getName2() {
		return cName2;
	}
	// END Method getName2


	// START Method setName2 generated
	/**
	 * Maps DB-Column: name2
	 * Logical type: STRING
	 */
	public void setName2( String pName2 ) {
		cName2 = StringTool.trimmedEmptyToNull( pName2 );
		forgetOriginalValue( FIELD_NAME2 );
		touchField( FIELD_NAME2 );
	}
	// END Method setName2


	// START Method getFormattedName2 generated
	/**
	 * Maps DB-Column: name2
	 * Logical type: STRING
	 */
	public String getFormattedName2() {
		return hasInvalidValue( FIELD_NAME2 ) ? getOriginalValue( FIELD_NAME2 ) : getPool().formatString( cName2 );
	}
	// END Method getFormattedName2


	// START Method setFormattedName2 generated
	/**
	 * Maps DB-Column: name2
	 * Logical type: STRING
	 */
	public void setFormattedName2( String pName2 ) throws java.text.ParseException {
		setName2( null );
		memorizeOriginalValue( FIELD_NAME2, pName2 );
		setName2( getPool().parseString( pName2 ) );
	}
	// END Method setFormattedName2


	// START Method getFormattedHTMLName2 generated
	/**
	 * Maps DB-Column: name2
	 * Logical type: STRING
	 */
	public String getFormattedHTMLName2() {
		return StringTool.escapeHTML( getFormattedName2() );
	}
	// END Method getFormattedHTMLName2


	// START Method getToStringName2 generated
	/**
	 * Maps DB-Column: name2
	 * Logical type: STRING
	 */
	public String getName2ToString() {
		return StringTool.nullToEmpty(getName2());
	}
	// END Method getToStringName2


	// START Method setFromStringName2 generated
	/**
	 * Maps DB-Column: name2
	 * Logical type: STRING
	 */
	public void setName2FromString( String pName2 ) {
		setName2( StringTool.emptyToNull( pName2 ) );
	}
	// END Method setFromStringName2


	// START Method getColumnMetaDataName2 generated
	/**
	 * Maps DB-Column: name2
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForName2() {
		return getBasicDCColumnMetaData( FIELD_NAME2 );
	}
	// END Method getColumnMetaDataName2


	// START Method getStreet generated
	/**
	 * Maps DB-Column: street
	 * Logical type: STRING
	 */
	public String getStreet() {
		return cStreet;
	}
	// END Method getStreet


	// START Method setStreet generated
	/**
	 * Maps DB-Column: street
	 * Logical type: STRING
	 */
	public void setStreet( String pStreet ) {
		cStreet = StringTool.trimmedEmptyToNull( pStreet );
		forgetOriginalValue( FIELD_STREET );
		touchField( FIELD_STREET );
	}
	// END Method setStreet


	// START Method getFormattedStreet generated
	/**
	 * Maps DB-Column: street
	 * Logical type: STRING
	 */
	public String getFormattedStreet() {
		return hasInvalidValue( FIELD_STREET ) ? getOriginalValue( FIELD_STREET ) : getPool().formatString( cStreet );
	}
	// END Method getFormattedStreet


	// START Method setFormattedStreet generated
	/**
	 * Maps DB-Column: street
	 * Logical type: STRING
	 */
	public void setFormattedStreet( String pStreet ) throws java.text.ParseException {
		setStreet( null );
		memorizeOriginalValue( FIELD_STREET, pStreet );
		setStreet( getPool().parseString( pStreet ) );
	}
	// END Method setFormattedStreet


	// START Method getFormattedHTMLStreet generated
	/**
	 * Maps DB-Column: street
	 * Logical type: STRING
	 */
	public String getFormattedHTMLStreet() {
		return StringTool.escapeHTML( getFormattedStreet() );
	}
	// END Method getFormattedHTMLStreet


	// START Method getToStringStreet generated
	/**
	 * Maps DB-Column: street
	 * Logical type: STRING
	 */
	public String getStreetToString() {
		return StringTool.nullToEmpty(getStreet());
	}
	// END Method getToStringStreet


	// START Method setFromStringStreet generated
	/**
	 * Maps DB-Column: street
	 * Logical type: STRING
	 */
	public void setStreetFromString( String pStreet ) {
		setStreet( StringTool.emptyToNull( pStreet ) );
	}
	// END Method setFromStringStreet


	// START Method getColumnMetaDataStreet generated
	/**
	 * Maps DB-Column: street
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForStreet() {
		return getBasicDCColumnMetaData( FIELD_STREET );
	}
	// END Method getColumnMetaDataStreet


	// START Method getPostalCode generated
	/**
	 * Maps DB-Column: postal_Code
	 * Logical type: STRING
	 */
	public String getPostalCode() {
		return cPostalCode;
	}
	// END Method getPostalCode


	// START Method setPostalCode generated
	/**
	 * Maps DB-Column: postal_Code
	 * Logical type: STRING
	 */
	public void setPostalCode( String pPostalCode ) {
		cPostalCode = StringTool.trimmedEmptyToNull( pPostalCode );
		forgetOriginalValue( FIELD_POSTALCODE );
		touchField( FIELD_POSTALCODE );
	}
	// END Method setPostalCode


	// START Method getFormattedPostalCode generated
	/**
	 * Maps DB-Column: postal_Code
	 * Logical type: STRING
	 */
	public String getFormattedPostalCode() {
		return hasInvalidValue( FIELD_POSTALCODE ) ? getOriginalValue( FIELD_POSTALCODE ) : getPool().formatString( cPostalCode );
	}
	// END Method getFormattedPostalCode


	// START Method setFormattedPostalCode generated
	/**
	 * Maps DB-Column: postal_Code
	 * Logical type: STRING
	 */
	public void setFormattedPostalCode( String pPostalCode ) throws java.text.ParseException {
		setPostalCode( null );
		memorizeOriginalValue( FIELD_POSTALCODE, pPostalCode );
		setPostalCode( getPool().parseString( pPostalCode ) );
	}
	// END Method setFormattedPostalCode


	// START Method getFormattedHTMLPostalCode generated
	/**
	 * Maps DB-Column: postal_Code
	 * Logical type: STRING
	 */
	public String getFormattedHTMLPostalCode() {
		return StringTool.escapeHTML( getFormattedPostalCode() );
	}
	// END Method getFormattedHTMLPostalCode


	// START Method getToStringPostalCode generated
	/**
	 * Maps DB-Column: postal_Code
	 * Logical type: STRING
	 */
	public String getPostalCodeToString() {
		return StringTool.nullToEmpty(getPostalCode());
	}
	// END Method getToStringPostalCode


	// START Method setFromStringPostalCode generated
	/**
	 * Maps DB-Column: postal_Code
	 * Logical type: STRING
	 */
	public void setPostalCodeFromString( String pPostalCode ) {
		setPostalCode( StringTool.emptyToNull( pPostalCode ) );
	}
	// END Method setFromStringPostalCode


	// START Method getColumnMetaDataPostalCode generated
	/**
	 * Maps DB-Column: postal_Code
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForPostalCode() {
		return getBasicDCColumnMetaData( FIELD_POSTALCODE );
	}
	// END Method getColumnMetaDataPostalCode


	// START Method getCity generated
	/**
	 * Maps DB-Column: city
	 * Logical type: STRING
	 */
	public String getCity() {
		return cCity;
	}
	// END Method getCity


	// START Method setCity generated
	/**
	 * Maps DB-Column: city
	 * Logical type: STRING
	 */
	public void setCity( String pCity ) {
		cCity = StringTool.trimmedEmptyToNull( pCity );
		forgetOriginalValue( FIELD_CITY );
		touchField( FIELD_CITY );
	}
	// END Method setCity


	// START Method getFormattedCity generated
	/**
	 * Maps DB-Column: city
	 * Logical type: STRING
	 */
	public String getFormattedCity() {
		return hasInvalidValue( FIELD_CITY ) ? getOriginalValue( FIELD_CITY ) : getPool().formatString( cCity );
	}
	// END Method getFormattedCity


	// START Method setFormattedCity generated
	/**
	 * Maps DB-Column: city
	 * Logical type: STRING
	 */
	public void setFormattedCity( String pCity ) throws java.text.ParseException {
		setCity( null );
		memorizeOriginalValue( FIELD_CITY, pCity );
		setCity( getPool().parseString( pCity ) );
	}
	// END Method setFormattedCity


	// START Method getFormattedHTMLCity generated
	/**
	 * Maps DB-Column: city
	 * Logical type: STRING
	 */
	public String getFormattedHTMLCity() {
		return StringTool.escapeHTML( getFormattedCity() );
	}
	// END Method getFormattedHTMLCity


	// START Method getToStringCity generated
	/**
	 * Maps DB-Column: city
	 * Logical type: STRING
	 */
	public String getCityToString() {
		return StringTool.nullToEmpty(getCity());
	}
	// END Method getToStringCity


	// START Method setFromStringCity generated
	/**
	 * Maps DB-Column: city
	 * Logical type: STRING
	 */
	public void setCityFromString( String pCity ) {
		setCity( StringTool.emptyToNull( pCity ) );
	}
	// END Method setFromStringCity


	// START Method getColumnMetaDataCity generated
	/**
	 * Maps DB-Column: city
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForCity() {
		return getBasicDCColumnMetaData( FIELD_CITY );
	}
	// END Method getColumnMetaDataCity


	// START Method getCountryIsoCode generated
	/**
	 * Maps DB-Column: country_Iso_Code
	 * Logical type: STRING
	 */
	public String getCountryIsoCode() {
		return cCountryIsoCode;
	}
	// END Method getCountryIsoCode


	// START Method setCountryIsoCode generated
	/**
	 * Maps DB-Column: country_Iso_Code
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
	 * Maps DB-Column: country_Iso_Code
	 * Logical type: STRING
	 */
	public String getFormattedCountryIsoCode() {
		return hasInvalidValue( FIELD_COUNTRYISOCODE ) ? getOriginalValue( FIELD_COUNTRYISOCODE ) : getPool().formatString( cCountryIsoCode );
	}
	// END Method getFormattedCountryIsoCode


	// START Method setFormattedCountryIsoCode generated
	/**
	 * Maps DB-Column: country_Iso_Code
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
	 * Maps DB-Column: country_Iso_Code
	 * Logical type: STRING
	 */
	public String getFormattedHTMLCountryIsoCode() {
		return StringTool.escapeHTML( getFormattedCountryIsoCode() );
	}
	// END Method getFormattedHTMLCountryIsoCode


	// START Method getToStringCountryIsoCode generated
	/**
	 * Maps DB-Column: country_Iso_Code
	 * Logical type: STRING
	 */
	public String getCountryIsoCodeToString() {
		return StringTool.nullToEmpty(getCountryIsoCode());
	}
	// END Method getToStringCountryIsoCode


	// START Method setFromStringCountryIsoCode generated
	/**
	 * Maps DB-Column: country_Iso_Code
	 * Logical type: STRING
	 */
	public void setCountryIsoCodeFromString( String pCountryIsoCode ) {
		setCountryIsoCode( StringTool.emptyToNull( pCountryIsoCode ) );
	}
	// END Method setFromStringCountryIsoCode


	// START Method getColumnMetaDataCountryIsoCode generated
	/**
	 * Maps DB-Column: country_Iso_Code
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForCountryIsoCode() {
		return getBasicDCColumnMetaData( FIELD_COUNTRYISOCODE );
	}
	// END Method getColumnMetaDataCountryIsoCode


	// START Method getIsDeleted generated
	/**
	 * Maps DB-Column: is_Deleted
	 * Logical type: BOOLEAN
	 */
	public Boolean getIsDeleted() {
		return cIsDeleted;
	}
	// END Method getIsDeleted


	// START Method setIsDeleted generated
	/**
	 * Maps DB-Column: is_Deleted
	 * Logical type: BOOLEAN
	 */
	public void setIsDeleted( Boolean pIsDeleted ) {
		cIsDeleted = pIsDeleted;
		forgetOriginalValue( FIELD_ISDELETED );
		touchField( FIELD_ISDELETED );
	}
	// END Method setIsDeleted


	// START Method getFormattedIsDeleted generated
	/**
	 * Maps DB-Column: is_Deleted
	 * Logical type: BOOLEAN
	 */
	public String getFormattedIsDeleted() {
		return hasInvalidValue( FIELD_ISDELETED ) ? getOriginalValue( FIELD_ISDELETED ) : getPool().formatBoolean( cIsDeleted );
	}
	// END Method getFormattedIsDeleted


	// START Method setFormattedIsDeleted generated
	/**
	 * Maps DB-Column: is_Deleted
	 * Logical type: BOOLEAN
	 */
	public void setFormattedIsDeleted( String pIsDeleted ) throws java.text.ParseException {
		setIsDeleted( null );
		memorizeOriginalValue( FIELD_ISDELETED, pIsDeleted );
		setIsDeleted( getPool().parseBoolean( pIsDeleted ) );
	}
	// END Method setFormattedIsDeleted


	// START Method getFormattedHTMLIsDeleted generated
	/**
	 * Maps DB-Column: is_Deleted
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLIsDeleted() {
		return StringTool.escapeHTML( getFormattedIsDeleted() );
	}
	// END Method getFormattedHTMLIsDeleted


	// START Method getToStringIsDeleted generated
	/**
	 * Maps DB-Column: is_Deleted
	 * Logical type: BOOLEAN
	 */
	public String getIsDeletedToString() {
		return StringTool.nullToEmpty(getIsDeleted());
	}
	// END Method getToStringIsDeleted


	// START Method setFromStringIsDeleted generated
	/**
	 * Maps DB-Column: is_Deleted
	 * Logical type: BOOLEAN
	 */
	public void setIsDeletedFromString( String pIsDeleted ) {
		setIsDeleted( BooleanTool.parseBoolean( pIsDeleted, null ) );
	}
	// END Method setFromStringIsDeleted


	// START Method getColumnMetaDataIsDeleted generated
	/**
	 * Maps DB-Column: is_Deleted
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForIsDeleted() {
		return getBasicDCColumnMetaData( FIELD_ISDELETED );
	}
	// END Method getColumnMetaDataIsDeleted


	// START Method getSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public Date getSapChanged() {
		return cSapChanged;
	}
	// END Method getSapChanged


	// START Method setSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public void setSapChanged( Date pSapChanged ) {
		cSapChanged = pSapChanged;
		forgetOriginalValue( FIELD_SAPCHANGED );
		touchField( FIELD_SAPCHANGED );
	}
	// END Method setSapChanged


	// START Method getFormattedSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public String getFormattedSapChanged() {
		return hasInvalidValue( FIELD_SAPCHANGED ) ? getOriginalValue( FIELD_SAPCHANGED ) : getPool().formatDate( "TimeStamp", "T:TimeStampShort|Date|TimeStamp$|TimeStampShort$|Date$", getSapChanged() );
	}
	// END Method getFormattedSapChanged


	// START Method setFormattedSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public void setFormattedSapChanged( String pSapChanged ) throws java.text.ParseException {
		setSapChanged( null );
		memorizeOriginalValue( FIELD_SAPCHANGED, pSapChanged );
		setSapChanged( getPool().parseDate( "TimeStamp", "T:TimeStampShort|Date|TimeStamp$|TimeStampShort$|Date$", pSapChanged ) );
	}
	// END Method setFormattedSapChanged


	// START Method getFormattedHTMLSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public String getFormattedHTMLSapChanged() {
		return StringTool.escapeHTML( getFormattedSapChanged() );
	}
	// END Method getFormattedHTMLSapChanged


	// START Method getToStringSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public String getSapChangedToString() {
		return StringTool.nullToEmpty( DateTool.convertSQLTimestamp( getSapChanged() ) );
	}
	// END Method getToStringSapChanged


	// START Method setFromStringSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public void setSapChangedFromString( String pSapChanged ) {
		setSapChanged( DateTool.convertDate( DateTool.parseSQLTimestamp( pSapChanged ) ) );
	}
	// END Method setFromStringSapChanged


	// START Method getColumnMetaDataSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public BasicDCColumnMetaData getColumnMetaDataForSapChanged() {
		return getBasicDCColumnMetaData( FIELD_SAPCHANGED );
	}
	// END Method getColumnMetaDataSapChanged


	// START Method getTZSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public Date getSapChanged( String pSrcTZ, String pDestTZ ) {
		Date mRes = getSapChanged();
		return (mRes == null) ? null : new Date( mRes.getTime() + DateTool.getTimeZoneOffsetForDateInMillis( mRes, pSrcTZ, pDestTZ, true ) );
	}
	// END Method getTZSapChanged


	// START Method setTZSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public void setSapChanged( Date pSapChanged, String pSrcTZ, String pDestTZ ) {
		setSapChanged( (pSapChanged == null) ? null : new Date( pSapChanged.getTime() + DateTool.getTimeZoneOffsetForDateInMillis( pSapChanged, pSrcTZ, pDestTZ, true ) ) );
	}
	// END Method setTZSapChanged


	// START Method getFormattedTZSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public String getFormattedSapChanged( String pSrcTZ, String pDestTZ ) {
		return hasInvalidValue( FIELD_SAPCHANGED ) ? getOriginalValue( FIELD_SAPCHANGED ) : getPool().formatDate( "TimeStamp", "T:TimeStampShort|Date|TimeStamp$|TimeStampShort$|Date$", getSapChanged( pSrcTZ, pDestTZ ) );
	}
	// END Method getFormattedTZSapChanged


	// START Method setFormattedTZSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public void setFormattedSapChanged( String pSapChanged, String pSrcTZ, String pDestTZ ) throws java.text.ParseException {
		setSapChanged( null );
		memorizeOriginalValue( FIELD_SAPCHANGED, pSapChanged );
		setSapChanged( getPool().parseDate( "TimeStamp", "T:TimeStampShort|Date|TimeStamp$|TimeStampShort$|Date$", pSapChanged ), pSrcTZ, pDestTZ );
	}
	// END Method setFormattedTZSapChanged


	// START Method getFormattedHTMLTZSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public String getFormattedHTMLSapChanged( String pSrcTZ, String pDestTZ ) {
		return StringTool.escapeHTML( getFormattedSapChanged( pSrcTZ, pDestTZ ) );
	}
	// END Method getFormattedHTMLTZSapChanged


	// START Method getToStringTZSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public String getSapChangedToString( String pSrcTZ, String pDestTZ ) {
		return StringTool.nullToEmpty( DateTool.convertSQLTimestamp( getSapChanged( pSrcTZ, pDestTZ ) ) );
	}
	// END Method getToStringTZSapChanged


	// START Method setFromStringTZSapChanged generated
	/**
	 * Maps DB-Column: sap_Changed
	 * Logical type: TimeStamp
	 */
	public void setSapChangedFromString( String pSapChanged, String pSrcTZ, String pDestTZ ) {
		setSapChanged( DateTool.convertDate( DateTool.parseSQLTimestamp( pSapChanged ) ), pSrcTZ, pDestTZ );
	}
	// END Method setFromStringTZSapChanged


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
