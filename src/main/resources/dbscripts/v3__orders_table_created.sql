create table ORDERS(
    id bigint auto_increment,
    fullPrice decimal,
    dateCreation timestamp,
    dateClosing timestamp,
    status varchar(10),
    owner_login varchar(30)
);

-- some test values
insert into ORDERS values (null, 300, '2019-04-15 10:00:00', null, 'CREATED', 'login1');
insert into ORDERS values (null, 500, '2019-04-15 12:00:00', null, 'CREATED', 'login1');
insert into ORDERS values (null, 200, '2019-04-13 12:00:00', null, 'CREATED', 'login2');