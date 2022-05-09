create table people_relation
(
    id                   bigint   not null,
    current_account_id bigint   not null,
    created_at           datetime null,
    following_account_id  bigint   not null,
    constraint people_relation_pk
        primary key (id)
)
    comment 'store following and follower relationships between people.';

