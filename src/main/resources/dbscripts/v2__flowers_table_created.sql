create table FLOWERS(
    id bigint auto_increment,
    title varchar(30),
    price decimal,
    count int
);

-- some test values
insert into FLOWERS values (null, 'Rose', 25, 100);
insert into FLOWERS values (null, 'Dandelion', 10.60, 40);
insert into FLOWERS values (null, 'Sunflower', 13.46, 7);