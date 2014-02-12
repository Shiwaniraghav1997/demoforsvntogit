/**
 * 
 * $Id: SAPSingleton.java,v 1.10 2010/04/08 09:58:55 imwif Exp $
 * 
 */

package com.bayer.bhc.doc41webui.integration.sap.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import com.bayer.ecim.foundation.basic.ConfigMap;
import com.bayer.ecim.foundation.basic.Dbg;
import com.bayer.ecim.foundation.basic.InitException;
import com.bayer.ecim.foundation.basic.Singleton;
import com.bayer.ecim.foundation.basic.StringTool;
import com.sap.conn.jco.JCo;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoThroughput;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;
import com.sap.conn.jco.monitor.JCoConnectionData;
import com.sap.conn.jco.monitor.JCoDestinationMonitor;
import com.sap.conn.jco.rt.DefaultThroughput;
import com.sap.conn.jco.rt.RuntimeEnvironment;


/**
 * @author IMKLJ
 *
 */
public class SAPSingleton extends Singleton {

	public static final String ID = "SAP";
	
	static final String COMPONENT_CONFIG_KEY 		= "eai";
	static final String COMPONENT_CONFIG_SAP 		= "sap";
	static final String COMPONENT_CONFIG_POOL 		= "pool";
	static final String COMPONENT_CONFIG_LOGON 		= "logon";
	static final String COMPONENT_CONFIG_RFC 		= "rfc";
	static final String COMPONENT_CONFIG 			= ".config";

	Map<String,Object> cConfig 						= null;	
	Map<String,Map<String,String>> cSAPLogons 		= null;
	Map<String,Map<String,String>> cRFCs 			= null;

	private static InMemoryDestinationDataProvider dataProvider;
 
     public SAPSingleton(String pID)
         throws InitException     {
         super(pID, true);
         String oldJavaLibPath = System.getProperty("java.library.path");
		try {
			dbg0 = Dbg.get().getChannelByName("SAPB_DBG0", "SAPB_DBG0", "sap.sl.dbg0", true);
			dbg1 = Dbg.get().getChannelByName("SAPB_DBG1", "SAPB_DBG1", "sap.sl.dbg1", false);
			dbg2 = Dbg.get().getChannelByName("SAPB_DBG2", "SAPB_DBG2", "sap.sl.dbg2", false);
			init();
			initSucceeded( SAPSingleton.class );
         } catch ( Exception mEx ) {
         	initFailed( new InitException( "Failed to initialize SAPSingleton:"+ mEx.getMessage(), mEx ) );
         } catch ( ExceptionInInitializerError mEr ) { // this special error may be catched and encapsulated / catching non critical, configuration problem of the local environment
        	 String resource = Environment.class.getClassLoader().getResource("com/sap/conn/jco/ext/Environment.class").getFile();
        	 initFailed( new InitException( "Failed to initialize SAPSingleton, local configuration problem!!! "+mEr.getMessage()+" "+oldJavaLibPath+"###"+resource, mEr ) );
         }
     }

     // replace dirty hack with better handling
     @SuppressWarnings("unchecked")
     protected synchronized void init() throws InitException    {
    	 try {
    		 if(dataProvider!=null){
        			 dataProvider.clean();
    		 } else {
    			 if(Environment.isDestinationDataProviderRegistered()){
    				 //try dirty hack
    				 try{
    					 Method getInstanceMethod = Environment.class.getDeclaredMethod("getInstance");
    					 getInstanceMethod.setAccessible(true);
    					 RuntimeEnvironment instance = (RuntimeEnvironment) getInstanceMethod.invoke(null);
    					 Method getProviderMethod = RuntimeEnvironment.class.getDeclaredMethod("getDestinationDataProvider");
    					 getProviderMethod.setAccessible(true);
    					 Object provider = getProviderMethod.invoke(instance);
    					 Environment.unregisterDestinationDataProvider((DestinationDataProvider) provider);
    				 } catch (Exception e){
	    				 //dirty hack did not work, so throw exception
	    				 throw new InitException("DestinationDataProvider registered but is unknown to SAPSingleton, try restarting the VM",e);
    				 }
    			 } 
    			 dataProvider = new InMemoryDestinationDataProvider();
    			 Environment.registerDestinationDataProvider(dataProvider); 
    		 }
    		 
    		 cConfig = ConfigMap.get().getSubConfig(COMPONENT_CONFIG_KEY,COMPONENT_CONFIG_SAP);
    		 cSAPLogons = new HashMap<String,Map<String,String>>();
    		 parseLogonConfig();
    		 createSAPPools(); 
    		 cRFCs = new HashMap<String,Map<String,String>>();
    		 parseRFCConfig();

    	 } catch (InitException ex) {
    		 throw ex;
    	 } catch (Exception ex) {
    		 throw new InitException("Init of SAP-Singleton failed! "+ex.getMessage(),ex);
    	 }
     }
     
     
     private void parseRFCConfig() throws InitException {
	  String bRFCNames = (String) cConfig.get(COMPONENT_CONFIG_RFC+".names");
	  StringTokenizer bStrTok = null;
	  String bRFCName = null;
	  
	  if ( bRFCNames != null ) {
		bStrTok = new StringTokenizer(bRFCNames,",");
		while (bStrTok.hasMoreTokens()) {
			bRFCName = bStrTok.nextToken();
			addRFCConfig( cConfig, COMPONENT_CONFIG_RFC+COMPONENT_CONFIG+"."+bRFCName+".", bRFCName );
		}
	  }
     }


