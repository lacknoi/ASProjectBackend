--drop table AS_ACCOUNT_BALANCE
create table AS_ACCOUNT_BALANCE
(
  ACCOUNT_NO    VARCHAR2(20) not null,
  TOTAL_BALANCE NUMBER(16,2),
  CREATED       date,
  CREATED_BY    varchar(50),
  LAST_UPD      date,
  LAST_UPD_BY   varchar(50)
);