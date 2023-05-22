package me.solution.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
* following-follower relation
*
* @author davincix
* @since 2023/5/22 05:31
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_follower")
@Builder
public class Follower {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long followingId;

    private Long followerId;

    /**
     * create time
     */
    private Date createdAt;
}
