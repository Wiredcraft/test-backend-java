package me.solution.model.reqresp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * update profile request
 *
 * @author davincix
 * @since 2023/5/20 15:47
 */
@ApiModel("update profile request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileReq {

    @ApiModelProperty(value = "new address", example = "shanghai")
    private String address;

    @ApiModelProperty(value = "new description", example = "new muggle")
    private String description;
}
