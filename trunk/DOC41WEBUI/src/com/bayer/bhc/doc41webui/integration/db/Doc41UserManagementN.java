/*
 * $Id: Doc41UserManagementN.java,v 1.0 2012/06/20 15:34:42 evfpu Exp $
 * Created on 20.06.2012
 *
 */
package com.bayer.bhc.doc41webui.integration.db;

import java.util.ArrayList;
import java.util.Locale;

import com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN.Doc41PGUPermissionNDC;
import com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN.Doc41PermissionNDC;
import com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN.Doc41ProfileNDC;
import com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN.Doc41UserNDC;
import com.bayer.bhc.doc41webui.integration.db.dc.usermanagementN.Doc41UserProfileNDC;
import com.bayer.ecim.foundation.basic.BooleanTool;
import com.bayer.ecim.foundation.basic.Dbg;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.ReflectTool;
import com.bayer.ecim.foundation.basic.SQLStringTool;
import com.bayer.ecim.foundation.dbx.DBObjectAccess;
import com.bayer.ecim.foundation.dbx.DBTransactionServer;
import com.bayer.ecim.foundation.dbx.QueryException;
import com.bayer.ecim.foundation.dbx.ResultObject;
import com.bayer.ecim.foundation.web.usermanagementN.OTUserManagementN;


/**
 * 
 * This is a customized copy of {@link com.bayer.ecim.foundation.web.usermanagementN.OTUserManagementN}
 * @author EVFPU
 * 
 */
public class Doc41UserManagementN extends OTUserManagementN {


    /**
     * @param pSingletonId
     * @throws InitException
     */
    public Doc41UserManagementN(String pSingletonId) throws InitException {
        super(pSingletonId);
        try {
            dbg0 = Dbg.get().getChannelByName("USRB_DBG0", "USRB_DBG0", "usr.sl.dbg0", true);
            dbg1 = Dbg.get().getChannelByName("USRB_DBG1", "USRB_DBG1", "usr.sl.dbg1", false);
            dbg2 = Dbg.get().getChannelByName("USRB_DBG2", "USRB_DBG2", "usr.sl.dbg2", false);
            cCache.setLogChannel( dbg1 );
            registerDC( Doc41UserNDC.class );
            registerDC( Doc41ProfileNDC.class );
            registerDC( Doc41PermissionNDC.class );
            registerDC( Doc41UserProfileNDC.class );
            registerDC( Doc41PGUPermissionNDC.class );
            DBObjectAccess.get().checkModuleRequiredVersion( "Foundation-UserMgmt", 1, 24 );
            initSucceeded( Doc41UserManagementN.class );
        } catch (Exception mEx) {
            initFailed( new InitException( "Failed to initialize " + cClassName + "!", mEx) );
		} catch (Error mErr) {
			initFailed( mErr );
        }
    }


