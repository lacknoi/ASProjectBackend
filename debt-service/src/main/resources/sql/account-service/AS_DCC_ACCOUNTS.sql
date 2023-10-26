--drop table USRDEBT.AS_DCC_ACCOUNTS
create table USRDEBT.AS_DCC_ACCOUNTS (
       ACCOUNT_ID number(5)
       , ACCOUNT_NO varchar(50)
       , ACCOUNT_NAME varchar(50)
       , STATUS varchar(50)
       , STATUS_DATE date
       , LAST_UPD date
);