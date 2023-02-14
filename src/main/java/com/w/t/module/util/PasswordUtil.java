package com.w.t.module.util;

import org.apache.shiro.crypto.hash.SimpleHash;

public class PasswordUtil {

    private final static String algorithmName = "md5";

    private final static Integer hashIterations = 1024;

    public static String encryptPassword(String password, String salt) {
        return new SimpleHash(algorithmName, password, salt, hashIterations).toString();
    }

}
