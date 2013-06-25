/*
 * (c)2007 Bayer AG Leverkusen, Bayer Business Solutions
 * All rights reserved.
 * (based on DCGenenerator DC-Definitions: Id: DCGenerator.ini,v 1.82 2012/10/05 12:22:39 evfpu Exp )
 *
 * $Id$
 */
package com.bayer.bhc.doc41webui.integration.db.dc.masterdata;


import java.util.*;

import com.bayer.ecim.foundation.basic.*;
import com.bayer.ecim.foundation.dbx.*;




/**
 * DC for MD_SYSPARAM in BOE2_MGR
 */
public class MDSysParamDC
	extends com.bayer.bhc.doc41webui.integration.db.dc.Doc41DataCarrier
{

	/** Maps DB-Column: param_Name */
	String cParamName;
	public static final String FIELD_PARAMNAME = "ParamName";

	/** Maps DB-Column: param_Type */
	String cParamType;
	public static final String FIELD_PARAMTYPE = "ParamType";

	/** Maps DB-Column: param_Stringvalue */
	String cParamStringvalue;
	public static final String FIELD_PARAMSTRINGVALUE = "ParamStringvalue";

	/** Maps DB-Column: param_Numbervalue */
	Long cParamNumbervalue;
	public static final String FIELD_PARAMNUMBERVALUE = "ParamNumbervalue";

	/** Maps DB-Column: param_Decimalvalue */
	java.math.BigDecimal cParamDecimalvalue;
	public static final String FIELD_PARAMDECIMALVALUE = "ParamDecimalvalue";

	/** Maps DB-Column: param_Booleanvalue */
	Boolean cParamBooleanvalue;
	public static final String FIELD_PARAMBOOLEANVALUE = "ParamBooleanvalue";

	/** Maps DB-Column: is_Deletable */
	Boolean cIsDeletable = Boolean.TRUE;
	public static final String FIELD_ISDELETABLE = "IsDeletable";

	/** The master Class of this class. */
	public final Class<DataCarrier> MASTER_CLASS = DataCarrier.class;

	/** Set the database tablename (null if unspecified) for this DC. */
	static {
		setDBTablename( MDSysParamDC.class, "[DOC41WEB_MGR].MD_SYSPARAM" );
	}

	private static final long serialVersionUID = 20130409035827586L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_MDSYSPARAMDC = java.math.BigDecimal.class;

	private static final HashMap<String,BasicDCFieldMeta> FIELD_META = new HashMap<String,BasicDCFieldMeta>( com.bayer.bhc.doc41webui.integration.db.dc.Doc41DataCarrier.localGetFieldMetaMap() );
	private static final String[] LOCAL_FIELD_LIST = new String[] {FIELD_PARAMNAME, FIELD_PARAMTYPE, FIELD_PARAMSTRINGVALUE, FIELD_PARAMNUMBERVALUE, FIELD_PARAMDECIMALVALUE, FIELD_PARAMBOOLEANVALUE, FIELD_ISDELETABLE};
	private static final String[] FIELD_LIST = StringTool.merge(  com.bayer.bhc.doc41webui.integration.db.dc.Doc41DataCarrier.localGetFieldList(), LOCAL_FIELD_LIST );

	static {
		try {
			FIELD_META.put( FIELD_PARAMNAME,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_PARAMTYPE,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_PARAMSTRINGVALUE,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_PARAMNUMBERVALUE,	new BasicDCFieldMeta( "IDS",	"ID",	Long.class,	null ) );
			FIELD_META.put( FIELD_PARAMDECIMALVALUE,	new BasicDCFieldMeta( "DECIMAL",	"QUANTITY",	java.math.BigDecimal.class,	null ) );
			FIELD_META.put( FIELD_PARAMBOOLEANVALUE,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
			FIELD_META.put( FIELD_ISDELETABLE,	new BasicDCFieldMeta( "BOOLEANS",	"BOOLEAN",	Boolean.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of MDSysParamDC!", null );
		}
	}

	/**
	 * The Constructor.
	 * (a)deprecated Use: static MDSysParamDC newInstanceOfMDSysParamDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public MDSysParamDC() {
		super( (Boolean)null );
	}

	/**
	 * Temporary Constructor.
	 */
	protected MDSysParamDC( Boolean pVal ) {
		super( pVal );
		pVal = null;
	}

	/**
	 * The Copy Constructor.
	 * (a)deprecated Use: static MDSysParamDC newInstanceOfMDSysParamDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public MDSysParamDC( BasicDataCarrier pDC )
		throws InitException
	{
		super( (Boolean)null );
		copyFrom( pDC );
	}
	

	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * @throws InitException if the instanciation failes.
	 */
	public static MDSysParamDC newInstanceOfMDSysParamDC() throws InitException {
		return (MDSysParamDC)newInstanceOf( MDSysParamDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 * @throws InitException if the instanciation failes.
	 */
	public static MDSysParamDC newInstanceOfMDSysParamDC( Locale pLoc ) throws InitException {
		return (MDSysParamDC)localizeDC( newInstanceOfMDSysParamDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pDC dc to copy the attributes from.
	 * @throws InitException if the instanciation failes.
	 */
	public static MDSysParamDC newInstanceOfMDSysParamDC( BasicDataCarrier pDC ) throws InitException {
		return (MDSysParamDC)newInstanceOfMDSysParamDC().copyFrom( pDC );
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
	public static final int CHECK_MDSYSPARAMDC = com.bayer.bhc.doc41webui.integration.db.dc.Doc41DataCarrier.CHECK_DOC41DATACARRIER;

	/**
	 * Copy the parameter DC pOther into this.
	 */
	@SuppressWarnings("unchecked")

	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) throws InitException {
		super.copyFrom(pOther);
		if (MDSysParamDC.class.isAssignableFrom(pOther.getClass())) {
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
			"com.bayer.bhc.doc41webui.integration.db.dc.masterdata",
			"MDSysParamDC",
			"com.bayer.bhc.doc41webui.integration.db.dc.Doc41DataCarrier",
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
		return MDSysParamDC.localGetCVSMeta();
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
		return MDSysParamDC.localGetFieldMeta( pFieldName );
	}

	/**
	 * Provides a field list of all fields that are supported by the DC.
	 */
	public static String[] localGetFieldList() {
		return FIELD_LIST;
	}


	// START Method getParamName generated
	/**
	 * Maps DB-Column: param_Name
	 * Logical type: STRING
	 */
	public String getParamName() {
		return cParamName;
	}
	// END Method getParamName


	// START Method setParamName generated
	/**
	 * Maps DB-Column: param_Name
	 * Logical type: STRING
	 */
	public void setParamName( String pParamName ) {
		cParamName = StringTool.trimmedEmptyToNull( pParamName );
		forgetOriginalValue( FIELD_PARAMNAME );
		touchField( FIELD_PARAMNAME );
	}
	// END Method setParamName


	// START Method getFormattedParamName generated
	/**
	 * Maps DB-Column: param_Name
	 * Logical type: STRING
	 */
	public String getFormattedParamName() {
		return hasInvalidValue( FIELD_PARAMNAME ) ? getOriginalValue( FIELD_PARAMNAME ) : getPool().formatString( cParamName );
	}
	// END Method getFormattedParamName


	// START Method setFormattedParamName generated
	/**
	 * Maps DB-Column: param_Name
	 * Logical type: STRING
	 */
	public void setFormattedParamName( String pParamName )
		throws java.text.ParseException
	{
		setParamName( null );
		memorizeOriginalValue( FIELD_PARAMNAME, pParamName );
		setParamName( getPool().parseString( pParamName ) );
	}
	// END Method setFormattedParamName


	// START Method getFormattedHTMLParamName generated
	/**
	 * Maps DB-Column: param_Name
	 * Logical type: STRING
	 */
	public String getFormattedHTMLParamName() {
		return StringTool.escapeHTML( getFormattedParamName() );
	}
	// END Method getFormattedHTMLParamName


	// START Method getToStringParamName generated
	/**
	 * Maps DB-Column: param_Name
	 * Logical type: STRING
	 */
	public String getParamNameToString() {
		return StringTool.nullToEmpty(getParamName());
	}
	// END Method getToStringParamName


	// START Method setFromStringParamName generated
	/**
	 * Maps DB-Column: param_Name
	 * Logical type: STRING
	 */
	public void setParamNameFromString( String pParamName ) {
		setParamName( StringTool.emptyToNull( pParamName ) );
	}
	// END Method setFromStringParamName


	// START Method getColumnMetaDataParamName generated
	/**
	 * Maps DB-Column: param_Name
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForParamName() {
		return getBasicDCColumnMetaData( FIELD_PARAMNAME );
	}
	// END Method getColumnMetaDataParamName


	// START Method getParamType generated
	/**
	 * Maps DB-Column: param_Type
	 * Logical type: STRING
	 */
	public String getParamType() {
		return cParamType;
	}
	// END Method getParamType


	// START Method setParamType generated
	/**
	 * Maps DB-Column: param_Type
	 * Logical type: STRING
	 */
	public void setParamType( String pParamType ) {
		cParamType = StringTool.trimmedEmptyToNull( pParamType );
		forgetOriginalValue( FIELD_PARAMTYPE );
		touchField( FIELD_PARAMTYPE );
	}
	// END Method setParamType


	// START Method getFormattedParamType generated
	/**
	 * Maps DB-Column: param_Type
	 * Logical type: STRING
	 */
	public String getFormattedParamType() {
		return hasInvalidValue( FIELD_PARAMTYPE ) ? getOriginalValue( FIELD_PARAMTYPE ) : getPool().formatString( cParamType );
	}
	// END Method getFormattedParamType


	// START Method setFormattedParamType generated
	/**
	 * Maps DB-Column: param_Type
	 * Logical type: STRING
	 */
	public void setFormattedParamType( String pParamType )
		throws java.text.ParseException
	{
		setParamType( null );
		memorizeOriginalValue( FIELD_PARAMTYPE, pParamType );
		setParamType( getPool().parseString( pParamType ) );
	}
	// END Method setFormattedParamType


	// START Method getFormattedHTMLParamType generated
	/**
	 * Maps DB-Column: param_Type
	 * Logical type: STRING
	 */
	public String getFormattedHTMLParamType() {
		return StringTool.escapeHTML( getFormattedParamType() );
	}
	// END Method getFormattedHTMLParamType


	// START Method getToStringParamType generated
	/**
	 * Maps DB-Column: param_Type
	 * Logical type: STRING
	 */
	public String getParamTypeToString() {
		return StringTool.nullToEmpty(getParamType());
	}
	// END Method getToStringParamType


	// START Method setFromStringParamType generated
	/**
	 * Maps DB-Column: param_Type
	 * Logical type: STRING
	 */
	public void setParamTypeFromString( String pParamType ) {
		setParamType( StringTool.emptyToNull( pParamType ) );
	}
	// END Method setFromStringParamType


	// START Method getColumnMetaDataParamType generated
	/**
	 * Maps DB-Column: param_Type
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForParamType() {
		return getBasicDCColumnMetaData( FIELD_PARAMTYPE );
	}
	// END Method getColumnMetaDataParamType


	// START Method getParamStringvalue generated
	/**
	 * Maps DB-Column: param_Stringvalue
	 * Logical type: STRING
	 */
	public String getParamStringvalue() {
		return cParamStringvalue;
	}
	// END Method getParamStringvalue


	// START Method setParamStringvalue generated
	/**
	 * Maps DB-Column: param_Stringvalue
	 * Logical type: STRING
	 */
	public void setParamStringvalue( String pParamStringvalue ) {
		cParamStringvalue = StringTool.trimmedEmptyToNull( pParamStringvalue );
		forgetOriginalValue( FIELD_PARAMSTRINGVALUE );
		touchField( FIELD_PARAMSTRINGVALUE );
	}
	// END Method setParamStringvalue


	// START Method getFormattedParamStringvalue generated
	/**
	 * Maps DB-Column: param_Stringvalue
	 * Logical type: STRING
	 */
	public String getFormattedParamStringvalue() {
		return hasInvalidValue( FIELD_PARAMSTRINGVALUE ) ? getOriginalValue( FIELD_PARAMSTRINGVALUE ) : getPool().formatString( cParamStringvalue );
	}
	// END Method getFormattedParamStringvalue


	// START Method setFormattedParamStringvalue generated
	/**
	 * Maps DB-Column: param_Stringvalue
	 * Logical type: STRING
	 */
	public void setFormattedParamStringvalue( String pParamStringvalue )
		throws java.text.ParseException
	{
		setParamStringvalue( null );
		memorizeOriginalValue( FIELD_PARAMSTRINGVALUE, pParamStringvalue );
		setParamStringvalue( getPool().parseString( pParamStringvalue ) );
	}
	// END Method setFormattedParamStringvalue


	// START Method getFormattedHTMLParamStringvalue generated
	/**
	 * Maps DB-Column: param_Stringvalue
	 * Logical type: STRING
	 */
	public String getFormattedHTMLParamStringvalue() {
		return StringTool.escapeHTML( getFormattedParamStringvalue() );
	}
	// END Method getFormattedHTMLParamStringvalue


	// START Method getToStringParamStringvalue generated
	/**
	 * Maps DB-Column: param_Stringvalue
	 * Logical type: STRING
	 */
	public String getParamStringvalueToString() {
		return StringTool.nullToEmpty(getParamStringvalue());
	}
	// END Method getToStringParamStringvalue


	// START Method setFromStringParamStringvalue generated
	/**
	 * Maps DB-Column: param_Stringvalue
	 * Logical type: STRING
	 */
	public void setParamStringvalueFromString( String pParamStringvalue ) {
		setParamStringvalue( StringTool.emptyToNull( pParamStringvalue ) );
	}
	// END Method setFromStringParamStringvalue


	// START Method getColumnMetaDataParamStringvalue generated
	/**
	 * Maps DB-Column: param_Stringvalue
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForParamStringvalue() {
		return getBasicDCColumnMetaData( FIELD_PARAMSTRINGVALUE );
	}
	// END Method getColumnMetaDataParamStringvalue


	// START Method getParamNumbervalue generated
	/**
	 * Maps DB-Column: param_Numbervalue
	 * Logical type: Id
	 */
	public Long getParamNumbervalue() {
		return cParamNumbervalue;
	}
	// END Method getParamNumbervalue


	// START Method setParamNumbervalue generated
	/**
	 * Maps DB-Column: param_Numbervalue
	 * Logical type: Id
	 */
	public void setParamNumbervalue( Long pParamNumbervalue ) {
		cParamNumbervalue = pParamNumbervalue;
		forgetOriginalValue( FIELD_PARAMNUMBERVALUE );
		touchField( FIELD_PARAMNUMBERVALUE );
	}
	// END Method setParamNumbervalue


	// START Method getFormattedParamNumbervalue generated
	/**
	 * Maps DB-Column: param_Numbervalue
	 * Logical type: Id
	 */
	public String getFormattedParamNumbervalue() {
		return hasInvalidValue( FIELD_PARAMNUMBERVALUE ) ? getOriginalValue( FIELD_PARAMNUMBERVALUE ) : getPool().formatId( "Id", "#", getParamNumbervalue() );
	}
	// END Method getFormattedParamNumbervalue


	// START Method setFormattedParamNumbervalue generated
	/**
	 * Maps DB-Column: param_Numbervalue
	 * Logical type: Id
	 */
	public void setFormattedParamNumbervalue( String pParamNumbervalue )
		throws java.text.ParseException
	{
		setParamNumbervalue( null );
		memorizeOriginalValue( FIELD_PARAMNUMBERVALUE, pParamNumbervalue );
		setParamNumbervalue( getPool().parseId( "Id", "#", pParamNumbervalue ) );
	}
	// END Method setFormattedParamNumbervalue


	// START Method getFormattedHTMLParamNumbervalue generated
	/**
	 * Maps DB-Column: param_Numbervalue
	 * Logical type: Id
	 */
	public String getFormattedHTMLParamNumbervalue() {
		return StringTool.escapeHTML( getFormattedParamNumbervalue() );
	}
	// END Method getFormattedHTMLParamNumbervalue


	// START Method getToStringParamNumbervalue generated
	/**
	 * Maps DB-Column: param_Numbervalue
	 * Logical type: Id
	 */
	public String getParamNumbervalueToString() {
		return StringTool.nullToEmpty(getParamNumbervalue());
	}
	// END Method getToStringParamNumbervalue


	// START Method setFromStringParamNumbervalue generated
	/**
	 * Maps DB-Column: param_Numbervalue
	 * Logical type: Id
	 */
	public void setParamNumbervalueFromString( String pParamNumbervalue ) {
		setParamNumbervalue( NumberTool.parseLong( pParamNumbervalue, null ) );
	}
	// END Method setFromStringParamNumbervalue


	// START Method getColumnMetaDataParamNumbervalue generated
	/**
	 * Maps DB-Column: param_Numbervalue
	 * Logical type: Id
	 */
	public BasicDCColumnMetaData getColumnMetaDataForParamNumbervalue() {
		return getBasicDCColumnMetaData( FIELD_PARAMNUMBERVALUE );
	}
	// END Method getColumnMetaDataParamNumbervalue


	// START Method getParamDecimalvalue generated
	/**
	 * Maps DB-Column: param_Decimalvalue
	 * Logical type: Quantity
	 */
	public java.math.BigDecimal getParamDecimalvalue() {
		return cParamDecimalvalue;
	}
	// END Method getParamDecimalvalue


	// START Method setParamDecimalvalue generated
	/**
	 * Maps DB-Column: param_Decimalvalue
	 * Logical type: Quantity
	 */
	public void setParamDecimalvalue( java.math.BigDecimal pParamDecimalvalue ) {
		cParamDecimalvalue = pParamDecimalvalue;
		forgetOriginalValue( FIELD_PARAMDECIMALVALUE );
		touchField( FIELD_PARAMDECIMALVALUE );
	}
	// END Method setParamDecimalvalue


	// START Method getFormattedParamDecimalvalue generated
	/**
	 * Maps DB-Column: param_Decimalvalue
	 * Logical type: Quantity
	 */
	public String getFormattedParamDecimalvalue() {
		return hasInvalidValue( FIELD_PARAMDECIMALVALUE ) ? getOriginalValue( FIELD_PARAMDECIMALVALUE ) : getPool().formatDecimal("Quantity", ",##0.000", getParamDecimalvalue() );
	}
	// END Method getFormattedParamDecimalvalue


	// START Method setFormattedParamDecimalvalue generated
	/**
	 * Maps DB-Column: param_Decimalvalue
	 * Logical type: Quantity
	 */
	public void setFormattedParamDecimalvalue( String pParamDecimalvalue )
		throws java.text.ParseException
	{
		setParamDecimalvalue( null );
		memorizeOriginalValue( FIELD_PARAMDECIMALVALUE, pParamDecimalvalue );
		setParamDecimalvalue( getPool().parseDecimal( "Quantity", ",##0.000", pParamDecimalvalue ) );
	}
	// END Method setFormattedParamDecimalvalue


	// START Method getFormattedHTMLParamDecimalvalue generated
	/**
	 * Maps DB-Column: param_Decimalvalue
	 * Logical type: Quantity
	 */
	public String getFormattedHTMLParamDecimalvalue() {
		return StringTool.escapeHTML( getFormattedParamDecimalvalue() );
	}
	// END Method getFormattedHTMLParamDecimalvalue


	// START Method getToStringParamDecimalvalue generated
	/**
	 * Maps DB-Column: param_Decimalvalue
	 * Logical type: Quantity
	 */
	public String getParamDecimalvalueToString() {
		return StringTool.nullToEmpty(getParamDecimalvalue());
	}
	// END Method getToStringParamDecimalvalue


	// START Method setFromStringParamDecimalvalue generated
	/**
	 * Maps DB-Column: param_Decimalvalue
	 * Logical type: Quantity
	 */
	public void setParamDecimalvalueFromString( String pParamDecimalvalue ) {
		setParamDecimalvalue( NumberTool.parseBD( pParamDecimalvalue, null ) );
	}
	// END Method setFromStringParamDecimalvalue


	// START Method getColumnMetaDataParamDecimalvalue generated
	/**
	 * Maps DB-Column: param_Decimalvalue
	 * Logical type: Quantity
	 */
	public BasicDCColumnMetaData getColumnMetaDataForParamDecimalvalue() {
		return getBasicDCColumnMetaData( FIELD_PARAMDECIMALVALUE );
	}
	// END Method getColumnMetaDataParamDecimalvalue


	// START Method getParamBooleanvalue generated
	/**
	 * Maps DB-Column: param_Booleanvalue
	 * Logical type: BOOLEAN
	 */
	public Boolean getParamBooleanvalue() {
		return cParamBooleanvalue;
	}
	// END Method getParamBooleanvalue


	// START Method setParamBooleanvalue generated
	/**
	 * Maps DB-Column: param_Booleanvalue
	 * Logical type: BOOLEAN
	 */
	public void setParamBooleanvalue( Boolean pParamBooleanvalue ) {
		cParamBooleanvalue = pParamBooleanvalue;
		forgetOriginalValue( FIELD_PARAMBOOLEANVALUE );
		touchField( FIELD_PARAMBOOLEANVALUE );
	}
	// END Method setParamBooleanvalue


	// START Method getFormattedParamBooleanvalue generated
	/**
	 * Maps DB-Column: param_Booleanvalue
	 * Logical type: BOOLEAN
	 */
	public String getFormattedParamBooleanvalue() {
		return hasInvalidValue( FIELD_PARAMBOOLEANVALUE ) ? getOriginalValue( FIELD_PARAMBOOLEANVALUE ) : getPool().formatBoolean( cParamBooleanvalue );
	}
	// END Method getFormattedParamBooleanvalue


	// START Method setFormattedParamBooleanvalue generated
	/**
	 * Maps DB-Column: param_Booleanvalue
	 * Logical type: BOOLEAN
	 */
	public void setFormattedParamBooleanvalue( String pParamBooleanvalue )
		throws java.text.ParseException
	{
		setParamBooleanvalue( null );
		memorizeOriginalValue( FIELD_PARAMBOOLEANVALUE, pParamBooleanvalue );
		setParamBooleanvalue( getPool().parseBoolean( pParamBooleanvalue ) );
	}
	// END Method setFormattedParamBooleanvalue


	// START Method getFormattedHTMLParamBooleanvalue generated
	/**
	 * Maps DB-Column: param_Booleanvalue
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLParamBooleanvalue() {
		return StringTool.escapeHTML( getFormattedParamBooleanvalue() );
	}
	// END Method getFormattedHTMLParamBooleanvalue


	// START Method getToStringParamBooleanvalue generated
	/**
	 * Maps DB-Column: param_Booleanvalue
	 * Logical type: BOOLEAN
	 */
	public String getParamBooleanvalueToString() {
		return StringTool.nullToEmpty(getParamBooleanvalue());
	}
	// END Method getToStringParamBooleanvalue


	// START Method setFromStringParamBooleanvalue generated
	/**
	 * Maps DB-Column: param_Booleanvalue
	 * Logical type: BOOLEAN
	 */
	public void setParamBooleanvalueFromString( String pParamBooleanvalue ) {
		setParamBooleanvalue( BooleanTool.parseBoolean( pParamBooleanvalue, null ) );
	}
	// END Method setFromStringParamBooleanvalue


	// START Method getColumnMetaDataParamBooleanvalue generated
	/**
	 * Maps DB-Column: param_Booleanvalue
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForParamBooleanvalue() {
		return getBasicDCColumnMetaData( FIELD_PARAMBOOLEANVALUE );
	}
	// END Method getColumnMetaDataParamBooleanvalue


	// START Method getIsDeletable generated
	/**
	 * Maps DB-Column: is_Deletable
	 * Logical type: BOOLEAN
	 */
	public Boolean getIsDeletable() {
		return cIsDeletable;
	}
	// END Method getIsDeletable


	// START Method setIsDeletable generated
	/**
	 * Maps DB-Column: is_Deletable
	 * Logical type: BOOLEAN
	 */
	public void setIsDeletable( Boolean pIsDeletable ) {
		cIsDeletable = pIsDeletable;
		forgetOriginalValue( FIELD_ISDELETABLE );
		touchField( FIELD_ISDELETABLE );
	}
	// END Method setIsDeletable


	// START Method getFormattedIsDeletable generated
	/**
	 * Maps DB-Column: is_Deletable
	 * Logical type: BOOLEAN
	 */
	public String getFormattedIsDeletable() {
		return hasInvalidValue( FIELD_ISDELETABLE ) ? getOriginalValue( FIELD_ISDELETABLE ) : getPool().formatBoolean( cIsDeletable );
	}
	// END Method getFormattedIsDeletable


	// START Method setFormattedIsDeletable generated
	/**
	 * Maps DB-Column: is_Deletable
	 * Logical type: BOOLEAN
	 */
	public void setFormattedIsDeletable( String pIsDeletable )
		throws java.text.ParseException
	{
		setIsDeletable( null );
		memorizeOriginalValue( FIELD_ISDELETABLE, pIsDeletable );
		setIsDeletable( getPool().parseBoolean( pIsDeletable ) );
	}
	// END Method setFormattedIsDeletable


	// START Method getFormattedHTMLIsDeletable generated
	/**
	 * Maps DB-Column: is_Deletable
	 * Logical type: BOOLEAN
	 */
	public String getFormattedHTMLIsDeletable() {
		return StringTool.escapeHTML( getFormattedIsDeletable() );
	}
	// END Method getFormattedHTMLIsDeletable


	// START Method getToStringIsDeletable generated
	/**
	 * Maps DB-Column: is_Deletable
	 * Logical type: BOOLEAN
	 */
	public String getIsDeletableToString() {
		return StringTool.nullToEmpty(getIsDeletable());
	}
	// END Method getToStringIsDeletable


	// START Method setFromStringIsDeletable generated
	/**
	 * Maps DB-Column: is_Deletable
	 * Logical type: BOOLEAN
	 */
	public void setIsDeletableFromString( String pIsDeletable ) {
		setIsDeletable( BooleanTool.parseBoolean( pIsDeletable, null ) );
	}
	// END Method setFromStringIsDeletable


	// START Method getColumnMetaDataIsDeletable generated
	/**
	 * Maps DB-Column: is_Deletable
	 * Logical type: BOOLEAN
	 */
	public BasicDCColumnMetaData getColumnMetaDataForIsDeletable() {
		return getBasicDCColumnMetaData( FIELD_ISDELETABLE );
	}
	// END Method getColumnMetaDataIsDeletable


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