	private void addRFCConfig(Map<String,Object> pConfig, String pConfigPrefix, String pRFCName ) {
		Map<String,String> bRFCConf = new HashMap<String,String>();
		bRFCConf.put( "sapcall",	getMapParm( pConfig, pConfigPrefix, "sapcall",	null, true ) );
		bRFCConf.put( "pool",		getMapParm( pConfig, pConfigPrefix, "pool",		null, true ) );
		bRFCConf.put( "class",		getMapParm( pConfig, pConfigPrefix, "class",	null, true ) );
		cRFCs.put(pRFCName,bRFCConf);  
	}


	private void parseLogonConfig() throws InitException {
		String bLogonNames = (String) cConfig.get(COMPONENT_CONFIG_LOGON+".names");     	
		StringTokenizer bStrTok = null;
		String bLogonName = null;
	  
		if ( bLogonNames != null ) {
			bStrTok = new StringTokenizer(bLogonNames,",");
			while (bStrTok.hasMoreTokens()) {
				bLogonName = bStrTok.nextToken();
				addLogonConfig( cConfig, COMPONENT_CONFIG_LOGON+COMPONENT_CONFIG+"."+bLogonName+".", bLogonName );
			}
		}
	}


	public void addLogonConfig( Map<String,Object> pConfig, String pConfigPrefix, String pLogonName ) {
		Map<String,String> bLogonConf = new HashMap<String,String>();
		bLogonConf.put("client",	getMapParm( pConfig, pConfigPrefix, "client",	null, true ) );
		bLogonConf.put("user",		getMapParm( pConfig, pConfigPrefix, "user",		null, true ) );
		bLogonConf.put("pwd",		getMapParm( pConfig, pConfigPrefix, "pwd",		null, true ) );
		bLogonConf.put("host",		getMapParm( pConfig, pConfigPrefix, "host",		null, true ) );
		bLogonConf.put("language",	getMapParm( pConfig, pConfigPrefix, "language",	null, true ) );
		bLogonConf.put("sysnr",		getMapParm( pConfig, pConfigPrefix, "sysnr",	null, true ) );
		cSAPLogons.put( pLogonName, bLogonConf );  
	}

	protected void releaseSAPPool(String pPoolName) {
		dataProvider.changeProperties(pPoolName, null);
	}


	protected void createSAPPool(String pPoolName) throws InitException {
		try {
			addPoolConfig( cConfig, COMPONENT_CONFIG_POOL+COMPONENT_CONFIG+"."+pPoolName+"." ,pPoolName);
		} catch (JCoException e) {
			throw new InitException("createSAPPool", e);
		}
	}


	public String getMapParm( Map<String,?> pMap, String pPrefix, String pParm, String pDefault, boolean pIsObligatory ) {
		if ( pMap == null )
			throw new InitException( "Internal error reading property '" + pPrefix + pParm + "' from Map, Map is null!!!" );
		if ( pPrefix == null )
			throw new InitException( "Internal error reading property '" + pParm + "', prefix is null!!!" );
		if ( pParm == null )
			throw new InitException( "Internal error reading property with prefix '" + pPrefix + "', property name is null!!!" );
		String mRes = StringTool.nullToEmpty( pMap.get( pPrefix + pParm ) );
		if ( pIsObligatory && ( mRes == null ) )
			throw new InitException( "Configuration error reading property '" + pPrefix + pParm + "', OBLIGATORY property is undefined!!!" );
		return ( mRes == null ) ? pDefault : mRes;
	}


