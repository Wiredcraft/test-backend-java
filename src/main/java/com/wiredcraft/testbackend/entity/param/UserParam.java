package com.wiredcraft.testbackend.entity.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiredcraft.testbackend.entity.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * query user param
 * author: yongchen
 * date: 2023/2/18
 */
public class UserParam {
    /**
     * user name
     */
    @Length(max = 50, message = "The length of name can not exceed 50")
    @NotNull(message = "name cannot be null")
    private String name;

    /**
     * date of birth
     */
    @NotNull(message = "dob cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    /**
     * user address
     */
    @Length(max = 100, message = "The length of address can not exceed 100")
    @NotNull(message = "address cannot be null")
    private String address;

    /**
     * user description
     */
    @Length(max = 255, message = "The length of description can not exceed 255")
    @NotNull(message = "description cannot be null")
    private String description;

    public static User convertToUser(UserParam userParam) {
        if (userParam == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
