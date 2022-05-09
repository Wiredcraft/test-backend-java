create table account
(
    id       bigint auto_increment,
    account  varchar(64) not null comment 'unique user account.',
    password varchar(64) null comment 'user encrypted password.',
    constraint account_pk
        primary key (id)
);

create unique index account_account_uindex
    on account (account);
