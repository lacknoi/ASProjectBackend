--drop table AS_SMS_MESSAGE
create table AS_SMS_MESSAGE(
  MESSAGE_ID int
  , MESSAGE nvarchar2(2000)
  , CREATED date
  , CREATED_BY nvarchar2(50)
  , LAST_UPD date
  , LAST_UPD_BY nvarchar2(50)
);