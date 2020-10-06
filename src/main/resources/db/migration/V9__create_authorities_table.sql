CREATE TABLE IF NOT EXISTS AUTHORITIES (
    user_id int8 not null,
    roles varchar not null,
    constraint fk_roles_user foreign key(user_id) references users(id)
);