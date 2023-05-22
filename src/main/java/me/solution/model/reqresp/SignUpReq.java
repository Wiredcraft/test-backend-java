package me.solution.model.reqresp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * signup request model
 *
 * @author davincix
 * @since 2023/5/20 15:47
 */
@ApiModel("signup request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReq {

    /**
     * username
     */
    @ApiModelProperty(value = "login name", required = true, example = "demo")
    private String name;

    /**
     * password for login
     */
    @ApiModelProperty(value = "login passwd", required = true, example = "1234")
    private String passwd;

    /**
     * date of birth
     */
    @ApiModelProperty(value = "date of birth")
    private Date dob;

    @ApiModelProperty(value = "address", example = "shanghai")
    private String address;

    @ApiModelProperty(value = "description", example = "muggle")
    private String description;
}
