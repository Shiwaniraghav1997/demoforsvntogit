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
public class MonitoringHistoryDC
	extends com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier
{

	/** Maps DB-Column: interface_Name */
	String cInterfaceName;
	public static final String FIELD_INTERFACENAME = "InterfaceName";

	/** Maps DB-Column: action_Type */
	String cActionType;
	public static final String FIELD_ACTIONTYPE = "ActionType";

	/** Maps DB-Column: action_Status */
	String cActionStatus;
	public static final String FIELD_ACTIONSTATUS = "ActionStatus";

	/** Maps DB-Column: action_Remarks */
	String cActionRemarks;
	public static final String FIELD_ACTIONREMARKS = "ActionRemarks";

	/** Maps DB-Column: action_Details */
	String cActionDetails;
	public static final String FIELD_ACTIONDETAILS = "ActionDetails";

	/** Maps DB-Column: response_Time */
	Long cResponseTime;
	public static final String FIELD_RESPONSETIME = "ResponseTime";

	/** The master Class of this class. */
	public final Class<DataCarrier> MASTER_CLASS = DataCarrier.class;

	/** Set the database tablename (null if unspecified) for this DC. */
	static {
		setDBTablename( MonitoringHistoryDC.class, "[DOC41WEB_MGR].IM_MONITORING_HISTORY" );
	}

	private static final long serialVersionUID = 20130712052911699L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_MONITORINGHISTORYDC = java.math.BigDecimal.class;

	@SuppressWarnings("unchecked")
	private static final HashMap<String,BasicDCFieldMeta> FIELD_META = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldMetaMap() );
	private static final String[] LOCAL_FIELD_LIST = new String[] {FIELD_INTERFACENAME, FIELD_ACTIONTYPE, FIELD_ACTIONSTATUS, FIELD_ACTIONREMARKS, FIELD_ACTIONDETAILS, FIELD_RESPONSETIME};
	private static final String[] FIELD_LIST = StringTool.merge(  com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldList(), LOCAL_FIELD_LIST );

	static {
		try {
			FIELD_META.put( FIELD_INTERFACENAME,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_ACTIONTYPE,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_ACTIONSTATUS,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_ACTIONREMARKS,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_ACTIONDETAILS,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_RESPONSETIME,	new BasicDCFieldMeta( "IDS",	"ID",	Long.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of MonitoringHistoryDC!", null );
		}
	}

	/**
	 * The Constructor.
	 * (a)deprecated Use: static MonitoringHistoryDC newInstanceOfMonitoringHistoryDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public MonitoringHistoryDC() {
		super( (Boolean)null );
	}

	/**
	 * Temporary Constructor.
	 */
	protected MonitoringHistoryDC( Boolean pVal ) {
		super( pVal );
		pVal = null;
	}

	/**
	 * The Copy Constructor.
	 * (a)deprecated Use: static MonitoringHistoryDC newInstanceOfMonitoringHistoryDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public MonitoringHistoryDC( BasicDataCarrier pDC )
		throws InitException
	{
		super( (Boolean)null );
		copyFrom( pDC );
	}
	

	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * @throws InitException if the instanciation failes.
	 */
	public static MonitoringHistoryDC newInstanceOfMonitoringHistoryDC() throws InitException {
		return (MonitoringHistoryDC)newInstanceOf( MonitoringHistoryDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 * @throws InitException if the instanciation failes.
	 */
	public static MonitoringHistoryDC newInstanceOfMonitoringHistoryDC( Locale pLoc ) throws InitException {
		return (MonitoringHistoryDC)localizeDC( newInstanceOfMonitoringHistoryDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pDC dc to copy the attributes from.
	 * @throws InitException if the instanciation failes.
	 */
	public static MonitoringHistoryDC newInstanceOfMonitoringHistoryDC( BasicDataCarrier pDC ) throws InitException {
		return (MonitoringHistoryDC)newInstanceOfMonitoringHistoryDC().copyFrom( pDC );
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
	public static final int CHECK_MONITORINGHISTORYDC = com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.CHECK_USERCHANGEABLEDATACARRIER;

	/**
	 * Copy the parameter DC pOther into this.
	 */
	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) throws InitException {
		super.copyFrom(pOther);
		if (MonitoringHistoryDC.class.isAssignableFrom(pOther.getClass())) {
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
			"MonitoringHistoryDC",
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
		return MonitoringHistoryDC.localGetCVSMeta();
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
		return MonitoringHistoryDC.localGetFieldMeta( pFieldName );
	}

	/**
	 * Provides a field list of all fields that are supported by the DC.
	 */
	public static String[] localGetFieldList() {
		return FIELD_LIST;
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
	public void setFormattedInterfaceName( String pInterfaceName )
		throws java.text.ParseException
	{
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


	// START Method getActionType generated
	/**
	 * Maps DB-Column: action_Type
	 * Logical type: STRING
	 */
	public String getActionType() {
		return cActionType;
	}
	// END Method getActionType


	// START Method setActionType generated
	/**
	 * Maps DB-Column: action_Type
	 * Logical type: STRING
	 */
	public void setActionType( String pActionType ) {
		cActionType = StringTool.trimmedEmptyToNull( pActionType );
		forgetOriginalValue( FIELD_ACTIONTYPE );
		touchField( FIELD_ACTIONTYPE );
	}
	// END Method setActionType


	// START Method getFormattedActionType generated
	/**
	 * Maps DB-Column: action_Type
	 * Logical type: STRING
	 */
	public String getFormattedActionType() {
		return hasInvalidValue( FIELD_ACTIONTYPE ) ? getOriginalValue( FIELD_ACTIONTYPE ) : getPool().formatString( cActionType );
	}
	// END Method getFormattedActionType


	// START Method setFormattedActionType generated
	/**
	 * Maps DB-Column: action_Type
	 * Logical type: STRING
	 */
	public void setFormattedActionType( String pActionType )
		throws java.text.ParseException
	{
		setActionType( null );
		memorizeOriginalValue( FIELD_ACTIONTYPE, pActionType );
		setActionType( getPool().parseString( pActionType ) );
	}
	// END Method setFormattedActionType


	// START Method getFormattedHTMLActionType generated
	/**
	 * Maps DB-Column: action_Type
	 * Logical type: STRING
	 */
	public String getFormattedHTMLActionType() {
		return StringTool.escapeHTML( getFormattedActionType() );
	}
	// END Method getFormattedHTMLActionType


	// START Method getToStringActionType generated
	/**
	 * Maps DB-Column: action_Type
	 * Logical type: STRING
	 */
	public String getActionTypeToString() {
		return StringTool.nullToEmpty(getActionType());
	}
	// END Method getToStringActionType


	// START Method setFromStringActionType generated
	/**
	 * Maps DB-Column: action_Type
	 * Logical type: STRING
	 */
	public void setActionTypeFromString( String pActionType ) {
		setActionType( StringTool.emptyToNull( pActionType ) );
	}
	// END Method setFromStringActionType


	// START Method getColumnMetaDataActionType generated
	/**
	 * Maps DB-Column: action_Type
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForActionType() {
		return getBasicDCColumnMetaData( FIELD_ACTIONTYPE );
	}
	// END Method getColumnMetaDataActionType


	// START Method getActionStatus generated
	/**
	 * Maps DB-Column: action_Status
	 * Logical type: STRING
	 */
	public String getActionStatus() {
		return cActionStatus;
	}
	// END Method getActionStatus


	// START Method setActionStatus generated
	/**
	 * Maps DB-Column: action_Status
	 * Logical type: STRING
	 */
	public void setActionStatus( String pActionStatus ) {
		cActionStatus = StringTool.trimmedEmptyToNull( pActionStatus );
		forgetOriginalValue( FIELD_ACTIONSTATUS );
		touchField( FIELD_ACTIONSTATUS );
	}
	// END Method setActionStatus


	// START Method getFormattedActionStatus generated
	/**
	 * Maps DB-Column: action_Status
	 * Logical type: STRING
	 */
	public String getFormattedActionStatus() {
		return hasInvalidValue( FIELD_ACTIONSTATUS ) ? getOriginalValue( FIELD_ACTIONSTATUS ) : getPool().formatString( cActionStatus );
	}
	// END Method getFormattedActionStatus


	// START Method setFormattedActionStatus generated
	/**
	 * Maps DB-Column: action_Status
	 * Logical type: STRING
	 */
	public void setFormattedActionStatus( String pActionStatus )
		throws java.text.ParseException
	{
		setActionStatus( null );
		memorizeOriginalValue( FIELD_ACTIONSTATUS, pActionStatus );
		setActionStatus( getPool().parseString( pActionStatus ) );
	}
	// END Method setFormattedActionStatus


	// START Method getFormattedHTMLActionStatus generated
	/**
	 * Maps DB-Column: action_Status
	 * Logical type: STRING
	 */
	public String getFormattedHTMLActionStatus() {
		return StringTool.escapeHTML( getFormattedActionStatus() );
	}
	// END Method getFormattedHTMLActionStatus


	// START Method getToStringActionStatus generated
	/**
	 * Maps DB-Column: action_Status
	 * Logical type: STRING
	 */
	public String getActionStatusToString() {
		return StringTool.nullToEmpty(getActionStatus());
	}
	// END Method getToStringActionStatus


	// START Method setFromStringActionStatus generated
	/**
	 * Maps DB-Column: action_Status
	 * Logical type: STRING
	 */
	public void setActionStatusFromString( String pActionStatus ) {
		setActionStatus( StringTool.emptyToNull( pActionStatus ) );
	}
	// END Method setFromStringActionStatus


	// START Method getColumnMetaDataActionStatus generated
	/**
	 * Maps DB-Column: action_Status
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForActionStatus() {
		return getBasicDCColumnMetaData( FIELD_ACTIONSTATUS );
	}
	// END Method getColumnMetaDataActionStatus


	// START Method getActionRemarks generated
	/**
	 * Maps DB-Column: action_Remarks
	 * Logical type: STRING
	 */
	public String getActionRemarks() {
		return cActionRemarks;
	}
	// END Method getActionRemarks


	// START Method setActionRemarks generated
	/**
	 * Maps DB-Column: action_Remarks
	 * Logical type: STRING
	 */
	public void setActionRemarks( String pActionRemarks ) {
		cActionRemarks = StringTool.trimmedEmptyToNull( pActionRemarks );
		forgetOriginalValue( FIELD_ACTIONREMARKS );
		touchField( FIELD_ACTIONREMARKS );
	}
	// END Method setActionRemarks


	// START Method getFormattedActionRemarks generated
	/**
	 * Maps DB-Column: action_Remarks
	 * Logical type: STRING
	 */
	public String getFormattedActionRemarks() {
		return hasInvalidValue( FIELD_ACTIONREMARKS ) ? getOriginalValue( FIELD_ACTIONREMARKS ) : getPool().formatString( cActionRemarks );
	}
	// END Method getFormattedActionRemarks


	// START Method setFormattedActionRemarks generated
	/**
	 * Maps DB-Column: action_Remarks
	 * Logical type: STRING
	 */
	public void setFormattedActionRemarks( String pActionRemarks )
		throws java.text.ParseException
	{
		setActionRemarks( null );
		memorizeOriginalValue( FIELD_ACTIONREMARKS, pActionRemarks );
		setActionRemarks( getPool().parseString( pActionRemarks ) );
	}
	// END Method setFormattedActionRemarks


	// START Method getFormattedHTMLActionRemarks generated
	/**
	 * Maps DB-Column: action_Remarks
	 * Logical type: STRING
	 */
	public String getFormattedHTMLActionRemarks() {
		return StringTool.escapeHTML( getFormattedActionRemarks() );
	}
	// END Method getFormattedHTMLActionRemarks


	// START Method getToStringActionRemarks generated
	/**
	 * Maps DB-Column: action_Remarks
	 * Logical type: STRING
	 */
	public String getActionRemarksToString() {
		return StringTool.nullToEmpty(getActionRemarks());
	}
	// END Method getToStringActionRemarks


	// START Method setFromStringActionRemarks generated
	/**
	 * Maps DB-Column: action_Remarks
	 * Logical type: STRING
	 */
	public void setActionRemarksFromString( String pActionRemarks ) {
		setActionRemarks( StringTool.emptyToNull( pActionRemarks ) );
	}
	// END Method setFromStringActionRemarks


	// START Method getColumnMetaDataActionRemarks generated
	/**
	 * Maps DB-Column: action_Remarks
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForActionRemarks() {
		return getBasicDCColumnMetaData( FIELD_ACTIONREMARKS );
	}
	// END Method getColumnMetaDataActionRemarks


	// START Method getActionDetails generated
	/**
	 * Maps DB-Column: action_Details
	 * Logical type: STRING
	 */
	public String getActionDetails() {
		return cActionDetails;
	}
	// END Method getActionDetails


	// START Method setActionDetails generated
	/**
	 * Maps DB-Column: action_Details
	 * Logical type: STRING
	 */
	public void setActionDetails( String pActionDetails ) {
		cActionDetails = StringTool.trimmedEmptyToNull( pActionDetails );
		forgetOriginalValue( FIELD_ACTIONDETAILS );
		touchField( FIELD_ACTIONDETAILS );
	}
	// END Method setActionDetails


	// START Method getFormattedActionDetails generated
	/**
	 * Maps DB-Column: action_Details
	 * Logical type: STRING
	 */
	public String getFormattedActionDetails() {
		return hasInvalidValue( FIELD_ACTIONDETAILS ) ? getOriginalValue( FIELD_ACTIONDETAILS ) : getPool().formatString( cActionDetails );
	}
	// END Method getFormattedActionDetails


	// START Method setFormattedActionDetails generated
	/**
	 * Maps DB-Column: action_Details
	 * Logical type: STRING
	 */
	public void setFormattedActionDetails( String pActionDetails )
		throws java.text.ParseException
	{
		setActionDetails( null );
		memorizeOriginalValue( FIELD_ACTIONDETAILS, pActionDetails );
		setActionDetails( getPool().parseString( pActionDetails ) );
	}
	// END Method setFormattedActionDetails


	// START Method getFormattedHTMLActionDetails generated
	/**
	 * Maps DB-Column: action_Details
	 * Logical type: STRING
	 */
	public String getFormattedHTMLActionDetails() {
		return StringTool.escapeHTML( getFormattedActionDetails() );
	}
	// END Method getFormattedHTMLActionDetails


	// START Method getToStringActionDetails generated
	/**
	 * Maps DB-Column: action_Details
	 * Logical type: STRING
	 */
	public String getActionDetailsToString() {
		return StringTool.nullToEmpty(getActionDetails());
	}
	// END Method getToStringActionDetails


	// START Method setFromStringActionDetails generated
	/**
	 * Maps DB-Column: action_Details
	 * Logical type: STRING
	 */
	public void setActionDetailsFromString( String pActionDetails ) {
		setActionDetails( StringTool.emptyToNull( pActionDetails ) );
	}
	// END Method setFromStringActionDetails


	// START Method getColumnMetaDataActionDetails generated
	/**
	 * Maps DB-Column: action_Details
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForActionDetails() {
		return getBasicDCColumnMetaData( FIELD_ACTIONDETAILS );
	}
	// END Method getColumnMetaDataActionDetails


	// START Method getResponseTime generated
	/**
	 * Maps DB-Column: response_Time
	 * Logical type: Id
	 */
	public Long getResponseTime() {
		return cResponseTime;
	}
	// END Method getResponseTime


	// START Method setResponseTime generated
	/**
	 * Maps DB-Column: response_Time
	 * Logical type: Id
	 */
	public void setResponseTime( Long pResponseTime ) {
		cResponseTime = pResponseTime;
		forgetOriginalValue( FIELD_RESPONSETIME );
		touchField( FIELD_RESPONSETIME );
	}
	// END Method setResponseTime


	// START Method getFormattedResponseTime generated
	/**
	 * Maps DB-Column: response_Time
	 * Logical type: Id
	 */
	public String getFormattedResponseTime() {
		return hasInvalidValue( FIELD_RESPONSETIME ) ? getOriginalValue( FIELD_RESPONSETIME ) : getPool().formatId( "Id", "#", getResponseTime() );
	}
	// END Method getFormattedResponseTime


	// START Method setFormattedResponseTime generated
	/**
	 * Maps DB-Column: response_Time
	 * Logical type: Id
	 */
	public void setFormattedResponseTime( String pResponseTime )
		throws java.text.ParseException
	{
		setResponseTime( null );
		memorizeOriginalValue( FIELD_RESPONSETIME, pResponseTime );
		setResponseTime( getPool().parseId( "Id", "#", pResponseTime ) );
	}
	// END Method setFormattedResponseTime


	// START Method getFormattedHTMLResponseTime generated
	/**
	 * Maps DB-Column: response_Time
	 * Logical type: Id
	 */
	public String getFormattedHTMLResponseTime() {
		return StringTool.escapeHTML( getFormattedResponseTime() );
	}
	// END Method getFormattedHTMLResponseTime


	// START Method getToStringResponseTime generated
	/**
	 * Maps DB-Column: response_Time
	 * Logical type: Id
	 */
	public String getResponseTimeToString() {
		return StringTool.nullToEmpty(getResponseTime());
	}
	// END Method getToStringResponseTime


	// START Method setFromStringResponseTime generated
	/**
	 * Maps DB-Column: response_Time
	 * Logical type: Id
	 */
	public void setResponseTimeFromString( String pResponseTime ) {
		setResponseTime( NumberTool.parseLong( pResponseTime, null ) );
	}
	// END Method setFromStringResponseTime


	// START Method getColumnMetaDataResponseTime generated
	/**
	 * Maps DB-Column: response_Time
	 * Logical type: Id
	 */
	public BasicDCColumnMetaData getColumnMetaDataForResponseTime() {
		return getBasicDCColumnMetaData( FIELD_RESPONSETIME );
	}
	// END Method getColumnMetaDataResponseTime


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
