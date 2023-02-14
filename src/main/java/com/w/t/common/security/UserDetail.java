package com.w.t.common.security;

import java.util.Set;

public class UserDetail {

    private Long userId;

    private Set<String> authorities;

    public UserDetail() {
    }

    public UserDetail(Long userId) {
        this.userId = userId;
    }

    public UserDetail(Long userId, Set<String> authorities) {
        this.userId = userId;
        this.authorities = authorities;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

}
