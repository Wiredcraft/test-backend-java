package me.solution.common.annotations.exception;

import me.solution.common.enums.ResultCodeEnum;
import org.jetbrains.annotations.Contract;

/**
 * @author davincix
 * @since 2023/5/22 12:25
 */
public class BizChecker {
    @Contract("true -> fail")
    public static void checkUserNameTaken(boolean expression) {
        if (expression) {
            throw new BizException(ResultCodeEnum.USER_NAME_TAKEN);
        }
    }
}
