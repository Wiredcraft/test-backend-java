alter table user
    add account_id bigint not null comment 'associated account id.';

create index user_user_id_index
    on user (account_id);

