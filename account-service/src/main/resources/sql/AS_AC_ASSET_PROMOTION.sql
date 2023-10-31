--drop table USRDEBT.AS_AC_ASSET_PROMOTION
create table USRDEBT.AS_AC_ASSET_PROMOTION (
      ASSET_PROMOTION_ID number(5)
       , MOBILE_ID number(5)
       , PRODUCT_ID number(5)
       , STATUS varchar(50)
       , STATUS_DATE date
       , CREATED date
       , CREATED_BY varchar(50)
       , LAST_UPD date
       , LAST_UPD_BY varchar(50)
);