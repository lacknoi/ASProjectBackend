--drop table USRDEBT.AS_PM_INVOICE_BALANCE
create table USRDEBT.AS_PM_INVOICE_BALANCE
(
  INVOICE_NUM   varchar2(50),
  ACCOUNT_NO    VARCHAR2(20),
  INVOICE_TYPE  varchar2(50),
  INVOICE_DATE  date,
  BILL_SEQ      int,
  DUE_DATE      date,
  INVOICE_MNY   NUMBER(16,2),
  TOTAL_BALANCE NUMBER(16,2),
  CREATED       date,
  CREATED_BY    varchar(50),
  LAST_UPD      date,
  LAST_UPD_BY   varchar(50)
);