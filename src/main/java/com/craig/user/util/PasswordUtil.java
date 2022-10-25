package com.craig.user.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class PasswordUtil {
    private static final String HEX_NUMS_STR = "0123456789ABCDEF";
    private static final Integer SALT_LENGTH = 12;

    /**
     * Convert a hexadecimal string into a byte array
     * 
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4
                    | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    /**
     * Convert the specified byte array into a hexadecimal string
     * 
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    public static Boolean validate(String password, String passwordMd5)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // Convert password in hexadecimal string format into byte array
        byte[] pwdInDb = hexStringToByte(passwordMd5);
        // Declare salt variables
        byte[] salt = new byte[SALT_LENGTH];
        // Extract the salt from the password byte array stored in the database
        System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
        // Create a message digest object
        MessageDigest md = MessageDigest.getInstance("MD5");
        // Pass salt data into the message digest object
        md.update(salt);
        // Pass the password data to the message digest object
        md.update(password.getBytes("UTF-8"));
        // Generate a message digest of the entered password
        byte[] digest = md.digest();
        // Declare a variable that saves the password message digest in the database
        byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
        // Get the message digest of the password in the database
        System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);
        // Compare whether the message digest generated based on the input password is
        // the same as the message digest in the database
        if (Arrays.equals(digest, digestInDb)) {
            // If the password is correct, return a password matching message
            return true;
        } else {
            // Incorrect password returns a password mismatch message
            return false;
        }
    }

    /**
     * Obtain the encrypted hexadecimal form password
     * 
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getEncryptedPwd(String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // Declare the encrypted password array variable
        byte[] pwd = null;
        // Random number generator
        SecureRandom random = new SecureRandom();
        // Declare salt array variable 12
        byte[] salt = new byte[SALT_LENGTH];
        // Put the random number into the salt variable
        random.nextBytes(salt);

        // Declare the message digest object
        MessageDigest md = null;
        // Create message summary
        md = MessageDigest.getInstance("MD5");
        // Pass salt data into the message digest object
        md.update(salt);
        // Pass the password data to the message digest object
        md.update(password.getBytes("UTF-8"));
        // Get the byte array of the message digest
        byte[] digest = md.digest();

        // Because the salt is to be stored in the byte array of the password, the byte
        // length of the salt is added
        pwd = new byte[digest.length + SALT_LENGTH];
        // Copy the bytes of the salt to the first 12 bytes of the generated encrypted
        // password byte array so that the salt can be taken out when verifying the
        // password
        System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
        // Copy the message digest to the byte starting from the 13th byte of the
        // encrypted password byte array
        System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
        // Convert the encrypted password in byte array format into a password in
        // hexadecimal string format
        return byteToHexString(pwd);
    }
}
