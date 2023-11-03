--drop table USRDEBT.AS_PM_AR_TRANSACTION
create table USRDEBT.AS_PM_AR_TRANSACTION
(
  TRANSACTION_ID int,
  ACCOUNT_NO    VARCHAR2(20) not null,
  INVOICE_NUM   varchar2(50),
  SEQ_NUM       int,
  MOVEMENT_FLAG varchar(2),
  MOVEMENT_DATE date,
  REF_ID        int,
  TOTAL_MNY     NUMBER(16,2),
  CREATED       date,
  CREATED_BY    varchar(50),
  LAST_UPD      date,
  LAST_UPD_BY   varchar(50)
);