create table FLOWERS(
    id bigint primary key auto_increment,
    title varchar(30) not null,
    price decimal check (price >= 0),
    count int check (count >= 0)
);

-- some test values
insert into FLOWERS values (null, 'Rose', 25, 100);
insert into FLOWERS values (null, 'Dandelion', 10.60, 40);
insert into FLOWERS values (null, 'Sunflower', 13.46, 7);