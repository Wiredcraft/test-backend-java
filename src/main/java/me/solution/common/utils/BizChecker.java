package me.solution.common.utils;

import me.solution.common.exception.BizException;
import me.solution.common.enums.ResultCodeEnum;
import me.solution.model.domain.User;
import org.jetbrains.annotations.Contract;

import java.util.Optional;

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

    @Contract("_ -> fail")
    public static void checkUserExist(User user) {
        Optional.ofNullable(user)
                .orElseThrow(() -> new BizException(ResultCodeEnum.USER_NOT_EXIST));
    }
}
