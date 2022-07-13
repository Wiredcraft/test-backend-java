create table user_table (
    id integer not null auto_increment,
    name varchar(255) not null default "",
    dob timestamp,
    address varchar(255) not null default "",
    description text not null default "",
    created_at timestamp not null default current_timestamp,
    primary key (id)
) engine innodb character set 'utf8mb4';
-- id has unique index