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
public class   MonitoringContactDC
       extends com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier {

	/** Maps DB-Column: interface_Name */
	String cInterfaceName;
	public static final String FIELD_INTERFACENAME = "InterfaceName";

	/** Maps DB-Column: contact_Type */
	String cContactType;
	public static final String FIELD_CONTACTTYPE = "ContactType";

	/** Maps DB-Column: cwid */
	String cCwid;
	public static final String FIELD_CWID = "Cwid";

	/** Maps DB-Column: first_Name */
	String cFirstName;
	public static final String FIELD_FIRSTNAME = "FirstName";

	/** Maps DB-Column: last_Name */
	String cLastName;
	public static final String FIELD_LASTNAME = "LastName";

	/** Maps DB-Column: email */
	String cEmail;
	public static final String FIELD_EMAIL = "Email";

	/** Maps DB-Column: phone1 */
	String cPhone1;
	public static final String FIELD_PHONE1 = "Phone1";

	/** Maps DB-Column: phone2 */
	String cPhone2;
	public static final String FIELD_PHONE2 = "Phone2";

	/** for compatibility: dummy variable to suppress import warning...*/
	public static final Class<DataCarrier> _DBX_DC_CLASS_MONITORINGCONTACTDC = DataCarrier.class;

	/** The master Class of this class. */
	public static final Class<BasicDataCarrier> MASTER_CLASS_MONITORINGCONTACTDC = BasicDataCarrier.class;

	/** Set the database table-name (null if unspecified) for this DC. */
	static {
		setDBTablename( MonitoringContactDC.class, "[DOC41WEB_MGR].IM_MONITORING_CONTACT" );
	}

	private static final long serialVersionUID = 20180319062713664L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_MONITORINGCONTACTDC = java.math.BigDecimal.class;

	private static final HashMap<String,BasicDCFieldMeta> FIELD_META       = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldMetaMap() );
	private static final String[]                         LOCAL_FIELD_LIST = new String[] {FIELD_INTERFACENAME, FIELD_CONTACTTYPE, FIELD_CWID, FIELD_FIRSTNAME, FIELD_LASTNAME, FIELD_EMAIL, FIELD_PHONE1, FIELD_PHONE2};
	private static final String[]                         FIELD_LIST       = StringTool.merge( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldList(), LOCAL_FIELD_LIST );

	/** Regeneration check for subclasses. If this line causes an error, you forgot to regenerate the direct superclass */
	public static final int CHECK_MONITORINGCONTACTDC = com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.CHECK_USERCHANGEABLEDATACARRIER;

	static {
		try {
			FIELD_META.put( FIELD_INTERFACENAME,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_CONTACTTYPE,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_CWID,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_FIRSTNAME,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_LASTNAME,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_EMAIL,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_PHONE1,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_PHONE2,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of MonitoringContactDC!", e );
		}
	}

	/**
	 * The standard Constructor (Framework internal: do not use in applications!).
	 * @deprecated Use: static MonitoringContactDC newInstanceOfMonitoringContactDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public MonitoringContactDC() {
		super( (Boolean)null );
	}

	/**
	 * Constructor for class-hierarchy (Framework internal: do not use in applications!).
	 */
	protected MonitoringContactDC( Boolean pVal ) {
		super( pVal );
	}

	/**
	 * The Copy Constructor.
	 * @deprecated Use: static MonitoringContactDC newInstanceOfMonitoringContactDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public MonitoringContactDC( BasicDataCarrier pDC ) {
		super( (Boolean)null );
		copyFrom( pDC );
	}


	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * throws an InitException if the instantiation fails.
	 */
	public static MonitoringContactDC newInstanceOfMonitoringContactDC() {
		return newInstanceOf( MonitoringContactDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 */
	public static MonitoringContactDC newInstanceOfMonitoringContactDC( Locale pLoc ) {
		return localizeDC( newInstanceOfMonitoringContactDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pDC DC to copy the attributes from.
	 */
	public static MonitoringContactDC newInstanceOfMonitoringContactDC( BasicDataCarrier pDC ) {
		return (MonitoringContactDC)newInstanceOfMonitoringContactDC().copyFrom( pDC );
	}

	/**
	 * Copy the parameter DC pOther into this.
	 */
	@Override
	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) {
		super.copyFrom(pOther);
		if (MonitoringContactDC.class.isAssignableFrom(pOther.getClass())) {
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
			"MonitoringContactDC",
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
		return MonitoringContactDC.localGetCVSMeta();
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
		return MonitoringContactDC.localGetFieldMeta( pFieldName );
	}

	/**
	 * Provides a field list of all fields that are supported by the DC.
	 */
	public static String[] localGetFieldList() {
		return FIELD_LIST.clone( );
	}


	// START Method getInterfaceName generated
	/**
	 * Maps DB-Column: interface_Name
	 * Logical type: STRING
	 */
	public String getInterfaceName() {
		return cInterfaceName;
	}
	// END Method getInterfaceName


	// START Method setInterfaceName generated
	/**
	 * Maps DB-Column: interface_Name
	 * Logical type: STRING
	 */
	public void setInterfaceName( String pInterfaceName ) {
		cInterfaceName = StringTool.trimmedEmptyToNull( pInterfaceName );
		forgetOriginalValue( FIELD_INTERFACENAME );
		touchField( FIELD_INTERFACENAME );
	}
	// END Method setInterfaceName


	// START Method getFormattedInterfaceName generated
	/**
	 * Maps DB-Column: interface_Name
	 * Logical type: STRING
	 */
	public String getFormattedInterfaceName() {
		return hasInvalidValue( FIELD_INTERFACENAME ) ? getOriginalValue( FIELD_INTERFACENAME ) : getPool().formatString( cInterfaceName );
	}
	// END Method getFormattedInterfaceName


	// START Method setFormattedInterfaceName generated
	/**
	 * Maps DB-Column: interface_Name
	 * Logical type: STRING
	 */
	public void setFormattedInterfaceName( String pInterfaceName ) throws java.text.ParseException {
		setInterfaceName( null );
		memorizeOriginalValue( FIELD_INTERFACENAME, pInterfaceName );
		setInterfaceName( getPool().parseString( pInterfaceName ) );
	}
	// END Method setFormattedInterfaceName


	// START Method getFormattedHTMLInterfaceName generated
	/**
	 * Maps DB-Column: interface_Name
	 * Logical type: STRING
	 */
	public String getFormattedHTMLInterfaceName() {
		return StringTool.escapeHTML( getFormattedInterfaceName() );
	}
	// END Method getFormattedHTMLInterfaceName


	// START Method getToStringInterfaceName generated
	/**
	 * Maps DB-Column: interface_Name
	 * Logical type: STRING
	 */
	public String getInterfaceNameToString() {
		return StringTool.nullToEmpty(getInterfaceName());
	}
	// END Method getToStringInterfaceName


	// START Method setFromStringInterfaceName generated
	/**
	 * Maps DB-Column: interface_Name
	 * Logical type: STRING
	 */
	public void setInterfaceNameFromString( String pInterfaceName ) {
		setInterfaceName( StringTool.emptyToNull( pInterfaceName ) );
	}
	// END Method setFromStringInterfaceName


	// START Method getColumnMetaDataInterfaceName generated
	/**
	 * Maps DB-Column: interface_Name
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForInterfaceName() {
		return getBasicDCColumnMetaData( FIELD_INTERFACENAME );
	}
	// END Method getColumnMetaDataInterfaceName


	// START Method getContactType generated
	/**
	 * Maps DB-Column: contact_Type
	 * Logical type: STRING
	 */
	public String getContactType() {
		return cContactType;
	}
	// END Method getContactType


	// START Method setContactType generated
	/**
	 * Maps DB-Column: contact_Type
	 * Logical type: STRING
	 */
	public void setContactType( String pContactType ) {
		cContactType = StringTool.trimmedEmptyToNull( pContactType );
		forgetOriginalValue( FIELD_CONTACTTYPE );
		touchField( FIELD_CONTACTTYPE );
	}
	// END Method setContactType


	// START Method getFormattedContactType generated
	/**
	 * Maps DB-Column: contact_Type
	 * Logical type: STRING
	 */
	public String getFormattedContactType() {
		return hasInvalidValue( FIELD_CONTACTTYPE ) ? getOriginalValue( FIELD_CONTACTTYPE ) : getPool().formatString( cContactType );
	}
	// END Method getFormattedContactType


	// START Method setFormattedContactType generated
	/**
	 * Maps DB-Column: contact_Type
	 * Logical type: STRING
	 */
	public void setFormattedContactType( String pContactType ) throws java.text.ParseException {
		setContactType( null );
		memorizeOriginalValue( FIELD_CONTACTTYPE, pContactType );
		setContactType( getPool().parseString( pContactType ) );
	}
	// END Method setFormattedContactType


	// START Method getFormattedHTMLContactType generated
	/**
	 * Maps DB-Column: contact_Type
	 * Logical type: STRING
	 */
	public String getFormattedHTMLContactType() {
		return StringTool.escapeHTML( getFormattedContactType() );
	}
	// END Method getFormattedHTMLContactType


	// START Method getToStringContactType generated
	/**
	 * Maps DB-Column: contact_Type
	 * Logical type: STRING
	 */
	public String getContactTypeToString() {
		return StringTool.nullToEmpty(getContactType());
	}
	// END Method getToStringContactType


	// START Method setFromStringContactType generated
	/**
	 * Maps DB-Column: contact_Type
	 * Logical type: STRING
	 */
	public void setContactTypeFromString( String pContactType ) {
		setContactType( StringTool.emptyToNull( pContactType ) );
	}
	// END Method setFromStringContactType


	// START Method getColumnMetaDataContactType generated
	/**
	 * Maps DB-Column: contact_Type
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForContactType() {
		return getBasicDCColumnMetaData( FIELD_CONTACTTYPE );
	}
	// END Method getColumnMetaDataContactType


	// START Method getCwid generated
	/**
	 * Maps DB-Column: cwid
	 * Logical type: STRING
	 */
	public String getCwid() {
		return cCwid;
	}
	// END Method getCwid


	// START Method setCwid generated
	/**
	 * Maps DB-Column: cwid
	 * Logical type: STRING
	 */
	public void setCwid( String pCwid ) {
		cCwid = StringTool.trimmedEmptyToNull( pCwid );
		forgetOriginalValue( FIELD_CWID );
		touchField( FIELD_CWID );
	}
	// END Method setCwid


	// START Method getFormattedCwid generated
	/**
	 * Maps DB-Column: cwid
	 * Logical type: STRING
	 */
	public String getFormattedCwid() {
		return hasInvalidValue( FIELD_CWID ) ? getOriginalValue( FIELD_CWID ) : getPool().formatString( cCwid );
	}
	// END Method getFormattedCwid


	// START Method setFormattedCwid generated
	/**
	 * Maps DB-Column: cwid
	 * Logical type: STRING
	 */
	public void setFormattedCwid( String pCwid ) throws java.text.ParseException {
		setCwid( null );
		memorizeOriginalValue( FIELD_CWID, pCwid );
		setCwid( getPool().parseString( pCwid ) );
	}
	// END Method setFormattedCwid


	// START Method getFormattedHTMLCwid generated
	/**
	 * Maps DB-Column: cwid
	 * Logical type: STRING
	 */
	public String getFormattedHTMLCwid() {
		return StringTool.escapeHTML( getFormattedCwid() );
	}
	// END Method getFormattedHTMLCwid


	// START Method getToStringCwid generated
	/**
	 * Maps DB-Column: cwid
	 * Logical type: STRING
	 */
	public String getCwidToString() {
		return StringTool.nullToEmpty(getCwid());
	}
	// END Method getToStringCwid


	// START Method setFromStringCwid generated
	/**
	 * Maps DB-Column: cwid
	 * Logical type: STRING
	 */
	public void setCwidFromString( String pCwid ) {
		setCwid( StringTool.emptyToNull( pCwid ) );
	}
	// END Method setFromStringCwid


	// START Method getColumnMetaDataCwid generated
	/**
	 * Maps DB-Column: cwid
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForCwid() {
		return getBasicDCColumnMetaData( FIELD_CWID );
	}
	// END Method getColumnMetaDataCwid


	// START Method getFirstName generated
	/**
	 * Maps DB-Column: first_Name
	 * Logical type: STRING
	 */
	public String getFirstName() {
		return cFirstName;
	}
	// END Method getFirstName


	// START Method setFirstName generated
	/**
	 * Maps DB-Column: first_Name
	 * Logical type: STRING
	 */
	public void setFirstName( String pFirstName ) {
		cFirstName = StringTool.trimmedEmptyToNull( pFirstName );
		forgetOriginalValue( FIELD_FIRSTNAME );
		touchField( FIELD_FIRSTNAME );
	}
	// END Method setFirstName


	// START Method getFormattedFirstName generated
	/**
	 * Maps DB-Column: first_Name
	 * Logical type: STRING
	 */
	public String getFormattedFirstName() {
		return hasInvalidValue( FIELD_FIRSTNAME ) ? getOriginalValue( FIELD_FIRSTNAME ) : getPool().formatString( cFirstName );
	}
	// END Method getFormattedFirstName


	// START Method setFormattedFirstName generated
	/**
	 * Maps DB-Column: first_Name
	 * Logical type: STRING
	 */
	public void setFormattedFirstName( String pFirstName ) throws java.text.ParseException {
		setFirstName( null );
		memorizeOriginalValue( FIELD_FIRSTNAME, pFirstName );
		setFirstName( getPool().parseString( pFirstName ) );
	}
	// END Method setFormattedFirstName


	// START Method getFormattedHTMLFirstName generated
	/**
	 * Maps DB-Column: first_Name
	 * Logical type: STRING
	 */
	public String getFormattedHTMLFirstName() {
		return StringTool.escapeHTML( getFormattedFirstName() );
	}
	// END Method getFormattedHTMLFirstName


	// START Method getToStringFirstName generated
	/**
	 * Maps DB-Column: first_Name
	 * Logical type: STRING
	 */
	public String getFirstNameToString() {
		return StringTool.nullToEmpty(getFirstName());
	}
	// END Method getToStringFirstName


	// START Method setFromStringFirstName generated
	/**
	 * Maps DB-Column: first_Name
	 * Logical type: STRING
	 */
	public void setFirstNameFromString( String pFirstName ) {
		setFirstName( StringTool.emptyToNull( pFirstName ) );
	}
	// END Method setFromStringFirstName


	// START Method getColumnMetaDataFirstName generated
	/**
	 * Maps DB-Column: first_Name
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForFirstName() {
		return getBasicDCColumnMetaData( FIELD_FIRSTNAME );
	}
	// END Method getColumnMetaDataFirstName


	// START Method getLastName generated
	/**
	 * Maps DB-Column: last_Name
	 * Logical type: STRING
	 */
	public String getLastName() {
		return cLastName;
	}
	// END Method getLastName


	// START Method setLastName generated
	/**
	 * Maps DB-Column: last_Name
	 * Logical type: STRING
	 */
	public void setLastName( String pLastName ) {
		cLastName = StringTool.trimmedEmptyToNull( pLastName );
		forgetOriginalValue( FIELD_LASTNAME );
		touchField( FIELD_LASTNAME );
	}
	// END Method setLastName


	// START Method getFormattedLastName generated
	/**
	 * Maps DB-Column: last_Name
	 * Logical type: STRING
	 */
	public String getFormattedLastName() {
		return hasInvalidValue( FIELD_LASTNAME ) ? getOriginalValue( FIELD_LASTNAME ) : getPool().formatString( cLastName );
	}
	// END Method getFormattedLastName


	// START Method setFormattedLastName generated
	/**
	 * Maps DB-Column: last_Name
	 * Logical type: STRING
	 */
	public void setFormattedLastName( String pLastName ) throws java.text.ParseException {
		setLastName( null );
		memorizeOriginalValue( FIELD_LASTNAME, pLastName );
		setLastName( getPool().parseString( pLastName ) );
	}
	// END Method setFormattedLastName


	// START Method getFormattedHTMLLastName generated
	/**
	 * Maps DB-Column: last_Name
	 * Logical type: STRING
	 */
	public String getFormattedHTMLLastName() {
		return StringTool.escapeHTML( getFormattedLastName() );
	}
	// END Method getFormattedHTMLLastName


	// START Method getToStringLastName generated
	/**
	 * Maps DB-Column: last_Name
	 * Logical type: STRING
	 */
	public String getLastNameToString() {
		return StringTool.nullToEmpty(getLastName());
	}
	// END Method getToStringLastName


	// START Method setFromStringLastName generated
	/**
	 * Maps DB-Column: last_Name
	 * Logical type: STRING
	 */
	public void setLastNameFromString( String pLastName ) {
		setLastName( StringTool.emptyToNull( pLastName ) );
	}
	// END Method setFromStringLastName


	// START Method getColumnMetaDataLastName generated
	/**
	 * Maps DB-Column: last_Name
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForLastName() {
		return getBasicDCColumnMetaData( FIELD_LASTNAME );
	}
	// END Method getColumnMetaDataLastName


	// START Method getEmail generated
	/**
	 * Maps DB-Column: email
	 * Logical type: STRING
	 */
	public String getEmail() {
		return cEmail;
	}
	// END Method getEmail


	// START Method setEmail generated
	/**
	 * Maps DB-Column: email
	 * Logical type: STRING
	 */
	public void setEmail( String pEmail ) {
		cEmail = StringTool.trimmedEmptyToNull( pEmail );
		forgetOriginalValue( FIELD_EMAIL );
		touchField( FIELD_EMAIL );
	}
	// END Method setEmail


	// START Method getFormattedEmail generated
	/**
	 * Maps DB-Column: email
	 * Logical type: STRING
	 */
	public String getFormattedEmail() {
		return hasInvalidValue( FIELD_EMAIL ) ? getOriginalValue( FIELD_EMAIL ) : getPool().formatString( cEmail );
	}
	// END Method getFormattedEmail


	// START Method setFormattedEmail generated
	/**
	 * Maps DB-Column: email
	 * Logical type: STRING
	 */
	public void setFormattedEmail( String pEmail ) throws java.text.ParseException {
		setEmail( null );
		memorizeOriginalValue( FIELD_EMAIL, pEmail );
		setEmail( getPool().parseString( pEmail ) );
	}
	// END Method setFormattedEmail


	// START Method getFormattedHTMLEmail generated
	/**
	 * Maps DB-Column: email
	 * Logical type: STRING
	 */
	public String getFormattedHTMLEmail() {
		return StringTool.escapeHTML( getFormattedEmail() );
	}
	// END Method getFormattedHTMLEmail


	// START Method getToStringEmail generated
	/**
	 * Maps DB-Column: email
	 * Logical type: STRING
	 */
	public String getEmailToString() {
		return StringTool.nullToEmpty(getEmail());
	}
	// END Method getToStringEmail


	// START Method setFromStringEmail generated
	/**
	 * Maps DB-Column: email
	 * Logical type: STRING
	 */
	public void setEmailFromString( String pEmail ) {
		setEmail( StringTool.emptyToNull( pEmail ) );
	}
	// END Method setFromStringEmail


	// START Method getColumnMetaDataEmail generated
	/**
	 * Maps DB-Column: email
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForEmail() {
		return getBasicDCColumnMetaData( FIELD_EMAIL );
	}
	// END Method getColumnMetaDataEmail


	// START Method getPhone1 generated
	/**
	 * Maps DB-Column: phone1
	 * Logical type: STRING
	 */
	public String getPhone1() {
		return cPhone1;
	}
	// END Method getPhone1


	// START Method setPhone1 generated
	/**
	 * Maps DB-Column: phone1
	 * Logical type: STRING
	 */
	public void setPhone1( String pPhone1 ) {
		cPhone1 = StringTool.trimmedEmptyToNull( pPhone1 );
		forgetOriginalValue( FIELD_PHONE1 );
		touchField( FIELD_PHONE1 );
	}
	// END Method setPhone1


	// START Method getFormattedPhone1 generated
	/**
	 * Maps DB-Column: phone1
	 * Logical type: STRING
	 */
	public String getFormattedPhone1() {
		return hasInvalidValue( FIELD_PHONE1 ) ? getOriginalValue( FIELD_PHONE1 ) : getPool().formatString( cPhone1 );
	}
	// END Method getFormattedPhone1


	// START Method setFormattedPhone1 generated
	/**
	 * Maps DB-Column: phone1
	 * Logical type: STRING
	 */
	public void setFormattedPhone1( String pPhone1 ) throws java.text.ParseException {
		setPhone1( null );
		memorizeOriginalValue( FIELD_PHONE1, pPhone1 );
		setPhone1( getPool().parseString( pPhone1 ) );
	}
	// END Method setFormattedPhone1


	// START Method getFormattedHTMLPhone1 generated
	/**
	 * Maps DB-Column: phone1
	 * Logical type: STRING
	 */
	public String getFormattedHTMLPhone1() {
		return StringTool.escapeHTML( getFormattedPhone1() );
	}
	// END Method getFormattedHTMLPhone1


	// START Method getToStringPhone1 generated
	/**
	 * Maps DB-Column: phone1
	 * Logical type: STRING
	 */
	public String getPhone1ToString() {
		return StringTool.nullToEmpty(getPhone1());
	}
	// END Method getToStringPhone1


	// START Method setFromStringPhone1 generated
	/**
	 * Maps DB-Column: phone1
	 * Logical type: STRING
	 */
	public void setPhone1FromString( String pPhone1 ) {
		setPhone1( StringTool.emptyToNull( pPhone1 ) );
	}
	// END Method setFromStringPhone1


	// START Method getColumnMetaDataPhone1 generated
	/**
	 * Maps DB-Column: phone1
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForPhone1() {
		return getBasicDCColumnMetaData( FIELD_PHONE1 );
	}
	// END Method getColumnMetaDataPhone1


	// START Method getPhone2 generated
	/**
	 * Maps DB-Column: phone2
	 * Logical type: STRING
	 */
	public String getPhone2() {
		return cPhone2;
	}
	// END Method getPhone2


	// START Method setPhone2 generated
	/**
	 * Maps DB-Column: phone2
	 * Logical type: STRING
	 */
	public void setPhone2( String pPhone2 ) {
		cPhone2 = StringTool.trimmedEmptyToNull( pPhone2 );
		forgetOriginalValue( FIELD_PHONE2 );
		touchField( FIELD_PHONE2 );
	}
	// END Method setPhone2


	// START Method getFormattedPhone2 generated
	/**
	 * Maps DB-Column: phone2
	 * Logical type: STRING
	 */
	public String getFormattedPhone2() {
		return hasInvalidValue( FIELD_PHONE2 ) ? getOriginalValue( FIELD_PHONE2 ) : getPool().formatString( cPhone2 );
	}
	// END Method getFormattedPhone2


	// START Method setFormattedPhone2 generated
	/**
	 * Maps DB-Column: phone2
	 * Logical type: STRING
	 */
	public void setFormattedPhone2( String pPhone2 ) throws java.text.ParseException {
		setPhone2( null );
		memorizeOriginalValue( FIELD_PHONE2, pPhone2 );
		setPhone2( getPool().parseString( pPhone2 ) );
	}
	// END Method setFormattedPhone2


	// START Method getFormattedHTMLPhone2 generated
	/**
	 * Maps DB-Column: phone2
	 * Logical type: STRING
	 */
	public String getFormattedHTMLPhone2() {
		return StringTool.escapeHTML( getFormattedPhone2() );
	}
	// END Method getFormattedHTMLPhone2


	// START Method getToStringPhone2 generated
	/**
	 * Maps DB-Column: phone2
	 * Logical type: STRING
	 */
	public String getPhone2ToString() {
		return StringTool.nullToEmpty(getPhone2());
	}
	// END Method getToStringPhone2


	// START Method setFromStringPhone2 generated
	/**
	 * Maps DB-Column: phone2
	 * Logical type: STRING
	 */
	public void setPhone2FromString( String pPhone2 ) {
		setPhone2( StringTool.emptyToNull( pPhone2 ) );
	}
	// END Method setFromStringPhone2


	// START Method getColumnMetaDataPhone2 generated
	/**
	 * Maps DB-Column: phone2
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForPhone2() {
		return getBasicDCColumnMetaData( FIELD_PHONE2 );
	}
	// END Method getColumnMetaDataPhone2


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
