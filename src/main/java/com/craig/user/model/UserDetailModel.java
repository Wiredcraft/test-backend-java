package com.craig.user.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetailModel extends UserModel {
    /**
     * follwers
     */
    @Schema(description = "user's followers")
    List<SimpleUserModel> followers;

    /**
     * following users
     */
    @Schema(description = "user's followings")
    List<SimpleUserModel> following;
}
