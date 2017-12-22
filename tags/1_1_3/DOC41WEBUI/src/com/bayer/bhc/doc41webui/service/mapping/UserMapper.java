package com.bayer.bhc.doc41webui.service.mapping;

import java.util.Locale;

import org.springframework.stereotype.Component;

import com.bayer.bbs.aila.model.AILAPerson;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.LocaleInSession;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.ecim.foundation.web.usermanagementN.UMUserNDC;
import com.bayer.ecim.foundation.web.usermanagementN.UM_CONSTS_N;

@Component
public class UserMapper extends AbstractMapper {

	public User mapToDomainObject(final UMUserNDC pDataCarrier, User pDomainObject) {
		pDomainObject = super.mapToDomainObject(pDataCarrier, pDomainObject);
		if(null == pDomainObject || null == pDataCarrier) {
            return pDomainObject;
        }
		
		pDomainObject.setDcId(pDataCarrier.getObjectID());
		pDomainObject.setActive(Boolean.valueOf(pDataCarrier.getObjectstateId().equals(UM_CONSTS_N.STATEACTIVE)));
		pDomainObject.setCwid(pDataCarrier.getCwid());
		pDomainObject.setEmail(pDataCarrier.getEmail());
		pDomainObject.setFirstname(pDataCarrier.getFirstname());
		pDomainObject.setSurname(pDataCarrier.getLastname());
		pDomainObject.setPhone(pDataCarrier.getPhone1());
		pDomainObject.setCompany(pDataCarrier.getCompanyName());
		if (pDataCarrier.getTimezoneId() != null) {
		    pDomainObject.setTimeZone(pDataCarrier.getTimezoneId());
		}
		
        if (pDataCarrier.getDisplayLanguageCode() != null) {
            if (pDataCarrier.getDisplayCountryCode() != null) {
            	pDomainObject.setLocale(new Locale(pDataCarrier.getDisplayLanguageCode(), pDataCarrier.getDisplayCountryCode()));
            } else {
            	pDomainObject.setLocale(new Locale(pDataCarrier.getDisplayLanguageCode()));
            }
        } else {
        	pDomainObject.setLocale(LocaleInSession.get() != null ? LocaleInSession.get() : Locale.US);
        }
		
        if (pDataCarrier.getIsexternal() != null) {
            pDomainObject.setType(pDataCarrier.getIsexternal().booleanValue() ? User.TYPE_EXTERNAL : User.TYPE_INTERNAL);
        } else {
            pDomainObject.setType(User.TYPE_EXTERNAL);
        }
        
		
		return pDomainObject;
	}
	
	public UMUserNDC mapToDataCarrier(final User pDomainObject, UMUserNDC pDataCarrier) {
		pDataCarrier =super.mapToDataCarrier(pDomainObject, pDataCarrier);
		if(null == pDomainObject || null == pDataCarrier) {
            return pDataCarrier;
        }

        pDataCarrier.setDisplayCountryCode(pDomainObject.getLocale().getCountry());
        pDataCarrier.setDisplayLanguageCode(pDomainObject.getLocale().getLanguage());

        pDataCarrier.setTimezoneId(pDomainObject.getTimeZone());
		pDataCarrier.setFirstname(pDomainObject.getFirstname());
		pDataCarrier.setLastname(pDomainObject.getSurname());
		pDataCarrier.setObjectstateId(pDomainObject.getActive().booleanValue() ? UM_CONSTS_N.STATEACTIVE : UM_CONSTS_N.STATEINACTIVE);
		pDataCarrier.setCwid(pDomainObject.getCwid());
		pDataCarrier.setEmail(pDomainObject.getEmail());
		pDataCarrier.setFirstname(pDomainObject.getFirstname());
		pDataCarrier.setPhone1(pDomainObject.getPhone());
		pDataCarrier.setLastname(pDomainObject.getSurname());
		pDataCarrier.setIsexternal(Boolean.valueOf(pDomainObject.isExternalUser()));
		if (Boolean.valueOf(pDomainObject.isExternalUser())) {
		    pDataCarrier.setCompanyName(pDomainObject.getCompany());
		}
		
		return pDataCarrier;
	}
	
	
	public User mapToDomainObject(final AILAPerson pDataCarrier, User pDomainObject) {
		if (pDataCarrier != null && pDomainObject != null) {
			pDomainObject.setCwid(pDataCarrier.getCwid());
			pDomainObject.setEmail(pDataCarrier.getMail());
			pDomainObject.setFirstname(pDataCarrier.getFirstName());
			pDomainObject.setSurname(pDataCarrier.getLastName());
			pDomainObject.setPhone(pDataCarrier.getPhone());
			pDomainObject.setCompany(pDataCarrier.getCompanyName());
		}
		return pDomainObject;
	}
	
	public AILAPerson mapToLdapDataCarrier(final User pDomainObject, AILAPerson pDataCarrier) {
        if (pDataCarrier != null && pDomainObject != null) {
        	pDataCarrier.setCwid(pDomainObject.getCwid());
        	pDataCarrier.setFirstName(pDomainObject.getFirstname());
        	pDataCarrier.setLastName(pDomainObject.getSurname());
        	pDataCarrier.setMail(pDomainObject.getEmail());
        	pDataCarrier.setPhone(pDomainObject.getPhone());
            if (Boolean.valueOf(pDomainObject.isExternalUser())) {
                pDataCarrier.setCompanyName(pDomainObject.getCompany());
            }
        }
        return pDataCarrier;
	}
	
}
