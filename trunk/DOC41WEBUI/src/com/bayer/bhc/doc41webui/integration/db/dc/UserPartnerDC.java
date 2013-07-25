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
public class UserPartnerDC
	extends com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier
{

	/** Maps DB-Column: user_Id */
	Long cUserId;
	public static final String FIELD_USERID = "UserId";

	/** Maps DB-Column: partner_Number */
	String cPartnerNumber;
	public static final String FIELD_PARTNERNUMBER = "PartnerNumber";

	/** Maps DB-Column: partner_Name1 */
	String cPartnerName1;
	public static final String FIELD_PARTNERNAME1 = "PartnerName1";

	/** Maps DB-Column: partner_Name2 */
	String cPartnerName2;
	public static final String FIELD_PARTNERNAME2 = "PartnerName2";

	/** The master Class of this class. */
	public final Class<DataCarrier> MASTER_CLASS = DataCarrier.class;

	/** Set the database tablename (null if unspecified) for this DC. */
	static {
		setDBTablename( UserPartnerDC.class, "[DOC41WEB_MGR].D41_USER_PARTNER" );
	}

	private static final long serialVersionUID = 20130724031312059L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_USERPARTNERDC = java.math.BigDecimal.class;

	@SuppressWarnings("unchecked")
	private static final HashMap<String,BasicDCFieldMeta> FIELD_META = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldMetaMap() );
	private static final String[] LOCAL_FIELD_LIST = new String[] {FIELD_USERID, FIELD_PARTNERNUMBER, FIELD_PARTNERNAME1, FIELD_PARTNERNAME2};
	private static final String[] FIELD_LIST = StringTool.merge(  com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldList(), LOCAL_FIELD_LIST );

	static {
		try {
			FIELD_META.put( FIELD_USERID,	new BasicDCFieldMeta( "IDS",	"ID",	Long.class,	null ) );
			FIELD_META.put( FIELD_PARTNERNUMBER,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_PARTNERNAME1,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_PARTNERNAME2,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of UserPartnerDC!", null );
		}
	}

	/**
	 * The Constructor.
	 * (a)deprecated Use: static UserPartnerDC newInstanceOfUserPartnerDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public UserPartnerDC() {
		super( (Boolean)null );
	}

	/**
	 * Temporary Constructor.
	 */
	protected UserPartnerDC( Boolean pVal ) {
		super( pVal );
		pVal = null;
	}

	/**
	 * The Copy Constructor.
	 * (a)deprecated Use: static UserPartnerDC newInstanceOfUserPartnerDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public UserPartnerDC( BasicDataCarrier pDC )
		throws InitException
	{
		super( (Boolean)null );
		copyFrom( pDC );
	}
	

	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * @throws InitException if the instanciation failes.
	 */
	public static UserPartnerDC newInstanceOfUserPartnerDC() throws InitException {
		return (UserPartnerDC)newInstanceOf( UserPartnerDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 * @throws InitException if the instanciation failes.
	 */
	public static UserPartnerDC newInstanceOfUserPartnerDC( Locale pLoc ) throws InitException {
		return (UserPartnerDC)localizeDC( newInstanceOfUserPartnerDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pDC dc to copy the attributes from.
	 * @throws InitException if the instanciation failes.
	 */
	public static UserPartnerDC newInstanceOfUserPartnerDC( BasicDataCarrier pDC ) throws InitException {
		return (UserPartnerDC)newInstanceOfUserPartnerDC().copyFrom( pDC );
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
	public static final int CHECK_USERPARTNERDC = com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.CHECK_USERCHANGEABLEDATACARRIER;

	/**
	 * Copy the parameter DC pOther into this.
	 */
	@SuppressWarnings("unchecked")

	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) throws InitException {
		super.copyFrom(pOther);
		if (UserPartnerDC.class.isAssignableFrom(pOther.getClass())) {
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
			"UserPartnerDC",
			"com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier",
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
		return UserPartnerDC.localGetCVSMeta();
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
		return UserPartnerDC.localGetFieldMeta( pFieldName );
	}

	/**
	 * Provides a field list of all fields that are supported by the DC.
	 */
	public static String[] localGetFieldList() {
		return FIELD_LIST;
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
	public void setFormattedUserId( String pUserId )
		throws java.text.ParseException
	{
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
	public void setFormattedPartnerNumber( String pPartnerNumber )
		throws java.text.ParseException
	{
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


	// START Method getPartnerName1 generated
	/**
	 * Maps DB-Column: partner_Name1
	 * Logical type: STRING
	 */
	public String getPartnerName1() {
		return cPartnerName1;
	}
	// END Method getPartnerName1


	// START Method setPartnerName1 generated
	/**
	 * Maps DB-Column: partner_Name1
	 * Logical type: STRING
	 */
	public void setPartnerName1( String pPartnerName1 ) {
		cPartnerName1 = StringTool.trimmedEmptyToNull( pPartnerName1 );
		forgetOriginalValue( FIELD_PARTNERNAME1 );
		touchField( FIELD_PARTNERNAME1 );
	}
	// END Method setPartnerName1


	// START Method getFormattedPartnerName1 generated
	/**
	 * Maps DB-Column: partner_Name1
	 * Logical type: STRING
	 */
	public String getFormattedPartnerName1() {
		return hasInvalidValue( FIELD_PARTNERNAME1 ) ? getOriginalValue( FIELD_PARTNERNAME1 ) : getPool().formatString( cPartnerName1 );
	}
	// END Method getFormattedPartnerName1


	// START Method setFormattedPartnerName1 generated
	/**
	 * Maps DB-Column: partner_Name1
	 * Logical type: STRING
	 */
	public void setFormattedPartnerName1( String pPartnerName1 )
		throws java.text.ParseException
	{
		setPartnerName1( null );
		memorizeOriginalValue( FIELD_PARTNERNAME1, pPartnerName1 );
		setPartnerName1( getPool().parseString( pPartnerName1 ) );
	}
	// END Method setFormattedPartnerName1


	// START Method getFormattedHTMLPartnerName1 generated
	/**
	 * Maps DB-Column: partner_Name1
	 * Logical type: STRING
	 */
	public String getFormattedHTMLPartnerName1() {
		return StringTool.escapeHTML( getFormattedPartnerName1() );
	}
	// END Method getFormattedHTMLPartnerName1


	// START Method getToStringPartnerName1 generated
	/**
	 * Maps DB-Column: partner_Name1
	 * Logical type: STRING
	 */
	public String getPartnerName1ToString() {
		return StringTool.nullToEmpty(getPartnerName1());
	}
	// END Method getToStringPartnerName1


	// START Method setFromStringPartnerName1 generated
	/**
	 * Maps DB-Column: partner_Name1
	 * Logical type: STRING
	 */
	public void setPartnerName1FromString( String pPartnerName1 ) {
		setPartnerName1( StringTool.emptyToNull( pPartnerName1 ) );
	}
	// END Method setFromStringPartnerName1


	// START Method getColumnMetaDataPartnerName1 generated
	/**
	 * Maps DB-Column: partner_Name1
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForPartnerName1() {
		return getBasicDCColumnMetaData( FIELD_PARTNERNAME1 );
	}
	// END Method getColumnMetaDataPartnerName1


	// START Method getPartnerName2 generated
	/**
	 * Maps DB-Column: partner_Name2
	 * Logical type: STRING
	 */
	public String getPartnerName2() {
		return cPartnerName2;
	}
	// END Method getPartnerName2


	// START Method setPartnerName2 generated
	/**
	 * Maps DB-Column: partner_Name2
	 * Logical type: STRING
	 */
	public void setPartnerName2( String pPartnerName2 ) {
		cPartnerName2 = StringTool.trimmedEmptyToNull( pPartnerName2 );
		forgetOriginalValue( FIELD_PARTNERNAME2 );
		touchField( FIELD_PARTNERNAME2 );
	}
	// END Method setPartnerName2


	// START Method getFormattedPartnerName2 generated
	/**
	 * Maps DB-Column: partner_Name2
	 * Logical type: STRING
	 */
	public String getFormattedPartnerName2() {
		return hasInvalidValue( FIELD_PARTNERNAME2 ) ? getOriginalValue( FIELD_PARTNERNAME2 ) : getPool().formatString( cPartnerName2 );
	}
	// END Method getFormattedPartnerName2


	// START Method setFormattedPartnerName2 generated
	/**
	 * Maps DB-Column: partner_Name2
	 * Logical type: STRING
	 */
	public void setFormattedPartnerName2( String pPartnerName2 )
		throws java.text.ParseException
	{
		setPartnerName2( null );
		memorizeOriginalValue( FIELD_PARTNERNAME2, pPartnerName2 );
		setPartnerName2( getPool().parseString( pPartnerName2 ) );
	}
	// END Method setFormattedPartnerName2


	// START Method getFormattedHTMLPartnerName2 generated
	/**
	 * Maps DB-Column: partner_Name2
	 * Logical type: STRING
	 */
	public String getFormattedHTMLPartnerName2() {
		return StringTool.escapeHTML( getFormattedPartnerName2() );
	}
	// END Method getFormattedHTMLPartnerName2


	// START Method getToStringPartnerName2 generated
	/**
	 * Maps DB-Column: partner_Name2
	 * Logical type: STRING
	 */
	public String getPartnerName2ToString() {
		return StringTool.nullToEmpty(getPartnerName2());
	}
	// END Method getToStringPartnerName2


	// START Method setFromStringPartnerName2 generated
	/**
	 * Maps DB-Column: partner_Name2
	 * Logical type: STRING
	 */
	public void setPartnerName2FromString( String pPartnerName2 ) {
		setPartnerName2( StringTool.emptyToNull( pPartnerName2 ) );
	}
	// END Method setFromStringPartnerName2


	// START Method getColumnMetaDataPartnerName2 generated
	/**
	 * Maps DB-Column: partner_Name2
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForPartnerName2() {
		return getBasicDCColumnMetaData( FIELD_PARTNERNAME2 );
	}
	// END Method getColumnMetaDataPartnerName2


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
