--         observer doc41_obsv
--READ_ONLY
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='READ_ONLY')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_obsv'));

--         business admin doc41_badm
--TOPNAV_MYPROFILE
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_MYPROFILE')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_badm'));

--TOPNAV_MANAGEMENT
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_MANAGEMENT')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_badm'));

--ADDINTERNALUSERTOLOGGROUP
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='ADDINTERNALUSERTOLOGGROUP')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--USER_CREATE
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='USER_CREATE')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_badm'));

--USER_EDIT
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='USER_EDIT')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_badm'));

--USER_IMPORT
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='USER_IMPORT')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_badm'));

--USER_LIST
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='USER_LIST')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_badm'));

--USER_LOOKUP
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='USER_LOOKUP')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_badm'));


--         tech admin doc41_tadm
--TOPNAV_MYPROFILE
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_MYPROFILE')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--TOPNAV_MAINTENANCE
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_MAINTENANCE')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--NAV_MONITORING
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='NAV_MONITORING')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--NAV_PROFILEPERMISSIONS
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='NAV_PROFILEPERMISSIONS')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--NAV_KGSCUSTOMIZING
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='NAV_KGSCUSTOMIZING')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--NAV_RFCMETADATA
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='NAV_RFCMETADATA')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--NAV_SUPPORTCONSOLE
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='NAV_SUPPORTCONSOLE')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--NAV_TRANSLATIONS
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='NAV_TRANSLATIONS')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--MONITORING
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='MONITORING')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--TRANSLATION
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TRANSLATION')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--PROFILEPERMISSIONS
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='PROFILEPERMISSIONS')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--RFCMETADATA
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='RFCMETADATA')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--KGSCUSTOMIZING 
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='KGSCUSTOMIZING')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--UNTRANSLATEDLABELS
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='UNTRANSLATEDLABELS')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--NAV_UNTRANSLATEDLABELS
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='NAV_UNTRANSLATEDLABELS')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));

--NAV_APPLOGS
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='NAV_APPLOGS')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_tadm'));


--         carrier doc41_carr
--TOPNAV_MYPROFILE
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_MYPROFILE')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_carr'));

--TOPNAV_UPLOAD
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_UPLOAD')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_carr'));

--TOPNAV_DOWNLOAD
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_DOWNLOAD')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_carr'));

--DOC_BOL_UP
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_BOL_UP')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_carr'));

--DOC_AWB_UP
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_AWB_UP')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_carr'));

--DOC_SHIPDECL_DOWN
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_SHIPDECL_DOWN')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_carr'));

--DOC_BOL_DOWN
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_BOL_DOWN')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_carr'));

--DOC_WB_DOWN
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_WB_DOWN')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_carr'));

--DOC_AWB_DOWN
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_AWB_DOWN')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_carr'));

--DOC_FDACERT_DOWN
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_FDACERT_DOWN')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_carr'));

--DOC_CMR_UP
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_CMR_UP')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_carr'));

--DOC_CMROUT_DOWN
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_CMROUT_DOWN')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_carr'));


--         customs broker doc41_cusbr
--TOPNAV_MYPROFILE
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_MYPROFILE')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_cusbr'));

--DOC_AWB_DIRECT_DOWN
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_AWB_DIRECT_DOWN')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_cusbr'));

--DOC_BOL_DIRECT_DOWN
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_BOL_DIRECT_DOWN')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_cusbr'));

--DOC_WB_DIRECT_DOWN
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_WB_DIRECT_DOWN')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_cusbr'));

--DOC_FDACERT_DIRECT_DOWN
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_FDACERT_DIRECT_DOWN')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_cusbr'));

--DOC_SHIPDECL_DIRECT_DOWN
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_SHIPDECL_DIRECT_DOWN')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_cusbr'));



--          material supplier doc41_matsup
--TOPNAV_MYPROFILE
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_MYPROFILE')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_matsup'));

--TOPNAV_UPLOAD
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_UPLOAD')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_matsup'));

--DOC_SUPCOA_UP
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_SUPCOA_UP')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_matsup'));


--          product supplier doc41_prodsup
--TOPNAV_MYPROFILE
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_MYPROFILE')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_prodsup'));

--TOPNAV_UPLOAD
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_UPLOAD')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_prodsup'));

--DOC_DELCERT_UP
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_DELCERT_UP')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_prodsup'));


--          del cert viewer country doc41_delcertvcountry
--TOPNAV_MYPROFILE
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_MYPROFILE')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_delcertvcountry'));

--TOPNAV_DOWNLOAD
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_DOWNLOAD')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_delcertvcountry'));

--DOC_DELCERT_DOWN_COUNTRY
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_DELCERT_DOWN_COUNTRY')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_delcertvcountry'));


--          del cert viewer customer doc41_delcertvcust
--TOPNAV_MYPROFILE
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_MYPROFILE')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_delcertvcust'));

--TOPNAV_DOWNLOAD
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_DOWNLOAD')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_delcertvcust'));

--DOC_DELCERT_DOWN_CUSTOMER
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_DELCERT_DOWN_CUSTOMER')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_delcertvcust'));



--         layout supplier doc41_laysup
--TOPNAV_MYPROFILE
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_MYPROFILE')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_laysup'));

--TOPNAV_UPLOAD
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_UPLOAD')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_laysup'));

--TOPNAV_DOWNLOAD
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_DOWNLOAD')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_laysup'));

--DOC_ARTWORK_UP_LS
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_ARTWORK_UP_LS')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_laysup'));

--DOC_ARTWORK_DOWN_LS
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_ARTWORK_DOWN_LS')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_laysup'));

--DOC_LAYOUT_UP_LS
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_LAYOUT_UP_LS')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_laysup'));

--DOC_LAYOUT_DOWN_LS
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_LAYOUT_DOWN_LS')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_laysup'));

--DOC_PMS_DOWN_LS
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_PMS_DOWN_LS')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_laysup'));

--DOC_PZ_DOWN_LS
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_PZ_DOWN_LS')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_laysup'));


--          pm supplier doc41_pmsup
--TOPNAV_MYPROFILE
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_MYPROFILE')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_pmsup'));

--TOPNAV_DOWNLOAD
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='TOPNAV_DOWNLOAD')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_pmsup'));

--DOC_ARTWORK_DOWN_PM
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_ARTWORK_DOWN_PM')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_pmsup'));

--DOC_LAYOUT_DOWN_PM
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_LAYOUT_DOWN_PM')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_pmsup'));

--DOC_PMS_DOWN_PM
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_PMS_DOWN_PM')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_pmsup'));

--DOC_PZ_DOWN_PM
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_PZ_DOWN_PM')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_pmsup'));

--DOC_TPACKDELREQ_DOWN_PM
insert into DOC41WEB_FDT.UM_PGU_PERMISSIONS
(PERMISSION_ID,PROFILE_ID) VALUES
((select OBJECT_ID FROM DOC41WEB_FDT.UM_PERMISSIONS WHERE CODE='DOC_TPACKDELREQ_DOWN_PM')
,
(select OBJECT_ID FROM DOC41WEB_FDT.UM_PROFILES WHERE PROFILENAME='doc41_pmsup'));
