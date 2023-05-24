package me.solution.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * enum for default result
 *
 * @author davincix
 * @since 2023/5/22 11:25
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    /**
     * instance
     */
    SUCCESS("0", "success"),
    NULL_POINTER_EXCEPTION("1", "null pointer exception"),
    INTERNAL_EXCEPTION("500", "internal exception"),

    UNAUTHORIZED("-1000", "unauthorized"),
    FORBIDDEN("-1001", "insufficient privileges"),
    INCORRECT_NAME_OR_PASSWD("-1002", "incorrect name or passwd"),
    USER_NOT_EXIST("-1003", "user not exist"),
    USER_NAME_TAKEN("-1004", "the name is taken"),
    ILLEGAL_PARAM("-1005", "illegal param"),
    ;

    private final String code;
    private final String msg;
}
