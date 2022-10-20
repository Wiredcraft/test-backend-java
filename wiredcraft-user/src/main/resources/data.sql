CREATE TABLE `user_info`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `uuid`         varchar(60)  NOT NULL COMMENT 'uuid',
    `name`         varchar(150) NOT NULL COMMENT '姓名',
    `dob`          datetime     NOT NULL COMMENT '出生日期',
    `address`      varchar(255)          DEFAULT NULL COMMENT '地址',
    `description`  varchar(255)          DEFAULT NULL COMMENT '描述',
    `created_date` datetime     NOT NULL COMMENT '创建时间',
    `updated_date` datetime     NOT NULL COMMENT '更新时间',
    `created_by`   varchar(60)  NOT NULL DEFAULT '' COMMENT '创建人',
    `updated_by`   varchar(60)  NOT NULL DEFAULT '' COMMENT '更新人',
    `is_deleted`   tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;