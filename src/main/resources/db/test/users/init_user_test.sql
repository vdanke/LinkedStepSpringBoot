CREATE TABLE IF NOT EXISTS USERS (
    id int not null,
    username varchar not null unique,
    password varchar not null,
    age int check(age >= 16 and age <= 150)
);

INSERT INTO USERS VALUES (1, 'first', 'first', 18);
INSERT INTO USERS VALUES (2, 'second', 'second', 27);
INSERT INTO USERS VALUES (3, 'third', 'third', 39);