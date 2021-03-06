create table ORDERS(
    id bigint primary key auto_increment,
    fullPrice decimal not null check (fullPrice >= 0),
    dateCreation timestamp not null,
    dateClosing timestamp,
    status varchar(10) check (status in ('CREATED', 'PAID', 'CLOSED')),
    owner_login varchar(30),
    foreign key (owner_login) references USERS(login)
);

-- some test values
insert into ORDERS values (null, 300, '2019-04-15 10:00:00', null, 'CREATED', 'login1');
insert into ORDERS values (null, 150, '2019-03-15 10:00:00', '2019-03-17 10:00:00', 'CLOSED', 'login1');
insert into ORDERS values (null, 500, '2019-04-15 12:00:00', null, 'PAID', 'login1');
insert into ORDERS values (null, 200, '2019-04-13 12:00:00', null, 'CREATED', 'login2');