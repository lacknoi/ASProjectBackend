--drop table USRDEBT.AS_DCC_TRANSACTION_BACKLOG
create table USRDEBT.AS_DCC_TRANSACTION_BACKLOG(
  MODE_ID nvarchar2(5)
  , PREASSIGN_ID nvarchar2(10)
  , PREASSIGN_DATE date
  , ASSIGN_ID nvarchar2(10)
  , ASSIGN_DATE date
  , ACCOUNT_NO nvarchar2(50)
  , REASON_ID int
  , CREATED date
  , CREATED_BY nvarchar2(50)
);