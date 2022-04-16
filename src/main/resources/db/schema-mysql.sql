DROP TABLE IF EXISTS system_user.user;

create table system_user.user
(
    id          bigint auto_increment primary key,
    user_id     varchar(20) unique not null comment 'user id',
    name        varchar(20)        not null comment 'user name',
    dob         varchar(20)        not null comment 'date of birth',
    address     varchar(20) default '' null comment 'user address',
    description varchar(20) default '' null comment 'user description',
    created_at  timestamp          not null comment 'user created date',
    updated_at  timestamp          not null comment 'user updated date'
);

create table system_user.friend
(
    id         bigint auto_increment primary key,
    user_id    varchar(20) not null comment 'user id',
    friend_id  varchar(20) not null comment 'friend id',
    created_at timestamp   not null comment 'user created date',
    updated_at timestamp   not null comment 'user updated date',
    constraint friend_user_id_friend_id_index unique (user_id, friend_id)
);

