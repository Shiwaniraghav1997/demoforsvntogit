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
public class   UserPlantDC
       extends com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier {

	/** Maps DB-Column: user_Id */
	Long cUserId;
	public static final String FIELD_USERID = "UserId";

	/** Maps DB-Column: Plant */
	String cPlant;
	public static final String FIELD_PLANT = "Plant";

	/** for compatibility: dummy variable to suppress import warning...*/
	public static final Class<DataCarrier> _DBX_DC_CLASS_USERPLANTDC = DataCarrier.class;

	/** The master Class of this class. */
	public static final Class<BasicDataCarrier> MASTER_CLASS_USERPLANTDC = BasicDataCarrier.class;

	/** Set the database table-name (null if unspecified) for this DC. */
	static {
		setDBTablename( UserPlantDC.class, "[DOC41WEB_MGR].D41_USER_PLANT" );
	}

	private static final long serialVersionUID = 20180319062714115L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_USERPLANTDC = java.math.BigDecimal.class;

	private static final HashMap<String,BasicDCFieldMeta> FIELD_META       = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldMetaMap() );
	private static final String[]                         LOCAL_FIELD_LIST = new String[] {FIELD_USERID, FIELD_PLANT};
	private static final String[]                         FIELD_LIST       = StringTool.merge( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldList(), LOCAL_FIELD_LIST );

	/** Regeneration check for subclasses. If this line causes an error, you forgot to regenerate the direct superclass */
	public static final int CHECK_USERPLANTDC = com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.CHECK_USERCHANGEABLEDATACARRIER;

	static {
		try {
			FIELD_META.put( FIELD_USERID,	new BasicDCFieldMeta( "IDS",	"ID",	Long.class,	null ) );
			FIELD_META.put( FIELD_PLANT,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of UserPlantDC!", e );
		}
	}

	/**
	 * The standard Constructor (Framework internal: do not use in applications!).
	 * @deprecated Use: static UserPlantDC newInstanceOfUserPlantDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public UserPlantDC() {
		super( (Boolean)null );
	}

	/**
	 * Constructor for class-hierarchy (Framework internal: do not use in applications!).
	 */
	protected UserPlantDC( Boolean pVal ) {
		super( pVal );
	}

	/**
	 * The Copy Constructor.
	 * @deprecated Use: static UserPlantDC newInstanceOfUserPlantDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public UserPlantDC( BasicDataCarrier pDC ) {
		super( (Boolean)null );
		copyFrom( pDC );
	}


	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * throws an InitException if the instantiation fails.
	 */
	public static UserPlantDC newInstanceOfUserPlantDC() {
		return newInstanceOf( UserPlantDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 */
	public static UserPlantDC newInstanceOfUserPlantDC( Locale pLoc ) {
		return localizeDC( newInstanceOfUserPlantDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pDC DC to copy the attributes from.
	 */
	public static UserPlantDC newInstanceOfUserPlantDC( BasicDataCarrier pDC ) {
		return (UserPlantDC)newInstanceOfUserPlantDC().copyFrom( pDC );
	}

	/**
	 * Copy the parameter DC pOther into this.
	 */
	@Override
	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) {
		super.copyFrom(pOther);
		if (UserPlantDC.class.isAssignableFrom(pOther.getClass())) {
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
			"UserPlantDC",
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
		return UserPlantDC.localGetCVSMeta();
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
		return UserPlantDC.localGetFieldMeta( pFieldName );
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


	// START Method getPlant generated
	/**
	 * Maps DB-Column: Plant
	 * Logical type: STRING
	 */
	public String getPlant() {
		return cPlant;
	}
	// END Method getPlant


	// START Method setPlant generated
	/**
	 * Maps DB-Column: Plant
	 * Logical type: STRING
	 */
	public void setPlant( String pPlant ) {
		cPlant = StringTool.trimmedEmptyToNull( pPlant );
		forgetOriginalValue( FIELD_PLANT );
		touchField( FIELD_PLANT );
	}
	// END Method setPlant


	// START Method getFormattedPlant generated
	/**
	 * Maps DB-Column: Plant
	 * Logical type: STRING
	 */
	public String getFormattedPlant() {
		return hasInvalidValue( FIELD_PLANT ) ? getOriginalValue( FIELD_PLANT ) : getPool().formatString( cPlant );
	}
	// END Method getFormattedPlant


	// START Method setFormattedPlant generated
	/**
	 * Maps DB-Column: Plant
	 * Logical type: STRING
	 */
	public void setFormattedPlant( String pPlant ) throws java.text.ParseException {
		setPlant( null );
		memorizeOriginalValue( FIELD_PLANT, pPlant );
		setPlant( getPool().parseString( pPlant ) );
	}
	// END Method setFormattedPlant


	// START Method getFormattedHTMLPlant generated
	/**
	 * Maps DB-Column: Plant
	 * Logical type: STRING
	 */
	public String getFormattedHTMLPlant() {
		return StringTool.escapeHTML( getFormattedPlant() );
	}
	// END Method getFormattedHTMLPlant


	// START Method getToStringPlant generated
	/**
	 * Maps DB-Column: Plant
	 * Logical type: STRING
	 */
	public String getPlantToString() {
		return StringTool.nullToEmpty(getPlant());
	}
	// END Method getToStringPlant


	// START Method setFromStringPlant generated
	/**
	 * Maps DB-Column: Plant
	 * Logical type: STRING
	 */
	public void setPlantFromString( String pPlant ) {
		setPlant( StringTool.emptyToNull( pPlant ) );
	}
	// END Method setFromStringPlant


	// START Method getColumnMetaDataPlant generated
	/**
	 * Maps DB-Column: Plant
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForPlant() {
		return getBasicDCColumnMetaData( FIELD_PLANT );
	}
	// END Method getColumnMetaDataPlant


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
