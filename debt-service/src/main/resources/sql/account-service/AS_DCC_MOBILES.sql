--drop table USRDEBT.AS_DCC_MOBILES
create table USRDEBT.AS_DCC_MOBILES (
       MOBILE_ID number(5)
       , ACCOUNT_ID varchar(50)
       , MOBILE_NO varchar(10)
       , STATUS varchar(50)
       , STATUS_DATE date
       , LAST_UPD date
);