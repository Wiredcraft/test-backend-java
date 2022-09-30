/*
 Navicat Premium Data Transfer

 Source Server         : localMysql
 Source Server Type    : MySQL
 Source Server Version : 50710
 Source Host           : localhost
 Source Database       : user_center

 Target Server Type    : MySQL
 Target Server Version : 50710
 File Encoding         : utf-8

 Date: 10/01/2022 01:12:37 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `friend`
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `friend_id` int(11) NOT NULL,
  `friend_name` varchar(255) DEFAULT NULL COMMENT '朋友的userId',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `friend`
-- ----------------------------
BEGIN;
INSERT INTO `friend` VALUES ('4', '3', 'Mia1', '1', 'Mia', '0', '2022-09-30 18:00:02', '2022-10-01 00:55:16'), ('5', '3', 'Mia1', '5', 'Mia5', '0', '2022-09-30 18:01:39', '2022-09-30 18:01:39'), ('6', '3', 'Mia1', '2', 'Miak', '0', '2022-09-30 18:30:18', '2022-09-30 18:30:18'), ('7', '7', 'kate', '3', 'Mia1', '0', '2022-10-01 00:47:24', '2022-10-01 00:55:12');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '姓名',
  `dob` date DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '地址',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'Mia', null, 'tvghjbhjkkkkk', '上海市虹口区', '描述', '0', '2022-09-30 13:08:27', '2022-09-30 13:08:27'), ('2', 'Miak', null, '1111', '1111', '1111', '0', '2022-09-30 13:23:49', '2022-09-30 18:29:28'), ('3', 'Mia1', '2022-09-30', '1111', '上海市黄浦区', '我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长我超长长', '0', '2022-09-30 13:24:46', '2022-09-30 14:56:50'), ('4', 'Mia4', null, '1111', '1111', '1111', '1', '2022-09-30 13:25:54', '2022-09-30 18:07:10'), ('5', 'Mia5', null, '1111', '1111', '1111', '0', '2022-09-30 13:26:07', '2022-09-30 13:26:07'), ('6', 'Cindy', null, 'tvghjbhjkkkkk', '上海市虹口区', '描述asdfasdfasd', '0', '2022-10-01 00:12:08', '2022-10-01 00:12:08'), ('7', 'kate', '1997-07-20', 'tvghjbhjkkkkk', '地址地址地址', '我是描述信息', '0', '2022-10-01 00:12:55', '2022-10-01 00:43:32');
COMMIT;

-- ----------------------------
--  Table structure for `user_geohash`
-- ----------------------------
DROP TABLE IF EXISTS `user_geohash`;
CREATE TABLE `user_geohash` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '名称',
  `longitude` double NOT NULL COMMENT '经度',
  `latitude` double NOT NULL COMMENT '纬度',
  `geo_code` varchar(64) NOT NULL COMMENT '经纬度所计算的geohash码',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_geo_hash` (`geo_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `user_geohash`
-- ----------------------------
BEGIN;
INSERT INTO `user_geohash` VALUES ('1', '1', '120.24191', '29.28946', 'wtjw85vrrx4d', '2022-09-30 21:29:51', '2022-09-30 21:42:32', '0'), ('2', '2', '119.635918', '29.110464', 'wtjjd7khwzy1', '2022-09-30 21:31:42', '2022-09-30 21:42:32', '0'), ('3', '3', '103.752457', '36.068718', 'wq3v0yn5x2r5', '2022-09-30 21:42:32', '2022-09-30 21:42:32', '0'), ('4', '4', '121.478889', '31.271572', 'wtw3ukf4p78c', '2022-09-30 21:42:32', '2022-09-30 21:42:32', '0'), ('5', '5', '120.0439', '29.363371', 'wtjr59mq195d', '2022-09-30 21:42:32', '2022-09-30 21:43:39', '0'), ('6', '7', '120.49476', '30.08189', 'wtmejujxeqtp', '2022-10-01 01:01:28', '2022-10-01 01:01:28', '0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
