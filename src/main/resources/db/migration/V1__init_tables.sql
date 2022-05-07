create table user
(
    id          varchar(64)                        not null,
    name        varchar(64)                        not null comment 'unique user name.',
    dob         DATE                               null comment 'date of birth.',
    address     varchar(255)                       null comment 'user contact address',
    description varchar(255)                       null,
    created_at  datetime default current_timestamp not null,
    constraint user_pk
        primary key (id)
);

create unique index user_name_uindex
    on user (name);

