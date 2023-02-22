package com.wiredcraft.testbackend.entity.param;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * author: yongchen
 * date: 2023/2/19
 */
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = -4014166266633094073L;

    @NotNull(message = "username cannot be null")
    private String username;

    @NotNull(message = "password cannot be null")
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
