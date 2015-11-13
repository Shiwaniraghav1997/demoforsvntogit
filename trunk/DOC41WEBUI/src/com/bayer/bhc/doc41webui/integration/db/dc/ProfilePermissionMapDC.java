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
 * Store an Atom of Profile-Permission-Assignment for Buiding a Map per Permission.
 */
public class ProfilePermissionMapDC
	extends com.bayer.ecim.foundation.dbx.DataCarrier
{

	/** Maps DB-Column: permissionname */
	String cPermissionname;
	public static final String FIELD_PERMISSIONNAME = "Permissionname";

	/** Maps DB-Column: permissiondescription */
	String cPermissiondescription;
	public static final String FIELD_PERMISSIONDESCRIPTION = "Permissiondescription";

	/** Maps DB-Column: profilename */
	String cProfilename;
	public static final String FIELD_PROFILENAME = "Profilename";

	/** Maps DB-Column: code */
	String cCode;
	public static final String FIELD_CODE = "Code";

	/** for compatibility, suppress import warning...*/
	public static final Class<DataCarrier> _DBX_DC_CLASS_PROFILEPERMISSIONMAPDC = DataCarrier.class; // dummy variable to avoid warning

	/** The master Class of this class. */
	public final Class<BasicDataCarrier> MASTER_CLASS_PROFILEPERMISSIONMAPDC = BasicDataCarrier.class;

	/** Set the database table-name (null if unspecified) for this DC. */
	static {
		setDBTablename( ProfilePermissionMapDC.class, null );
	}

	private static final long serialVersionUID = 20151113025101269L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_PROFILEPERMISSIONMAPDC = java.math.BigDecimal.class;

	private static final HashMap<String,BasicDCFieldMeta> FIELD_META = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.dbx.DataCarrier.localGetFieldMetaMap() );
	private static final String[] LOCAL_FIELD_LIST = new String[] {FIELD_PERMISSIONNAME, FIELD_PERMISSIONDESCRIPTION, FIELD_PROFILENAME, FIELD_CODE};
	private static final String[] FIELD_LIST = StringTool.merge(  com.bayer.ecim.foundation.dbx.DataCarrier.localGetFieldList(), LOCAL_FIELD_LIST );

	static {
		try {
			FIELD_META.put( FIELD_PERMISSIONNAME,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_PERMISSIONDESCRIPTION,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_PROFILENAME,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_CODE,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of ProfilePermissionMapDC!", null );
		}
	}

	/**
	 * The Constructor.
	 * (a)deprecated Use: static ProfilePermissionMapDC newInstanceOfProfilePermissionMapDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public ProfilePermissionMapDC() {
		super( (Boolean)null );
	}

	/**
	 * Temporary Constructor.
	 */
	protected ProfilePermissionMapDC( Boolean pVal ) {
		super( pVal );
		pVal = null;
	}

	/**
	 * The Copy Constructor.
	 * (a)deprecated Use: static ProfilePermissionMapDC newInstanceOfProfilePermissionMapDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public ProfilePermissionMapDC( BasicDataCarrier pDC )
		throws InitException
	{
		super( (Boolean)null );
		copyFrom( pDC );
	}
	

	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * @throws InitException if the instantiation fails.
	 */
	public static ProfilePermissionMapDC newInstanceOfProfilePermissionMapDC() throws InitException {
		return (ProfilePermissionMapDC)newInstanceOf( ProfilePermissionMapDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 * @throws InitException if the instantiation fails.
	 */
	public static ProfilePermissionMapDC newInstanceOfProfilePermissionMapDC( Locale pLoc ) throws InitException {
		return (ProfilePermissionMapDC)localizeDC( newInstanceOfProfilePermissionMapDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pDC dc to copy the attributes from.
	 * @throws InitException if the instantiation fails.
	 */
	public static ProfilePermissionMapDC newInstanceOfProfilePermissionMapDC( BasicDataCarrier pDC ) throws InitException {
		return (ProfilePermissionMapDC)newInstanceOfProfilePermissionMapDC().copyFrom( pDC );
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
	public static final int CHECK_PROFILEPERMISSIONMAPDC = com.bayer.ecim.foundation.dbx.DataCarrier.CHECK_DATACARRIER;

	/**
	 * Copy the parameter DC pOther into this.
	 */
	@Override
	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) throws InitException {
		super.copyFrom(pOther);
		if (ProfilePermissionMapDC.class.isAssignableFrom(pOther.getClass())) {
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
			"ProfilePermissionMapDC",
			"com.bayer.ecim.foundation.dbx.DataCarrier",
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
		return ProfilePermissionMapDC.localGetCVSMeta();
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
		return ProfilePermissionMapDC.localGetFieldMeta( pFieldName );
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


	// START Method getProfilename generated
	/**
	 * Maps DB-Column: profilename
	 * Logical type: STRING
	 */
	public String getProfilename() {
		return cProfilename;
	}
	// END Method getProfilename


	// START Method setProfilename generated
	/**
	 * Maps DB-Column: profilename
	 * Logical type: STRING
	 */
	public void setProfilename( String pProfilename ) {
		cProfilename = StringTool.trimmedEmptyToNull( pProfilename );
		forgetOriginalValue( FIELD_PROFILENAME );
		touchField( FIELD_PROFILENAME );
	}
	// END Method setProfilename


	// START Method getFormattedProfilename generated
	/**
	 * Maps DB-Column: profilename
	 * Logical type: STRING
	 */
	public String getFormattedProfilename() {
		return hasInvalidValue( FIELD_PROFILENAME ) ? getOriginalValue( FIELD_PROFILENAME ) : getPool().formatString( cProfilename );
	}
	// END Method getFormattedProfilename


	// START Method setFormattedProfilename generated
	/**
	 * Maps DB-Column: profilename
	 * Logical type: STRING
	 */
	public void setFormattedProfilename( String pProfilename )
		throws java.text.ParseException
	{
		setProfilename( null );
		memorizeOriginalValue( FIELD_PROFILENAME, pProfilename );
		setProfilename( getPool().parseString( pProfilename ) );
	}
	// END Method setFormattedProfilename


	// START Method getFormattedHTMLProfilename generated
	/**
	 * Maps DB-Column: profilename
	 * Logical type: STRING
	 */
	public String getFormattedHTMLProfilename() {
		return StringTool.escapeHTML( getFormattedProfilename() );
	}
	// END Method getFormattedHTMLProfilename


	// START Method getToStringProfilename generated
	/**
	 * Maps DB-Column: profilename
	 * Logical type: STRING
	 */
	public String getProfilenameToString() {
		return StringTool.nullToEmpty(getProfilename());
	}
	// END Method getToStringProfilename


	// START Method setFromStringProfilename generated
	/**
	 * Maps DB-Column: profilename
	 * Logical type: STRING
	 */
	public void setProfilenameFromString( String pProfilename ) {
		setProfilename( StringTool.emptyToNull( pProfilename ) );
	}
	// END Method setFromStringProfilename


	// START Method getColumnMetaDataProfilename generated
	/**
	 * Maps DB-Column: profilename
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForProfilename() {
		return getBasicDCColumnMetaData( FIELD_PROFILENAME );
	}
	// END Method getColumnMetaDataProfilename


	// START Method getCode generated
	/**
	 * Maps DB-Column: code
	 * Logical type: STRING
	 */
	public String getCode() {
		return cCode;
	}
	// END Method getCode


	// START Method setCode generated
	/**
	 * Maps DB-Column: code
	 * Logical type: STRING
	 */
	public void setCode( String pCode ) {
		cCode = StringTool.trimmedEmptyToNull( pCode );
		forgetOriginalValue( FIELD_CODE );
		touchField( FIELD_CODE );
	}
	// END Method setCode


	// START Method getFormattedCode generated
	/**
	 * Maps DB-Column: code
	 * Logical type: STRING
	 */
	public String getFormattedCode() {
		return hasInvalidValue( FIELD_CODE ) ? getOriginalValue( FIELD_CODE ) : getPool().formatString( cCode );
	}
	// END Method getFormattedCode


	// START Method setFormattedCode generated
	/**
	 * Maps DB-Column: code
	 * Logical type: STRING
	 */
	public void setFormattedCode( String pCode )
		throws java.text.ParseException
	{
		setCode( null );
		memorizeOriginalValue( FIELD_CODE, pCode );
		setCode( getPool().parseString( pCode ) );
	}
	// END Method setFormattedCode


	// START Method getFormattedHTMLCode generated
	/**
	 * Maps DB-Column: code
	 * Logical type: STRING
	 */
	public String getFormattedHTMLCode() {
		return StringTool.escapeHTML( getFormattedCode() );
	}
	// END Method getFormattedHTMLCode


	// START Method getToStringCode generated
	/**
	 * Maps DB-Column: code
	 * Logical type: STRING
	 */
	public String getCodeToString() {
		return StringTool.nullToEmpty(getCode());
	}
	// END Method getToStringCode


	// START Method setFromStringCode generated
	/**
	 * Maps DB-Column: code
	 * Logical type: STRING
	 */
	public void setCodeFromString( String pCode ) {
		setCode( StringTool.emptyToNull( pCode ) );
	}
	// END Method setFromStringCode


	// START Method getColumnMetaDataCode generated
	/**
	 * Maps DB-Column: code
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForCode() {
		return getBasicDCColumnMetaData( FIELD_CODE );
	}
	// END Method getColumnMetaDataCode


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
