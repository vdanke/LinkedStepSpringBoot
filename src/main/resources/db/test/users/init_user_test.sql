CREATE TABLE IF NOT EXISTS USERS (
    id int not null,
    username varchar not null unique,
    password varchar not null,
    age int check(age >= 16 and age <= 150)
);

CREATE TABLE IF NOT EXISTS PROFILES (
    id int not null,
    description varchar(512) not null,
    user_id int not null,
    primary key (id),
    constraint fk_profile_user foreign key(user_id) references users(id)
);

CREATE TABLE IF NOT EXISTS AUTHORITIES (
    user_id int not null,
    roles varchar not null,
    constraint fk_roles_user foreign key(user_id) references users(id)
);


INSERT INTO USERS VALUES (1, 'first', 'first', 18);
INSERT INTO USERS VALUES (2, 'second', 'second', 27);
INSERT INTO USERS VALUES (3, 'third', 'third', 39);

INSERT INTO PROFILES VALUES (1, 'first', 1);
INSERT INTO PROFILES VALUES (2, 'second', 2);
INSERT INTO PROFILES VALUES (3, 'third', 3);

INSERT INTO AUTHORITIES VALUES(1, 'ROLE_USER');
INSERT INTO AUTHORITIES VALUES(2, 'ROLE_USER');
INSERT INTO AUTHORITIES VALUES(3, 'ROLE_USER');