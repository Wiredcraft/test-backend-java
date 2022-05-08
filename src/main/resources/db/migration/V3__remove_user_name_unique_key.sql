alter table user
    drop key user_name_uindex;

alter table user
    modify name varchar(64) null comment 'user name, like Jerry Chin.';
