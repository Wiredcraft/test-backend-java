package me.solution.model.reqresp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("user response")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResp {

    @ApiModelProperty(value = "user id")
    private Long id;

    /**
     * username
     */
    @ApiModelProperty(value = "login name")
    private String name;

    /**
     * date of birth
     */
    @ApiModelProperty(value = "date of birth")
    private Date dob;

    private String address;

    private String description;

    /**
     * create time
     */
    private Date createdAt;
}