    /**
     * Getter methode with fallback, if no instanciation class was defined to Singleton by properties.
     * @return
     * @throws InitException
     */
    public static Doc41UserManagementN get()
        throws InitException
    {
    	Doc41UserManagementN mSing = (Doc41UserManagementN)getSingleton(ID);
        return (mSing != null) ? mSing : new Doc41UserManagementN(ID);
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getUserByCWID(java.lang.String, java.util.Locale)
	 */
    @Override
	public Doc41UserNDC getUserByCWID( String pCWID, Locale pLoc ) throws QueryException {
    	Doc41UserNDC mRet = null;
        checkObligatoryParameter( pCWID, "pCWID", "getUserByCWID( pCWID, pLoc )" );
        mRet = (Doc41UserNDC)cCache.getSync( "UC|" + pCWID );
        if ( mRet != null )
            mRet.localize( pLoc );
        else {
            ResultObject mRes = getUsers( pCWID, null, null, null, null, null, null, null, -1, -1, null, null, null, null, pLoc );
            mRet = (Doc41UserNDC)cCache.putSync((mRes.getResult().size() == 0) ? null : mRes.getResult().get( 0 ));
        }
        return mRet;
    }
    
    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getUserByObjectId(java.lang.Long, java.util.Locale)
	 */
    @Override
	public Doc41UserNDC getUserByObjectId( Long pObjectId, Locale pLoc ) throws QueryException {
    	Doc41UserNDC mRet = null;
        checkObligatoryParameter( pObjectId, "pObjectId", "getUserByObjectId( pObjectId, pLoc )" );
        mRet = (Doc41UserNDC)cCache.getSync( pObjectId );
        if ( mRet != null )
            mRet.localize( pLoc );
        else {
            ResultObject mRes = getUsers( null, pObjectId, null, null, null, null, null, null, -1, -1, null, null, null, null, pLoc );
            mRet = (Doc41UserNDC)cCache.putSync((mRes.getResult().size() == 0) ? null : mRes.getResult().get( 0 ));
        }
        return mRet;
    }
    

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getUsers(java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Boolean, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.util.Locale)
	 */
    @Override
	public ResultObject getUsers( String pCWID, Long pUserId, String pFirstName, String pLastName, String pEmail, Long pObjectStateId, Long pObjectStateIntId, Boolean pIsExternal,
            int pFromIdx, int pToIdx, String pQuick, String pRegChar, String pOrderBy, Long pLastCount, Locale pLoc ) throws QueryException {
        final String METHODE = "ResultObject getUsers( String pCWID, Long pUserId, String pFirstName, String pLastName, String pEmail, Long pObjectStateId, Long pObjectStateIntId, Boolean pIsExternal, int pFromIdx, int pToIdx, String pQuick, String pRegChar, String pOrderBy, Long pLastCount, Locale pLoc )";
        return localizeDC(
                queryResultWithPagingAutoMode( DBTransactionServer.NOTRX, Doc41UserNDC.class, METHODE, pFromIdx, pToIdx, "getUsers",
                        
                        new String[] {
                            "COUNT",
                            "CWID",
                            "UID",
                            "FIRSTNAME",
                            "LASTNAME",
                            "EMAIL",
                            "OBJSTATE",
                            "OBJSTATEINT",
                            "ISEXTERNAL",
                            "QUICK",
                            "REGCHAR",
                        },
                            
                        new Object[] {
                            "*",
                            SQLStringTool.escape( pCWID ),
                            pUserId,
                            SQLStringTool.escape( pFirstName ),
                            SQLStringTool.escape( pLastName ),
                            SQLStringTool.escape( pEmail ),
                            pObjectStateId,
                            pObjectStateIntId,
                            BooleanTool.toSQLTemplValue( pIsExternal ),
                            SQLStringTool.escape( pQuick ),
                            SQLStringTool.escape( pRegChar )
                        },
                        
                        new String[] {
                            "COUNT",
                            "CWID",
                            "UID",
                            "FIRSTNAME",
                            "LASTNAME",
                            "EMAIL",
                            "OBJSTATE",
                            "OBJSTATEINT",
                            "ISEXTERNAL",
                            "QUICK",
                            "REGCHAR",
                            "FROM_IDX",
                            "TO_IDX",
                            "ORDERBY"
                        },
                                    
                        new Object[] {
                            null,
                            SQLStringTool.escape( pCWID ),
                            pUserId,
                            SQLStringTool.escape( pFirstName ),
                            SQLStringTool.escape( pLastName ),
                            SQLStringTool.escape( pEmail ),
                            pObjectStateId,
                            pObjectStateIntId,
                            BooleanTool.toSQLTemplValue( pIsExternal ),
                            SQLStringTool.escape( pQuick ),
                            SQLStringTool.escape( pRegChar ),
                            (pFromIdx < 0) ? null : new Integer( pFromIdx ),
                            (pToIdx   < 0) ? null : new Integer( pToIdx+1 ),
                            SQLStringTool.escape( pOrderBy )
                        },
                        
                        pLastCount ), pLoc );
    }


    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getUsersByGroup(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Boolean, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.util.Locale)
	 */
    @Override
	public ResultObject getUsersByGroup( Long pGroupId, String pFirstName, String pLastName, String pEmail, Long pObjectStateId, Boolean pIsExternal,
            int pFromIdx, int pToIdx, String pQuick, String pRegChar, String pOrderBy, Long pLastCount, Locale pLoc ) throws QueryException {
        final String METHODE = "ResultObject getUsersByGroup( Long pGroupId, String pFirstName, String pLastName, String pEmail, Long pObjectStateId, Boolean pIsExternal, int pFromIdx, int pToIdx, String pQuick, String pRegChar, String pOrderBy, Long pLastCount, Locale pLoc )";
        checkObligatoryParameter( pGroupId, "pGroupId", METHODE );
        return localizeDC(
                queryResultWithPagingAutoMode( DBTransactionServer.NOTRX, Doc41UserNDC.class, METHODE, pFromIdx, pToIdx, "getUsersByGroup",
                        
                        new String[] {
                            "COUNT",
                            "GROUPID",
                            "FIRSTNAME",
                            "LASTNAME",
                            "EMAIL",
                            "OBJSTATE",
                            "ISEXTERNAL",
                            "QUICK",
                            "REGCHAR",
                        },
                            
                        new Object[] {
                            "*",
                            pGroupId,
                            SQLStringTool.escape( pFirstName ),
                            SQLStringTool.escape( pLastName ),
                            SQLStringTool.escape( pEmail ),
                            pObjectStateId,
                            BooleanTool.toSQLTemplValue( pIsExternal ),
                            SQLStringTool.escape( pQuick ),
                            SQLStringTool.escape( pRegChar )
                        },
                        
                        new String[] {
                            "COUNT",
                            "GROUPID",
                            "FIRSTNAME",
                            "LASTNAME",
                            "EMAIL",
                            "OBJSTATE",
                            "ISEXTERNAL",
                            "QUICK",
                            "REGCHAR",
                            "FROM_IDX",
                            "TO_IDX",
                            "ORDERBY"
                        },
                                    
                        new Object[] {
                            null,
                            pGroupId,
                            SQLStringTool.escape( pFirstName ),
                            SQLStringTool.escape( pLastName ),
                            SQLStringTool.escape( pEmail ),
                            pObjectStateId,
                            BooleanTool.toSQLTemplValue( pIsExternal ),
                            SQLStringTool.escape( pQuick ),
                            SQLStringTool.escape( pRegChar ),
                            (pFromIdx < 0) ? null : new Integer( pFromIdx ),
                            (pToIdx   < 0) ? null : new Integer( pToIdx+1 ),
                            SQLStringTool.escape( pOrderBy )
                        },
                        
                        pLastCount ), pLoc );
    }

    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getUsersByGroup(java.lang.Long, java.lang.Long, java.lang.Boolean, java.lang.String, java.util.Locale)
	 */
    @Override
	@SuppressWarnings("unchecked")
	public ArrayList<Doc41UserNDC> getUsersByGroup( Long pGroupId, Long pObjectStateId, Boolean pIsExternal, String pOrderBy, Locale pLoc ) throws QueryException {
        final String METHODE = "ArrayList getUsersByGroup( Long pGroupId, Long pObjectStateId, Boolean pIsExternal, String pOrderBy, Locale pLoc )";
        checkObligatoryParameter( pGroupId, "pGroupId", METHODE );
        return getUsersByGroup( pGroupId, null, null, null, pObjectStateId, pIsExternal, -1, -1, null, null, pOrderBy, null, pLoc ).getResult();
    }


    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getUsersByProfile(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Boolean, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.util.Locale)
	 */
    @Override
	public ResultObject getUsersByProfile( Long pProfileId, String pFirstName, String pLastName, String pEmail, Long pObjectStateId, Boolean pIsExternal,
            int pFromIdx, int pToIdx, String pQuick, String pRegChar, String pOrderBy, Long pLastCount, Locale pLoc ) throws QueryException {
        final String METHODE = "ResultObject getUsersByProfile( Long pProfileId, String pFirstName, String pLastName, String pEmail, Long pObjectStateId, Boolean pIsExternal, int pFromIdx, int pToIdx, String pQuick, String pRegChar, String pOrderBy, Long pLastCount, Locale pLoc )";
        checkObligatoryParameter( pProfileId, "pProfileId", METHODE );
        return localizeDC(
                queryResultWithPagingAutoMode( DBTransactionServer.NOTRX, Doc41UserNDC.class, METHODE, pFromIdx, pToIdx, "getUsersByProfile",
                        
                        new String[] {
                            "COUNT",
                            "PROFILEID",
                            "FIRSTNAME",
                            "LASTNAME",
                            "EMAIL",
                            "OBJSTATE",
                            "ISEXTERNAL",
                            "QUICK",
                            "REGCHAR",
                        },
                            
                        new Object[] {
                            "*",
                            pProfileId,
                            SQLStringTool.escape( pFirstName ),
                            SQLStringTool.escape( pLastName ),
                            SQLStringTool.escape( pEmail ),
                            pObjectStateId,
                            BooleanTool.toSQLTemplValue( pIsExternal ),
                            SQLStringTool.escape( pQuick ),
                            SQLStringTool.escape( pRegChar )
                        },
                        
                        new String[] {
                            "COUNT",
                            "PROFILEID",
                            "FIRSTNAME",
                            "LASTNAME",
                            "EMAIL",
                            "OBJSTATE",
                            "ISEXTERNAL",
                            "QUICK",
                            "REGCHAR",
                            "FROM_IDX",
                            "TO_IDX",
                            "ORDERBY"
                        },
                                    
                        new Object[] {
                            null,
                            pProfileId,
                            SQLStringTool.escape( pFirstName ),
                            SQLStringTool.escape( pLastName ),
                            SQLStringTool.escape( pEmail ),
                            pObjectStateId,
                            BooleanTool.toSQLTemplValue( pIsExternal ),
                            SQLStringTool.escape( pQuick ),
                            SQLStringTool.escape( pRegChar ),
                            (pFromIdx < 0) ? null : new Integer( pFromIdx ),
                            (pToIdx   < 0) ? null : new Integer( pToIdx+1 ),
                            SQLStringTool.escape( pOrderBy )
                        },
                        
                        pLastCount ), pLoc );
    }
    
    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getUsersByProfile(java.lang.Long, java.lang.Long, java.lang.Boolean, java.lang.String, java.util.Locale)
	 */
    @Override
	@SuppressWarnings("unchecked")
	public ArrayList<Doc41UserNDC> getUsersByProfile( Long pProfileId, Long pObjectStateId, Boolean pIsExternal, String pOrderBy, Locale pLoc ) throws QueryException {
        final String METHODE = "ArrayList getUsersByProfile( Long pProfileId, Long pObjectStateId, Boolean pIsExternal, String pOrderBy, Locale pLoc )";
        checkObligatoryParameter( pProfileId, "pProfileId", METHODE );
        return getUsersByProfile( pProfileId, null, null, null, pObjectStateId, pIsExternal, -1, -1, null, null, pOrderBy, null, pLoc ).getResult();
    }

    
    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getUsersByPermission(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Boolean, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.util.Locale)
	 */
    @Override
	public ResultObject getUsersByPermission( Long pPermissionId, String pFirstName, String pLastName, String pEmail, Long pObjectStateId, Boolean pIsExternal,
            int pFromIdx, int pToIdx, String pQuick, String pRegChar, String pOrderBy, Long pLastCount, Locale pLoc ) throws QueryException {
        final String METHODE = "ResultObject getUsersByPermission( Long pPermissionId, String pFirstName, String pLastName, String pEmail, Long pObjectStateId, Boolean pIsExternal, int pFromIdx, int pToIdx, String pQuick, String pRegChar, String pOrderBy, Long pLastCount, Locale pLoc )";
        checkObligatoryParameter( pPermissionId, "pPermissionId", METHODE );
        return localizeDC(
                queryResultWithPagingAutoMode( DBTransactionServer.NOTRX, Doc41UserNDC.class, METHODE, pFromIdx, pToIdx, "getUsersByPermission",
                        
                        new String[] {
                            "COUNT",
                            "PERMISSIONID",
                            "FIRSTNAME",
                            "LASTNAME",
                            "EMAIL",
                            "OBJSTATE",
                            "ISEXTERNAL",
                            "QUICK",
                            "REGCHAR",
                        },
                            
                        new Object[] {
                            "*",
                            pPermissionId,
                            SQLStringTool.escape( pFirstName ),
                            SQLStringTool.escape( pLastName ),
                            SQLStringTool.escape( pEmail ),
                            pObjectStateId,
                            BooleanTool.toSQLTemplValue( pIsExternal ),
                            SQLStringTool.escape( pQuick ),
                            SQLStringTool.escape( pRegChar )
                        },
                        
                        new String[] {
                            "COUNT",
                            "PERMISSIONID",
                            "FIRSTNAME",
                            "LASTNAME",
                            "EMAIL",
                            "OBJSTATE",
                            "ISEXTERNAL",
                            "QUICK",
                            "REGCHAR",
                            "FROM_IDX",
                            "TO_IDX",
                            "ORDERBY"
                        },
                                    
                        new Object[] {
                            null,
                            pPermissionId,
                            SQLStringTool.escape( pFirstName ),
                            SQLStringTool.escape( pLastName ),
                            SQLStringTool.escape( pEmail ),
                            pObjectStateId,
                            BooleanTool.toSQLTemplValue( pIsExternal ),
                            SQLStringTool.escape( pQuick ),
                            SQLStringTool.escape( pRegChar ),
                            (pFromIdx < 0) ? null : new Integer( pFromIdx ),
                            (pToIdx   < 0) ? null : new Integer( pToIdx+1 ),
                            SQLStringTool.escape( pOrderBy )
                        },
                        
                        pLastCount ), pLoc );
    }

    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getUsersByPermission(java.lang.Long, java.lang.Long, java.lang.Boolean, java.lang.String, java.util.Locale)
	 */
    @Override
	@SuppressWarnings("unchecked")
	public ArrayList<Doc41UserNDC> getUsersByPermission( Long pPermissionId, Long pObjectStateId, Boolean pIsExternal, String pOrderBy, Locale pLoc ) throws QueryException {
        final String METHODE = "ArrayList getUsersByPermission( Long pPermissionId, Long pObjectStateId, Boolean pIsExternal, String pOrderBy, Locale pLoc )";
        checkObligatoryParameter( pPermissionId, "pPermissionId", METHODE );
        return getUsersByPermission( pPermissionId, null, null, null, pObjectStateId, pIsExternal, -1, -1, null, null, pOrderBy, null, pLoc ).getResult();
    }


    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getProfiles(java.lang.Long, java.lang.Boolean, java.lang.String, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.util.Locale)
	 */
    @Override
	public ResultObject getProfiles( Long pProfileId, Boolean pIsExternal, String pName,
            int pFromIdx, int pToIdx, String pQuick, String pRegChar, String pOrderBy, Long pLastCount, Locale pLoc ) throws QueryException {
        final String METHODE = "ResultObject getProfiles( Long pProfileId, Boolean pIsExternal, String pName, int pFromIdx, int pToIdx, String pQuick, String pRegChar, String pOrderBy, Long pLastCount, Locale pLoc )";
        return localizeDC(
                queryResultWithPagingAutoMode( DBTransactionServer.NOTRX, Doc41ProfileNDC.class, METHODE, pFromIdx, pToIdx, "getProfiles",
                   
                        new String[] {
                            "COUNT",
                            "PROFILEID",
                            "ISEXTERNAL",
                            "NAME",
                            "QUICK",
                            "REGCHAR",
                       },
                       
                       new Object[] {
                            "*",
                            pProfileId,
                            BooleanTool.toSQLTemplValue( pIsExternal ),
                            SQLStringTool.escape( pName ),
                            SQLStringTool.escape( pQuick ),
                            SQLStringTool.escape( pRegChar )
                       },
                   
                       new String[] {
                            "COUNT",
                            "PROFILEID",
                            "ISEXTERNAL",
                            "NAME",
                            "QUICK",
                            "REGCHAR",
                            "FROM_IDX",
                            "TO_IDX",
                            "ORDERBY"
                       },
                               
                       new Object[] {
                            null,
                            pProfileId,
                            BooleanTool.toSQLTemplValue( pIsExternal ),
                            SQLStringTool.escape( pName ),
                            SQLStringTool.escape( pQuick ),
                            SQLStringTool.escape( pRegChar ),
                            (pFromIdx < 0) ? null : new Integer( pFromIdx ),
                            (pToIdx   < 0) ? null : new Integer( pToIdx+1 ),
                            SQLStringTool.escape( pOrderBy )
                       },
                   
                       pLastCount ), pLoc );
    }
    
    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getProfilesByPermission(java.lang.Long, java.lang.Boolean, java.lang.String, java.util.Locale)
	 */
    @Override
	@SuppressWarnings("unchecked")
	public ArrayList<Doc41ProfileNDC> getProfilesByPermission( Long pPermissionId, Boolean pIsExternal, String pOrderBy, Locale pLoc ) throws QueryException {
        final String METHODE = "ArrayList getProfilesByPermission( Long pPermissionId, Boolean pIsExternal, String pOrderBy, Locale pLoc )";
        checkObligatoryParameter( pPermissionId, "pPermissionId", METHODE );
        return localizeDC(
                queryDCs( DBTransactionServer.NOTRX, Doc41ProfileNDC.class, METHODE, "getProfilesByPermission",

                    new String[] {
                        "PERMISSIONID",
                        "ISEXTERNAL",
                        "ORDERBY"
                    },
                                
                    new Object[] {
                        pPermissionId,
                        BooleanTool.toSQLTemplValue( pIsExternal ),
                        SQLStringTool.escape( pOrderBy )
                    }
                
                ), pLoc );
    }
    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getPermissions(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.util.Locale)
	 */
    @Override
	public ResultObject getPermissions( Long pPermissionId, String pCode, String pType, String pName, Boolean pAssignUser, Boolean pAssignGroup, Boolean pAssignProfile,
            int pFromIdx, int pToIdx, String pQuick, String pRegChar, String pOrderBy, Long pLastCount, Locale pLoc ) throws QueryException {
            final String METHODE = "ResultObject getPermissions( Long pPermissionId, String pCode, String pType, String pName, Boolean pAssignUser, Boolean pAssignGroup, Boolean pAssignProfile, int pFromIdx, int pToIdx, String pQuick, String pRegChar, String pOrderBy, Long pLastCount, Locale pLoc )";
                return localizeDC(
                        queryResultWithPagingAutoMode( DBTransactionServer.NOTRX, Doc41PermissionNDC.class, METHODE, pFromIdx, pToIdx, "getPermissions",
                                 
                                new String[] {
                                    "COUNT",
                                    "PERMISSIONID",
                                    "CODE",
                                    "TYPE",
                                    "NAME",
                                    "ASSIGNUSER",
                                    "ASSIGNGROUP",
                                    "ASSIGNPROFILE",
                                    "QUICK",
                                    "REGCHAR",
                                 },
                                     
                                 new Object[] {
                                     "*",
                                     pPermissionId,
                                     SQLStringTool.escape( pCode ),
                                     SQLStringTool.escape( pType ),
                                     SQLStringTool.escape( pName ),
                                     BooleanTool.toSQLTemplValue( pAssignUser ),
                                     BooleanTool.toSQLTemplValue( pAssignGroup ),
                                     BooleanTool.toSQLTemplValue( pAssignProfile ),
                                     SQLStringTool.escape( pQuick ),
                                     SQLStringTool.escape( pRegChar )
                                 },
                                 
                                 new String[] {
                                     "COUNT",
                                     "PERMISSIONID",
                                     "CODE",
                                     "TYPE",
                                     "NAME",
                                     "ASSIGNUSER",
                                     "ASSIGNGROUP",
                                     "ASSIGNPROFILE",
                                     "QUICK",
                                     "REGCHAR",
                                     "FROM_IDX",
                                     "TO_IDX",
                                     "ORDERBY"
                                 },
                                             
                                 new Object[] {
                                     null,
                                     pPermissionId,
                                     SQLStringTool.escape( pCode ),
                                     SQLStringTool.escape( pType ),
                                     SQLStringTool.escape( pName ),
                                     BooleanTool.toSQLTemplValue( pAssignUser ),
                                     BooleanTool.toSQLTemplValue( pAssignGroup ),
                                     BooleanTool.toSQLTemplValue( pAssignProfile ),
                                     SQLStringTool.escape( pQuick ),
                                     SQLStringTool.escape( pRegChar ),
                                     (pFromIdx < 0) ? null : new Integer( pFromIdx ),
                                     (pToIdx   < 0) ? null : new Integer( pToIdx+1 ),
                                     SQLStringTool.escape( pOrderBy )
                                 },
                                 
                                 pLastCount ), pLoc );
    }

    
    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getPermissionsByUser(java.lang.Long, java.lang.String, java.lang.String, java.util.Locale)
	 */
    @Override
	@SuppressWarnings("unchecked")
	public ArrayList<Doc41PermissionNDC> getPermissionsByUser( Long pUserId, String pType, String pOrderBy, Locale pLoc ) throws QueryException {
        final String METHODE = "ArrayList getPermissionsByUser( Long pUserId, String pType, String pOrderBy, Locale pLoc )";
        checkObligatoryParameter( pUserId, "pUserId", METHODE );
        return localizeDC(
                queryDCs( DBTransactionServer.NOTRX, Doc41PermissionNDC.class, METHODE, "getPermissionsByUser",

                    new String[] {
                        "USERID",
                        "TYPE",
                        "ORDERBY"
                    },
                                
                    new Object[] {
                        pUserId,
                        SQLStringTool.escape( pType ),
                        SQLStringTool.escape( pOrderBy )
                    }
                
                ), pLoc );
    }


    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getPermissionsByGroup(java.lang.Long, java.lang.String, java.lang.String, java.util.Locale)
	 */
    @Override
	@SuppressWarnings("unchecked")
	public ArrayList<Doc41PermissionNDC> getPermissionsByGroup( Long pGroupId, String pType, String pOrderBy, Locale pLoc ) throws QueryException {
        final String METHODE = "ArrayList getPermissionsByGroup( Long pGroupId, String pType, String pOrderBy, Locale pLoc )";
        checkObligatoryParameter( pGroupId, "pGroupId", METHODE );
        return localizeDC(
                queryDCs( DBTransactionServer.NOTRX, Doc41PermissionNDC.class, METHODE, "getPermissionsByGroup",

                    new String[] {
                        "GROUPID",
                        "TYPE",
                        "ORDERBY"
                    },
                                
                    new Object[] {
                        pGroupId,
                        SQLStringTool.escape( pType ),
                        SQLStringTool.escape( pOrderBy )
                    }
                
                ), pLoc );
    }


    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getPermissionsByProfile(java.lang.Long, java.lang.String, java.lang.String, java.util.Locale)
	 */
    @Override
	@SuppressWarnings("unchecked")
	public ArrayList<Doc41PermissionNDC> getPermissionsByProfile( Long pProfileId, String pType, String pOrderBy, Locale pLoc ) throws QueryException {
        final String METHODE = "ArrayList getPermissionsByProfile( Long pProfileId, String pType, String pOrderBy, Locale pLoc )";
        checkObligatoryParameter( pProfileId, "pProfileId", METHODE );
        return localizeDC(
                queryDCs( DBTransactionServer.NOTRX, Doc41PermissionNDC.class, METHODE, "getPermissionsByProfile",

                    new String[] {
                        "PROFILEID",
                        "TYPE",
                        "ORDERBY"
                    },
                                
                    new Object[] {
                        pProfileId,
                        SQLStringTool.escape( pType ),
                        SQLStringTool.escape( pOrderBy )
                    }
                
                ), pLoc );
    }


    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#getResolvedUserPermissions(java.lang.Long, java.lang.String, java.lang.String, java.util.Locale)
	 */
    @Override
	@SuppressWarnings("unchecked")
	public ArrayList<Doc41PermissionNDC> getResolvedUserPermissions( Long pUserId, String pType, String pOrderBy, Locale pLoc ) throws QueryException {
        final String METHODE = "ArrayList getResolvedUserPermissions( Long pUserId, String pType, String pOrderBy, Locale pLoc )";
        checkObligatoryParameter( pUserId, "pUserId", METHODE );
        return localizeDC(
                queryDCs( DBTransactionServer.NOTRX, Doc41PermissionNDC.class, METHODE, "getResolvedUserPermissions",

                    new String[] {
                        "USERID",
                        "TYPE",
                        "ORDERBY"
                    },
                                
                    new Object[] {
                        pUserId,
                        SQLStringTool.escape( pType ),
                        SQLStringTool.escape( pOrderBy )
                    }
                
                ), pLoc );
    }


    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#createUserDC(java.util.Locale)
	 */
    @Override
	public Doc41UserNDC createUserDC( Locale pLocale ) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Doc41UserNDC) localizeDC( (Doc41UserNDC) ReflectTool.classForNameEx( Doc41UserNDC.class.getName() ).newInstance(), pLocale);
    }


    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#createProfileDC(java.util.Locale)
	 */
    @Override
	public Doc41ProfileNDC createProfileDC( Locale pLocale ) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Doc41ProfileNDC) localizeDC( (Doc41ProfileNDC)ReflectTool.classForNameEx( Doc41ProfileNDC.class.getName() ).newInstance(), pLocale);
    }


    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#createUserProfileDC(java.util.Locale)
	 */
    @Override
	public Doc41UserProfileNDC createUserProfileDC( Locale pLocale ) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Doc41UserProfileNDC) localizeDC( (Doc41UserProfileNDC)ReflectTool.classForNameEx( Doc41UserProfileNDC.class.getName() ).newInstance(), pLocale);
    }


    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#createPermissionDC(java.util.Locale)
	 */
    @Override
	public Doc41PermissionNDC createPermissionDC( Locale pLocale ) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Doc41PermissionNDC) localizeDC( (Doc41PermissionNDC)ReflectTool.classForNameEx( Doc41PermissionNDC.class.getName() ).newInstance(), pLocale);
    }


    /* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.integration.db.BOEUserManagement#createPGUPermissionDC(java.util.Locale)
	 */
    @Override
	public Doc41PGUPermissionNDC createPGUPermissionDC( Locale pLocale ) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Doc41PGUPermissionNDC) localizeDC( (Doc41PGUPermissionNDC)ReflectTool.classForNameEx( Doc41PGUPermissionNDC.class.getName() ).newInstance(), pLocale);
    }
    


}
