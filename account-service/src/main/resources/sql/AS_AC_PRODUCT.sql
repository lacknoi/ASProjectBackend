--drop table USRDEBT.AS_AC_PRODUCT
create table USRDEBT.AS_AC_PRODUCT (
      PRODUCT_ID number(5)
       , PRODUCT_CD varchar(5)
       , PRODUCT_NAME varchar(50)
       , PRODUCT_CLASS varchar(50)
       , PRICE number(22,2)
       , STATUS varchar(50)
       , STATUS_DATE date
       , CREATED date
       , CREATED_BY varchar(50)
       , LAST_UPD date
       , LAST_UPD_BY varchar(50)
);

insert into USRDEBT.AS_AC_PRODUCT (PRODUCT_ID, PRODUCT_CD, PRODUCT_NAME, PRODUCT_CLASS, PRICE, STATUS, STATUS_DATE, CREATED, CREATED_BY, LAST_UPD, LAST_UPD_BY)
values (1, 'P0001', 'Service MVPN Ready 500B 18Months 3MB[Sit]', 'Main', 500.00, 'Active', sysdate, sysdate, 'MG', sysdate, 'MG');

insert into USRDEBT.AS_AC_PRODUCT (PRODUCT_ID, PRODUCT_CD, PRODUCT_NAME, PRODUCT_CLASS, PRICE, STATUS, STATUS_DATE, CREATED, CREATED_BY, LAST_UPD, LAST_UPD_BY)
values (2, 'P0002', 'Service MVPN Ready 700B 18Months 3MB[Sit]', 'Main', 700.00, 'Active', sysdate, sysdate, 'MG', sysdate, 'MG');
