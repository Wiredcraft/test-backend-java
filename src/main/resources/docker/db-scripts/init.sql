CREATE DATABASE IF NOT EXISTS test_db
    DEFAULT CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS `user`
(
    `id`          int(6)  UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `name`        varchar(50) NOT NULL COMMENT 'user name',
    `password`    varchar(50) NOT NULL COMMENT 'user password',
    `dob`         DATE NULL COMMENT 'date of birth',
    `address`     varchar(50) NULL COMMENT 'user address',
    `description` varchar(100)  NULL COMMENT 'user description',
    `longitude`   decimal(18, 15) NULL COMMENT 'user location longitude',
    `latitude`    decimal(18, 15) NULL COMMENT 'user location latitude',
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_name_password(`name`, `password`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
