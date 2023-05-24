-- create database
create
database if not exists auth
    default character set utf8mb4
    default collate utf8mb4_unicode_ci;
use auth;

-- t_user
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`          bigint                                  NOT NULL AUTO_INCREMENT COMMENT 'user id',
    `name`        varchar(50) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT 'username',
    `passwd`      varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'password for login',
    `dob`         datetime                                         DEFAULT NULL COMMENT 'date of birth',
    `address`     varchar(128) COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT 'address',
    `delete_flag` tinyint                                          DEFAULT '0' COMMENT 'delete flag, 0:normal; 1:deleted',
    `description` varchar(128) COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT 'description',
    `created_at`  datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_username` (`name`) COMMENT 'unique index for username'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='user';

-- follow
DROP TABLE IF EXISTS `t_following`;
CREATE TABLE `t_following`
(
    `id`           bigint NOT NULL AUTO_INCREMENT COMMENT 'pk',
    `follower_id`  bigint NOT NULL COMMENT 'follower user id',
    `following_id` bigint NOT NULL COMMENT 'following user id',
    `created_at`   datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'record created datetime',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_follower_following` (`follower_id`,`following_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='following relations';

DROP TABLE IF EXISTS `t_follower`;
CREATE TABLE `t_follower`
(
    `id`           bigint NOT NULL AUTO_INCREMENT COMMENT 'pk',
    `following_id` bigint NOT NULL COMMENT 'following user id',
    `follower_id`  bigint NOT NULL COMMENT 'follower user id',
    `created_at`   datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'record created datetime',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_following_follower` (`following_id`,`follower_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='follower relations';
