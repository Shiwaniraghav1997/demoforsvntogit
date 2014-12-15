/*
 * (c)2007 Bayer AG Leverkusen, Bayer Business Solutions
 * All rights reserved.
 * (based on DCGenenerator DC-Definitions: Id: DCGenerator.ini,v 1.82 2012/10/05 12:22:39 evfpu Exp )
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
public class ProfilePermissionDC
	extends com.bayer.ecim.foundation.dbx.DataCarrier
{

	/** Maps DB-Column: permissionname */
	String cPermissionname;
	public static final String FIELD_PERMISSIONNAME = "Permissionname";

	/** Maps DB-Column: permissiondescription */
	String cPermissiondescription;
	public static final String FIELD_PERMISSIONDESCRIPTION = "Permissiondescription";

	/** Maps DB-Column: doc41_Carr */
	Boolean cDoc41Carr;
	public static final String FIELD_DOC41CARR = "Doc41Carr";

	/** Maps DB-Column: doc41_Cusbr */
	Boolean cDoc41Cusbr;
	public static final String FIELD_DOC41CUSBR = "Doc41Cusbr";

	/** Maps DB-Column: doc41_Laysup */
	Boolean cDoc41Laysup;
	public static final String FIELD_DOC41LAYSUP = "Doc41Laysup";

	/** Maps DB-Column: doc41_Pmsup */
	Boolean cDoc41Pmsup;
	public static final String FIELD_DOC41PMSUP = "Doc41Pmsup";

	/** Maps DB-Column: doc41_Badm */
	Boolean cDoc41Badm;
	public static final String FIELD_DOC41BADM = "Doc41Badm";

	/** Maps DB-Column: doc41_Tadm */
	Boolean cDoc41Tadm;
	public static final String FIELD_DOC41TADM = "Doc41Tadm";

	/** Maps DB-Column: doc41_Obsv */
	Boolean cDoc41Obsv;
	public static final String FIELD_DOC41OBSV = "Doc41Obsv";

	/** Maps DB-Column: doc41_matsup */
	Boolean cDoc41Matsup;
	public static final String FIELD_DOC41MATSUP = "Doc41Matsup";

	/** Maps DB-Column: doc41_prodsup */
	Boolean cDoc41Prodsup;
	public static final String FIELD_DOC41PRODSUP = "Doc41Prodsup";

	/** Maps DB-Column: doc41_delcertvcountry */
	Boolean cDoc41Delcertvcountry;
	public static final String FIELD_DOC41DELCERTVCOUNTRY = "Doc41Delcertvcountry";

	/** Maps DB-Column: doc41_delcertvcust */
	Boolean cDoc41Delcertvcust;
	public static final String FIELD_DOC41DELCERTVCUST = "Doc41Delcertvcust";

	/** The master Class of this class. */
	public final Class<DataCarrier> MASTER_CLASS = DataCarrier.class;

	/** Set the database tablename (null if unspecified) for this DC. */
	static {
		setDBTablename( ProfilePermissionDC.class, null );
	}

	private static final long serialVersionUID = 20131111052250143L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_PROFILEPERMISSIONDC = java.math.BigDecimal.class;

	@SuppressWarnings("unchecked")
	private static final HashMap<String,BasicDCFieldMeta> FIELD_META = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.dbx.DataCarrier.localGetFieldMetaMap() );
	private static final String[] LOCAL_FIELD_LIST = new String[] {FIELD_PERMISSIONNAME, FIELD_PERMISSIONDESCRIPTION, FIELD_DOC41CARR, FIELD_DOC41CUSBR, FIELD_DOC41LAYSUP, FIELD_DOC41PMSUP, FIELD_DOC41BADM, FIELD_DOC41TADM, FIELD_DOC41OBSV, FIELD_DOC41MATSUP, FIELD_DOC41PRODSUP, FIELD_DOC41DELCERTVCOUNTRY, FIELD_DOC41DELCERTVCUST};
	private static final String[] FIELD_LIST = StringTool.merge(  com.bayer.ecim.foundation.dbx.DataCarrier.localGetFieldList(), LOCAL_FIELD_LIST );

	static {
		try {
			FIELD_META.put( FIELD_PERMISSIONNAME,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_PERMISSIONDESCRIPTION,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_DOC41CARR,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_DOC41CUSBR,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_DOC41LAYSUP,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_DOC41PMSUP,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_DOC41BADM,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_DOC41TADM,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_DOC41OBSV,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_DOC41MATSUP,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_DOC41PRODSUP,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_DOC41DELCERTVCOUNTRY,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_DOC41DELCERTVCUST,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of ProfilePermissionDC!", null );
		}
	}

	/**
	 * The Constructor.
	 * (a)deprecated Use: static ProfilePermissionDC newInstanceOfProfilePermissionDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public ProfilePermissionDC() {
		super( (Boolean)null );
	}

	/**
	 * Temporary Constructor.
	 */
	protected ProfilePermissionDC( Boolean pVal ) {
		super( pVal );
		pVal = null;
	}

	/**
	 * The Copy Constructor.
	 * (a)deprecated Use: static ProfilePermissionDC newInstanceOfProfilePermissionDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public ProfilePermissionDC( BasicDataCarrier pDC )
		throws InitException
	{
		super( (Boolean)null );
		copyFrom( pDC );
	}
	

	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * @throws InitException if the instanciation failes.
	 */
	public static ProfilePermissionDC newInstanceOfProfilePermissionDC() throws InitException {
		return (ProfilePermissionDC)newInstanceOf( ProfilePermissionDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 * @throws InitException if the instanciation failes.
	 */
	public static ProfilePermissionDC newInstanceOfProfilePermissionDC( Locale pLoc ) throws InitException {
		return (ProfilePermissionDC)localizeDC( newInstanceOfProfilePermissionDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pDC dc to copy the attributes from.
	 * @throws InitException if the instanciation failes.
	 */
	public static ProfilePermissionDC newInstanceOfProfilePermissionDC( BasicDataCarrier pDC ) throws InitException {
		return (ProfilePermissionDC)newInstanceOfProfilePermissionDC().copyFrom( pDC );
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
	public static final int CHECK_PROFILEPERMISSIONDC = com.bayer.ecim.foundation.dbx.DataCarrier.CHECK_DATACARRIER;

	/**
	 * Copy the parameter DC pOther into this.
	 */
	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) throws InitException {
		super.copyFrom(pOther);
		if (ProfilePermissionDC.class.isAssignableFrom(pOther.getClass())) {
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
			"com.bayer.bhc.doc41webui.integration.db.dc",
			"ProfilePermissionDC",
			"com.bayer.ecim.foundation.dbx.DataCarrier",
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
		return ProfilePermissionDC.localGetCVSMeta();
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
		return ProfilePermissionDC.localGetFieldMeta( pFieldName );
	}

	/**
	 * Provides a field list of all fields that are supported by the DC.
	 */
	public static String[] localGetFieldList() {
		return FIELD_LIST;
	}


	// START Method getPermissionname generated
	/**
	 * Maps DB-Column: permissionname
	 * Logical type: STRING
	 */
	public String getPermissionname() {
		return cPermissionname;
	}
	// END Method getPermissionname


	// START Method setPermissionname generated
	/**
	 * Maps DB-Column: permissionname
	 * Logical type: STRING
	 */
	public void setPermissionname( String pPermissionname ) {
		cPermissionname = StringTool.trimmedEmptyToNull( pPermissionname );
		forgetOriginalValue( FIELD_PERMISSIONNAME );
		touchField( FIELD_PERMISSIONNAME );
	}
	// END Method setPermissionname


	// START Method getFormattedPermissionname generated
	/**
	 * Maps DB-Column: permissionname
	 * Logical type: STRING
	 */
	public String getFormattedPermissionname() {
		return hasInvalidValue( FIELD_PERMISSIONNAME ) ? getOriginalValue( FIELD_PERMISSIONNAME ) : getPool().formatString( cPermissionname );
	}
	// END Method getFormattedPermissionname


	// START Method setFormattedPermissionname generated
	/**
	 * Maps DB-Column: permissionname
	 * Logical type: STRING
	 */
	public void setFormattedPermissionname( String pPermissionname )
		throws java.text.ParseException
	{
		setPermissionname( null );
		memorizeOriginalValue( FIELD_PERMISSIONNAME, pPermissionname );
		setPermissionname( getPool().parseString( pPermissionname ) );
	}
	// END Method setFormattedPermissionname


	// START Method getFormattedHTMLPermissionname generated
	/**
	 * Maps DB-Column: permissionname
	 * Logical type: STRING
	 */
	public String getFormattedHTMLPermissionname() {
		return StringTool.escapeHTML( getFormattedPermissionname() );
	}
	// END Method getFormattedHTMLPermissionname


	// START Method getToStringPermissionname generated
	/**
	 * Maps DB-Column: permissionname
	 * Logical type: STRING
	 */
	public String getPermissionnameToString() {
		return StringTool.nullToEmpty(getPermissionname());
	}
	// END Method getToStringPermissionname


	// START Method setFromStringPermissionname generated
	/**
	 * Maps DB-Column: permissionname
	 * Logical type: STRING
	 */
	public void setPermissionnameFromString( String pPermissionname ) {
		setPermissionname( StringTool.emptyToNull( pPermissionname ) );
	}
	// END Method setFromStringPermissionname


	// START Method getColumnMetaDataPermissionname generated
	/**
	 * Maps DB-Column: permissionname
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForPermissionname() {
		return getBasicDCColumnMetaData( FIELD_PERMISSIONNAME );
	}
	// END Method getColumnMetaDataPermissionname


	// START Method getPermissiondescription generated
	/**
	 * Maps DB-Column: permissiondescription
	 * Logical type: STRING
	 */
	public String getPermissiondescription() {
		return cPermissiondescription;
	}
	// END Method getPermissiondescription


	// START Method setPermissiondescription generated
	/**
	 * Maps DB-Column: permissiondescription
	 * Logical type: STRING
	 */
	public void setPermissiondescription( String pPermissiondescription ) {
		cPermissiondescription = StringTool.trimmedEmptyToNull( pPermissiondescription );
		forgetOriginalValue( FIELD_PERMISSIONDESCRIPTION );
		touchField( FIELD_PERMISSIONDESCRIPTION );
	}
	// END Method setPermissiondescription


	// START Method getFormattedPermissiondescription generated
	/**
	 * Maps DB-Column: permissiondescription
	 * Logical type: STRING
	 */
	public String getFormattedPermissiondescription() {
		return hasInvalidValue( FIELD_PERMISSIONDESCRIPTION ) ? getOriginalValue( FIELD_PERMISSIONDESCRIPTION ) : getPool().formatString( cPermissiondescription );
	}
	// END Method getFormattedPermissiondescription


	// START Method setFormattedPermissiondescription generated
	/**
	 * Maps DB-Column: permissiondescription
	 * Logical type: STRING
	 */
	public void setFormattedPermissiondescription( String pPermissiondescription )
		throws java.text.ParseException
	{
		setPermissiondescription( null );
		memorizeOriginalValue( FIELD_PERMISSIONDESCRIPTION, pPermissiondescription );
		setPermissiondescription( getPool().parseString( pPermissiondescription ) );
	}
	// END Method setFormattedPermissiondescription


	// START Method getFormattedHTMLPermissiondescription generated
	/**
	 * Maps DB-Column: permissiondescription
	 * Logical type: STRING
	 */
	public String getFormattedHTMLPermissiondescription() {
		return StringTool.escapeHTML( getFormattedPermissiondescription() );
	}
	// END Method getFormattedHTMLPermissiondescription


	// START Method getToStringPermissiondescription generated
	/**
	 * Maps DB-Column: permissiondescription
	 * Logical type: STRING
	 */
	public String getPermissiondescriptionToString() {
		return StringTool.nullToEmpty(getPermissiondescription());
	}
	// END Method getToStringPermissiondescription


	// START Method setFromStringPermissiondescription generated
	/**
	 * Maps DB-Column: permissiondescription
	 * Logical type: STRING
	 */
	public void setPermissiondescriptionFromString( String pPermissiondescription ) {
		setPermissiondescription( StringTool.emptyToNull( pPermissiondescription ) );
	}
	// END Method setFromStringPermissiondescription


	// START Method getColumnMetaDataPermissiondescription generated
	/**
	 * Maps DB-Column: permissiondescription
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForPermissiondescription() {
		return getBasicDCColumnMetaData( FIELD_PERMISSIONDESCRIPTION );
	}
	// END Method getColumnMetaDataPermissiondescription


	// START Method getDoc41Carr generated
	/**
	 * Maps DB-Column: doc41_Carr
	 * Logical type: BOOLEAN
	 */
	public Boolean getDoc41Carr() {
		return cDoc41Carr;
	}
	// END Method getDoc41Carr


	// START Method setDoc41Carr generated
	/**
	 * Maps DB-Column: doc41_Carr
	 * Logical type: BOOLEAN
	 */
	public void setDoc41Carr( Boolean pDoc41Carr ) {
		cDoc41Carr = pDoc41Carr;
		forgetOriginalValue( FIELD_DOC41CARR );
		touchField( FIELD_DOC41CARR );
	}
	// END Method setDoc41Carr


	// START Method getFormattedDoc41Carr generated
	/**
	 * Maps DB-Column: doc41_Carr
	 * Logical type: BOOLEAN
	 */
	public String getFormattedDoc41Carr() {
		return hasInvalidValue( FIELD_DOC41CARR ) ? getOriginalValue( FIELD_DOC41CARR ) : getPool().formatBoolean( cDoc41Carr );
	}
	// END Method getFormattedDoc41Carr


	// START Method setFormattedDoc41Carr generated
	/**
	 * Maps DB-Column: doc41_Carr
	 * Logical type: BOOLEAN
	 */
	public void setFormattedDoc41Carr( String pDoc41Carr )
		throws java.text.ParseException
	{
		setDoc41Carr( null );
		memorizeOriginalValue( FIELD_DOC41CARR, pDoc41Carr );
		setDoc41Carr( getPool().parseBoolean( pDoc41Carr ) );
	}
	// END Method setFormattedDoc41Carr


	// START Method getFormattedHTMLDoc41Carr generated
	/**
	 * Maps DB-Column: doc41_Carr
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLDoc41Carr() {
		return StringTool.escapeHTML( getFormattedDoc41Carr() );
	}
	// END Method getFormattedHTMLDoc41Carr


	// START Method getToStringDoc41Carr generated
	/**
	 * Maps DB-Column: doc41_Carr
	 * Logical type: BOOLEAN
	 */
	public String getDoc41CarrToString() {
		return StringTool.nullToEmpty(getDoc41Carr());
	}
	// END Method getToStringDoc41Carr


	// START Method setFromStringDoc41Carr generated
	/**
	 * Maps DB-Column: doc41_Carr
	 * Logical type: BOOLEAN
	 */
	public void setDoc41CarrFromString( String pDoc41Carr ) {
		setDoc41Carr( BooleanTool.parseBoolean( pDoc41Carr, null ) );
	}
	// END Method setFromStringDoc41Carr


	// START Method getColumnMetaDataDoc41Carr generated
	/**
	 * Maps DB-Column: doc41_Carr
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForDoc41Carr() {
		return getBasicDCColumnMetaData( FIELD_DOC41CARR );
	}
	// END Method getColumnMetaDataDoc41Carr


	// START Method getDoc41Cusbr generated
	/**
	 * Maps DB-Column: doc41_Cusbr
	 * Logical type: BOOLEAN
	 */
	public Boolean getDoc41Cusbr() {
		return cDoc41Cusbr;
	}
	// END Method getDoc41Cusbr


	// START Method setDoc41Cusbr generated
	/**
	 * Maps DB-Column: doc41_Cusbr
	 * Logical type: BOOLEAN
	 */
	public void setDoc41Cusbr( Boolean pDoc41Cusbr ) {
		cDoc41Cusbr = pDoc41Cusbr;
		forgetOriginalValue( FIELD_DOC41CUSBR );
		touchField( FIELD_DOC41CUSBR );
	}
	// END Method setDoc41Cusbr


	// START Method getFormattedDoc41Cusbr generated
	/**
	 * Maps DB-Column: doc41_Cusbr
	 * Logical type: BOOLEAN
	 */
	public String getFormattedDoc41Cusbr() {
		return hasInvalidValue( FIELD_DOC41CUSBR ) ? getOriginalValue( FIELD_DOC41CUSBR ) : getPool().formatBoolean( cDoc41Cusbr );
	}
	// END Method getFormattedDoc41Cusbr


	// START Method setFormattedDoc41Cusbr generated
	/**
	 * Maps DB-Column: doc41_Cusbr
	 * Logical type: BOOLEAN
	 */
	public void setFormattedDoc41Cusbr( String pDoc41Cusbr )
		throws java.text.ParseException
	{
		setDoc41Cusbr( null );
		memorizeOriginalValue( FIELD_DOC41CUSBR, pDoc41Cusbr );
		setDoc41Cusbr( getPool().parseBoolean( pDoc41Cusbr ) );
	}
	// END Method setFormattedDoc41Cusbr


	// START Method getFormattedHTMLDoc41Cusbr generated
	/**
	 * Maps DB-Column: doc41_Cusbr
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLDoc41Cusbr() {
		return StringTool.escapeHTML( getFormattedDoc41Cusbr() );
	}
	// END Method getFormattedHTMLDoc41Cusbr


	// START Method getToStringDoc41Cusbr generated
	/**
	 * Maps DB-Column: doc41_Cusbr
	 * Logical type: BOOLEAN
	 */
	public String getDoc41CusbrToString() {
		return StringTool.nullToEmpty(getDoc41Cusbr());
	}
	// END Method getToStringDoc41Cusbr


	// START Method setFromStringDoc41Cusbr generated
	/**
	 * Maps DB-Column: doc41_Cusbr
	 * Logical type: BOOLEAN
	 */
	public void setDoc41CusbrFromString( String pDoc41Cusbr ) {
		setDoc41Cusbr( BooleanTool.parseBoolean( pDoc41Cusbr, null ) );
	}
	// END Method setFromStringDoc41Cusbr


	// START Method getColumnMetaDataDoc41Cusbr generated
	/**
	 * Maps DB-Column: doc41_Cusbr
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForDoc41Cusbr() {
		return getBasicDCColumnMetaData( FIELD_DOC41CUSBR );
	}
	// END Method getColumnMetaDataDoc41Cusbr


	// START Method getDoc41Laysup generated
	/**
	 * Maps DB-Column: doc41_Laysup
	 * Logical type: BOOLEAN
	 */
	public Boolean getDoc41Laysup() {
		return cDoc41Laysup;
	}
	// END Method getDoc41Laysup


	// START Method setDoc41Laysup generated
	/**
	 * Maps DB-Column: doc41_Laysup
	 * Logical type: BOOLEAN
	 */
	public void setDoc41Laysup( Boolean pDoc41Laysup ) {
		cDoc41Laysup = pDoc41Laysup;
		forgetOriginalValue( FIELD_DOC41LAYSUP );
		touchField( FIELD_DOC41LAYSUP );
	}
	// END Method setDoc41Laysup


	// START Method getFormattedDoc41Laysup generated
	/**
	 * Maps DB-Column: doc41_Laysup
	 * Logical type: BOOLEAN
	 */
	public String getFormattedDoc41Laysup() {
		return hasInvalidValue( FIELD_DOC41LAYSUP ) ? getOriginalValue( FIELD_DOC41LAYSUP ) : getPool().formatBoolean( cDoc41Laysup );
	}
	// END Method getFormattedDoc41Laysup


	// START Method setFormattedDoc41Laysup generated
	/**
	 * Maps DB-Column: doc41_Laysup
	 * Logical type: BOOLEAN
	 */
	public void setFormattedDoc41Laysup( String pDoc41Laysup )
		throws java.text.ParseException
	{
		setDoc41Laysup( null );
		memorizeOriginalValue( FIELD_DOC41LAYSUP, pDoc41Laysup );
		setDoc41Laysup( getPool().parseBoolean( pDoc41Laysup ) );
	}
	// END Method setFormattedDoc41Laysup


	// START Method getFormattedHTMLDoc41Laysup generated
	/**
	 * Maps DB-Column: doc41_Laysup
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLDoc41Laysup() {
		return StringTool.escapeHTML( getFormattedDoc41Laysup() );
	}
	// END Method getFormattedHTMLDoc41Laysup


	// START Method getToStringDoc41Laysup generated
	/**
	 * Maps DB-Column: doc41_Laysup
	 * Logical type: BOOLEAN
	 */
	public String getDoc41LaysupToString() {
		return StringTool.nullToEmpty(getDoc41Laysup());
	}
	// END Method getToStringDoc41Laysup


	// START Method setFromStringDoc41Laysup generated
	/**
	 * Maps DB-Column: doc41_Laysup
	 * Logical type: BOOLEAN
	 */
	public void setDoc41LaysupFromString( String pDoc41Laysup ) {
		setDoc41Laysup( BooleanTool.parseBoolean( pDoc41Laysup, null ) );
	}
	// END Method setFromStringDoc41Laysup


	// START Method getColumnMetaDataDoc41Laysup generated
	/**
	 * Maps DB-Column: doc41_Laysup
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForDoc41Laysup() {
		return getBasicDCColumnMetaData( FIELD_DOC41LAYSUP );
	}
	// END Method getColumnMetaDataDoc41Laysup


	// START Method getDoc41Pmsup generated
	/**
	 * Maps DB-Column: doc41_Pmsup
	 * Logical type: BOOLEAN
	 */
	public Boolean getDoc41Pmsup() {
		return cDoc41Pmsup;
	}
	// END Method getDoc41Pmsup


	// START Method setDoc41Pmsup generated
	/**
	 * Maps DB-Column: doc41_Pmsup
	 * Logical type: BOOLEAN
	 */
	public void setDoc41Pmsup( Boolean pDoc41Pmsup ) {
		cDoc41Pmsup = pDoc41Pmsup;
		forgetOriginalValue( FIELD_DOC41PMSUP );
		touchField( FIELD_DOC41PMSUP );
	}
	// END Method setDoc41Pmsup


	// START Method getFormattedDoc41Pmsup generated
	/**
	 * Maps DB-Column: doc41_Pmsup
	 * Logical type: BOOLEAN
	 */
	public String getFormattedDoc41Pmsup() {
		return hasInvalidValue( FIELD_DOC41PMSUP ) ? getOriginalValue( FIELD_DOC41PMSUP ) : getPool().formatBoolean( cDoc41Pmsup );
	}
	// END Method getFormattedDoc41Pmsup


	// START Method setFormattedDoc41Pmsup generated
	/**
	 * Maps DB-Column: doc41_Pmsup
	 * Logical type: BOOLEAN
	 */
	public void setFormattedDoc41Pmsup( String pDoc41Pmsup )
		throws java.text.ParseException
	{
		setDoc41Pmsup( null );
		memorizeOriginalValue( FIELD_DOC41PMSUP, pDoc41Pmsup );
		setDoc41Pmsup( getPool().parseBoolean( pDoc41Pmsup ) );
	}
	// END Method setFormattedDoc41Pmsup


	// START Method getFormattedHTMLDoc41Pmsup generated
	/**
	 * Maps DB-Column: doc41_Pmsup
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLDoc41Pmsup() {
		return StringTool.escapeHTML( getFormattedDoc41Pmsup() );
	}
	// END Method getFormattedHTMLDoc41Pmsup


	// START Method getToStringDoc41Pmsup generated
	/**
	 * Maps DB-Column: doc41_Pmsup
	 * Logical type: BOOLEAN
	 */
	public String getDoc41PmsupToString() {
		return StringTool.nullToEmpty(getDoc41Pmsup());
	}
	// END Method getToStringDoc41Pmsup


	// START Method setFromStringDoc41Pmsup generated
	/**
	 * Maps DB-Column: doc41_Pmsup
	 * Logical type: BOOLEAN
	 */
	public void setDoc41PmsupFromString( String pDoc41Pmsup ) {
		setDoc41Pmsup( BooleanTool.parseBoolean( pDoc41Pmsup, null ) );
	}
	// END Method setFromStringDoc41Pmsup


	// START Method getColumnMetaDataDoc41Pmsup generated
	/**
	 * Maps DB-Column: doc41_Pmsup
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForDoc41Pmsup() {
		return getBasicDCColumnMetaData( FIELD_DOC41PMSUP );
	}
	// END Method getColumnMetaDataDoc41Pmsup


	// START Method getDoc41Badm generated
	/**
	 * Maps DB-Column: doc41_Badm
	 * Logical type: BOOLEAN
	 */
	public Boolean getDoc41Badm() {
		return cDoc41Badm;
	}
	// END Method getDoc41Badm


	// START Method setDoc41Badm generated
	/**
	 * Maps DB-Column: doc41_Badm
	 * Logical type: BOOLEAN
	 */
	public void setDoc41Badm( Boolean pDoc41Badm ) {
		cDoc41Badm = pDoc41Badm;
		forgetOriginalValue( FIELD_DOC41BADM );
		touchField( FIELD_DOC41BADM );
	}
	// END Method setDoc41Badm


	// START Method getFormattedDoc41Badm generated
	/**
	 * Maps DB-Column: doc41_Badm
	 * Logical type: BOOLEAN
	 */
	public String getFormattedDoc41Badm() {
		return hasInvalidValue( FIELD_DOC41BADM ) ? getOriginalValue( FIELD_DOC41BADM ) : getPool().formatBoolean( cDoc41Badm );
	}
	// END Method getFormattedDoc41Badm


	// START Method setFormattedDoc41Badm generated
	/**
	 * Maps DB-Column: doc41_Badm
	 * Logical type: BOOLEAN
	 */
	public void setFormattedDoc41Badm( String pDoc41Badm )
		throws java.text.ParseException
	{
		setDoc41Badm( null );
		memorizeOriginalValue( FIELD_DOC41BADM, pDoc41Badm );
		setDoc41Badm( getPool().parseBoolean( pDoc41Badm ) );
	}
	// END Method setFormattedDoc41Badm


	// START Method getFormattedHTMLDoc41Badm generated
	/**
	 * Maps DB-Column: doc41_Badm
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLDoc41Badm() {
		return StringTool.escapeHTML( getFormattedDoc41Badm() );
	}
	// END Method getFormattedHTMLDoc41Badm


	// START Method getToStringDoc41Badm generated
	/**
	 * Maps DB-Column: doc41_Badm
	 * Logical type: BOOLEAN
	 */
	public String getDoc41BadmToString() {
		return StringTool.nullToEmpty(getDoc41Badm());
	}
	// END Method getToStringDoc41Badm


	// START Method setFromStringDoc41Badm generated
	/**
	 * Maps DB-Column: doc41_Badm
	 * Logical type: BOOLEAN
	 */
	public void setDoc41BadmFromString( String pDoc41Badm ) {
		setDoc41Badm( BooleanTool.parseBoolean( pDoc41Badm, null ) );
	}
	// END Method setFromStringDoc41Badm


	// START Method getColumnMetaDataDoc41Badm generated
	/**
	 * Maps DB-Column: doc41_Badm
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForDoc41Badm() {
		return getBasicDCColumnMetaData( FIELD_DOC41BADM );
	}
	// END Method getColumnMetaDataDoc41Badm


	// START Method getDoc41Tadm generated
	/**
	 * Maps DB-Column: doc41_Tadm
	 * Logical type: BOOLEAN
	 */
	public Boolean getDoc41Tadm() {
		return cDoc41Tadm;
	}
	// END Method getDoc41Tadm


	// START Method setDoc41Tadm generated
	/**
	 * Maps DB-Column: doc41_Tadm
	 * Logical type: BOOLEAN
	 */
	public void setDoc41Tadm( Boolean pDoc41Tadm ) {
		cDoc41Tadm = pDoc41Tadm;
		forgetOriginalValue( FIELD_DOC41TADM );
		touchField( FIELD_DOC41TADM );
	}
	// END Method setDoc41Tadm


	// START Method getFormattedDoc41Tadm generated
	/**
	 * Maps DB-Column: doc41_Tadm
	 * Logical type: BOOLEAN
	 */
	public String getFormattedDoc41Tadm() {
		return hasInvalidValue( FIELD_DOC41TADM ) ? getOriginalValue( FIELD_DOC41TADM ) : getPool().formatBoolean( cDoc41Tadm );
	}
	// END Method getFormattedDoc41Tadm


	// START Method setFormattedDoc41Tadm generated
	/**
	 * Maps DB-Column: doc41_Tadm
	 * Logical type: BOOLEAN
	 */
	public void setFormattedDoc41Tadm( String pDoc41Tadm )
		throws java.text.ParseException
	{
		setDoc41Tadm( null );
		memorizeOriginalValue( FIELD_DOC41TADM, pDoc41Tadm );
		setDoc41Tadm( getPool().parseBoolean( pDoc41Tadm ) );
	}
	// END Method setFormattedDoc41Tadm


	// START Method getFormattedHTMLDoc41Tadm generated
	/**
	 * Maps DB-Column: doc41_Tadm
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLDoc41Tadm() {
		return StringTool.escapeHTML( getFormattedDoc41Tadm() );
	}
	// END Method getFormattedHTMLDoc41Tadm


	// START Method getToStringDoc41Tadm generated
	/**
	 * Maps DB-Column: doc41_Tadm
	 * Logical type: BOOLEAN
	 */
	public String getDoc41TadmToString() {
		return StringTool.nullToEmpty(getDoc41Tadm());
	}
	// END Method getToStringDoc41Tadm


	// START Method setFromStringDoc41Tadm generated
	/**
	 * Maps DB-Column: doc41_Tadm
	 * Logical type: BOOLEAN
	 */
	public void setDoc41TadmFromString( String pDoc41Tadm ) {
		setDoc41Tadm( BooleanTool.parseBoolean( pDoc41Tadm, null ) );
	}
	// END Method setFromStringDoc41Tadm


	// START Method getColumnMetaDataDoc41Tadm generated
	/**
	 * Maps DB-Column: doc41_Tadm
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForDoc41Tadm() {
		return getBasicDCColumnMetaData( FIELD_DOC41TADM );
	}
	// END Method getColumnMetaDataDoc41Tadm


	// START Method getDoc41Obsv generated
	/**
	 * Maps DB-Column: doc41_Obsv
	 * Logical type: BOOLEAN
	 */
	public Boolean getDoc41Obsv() {
		return cDoc41Obsv;
	}
	// END Method getDoc41Obsv


	// START Method setDoc41Obsv generated
	/**
	 * Maps DB-Column: doc41_Obsv
	 * Logical type: BOOLEAN
	 */
	public void setDoc41Obsv( Boolean pDoc41Obsv ) {
		cDoc41Obsv = pDoc41Obsv;
		forgetOriginalValue( FIELD_DOC41OBSV );
		touchField( FIELD_DOC41OBSV );
	}
	// END Method setDoc41Obsv


	// START Method getFormattedDoc41Obsv generated
	/**
	 * Maps DB-Column: doc41_Obsv
	 * Logical type: BOOLEAN
	 */
	public String getFormattedDoc41Obsv() {
		return hasInvalidValue( FIELD_DOC41OBSV ) ? getOriginalValue( FIELD_DOC41OBSV ) : getPool().formatBoolean( cDoc41Obsv );
	}
	// END Method getFormattedDoc41Obsv


	// START Method setFormattedDoc41Obsv generated
	/**
	 * Maps DB-Column: doc41_Obsv
	 * Logical type: BOOLEAN
	 */
	public void setFormattedDoc41Obsv( String pDoc41Obsv )
		throws java.text.ParseException
	{
		setDoc41Obsv( null );
		memorizeOriginalValue( FIELD_DOC41OBSV, pDoc41Obsv );
		setDoc41Obsv( getPool().parseBoolean( pDoc41Obsv ) );
	}
	// END Method setFormattedDoc41Obsv


	// START Method getFormattedHTMLDoc41Obsv generated
	/**
	 * Maps DB-Column: doc41_Obsv
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLDoc41Obsv() {
		return StringTool.escapeHTML( getFormattedDoc41Obsv() );
	}
	// END Method getFormattedHTMLDoc41Obsv


	// START Method getToStringDoc41Obsv generated
	/**
	 * Maps DB-Column: doc41_Obsv
	 * Logical type: BOOLEAN
	 */
	public String getDoc41ObsvToString() {
		return StringTool.nullToEmpty(getDoc41Obsv());
	}
	// END Method getToStringDoc41Obsv


	// START Method setFromStringDoc41Obsv generated
	/**
	 * Maps DB-Column: doc41_Obsv
	 * Logical type: BOOLEAN
	 */
	public void setDoc41ObsvFromString( String pDoc41Obsv ) {
		setDoc41Obsv( BooleanTool.parseBoolean( pDoc41Obsv, null ) );
	}
	// END Method setFromStringDoc41Obsv


	// START Method getColumnMetaDataDoc41Obsv generated
	/**
	 * Maps DB-Column: doc41_Obsv
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForDoc41Obsv() {
		return getBasicDCColumnMetaData( FIELD_DOC41OBSV );
	}
	// END Method getColumnMetaDataDoc41Obsv


	// START Method getDoc41Matsup generated
	/**
	 * Maps DB-Column: doc41_matsup
	 * Logical type: BOOLEAN
	 */
	public Boolean getDoc41Matsup() {
		return cDoc41Matsup;
	}
	// END Method getDoc41Matsup


	// START Method setDoc41Matsup generated
	/**
	 * Maps DB-Column: doc41_matsup
	 * Logical type: BOOLEAN
	 */
	public void setDoc41Matsup( Boolean pDoc41Matsup ) {
		cDoc41Matsup = pDoc41Matsup;
		forgetOriginalValue( FIELD_DOC41MATSUP );
		touchField( FIELD_DOC41MATSUP );
	}
	// END Method setDoc41Matsup


	// START Method getFormattedDoc41Matsup generated
	/**
	 * Maps DB-Column: doc41_matsup
	 * Logical type: BOOLEAN
	 */
	public String getFormattedDoc41Matsup() {
		return hasInvalidValue( FIELD_DOC41MATSUP ) ? getOriginalValue( FIELD_DOC41MATSUP ) : getPool().formatBoolean( cDoc41Matsup );
	}
	// END Method getFormattedDoc41Matsup


	// START Method setFormattedDoc41Matsup generated
	/**
	 * Maps DB-Column: doc41_matsup
	 * Logical type: BOOLEAN
	 */
	public void setFormattedDoc41Matsup( String pDoc41Matsup )
		throws java.text.ParseException
	{
		setDoc41Matsup( null );
		memorizeOriginalValue( FIELD_DOC41MATSUP, pDoc41Matsup );
		setDoc41Matsup( getPool().parseBoolean( pDoc41Matsup ) );
	}
	// END Method setFormattedDoc41Matsup


	// START Method getFormattedHTMLDoc41Matsup generated
	/**
	 * Maps DB-Column: doc41_matsup
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLDoc41Matsup() {
		return StringTool.escapeHTML( getFormattedDoc41Matsup() );
	}
	// END Method getFormattedHTMLDoc41Matsup


	// START Method getToStringDoc41Matsup generated
	/**
	 * Maps DB-Column: doc41_matsup
	 * Logical type: BOOLEAN
	 */
	public String getDoc41MatsupToString() {
		return StringTool.nullToEmpty(getDoc41Matsup());
	}
	// END Method getToStringDoc41Matsup


	// START Method setFromStringDoc41Matsup generated
	/**
	 * Maps DB-Column: doc41_matsup
	 * Logical type: BOOLEAN
	 */
	public void setDoc41MatsupFromString( String pDoc41Matsup ) {
		setDoc41Matsup( BooleanTool.parseBoolean( pDoc41Matsup, null ) );
	}
	// END Method setFromStringDoc41Matsup


	// START Method getColumnMetaDataDoc41Matsup generated
	/**
	 * Maps DB-Column: doc41_matsup
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForDoc41Matsup() {
		return getBasicDCColumnMetaData( FIELD_DOC41MATSUP );
	}
	// END Method getColumnMetaDataDoc41Matsup


	// START Method getDoc41Prodsup generated
	/**
	 * Maps DB-Column: doc41_prodsup
	 * Logical type: BOOLEAN
	 */
	public Boolean getDoc41Prodsup() {
		return cDoc41Prodsup;
	}
	// END Method getDoc41Prodsup


	// START Method setDoc41Prodsup generated
	/**
	 * Maps DB-Column: doc41_prodsup
	 * Logical type: BOOLEAN
	 */
	public void setDoc41Prodsup( Boolean pDoc41Prodsup ) {
		cDoc41Prodsup = pDoc41Prodsup;
		forgetOriginalValue( FIELD_DOC41PRODSUP );
		touchField( FIELD_DOC41PRODSUP );
	}
	// END Method setDoc41Prodsup


	// START Method getFormattedDoc41Prodsup generated
	/**
	 * Maps DB-Column: doc41_prodsup
	 * Logical type: BOOLEAN
	 */
	public String getFormattedDoc41Prodsup() {
		return hasInvalidValue( FIELD_DOC41PRODSUP ) ? getOriginalValue( FIELD_DOC41PRODSUP ) : getPool().formatBoolean( cDoc41Prodsup );
	}
	// END Method getFormattedDoc41Prodsup


	// START Method setFormattedDoc41Prodsup generated
	/**
	 * Maps DB-Column: doc41_prodsup
	 * Logical type: BOOLEAN
	 */
	public void setFormattedDoc41Prodsup( String pDoc41Prodsup )
		throws java.text.ParseException
	{
		setDoc41Prodsup( null );
		memorizeOriginalValue( FIELD_DOC41PRODSUP, pDoc41Prodsup );
		setDoc41Prodsup( getPool().parseBoolean( pDoc41Prodsup ) );
	}
	// END Method setFormattedDoc41Prodsup


	// START Method getFormattedHTMLDoc41Prodsup generated
	/**
	 * Maps DB-Column: doc41_prodsup
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLDoc41Prodsup() {
		return StringTool.escapeHTML( getFormattedDoc41Prodsup() );
	}
	// END Method getFormattedHTMLDoc41Prodsup


	// START Method getToStringDoc41Prodsup generated
	/**
	 * Maps DB-Column: doc41_prodsup
	 * Logical type: BOOLEAN
	 */
	public String getDoc41ProdsupToString() {
		return StringTool.nullToEmpty(getDoc41Prodsup());
	}
	// END Method getToStringDoc41Prodsup


	// START Method setFromStringDoc41Prodsup generated
	/**
	 * Maps DB-Column: doc41_prodsup
	 * Logical type: BOOLEAN
	 */
	public void setDoc41ProdsupFromString( String pDoc41Prodsup ) {
		setDoc41Prodsup( BooleanTool.parseBoolean( pDoc41Prodsup, null ) );
	}
	// END Method setFromStringDoc41Prodsup


	// START Method getColumnMetaDataDoc41Prodsup generated
	/**
	 * Maps DB-Column: doc41_prodsup
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForDoc41Prodsup() {
		return getBasicDCColumnMetaData( FIELD_DOC41PRODSUP );
	}
	// END Method getColumnMetaDataDoc41Prodsup


	// START Method getDoc41Delcertvcountry generated
	/**
	 * Maps DB-Column: doc41_delcertvcountry
	 * Logical type: BOOLEAN
	 */
	public Boolean getDoc41Delcertvcountry() {
		return cDoc41Delcertvcountry;
	}
	// END Method getDoc41Delcertvcountry


	// START Method setDoc41Delcertvcountry generated
	/**
	 * Maps DB-Column: doc41_delcertvcountry
	 * Logical type: BOOLEAN
	 */
	public void setDoc41Delcertvcountry( Boolean pDoc41Delcertvcountry ) {
		cDoc41Delcertvcountry = pDoc41Delcertvcountry;
		forgetOriginalValue( FIELD_DOC41DELCERTVCOUNTRY );
		touchField( FIELD_DOC41DELCERTVCOUNTRY );
	}
	// END Method setDoc41Delcertvcountry


	// START Method getFormattedDoc41Delcertvcountry generated
	/**
	 * Maps DB-Column: doc41_delcertvcountry
	 * Logical type: BOOLEAN
	 */
	public String getFormattedDoc41Delcertvcountry() {
		return hasInvalidValue( FIELD_DOC41DELCERTVCOUNTRY ) ? getOriginalValue( FIELD_DOC41DELCERTVCOUNTRY ) : getPool().formatBoolean( cDoc41Delcertvcountry );
	}
	// END Method getFormattedDoc41Delcertvcountry


	// START Method setFormattedDoc41Delcertvcountry generated
	/**
	 * Maps DB-Column: doc41_delcertvcountry
	 * Logical type: BOOLEAN
	 */
	public void setFormattedDoc41Delcertvcountry( String pDoc41Delcertvcountry )
		throws java.text.ParseException
	{
		setDoc41Delcertvcountry( null );
		memorizeOriginalValue( FIELD_DOC41DELCERTVCOUNTRY, pDoc41Delcertvcountry );
		setDoc41Delcertvcountry( getPool().parseBoolean( pDoc41Delcertvcountry ) );
	}
	// END Method setFormattedDoc41Delcertvcountry


	// START Method getFormattedHTMLDoc41Delcertvcountry generated
	/**
	 * Maps DB-Column: doc41_delcertvcountry
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLDoc41Delcertvcountry() {
		return StringTool.escapeHTML( getFormattedDoc41Delcertvcountry() );
	}
	// END Method getFormattedHTMLDoc41Delcertvcountry


	// START Method getToStringDoc41Delcertvcountry generated
	/**
	 * Maps DB-Column: doc41_delcertvcountry
	 * Logical type: BOOLEAN
	 */
	public String getDoc41DelcertvcountryToString() {
		return StringTool.nullToEmpty(getDoc41Delcertvcountry());
	}
	// END Method getToStringDoc41Delcertvcountry


	// START Method setFromStringDoc41Delcertvcountry generated
	/**
	 * Maps DB-Column: doc41_delcertvcountry
	 * Logical type: BOOLEAN
	 */
	public void setDoc41DelcertvcountryFromString( String pDoc41Delcertvcountry ) {
		setDoc41Delcertvcountry( BooleanTool.parseBoolean( pDoc41Delcertvcountry, null ) );
	}
	// END Method setFromStringDoc41Delcertvcountry


	// START Method getColumnMetaDataDoc41Delcertvcountry generated
	/**
	 * Maps DB-Column: doc41_delcertvcountry
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForDoc41Delcertvcountry() {
		return getBasicDCColumnMetaData( FIELD_DOC41DELCERTVCOUNTRY );
	}
	// END Method getColumnMetaDataDoc41Delcertvcountry


	// START Method getDoc41Delcertvcust generated
	/**
	 * Maps DB-Column: doc41_delcertvcust
	 * Logical type: BOOLEAN
	 */
	public Boolean getDoc41Delcertvcust() {
		return cDoc41Delcertvcust;
	}
	// END Method getDoc41Delcertvcust


	// START Method setDoc41Delcertvcust generated
	/**
	 * Maps DB-Column: doc41_delcertvcust
	 * Logical type: BOOLEAN
	 */
	public void setDoc41Delcertvcust( Boolean pDoc41Delcertvcust ) {
		cDoc41Delcertvcust = pDoc41Delcertvcust;
		forgetOriginalValue( FIELD_DOC41DELCERTVCUST );
		touchField( FIELD_DOC41DELCERTVCUST );
	}
	// END Method setDoc41Delcertvcust


	// START Method getFormattedDoc41Delcertvcust generated
	/**
	 * Maps DB-Column: doc41_delcertvcust
	 * Logical type: BOOLEAN
	 */
	public String getFormattedDoc41Delcertvcust() {
		return hasInvalidValue( FIELD_DOC41DELCERTVCUST ) ? getOriginalValue( FIELD_DOC41DELCERTVCUST ) : getPool().formatBoolean( cDoc41Delcertvcust );
	}
	// END Method getFormattedDoc41Delcertvcust


	// START Method setFormattedDoc41Delcertvcust generated
	/**
	 * Maps DB-Column: doc41_delcertvcust
	 * Logical type: BOOLEAN
	 */
	public void setFormattedDoc41Delcertvcust( String pDoc41Delcertvcust )
		throws java.text.ParseException
	{
		setDoc41Delcertvcust( null );
		memorizeOriginalValue( FIELD_DOC41DELCERTVCUST, pDoc41Delcertvcust );
		setDoc41Delcertvcust( getPool().parseBoolean( pDoc41Delcertvcust ) );
	}
	// END Method setFormattedDoc41Delcertvcust


	// START Method getFormattedHTMLDoc41Delcertvcust generated
	/**
	 * Maps DB-Column: doc41_delcertvcust
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLDoc41Delcertvcust() {
		return StringTool.escapeHTML( getFormattedDoc41Delcertvcust() );
	}
	// END Method getFormattedHTMLDoc41Delcertvcust


	// START Method getToStringDoc41Delcertvcust generated
	/**
	 * Maps DB-Column: doc41_delcertvcust
	 * Logical type: BOOLEAN
	 */
	public String getDoc41DelcertvcustToString() {
		return StringTool.nullToEmpty(getDoc41Delcertvcust());
	}
	// END Method getToStringDoc41Delcertvcust


	// START Method setFromStringDoc41Delcertvcust generated
	/**
	 * Maps DB-Column: doc41_delcertvcust
	 * Logical type: BOOLEAN
	 */
	public void setDoc41DelcertvcustFromString( String pDoc41Delcertvcust ) {
		setDoc41Delcertvcust( BooleanTool.parseBoolean( pDoc41Delcertvcust, null ) );
	}
	// END Method setFromStringDoc41Delcertvcust


	// START Method getColumnMetaDataDoc41Delcertvcust generated
	/**
	 * Maps DB-Column: doc41_delcertvcust
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForDoc41Delcertvcust() {
		return getBasicDCColumnMetaData( FIELD_DOC41DELCERTVCUST );
	}
	// END Method getColumnMetaDataDoc41Delcertvcust


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
