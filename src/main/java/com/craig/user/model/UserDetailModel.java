package com.craig.user.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetailModel extends UserModel implements UserDetails{
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

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.getName();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        //user is allways available
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        //no lock function for user
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        //always enabled
        return true;
    }
}
