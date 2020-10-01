CREATE TABLE USERS (
    id int8 not null,
    username varchar(128) not null unique,
    password varchar(256) not null,
    primary key (id)
);