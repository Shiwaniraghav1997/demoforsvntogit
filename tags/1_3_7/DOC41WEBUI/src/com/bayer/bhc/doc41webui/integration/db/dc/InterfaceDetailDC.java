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
public class   InterfaceDetailDC
       extends com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier {

	/** Maps DB-Column: interface_Name */
	String cInterfaceName;
	public static final String FIELD_INTERFACENAME = "InterfaceName";

	/** Maps DB-Column: interface_Description */
	String cInterfaceDescription;
	public static final String FIELD_INTERFACEDESCRIPTION = "InterfaceDescription";

	/** for compatibility: dummy variable to suppress import warning...*/
	public static final Class<DataCarrier> _DBX_DC_CLASS_INTERFACEDETAILDC = DataCarrier.class;

	/** The master Class of this class. */
	public static final Class<BasicDataCarrier> MASTER_CLASS_INTERFACEDETAILDC = BasicDataCarrier.class;

	/** Set the database table-name (null if unspecified) for this DC. */
	static {
		setDBTablename( InterfaceDetailDC.class, "[DOC41WEB_MGR].IM_INTERFACE_DETAIL" );
	}

	private static final long serialVersionUID = 20180319062713521L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_INTERFACEDETAILDC = java.math.BigDecimal.class;

	private static final HashMap<String,BasicDCFieldMeta> FIELD_META       = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldMetaMap() );
	private static final String[]                         LOCAL_FIELD_LIST = new String[] {FIELD_INTERFACENAME, FIELD_INTERFACEDESCRIPTION};
	private static final String[]                         FIELD_LIST       = StringTool.merge( com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.localGetFieldList(), LOCAL_FIELD_LIST );

	/** Regeneration check for subclasses. If this line causes an error, you forgot to regenerate the direct superclass */
	public static final int CHECK_INTERFACEDETAILDC = com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier.CHECK_USERCHANGEABLEDATACARRIER;

	static {
		try {
			FIELD_META.put( FIELD_INTERFACENAME,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
			FIELD_META.put( FIELD_INTERFACEDESCRIPTION,	new BasicDCFieldMeta( "STRINGS",	"STRING",	String.class,	null ) );
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of InterfaceDetailDC!", e );
		}
	}

	/**
	 * The standard Constructor (Framework internal: do not use in applications!).
	 * @deprecated Use: static InterfaceDetailDC newInstanceOfInterfaceDetailDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public InterfaceDetailDC() {
		super( (Boolean)null );
	}

	/**
	 * Constructor for class-hierarchy (Framework internal: do not use in applications!).
	 */
	protected InterfaceDetailDC( Boolean pVal ) {
		super( pVal );
	}

	/**
	 * The Copy Constructor.
	 * @deprecated Use: static InterfaceDetailDC newInstanceOfInterfaceDetailDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	@Deprecated
	public InterfaceDetailDC( BasicDataCarrier pDC ) {
		super( (Boolean)null );
		copyFrom( pDC );
	}


	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * throws an InitException if the instantiation fails.
	 */
	public static InterfaceDetailDC newInstanceOfInterfaceDetailDC() {
		return newInstanceOf( InterfaceDetailDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 */
	public static InterfaceDetailDC newInstanceOfInterfaceDetailDC( Locale pLoc ) {
		return localizeDC( newInstanceOfInterfaceDetailDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * throws an InitException if the instantiation fails.
	 * @param pDC DC to copy the attributes from.
	 */
	public static InterfaceDetailDC newInstanceOfInterfaceDetailDC( BasicDataCarrier pDC ) {
		return (InterfaceDetailDC)newInstanceOfInterfaceDetailDC().copyFrom( pDC );
	}

	/**
	 * Copy the parameter DC pOther into this.
	 */
	@Override
	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) {
		super.copyFrom(pOther);
		if (InterfaceDetailDC.class.isAssignableFrom(pOther.getClass())) {
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
			"InterfaceDetailDC",
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
		return InterfaceDetailDC.localGetCVSMeta();
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
		return InterfaceDetailDC.localGetFieldMeta( pFieldName );
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


	// START Method getInterfaceDescription generated
	/**
	 * Maps DB-Column: interface_Description
	 * Logical type: STRING
	 */
	public String getInterfaceDescription() {
		return cInterfaceDescription;
	}
	// END Method getInterfaceDescription


	// START Method setInterfaceDescription generated
	/**
	 * Maps DB-Column: interface_Description
	 * Logical type: STRING
	 */
	public void setInterfaceDescription( String pInterfaceDescription ) {
		cInterfaceDescription = StringTool.trimmedEmptyToNull( pInterfaceDescription );
		forgetOriginalValue( FIELD_INTERFACEDESCRIPTION );
		touchField( FIELD_INTERFACEDESCRIPTION );
	}
	// END Method setInterfaceDescription


	// START Method getFormattedInterfaceDescription generated
	/**
	 * Maps DB-Column: interface_Description
	 * Logical type: STRING
	 */
	public String getFormattedInterfaceDescription() {
		return hasInvalidValue( FIELD_INTERFACEDESCRIPTION ) ? getOriginalValue( FIELD_INTERFACEDESCRIPTION ) : getPool().formatString( cInterfaceDescription );
	}
	// END Method getFormattedInterfaceDescription


	// START Method setFormattedInterfaceDescription generated
	/**
	 * Maps DB-Column: interface_Description
	 * Logical type: STRING
	 */
	public void setFormattedInterfaceDescription( String pInterfaceDescription ) throws java.text.ParseException {
		setInterfaceDescription( null );
		memorizeOriginalValue( FIELD_INTERFACEDESCRIPTION, pInterfaceDescription );
		setInterfaceDescription( getPool().parseString( pInterfaceDescription ) );
	}
	// END Method setFormattedInterfaceDescription


	// START Method getFormattedHTMLInterfaceDescription generated
	/**
	 * Maps DB-Column: interface_Description
	 * Logical type: STRING
	 */
	public String getFormattedHTMLInterfaceDescription() {
		return StringTool.escapeHTML( getFormattedInterfaceDescription() );
	}
	// END Method getFormattedHTMLInterfaceDescription


	// START Method getToStringInterfaceDescription generated
	/**
	 * Maps DB-Column: interface_Description
	 * Logical type: STRING
	 */
	public String getInterfaceDescriptionToString() {
		return StringTool.nullToEmpty(getInterfaceDescription());
	}
	// END Method getToStringInterfaceDescription


	// START Method setFromStringInterfaceDescription generated
	/**
	 * Maps DB-Column: interface_Description
	 * Logical type: STRING
	 */
	public void setInterfaceDescriptionFromString( String pInterfaceDescription ) {
		setInterfaceDescription( StringTool.emptyToNull( pInterfaceDescription ) );
	}
	// END Method setFromStringInterfaceDescription


	// START Method getColumnMetaDataInterfaceDescription generated
	/**
	 * Maps DB-Column: interface_Description
	 * Logical type: STRING
	 */
	public BasicDCColumnMetaData getColumnMetaDataForInterfaceDescription() {
		return getBasicDCColumnMetaData( FIELD_INTERFACEDESCRIPTION );
	}
	// END Method getColumnMetaDataInterfaceDescription


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
