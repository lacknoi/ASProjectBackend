--drop table USRDEBT.AS_DCC_TEMP_TRANSACTION
create table USRDEBT.AS_DCC_TEMP_TRANSACTION(
  MODE_ID nvarchar2(50)
  , PREASSIGN_ID nvarchar2(50)
  , ACCOUNT_NO nvarchar2(50)
  , CREATED date
  , CREATED_BY nvarchar2(50)
  , LAST_UPD date
  , LAST_UPD_BY nvarchar2(50)
);