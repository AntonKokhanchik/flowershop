create table FLOWERS(
    id bigserial primary key,
    title varchar(30) not null,
    price decimal check (price >= 0),
    count int check (count >= 0)
);

-- some test values
insert into FLOWERS values (default, 'Rose', 25, 100);
insert into FLOWERS values (default, 'Dandelion', 10.60, 40);
insert into FLOWERS values (default, 'Sunflower', 13.46, 7);