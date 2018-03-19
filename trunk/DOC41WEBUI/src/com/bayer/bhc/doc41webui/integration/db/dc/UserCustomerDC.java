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
public class   UserCustomerDC
       extends com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier {

	/** Maps DB-Column: user_Id */
	Long cUserId;
	public static final String FIELD_USERID = "UserId";

	/** Maps DB-Column: customer_Id */
	Long cCustomerId;
	public static final String FIELD_CUSTOMERID = "CustomerId";

	/** for compatibility: dummy variable to suppress import warning...*/
	public static final Class<DataCarrier> _DBX_DC_CLASS_USERCUSTOMERDC = DataCarrier.class;

	/** The master Class of this class. */
	public static final Class<BasicDataCarrier> MASTER_CLASS_USERCUSTOMERDC = BasicDataCarrier.class;

	/** Set the database table-name (null if unspecified) for this DC. */
	static {
		setDBTablename( UserCustomerDC.class, "[DOC41WEB_MGR].D41_USER_CUSTOMER" );
	}

	private static final long serialVersionUID = 20180319062714103L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_USERCUSTOMERDC = java.math.BigDecimal.class;

	private static final HashMap<String,BasicDCFieldMeta> FIELD_META       = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldMetaMap() );
	private static final String[]                         LOCAL_FIELD_LIST = new String[] {FIELD_USERID, FIELD_CUSTOMERID};
	private static final String[]                         FIELD_LIST       = StringTool.merge( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldList(), LOCAL_FIELD_LIST );

	/** Regeneration check for subclasses. If this line causes an error, you forgot to regenerate the direct superclass */
	public static final int CHECK_USERCUSTOMERDC = com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.CHECK_USERCHANGEABLEDATACARRIER;

	static {
		try {
			FIELD_META.put( FIELD_USERID,	new BasicDCFieldMeta( "IDS",	"ID",	Long.class,	null ) );
			FIELD_META.put( FIELD_CUSTOMERID,	new BasicDCFieldMeta( "IDS",	"ID",	Long.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of UserCustomerDC!", e );
		}
	}

	/**
	 * The standard Constructor (Framework internal: do not use in applications!).
	 * @deprecated Use: static UserCustomerDC newInstanceOfUserCustomerDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public UserCustomerDC() {
		super( (Boolean)null );
	}

	/**
	 * Constructor for class-hierarchy (Framework internal: do not use in applications!).
	 */
	protected UserCustomerDC( Boolean pVal ) {
		super( pVal );
	}

	/**
	 * The Copy Constructor.
	 * @deprecated Use: static UserCustomerDC newInstanceOfUserCustomerDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public UserCustomerDC( BasicDataCarrier pDC ) {
		super( (Boolean)null );
		copyFrom( pDC );
	}


	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * throws an InitException if the instantiation fails.
	 */
	public static UserCustomerDC newInstanceOfUserCustomerDC() {
		return newInstanceOf( UserCustomerDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 */
	public static UserCustomerDC newInstanceOfUserCustomerDC( Locale pLoc ) {
		return localizeDC( newInstanceOfUserCustomerDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pDC DC to copy the attributes from.
	 */
	public static UserCustomerDC newInstanceOfUserCustomerDC( BasicDataCarrier pDC ) {
		return (UserCustomerDC)newInstanceOfUserCustomerDC().copyFrom( pDC );
	}

	/**
	 * Copy the parameter DC pOther into this.
	 */
	@Override
	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) {
		super.copyFrom(pOther);
		if (UserCustomerDC.class.isAssignableFrom(pOther.getClass())) {
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
			"UserCustomerDC",
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
		return UserCustomerDC.localGetCVSMeta();
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
		return UserCustomerDC.localGetFieldMeta( pFieldName );
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


	// START Method getCustomerId generated
	/**
	 * Maps DB-Column: customer_Id
	 * Logical type: Id
	 */
	public Long getCustomerId() {
		return cCustomerId;
	}
	// END Method getCustomerId


	// START Method setCustomerId generated
	/**
	 * Maps DB-Column: customer_Id
	 * Logical type: Id
	 */
	public void setCustomerId( Long pCustomerId ) {
		cCustomerId = pCustomerId;
		forgetOriginalValue( FIELD_CUSTOMERID );
		touchField( FIELD_CUSTOMERID );
	}
	// END Method setCustomerId


	// START Method getFormattedCustomerId generated
	/**
	 * Maps DB-Column: customer_Id
	 * Logical type: Id
	 */
	public String getFormattedCustomerId() {
		return hasInvalidValue( FIELD_CUSTOMERID ) ? getOriginalValue( FIELD_CUSTOMERID ) : getPool().formatId( "Id", "#", getCustomerId() );
	}
	// END Method getFormattedCustomerId


	// START Method setFormattedCustomerId generated
	/**
	 * Maps DB-Column: customer_Id
	 * Logical type: Id
	 */
	public void setFormattedCustomerId( String pCustomerId ) throws java.text.ParseException {
		setCustomerId( null );
		memorizeOriginalValue( FIELD_CUSTOMERID, pCustomerId );
		setCustomerId( getPool().parseId( "Id", "#", pCustomerId ) );
	}
	// END Method setFormattedCustomerId


	// START Method getFormattedHTMLCustomerId generated
	/**
	 * Maps DB-Column: customer_Id
	 * Logical type: Id
	 */
	public String getFormattedHTMLCustomerId() {
		return StringTool.escapeHTML( getFormattedCustomerId() );
	}
	// END Method getFormattedHTMLCustomerId


	// START Method getToStringCustomerId generated
	/**
	 * Maps DB-Column: customer_Id
	 * Logical type: Id
	 */
	public String getCustomerIdToString() {
		return StringTool.nullToEmpty(getCustomerId());
	}
	// END Method getToStringCustomerId


	// START Method setFromStringCustomerId generated
	/**
	 * Maps DB-Column: customer_Id
	 * Logical type: Id
	 */
	public void setCustomerIdFromString( String pCustomerId ) {
		setCustomerId( NumberTool.parseLong( pCustomerId, null ) );
	}
	// END Method setFromStringCustomerId


	// START Method getColumnMetaDataCustomerId generated
	/**
	 * Maps DB-Column: customer_Id
	 * Logical type: Id
	 */
	public BasicDCColumnMetaData getColumnMetaDataForCustomerId() {
		return getBasicDCColumnMetaData( FIELD_CUSTOMERID );
	}
	// END Method getColumnMetaDataCustomerId


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
