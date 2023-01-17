package com.wiredcraft.user.tiny.modules.ums.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 *
 * @author yuao
 * @since 2023-01-14
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Follower对象", description = "")
public class Follower implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userId;

    private String followerId;


}
