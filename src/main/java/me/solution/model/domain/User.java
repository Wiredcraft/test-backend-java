package me.solution.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * user domain
 *
 * @author davincix
 * @since 2023/5/20 17:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_user")
public class User {

    @TableId
    private Long id;

    /**
     * username
     */
    private String username;

    /**
     * create time
     */
    private Date createdAt;
}
