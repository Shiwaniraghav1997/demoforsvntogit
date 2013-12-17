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

	/** Maps DB-Column: partner_Id */
	Long cPartnerId;
	public static final String FIELD_PARTNERID = "PartnerId";

	/** The master Class of this class. */
	public final Class<DataCarrier> MASTER_CLASS = DataCarrier.class;

	/** Set the database tablename (null if unspecified) for this DC. */
	static {
		setDBTablename( UserPartnerDC.class, "[DOC41WEB_MGR].D41_USER_PARTNER" );
	}

	private static final long serialVersionUID = 20131217125425752L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_USERPARTNERDC = java.math.BigDecimal.class;

	@SuppressWarnings("unchecked")
	private static final HashMap<String,BasicDCFieldMeta> FIELD_META = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldMetaMap() );
	private static final String[] LOCAL_FIELD_LIST = new String[] {FIELD_USERID, FIELD_PARTNERID};
	private static final String[] FIELD_LIST = StringTool.merge(  com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldList(), LOCAL_FIELD_LIST );

	static {
		try {
			FIELD_META.put( FIELD_USERID,	new BasicDCFieldMeta( "IDS",	"ID",	Long.class,	null ) );
			FIELD_META.put( FIELD_PARTNERID,	new BasicDCFieldMeta( "IDS",	"ID",	Long.class,	null ) );
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


	// START Method getPartnerId generated
	/**
	 * Maps DB-Column: partner_Id
	 * Logical type: Id
	 */
	public Long getPartnerId() {
		return cPartnerId;
	}
	// END Method getPartnerId


	// START Method setPartnerId generated
	/**
	 * Maps DB-Column: partner_Id
	 * Logical type: Id
	 */
	public void setPartnerId( Long pPartnerId ) {
		cPartnerId = pPartnerId;
		forgetOriginalValue( FIELD_PARTNERID );
		touchField( FIELD_PARTNERID );
	}
	// END Method setPartnerId


	// START Method getFormattedPartnerId generated
	/**
	 * Maps DB-Column: partner_Id
	 * Logical type: Id
	 */
	public String getFormattedPartnerId() {
		return hasInvalidValue( FIELD_PARTNERID ) ? getOriginalValue( FIELD_PARTNERID ) : getPool().formatId( "Id", "#", getPartnerId() );
	}
	// END Method getFormattedPartnerId


	// START Method setFormattedPartnerId generated
	/**
	 * Maps DB-Column: partner_Id
	 * Logical type: Id
	 */
	public void setFormattedPartnerId( String pPartnerId )
		throws java.text.ParseException
	{
		setPartnerId( null );
		memorizeOriginalValue( FIELD_PARTNERID, pPartnerId );
		setPartnerId( getPool().parseId( "Id", "#", pPartnerId ) );
	}
	// END Method setFormattedPartnerId


	// START Method getFormattedHTMLPartnerId generated
	/**
	 * Maps DB-Column: partner_Id
	 * Logical type: Id
	 */
	public String getFormattedHTMLPartnerId() {
		return StringTool.escapeHTML( getFormattedPartnerId() );
	}
	// END Method getFormattedHTMLPartnerId


	// START Method getToStringPartnerId generated
	/**
	 * Maps DB-Column: partner_Id
	 * Logical type: Id
	 */
	public String getPartnerIdToString() {
		return StringTool.nullToEmpty(getPartnerId());
	}
	// END Method getToStringPartnerId


	// START Method setFromStringPartnerId generated
	/**
	 * Maps DB-Column: partner_Id
	 * Logical type: Id
	 */
	public void setPartnerIdFromString( String pPartnerId ) {
		setPartnerId( NumberTool.parseLong( pPartnerId, null ) );
	}
	// END Method setFromStringPartnerId


	// START Method getColumnMetaDataPartnerId generated
	/**
	 * Maps DB-Column: partner_Id
	 * Logical type: Id
	 */
	public BasicDCColumnMetaData getColumnMetaDataForPartnerId() {
		return getBasicDCColumnMetaData( FIELD_PARTNERID );
	}
	// END Method getColumnMetaDataPartnerId


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
