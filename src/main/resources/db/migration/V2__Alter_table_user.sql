alter table users
    change column name username varchar(255) not null,
    change column phone phone_number varchar(20),
    add column password varchar(255) not null;