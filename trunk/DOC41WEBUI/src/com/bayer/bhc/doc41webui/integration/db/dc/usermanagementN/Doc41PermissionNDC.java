/*
 * (c)2007 Bayer AG Leverkusen, Bayer Business Solutions
 * All rights reserved.
 * (based on DCGenenerator DC-Definitions: Id: DCGenerator.ini,v 1.82 2012/10/05 12:22:39 evfpu Exp )
 *
 * $Id$
 */
package com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN;


import java.util.*;

import com.bayer.ecim.foundation.basic.*;
import com.bayer.ecim.foundation.dbx.*;




/**
 * DC for UM_PERMISSIONS in BOE2_FDT
 */
public class Doc41PermissionNDC
	extends com.bayer.ecim.foundation.web.usermanagementN.UMPermissionNDC
{

	/** The master Class of this class. */
	public final Class<DataCarrier> MASTER_CLASS = DataCarrier.class;

	/** Set the database tablename (null if unspecified) for this DC. */
	static {
		setDBTablename( Doc41PermissionNDC.class, "[UM].UM_PERMISSIONS" );
	}

	private static final long serialVersionUID = 20130409034042286L;
	protected static final Class<java.math.BigDecimal> _BD_CLASS_DOC41PERMISSIONNDC = java.math.BigDecimal.class;

	@SuppressWarnings("unchecked")
	private static final HashMap<String,BasicDCFieldMeta> FIELD_META = new HashMap<String,BasicDCFieldMeta>( com.bayer.ecim.foundation.web.usermanagementN.UMPermissionNDC.localGetFieldMetaMap() );
	private static final String[] LOCAL_FIELD_LIST = new String[] {};
	private static final String[] FIELD_LIST = StringTool.merge(  com.bayer.ecim.foundation.web.usermanagementN.UMPermissionNDC.localGetFieldList(), LOCAL_FIELD_LIST );

	static {
		try {
		} catch ( Exception e ) {
			throw new InitException( "Failed to detect return types of the getter methods of Doc41PermissionNDC!", null );
		}
	}

	/**
	 * The Constructor.
	 * (a)deprecated Use: static Doc41PermissionNDC newInstanceOfDoc41PermissionNDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public Doc41PermissionNDC() {
		super( (Boolean)null );
	}

	/**
	 * Temporary Constructor.
	 */
	protected Doc41PermissionNDC( Boolean pVal ) {
		super( pVal );
		pVal = null;
	}

	/**
	 * The Copy Constructor.
	 * (a)deprecated Use: static Doc41PermissionNDC newInstanceOfDoc41PermissionNDC()  instead, supports replacement by subclass!!! Constructor will once change from public to protected!!!
	 */
	public Doc41PermissionNDC( BasicDataCarrier pDC )
		throws InitException
	{
		super( (Boolean)null );
		copyFrom( pDC );
	}
	

	/**
	 * Create a new instance, supports replacement by subclass!!!
	 * @throws InitException if the instanciation failes.
	 */
	public static Doc41PermissionNDC newInstanceOfDoc41PermissionNDC() throws InitException {
		return (Doc41PermissionNDC)newInstanceOf( Doc41PermissionNDC.class );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pLoc java.util.Locale the Locale to localize the new instance to, automatically.
	 * @throws InitException if the instanciation failes.
	 */
	public static Doc41PermissionNDC newInstanceOfDoc41PermissionNDC( Locale pLoc ) throws InitException {
		return (Doc41PermissionNDC)localizeDC( newInstanceOfDoc41PermissionNDC(), pLoc );
	}

	/**
	 * Create a new instance, supports replacement by subclass, with automatic localization!!!
	 * @param pDC dc to copy the attributes from.
	 * @throws InitException if the instanciation failes.
	 */
	public static Doc41PermissionNDC newInstanceOfDoc41PermissionNDC( BasicDataCarrier pDC ) throws InitException {
		return (Doc41PermissionNDC)newInstanceOfDoc41PermissionNDC().copyFrom( pDC );
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
	public static final int CHECK_DOC41PERMISSIONNDC = com.bayer.ecim.foundation.web.usermanagementN.UMPermissionNDC.CHECK_UMPERMISSIONNDC;

	/**
	 * Copy the parameter DC pOther into this.
	 */
	@SuppressWarnings("unchecked")

	public BasicDataCarrier copyFrom(BasicDataCarrier pOther) throws InitException {
		super.copyFrom(pOther);
		if (Doc41PermissionNDC.class.isAssignableFrom(pOther.getClass())) {
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
			"com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN",
			"Doc41PermissionNDC",
			"com.bayer.ecim.foundation.web.usermanagementN.UMPermissionNDC",
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
		return Doc41PermissionNDC.localGetCVSMeta();
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
		return Doc41PermissionNDC.localGetFieldMeta( pFieldName );
	}

	/**
	 * Provides a field list of all fields that are supported by the DC.
	 */
	public static String[] localGetFieldList() {
		return FIELD_LIST;
	}


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
