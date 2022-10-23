-- Active: 1666423863732@@127.0.0.1@3306@user_center
CREATE user_center;

USING user_center;

CREATE TABLE
    t_user(
        `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'primary key',
        `name` VARCHAR(255) NOT NULL COMMENT 'user name',
        `dob` DATETIME NULL COMMENT 'date of birth',
        `address` VARCHAR(255) NULL COMMENT 'user address',
        `description` VARCHAR(255) NULL COMMENT 'user description',
        `created_at` DATETIME NOT NULL DEFAULT now() COMMENT 'user create date',
        `updated_at` DATETIME NULL DEFAULT now() ON UPDATE now() COMMENT 'update time',
        `deleted` BIT NOT NULL DEFAULT 0 COMMENT 'logic delete flag, 0:exist,1:deleted',
        PRIMARY KEY (`id`)
    ) COMMENT 'user table';

CREATE TABLE t_user_follower(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'primary key',
    `user_id` BIGINT UNSIGNED NOT NULL COMMENT 't_user.id',
    `follower_id` BIGINT UNSIGNED NOT NULL COMMENT 'follower_id reference to t_user.id',
    `created_at` DATETIME NOT NULL DEFAULT now() COMMENT 'relation create date',
    `updated_at` DATETIME NULL DEFAULT now() ON UPDATE now() COMMENT 'update time',
    `deleted` BIT NOT NULL DEFAULT 0 COMMENT 'logic delete flag, 0:exist,1:deleted',
    PRIMARY KEY (`id`)
) COMMENT 'user follower relation table';

CREATE TABLE t_user_coordinate(  
    `id` BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'primary Key',
    `user_id` BIGINT UNSIGNED NOT NULL COMMENT 'user.id',
    `last_coord` POINT NOT NULL COMMENT 'last login coordinate',
    `log_time` DATETIME NOT NULL DEFAULT now() COMMENT 'log Time'
) COMMENT 'user last login coordinate record';