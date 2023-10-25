--drop table USRDEBT.AS_DCC_ACCOUNTS
create table USRDEBT.AS_DCC_ACCOUNTS (
       ACCOUNT_ID number(5)
       , ACCOUNT_NO varchar(50)
       , ACCOUNT_NAME varchar(50)
       , STATUS_CD varchar(50)
       , CREATED date
       , CREATED_BY varchar(50)
       , LAST_UPD date
       , LAST_UPD_BY varchar(50)
);