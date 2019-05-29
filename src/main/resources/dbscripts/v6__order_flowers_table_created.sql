create table ORDER_FLOWERS(
    id bigserial primary key,
    flowerName varchar(30) not null,
    price decimal check (price >= 0),
    count int check (count >= 0),
    order_id bigint,
    foreign key (order_id) references ORDERS(id)
);

-- some test values
insert into ORDER_FLOWERS values (default, 'Mandrake', 300, 1, 1);
insert into ORDER_FLOWERS values (default, 'Managrass', 50, 3, 2);
insert into ORDER_FLOWERS values (default, 'Mandrake', 300, 1, 3);
insert into ORDER_FLOWERS values (default, 'Managrass', 50, 4, 3);
insert into ORDER_FLOWERS values (default, 'Managrass', 50, 4, 4);