create table if not exists orders
(
    id serial primary key
);

create table if not exists orders_line_items
(
    id       serial primary key,
    product  int not null,
    quantity int not null,
    orders   int references orders (id)
);