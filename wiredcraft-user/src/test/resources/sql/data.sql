DROP TABLE IF EXISTS USER_INFO;

DROP TABLE IF EXISTS USER_INFO;

CREATE TABLE USER_INFO
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(250) NOT NULL,
    address     VARCHAR(250) DEFAULT NULL,
    description varchar(255) DEFAULT NULL,
    created_by  varchar(60)  DEFAULT NULL,
    updated_by  varchar(60)  DEFAULT NULL,
    is_deleted  bit(1) DEFAULT NULL
);

-- CREATE TABLE USER_INFO
-- (
--     id           INT AUTO_INCREMENT PRIMARY KEY,
--     name         varchar(255) DEFAULT NULL,
--     address      varchar(255) DEFAULT NULL,
--     description  varchar(255) DEFAULT NULL,
--     created_by   varchar(60) DEFAULT NULL,
--     updated_by   varchar(60) DEFAULT NULL,
--     is_deleted   bit(1) DEFAULT NULL
-- );



