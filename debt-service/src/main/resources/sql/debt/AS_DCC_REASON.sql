--drop table USRDEBT.AS_DCC_REASON
create table USRDEBT.AS_DCC_REASON(
  REASON_ID int
  , REASON_TYPE nvarchar2(50)
  , REASON_DESCRIPTION nvarchar2(255)
  , CREATED date
  , CREATED_BY nvarchar2(50)
  , LAST_UPD date
  , LAST_UPD_BY nvarchar2(50)
);