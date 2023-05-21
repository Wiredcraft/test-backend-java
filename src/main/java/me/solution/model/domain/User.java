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
 * user domain
 *
 * @author davincix
 * @since 2023/5/20 17:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_user")
@Builder
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * username
     */
    private String name;

    /**
     * password for login
     */
    private String passwd;

    /**
     * date of birth
     */
    private Date dob;

    private String address;

    private String description;

    /**
     * {@link me.solution.enums.UserDeleteFlag}
     */
    private Integer deleteFlag;

    /**
     * create time
     */
    private Date createdAt;
}
