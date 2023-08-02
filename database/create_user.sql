alter session set "_ORACLE_SCRIPT"=true;
CREATE USER "BIOSLIS" PROFILE "DEFAULT" IDENTIFIED BY "123456" ACCOUNT UNLOCK;
CREATE USER "APP" PROFILE "DEFAULT" IDENTIFIED BY "123456" ACCOUNT UNLOCK;
GRANT "CONNECT" TO "BIOSLIS";
GRANT "RESOURCE" TO "BIOSLIS";
GRANT ALTER ANY INDEX TO "BIOSLIS";
GRANT ALTER ANY SEQUENCE TO "BIOSLIS";
GRANT ALTER ANY TABLE TO "BIOSLIS";
GRANT ALTER ANY TRIGGER TO "BIOSLIS";
GRANT CREATE ANY INDEX TO "BIOSLIS";
GRANT CREATE ANY SEQUENCE TO "BIOSLIS";
GRANT CREATE ANY SYNONYM TO "BIOSLIS";
GRANT CREATE ANY TABLE TO "BIOSLIS";
GRANT CREATE ANY TRIGGER TO "BIOSLIS";
GRANT CREATE ANY VIEW TO "BIOSLIS";
GRANT CREATE PROCEDURE TO "BIOSLIS";
GRANT CREATE PUBLIC SYNONYM TO "BIOSLIS";
GRANT CREATE TRIGGER TO "BIOSLIS";
GRANT CREATE VIEW TO "BIOSLIS";
GRANT DELETE ANY TABLE TO "BIOSLIS";
GRANT DROP ANY INDEX TO "BIOSLIS";
GRANT DROP ANY SEQUENCE TO "BIOSLIS";
GRANT DROP ANY TABLE TO "BIOSLIS";
GRANT DROP ANY TRIGGER TO "BIOSLIS";
GRANT DROP ANY VIEW TO "BIOSLIS";
GRANT INSERT ANY TABLE TO "BIOSLIS";
GRANT QUERY REWRITE TO "BIOSLIS";
GRANT SELECT ANY TABLE TO "BIOSLIS";
GRANT UNLIMITED TABLESPACE TO "BIOSLIS";