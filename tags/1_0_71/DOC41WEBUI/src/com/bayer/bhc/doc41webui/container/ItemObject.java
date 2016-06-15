package com.bayer.bhc.doc41webui.container;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.bayer.bhc.doc41webui.common.exception.Doc41TechnicalException;

/**
 * Item Object. Base class for all non database domain objects.
 * 
 */
public abstract class ItemObject implements java.io.Serializable {   
	private static final long serialVersionUID = 1L;

	/**
     * Erzeuge (deep-)Kopie über Serialisierung
     * @author Josef Wasel
     * @throws Doc41TechnicalException 
     */
    public Object deepCopy() throws Doc41TechnicalException {
    
        try {
            // compare reset()
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream outS = new ObjectOutputStream(byteStream);
    
            outS.writeObject(this);
            outS.flush();
    
            byte[] buf = byteStream.toByteArray();

            return (ItemObject) new ObjectInputStream(new ByteArrayInputStream(buf)).readObject();
    
        } catch (Exception e) {
            throw new Doc41TechnicalException(this.getClass(), "DomainObject.deepCopy", e);
        }
    }
}
