package me.solution.model.reqresp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * login request model
 *
 * @author davincix
 * @since 2023/5/20 15:47
 */
@ApiModel("login request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {

    @ApiModelProperty(value = "login name", required = true, example = "demo")
    private String name;

    @ApiModelProperty(value = "login passwd", required = true, example = "1234")
    private String passwd;
}
