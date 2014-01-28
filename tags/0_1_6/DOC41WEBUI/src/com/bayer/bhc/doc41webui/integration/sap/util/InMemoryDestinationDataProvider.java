package com.bayer.bhc.doc41webui.integration.sap.util;

import java.util.HashMap;
import java.util.Properties;

import com.sap.conn.jco.ext.DataProviderException;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class InMemoryDestinationDataProvider implements DestinationDataProvider {

	private DestinationDataEventListener eL;
	private HashMap<String, Properties> inMemoryStorage = new HashMap<String, Properties>();

	public Properties getDestinationProperties(String destinationName) {
		try {
			Properties p = inMemoryStorage.get(destinationName);
			if (p != null) {
				// check if all is correct, for example
				if (p.isEmpty())
					throw new DataProviderException(
							DataProviderException.Reason.INVALID_CONFIGURATION,
							"destination configuration is incorrect", null);

				return p;
			}

			return null;
		} catch (RuntimeException re) {
			throw new DataProviderException(
					DataProviderException.Reason.INTERNAL_ERROR, re);
		}
	}

	// An implementation supporting events has to retain the eventListener
	// instance provided
	// by the JCo runtime. This listener instance shall be used to notify the
	// JCo runtime
	// about all changes in destination configurations.
	public void setDestinationDataEventListener(
			DestinationDataEventListener eventListener) {
		this.eL = eventListener;
	}

	public boolean supportsEvents() {
		return true;
	}

	
	void changeProperties(String destName, Properties properties) {
		synchronized (inMemoryStorage) {
			if (properties == null) {
				if (inMemoryStorage.remove(destName) != null)
					eL.deleted(destName);
			} else {
				inMemoryStorage.put(destName, properties);
				eL.updated(destName); // create or updated
			}
		}
	}

	void clean() {
		synchronized (inMemoryStorage) {
			while(!inMemoryStorage.isEmpty()){
				String destName=inMemoryStorage.keySet().iterator().next();
				changeProperties(destName, null);
			}
		}
	}

}
