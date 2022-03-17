package com.zhangyongxin.demo.common;

/**
 * @Auther zhangyongxin
 * @date 2022/3/20 下午9:33
 */
public enum UserTypeEnum {
    MANAGE("1"),
    WECHAT("2");

    private final String value;

    public String getValue() {
        return value;
    }

    UserTypeEnum(String value) {
        this.value = value;
    }
}
