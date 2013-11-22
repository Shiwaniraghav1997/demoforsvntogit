
------------------------------------
-- Alter-Script: CVS v1.0 -> v1.1 --
------------------------------------
-- New versioning
-- Enhancements for DOC41WEB

INSERT INTO  doc41web_fdt.Versions VALUES ( 'Foundation-X', 1, 1);

GRANT                                 REFERENCES ON UM_USERS       TO DOC41WEB_MGR WITH GRANT OPTION;
GRANT                                 REFERENCES ON UM_PROFILES    TO DOC41WEB_MGR WITH GRANT OPTION;
GRANT DELETE                                     ON UM_PERMISSIONS TO MXDOC41WEB;
------------------------------------
-- Alter-Script: CVS v1.1 -> v1.2 --
------------------------------------

--UPDATE bco_fdt.Versions SET subVersion = 2 WHERE ( module = 'Foundation-X' ) AND ( subVersion = 1 );
--COMMIT WORK;
--