package com.wiredcraft.testbackend.util;

public enum CacheKeyEnum {

    /**
     * use string to store user info
     */
    KEY_USER_INFO("user:info:{0}", "store user info"),

    /**
     * use string to store user name info
     */
    KEY_USER_NAME("user:name:{0}", "store user info"),

    /**
     * use zset to store fans of a user
     */
    KEY_USER_FANS_IDS("user:fans:{0}", "store users id who follow you"),
    /**
     * use zset to store users id who you follow
     */
    KEY_USER_FOLLOW_IDS("user:follow:{0}", "store users id who you follow"),
    ;

    /**
     * cache key
     */
    private String key;

    /**
     * cache desc
     */
    private String desc;

    CacheKeyEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