	public int getMapParm( Map<String,?> pMap, String pPrefix, String pParm, int pDefault, boolean pIsObligatory ) {
	    String v = getMapParm( pMap, pPrefix, pParm, (String)null, pIsObligatory );
	    try {
	        return ( v == null ) ? pDefault : Integer.parseInt( v );
	    } catch ( NumberFormatException mEx ) {
	        throw new InitException( "Configuration error reading property '" + pPrefix + pParm + "', integer property is invalid: '" + v +"'" );
	    }
	}


	public void addPoolConfig(Map<String,Object> pConfig, String pConfigPrefix, String pPoolName) throws InitException, JCoException {
		int mMaxConn = getMapParm(pConfig, pConfigPrefix, "maxconn", 0, true );
		String bLogon = getMapParm( pConfig, pConfigPrefix, "logon", null, true );
		Map<String,String> bLoginConf = cSAPLogons.get( bLogon );
		if (bLoginConf == null)
			throw new InitException("Logon configuration not found: " + bLogon );

		addDestinationToSapManager(
			pPoolName,
			getMapParm(bLoginConf, "", "client",	null, false),
			getMapParm(bLoginConf, "", "user",	null, false),
			getMapParm(bLoginConf, "", "pwd",	null, false),
			getMapParm(bLoginConf, "", "language",	null, false),
			getMapParm(bLoginConf, "", "host",	null, false),
			getMapParm(bLoginConf, "", "sysnr",	null, false),
			mMaxConn
		);
	}


