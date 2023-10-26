--drop table USRDEBT.AS_AC_MOBILES
create table USRDEBT.AS_AC_MOBILES (
       MOBILE_ID number(5)
       , ACCOUNT_ID varchar(50)
       , MOBILE_NO varchar(10)
       , STATUS varchar(50)
       , STATUS_DATE date
       , CREATED date
       , CREATED_BY varchar(50)
       , LAST_UPD date
       , LAST_UPD_BY varchar(50)
);