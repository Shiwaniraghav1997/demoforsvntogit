/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.container;

import com.bayer.bhc.doc41webui.common.paging.PagingData;
import com.bayer.bhc.doc41webui.common.paging.PagingRequest;
import com.bayer.bhc.doc41webui.domain.User;


/**
 * UserPagingRequest object.
 * 
 * @author ezzqc
 */
public class UserPagingRequest extends PagingRequest {

    private String company;

    private String cwid;

    private String role;

    private String surname;

    private Boolean isActive;

    private Boolean isExternal;

    public UserPagingRequest(final UserListFilter pFilter, final PagingData pagingData) {
        super(pagingData);

        if (pFilter.getStatus() != null
                && !pFilter.getStatus().equals(UserListFilter.FILTER_ALL_OPTIONS)) {
            isActive = Boolean.valueOf(pFilter.getStatus().equals(User.STATUS_ACTIVE));
        }

        if (pFilter.getType() != null
                && !pFilter.getType().equals(UserListFilter.FILTER_ALL_OPTIONS)) {
            isExternal = Boolean.valueOf(pFilter.getType().equals(User.TYPE_EXTERNAL));
        }

        role = pFilter.getRole() != null
                && !pFilter.getRole().equals(UserListFilter.FILTER_ALL_OPTIONS) ? pFilter.getRole()
                : null;

        cwid = pFilter.getCwid() != null ? pFilter.getCwid().toUpperCase() : null;
        company = pFilter.getCompany();
        surname = pFilter.getSurname();
    }

    public String getCompany() {
        return company;
    }

    public String getCwid() {
        return cwid;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Boolean getIsExternal() {
        return isExternal;
    }

    public String getRole() {
        return role;
    }

    public String getSurname() {
        return surname;
    }

}
