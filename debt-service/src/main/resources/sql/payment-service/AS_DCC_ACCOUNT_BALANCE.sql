--drop table USRDEBT.AS_DCC_ACCOUNT_BALANCE
create table USRDEBT.AS_DCC_ACCOUNT_BALANCE
(
  ACCOUNT_BALANCE_ID int,
  ACCOUNT_NO    VARCHAR2(20) not null,
  TOTAL_BALANCE NUMBER(16,2),
  LAST_UPD      date
);