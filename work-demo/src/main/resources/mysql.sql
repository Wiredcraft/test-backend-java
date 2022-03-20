/*
 Navicat MySQL Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50713
 Source Host           : 127.0.0.1:3306
 Source Schema         : zhangyx_test

 Target Server Type    : MySQL
 Target Server Version : 50713
 File Encoding         : 65001

 Date: 20/03/2022 13:38:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `user_id` int(11) DEFAULT NULL,
                             `role_id` int(11) NOT NULL,
                             PRIMARY KEY (`id`) USING BTREE,
                             KEY `user_id_idx` (`user_id`),
                             KEY `role_id_idx` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of role_user
-- ----------------------------
BEGIN;
INSERT INTO `role_user` VALUES (1, 20, 1);
COMMIT;

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
                           `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `url` varchar(100) DEFAULT '' COMMENT 'url',
                           `name` varchar(100) NOT NULL DEFAULT '' COMMENT '名称',
                           `remark` varchar(500) DEFAULT NULL COMMENT '角色备注',
                           `ico` varchar(100) DEFAULT '' COMMENT '图标',
                           `model` varchar(100) DEFAULT '' COMMENT '所属模块',
                           `p_id` bigint(20) unsigned DEFAULT NULL COMMENT '父节点',
                           `is_delete` int(1) DEFAULT '0' COMMENT '逻辑删除，0表示不删除',
                           `sort` int(12) DEFAULT '0' COMMENT '排序',
                           `permission` varchar(250) NOT NULL COMMENT '符合Shiro格式的权限字符串，例如[maintain:*]',
                           `active_url` varchar(100) DEFAULT NULL COMMENT '选中url',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
BEGIN;
INSERT INTO `tb_menu` VALUES (1, '', '创建用户', NULL, '', '', NULL, 0, 0, 'user:create', NULL);
INSERT INTO `tb_menu` VALUES (2, '', '修改用户', NULL, '', '', NULL, 0, 0, 'user:update', NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
                           `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                           `name` varchar(100) NOT NULL DEFAULT '' COMMENT '名称',
                           `remark` varchar(500) DEFAULT NULL COMMENT '角色备注',
                           `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
                           `create_user` bigint(20) unsigned DEFAULT NULL COMMENT '创建人',
                           `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `update_user` bigint(20) unsigned DEFAULT NULL COMMENT '更新人',
                           `state` int(1) DEFAULT '1' COMMENT '是否冻结，0表示冻结，1表示正常',
                           `is_delete` int(1) DEFAULT '0' COMMENT '逻辑删除，0表示不删除',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_role` VALUES (1, 'admin', '超级管理员', NULL, NULL, '2018-06-22 10:05:57', NULL, 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu` (
                                `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `role_id` bigint(20) unsigned NOT NULL COMMENT '角色id',
                                `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单id',
                                PRIMARY KEY (`id`),
                                KEY `ROLE_ID_INDEX` (`role_id`),
                                KEY `MENU_ID_INDEX` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色与菜单的关联表，负责权限管理';

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `tb_role_menu` VALUES (1, 1, 63);
INSERT INTO `tb_role_menu` VALUES (2, 1, 64);
COMMIT;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
                             `password` varchar(32) DEFAULT NULL COMMENT '密码',
                             `name` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名',
                             `dob` date DEFAULT NULL COMMENT '生日',
                             `address` varchar(255) DEFAULT NULL COMMENT '地址',
                             `description` varchar(255) DEFAULT NULL COMMENT '描述',
                             `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `username` (`username`),
                             KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of user_info
-- ----------------------------
BEGIN;
INSERT INTO `user_info` VALUES (1, 'admin', 'admin', '', NULL, NULL, NULL, '2022-03-20 13:38:33');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
