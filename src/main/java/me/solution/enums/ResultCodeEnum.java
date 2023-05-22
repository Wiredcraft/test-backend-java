package me.solution.enums;

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

    ILLEGAL_TOKEN("-1000", "illegal token"),
    INCORRECT_NAME_OR_PASSWD("-1001", "incorrect name or passwd"),
    USER_NAME_TAKEN("-1002", "the name is taken"),
    ;

    private final String code;
    private final String msg;
}
