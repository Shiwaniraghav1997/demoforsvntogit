/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.integration.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.ecim.foundation.business.sbcommon.DisplayTextDC;
import com.bayer.ecim.foundation.business.sbcommon.DisplayTextTransformer;

/**
 * DisplaytextDAO implementation.
 * 
 * @author ezzqc
 * @id $Id: DisplaytextDAOImpl.java,v 1.2 2012/02/22 14:16:09 ezzqc Exp $
 */
@Component
public class DisplaytextDAO {
 
    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.DisplaytextDAO#getDisplaytextList(long, java.util.Locale)
     */
    @SuppressWarnings("unchecked")
    public List<DisplayTextDC> getDisplaytextList(long texttype) {
        return (ArrayList<DisplayTextDC>) DisplayTextTransformer.get().getAllByType(new Long(texttype), LocaleInSession.get());
    }
    
    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.DisplaytextDAO#getDisplaytextListBackend(long, java.util.Locale)
     */
    @SuppressWarnings("unchecked")
    public List<DisplayTextDC> getDisplaytextListBackend(long texttype, Locale locale) {
        return (ArrayList<DisplayTextDC>) DisplayTextTransformer.get().getAllByType(new Long(texttype), locale);
    }

    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.DisplaytextDAO#getDisplaytext(long, long, java.util.Locale)
     */
    public DisplayTextDC getDisplaytext(long texttype, long id) {
        return DisplayTextTransformer.get().getByType(new Long(texttype), new Long(id), LocaleInSession.get());
    }

    /*
     * (non-Javadoc)
     * @see com.bayer.bhc.doc41webui.integration.db.DisplaytextDAO#getDisplaytextBackend(long, long, java.util.Locale)
     */
    public DisplayTextDC getDisplaytextBackend(long texttype, long id, Locale locale) {
        return DisplayTextTransformer.get().getByType(new Long(texttype), new Long(id), locale);
    }

	@SuppressWarnings("unchecked")
	public List<DisplayTextDC> getLanguageCountryCodes() {
		return DisplayTextTransformer.get().getLimitedLanguageCountries(LocaleInSession.get(), false, false);
	}
}
