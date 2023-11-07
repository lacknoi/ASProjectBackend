--drop table USRDEBT.AS_DCC_DEBT_CRITERIA
create table USRDEBT.AS_DCC_DEBT_CRITERIA(
  CRITERIA_ID int
  , MODE_ID nvarchar2(5)
  , CRITERIA_TYPE nvarchar2(100)
  , CRITERIA_DESCRIPTION nvarchar2(100)
  , ASSIGN_STATUS nvarchar2(5)
  , PREASSIGN_ID nvarchar2(10)
  , PREASSIGN_DATE date
  , ASSIGN_ID nvarchar2(10)
  , ASSIGN_DATE date
  , UNASSIGN_DATE date
  , DEBT_AMT_FROM number(22,2)
  , DEBT_AMT_TO number(22,2)
  , DEBT_AGE_FROM int
  , DEBT_AGE_TO int
  , ACCOUNT_STATUS_LIST nvarchar2(2000)
  , MOBILE_STATUS_LIST nvarchar2(2000)
  , CREATED date
  , CREATED_BY nvarchar2(50)
  , LAST_UPD date
  , LAST_UPD_BY nvarchar2(50)
);