package com.craig.user.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

public class PasswordUtilTest {
    @Test
    void testGetEncryptedPwd() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String md5Password = PasswordUtil.getEncryptedPwd("admin");
        System.out.println("password md5 for admin : " + md5Password);
        boolean result = PasswordUtil.validate("admin", md5Password);
        assertTrue(result);
    }
}
