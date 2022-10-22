package com.craig.user.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_user_following")
public class UserFollowing {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long followerId;

    private Date createdAt;

    private Date updatedAt;

    private Boolean deleted;
}
