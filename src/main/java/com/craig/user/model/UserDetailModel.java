package com.craig.user.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetailModel extends UserModel {
    /**
     * follwers
     */
    List<SimpleUserModel> followers;

    /**
     * following users
     */
    List<SimpleUserModel> following;
}
