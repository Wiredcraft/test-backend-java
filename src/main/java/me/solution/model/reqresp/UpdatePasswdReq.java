package me.solution.model.reqresp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * update passwd request
 *
 * @author davincix
 * @since 2023/5/20 15:47
 */
@ApiModel("update password request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswdReq {

    @ApiModelProperty(value = "new password", required = true, example = "1234new")
    @NotEmpty(message = "the passwd is empty")
    @Length(min = 8, max = 50, message = "the passwd length should be between 8-50")
    private String passwd;
}
