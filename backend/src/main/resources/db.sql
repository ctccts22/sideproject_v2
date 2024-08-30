-- auto-generated definition
create table users
(
    user_id        bigserial
        primary key,
    created_at     varchar(255),
    deleted_at     varchar(255),
    updated_at     varchar(255),
    birth          varchar(255) not null,
    email          varchar(255) not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
        unique,
    gender         varchar(255) not null,
    mobile_carrier varchar(255) not null,
    name           varchar(255) not null,
    password       varchar(255) not null,
    phone          varchar(255) not null,
    status         varchar(255) not null,
    role_id        varchar(255)
        constraint fkp56c1712k691lhsyewcssf40f
        references roles
);

alter table users
    owner to myid;


-- auto-generated definition
create table roles
(
    role_id varchar(255) not null
        primary key,
    name    varchar(255) not null
);

alter table roles
    owner to myid;


-- auto-generated definition
create table orders
(
    order_id     varchar(255) not null
        primary key,
    order_date   varchar(255) not null,
    order_status varchar(255) not null,
    total_price  integer      not null,
    user_id      bigint
        constraint fk32ql8ubntj5uh44ph9659tiih
        references users
);

alter table orders
    owner to myid;


-- auto-generated definition
create table addresses
(
    addresses_id bigserial
        primary key,
    main_address varchar(255) not null,
    sub_address  varchar(255) not null,
    zip_code     varchar(255),
    user_id      bigint
        constraint fk1fa36y2oqhao3wgg2rw1pi459
        references users,
    address_type varchar(255) not null
);

alter table addresses
    owner to myid;






