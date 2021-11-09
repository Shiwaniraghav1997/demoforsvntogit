/*
 * (c)2002 Bayer AG Leverkusen, Bayer Business Solutions
 * All rights reserved.
 *
 * $Id: BeanLocatorUtility.java,v 1.1 2011/02/16 10:06:03 ezbym Exp $
 */
package com.bayer.bhc.doc41webui.tasks.model;

import com.bayer.ecim.foundation.basic.InitException;

public class BeanLocatorUtility extends
        com.bayer.ecim.foundation.business.sbcommon.blu.BeanLocatorUtility {

    /** The unique ID of this Singleton. */

    public BeanLocatorUtility(String pID) throws InitException {
        super(pID);
        initSucceeded(BeanLocatorUtility.class);
    }

    public static com.bayer.ecim.foundation.business.sbcommon.blu.BeanLocatorUtility get()
            throws InitException {
        com.bayer.ecim.foundation.business.sbcommon.blu.BeanLocatorUtility mSing = com.bayer.ecim.foundation.business.sbcommon.blu.BeanLocatorUtility
                .get();
        return (mSing instanceof BeanLocatorUtility) ? mSing
                : new BeanLocatorUtility(ID);
    }

    public static BeanLocatorUtility getDoc41Auto() throws InitException {
        return (BeanLocatorUtility) get();
    }

}
