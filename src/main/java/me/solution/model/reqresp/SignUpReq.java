package me.solution.model.reqresp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "the name is empty")
    @Length(min = 8, max = 50, message = "the name length should be between 8-50")
    private String name;

    /**
     * password for login
     */
    @ApiModelProperty(value = "login passwd", required = true, example = "1234")
    @NotEmpty(message = "the passwd is empty")
    @Length(min = 8, max = 50, message = "the passwd length should be between 8-50")
    private String passwd;

    /**
     * date of birth
     */
    @ApiModelProperty(value = "date of birth")
    private Date dob;

    @ApiModelProperty(value = "address", example = "shanghai")
    @Length(max = 128, message = "the max length of address is 128")
    private String address;

    @ApiModelProperty(value = "description", example = "muggle")
    @Length(max = 128, message = "the max length of description is 128")
    private String description;
}
