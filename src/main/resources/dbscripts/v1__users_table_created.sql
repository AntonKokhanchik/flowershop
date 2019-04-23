create table USERS(
    login varchar(30) primary key,
    password varchar(30) not null,
    fullName varchar(50) not null,
    address varchar(50),
    phone varchar(20),
    balance decimal check (balance >= 0),
    discount int check (discount >= 0)
);

insert into USERS values('admin', 'admin123', 'admin', 'unknown', '+0(000)000-00-00', 0, 0);

-- some test values
insert into USERS values('login1', 'pass1', 'Alex1', 'anywhere1', '+0(000)000-00-01', 0, 0);
insert into USERS values('login2', 'pass2', 'Alex2', 'anywhere2', '+0(000)000-00-02', 500, 0);
insert into USERS values('login3', 'pass3', 'Alex3', 'anywhere3', '+0(000)000-00-03', 0, 0);
insert into USERS values('login4', 'pass4', 'Alex4', 'anywhere4', '+0(000)000-00-04', 0, 0);
insert into USERS values('login5', 'pass5', 'Alex5', 'anywhere5', '+0(000)000-00-05', 0, 0);
