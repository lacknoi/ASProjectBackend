--drop table AS_SMS_TRANSACTION
create table AS_SMS_TRANSACTION(
  SMS_TRANSACTION_ID int
  , ACCOUNT_NO nvarchar2(50)
  , MOBILE_NO nvarchar2(10)
  , CREATED date
  , CREATED_BY nvarchar2(50)
  , LAST_UPD date
  , LAST_UPD_BY nvarchar2(50)
) 