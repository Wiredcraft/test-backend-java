DROP TABLE IF EXISTS `account_user`;
CREATE TABLE `account_user`
(
    `id`          int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'user name',
    `password`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'user password',
    `dob`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'date of birth',
    `address`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'user address',
    `description` varchar(0) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'user description',
    `longitude`   decimal(18, 15) NULL DEFAULT NULL COMMENT 'user location longitude',
    `latitude`    double(18, 15
) NULL DEFAULT NULL COMMENT 'user location latitude',
  `create_at` timestamp(0) NULL DEFAULT NULL COMMENT 'create date',
  `update_at` timestamp(0) NULL DEFAULT NULL COMMENT 'update date',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `account_user`(`id`, `name`, `password`, `dob`, `address`, `description`, `longitude`, `latitude`, `create_at`, `update_at`) VALUES (1, 'zhangxiang1', 'e398a3701330607fb15816edcd4dbfd7', 'zhangxiang', NULL, NULL, 116.310771000000000, 40.062630000000000, '2023-02-13 07:39:45', '2023-02-13 08:40:21');
INSERT INTO `account_user`(`id`, `name`, `password`, `dob`, `address`, `description`, `longitude`, `latitude`, `create_at`, `update_at`) VALUES (2, 'zhangxiang2', 'e398a3701330607fb15816edcd4dbfd7', 'zhangxiang', NULL, NULL, 116.310772000000000, 40.062630000000000, '2023-02-13 07:39:45', '2023-02-13 08:40:21');
INSERT INTO `account_user`(`id`, `name`, `password`, `dob`, `address`, `description`, `longitude`, `latitude`, `create_at`, `update_at`) VALUES (3, 'zhangxiang3', 'e398a3701330607fb15816edcd4dbfd7', 'zhangxiang', NULL, NULL, 116.310773000000000, 40.062630000000000, '2023-02-13 07:39:45', '2023-02-13 08:40:21');
INSERT INTO `account_user`(`id`, `name`, `password`, `dob`, `address`, `description`, `longitude`, `latitude`, `create_at`, `update_at`) VALUES (4, 'zhangxiang4', 'e398a3701330607fb15816edcd4dbfd7', 'zhangxiang', NULL, NULL, 116.310774000000000, 40.062630000000000, '2023-02-13 07:39:45', '2023-02-13 08:40:21');
INSERT INTO `account_user`(`id`, `name`, `password`, `dob`, `address`, `description`, `longitude`, `latitude`, `create_at`, `update_at`) VALUES (5, 'zhangxiang5', 'e398a3701330607fb15816edcd4dbfd7', 'zhangxiang', NULL, NULL, 116.310775000000000, 40.062630000000000, '2023-02-13 07:39:45', '2023-02-13 08:40:21');

DROP TABLE IF EXISTS `follow_user`;
CREATE TABLE `follow_user`
(
    `id`           int NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `follower_id`  int NOT NULL COMMENT 'follower user id',
    `following_id` int NOT NULL COMMENT 'following user id',
    `create_at`    timestamp NULL DEFAULT NULL COMMENT 'create date',
    `update_at`    timestamp NULL DEFAULT NULL COMMENT 'update date',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

INSERT INTO `follow_user`(`id`, `follower_id`, `following_id`, `create_at`, `update_at`) VALUES (1, 1, 2, '2023-02-13 07:39:45', '2023-02-13 07:39:45');
INSERT INTO `follow_user`(`id`, `follower_id`, `following_id`, `create_at`, `update_at`) VALUES (2, 1, 3, '2023-02-13 07:39:45', '2023-02-13 07:39:45');
INSERT INTO `follow_user`(`id`, `follower_id`, `following_id`, `create_at`, `update_at`) VALUES (3, 1, 4, '2023-02-13 07:39:45', '2023-02-13 07:39:45');
