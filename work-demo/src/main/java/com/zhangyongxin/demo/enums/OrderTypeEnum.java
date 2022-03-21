package com.zhangyongxin.demo.enums;

/**
 * 订单状态枚举
 * @Auther zhangyongxin
 * @date 2022/3/17 下午8:02
 */
public enum OrderTypeEnum {

    NOT_PAYED(1,"已预约未付款"),
    PAYED(3,"已预约已付款"),
    CHECKED(5,"已验房"),
    EVALUATED(8,"已评价"),
    CANCELED(12,"已取消");

    private int code;

    private String describe;

    OrderTypeEnum(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public int getCode() {
        return code;
    }

}