	private void addDestinationToSapManager(
	        String pPoolName,
		String pClient,
		String pUser,
		String pPwd,
		String pLanguage,
		String pHost,
		String pSysnr,
		int pMaxConn ) throws JCoException
	{
		pPwd = StringTool.decodePassword( pPwd );
		Dbg.get().println( dbg0, this, null, "configure a new destination '" + pPoolName + "/" + pMaxConn +
		        "'(" + pClient + "," + pUser + "," + StringTool.repeat( "*" , StringTool.nullToEmpty(pPwd).length() ) + "," +
		        pLanguage + "," + pHost + "," + pSysnr + ")  <= (client,user,pwd,language,host,sysnr)" );
		if ( pMaxConn < 1 ) {
			throw new UnsupportedOperationException("unpooled connections not supported");
		}
		Properties connectProperties = new Properties();
		connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, ""+pMaxConn);
		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, pHost);
		connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  pSysnr);
		connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, pClient);
		connectProperties.setProperty(DestinationDataProvider.JCO_USER,   pUser);
		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, pPwd);
		connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   pLanguage);

		dataProvider.changeProperties(pPoolName, connectProperties);
			
	}

	private JCoDestination getDestinationFromSapManager(String pPoolName) throws JCoException{
		return JCoDestinationManager.getDestination(pPoolName);
	}
	


	protected void createSAPPools() throws InitException {
		String bPoolNames = StringTool.emptyToNull( StringTool.toString( cConfig.get(COMPONENT_CONFIG_POOL+".names") ) ); 
		StringTokenizer bStrTok = null;
		
		if ( bPoolNames != null ) {
			bStrTok = new StringTokenizer(bPoolNames,",");
			while (bStrTok.hasMoreTokens()) {
				String mPool = bStrTok.nextToken();
				releaseSAPPool(mPool);
				createSAPPool( mPool );
			}	  							  		
		}
	}
 
 
	public static synchronized SAPSingleton get()
		throws InitException
	{
		SAPSingleton mSing = (SAPSingleton)getSingleton(ID);
		return (mSing != null) ? mSing : new SAPSingleton(ID);
	}

	/*
	 * Bei Aufruf aus Chain sind die Parameter auf speziellem Weg erzeugt und eine
	 * Recovery ist unmöglich.
	 * Dafür und  für ähnliche Fälle kann die Recovery ausgeschaltet werden. 
 	 */
 	
	public <E> List<E> performRFC(String pRFCName, List<?> pParms) throws SAPException {
		return performRFC( pRFCName, pParms, true );
	}
	 	
	public <E> List<E> performRFC(String pRFCName, List<?> pParms, boolean pTryAutoRecovery) throws SAPException {
		JCoDestination mSAPDestination = null;
		List<E> mResult = null;

		Map<String, String> mRFCConfig = cRFCs.get(pRFCName);
		if ( mRFCConfig == null ){
			throw new SAPException("No configuration for RFC \""+pRFCName+"\"", null, null, null, null, true, true);
		}
		String bPoolName = mRFCConfig.get("pool");
		if(StringTool.isTrimmedEmptyOrNull(bPoolName)){
			throw new SAPException("no pool configured for RFC '"+pRFCName+"'", null);
		}
		String bSAPRFCName = mRFCConfig.get("sapcall");
		if(StringTool.isTrimmedEmptyOrNull(bSAPRFCName)){
			throw new SAPException("no sapcall configured for RFC '"+pRFCName+"'", null);
		}
		String bRFCCallerClass = mRFCConfig.get("class");
		if(StringTool.isTrimmedEmptyOrNull(bRFCCallerClass)){
			throw new SAPException("no class configured for RFC '"+pRFCName+"'", null);
		}
		

		try {
			try {
				mSAPDestination = getDestinationFromSapManager(bPoolName);
			} catch (JCoException ex) {
				if ( pTryAutoRecovery ) {
					SAPException tempException = new SAPException("Failed to receive connection from pool, trying autorecovery...", ex );
					tempException.getId();//useless code to make FindBugs happy
					Dbg.get().println( dbg0, this, null, "Trying autorecovery, because SAPSingleteon.performRFC failed with " + ex.getClass().getName() + ": " + ex.getMessage() );
					return new SAPSingleton( ID ).performRFC( pRFCName, pParms, false );
				} else {
					throw new SAPException("Performing an RFC failed!", ex);
				}
			}
			RFCCaller<E> bRFCCaller = null;
			try {    
				@SuppressWarnings("unchecked")
				Class<RFCCaller<E>> forName = (Class<RFCCaller<E>>) Class.forName(bRFCCallerClass);
				bRFCCaller = forName.newInstance();
			} catch (Exception ex) {
				throw new SAPException("RFC Caller for function \""+pRFCName+"\" could not be instantiated", ex);	
			}
			
			mResult = performRFC( pRFCName, pParms, bSAPRFCName, bRFCCaller, mSAPDestination );
		} catch (SAPException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new SAPException( "Failed to process SAPRFC!", ex );
		} 
	 	return mResult;
	}
	 
 
	private <E> List<E> performRFC(String pRFCName, List<?> pParms, String pSAPRFCName, RFCCaller<E> pRFCCaller, JCoDestination pSAPDestination ) throws SAPException, JCoException {
		
		JCoThroughput bPerfDate = new DefaultThroughput();
		pSAPDestination.setThroughput( bPerfDate );
		JCoRepository pSAPRep = pSAPDestination.getRepository();
		Dbg.get().println( dbg0, this, null, "Getting function template '" + pSAPRFCName.toUpperCase() + "' from repository '" + pSAPRep.getName() + "'(" + pSAPRep.toString() +")" );
		JCoFunctionTemplate bTemplate = pSAPRep.getFunctionTemplate(pSAPRFCName.toUpperCase());
		if (bTemplate == null)
			throw new SAPException( "SAP RFC is unknown or cannot be called: " + pSAPRFCName, null );
		JCoFunction bFunction = bTemplate.getFunction();
		pRFCCaller.prepareCall( bFunction, pParms );
		try {
			bFunction.execute(pSAPDestination);
		} catch (Exception ex) {
			pRFCCaller.handleException( ex );
		} 
		return pRFCCaller.processResult( bFunction );
	}

	public String dumpMetadata(String pRFCName) throws SAPException, JCoException {
		JCoFunction bFunction = getJCoFunction(pRFCName);
		return RFCMetaDataDumper.dumpFunction(bFunction);
	}
	
	public JCoFunction getJCoFunction(String pRFCName) throws SAPException, JCoException{
		Map<String, String> mRFCConfig = cRFCs.get(pRFCName);
		if (mRFCConfig == null) {
			throw new SAPException("No configuration for RFC \"" + pRFCName
					+ "\"", null, null, null, null, true, true);
		}
		String bPoolName = mRFCConfig.get("pool");
		String bSAPRFCName = mRFCConfig.get("sapcall");

		JCoDestination mSAPDestination = getDestinationFromSapManager(bPoolName);
		JCoThroughput bPerfDate = new DefaultThroughput();
		mSAPDestination.setThroughput(bPerfDate);
		JCoRepository pSAPRep = mSAPDestination.getRepository();
		JCoFunctionTemplate bTemplate = pSAPRep.getFunctionTemplate(bSAPRFCName
				.toUpperCase());
		JCoFunction bFunction = bTemplate.getFunction();
		return bFunction;
	}
	
	public Set<String> getRFCNames() {
		return cRFCs.keySet();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		List<String> destinationIDs = JCo.getDestinationIDs();
		for (String destId : destinationIDs) {
			sb.append("##############################################################\n");
			sb.append("Destination-ID:");
			sb.append(destId);
			sb.append("\n\n");
			JCoDestinationMonitor destinationMonitor = JCo.getDestinationMonitor(destId);
			sb.append("OriginDestinationID: ");
			sb.append(destinationMonitor.getOriginDestinationID());
			sb.append("\n");
			
			sb.append("PoolCapacity: ");
			sb.append(destinationMonitor.getPoolCapacity());
			sb.append("\n");
			
			sb.append("PeakLimit: ");
			sb.append(destinationMonitor.getPeakLimit());
			sb.append("\n");
			
			sb.append("UsedConnectionCount: ");
			sb.append(destinationMonitor.getUsedConnectionCount());
			sb.append("\n");
			
			sb.append("PooledConnectionCount: ");
			sb.append(destinationMonitor.getPooledConnectionCount());
			sb.append("\n");
			
			sb.append("MaxUsedCount: ");
			sb.append(destinationMonitor.getMaxUsedCount());
			sb.append("\n");
			
			sb.append("LastActivityTimestamp: ");
			sb.append(destinationMonitor.getLastActivityTimestamp());
			sb.append("\n");
			
			sb.append("\nConnections: \n");
			List<? extends JCoConnectionData> connectionsData = destinationMonitor.getConnectionsData();
			for (JCoConnectionData jCoConnectionData : connectionsData) {
				sb.append("-----------------------------------------------------\n");
				
				sb.append("Protocol: ");
				sb.append(jCoConnectionData.getProtocol());
				sb.append("\n");
				
				sb.append("SessionId: ");
				sb.append(jCoConnectionData.getSessionId());
				sb.append("\n");
				
				sb.append("FunctionModuleName: ");
				sb.append(jCoConnectionData.getFunctionModuleName());
				sb.append("\n");
				
				sb.append("DSRPassport: ");
				sb.append(jCoConnectionData.getDSRPassport());
				sb.append("\n");
				
				sb.append("ConnectionType: ");
				sb.append(jCoConnectionData.getConnectionType());
				sb.append("\n");
				
				sb.append("SystemID: ");
				sb.append(jCoConnectionData.getSystemID());
				sb.append("\n");
				
				sb.append("AbapHost: ");
				sb.append(jCoConnectionData.getAbapHost());
				sb.append("\n");
				
				sb.append("AbapSystemNumber: ");
				sb.append(jCoConnectionData.getAbapSystemNumber());
				sb.append("\n");
				
				sb.append("AbapClient: ");
				sb.append(jCoConnectionData.getAbapClient());
				sb.append("\n");
				
				sb.append("AbapUser: ");
				sb.append(jCoConnectionData.getAbapUser());
				sb.append("\n");
				
				sb.append("AbapLanguage: ");
				sb.append(jCoConnectionData.getAbapLanguage());
				sb.append("\n");
				
				sb.append("ConvId: ");
				sb.append(jCoConnectionData.getConvId());
				sb.append("\n");
				
				sb.append("ApplicationName: ");
				sb.append(jCoConnectionData.getApplicationName());
				sb.append("\n");
				
				sb.append("GroupName: ");
				sb.append(jCoConnectionData.getGroupName());
				sb.append("\n");
				
				sb.append("State (0:inactive, 1: active, 2: stateful): ");
				sb.append(jCoConnectionData.getState());
				sb.append("\n");
				
				sb.append("LastActivityTimestamp: ");
				sb.append(jCoConnectionData.getLastActivityTimestamp());
				sb.append("\n");
				
				sb.append("ThreadId: ");
				sb.append(jCoConnectionData.getThreadId());
				sb.append("\n");
				
				sb.append("ThreadName: ");
				sb.append(jCoConnectionData.getThreadName());
				sb.append("\n");
				
				sb.append("ConnectionHandle: ");
				sb.append(jCoConnectionData.getConnectionHandle());
				sb.append("\n");
				
				sb.append("-----------------------------------------------------\n");
			}
			
			sb.append("##############################################################\n");
		}
		return sb.toString();
	}

}

