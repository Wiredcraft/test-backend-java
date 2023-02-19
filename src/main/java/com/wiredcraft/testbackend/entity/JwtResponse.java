package com.wiredcraft.testbackend.entity;

import java.io.Serializable;

/**
 * author: yongchen
 * date: 2023/2/19
 */
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -6589880265116184322L;

    private String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}