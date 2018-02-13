/*
 * (c)2007 Bayer AG Leverkusen, Bayer Business Solutions
 * All rights reserved.
 * (based on DCGenenerator DC-Definitions: Id: DCGenerator.ini 10163 2015-10-28 17:47:12Z imwif )
 *
 * $Id$
 */
package com.bayer.bhc.doc41webui.integration.db.dc;


import java.util.*;

import com.bayer.ecim.foundation.basic.*;
import com.bayer.ecim.foundation.dbx.*;




/**
 * Extended Permission DC of Doc41 with fields marking required extra objects (Custmer, Country, Vendor, Plant)
 */
public class UMDoc41PermissionNDC
	extends com.bayer.ecim.foundation.web.usermanagementN.UMPermissionNDC
{

	/** Maps DB-Column: has_Customer */
	Boolean cHasCustomer = Boolean.FALSE;
	public static final String FIELD_HASCUSTOMER = "HasCustomer";

	/** Maps DB-Column: has_Vendor */
	Boolean cHasVendor = Boolean.FALSE;
	public static final String FIELD_HASVENDOR = "HasVendor";

	/** Maps DB-Column: has_Country */
	Boolean cHasCountry = Boolean.FALSE;
	public static final String FIELD_HASCOUNTRY = "HasCountry";

	/** Maps DB-Column: has_Plant */
	Boolean cHasPlant = Boolean.FALSE;
	public static final String FIELD_HASPLANT = "HasPlant";

	/** for compatibility, suppress import warning...*/
	public static final Class<DataCarrier> _DBX_DC_CLASS_UMDOC41PERMISSIONNDC = DataCarrier.class; // dummy variable to avoid warning

	/** The master Class of this class. */
	public final Class<BasicDataCarrier> MASTER_CLASS_UMDOC41PERMISSIONNDC = BasicDataCarrier.class;

	/** Set the database table-name (null if unspecified) for this DC. */
	static {
		setDBTablename( UMDoc41PermissionNDC.class, "[UM].UM_Permissions" );
	}

	private static final long serialVersionUID = 20151110091259861L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_UMDOC41PERMISSIONNDC = java.math.BigDecimal.class;

	private static final HashMap<String,BasicDCFieldMeta> FIELD_META = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.web.usermanagementN.UMPermissionNDC.localGetFieldMetaMap() );
	private static final String[] LOCAL_FIELD_LIST = new String[] {FIELD_HASCUSTOMER, FIELD_HASVENDOR, FIELD_HASCOUNTRY, FIELD_HASPLANT};
	private static final String[] FIELD_LIST = StringTool.merge(  com.bayer.ecim.foundation.web.usermanagementN.UMPermissionNDC.localGetFieldList(), LOCAL_FIELD_LIST );

	static {
		try {
			FIELD_META.put( FIELD_HASCUSTOMER,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_HASVENDOR,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_HASCOUNTRY,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_HASPLANT,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of UMDoc41PermissionNDC!", null );
		}
	}

	/**
	 * The Constructor.
	 * (a)deprecated Use: static UMDoc41PermissionNDC newInstanceOfUMDoc41PermissionNDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public UMDoc41PermissionNDC() {
		super( (Boolean)null );
	}

	/**
	 * Temporary Constructor.
	 */
	protected UMDoc41PermissionNDC( Boolean pVal ) {
		super( pVal );
		pVal = null;
	}

	/**
	 * The Copy Constructor.
	 * (a)deprecated Use: static UMDoc41PermissionNDC newInstanceOfUMDoc41PermissionNDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public UMDoc41PermissionNDC( BasicDataCarrier pDC )
		throws InitException
	{
		super( (Boolean)null );
		copyFrom( pDC );
	}
	

	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * @throws InitException if the instantiation fails.
	 */
	public static UMDoc41PermissionNDC newInstanceOfUMDoc41PermissionNDC() throws InitException {
		return (UMDoc41PermissionNDC)newInstanceOf( UMDoc41PermissionNDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 * @throws InitException if the instantiation fails.
	 */
	public static UMDoc41PermissionNDC newInstanceOfUMDoc41PermissionNDC( Locale pLoc ) throws InitException {
		return (UMDoc41PermissionNDC)localizeDC( newInstanceOfUMDoc41PermissionNDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pDC dc to copy the attributes from.
	 * @throws InitException if the instantiation fails.
	 */
	public static UMDoc41PermissionNDC newInstanceOfUMDoc41PermissionNDC( BasicDataCarrier pDC ) throws InitException {
		return (UMDoc41PermissionNDC)newInstanceOfUMDoc41PermissionNDC().copyFrom( pDC );
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
	public static final int CHECK_UMDOC41PERMISSIONNDC = com.bayer.ecim.foundation.web.usermanagementN.UMPermissionNDC.CHECK_UMPERMISSIONNDC;

	/**
	 * Copy the parameter DC pOther into this.
	 */
	@Override
	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) throws InitException {
		super.copyFrom(pOther);
		if (UMDoc41PermissionNDC.class.isAssignableFrom(pOther.getClass())) {
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
	 * Get the CVS meta-data of this DC and it's used generator.
	 */
	public static BasicDCGeneratorMeta localGetCVSMeta() {
		return new BasicDCGeneratorMeta(
			"com.bayer.bhc.doc41webui.integration.db.dc",
			"UMDoc41PermissionNDC",
			"com.bayer.ecim.foundation.web.usermanagementN.UMPermissionNDC",
			"Date: 2015-10-28 18:47:12 +0100 (Wed, 28 Oct 2015) ",
			"Revision: 10163 ",
			"Author: imwif ",
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
		return UMDoc41PermissionNDC.localGetCVSMeta();
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
		return UMDoc41PermissionNDC.localGetFieldMeta( pFieldName );
	}

	/**
	 * Provides a field list of all fields that are supported by the DC.
	 */
	public static String[] localGetFieldList() {
		return FIELD_LIST;
	}


	// START Method getHasCustomer generated
	/**
	 * Maps DB-Column: has_Customer
	 * Logical type: BOOLEAN
	 */
	public Boolean getHasCustomer() {
		return cHasCustomer;
	}
	// END Method getHasCustomer


	// START Method setHasCustomer generated
	/**
	 * Maps DB-Column: has_Customer
	 * Logical type: BOOLEAN
	 */
	public void setHasCustomer( Boolean pHasCustomer ) {
		cHasCustomer = pHasCustomer;
		forgetOriginalValue( FIELD_HASCUSTOMER );
		touchField( FIELD_HASCUSTOMER );
	}
	// END Method setHasCustomer


	// START Method getFormattedHasCustomer generated
	/**
	 * Maps DB-Column: has_Customer
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHasCustomer() {
		return hasInvalidValue( FIELD_HASCUSTOMER ) ? getOriginalValue( FIELD_HASCUSTOMER ) : getPool().formatBoolean( cHasCustomer );
	}
	// END Method getFormattedHasCustomer


	// START Method setFormattedHasCustomer generated
	/**
	 * Maps DB-Column: has_Customer
	 * Logical type: BOOLEAN
	 */
	public void setFormattedHasCustomer( String pHasCustomer )
		throws java.text.ParseException
	{
		setHasCustomer( null );
		memorizeOriginalValue( FIELD_HASCUSTOMER, pHasCustomer );
		setHasCustomer( getPool().parseBoolean( pHasCustomer ) );
	}
	// END Method setFormattedHasCustomer


	// START Method getFormattedHTMLHasCustomer generated
	/**
	 * Maps DB-Column: has_Customer
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLHasCustomer() {
		return StringTool.escapeHTML( getFormattedHasCustomer() );
	}
	// END Method getFormattedHTMLHasCustomer


	// START Method getToStringHasCustomer generated
	/**
	 * Maps DB-Column: has_Customer
	 * Logical type: BOOLEAN
	 */
	public String getHasCustomerToString() {
		return StringTool.nullToEmpty(getHasCustomer());
	}
	// END Method getToStringHasCustomer


	// START Method setFromStringHasCustomer generated
	/**
	 * Maps DB-Column: has_Customer
	 * Logical type: BOOLEAN
	 */
	public void setHasCustomerFromString( String pHasCustomer ) {
		setHasCustomer( BooleanTool.parseBoolean( pHasCustomer, null ) );
	}
	// END Method setFromStringHasCustomer


	// START Method getColumnMetaDataHasCustomer generated
	/**
	 * Maps DB-Column: has_Customer
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForHasCustomer() {
		return getBasicDCColumnMetaData( FIELD_HASCUSTOMER );
	}
	// END Method getColumnMetaDataHasCustomer


	// START Method getHasVendor generated
	/**
	 * Maps DB-Column: has_Vendor
	 * Logical type: BOOLEAN
	 */
	public Boolean getHasVendor() {
		return cHasVendor;
	}
	// END Method getHasVendor


	// START Method setHasVendor generated
	/**
	 * Maps DB-Column: has_Vendor
	 * Logical type: BOOLEAN
	 */
	public void setHasVendor( Boolean pHasVendor ) {
		cHasVendor = pHasVendor;
		forgetOriginalValue( FIELD_HASVENDOR );
		touchField( FIELD_HASVENDOR );
	}
	// END Method setHasVendor


	// START Method getFormattedHasVendor generated
	/**
	 * Maps DB-Column: has_Vendor
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHasVendor() {
		return hasInvalidValue( FIELD_HASVENDOR ) ? getOriginalValue( FIELD_HASVENDOR ) : getPool().formatBoolean( cHasVendor );
	}
	// END Method getFormattedHasVendor


	// START Method setFormattedHasVendor generated
	/**
	 * Maps DB-Column: has_Vendor
	 * Logical type: BOOLEAN
	 */
	public void setFormattedHasVendor( String pHasVendor )
		throws java.text.ParseException
	{
		setHasVendor( null );
		memorizeOriginalValue( FIELD_HASVENDOR, pHasVendor );
		setHasVendor( getPool().parseBoolean( pHasVendor ) );
	}
	// END Method setFormattedHasVendor


	// START Method getFormattedHTMLHasVendor generated
	/**
	 * Maps DB-Column: has_Vendor
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLHasVendor() {
		return StringTool.escapeHTML( getFormattedHasVendor() );
	}
	// END Method getFormattedHTMLHasVendor


	// START Method getToStringHasVendor generated
	/**
	 * Maps DB-Column: has_Vendor
	 * Logical type: BOOLEAN
	 */
	public String getHasVendorToString() {
		return StringTool.nullToEmpty(getHasVendor());
	}
	// END Method getToStringHasVendor


	// START Method setFromStringHasVendor generated
	/**
	 * Maps DB-Column: has_Vendor
	 * Logical type: BOOLEAN
	 */
	public void setHasVendorFromString( String pHasVendor ) {
		setHasVendor( BooleanTool.parseBoolean( pHasVendor, null ) );
	}
	// END Method setFromStringHasVendor


	// START Method getColumnMetaDataHasVendor generated
	/**
	 * Maps DB-Column: has_Vendor
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForHasVendor() {
		return getBasicDCColumnMetaData( FIELD_HASVENDOR );
	}
	// END Method getColumnMetaDataHasVendor


	// START Method getHasCountry generated
	/**
	 * Maps DB-Column: has_Country
	 * Logical type: BOOLEAN
	 */
	public Boolean getHasCountry() {
		return cHasCountry;
	}
	// END Method getHasCountry


	// START Method setHasCountry generated
	/**
	 * Maps DB-Column: has_Country
	 * Logical type: BOOLEAN
	 */
	public void setHasCountry( Boolean pHasCountry ) {
		cHasCountry = pHasCountry;
		forgetOriginalValue( FIELD_HASCOUNTRY );
		touchField( FIELD_HASCOUNTRY );
	}
	// END Method setHasCountry


	// START Method getFormattedHasCountry generated
	/**
	 * Maps DB-Column: has_Country
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHasCountry() {
		return hasInvalidValue( FIELD_HASCOUNTRY ) ? getOriginalValue( FIELD_HASCOUNTRY ) : getPool().formatBoolean( cHasCountry );
	}
	// END Method getFormattedHasCountry


	// START Method setFormattedHasCountry generated
	/**
	 * Maps DB-Column: has_Country
	 * Logical type: BOOLEAN
	 */
	public void setFormattedHasCountry( String pHasCountry )
		throws java.text.ParseException
	{
		setHasCountry( null );
		memorizeOriginalValue( FIELD_HASCOUNTRY, pHasCountry );
		setHasCountry( getPool().parseBoolean( pHasCountry ) );
	}
	// END Method setFormattedHasCountry


	// START Method getFormattedHTMLHasCountry generated
	/**
	 * Maps DB-Column: has_Country
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLHasCountry() {
		return StringTool.escapeHTML( getFormattedHasCountry() );
	}
	// END Method getFormattedHTMLHasCountry


	// START Method getToStringHasCountry generated
	/**
	 * Maps DB-Column: has_Country
	 * Logical type: BOOLEAN
	 */
	public String getHasCountryToString() {
		return StringTool.nullToEmpty(getHasCountry());
	}
	// END Method getToStringHasCountry


	// START Method setFromStringHasCountry generated
	/**
	 * Maps DB-Column: has_Country
	 * Logical type: BOOLEAN
	 */
	public void setHasCountryFromString( String pHasCountry ) {
		setHasCountry( BooleanTool.parseBoolean( pHasCountry, null ) );
	}
	// END Method setFromStringHasCountry


	// START Method getColumnMetaDataHasCountry generated
	/**
	 * Maps DB-Column: has_Country
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForHasCountry() {
		return getBasicDCColumnMetaData( FIELD_HASCOUNTRY );
	}
	// END Method getColumnMetaDataHasCountry


	// START Method getHasPlant generated
	/**
	 * Maps DB-Column: has_Plant
	 * Logical type: BOOLEAN
	 */
	public Boolean getHasPlant() {
		return cHasPlant;
	}
	// END Method getHasPlant


	// START Method setHasPlant generated
	/**
	 * Maps DB-Column: has_Plant
	 * Logical type: BOOLEAN
	 */
	public void setHasPlant( Boolean pHasPlant ) {
		cHasPlant = pHasPlant;
		forgetOriginalValue( FIELD_HASPLANT );
		touchField( FIELD_HASPLANT );
	}
	// END Method setHasPlant


	// START Method getFormattedHasPlant generated
	/**
	 * Maps DB-Column: has_Plant
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHasPlant() {
		return hasInvalidValue( FIELD_HASPLANT ) ? getOriginalValue( FIELD_HASPLANT ) : getPool().formatBoolean( cHasPlant );
	}
	// END Method getFormattedHasPlant


	// START Method setFormattedHasPlant generated
	/**
	 * Maps DB-Column: has_Plant
	 * Logical type: BOOLEAN
	 */
	public void setFormattedHasPlant( String pHasPlant )
		throws java.text.ParseException
	{
		setHasPlant( null );
		memorizeOriginalValue( FIELD_HASPLANT, pHasPlant );
		setHasPlant( getPool().parseBoolean( pHasPlant ) );
	}
	// END Method setFormattedHasPlant


	// START Method getFormattedHTMLHasPlant generated
	/**
	 * Maps DB-Column: has_Plant
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLHasPlant() {
		return StringTool.escapeHTML( getFormattedHasPlant() );
	}
	// END Method getFormattedHTMLHasPlant


	// START Method getToStringHasPlant generated
	/**
	 * Maps DB-Column: has_Plant
	 * Logical type: BOOLEAN
	 */
	public String getHasPlantToString() {
		return StringTool.nullToEmpty(getHasPlant());
	}
	// END Method getToStringHasPlant


	// START Method setFromStringHasPlant generated
	/**
	 * Maps DB-Column: has_Plant
	 * Logical type: BOOLEAN
	 */
	public void setHasPlantFromString( String pHasPlant ) {
		setHasPlant( BooleanTool.parseBoolean( pHasPlant, null ) );
	}
	// END Method setFromStringHasPlant


	// START Method getColumnMetaDataHasPlant generated
	/**
	 * Maps DB-Column: has_Plant
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForHasPlant() {
		return getBasicDCColumnMetaData( FIELD_HASPLANT );
	}
	// END Method getColumnMetaDataHasPlant


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
