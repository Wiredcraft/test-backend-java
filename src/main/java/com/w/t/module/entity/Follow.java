package com.w.t.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.w.t.common.core.web.BaseEntity;
import lombok.Data;

@Data
@TableName("follow_user")
public class Follow extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long followerId;

    private Long followingId;

}
