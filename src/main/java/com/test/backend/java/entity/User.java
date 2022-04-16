package com.test.backend.java.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Rock Jiang
 * @since 2022-04-16
 */
@Getter
@Setter
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("user name")
    private String userId;

    @ApiModelProperty("user name")
    private String name;

    @ApiModelProperty("date of birth")
    private String dob;

    @ApiModelProperty("user address")
    private String address;

    @ApiModelProperty("user description")
    private String description;

    @ApiModelProperty("user created date")
    private LocalDateTime createdAt;

    @ApiModelProperty("user updated date")
    private LocalDateTime updatedAt;

}
